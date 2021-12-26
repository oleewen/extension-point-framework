package com.springframework.extensionpoint.model;

import org.springframework.util.StringUtils;

/**
 * 默认解析实现
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/12/23 00:36
 */
public class DefaultDimensionHandler implements DimensionHandler {

    @Override
    public Dimensions parseDimensionValue(String dimensionValue) {
        if (!StringUtils.hasLength(dimensionValue)) {
            return Dimensions.EMPTY;
        }
        String[] split = dimensionValue.split(";");
        if (split.length > 0) {
            Dimensions list = new Dimensions();
            for (String each : split) {
                String[] keyValue = StringUtils.split(each, "=");
                if (keyValue != null && keyValue.length == 2) {
                    list.addDimension(keyValue[0], keyValue[1]);
                }
            }
            return list;
        }
        return Dimensions.EMPTY;
    }
}
