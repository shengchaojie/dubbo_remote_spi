package com.souche.extensionlite.platform.config;

import com.souche.extensionlite.common.ApplicationContextHolder;
import com.souche.extensionlite.platform.postprocessor.ExtensionBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shengchaojie
 * @date 2020/8/16
 **/
@Configuration
public class ExtensionConfiguration {

    @Bean
    public ExtensionBeanPostProcessor extensionBeanPostProcessor(){
        return new ExtensionBeanPostProcessor();
    }

    @Bean
    public ApplicationContextHolder applicationContextHolder(){
        return new ApplicationContextHolder();
    }

}
