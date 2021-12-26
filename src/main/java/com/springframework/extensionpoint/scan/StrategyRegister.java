package com.springframework.extensionpoint.scan;

import com.springframework.extensionpoint.model.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 策略注册中心
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/12/22 23:41
 */
public class StrategyRegister {

    private volatile static StrategyRegister INSTANCE;

    private static final Map<Class<? extends RouterStrategy<? extends IExtensionPoint>>, RouterStrategy<? extends IExtensionPoint>> ROUTER_STRATEGY_CLASS_MAP = new HashMap<>();
    private static final Map<Class<? extends ResultStrategy<?>>, ResultStrategy<?>> RESULT_STRATEGY_CLASS_MAP = new HashMap<>();
    private static final Map<Class<? extends ExceptionStrategy<?>>, ExceptionStrategy<?>> EXCEPTION_STRATEGY_CLASS_MAP = new HashMap<>();
    private static final Map<Class<? extends DimensionHandler>, DimensionHandler> DIMENSION_HANDLER_CLASS_MAP = new HashMap<>();

    /**
     * 单例创建对象
     */
    public static StrategyRegister getInstance() {
        if (INSTANCE == null) {
            synchronized (StrategyRegister.class) {
                if (INSTANCE == null) {
                    INSTANCE = new StrategyRegister();
                }
            }
        }
        return INSTANCE;
    }

    @SuppressWarnings({"unchecked"})
    public void addRouterStrategy(RouterStrategy<? extends IExtensionPoint> routerStrategy) {
        ROUTER_STRATEGY_CLASS_MAP.put((Class<? extends RouterStrategy<? extends IExtensionPoint>>) routerStrategy.getClass(), routerStrategy);
    }

    @SuppressWarnings({"unchecked"})
    public void addResultStrategy(ResultStrategy<?> resultStrategy) {
        RESULT_STRATEGY_CLASS_MAP.put((Class<? extends ResultStrategy<?>>) resultStrategy.getClass(), resultStrategy);
    }

    @SuppressWarnings({"unchecked"})
    public void addExceptionStrategy(ExceptionStrategy<?> exceptionStrategy) {
        EXCEPTION_STRATEGY_CLASS_MAP.put((Class<? extends ExceptionStrategy<?>>) exceptionStrategy.getClass(), exceptionStrategy);
    }

    public void addDimensionHandler(DimensionHandler dimensionHandler) {
        DIMENSION_HANDLER_CLASS_MAP.put(dimensionHandler.getClass(), dimensionHandler);
    }

    public RouterStrategy<? extends IExtensionPoint> getRouterStrategy(Class<? extends RouterStrategy<? extends IExtensionPoint>> clazz) {
        return ROUTER_STRATEGY_CLASS_MAP.get(clazz);
    }

    public ResultStrategy<?> getResultStrategy(Class<? extends ResultStrategy<?>> clazz) {
        return RESULT_STRATEGY_CLASS_MAP.get(clazz);
    }

    public ExceptionStrategy<?> getExceptionStrategy(Class<? extends ExceptionStrategy<?>> clazz) {
        return EXCEPTION_STRATEGY_CLASS_MAP.get(clazz);
    }

    public DimensionHandler getDimensionHandler(Class<? extends DimensionHandler> clazz) {
        return DIMENSION_HANDLER_CLASS_MAP.get(clazz);
    }


}
