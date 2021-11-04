package com.springframework.extensionpoint.scan;

import com.springframework.extensionpoint.model.ExtensionPoint;
import com.springframework.extensionpoint.model.ExtensionPointCode;

import java.util.List;
import java.util.Map;

public class ExtensionPointScannerRegister {
    private static Map<ExtensionPointCode, ExtensionPoint> map;

    public void register() {
    }

    public static <T extends ExtensionPoint> List<T> getExtensionPoints(String code) {
        // use RouterStrategy
        return null;
    }
}
