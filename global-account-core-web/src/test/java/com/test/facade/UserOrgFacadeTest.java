package com.test.facade;

import com.baofu.international.global.account.core.facade.UserOrgFacade;
import com.baofu.international.global.account.core.facade.model.user.OrgInfoRespDto;
import com.system.commons.result.Result;
import com.test.frame.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.UUID;

/**
 * 用户服务测试类
 */
public class UserOrgFacadeTest extends Base {

    @Autowired
    private UserOrgFacade userOrgFacade;

    @Test
    public void test() {
        Result<OrgInfoRespDto> result = userOrgFacade.findByUserNo(5181171114000005498l, UUID.randomUUID().toString());
        System.out.println(result);
    }

}
