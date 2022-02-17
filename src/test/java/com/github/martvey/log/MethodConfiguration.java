package com.github.martvey.log;

import com.github.martvey.log.aspect.LogContextVariablesRegistry;
import com.github.martvey.log.aspect.LogInterceptorRegistry;
import com.github.martvey.log.config.MethodLogConfigurerAdapter;
import com.github.martvey.log.entity.RootObjectHolder;
import com.github.martvey.log.interceptor.TestLogInterceptor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = MethodConfiguration.class)
public class MethodConfiguration implements MethodLogConfigurerAdapter {
    @Override
    public void registryLogInterceptor(LogInterceptorRegistry logInterceptorRegistry) {
        TestLogInterceptor testLogInterceptor = new TestLogInterceptor();
        logInterceptorRegistry.addLogInterceptor(testLogInterceptor);
    }

    @Override
    public void registryContextVariables(LogContextVariablesRegistry logContextVariablesRegistry) {
        RootObjectHolder rootObjectHolder = new RootObjectHolder();
        logContextVariablesRegistry.setLogVariables(() -> Collections.singletonMap("ctx", rootObjectHolder.createRootObject()));
    }
}
