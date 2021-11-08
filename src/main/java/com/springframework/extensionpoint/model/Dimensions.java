package com.springframework.extensionpoint.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Dimensions {
    public static final Dimensions EMPTY = new Dimensions();
    List<Dimension> dimensions = new ArrayList<>();

    public void addDimension(String dimension, String value) {
        dimensions.add(new Dimension(dimension, value));
    }

    private class Dimension {
        private String dimension;
        private String value;

        public Dimension(String dimension, String value) {
            this.dimension = dimension;
            this.value = value;
        }
    }
}
