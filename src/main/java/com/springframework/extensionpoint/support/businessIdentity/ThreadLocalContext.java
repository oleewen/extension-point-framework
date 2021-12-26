package com.springframework.extensionpoint.support.businessIdentity;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 规则引擎请求缓存上下文（ThreadLocal）
 *
 * @author qiye -- fuqile@youzan.com Created on 2020/12/08 10:27
 */
public class ThreadLocalContext implements AutoCloseable {

    /**
     * 使用阿里巴巴增强线程池
     * 解决了子线程ThreadLocal的传递问题
     * 解决了线程池中线程复用但ThreadLocal也被复用的问题
     */
    private final static ThreadLocal<ThreadLocalContext> CONTEXT = ThreadLocal.withInitial(ThreadLocalContext::new);

    /**
     * 保存所有value
     */
    private final Map<String, Object> values = Maps.newHashMap();

    /**
     * 获取context
     */
    public static ThreadLocalContext getContext() {
        return CONTEXT.get();
    }

    public void putParam(String paramKey, Object value) {
        values.put(paramKey, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getParam(String paramKey) {
        return (T) values.get(paramKey);
    }

    @Override
    public void close() {
        exit();
    }

    /**
     * 删除本地线程变量
     */
    public static void exit() {
        CONTEXT.remove();
    }

}
