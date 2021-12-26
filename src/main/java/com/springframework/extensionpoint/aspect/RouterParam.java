package com.springframework.extensionpoint.aspect;

import com.springframework.extensionpoint.model.ExtensionPointCode;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 路由参数
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/12/26 02:54
 */
@AllArgsConstructor
@Data
public class RouterParam {

    /**
     * 扩展点code
     */
    private ExtensionPointCode extensionPointCode;
    /**
     * 接口入参
     */
    private Object[] params;
    /**
     * 自定义实现的参数
     */
    private Object customParam;

}
