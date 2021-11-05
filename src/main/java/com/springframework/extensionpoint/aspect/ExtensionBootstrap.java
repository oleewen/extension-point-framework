package com.springframework.extensionpoint.aspect;

import com.springframework.extensionpoint.model.ExceptionStrategy;
import com.springframework.extensionpoint.model.IExtensionPoint;
import com.springframework.extensionpoint.model.ResultStrategy;
import com.springframework.extensionpoint.scan.ExtensionPointRegister;
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
public class ExtensionBootstrap {

    /**
     * 执行扩展点
     */
    @SuppressWarnings("unchecked")
    public static <V, R> R execute(String code, V param, Object[] args) {
        try {
            List<IExtensionPoint> extensionPoints = ExtensionPointRegister.getExtensionPoints(code, param);
            if (CollectionUtils.isEmpty(extensionPoints)) {
                throw new RuntimeException("extension point not found, code:" + code);
            }
            // execute extensionPoints
            List<Object> executeResultList = extensionPoints.stream().map(extensionPoint -> {
                try {
                    Method method = ExtensionPointRegister.getMethodByCode(code, extensionPoint);
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
            ResultStrategy<R> resultStrategy = ExtensionPointRegister.getResultStrategy(code);
            if (resultStrategy == null) {
                throw new RuntimeException("result strategy can not null");
            }
            return resultStrategy.execute((List<R>) executeResultList);
        } catch (Exception ex) {
            // use ExceptionStrategy
            ExceptionStrategy<R, V> exceptionStrategy = ExtensionPointRegister.getExceptionStrategy(code);
            if (exceptionStrategy != null) {
                return exceptionStrategy.execute(param, ex);
            }
            throw ex;
        }
    }
}
