package com.springframework.extensionpoint.model;

import lombok.SneakyThrows;

/**
 * 什么都不做的异常处理器
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/11/04 22:04
 */
public class DoNothingExceptionStrategy implements ExceptionStrategy<Object> {

    @SneakyThrows
    @Override
    public Object execute(Object[] args, Throwable throwable) {
        throw throwable;
    }
}
