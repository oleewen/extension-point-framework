package com.springframework.extensionpoint.sample.businessIdentity;

import com.springframework.extensionpoint.support.businessIdentity.AbstractBusinessIdentityRouterFeatureStrategy;
import com.springframework.extensionpoint.support.businessIdentity.BusinessIdentityRouterFeature;
import org.mockito.internal.util.collections.Sets;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 单纯的零售路由特征
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/05 12:17
 */
public class RetailRouterFeatureStrategy extends AbstractBusinessIdentityRouterFeatureStrategy {

    @Override
    public BusinessIdentityRouterFeature getFeature() {
        BusinessIdentityRouterFeature feature = new BusinessIdentityRouterFeature();
        Map<String, Set<String>> map = new HashMap<>();
        map.put("BUSINESS_LINE", Sets.newSet("RETAIL"));
        feature.setApplicableIdentity(map);
        return feature;
    }
}
