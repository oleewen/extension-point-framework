package com.springframework.extensionpoint.aspect;

import com.springframework.extensionpoint.annotation.ExtensionPoint;
import com.springframework.extensionpoint.scan.ExtensionPointScannerRegister;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Aspect
public class ExtensionPointAspect {
    private static final Logger logger = LoggerFactory.getLogger(ExtensionPointAspect.class);

    @Pointcut("@annotation(com.springframework.extensionpoint.annotation.ExtensionPoint)")
    public void extensionPointPointcut() {
    }

    @Around("extensionPointPointcut()&&@annotation(extensionPoint))")
    public Object methodsAnnotatedWithCoBuildPoint(ProceedingJoinPoint joinPoint, ExtensionPoint extensionPoint) throws Throwable {
        //
        try {
            List<com.springframework.extensionpoint.model.ExtensionPoint> extensionPoints = ExtensionPointScannerRegister.getExtensionPoints(extensionPoint.code());
            // execute extensionPoints
            // use ResultStrategy if need
        } catch (Exception ex){
            // use ExceptionStrategy
        }
        return joinPoint.proceed();
    }
}
