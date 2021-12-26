package com.springframework.extensionpoint.model;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * 扩展点实现对象
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/12/22 23:18
 */
@Data
public class ExtensionObject {

    /**
     * 路由维度值
     */
    private String dimensions;
    /**
     * 路由维度值处理器
     */
    private Class<? extends DimensionHandler> dimensionHandler;
    /**
     * 扩展点实例方法
     */
    private Method method;
    /**
     * 扩展点实例
     */
    private IExtensionPoint extensionInstance;

}
