package com.springframework.extensionpoint.sample;

import com.springframework.extensionpoint.scan.ExtensionPointScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 单测SpringBoot应用入口
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/04 22:04
 */
@ExtensionPointScan("com.springframework.extensionpoint")
@SpringBootApplication(scanBasePackages = "com.springframework.extensionpoint")
public class JunitApplication {
    public static void main(String[] args) {
        SpringApplication.run(JunitApplication.class, args);
    }
}
