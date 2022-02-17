package com.github.martvey.log.anatition;

import java.lang.annotation.*;

/**
 * @author vvnn1_
 * @version 1.0
 * @date 2020/9/10 16:49
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogProperties {
    /**
     * 是否支持表达式解析
     * @return true是 false否
     */
    boolean eval() default false;

    /**
     * 自定义的KEY
     * @return key值
     */
    String key();

    /**
     * 自定义的value
     * @return value值
     */
    String value();
}
