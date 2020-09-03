package io.github.shengchaojie.drs.consumer.config;

import io.github.shengchaojie.drs.common.ApplicationContextHolder;
import io.github.shengchaojie.drs.consumer.postprocessor.DefaultExtensionBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shengchaojie
 * @date 2020/8/16
 **/
@Configuration
public class ExtensionConfiguration {

    @Bean
    public ApplicationContextHolder applicationContextHolder(){
        return new ApplicationContextHolder();
    }

    @Bean
    public DefaultExtensionBeanPostProcessor defaultExtensionBeanPostProcessor(){
        return new DefaultExtensionBeanPostProcessor();
    }

}
