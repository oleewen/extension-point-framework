# extension-point-framework

## 扩展点定义

## 扩展点实现

## 扩展点策略

## 扩展点执行，两种方式实现扩展点执行（推荐第一种更优雅）

### 一、使用@ExtensionPointAutowired注解注入扩展点并调用

**1. 使用简介**

    1. 使用@ExtensionPointAutowired替换@Autowire注入扩展点
    2. 与正常接口调用使用无异

**2. Sample**

    com.springframework.extensionpoint.sample.businessIdentity.ExtensionPointAutowiredTest

### 二、使用ExtensionExecutor直接调用扩展点

**1. 使用简介**

    1. 直接调用静态类ExtensionExecutor.execute执行
    2. 需要显示传入扩展点接口code、接口调用参数数组

**2. Sample**

    com.springframework.extensionpoint.sample.businessIdentity.ExtensionPointExecutorTest


