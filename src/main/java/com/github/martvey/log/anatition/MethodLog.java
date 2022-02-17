package com.github.martvey.log.anatition;


import java.lang.annotation.*;

/**
 * @author vvnn1_
 * @version 1.0
 * @date 2020/9/10 10:30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLog {
    /**
     * 日志控制
     * @return true做，false不做，支持表达式解析
     */
    String condition() default "true";

    /**
     * 前置日志
     * @return 日志信息
     * @see com.github.martvey.log.interceptor.LogInterceptor
     */
    String before() default "";

    /**
     * 后置日志
     * @return 日志信息
     */
    String after() default "";

    /**
     * 返回日志
     * @return 日志信息
     * @see com.github.martvey.log.interceptor.LogInterceptor
     */
    String afterReturning() default "";

    /**
     * 异常日志
     * @return 日志信息
     * @see com.github.martvey.log.interceptor.LogInterceptor
     */
    String afterThrowing() default "";

    /**
     * 自定义属性
     * @return 自定义属性注解
     * @see com.github.martvey.log.interceptor.LogInterceptor
     */
    LogProperties[] properties() default {};
}
