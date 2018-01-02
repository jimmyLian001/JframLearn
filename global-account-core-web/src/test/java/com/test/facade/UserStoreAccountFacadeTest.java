package com.test.facade;

import com.baofu.international.global.account.core.facade.UserStoreAccountFacade;
import com.test.frame.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.UUID;

/**
 * <p>
 * 1、方法描述
 * </p>
 * User: 香克斯  Date: 2017/11/7 ProjectName:account-core  Version: 1.0
 */
public class UserStoreAccountFacadeTest extends Base {

    @Autowired
    private UserStoreAccountFacade userStoreAccountFacade;

    /**
     * 根据用户编号查询店铺收款账户信息 测试
     */
    @Test
    public void queryUserStoreAccountTest() {
        userStoreAccountFacade.queryUserStoreAccount(100018529L, UUID.randomUUID().toString());
    }

    /**
     * 根据用户编号币种查询店铺收款账户信息 测试
     */
    @Test
    public void queryUserStoreAccountByCcyTest() {
        userStoreAccountFacade.queryUserStoreAccount(100018529L, "JPY", UUID.randomUUID().toString());
    }

    @Test
    public void queryStoreDetailAccountTest() {
        System.out.println(userStoreAccountFacade.queryStoreAccountInfo(5181171128000027808L, 1711290036000184460L, UUID.randomUUID().toString()));
    }
}
