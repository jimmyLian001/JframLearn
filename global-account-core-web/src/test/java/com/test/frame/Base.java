package com.test.frame;

import com.baofu.international.global.account.core.common.util.PropertyInitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

/**
 * User: yangjian  Date: 2017-05-18 ProjectName:  Version: 1.0
 */
@Slf4j
@ContextConfiguration(locations = {"classpath:com.spring.config/config-all.xml"})
public class Base extends AbstractTestNGSpringContextTests {

    @BeforeSuite
    public void setUp() {
        PropertyInitConfig.initProperties();
        System.out.println("-------------测试案例启动-------------");
    }

    @AfterSuite
    public void after() {
        System.out.println("-------------测试案例结束-------------");
    }
}
