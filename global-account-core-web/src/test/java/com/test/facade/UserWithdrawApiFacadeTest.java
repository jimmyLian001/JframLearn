package com.test.facade;

import com.baofu.international.global.account.core.facade.UserWithdrawApiFacade;
import com.baofu.international.global.account.core.facade.model.UserDistributeApiDto;
import com.baofu.international.global.account.core.facade.model.UserSettleApplyDto;
import com.system.commons.result.Result;
import com.test.frame.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.UUID;

/**
 * <p>
 * 1、用户结汇申请处理结果
 * </p>
 * User: feng_jiang  Date: 2017/11/7 ProjectName:account-core  Version: 1.0
 */
public class UserWithdrawApiFacadeTest extends Base {

    @Autowired
    private UserWithdrawApiFacade userWithdrawApiFacade;

    /**
     * 用户结汇申请处理结果
     */
    @Test
    public void dealUserSettleApplyTest() {
        UserSettleApplyDto userSettleApplyDto = new UserSettleApplyDto();
        userSettleApplyDto.setMemberId(100018529L);
        userSettleApplyDto.setRemitReqNo("1711292024000185310");
        userSettleApplyDto.setStatus("6");
        Result<Boolean> list = userWithdrawApiFacade.dealUserSettleApply(userSettleApplyDto,
                UUID.randomUUID().toString());
        if (list.isSuccess()) {
            System.out.println("====================" + list.getResult());
        }
    }

    @Test
    public void dealWithdrawDistributeApplyTest() {
        UserDistributeApiDto userDistributeApiDto = new UserDistributeApiDto();
        userDistributeApiDto.setTransNo("231231231");
        userDistributeApiDto.setState(1231L);
        userDistributeApiDto.setTransRemark("测试");
        Result<Boolean> list = userWithdrawApiFacade.dealWithdrawDistributeApply(userDistributeApiDto,
                UUID.randomUUID().toString());
        if (list.isSuccess()) {
            System.out.println("====================" + list.getResult());
        }
    }
}