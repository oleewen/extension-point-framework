package com.springframework.extensionpoint.model;

public interface ExceptionStrategy<T, V> {
    T execute(V param, Throwable throwable);
}
