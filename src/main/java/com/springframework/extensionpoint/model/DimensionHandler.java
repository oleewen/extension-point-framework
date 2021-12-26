package com.springframework.extensionpoint.model;

/**
 * 维度值解析器
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/05 10:42
 */
public interface DimensionHandler {

    /**
     * 解析维度值
     *
     * @param dimensionValue 维度值
     * @return 维度对象
     */
    Dimensions parseDimensionValue(String dimensionValue);

}
