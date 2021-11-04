package com.springframework.extensionpoint.model;

import java.util.List;

public interface RouterStrategy {
    <T,V> List<T> execute(V param);
}
