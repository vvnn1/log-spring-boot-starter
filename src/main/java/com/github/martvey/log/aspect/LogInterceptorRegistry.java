package com.github.martvey.log.aspect;

import com.github.martvey.log.interceptor.LogInterceptor;

import java.util.List;

/**
 * @author vvnn1_
 * @version 1.0
 * @date 2020/9/10 10:37
 */
public interface LogInterceptorRegistry {
    void addLogInterceptor(LogInterceptor logInterceptor);
    void addLogInterceptor(List<LogInterceptor> logInterceptorList);
}
