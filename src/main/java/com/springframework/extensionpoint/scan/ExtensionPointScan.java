package com.springframework.extensionpoint.scan;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 扩展点扫描配置注解
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/04 22:04
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ExtensionPointScannerRegister.class)
public @interface ExtensionPointScan {

    /**
     * Alias for the {@link #basePackages()} attribute.
     */
    @AliasFor("basePackages")
    String[] value() default {};

    /**
     * Base packages to scan for ExtensionPoint interfaces.
     */
    @AliasFor("value")
    String[] basePackages() default {};
}
