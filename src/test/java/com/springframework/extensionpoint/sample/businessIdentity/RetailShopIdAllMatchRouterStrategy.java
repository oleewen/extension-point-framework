package com.springframework.extensionpoint.sample.businessIdentity;

import com.springframework.extensionpoint.support.businessIdentity.AbstractIdentityAllMatchRouterStrategy;
import org.assertj.core.util.Lists;

import java.util.List;

/**
 * 零售和店铺id组合的取最优路由策略
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/05 17:35
 */
public class RetailShopIdAllMatchRouterStrategy extends AbstractIdentityAllMatchRouterStrategy {

    @Override
    protected List<String> supportDimensions() {
        return Lists.newArrayList("BUSINESS_LINE", "SHOP_ID");
    }
}
