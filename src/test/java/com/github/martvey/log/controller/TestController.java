package com.github.martvey.log.controller;

import com.github.martvey.log.anatition.LogProperties;
import com.github.martvey.log.anatition.MethodLog;
import com.github.martvey.log.entity.AppQuery;
import com.github.martvey.log.entity.AppVO;

public interface TestController {
    @MethodLog(
            before = "#{#ctx.userName} 在 #{#ctx.currentTime} 查询了 #{#query.appNameVague}",
            after = "#{#ctx.userName} 查寻完毕",
            afterReturning = "查询到的app是#{#result.appName}",
            afterThrowing = "查询出错，原因是: #{#error.message}",
            properties = {
                    @LogProperties(key = "参数一", value = "11111"),
                    @LogProperties(eval = true, key = "参数二-app名称", value = "#{#query.appNameVague}"),
                    @LogProperties(eval = true, key = "参数三-调用方法", value = "#{#query.testMethod()}")
            })
    AppVO test(AppQuery query);
}
