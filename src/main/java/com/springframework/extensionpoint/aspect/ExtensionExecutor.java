package com.springframework.extensionpoint.aspect;

import com.springframework.extensionpoint.model.*;
import com.springframework.extensionpoint.scan.ExtensionPointRegister;
import com.springframework.extensionpoint.scan.StrategyRegister;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 扩展点执行入口
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/05 12:53
 */
@NoArgsConstructor
public class ExtensionExecutor {

    /**
     * 执行扩展点
     * @param code 扩展点接口code标识
     * @param args 扩展点接口参数数组
     */
    @SuppressWarnings("unchecked")
    public static <R> R execute(String code, Object[] args) {
        ExtensionPointCode extensionPointCode = ExtensionPointCode.getInstance(code);
        ExtensionPointObject extensionPointObject = ExtensionPointRegister.getExtensionPointObject(extensionPointCode);
        if (extensionPointObject == null) {
            throw new RuntimeException("extension point not found, code:" + extensionPointCode);
        }
        try {
            List<IExtensionPoint> extensionPoints = ExtensionPointRegister.getExtensionPoints(code, args);
            if (CollectionUtils.isEmpty(extensionPoints)) {
                throw new RuntimeException("extension point not found, code:" + code);
            }
            // execute extensionPoints
            List<Object> executeResultList = extensionPoints.stream().map(extensionPoint -> {
                try {
                    Method method = ExtensionPointRegister.getMethodByCode(ExtensionPointCode.getInstance(code), extensionPoint);
                    if (method == null) {
                        throw new RuntimeException("method not found, code:" + code);
                    }
                    return method.invoke(extensionPoint, args);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
            // use ResultStrategy
            ResultStrategy<R> resultStrategy = (ResultStrategy<R>) StrategyRegister.getInstance().getResultStrategy(extensionPointObject.getResultStrategy());
            if (resultStrategy == null) {
                throw new RuntimeException("result strategy can not null");
            }
            return resultStrategy.execute((List<R>) executeResultList);
        } catch (Exception ex) {
            // use ExceptionStrategy
            ExceptionStrategy<?> exceptionStrategy = StrategyRegister.getInstance().getExceptionStrategy(extensionPointObject.getExceptionStrategy());
            if (exceptionStrategy != null) {
                return (R) exceptionStrategy.execute(args, ex);
            }
            throw ex;
        }
    }
}
