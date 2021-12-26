package com.springframework.extensionpoint.aspect;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * ExtensionPointAutowired注解的代理实现bean
 *
 * @author qiye -- fuqile@youzan.com
 * Created on 2021/12/25 20:37
 */
public class ExtensionPointAutowiredBean<T> implements FactoryBean<T>, BeanClassLoaderAware {

    /**
     * 扩展点接口类
     */
    private final Class<?> interfaceClass;
    /**
     * 类加载器
     */
    private ClassLoader classLoader;

    public ExtensionPointAutowiredBean(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getObject() throws Exception {
        return (T) Proxy.newProxyInstance(classLoader, new Class[]{interfaceClass}, new ExtensionPointInvocationHandler(interfaceClass));
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
