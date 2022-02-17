package com.github.martvey.log.config;

import com.github.martvey.log.aspect.LogContextVariablesRegistry;
import com.github.martvey.log.aspect.LogInterceptorRegistry;

/**
 * @author vvnn1_
 * @version 1.0
 * @date 2020/9/10 10:49
 */
public interface MethodLogConfigurerAdapter {
    default void registryLogInterceptor(LogInterceptorRegistry logInterceptorRegistry){};
    default void registryContextVariables(LogContextVariablesRegistry logContextVariablesRegistry){};
}
