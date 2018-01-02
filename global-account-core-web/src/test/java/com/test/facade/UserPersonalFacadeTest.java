package com.test.facade;

import com.baofu.international.global.account.core.facade.UserPersonalFacade;
import com.baofu.international.global.account.core.facade.model.user.UserPersonalDto;
import com.system.commons.result.Result;
import com.test.frame.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.UUID;

/**
 * 用户服务测试类
 */
public class UserPersonalFacadeTest extends Base {

    @Autowired
    private UserPersonalFacade userPersonalFacade;

    @Test
    public void test() {
        Result<UserPersonalDto> result = userPersonalFacade.findByUserNo(5181171114000005498l, UUID.randomUUID().toString());
    }

}
