package com.springframework.extensionpoint.model;

import java.util.List;

/**
 * 结果处理策略
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/05 10:42
 */
public interface ResultStrategy<T> {
    /**
     * 执行结果处理
     *
     * @param list 候选结果集
     * @return 最终结果
     */
    T execute(List<T> list);
}
