package com.springframework.extensionpoint.support.businessIdentity;

import lombok.Data;

import java.util.Map;

/**
 * 身份参数
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/04 22:04
 */
@Data
public class IdentityParam {

    /**
     * 实际的业务身份
     */
    private Map<String, String> actualIdentity;

}
