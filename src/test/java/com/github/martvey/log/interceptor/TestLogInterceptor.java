package com.github.martvey.log.interceptor;

import java.util.Map;

public class TestLogInterceptor implements LogInterceptor{
    @Override
    public void doBeforeLog(String message, Map<String, String> properties) {
        System.out.println("================doBeforeLog==============");
        System.out.println("message==>" + message);
        System.out.println("properties==>");
        System.out.println("\t" + properties);
    }

    @Override
    public void doAfterLog(String message, Map<String, String> properties) {
        System.out.println();
        System.out.println("================doAfterLog==============");
        System.out.println("message==>" + message);
        System.out.println("properties==>");
        System.out.println("\t" + properties);
    }

    @Override
    public void doAfterReturningLog(String message, Map<String, String> properties) {
        System.out.println();
        System.out.println("================doAfterReturningLog==============");
        System.out.println("message==>" + message);
        System.out.println("properties==>");
        System.out.println("\t" + properties);
    }

    @Override
    public void doAfterThrowingLog(String message, Map<String, String> properties) {
        System.out.println();
        System.out.println("================doAfterThrowingLog==============");
        System.out.println("message==>" + message);
        System.out.println("properties==>");
        System.out.println("\t" + properties);
    }
}
