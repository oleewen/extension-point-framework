package com.springframework.extensionpoint.annotation;

import java.lang.annotation.*;

/**
 * 扩展点自动注入注解
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/12/25 16:10
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExtensionPointAutowired {

}
