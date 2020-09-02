package io.github.shengchaojie.drs.consumer;

import io.github.shengchaojie.drs.common.DubboConfigUtils;
import io.github.shengchaojie.drs.common.ExtensionRegistry;
import io.github.shengchaojie.drs.common.Mode;
import io.github.shengchaojie.drs.common.SpiException;
import io.github.shengchaojie.drs.common.annotation.SPI;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.Constants;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * @author shengchaojie
 * @date 2020/8/14
 **/
public  class SPIFactoryBean<T> implements FactoryBean<T> {

    private Class<T> spiInterface;

    public SPIFactoryBean(Class<T> spiInterface) {
        this.spiInterface = spiInterface;
    }

    @Override
    public T getObject() throws Exception {
        SPI spi = AnnotationUtils.getAnnotation(spiInterface,SPI.class);
        if(Objects.isNull(spi)){
            throw new SpiException("接口未配置SPI注解");
        }
        return (T)Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class[]{spiInterface},new ExtensionInvocationHandler(spiInterface,spi.mode()));
    }

    @Override
    public Class<?> getObjectType() {
        return spiInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    private static class ExtensionInvocationHandler<T> implements InvocationHandler{

        private ReferenceConfigCache referenceConfigCache = ReferenceConfigCache.getCache();

        private Class<T> spiInterface;

        private Mode mode;

        public ExtensionInvocationHandler(Class<T> spiInterface, Mode mode) {
            this.spiInterface = spiInterface;
            this.mode = mode;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String bizCode = BusinessContext.getBizCode();
            if(StringUtils.isEmpty(bizCode)){
                throw new SpiException("获取不到bizCode");
            }

            //先从本地找实现
            T bean = ExtensionRegistry.getINSTANCE().get(spiInterface,bizCode);
            if(Objects.nonNull(bean)){
                return method.invoke(bean,args);
            }

            ReferenceConfig<T> referenceConfig = new ReferenceConfig<T>();
            referenceConfig.setInterface(spiInterface);
            referenceConfig.setConsumer(DubboConfigUtils.getConsumerConfig());
            referenceConfig.setRegistries(DubboConfigUtils.getRegestries());
            referenceConfig.setCheck(false);
            if(Mode.GROUP.equals(mode)) {
                referenceConfig.setGroup(bizCode);
            }
            referenceConfig.setTimeout(10000);//todo 配置化
            T obj = referenceConfigCache.get(referenceConfig);

            if(Mode.TAG.equals(mode)){
                RpcContext.getContext().setAttachment(CommonConstants.TAG_KEY,bizCode);
                RpcContext.getContext().setAttachment(Constants.FORCE_USE_TAG,"true");
            }

            try {
                return method.invoke(obj, args);
            }catch (RpcException rpcException){
                if(!rpcException.isNoInvokerAvailableAfterFilter()){
                    throw rpcException;
                }

                obj = ExtensionRegistry.getINSTANCE().getDefault(spiInterface);
                if(obj == null){
                    throw rpcException;
                }
                return method.invoke(obj, args);
            }
        }
    }

}
