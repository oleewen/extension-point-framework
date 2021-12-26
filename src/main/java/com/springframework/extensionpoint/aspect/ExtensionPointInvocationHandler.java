package com.springframework.extensionpoint.aspect;

import com.springframework.extensionpoint.model.*;
import com.springframework.extensionpoint.scan.ExtensionPointRegister;
import com.springframework.extensionpoint.scan.StrategyRegister;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 扩展调用动态代理类
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/12/26 02:43
 */
public class ExtensionPointInvocationHandler implements InvocationHandler {

    private final Class<?> interfaceClass;

    public ExtensionPointInvocationHandler(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length == 0) {
            if ("toString".equals(methodName)) {
                return this.toString();
            } else if ("hashCode".equals(methodName)) {
                return this.hashCode();
            }
        } else if (parameterTypes.length == 1 && "equals".equals(methodName)) {
            return this.equals(args[0]);
        }
        try {
            ExtensionPointCode extensionPointCode = ExtensionPointRegister.getExtensionPointCode(interfaceClass, method);
            ExtensionPointObject extensionPointObject = ExtensionPointRegister.getExtensionPointObject(extensionPointCode);
            RouterStrategy<? extends IExtensionPoint> routerStrategy = StrategyRegister.getInstance().getRouterStrategy(extensionPointObject.getRouterStrategy());
            if (routerStrategy == null) {
                throw new RuntimeException("please set router strategy first");
            }
            RouterParam routerParam = new RouterParam(extensionPointCode, args, routerStrategy.customGetParam(args));
            List<IExtensionPoint> extensionPoints = (List<IExtensionPoint>) routerStrategy.execute(routerParam);
            if (CollectionUtils.isEmpty(extensionPoints)) {
                throw new RuntimeException("extension point not found, code:" + extensionPointCode.getCode());
            }
            // execute extensionPoints
            List<Object> executeResultList = extensionPoints.stream().map(extensionPoint -> {
                try {
                    Method actualMethod = ExtensionPointRegister.getMethodByCode(extensionPointCode, extensionPoint);
                    if (actualMethod == null) {
                        throw new RuntimeException("method not found, code:" + extensionPointCode.getCode());
                    }
                    return actualMethod.invoke(extensionPoint, args);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
            // use ResultStrategy
            ResultStrategy<Object> resultStrategy = (ResultStrategy<Object>) StrategyRegister.getInstance().getResultStrategy(extensionPointObject.getResultStrategy());
            if (resultStrategy == null) {
                throw new RuntimeException("result strategy can not null");
            }
            return resultStrategy.execute(executeResultList);
        } catch (Exception ex) {
            // use ExceptionStrategy
//            ExceptionStrategy<R, V> exceptionStrategy = ExtensionPointRegister.getExceptionStrategy(code);
//            if (exceptionStrategy != null) {
//                return exceptionStrategy.execute(param, ex);
//            }
            throw ex;
        }
    }


}
