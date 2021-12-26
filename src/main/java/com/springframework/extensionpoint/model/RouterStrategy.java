package com.springframework.extensionpoint.model;

import java.util.List;

public interface RouterStrategy<T extends IExtensionPoint> {
    List<T> execute(RouterParam param);

    default Object customGetParam(Object[] interfaceArgs) {
        return interfaceArgs;
    }
}
