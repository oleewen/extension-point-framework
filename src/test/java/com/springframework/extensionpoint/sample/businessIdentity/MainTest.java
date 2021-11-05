package com.springframework.extensionpoint.sample.businessIdentity;

import com.springframework.extensionpoint.aspect.ExtensionBootstrap;
import com.springframework.extensionpoint.sample.JunitApplication;
import com.springframework.extensionpoint.support.businessIdentity.IdentityParam;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试入口
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/04 22:04
 */
@SpringBootTest(classes = JunitApplication.class)
public class MainTest {

    /**
     * 背景数据：
     * 扩展点BusinessIdentityDemoInterface：用于获取订单属性
     * 扩展点实现RetailDemoExtension：零售业务线下都会执行到，会输出属性A
     * 扩展点实现RetailShopIdDemoExtension：零售业务线+店铺id为12345的时候会执行到，会输出属性B
     * <p>
     * 测试场景：所有扩展点实现都能匹配到，所有匹配的属性都叠加输出
     * 传入业务线：零售
     * 传入店铺id：12345
     * 使用策略：所有匹配的都可叠加
     * 期望输出：属性A+属性B
     */
    @Test
    public void testExecuteOverlay() {
        IdentityParam identityParam = new IdentityParam();
        Map<String, String> actualIdentity = new HashMap<>();
        actualIdentity.put("BUSINESS_LINE", "RETAIL");
        actualIdentity.put("SHOP_ID", "12345");
        identityParam.setActualIdentity(actualIdentity);
        List<String> attributes = ExtensionBootstrap.execute("getOrderAttributesOverlay", identityParam, new Object[]{"E202111051111111"});
        System.out.println(attributes);
        assert attributes.size() == 2;
    }

    /**
     * 背景数据：
     * 扩展点BusinessIdentityDemoInterface：用于获取订单属性
     * 扩展点实现RetailDemoExtension：零售业务线下都会执行到，会输出属性A
     * 扩展点实现RetailShopIdDemoExtension：零售业务线+店铺id为12345的时候会执行到，会输出属性B
     * <p>
     * 测试场景：一个扩展点实现能匹配到，另一个扩展点匹配失败，只叠加输出匹配的属性
     * 传入业务线：零售
     * 传入店铺id：11111
     * 使用策略：所有匹配的都可叠加
     * 期望输出：属性A
     */
    @Test
    public void testExecuteOverlayShopIdNotMatch() {
        IdentityParam identityParam = new IdentityParam();
        Map<String, String> actualIdentity = new HashMap<>();
        actualIdentity.put("BUSINESS_LINE", "RETAIL");
        actualIdentity.put("SHOP_ID", "11111");
        identityParam.setActualIdentity(actualIdentity);
        List<String> attributes = ExtensionBootstrap.execute("getOrderAttributesOverlay", identityParam, new Object[]{"E202111051111111"});
        System.out.println(attributes);
        assert attributes.size() == 1;
    }

    /**
     * 背景数据：
     * 扩展点BusinessIdentityDemoInterface：用于获取订单属性
     * 扩展点实现RetailDemoExtension：零售业务线下都会执行到，会输出属性A
     * 扩展点实现RetailShopIdDemoExtension：零售业务线+店铺id为12345的时候会执行到，会输出属性B
     * <p>
     * 测试场景：所有扩展点实现都能匹配到，只输出匹配度最高的一个实现内的属性B
     * 传入业务线：零售
     * 传入店铺id：12345
     * 使用策略：输出匹配度最高的属性B
     * 期望输出：属性B
     */
    @Test
    public void testExecuteOptimal() {
        IdentityParam identityParam = new IdentityParam();
        Map<String, String> actualIdentity = new HashMap<>();
        actualIdentity.put("BUSINESS_LINE", "RETAIL");
        actualIdentity.put("SHOP_ID", "12345");
        identityParam.setActualIdentity(actualIdentity);
        List<String> attributes = ExtensionBootstrap.execute("getOrderAttributesOptimal", identityParam, new Object[]{"E202111051111111"});
        System.out.println(attributes);
        assert attributes.size() == 1;
    }

}
