package com.springframework.extensionpoint.model;

import java.util.List;

public interface RouterStrategy<V, T extends IExtensionPoint> {
    List<T> execute(String code, V param);
}
