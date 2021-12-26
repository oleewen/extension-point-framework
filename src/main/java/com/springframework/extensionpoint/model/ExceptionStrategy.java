package com.springframework.extensionpoint.model;

/**
 * 异常策略
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/05 10:42
 */
public interface ExceptionStrategy<T> {
    /**
     * 异常策略执行
     *
     * @param args      接口参数
     * @param throwable 原异常
     * @return 异常处理结果
     */
    T execute(Object[] args, Throwable throwable);
}
