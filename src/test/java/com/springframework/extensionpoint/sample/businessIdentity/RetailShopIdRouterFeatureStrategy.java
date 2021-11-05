package com.springframework.extensionpoint.sample.businessIdentity;

import com.springframework.extensionpoint.support.businessIdentity.AbstractBusinessIdentityRouterFeatureStrategy;
import com.springframework.extensionpoint.support.businessIdentity.BusinessIdentityRouterFeature;
import org.mockito.internal.util.collections.Sets;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 零售+店铺id组合的路由特征
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/05 12:17
 */
public class RetailShopIdRouterFeatureStrategy extends AbstractBusinessIdentityRouterFeatureStrategy {

    @Override
    public BusinessIdentityRouterFeature getFeature() {
        BusinessIdentityRouterFeature feature = new BusinessIdentityRouterFeature();
        Map<String, Set<String>> map = new HashMap<>();
        map.put("BUSINESS_LINE", Sets.newSet("RETAIL"));
        map.put("SHOP_ID", Sets.newSet("12345"));
        feature.setApplicableIdentity(map);
        return feature;
    }
}
