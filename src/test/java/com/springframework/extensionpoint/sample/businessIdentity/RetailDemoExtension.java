package com.springframework.extensionpoint.sample.businessIdentity;

import com.springframework.extensionpoint.annotation.Extension;
import org.assertj.core.util.Lists;

import java.util.List;

/**
 * 单纯的零售场景扩展点实现
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/04 22:04
 */
@Extension(dimensions ="BUSINESS_LINE=RETAIL;")
public class RetailDemoExtension implements BusinessIdentityDemoInterface{

    @Override
    public List<String> getOrderAttributesOverlay(String orderNo) {
        return Lists.newArrayList("属性A");
    }

    @Override
    public List<String> getOrderAttributesOptimal(String orderNo) {
        return Lists.newArrayList("属性A");
    }
}
