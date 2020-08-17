package com.souche.extensionlite.business.config;

import com.souche.extensionlite.business.SPIFactoryBean;
import com.souche.extensionlite.business.annotation.SPIRegistrar;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author shengchaojie
 * @date 2020/8/17
 **/
public class SPIFactoryBeanRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(SPIRegistrar.class.getName()));
        for(Class<?> clazz : annoAttrs.getClassArray("value")){
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(SPIFactoryBean.class);
            beanDefinitionBuilder.addConstructorArgValue(clazz);
            registry.registerBeanDefinition(clazz.getName(),beanDefinitionBuilder.getBeanDefinition());
        }
    }
}
