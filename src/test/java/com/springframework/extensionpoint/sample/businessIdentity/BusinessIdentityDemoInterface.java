package com.springframework.extensionpoint.sample.businessIdentity;

import com.springframework.extensionpoint.annotation.ExtensionPoint;
import com.springframework.extensionpoint.model.IExtensionPoint;
import com.springframework.extensionpoint.support.businessIdentity.MergeResultStrategy;

import java.util.List;

/**
 * 业务身份demo接口
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/04 22:04
 */
public interface BusinessIdentityDemoInterface extends IExtensionPoint {


    /**
     * 举例：获取订单属性
     * 路由策略：根据业务线+店铺id路由，符合条件的全匹配
     * 结果策略: 返回值叠加
     *
     * @return 所有订单属性
     */
    @ExtensionPoint(code = "getOrderAttributesOverlay", routerStrategy = RetailShopIdAllMatchRouterStrategy.class, resultStrategy = MergeResultStrategy.class)
    List<String> getOrderAttributesOverlay(String orderNo);

    /**
     * 举例：获取订单属性
     * 路由策略：根据业务线+店铺id路由，返回匹配度最高的
     * 结果策略: 返回值叠加
     *
     * @return 最优订单属性
     */
    @ExtensionPoint(code = "getOrderAttributesOptimal", routerStrategy = RetailShopIdMatchOptimalRouterStrategy.class, resultStrategy = MergeResultStrategy.class)
    List<String> getOrderAttributesOptimal(String orderNo);

}
