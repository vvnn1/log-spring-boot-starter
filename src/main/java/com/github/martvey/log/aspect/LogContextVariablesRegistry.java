package com.github.martvey.log.aspect;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @author vvnn1_
 * @version 1.0
 * @date 2020/9/10 16:18
 */
public interface LogContextVariablesRegistry {
    void setLogVariables(Supplier<Map<String,Object>> contextVariable);
}
