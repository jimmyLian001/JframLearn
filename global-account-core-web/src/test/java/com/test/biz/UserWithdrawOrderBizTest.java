package com.test.biz;

import com.baofu.international.global.account.core.biz.UserWithdrawOrderBiz;
import com.baofu.international.global.account.core.biz.models.BatchWithdrawBo;
import com.test.frame.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.math.BigDecimal;

/**
 * <p>
 * 功能：用户自主注册平台提现订单操作
 * </p>
 * User: feng_jiang  Date: 2017/11/23 ProjectName:account-core  Version: 1.0
 */
public class UserWithdrawOrderBizTest extends Base {

    /**
     * 功能：用户自主注册平台提现订单操作
     */
    @Autowired
    private UserWithdrawOrderBiz userWithdrawOrderBiz;

    /**
     * 创建批量提现文件批次
     */
    @Test
    public void createFileBatchTest() {
        BatchWithdrawBo batchWithdrawBo = new BatchWithdrawBo();
        batchWithdrawBo.setUserNo(5181171127000013378L);
        batchWithdrawBo.setSellerId("A1JK4WMLRXJYAM");
        batchWithdrawBo.setWithdrawAmt(new BigDecimal("20"));
        batchWithdrawBo.setWithdrawCcy("USD");
        batchWithdrawBo.setCreateBy("15654564645");
        batchWithdrawBo.setAccountNo(1254812121212L);
        batchWithdrawBo.setPayeeIdType(1);
        batchWithdrawBo.setPayeeName("陈宏");
        batchWithdrawBo.setPayeeIdNo("be7d6cd1514862daddd65928d3025cf97c9181ccb902f10c");
        try {
            userWithdrawOrderBiz.createWithdrawFileBatch(batchWithdrawBo);
        } catch (Exception e) {
        }
    }
    /**
     * 根据提现批次号更新外部订单为可用
     */
    @Test
    public void updateExternalOrderTest() {
        userWithdrawOrderBiz.updateExternalOrder(1712132122000232101L);
    }
}
