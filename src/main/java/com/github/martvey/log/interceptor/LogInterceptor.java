package com.github.martvey.log.interceptor;

import java.util.Map;

/**
 * @author vvnn1_
 * @version 1.0
 * @date 2020/9/10 10:40
 */
public interface LogInterceptor {
    /**
     * 前置通知
     * @param message 日志信息
     * @param properties 自定义参数
     */
    default void doBeforeLog(String message, Map<String,String> properties){}
    /**
     * 后置通知
     * @param message 日志信息
     * @param properties 自定义参数
     */
    default void doAfterLog(String message, Map<String,String> properties){}
    /**
     * 返回通知
     * @param message 日志信息
     * @param properties 自定义参数
     */
    default void doAfterReturningLog(String message, Map<String,String> properties){}
    /**
     * 异常通知
     * @param message 日志信息
     * @param properties 自定义参数
     */
    default void doAfterThrowingLog(String message, Map<String,String> properties){}
}
