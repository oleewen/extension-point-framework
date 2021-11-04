package com.springframework.extensionpoint.stragety;

import java.util.List;

public interface ResultStrategy {
    <T> T execute(List<T> list);
}
