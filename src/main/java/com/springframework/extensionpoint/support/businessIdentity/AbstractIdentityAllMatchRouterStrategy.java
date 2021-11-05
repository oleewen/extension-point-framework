package com.springframework.extensionpoint.support.businessIdentity;

import com.google.common.collect.Lists;
import com.springframework.extensionpoint.annotation.Extension;
import com.springframework.extensionpoint.model.RouterFeature;
import com.springframework.extensionpoint.model.RouterFeatureStrategy;
import com.springframework.extensionpoint.scan.ExtensionPointRegister;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 业务身份全匹配路由策略
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/05 14:43
 */
public abstract class AbstractIdentityAllMatchRouterStrategy extends AbstractBusinessIdentityRouterStrategy {

    @Override
    protected List<Extension> matchExtension(List<Extension> candidateExtIdentities, Map<String, String> businessIdentity, List<String> orderedDimensionList) {
        // 当业务身份为空的时候，认为是全不匹配
        if (CollectionUtils.isEmpty(businessIdentity)) {
            return Lists.newArrayList();
        }
        // 所有要素匹配的都会返回
        List<Extension> extensionList = Lists.newArrayList();
        for (Extension extension : candidateExtIdentities) {
            RouterFeatureStrategy<RouterFeature> routerFeatureStrategy = ExtensionPointRegister.getRouterFeatureStrategy(extension);
            if (routerFeatureStrategy == null) {
                continue;
            }
            //TODO 强转校验
            BusinessIdentityRouterFeature feature = (BusinessIdentityRouterFeature) routerFeatureStrategy.getFeature();
            Map<String, Set<String>> applicableIdentityMap = feature.getApplicableIdentity();
            // 当为空时默认认为是全匹配
            if (CollectionUtils.isEmpty(applicableIdentityMap)) {
                extensionList.add(extension);
                continue;
            }
            boolean matched = false;
            for (Map.Entry<String,String> entry:businessIdentity.entrySet()){
                if (!orderedDimensionList.contains(entry.getKey())) {
                    continue;
                }
                Set<String> applicableSet = applicableIdentityMap.get(entry.getKey());
                if (CollectionUtils.isEmpty(applicableSet) || applicableSet.contains(entry.getValue())) {
                    matched = true;
                }
                // 全匹配才算，只要有一个不满足就退出
                break;
            }
            businessIdentity.forEach((key, value) -> {

            });
            if (matched) {
                extensionList.add(extension);
            }
        }
        return extensionList;
    }

}
