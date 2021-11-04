package com.springframework.extensionpoint.model;

import java.util.List;

public interface ExceptionStrategy {
    <T, V> T execute(V param);
}
