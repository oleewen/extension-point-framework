package com.springframework.extensionpoint.model;

/**
 * 路由特征
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/05 10:32
 */
@FunctionalInterface
public interface RouterFeatureStrategy<T extends RouterFeature> {
    /**
     * 获取路由特征
     *
     * @return 自定义路由特征
     */
    T getFeature();

}
