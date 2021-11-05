package com.springframework.extensionpoint.annotation;

import com.springframework.extensionpoint.model.DoNothingExceptionStrategy;
import com.springframework.extensionpoint.model.ExceptionStrategy;
import com.springframework.extensionpoint.model.ResultStrategy;
import com.springframework.extensionpoint.model.RouterStrategy;

import java.lang.annotation.*;

/**
 * 用于定义扩展点的注解
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/04 22:04
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ExtensionPoint {
    /**
     * 扩展点唯一标识
     */
    String code();

    /**
     * 路由策略
     */
    Class<? extends RouterStrategy<?, ?>> routerStrategy();

    /**
     * 结果取值策略
     */
    Class<? extends ResultStrategy<?>> resultStrategy();

    /**
     * 异常策略
     */
    Class<? extends ExceptionStrategy> exceptionStrategy() default DoNothingExceptionStrategy.class;
}
