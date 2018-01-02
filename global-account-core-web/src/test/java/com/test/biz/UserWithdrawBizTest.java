package com.test.biz;

import com.baofu.international.global.account.core.biz.UserWithdrawBiz;
import com.baofu.international.global.account.core.biz.external.BankWithdrawBizImpl;
import com.baofu.international.global.account.core.biz.models.UserWithdrawCashBo;
import com.baofu.international.global.account.core.biz.models.UserWithdrawDistributeBo;
import com.baofu.international.global.account.core.biz.models.UserWithdrawReqBo;
import com.test.frame.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * <p>
 * 1、方法描述
 * </p>
 * User: 香克斯  Date: 2017/11/5 ProjectName:account-core  Version: 1.0
 */
public class UserWithdrawBizTest extends Base {

    /**
     * 代付API统一服务
     */
    @Autowired
    private BankWithdrawBizImpl bankWithdrawBiz;

    /**
     * 功能：用户自主注册平台提现
     */
    @Autowired
    private UserWithdrawBiz userWithdrawBiz;

    /**
     * 代付API 请求申请
     */
    @Test
    public void userWithdrawApplyTest() throws IOException {
        UserWithdrawReqBo userWithdrawReqBo = new UserWithdrawReqBo();
        userWithdrawReqBo.setRequestNo(UUID.randomUUID().toString());
        userWithdrawReqBo.setOrderAmt(new BigDecimal("120"));
        userWithdrawReqBo.setRemarks("美国收款账户测试转账");
        userWithdrawReqBo.setBankName("中国工商银行");
        userWithdrawReqBo.setCardHolder("张三");
        userWithdrawReqBo.setBankCardNo("62226012345678901");
        userWithdrawReqBo.setCardId("362430198610165970");
        try {
            bankWithdrawBiz.userWithdrawApply(userWithdrawReqBo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 代付API 申请结果查询
     */
    @Test(enabled = false)
    public void userWithdrawQueryTest() {
        bankWithdrawBiz.userWithdrawQuery("fb2c4d93-ae2e-4802-b5c5-bf02db9aafb5");
    }

    /**
     * 用户提现
     */
    @Test
    public void userWithdrawCashTest() {
        UserWithdrawCashBo userWithdrawCashBo = new UserWithdrawCashBo();
        userWithdrawCashBo.setUserNo(1711131714000418753L);
        userWithdrawCashBo.setBankCardRecordNo(1711131755000000018L);
        userWithdrawCashBo.setWithdrawAmt(new BigDecimal("15"));
        userWithdrawCashBo.setWithdrawCcy("USD");
        userWithdrawCashBo.setCreateBy("15654564645");
        userWithdrawCashBo.setAccountNo(1L);
        userWithdrawCashBo.setSellerId("0001");
        userWithdrawBiz.userWithdrawCash(userWithdrawCashBo);
    }

    /**
     * 用户转账分发
     */
    @Test
    public void userWithdrawDistributeTest() {
        UserWithdrawDistributeBo userWithdrawDistributeBo = new UserWithdrawDistributeBo();
        userWithdrawDistributeBo.setMemberId(100018529L);
        //提现订单号
        userWithdrawDistributeBo.setBusinessNo(1712141612000232567L);
        userWithdrawBiz.userWithdrawDistribute(userWithdrawDistributeBo);
    }
}
