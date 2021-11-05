package com.springframework.extensionpoint.support.businessIdentity;

import com.springframework.extensionpoint.annotation.Extension;
import com.springframework.extensionpoint.model.RouterFeature;
import com.springframework.extensionpoint.model.RouterFeatureStrategy;
import com.springframework.extensionpoint.scan.ExtensionPointRegister;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhanganhua
 * @date 2021-10-13 11:05
 *
 * 扩展点匹配决策器
 */
public class ExtensionIdentityMatchDecider {

    /**
     * 包含
     */
    private final static String CONTAINS = "9";
    /**
     * 不包含
     */
    private final static String NOT_CONTAINS = "1";
    /**
     * 默认
     */
    private final static String DEFAULT = "3";

    /**
     * 扩展点身份最优匹配
     * @param candidateExtIdentities  候选拓展身份集合
     * @param businessIdentity  当前流程业务身份
     * @param orderedDimensionList  业务身份模版评分顺序
     * @return 最优拓展点身份
     *
     * 无匹配 return null
     */
    public static Extension extOptimalMatch(List<Extension> candidateExtIdentities, Map<String, String> businessIdentity, List<String> orderedDimensionList) {
        if (CollectionUtils.isEmpty(orderedDimensionList)) {
            return null;
        }
        Extension optimalExtensionPointImpl = null;
        long highestScore = 0L;
        for (Extension extension : candidateExtIdentities) {
            RouterFeatureStrategy<RouterFeature> routerFeatureStrategy = ExtensionPointRegister.getRouterFeatureStrategy(extension);
            if(routerFeatureStrategy == null){
                continue;
            }
            //TODO 强转校验
            BusinessIdentityRouterFeature feature = (BusinessIdentityRouterFeature) routerFeatureStrategy.getFeature();
            Map<String, Set<String>> extIdentityDimensionValueMap = feature.getApplicableIdentity();

            String score = getScore(businessIdentity, extIdentityDimensionValueMap, orderedDimensionList);

            if (!score.contains(NOT_CONTAINS) && highestScore < Long.parseLong(score)) {
                optimalExtensionPointImpl = extension;
                highestScore = Long.parseLong(score);
            }
        }

        return optimalExtensionPointImpl;
    }

    /**
     * 获取分数
     */
    private static String getScore(Map<String, String> businessIdentity, Map<String, Set<String>> extIdentityDimensionValueMap, List<String> orderedDimensionList) {
        StringBuilder score = new StringBuilder();
        for (String dimensionElement : orderedDimensionList) {
            // 如果没有设置，则打默认分
            if (extIdentityDimensionValueMap == null) {
                score.append(DEFAULT);
                continue;
            }

            // 拿到扩展点实例 单个维度的值
            Set<String> extIdentityElementInstance = extIdentityDimensionValueMap.get(dimensionElement);

            // 如果维度没设置，则打默认分
            if (extIdentityElementInstance == null || extIdentityElementInstance.isEmpty()) {
                score.append(DEFAULT);
                continue;
            }

            // 拿到业务身份对应的值
            String businessIdentityElementInstance = businessIdentity.get(dimensionElement);

            // 进行比较
            if (extIdentityElementInstance.contains(businessIdentityElementInstance)) {
                score.append(CONTAINS);
            } else {
                score.append(NOT_CONTAINS);
            }
        }

        return score.toString();
    }

}
