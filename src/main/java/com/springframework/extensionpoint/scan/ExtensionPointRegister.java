package com.springframework.extensionpoint.scan;

import com.springframework.extensionpoint.annotation.Extension;
import com.springframework.extensionpoint.annotation.ExtensionPoint;
import com.springframework.extensionpoint.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExtensionPointRegister implements ApplicationListener<ContextRefreshedEvent> {

    private static final Map<ExtensionPointCode, List<Method>> CODE_METHOD_MAP = new HashMap<>();
    private static final Map<Method, IExtensionPoint> METHOD_EXTENSION_POINT_MAP = new HashMap<>();
    private static final Map<ExtensionPointCode, RouterStrategy<?, ? extends IExtensionPoint>> EXTENSION_POINT_ROUTER_STRATEGY_MAP = new HashMap<>();
    private static final Map<ExtensionPointCode, ResultStrategy<?>> EXTENSION_POINT_RESULT_STRATEGY_MAP = new HashMap<>();
    private static final Map<ExtensionPointCode, ExceptionStrategy<?, ?>> EXTENSION_POINT_EXCEPTION_STRATEGY_MAP = new HashMap<>();
    private static final Map<ExtensionPointCode, List<Extension>> CODE_EXTENSION_MAP = new HashMap<>();
    private static final Map<Extension, RouterFeatureStrategy<?>> EXTENSION_ROUTER_FEATURE_STRATEGY_MAP = new HashMap<>();
    private static final Map<Extension, IExtensionPoint> EXTENSION_EXTENSION_POINT_MAP = new HashMap<>();
    private static final Map<Class<? extends RouterStrategy<?, ? extends IExtensionPoint>>, RouterStrategy<?, ? extends IExtensionPoint>> ROUTER_STRATEGY_CLASS_MAP = new HashMap<>();
    private static final Map<Class<? extends ResultStrategy<?>>, ResultStrategy<?>> RESULT_STRATEGY_CLASS_MAP = new HashMap<>();
    private static final Map<Class<? extends ExceptionStrategy<?, ?>>, ExceptionStrategy<?, ?>> EXCEPTION_STRATEGY_CLASS_MAP = new HashMap<>();
    private static final Map<Class<? extends RouterFeatureStrategy<?>>, RouterFeatureStrategy<?>> ROUTER_FEATURE_STRATEGY_CLASS_MAP = new HashMap<>();

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
            routerStrategyMap.values().forEach(strategyBean -> ROUTER_STRATEGY_CLASS_MAP.put((Class<? extends RouterStrategy<?, ? extends IExtensionPoint>>) strategyBean.getClass(), strategyBean));
        }
        Map<String, ResultStrategy> resultStrategyMap = applicationContext.getBeansOfType(ResultStrategy.class);
        if (!CollectionUtils.isEmpty(resultStrategyMap)) {
            resultStrategyMap.values().forEach(strategyBean -> RESULT_STRATEGY_CLASS_MAP.put((Class<? extends ResultStrategy<?>>) strategyBean.getClass(), strategyBean));
        }
        Map<String, ExceptionStrategy> exceptionStrategyMap = applicationContext.getBeansOfType(ExceptionStrategy.class);
        if (!CollectionUtils.isEmpty(exceptionStrategyMap)) {
            exceptionStrategyMap.values().forEach(strategyBean -> EXCEPTION_STRATEGY_CLASS_MAP.put((Class<? extends ExceptionStrategy<?, ?>>) strategyBean.getClass(), strategyBean));
        }
        Map<String, RouterFeatureStrategy> routerFeatureStrategyMap = applicationContext.getBeansOfType(RouterFeatureStrategy.class);
        if (!CollectionUtils.isEmpty(routerFeatureStrategyMap)) {
            routerFeatureStrategyMap.values().forEach(strategyBean -> ROUTER_FEATURE_STRATEGY_CLASS_MAP.put((Class<? extends RouterFeatureStrategy<?>>) strategyBean.getClass(), strategyBean));
        }
    }

    /**
     * 注册扩展点
     */
    public void registerExtensionPoint(ApplicationContext applicationContext) {
        Map<String, IExtensionPoint> pointBeanMap = applicationContext.getBeansOfType(IExtensionPoint.class);
        pointBeanMap.forEach((beanName, pointBean) -> {
            Method[] extMethods = pointBean.getClass().getDeclaredMethods();
            for (Method method : extMethods) {
                ExtensionPoint extensionPoint = AnnotationUtils.findAnnotation(method, ExtensionPoint.class);
                if (extensionPoint == null) {
                    continue;
                }
                METHOD_EXTENSION_POINT_MAP.put(method, pointBean);
                ExtensionPointCode extensionPointCode = ExtensionPointCode.getInstance(extensionPoint.code());
                CODE_METHOD_MAP.computeIfAbsent(extensionPointCode, key -> new ArrayList<>()).add(method);
                RouterStrategy<?, ? extends IExtensionPoint> routerStrategy = ROUTER_STRATEGY_CLASS_MAP.get(extensionPoint.routerStrategy());
                if (routerStrategy != null) {
                    EXTENSION_POINT_ROUTER_STRATEGY_MAP.put(extensionPointCode, routerStrategy);
                }
                ResultStrategy<?> resultStrategy = RESULT_STRATEGY_CLASS_MAP.get(extensionPoint.resultStrategy());
                if (resultStrategy != null) {
                    EXTENSION_POINT_RESULT_STRATEGY_MAP.put(extensionPointCode, resultStrategy);
                }
                ExceptionStrategy<?, ?> exceptionStrategy = EXCEPTION_STRATEGY_CLASS_MAP.get(extensionPoint.exceptionStrategy());
                if (exceptionStrategy != null) {
                    EXTENSION_POINT_EXCEPTION_STRATEGY_MAP.put(extensionPointCode, exceptionStrategy);
                }
                Extension extension = AnnotationUtils.findAnnotation(method, Extension.class);
                if (extension == null) {
                    continue;
                }
                CODE_EXTENSION_MAP.computeIfAbsent(extensionPointCode, key -> new ArrayList<>()).add(extension);
                EXTENSION_EXTENSION_POINT_MAP.put(extension, pointBean);
                RouterFeatureStrategy<?> routerFeatureStrategy = ROUTER_FEATURE_STRATEGY_CLASS_MAP.get(extension.routerFeatureStrategy());
                if (routerFeatureStrategy != null) {
                    EXTENSION_ROUTER_FEATURE_STRATEGY_MAP.put(extension, routerFeatureStrategy);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    public static <V, T extends IExtensionPoint> List<T> getExtensionPoints(String code, V v) {
        ExtensionPointCode extensionPointCode = ExtensionPointCode.getInstance(code);
        RouterStrategy<V, IExtensionPoint> routerStrategy = (RouterStrategy<V, IExtensionPoint>) EXTENSION_POINT_ROUTER_STRATEGY_MAP.get(extensionPointCode);
        if (routerStrategy == null) {
            throw new RuntimeException("please set router strategy first");
        }
        return (List<T>) routerStrategy.execute(code, v);
    }

    public static Method getMethodByCode(String code, IExtensionPoint extensionPoint) {
        List<Method> methods = CODE_METHOD_MAP.get(ExtensionPointCode.getInstance(code));
        if (CollectionUtils.isEmpty(methods)) {
            return null;
        }
        return methods.stream().filter(method -> extensionPoint == METHOD_EXTENSION_POINT_MAP.get(method)).findFirst().orElse(null);
    }

    @SuppressWarnings("unchecked")
    public static <T> ResultStrategy<T> getResultStrategy(String code) {
        return (ResultStrategy<T>) EXTENSION_POINT_RESULT_STRATEGY_MAP.get(ExtensionPointCode.getInstance(code));
    }

    @SuppressWarnings("unchecked")
    public static <T, V> ExceptionStrategy<T, V> getExceptionStrategy(String code) {
        return (ExceptionStrategy<T, V>) EXTENSION_POINT_EXCEPTION_STRATEGY_MAP.get(ExtensionPointCode.getInstance(code));
    }

    public static List<Extension> getExtensionPointImplList(String code) {
        return CODE_EXTENSION_MAP.get(ExtensionPointCode.getInstance(code));
    }

    @SuppressWarnings("unchecked")
    public static <T extends RouterFeature> RouterFeatureStrategy<T> getRouterFeatureStrategy(Extension extensionPointImpl) {
        return (RouterFeatureStrategy<T>) EXTENSION_ROUTER_FEATURE_STRATEGY_MAP.get(extensionPointImpl);
    }

    public static IExtensionPoint getExtensionPointInstance(Extension extensionPointImpl) {
        return EXTENSION_EXTENSION_POINT_MAP.get(extensionPointImpl);
    }
}
