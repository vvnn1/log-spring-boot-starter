package com.github.martvey.log.entity;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author vvnn1_
 * @version 1.0
 * @date 2021/2/8 9:29
 */
public class RootObjectHolder {
    public LogBaseExpressionRootObject createRootObject(){
        LogBaseExpressionRootObject logBaseExpressionRootObject = new LogBaseExpressionRootObject();
        logBaseExpressionRootObject.setUserId("123");
        logBaseExpressionRootObject.setUserName("小王");
        logBaseExpressionRootObject.setCurrentTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        return logBaseExpressionRootObject;
    }

}
