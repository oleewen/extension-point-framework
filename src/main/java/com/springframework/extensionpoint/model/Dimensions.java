package com.springframework.extensionpoint.model;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
public class Dimensions {
    public static final Dimensions EMPTY = new Dimensions();
    List<Dimension> dimensions = new ArrayList<>();

    public void addDimension(String dimension, String value) {
        dimensions.add(new Dimension(dimension, value));
    }

    @Data
    @AllArgsConstructor
    private static class Dimension {
        private String dimension;
        private String value;
    }

    public Map<String, Set<String>> toMap() {
        if (CollectionUtils.isEmpty(dimensions)) {
            return Maps.newHashMap();
        }
        Map<String, Set<String>> result = Maps.newHashMap();
        dimensions.stream().forEach(dimension -> {
            Set<String> values = result.computeIfAbsent(dimension.getDimension(), key -> Sets.newHashSet());
            values.add(dimension.getValue());
        });
        return result;
    }
}
