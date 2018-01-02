package com.test.facade;

import com.baofu.international.global.account.core.facade.UserLoginFacade;
import com.baofu.international.global.account.core.facade.model.UserLoginReqDTO;
import com.test.frame.Base;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.UUID;

/**
 * 登录服务测试
 *
 * @author: 不良人 Date:2017/11/7 ProjectName: globalaccount-core Version: 1.0
 */
@Slf4j
public class UserLoginFacadeTest extends Base {

    /**
     * 登录接口
     */
    @Autowired
    private UserLoginFacade userLoginFacade;

    @Test
    public void userLoginFacadeTest() {
        UserLoginReqDTO dto = new UserLoginReqDTO();
        dto.setLoginNo("fengsuixing@baofoo.com");
        dto.setLoginPwd("123123");
        dto.setLoginIp("127.0.0.1");
        userLoginFacade.userLogin(dto, UUID.randomUUID().toString());
    }
}
