package com.springframework.extensionpoint.support.businessIdentity;

import com.google.common.collect.Lists;
import com.springframework.extensionpoint.model.ResultStrategy;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 合并所有结果处理策略
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/04 22:04
 */
public class MergeResultStrategy implements ResultStrategy<List<Object>> {
    @Override
    public List<Object> execute(List<List<Object>> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        return list.stream().flatMap(Collection::stream).collect(Collectors.toList());
    }
}
