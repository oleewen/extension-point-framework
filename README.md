# extension-point-framework

## 一、扩展点定义（@ExtensionPoint）

    定义在接口方法上，需要指定code，code应用内全局唯一。可自定义指定扩展点策略

## 二、扩展点实现（@Extension）

    定义在接口实现类或方法上，定义在方法上会覆盖定义在类上的，需要填写扩展点实现自身的维度特征

## 三、扩展点策略

### 1. 路由策略（RouterStrategy）：决策出需要执行哪些扩展点实现

    @ExtensionPoint的属性，默认已经实现全匹配和取最优两种策略，如果需要自定义，可实现RouterStrategy接口，在@ExtensionPoint上指定具体实现即可

### 2. 结果处理策略（ResultStrategy）：对扩展点执行结果进行合并处理

    @ExtensionPoint的属性，默认实现为List结果合并策略

### 3. 异常策略（ExceptionStrategy）：对异常结果进行处理策略

    @ExtensionPoint的属性，默认实现为不处理

### 4. 维度解析处理器（DimensionHandler）：对维度特征值进行解析

    @Extension的属性，用于解析@Extension的dimensions()值，默认实现为DefaultDimensionHandler

## 四、扩展点执行（推荐第一种更优雅）

### 1. 使用@ExtensionPointAutowired注解注入扩展点并调用

- 使用简介

      1. 使用@ExtensionPointAutowired替换@Autowire注入扩展点
      2. 与正常接口调用使用无异

- Sample

      com.springframework.extensionpoint.sample.businessIdentity.ExtensionPointAutowiredTest

### 2. 使用ExtensionExecutor直接调用扩展点

- 使用简介

      1. 直接调用静态类ExtensionExecutor.execute执行
      2. 需要显示传入扩展点接口code、接口调用参数数组

- Sample

      com.springframework.extensionpoint.sample.businessIdentity.ExtensionPointExecutorTest


