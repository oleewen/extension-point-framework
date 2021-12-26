package com.springframework.extensionpoint.model;

public interface DimensionHandler {

    /**
     * 解析维度值
     *
     * @param dimensionValue 维度值
     * @return 维度对象
     */
    Dimensions parseDimensionValue(String dimensionValue);

}
