package com.springframework.extensionpoint.model;

import lombok.Data;

import java.util.List;

/**
 * 扩展点相关数据
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/12/22 23:18
 */
@Data
public class ExtensionPointObject {

    /**
     * 扩展点定义code
     */
    private ExtensionPointCode extensionPointCode;
    /**
     * 路由策略
     */
    private Class<? extends RouterStrategy<?>> routerStrategy;
    /**
     * 结果取值策略
     */
    private Class<? extends ResultStrategy<?>> resultStrategy;
    /**
     * 异常策略
     */
    private Class<? extends ExceptionStrategy<?, ?>> exceptionStrategy;
    /**
     * 扩展点实现
     */
    private List<ExtensionObject> extensionList;
}
