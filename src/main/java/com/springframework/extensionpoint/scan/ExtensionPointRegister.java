package com.springframework.extensionpoint.scan;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.springframework.extensionpoint.annotation.Extension;
import com.springframework.extensionpoint.annotation.ExtensionPoint;
import com.springframework.extensionpoint.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.util.ClassUtils.getAllInterfacesForClass;

@Component
public class ExtensionPointRegister implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * key：扩展点code
     * value：扩展点对象
     */
    private static final Map<ExtensionPointCode, ExtensionPointObject> CODE_EXTENSION_POINT_OBJECT_MAP = new ConcurrentHashMap<>();
    /**
     * key：接口名+":"+方法名
     * value：扩展点code
     */
    private static final Map<String, ExtensionPointCode> INTERFACE_CLASS_EXTENSION_POINT_MAP = new ConcurrentHashMap<>();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // 注册策略
        registerStrategy(contextRefreshedEvent.getApplicationContext());
        // 注册扩展点
        registerExtensionPoint(contextRefreshedEvent.getApplicationContext());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void registerStrategy(ApplicationContext applicationContext) {
        Map<String, RouterStrategy> routerStrategyMap = applicationContext.getBeansOfType(RouterStrategy.class);
        if (!CollectionUtils.isEmpty(routerStrategyMap)) {
            routerStrategyMap.values().forEach(strategyBean -> StrategyRegister.getInstance().addRouterStrategy(strategyBean));
        }
        Map<String, ResultStrategy> resultStrategyMap = applicationContext.getBeansOfType(ResultStrategy.class);
        if (!CollectionUtils.isEmpty(resultStrategyMap)) {
            resultStrategyMap.values().forEach(strategyBean -> StrategyRegister.getInstance().addResultStrategy(strategyBean));
        }
        Map<String, ExceptionStrategy> exceptionStrategyMap = applicationContext.getBeansOfType(ExceptionStrategy.class);
        if (!CollectionUtils.isEmpty(exceptionStrategyMap)) {
            exceptionStrategyMap.values().forEach(strategyBean -> StrategyRegister.getInstance().addExceptionStrategy(strategyBean));
        }
        Map<String, DimensionHandler> dimensionHandlerMap = applicationContext.getBeansOfType(DimensionHandler.class);
        if (!CollectionUtils.isEmpty(dimensionHandlerMap)) {
            dimensionHandlerMap.values().forEach(handler -> StrategyRegister.getInstance().addDimensionHandler(handler));
        }
    }

    /**
     * 注册扩展点
     */
    public void registerExtensionPoint(ApplicationContext applicationContext) {
        Map<String, IExtensionPoint> pointBeanMap = applicationContext.getBeansOfType(IExtensionPoint.class);
        Map<ExtensionPointCode, List<ExtensionObject>> codeExtensionObjectMap = Maps.newHashMap();
        pointBeanMap.forEach((beanName, pointBean) -> {
            String interfaceClassName = resolveInterfaceName(pointBean.getClass());
            Method[] extMethods = pointBean.getClass().getDeclaredMethods();
            for (Method method : extMethods) {
                ExtensionPoint extensionPoint = AnnotationUtils.findAnnotation(method, ExtensionPoint.class);
                if (extensionPoint == null) {
                    continue;
                }
                ExtensionPointCode extensionPointCode = ExtensionPointCode.getInstance(extensionPoint.code());
                ExtensionPointObject extensionPointObject = new ExtensionPointObject();
                extensionPointObject.setExtensionPointCode(extensionPointCode);
                extensionPointObject.setRouterStrategy(extensionPoint.routerStrategy());
                extensionPointObject.setResultStrategy(extensionPoint.resultStrategy());
                extensionPointObject.setExceptionStrategy(extensionPoint.exceptionStrategy());
                CODE_EXTENSION_POINT_OBJECT_MAP.put(extensionPointObject.getExtensionPointCode(), extensionPointObject);
                Extension extension = AnnotationUtils.findAnnotation(method, Extension.class);
                if (extension == null) {
                    continue;
                }
                ExtensionObject extensionObject = new ExtensionObject();
                extensionObject.setMethod(method);
                extensionObject.setExtensionInstance(pointBean);
                extensionObject.setDimensions(extension.dimensions());
                extensionObject.setDimensionHandler(extension.dimensionHandler());
                List<ExtensionObject> extensionObjectList = codeExtensionObjectMap.computeIfAbsent(extensionPointCode, key -> Lists.newArrayList());
                extensionObjectList.add(extensionObject);
                INTERFACE_CLASS_EXTENSION_POINT_MAP.put(interfaceClassName + ":" + method.getName(), extensionPointCode);
            }
        });
        CODE_EXTENSION_POINT_OBJECT_MAP.forEach((key, value) -> {
            ExtensionPointObject extensionPointObject = CODE_EXTENSION_POINT_OBJECT_MAP.get(key);
            if (extensionPointObject != null) {
                extensionPointObject.setExtensionList(codeExtensionObjectMap.get(key));
            }
        });
    }

    public static ExtensionPointObject getExtensionPointObject(ExtensionPointCode extensionPointCode) {
        return CODE_EXTENSION_POINT_OBJECT_MAP.get(extensionPointCode);
    }

    @SuppressWarnings("unchecked")
    public static List<IExtensionPoint> getExtensionPoints(String code, Object[] args) {
        ExtensionPointCode extensionPointCode = ExtensionPointCode.getInstance(code);
        ExtensionPointObject extensionPointObject = ExtensionPointRegister.getExtensionPointObject(extensionPointCode);
        RouterStrategy<? extends IExtensionPoint> routerStrategy = StrategyRegister.getInstance().getRouterStrategy(extensionPointObject.getRouterStrategy());
        if (routerStrategy == null) {
            throw new RuntimeException("please set router strategy first");
        }
        RouterParam routerParam = new RouterParam(extensionPointCode, args, routerStrategy.customGetParam(args));
        return (List<IExtensionPoint>) routerStrategy.execute(routerParam);
    }

    public static Method getMethodByCode(ExtensionPointCode code, IExtensionPoint extensionPoint) {
        ExtensionPointObject extensionPointObject = CODE_EXTENSION_POINT_OBJECT_MAP.get(code);
        if (extensionPointObject == null || extensionPointObject.getExtensionList() == null) {
            return null;
        }
        return extensionPointObject.getExtensionList().stream().filter(instance -> extensionPoint == instance.getExtensionInstance()).map(ExtensionObject::getMethod).findFirst().orElse(null);
    }

    public static List<ExtensionObject> getExtensionPointImplList(ExtensionPointCode code) {
        ExtensionPointObject extensionPointObject = CODE_EXTENSION_POINT_OBJECT_MAP.get(code);
        if (extensionPointObject == null) {
            return Lists.newArrayList();
        }
        return extensionPointObject.getExtensionList();
    }

    public static String resolveInterfaceName(Class<?> defaultInterfaceClass) {
        // TODO Should be configurable extension point interface name, used to support multi-interface extension
        Class<?> interfaceClass = null;
        // get from annotation element type
        if (defaultInterfaceClass != null) {
            // Find all interfaces from the annotated class
            Class<?>[] allInterfaces = getAllInterfacesForClass(defaultInterfaceClass);
            if (allInterfaces.length > 0) {
                interfaceClass = allInterfaces[0];
            }
        }
        Assert.notNull(interfaceClass, "interface class must be present!");
        Assert.isTrue(interfaceClass.isInterface(), "The annotated type must be an interface!");
        return interfaceClass.getName();
    }

    public static ExtensionPointCode getExtensionPointCode(Class<?> interfaceClass, Method method) {
        return INTERFACE_CLASS_EXTENSION_POINT_MAP.get(interfaceClass.getName() + ":" + method.getName());
    }

}
