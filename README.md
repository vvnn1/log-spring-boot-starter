log-spring-boot-starter
======================
## 一、简介
log-spring-boot-starter是基于SpringBoot的AOP日志框架，可以将日志参数化。

### 1、主要优势
* **[1]可以添加多种自定义的日志拦截器**
* **[2]基于注解的日志，无需手动拼装日志，使业务代码更加整洁***
* **[3]能通过表达式解析出方法参数中的属性，返回对象中的属性，调用对象的方法等**
* **[4]有前置，后置，返回，异常四种日志类型**

## 二、快速开始
详细参见test下的demo
### 1、引入依赖
```xml
<dependency>
    <groupId>com.github.martvey</groupId>
    <artifactId>log-spring-boot-starter</artifactId>
    <version>0.0.6</version>
</dependency>
```
### 2、日志切面实现
日志拦截器需实现LogInterceptor接口，参数message和properties由注解@MethodLog定义
```java
public class TestLogInterceptor implements LogInterceptor{
    @Override
    public void doBeforeLog(String message, Map<String, String> properties) {
        System.out.println("================doBeforeLog==============");
        System.out.println("message==>" + message);
        System.out.println("properties==>");
        System.out.println("\t" + properties);
    }
}
```

### 3、切面注册
继承MethodLogConfigurerAdapter，进行上下文及自定义的日志拦截器注册

```java
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = MethodConfiguration.class)
public class MethodConfiguration implements MethodLogConfigurerAdapter {
    @Override
    public void registryLogInterceptor(LogInterceptorRegistry logInterceptorRegistry) {
        TestLogInterceptor testLogInterceptor = new TestLogInterceptor();//自定义拦截器
        logInterceptorRegistry.addLogInterceptor(testLogInterceptor);//注册
    }

    @Override
    public void registryContextVariables(LogContextVariablesRegistry logContextVariablesRegistry) {
        RootObjectHolder rootObjectHolder = new RootObjectHolder();//上下文
        logContextVariablesRegistry.setLogVariables(() -> Collections.singletonMap("ctx", rootObjectHolder.createRootObject()));//上下文注册
    }
}
```

### 4、注解使用
```java
public interface TestController {
    @MethodLog(
            before = "#{#ctx.userName} 再 #{#ctx.currentTime} 查询了 #{#query.appNameVague}", //前置通知
            after = "#{#ctx.userName} 查寻完毕",// 后置通知
            afterReturning = "查询到的app是#{#result.appName}", // 返回通知 
            afterThrowing = "查询出错，原因是: #{#error.message}",// 异常通知
            properties = {
                    @LogProperties(key = "参数一", value = "11111"),
                    @LogProperties(eval = true, key = "参数二-app名称", value = "#{#query.appNameVague}"),// 属性获取
                    @LogProperties(eval = true, key = "参数三-调用方法", value = "#{#query.testMethod()}")// 函数调用
            })
    AppVO test(AppQuery query);
}
```
运行结果
```text
================doBeforeLog==============
message==>小王 在 2022-02-17T16:15:30.774 查询了 抖
properties==>
	{参数一=11111, 参数二-app名称=抖, 参数三-调用方法=我是方法testMethod}

================doAfterLog==============
message==>小王 查寻完毕
properties==>
	{参数一=11111, 参数二-app名称=抖, 参数三-调用方法=我是方法testMethod}

================doAfterReturningLog==============
message==>查询到的app是抖音
properties==>
	{参数一=11111, 参数二-app名称=抖, 参数三-调用方法=我是方法testMethod}
```