package com.github.martvey.log;

import com.github.martvey.log.controller.TestController;
import com.github.martvey.log.entity.AppQuery;
import com.github.martvey.log.entity.AppVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = MethodConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MethodLogTest {
    @Autowired
    private TestController testController;

    @Test
    public void methodLogTest(){
        AppQuery appQuery = new AppQuery();
        appQuery.setAppNameVague("抖");
        AppVO test = testController.test(appQuery);
        System.out.println("结束了，查询内容是：" + test);
    }
}
