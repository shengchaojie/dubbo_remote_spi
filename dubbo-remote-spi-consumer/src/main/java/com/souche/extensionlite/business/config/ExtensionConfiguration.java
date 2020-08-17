package com.souche.extensionlite.business.config;

import com.souche.extensionlite.common.ApplicationContextHolder;
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

}
