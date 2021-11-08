package com.springframework.extensionpoint.model;

import org.springframework.util.StringUtils;

public class DimensionHandler {
    public Dimensions getDimensions(String dimension) {
        String[] split = StringUtils.split(dimension, "&");
        if (split != null && split.length > 0) {
            Dimensions list = new Dimensions();
            for (String each : split) {
                String[] split1 = StringUtils.split(each, "@");
                if (split1 != null && split1.length == 2) {
                    list.addDimension(split1[0], split1[1]);
                }
            }
            return list;
        }
        return Dimensions.EMPTY;
    }
}
