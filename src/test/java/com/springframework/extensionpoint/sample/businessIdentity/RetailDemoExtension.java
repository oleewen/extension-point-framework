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
@Extension(dimensions ="bizId@RETAIL&merchant@1234")
public class RetailDemoExtension implements BusinessIdentityDemoInterface{

    @Override
    public List<String> getOrderAttributesOverlay(String orderNo) {
        return Lists.newArrayList("零售属性A（零售店铺下都有）");
    }

    @Override
    public List<String> getOrderAttributesOptimal(String orderNo) {
        return Lists.newArrayList("零售属性A（零售店铺下都有）");
    }
}
