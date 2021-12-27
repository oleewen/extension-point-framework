package com.springframework.extensionpoint.support.businessIdentity;

import com.google.common.collect.Lists;
import com.springframework.extensionpoint.model.ExtensionObject;

import java.util.List;
import java.util.Map;

/**
 * 业务身份匹配取最优路由策略
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/05 14:43
 */
public abstract class AbstractIdentityMatchOptimalRouterStrategy extends AbstractBusinessIdentityRouterStrategy {

    @Override
    protected List<ExtensionObject> matchExtension(List<ExtensionObject> candidateExtIdentities, Map<String, String> businessIdentity, List<String> orderedDimensionList) {
        // 按要素匹配最优策略
        ExtensionObject extensionPoint = ExtensionIdentityMatchDecider.extOptimalMatch(candidateExtIdentities, businessIdentity, orderedDimensionList);
        if (extensionPoint == null) {
            return Lists.newArrayList();
        }
        return Lists.newArrayList(extensionPoint);
    }

}
