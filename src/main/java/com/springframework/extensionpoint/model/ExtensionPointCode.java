package com.springframework.extensionpoint.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtensionPointCode {
    /**
     * 扩展点唯一标识
     */
    String code;

    public static ExtensionPointCode getInstance(String code) {
        return new ExtensionPointCode(code);
    }
}
