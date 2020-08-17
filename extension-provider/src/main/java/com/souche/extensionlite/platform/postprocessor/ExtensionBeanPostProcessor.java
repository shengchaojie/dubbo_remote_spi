package com.souche.extensionlite.platform.postprocessor;

import com.souche.extensionlite.common.annotation.Extension;
import com.souche.extensionlite.platform.dubbo.DubboServiceExporter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

/**
 * 处理Extension注解，暴露服务
 *
 * @author yonghuang
 */

@Slf4j
public class ExtensionBeanPostProcessor implements BeanPostProcessor, Ordered {

    private DubboServiceExporter dubboServiceExporter = DubboServiceExporter.getINSTNACE();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Extension extension = AopUtils.getTargetClass(bean).getAnnotation(Extension.class);
        if(extension!=null){
            dubboServiceExporter.export(bean,extension.bizCode());
        }

        return bean;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }

}
