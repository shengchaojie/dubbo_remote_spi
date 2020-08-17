package com.souche.extensionlite.platform.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author shengchaojie
 * @date 2020/8/16
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ExtensionConfiguration.class)
public @interface EnableExtensionProvider {
}
