package com.souche.extensionlite.business.annotation;

import com.souche.extensionlite.business.config.SPIFactoryBeanRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author shengchaojie
 * @date 2020/8/17
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(SPIFactoryBeanRegistrar.class)
public @interface SPIRegistrar {

    Class<?>[] value() default {};

}
