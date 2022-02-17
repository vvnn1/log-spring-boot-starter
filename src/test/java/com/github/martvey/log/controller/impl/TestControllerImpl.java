package com.github.martvey.log.controller.impl;

import com.github.martvey.log.controller.TestController;
import com.github.martvey.log.entity.AppQuery;
import com.github.martvey.log.entity.AppVO;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class TestControllerImpl implements TestController {
    @Override
    public AppVO test(AppQuery query) {
        AppVO appVO = new AppVO();
        appVO.setAppName("抖音");
        appVO.setUserName("小王");

        if (new Random().nextInt() % 10 > 7){
            throw new RuntimeException("抛异常了");
        }
        return appVO;
    }
}
