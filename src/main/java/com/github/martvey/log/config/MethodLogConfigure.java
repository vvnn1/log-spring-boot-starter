package com.github.martvey.log.config;

import com.github.martvey.log.aspect.LogAspect;
import com.github.martvey.log.aspect.LogContextVariablesRegistry;
import com.github.martvey.log.aspect.LogInterceptorRegistry;
import com.github.martvey.log.pointcut.MethodLogAnnotationPointcut;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * @author vvnn1_
 * @version 1.0
 * @date 2020/9/10 16:01
 */
@Configuration
public class MethodLogConfigure {
    @Bean
    @ConditionalOnProperty(prefix = "method-log.on-off",name = "on-off",havingValue = "true", matchIfMissing = true)
    public Advisor logAspect(MethodLogConfigurerAdapter configurerAdapterList){
        MethodLogAnnotationPointcut pointcut = new MethodLogAnnotationPointcut();
        LogAspect logAspect = new LogAspect(configurerAdapterList);
        return new DefaultPointcutAdvisor(pointcut, logAspect);
    }

    @Bean
    @ConditionalOnMissingBean(MethodLogConfigurerAdapter.class)
    public MethodLogConfigurerAdapter defaultMethodLogConfigurerAdapter(){
        return new MethodLogConfigurerAdapter() {
            @Override
            public void registryLogInterceptor(LogInterceptorRegistry logInterceptorRegistry) {

            }

            @Override
            public void registryContextVariables(LogContextVariablesRegistry logContextVariablesRegistry) {
                logContextVariablesRegistry.setLogVariables(Collections::emptyMap);
            }
        };
    }
}
