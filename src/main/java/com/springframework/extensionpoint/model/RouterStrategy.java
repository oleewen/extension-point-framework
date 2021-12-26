package com.springframework.extensionpoint.model;

import java.util.List;

/**
 * 路由策略
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/05 10:42
 */
public interface RouterStrategy<T extends IExtensionPoint> {
    /**
     * 路由策略执行
     *
     * @param param 路由参数
     * @return 路由结果
     */
    List<T> execute(RouterParam param);

    /**
     * 自定义获取路由参数
     *
     * @param interfaceArgs 接口参数
     * @return 自定义获取参数，会传入RouterParam#customParam
     */
    default Object customGetParam(Object[] interfaceArgs) {
        return interfaceArgs;
    }
}
