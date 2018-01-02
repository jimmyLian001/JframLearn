package com.test.facade;

import com.baofu.international.global.account.core.facade.user.UserFacade;
import com.test.frame.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * 用户服务测试类
 */
public class UserFacadeTest extends Base {

    @Autowired
    private UserFacade userFacade;

    @Test
    public void test() {
        userFacade.findByApplyNo(null, "1234");
    }

}
