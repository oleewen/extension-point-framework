package com.springframework.extensionpoint.scan;

import com.springframework.extensionpoint.model.ExtensionPoint;
import com.springframework.extensionpoint.model.ExtensionPointCode;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExtensionPointScannerRegister implements ImportBeanDefinitionRegistrar {

    private static Map<ExtensionPointCode, ExtensionPoint> map;

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        ClassPathExtensionPointScanner scanner = new ClassPathExtensionPointScanner(registry);

        /* scan all ExtensionPoint annotation class */
        AnnotationAttributes annotationAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(ExtensionPointScan.class.getName()));
        String[] basePackages = annotationAttrs.getStringArray("basePackages");

        scanner.registerFilters(com.springframework.extensionpoint.annotation.ExtensionPoint.class);
        Set<BeanDefinitionHolder> definitions = scanner.doScan(basePackages);

        /* */
        if(definitions!=null && !definitions.isEmpty()){
            for(BeanDefinitionHolder definition:definitions){
                Class<? extends BeanDefinition> clazz = definition.getBeanDefinition().getClass();

                if(clazz.isAnnotationPresent(com.springframework.extensionpoint.annotation.ExtensionPoint.class)){

                }

            }
        }
    }

    public static <T extends ExtensionPoint> List<T> getExtensionPoints(String code) {
        // use RouterStrategy
        return null;
    }
}
