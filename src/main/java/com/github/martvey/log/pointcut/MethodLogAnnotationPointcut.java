package com.github.martvey.log.pointcut;

import com.github.martvey.log.anatition.MethodLog;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * @author vvnn1_
 * @version 1.0
 * @date 2021/6/26 14:43
 */
public class MethodLogAnnotationPointcut extends StaticMethodMatcherPointcut {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return method.isAnnotationPresent(MethodLog.class)
                || ClassUtils.getInterfaceMethodIfPossible(method).isAnnotationPresent(MethodLog.class);
    }
}
