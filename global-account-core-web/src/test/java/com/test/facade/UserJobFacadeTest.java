package com.test.facade;

import com.baofu.international.global.account.core.facade.UserJobFacade;
import com.test.frame.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.UUID;

/**
 * <p>
 * 1、用户定时任务
 * </p>
 * User: feng_jiang  Date: 2017/11/7 ProjectName:account-core  Version: 1.0
 */
public class UserJobFacadeTest extends Base {

    @Autowired
    private
    UserJobFacade userJobFacade;

    /**
     * 用户前台提现归集
     */
    @Test
    public void execUserWithdrawMergeTest() {
        userJobFacade.execUserWithdrawMerge(1L, UUID.randomUUID().toString());
    }

    /**
     * 定时获取中行汇率
     */
    @Test
    public void loadCgwBocRateTest() {
        userJobFacade.loadCgwBocRate(UUID.randomUUID().toString());
    }
}