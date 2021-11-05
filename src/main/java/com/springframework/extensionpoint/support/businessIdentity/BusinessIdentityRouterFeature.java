package com.springframework.extensionpoint.support.businessIdentity;

import com.springframework.extensionpoint.model.RouterFeature;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;
import java.util.Set;

/**
 * 业务身份路由特征
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/05 12:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessIdentityRouterFeature extends RouterFeature {

    /**
     * 可适用的业务身份
     * key：业务身份要素key
     * value：可适用的业务身份要素值
     */
    private Map<String, Set<String>> applicableIdentity;
}
