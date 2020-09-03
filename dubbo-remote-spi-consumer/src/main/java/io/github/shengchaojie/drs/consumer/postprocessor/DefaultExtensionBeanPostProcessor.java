package io.github.shengchaojie.drs.consumer.postprocessor;

import io.github.shengchaojie.drs.common.ExtensionRegistry;
import io.github.shengchaojie.drs.common.SpiException;
import io.github.shengchaojie.drs.common.annotation.DefaultExtension;
import io.github.shengchaojie.drs.common.annotation.SPI;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shengchaojie
 * @date 2020/9/3
 **/
public class DefaultExtensionBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private Boolean isSPIInterface(Class<?> clazz) {
        return clazz.isAnnotationPresent(SPI.class);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        DefaultExtension defaultExtension = AopUtils.getTargetClass(bean).getAnnotation(DefaultExtension.class);
        if (defaultExtension == null) {
            return bean;
        }

        Class<?> clazz = AopUtils.getTargetClass(bean);
        List<Class<?>>  spiInterfaces = Arrays.stream(ClassUtils.getAllInterfacesForClass(clazz)).filter(this::isSPIInterface).collect(Collectors.toList());
        if (spiInterfaces.size() == 0) {
            throw SpiException.fail(AopUtils.getTargetClass(bean).getName() + "使用了@DefaultExtension标注,但未继承SPI接口!");
        }

        for (Class<?> spiInterface : spiInterfaces) {
            ExtensionRegistry.getINSTANCE().registryDefault(spiInterface.getName(),bean);
        }

        return bean;
    }
}
