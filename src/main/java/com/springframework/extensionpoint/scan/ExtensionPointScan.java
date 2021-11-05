package com.springframework.extensionpoint.scan;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Configuration Example:
 *
 * @Configuration
 * @ExtensionPointScan("com.company.system.appname.extensionpoint")
 * public class ApplicationConfig {
 * }
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({ExtensionPointScannerRegister.class})
public @interface ExtensionPointScan {
    /**
     * Base packages to scan for ExtensionPoint interfaces.
     */
    String[] basePackages() default {};
}
