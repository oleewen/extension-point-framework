package com.springframework.extensionpoint.stragety;

import java.util.List;

public interface RouterStrategy {
    <T,V> List<T> execute(V param);
}
