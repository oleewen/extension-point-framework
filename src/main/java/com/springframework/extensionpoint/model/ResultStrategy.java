package com.springframework.extensionpoint.model;

import java.util.List;

public interface ResultStrategy<T>{
    T execute(List<T> list);
}
