package com.souche.extensionlite.common.annotation;

import com.souche.extensionlite.common.Mode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在接口上标注 @SPI 实现SPI
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SPI {

    Mode mode() default Mode.GROUP;

}
