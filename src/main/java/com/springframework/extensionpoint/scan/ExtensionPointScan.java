package com.springframework.extensionpoint.scan;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ExtensionPointScan {

    /** Alias for the {@link #basePackages()} attribute. */
    String[] value() default {};

    /** Base packages to scan for ExtensionPoint interfaces. */
    String[] basePackages() default {};
}
