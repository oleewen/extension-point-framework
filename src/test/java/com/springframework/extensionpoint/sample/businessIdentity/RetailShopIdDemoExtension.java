package com.springframework.extensionpoint.sample.businessIdentity;

import com.springframework.extensionpoint.annotation.Extension;
import org.assertj.core.util.Lists;

import java.util.List;

/**
 * 零售场景+指定店铺id场景扩展点实现
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/04 22:04
 */
public class RetailShopIdDemoExtension implements BusinessIdentityDemoInterface{

    @Override
    @Extension(routerFeatureStrategy = RetailShopIdRouterFeatureStrategy.class)
    public List<String> getOrderAttributesOverlay(String orderNo) {
        return Lists.newArrayList("零售属性B（只有在店铺id为12345时才有）");
    }

    @Override
    @Extension(routerFeatureStrategy = RetailShopIdRouterFeatureStrategy.class)
    public List<String> getOrderAttributesOptimal(String orderNo) {
        return Lists.newArrayList("零售属性B（只有在店铺id为12345时才有）");
    }
}
