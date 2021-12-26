package com.springframework.extensionpoint.annotation;

import com.springframework.extensionpoint.model.DefaultDimensionHandler;
import com.springframework.extensionpoint.model.DimensionHandler;

import java.lang.annotation.*;

/**
 * 用于定义扩展点实现的注解
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/04 22:04
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface Extension {

    /**
     * 路由维度值
     */
    String dimensions();

    /**
     * 路由维度值处理器
     */
    Class<? extends DimensionHandler> dimensionHandler() default DefaultDimensionHandler.class;
}
