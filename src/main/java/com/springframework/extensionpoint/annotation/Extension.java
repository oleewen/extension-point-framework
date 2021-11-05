package com.springframework.extensionpoint.annotation;

import com.springframework.extensionpoint.model.RouterFeatureStrategy;

import java.lang.annotation.*;

/**
 * 用于定义扩展点实现的注解
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/04 22:04
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Extension {

    /**
     * 路由特征策略
     */
    Class<? extends RouterFeatureStrategy<?>> routerFeatureStrategy();

}
