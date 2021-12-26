package com.springframework.extensionpoint.model;

public interface ExceptionStrategy<T> {
    T execute(Object[] objects, Throwable throwable);
}
