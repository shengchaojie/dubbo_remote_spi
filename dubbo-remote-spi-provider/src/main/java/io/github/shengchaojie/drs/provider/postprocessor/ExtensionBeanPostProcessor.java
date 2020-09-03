package io.github.shengchaojie.drs.provider.postprocessor;

import io.github.shengchaojie.drs.common.ExtensionRegistry;
import io.github.shengchaojie.drs.common.SpiException;
import io.github.shengchaojie.drs.common.annotation.DefaultExtension;
import io.github.shengchaojie.drs.common.annotation.Extension;
import io.github.shengchaojie.drs.common.annotation.SPI;
import io.github.shengchaojie.drs.provider.dubbo.DubboServiceExporter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private Boolean isSPIInterface(Class<?> clazz) {
        return clazz.isAnnotationPresent(SPI.class);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Extension extension = AopUtils.getTargetClass(bean).getAnnotation(Extension.class);
        if (extension != null) {
            Class<?> clazz = AopUtils.getTargetClass(bean);
            //获取Extension标注的实现类继承的spi接口
            List<Class<?>> spiInterfaces = Arrays.stream(ClassUtils.getAllInterfacesForClass(clazz)).filter(this::isSPIInterface).collect(Collectors.toList());
            if (spiInterfaces.size() == 0) {
                throw SpiException.fail(AopUtils.getTargetClass(bean).getName() + "使用了@Extension标注,但未继承SPI接口!");
            }
            dubboServiceExporter.export(bean, extension.bizCode(), spiInterfaces);
        }

        return bean;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }

}
