package com.springframework.extensionpoint.support.businessIdentity;

import com.google.common.collect.Lists;
import com.springframework.extensionpoint.annotation.Extension;

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
    protected List<Extension> matchExtension(List<Extension> candidateExtIdentities, Map<String, String> businessIdentity, List<String> orderedDimensionList) {
        // 按要素匹配最优策略
        Extension extensionPoint = ExtensionIdentityMatchDecider.extOptimalMatch(candidateExtIdentities, businessIdentity, orderedDimensionList);
        return Lists.newArrayList(extensionPoint);
    }

}
