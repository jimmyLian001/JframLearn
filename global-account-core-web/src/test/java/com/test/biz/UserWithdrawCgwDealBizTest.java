package com.test.biz;

import com.baofoo.cbcgw.facade.dto.gw.response.CgwReceiptAndDisbursementRespDto;
import com.baofoo.cbcgw.facade.dto.gw.response.CgwTransferRespDto;
import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwTransferResultDto;
import com.baofu.international.global.account.core.biz.UserAccountPaymentDetailBiz;
import com.baofu.international.global.account.core.biz.UserWithdrawCgwDealBiz;
import com.system.commons.utils.JsonUtil;
import com.test.frame.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * <p>
 * 1、方法描述:商户提现测试
 * </p>
 * User: feng_jiang  Date: 2017/11/10 ProjectName:account-core  Version: 1.0
 */
public class UserWithdrawCgwDealBizTest extends Base {

    /**
     * 用户转账&宝付转账至备付金
     */
    @Autowired
    private UserWithdrawCgwDealBiz userWithdrawCgwDealBiz;

    /**
     * 收款账户收支明细 Biz
     */
    @Autowired
    private UserAccountPaymentDetailBiz userAccountPaymentDetailBiz;

    /**
     * 用户转账渠道第一次异步通知处理
     */
    @Test
    public void userWithdrawOneProcessTest() {
        String msgText = " {\"batchId\":2017092713521856,\"bfBatchId\":1711121145000417852,\"code\":2,\"message\":\"" +
                "Completed: Transfer of $10.02 to account:AC-6FNVXPCTAHH\",\"retMsg\":\"成功\",\"traceLogId\":\"29f70d09-e2a4-47ec-8d8a-302d3b819757\",\"transferStatus\":6,\"transferType\":0}";
        CgwTransferRespDto transferRespDto = JsonUtil.toObject(msgText, CgwTransferRespDto.class);
        userWithdrawCgwDealBiz.userWithdrawOneProcess(transferRespDto);
    }

    /**
     * 用户转账渠道第二次异步通知处理
     */
    @Test
    public void userWithdrawTwoProcessTest() {
        String msgText = "{\"batchId\":2017092810562001,\"bfBatchId\":1711121126000417823,\"code\":1,\"message\":\"Pending: Transfer of $21.04 to paymentmethod:PA-V9XEP6ZAFG7\",\"retMsg\":\"处理中\",\"transferStatus\":6,\"transferType\":1}";
        CgwTransferResultDto cgwTransferResultDto = JsonUtil.toObject(msgText, CgwTransferResultDto.class);
        userWithdrawCgwDealBiz.userWithdrawTwoProcess(cgwTransferResultDto);
    }

    /**
     * 资金归集第一次异步通知
     */
    @Test
    public void userWithdrawMergeOneProcessTest() {
        String msgText = "{\"bfBatchId\":\"1711121502000417962\",\"businessType\":0,\"code\":4,\"message\":\"Completed: Transfer of $10.02 to account:AC-6FNVXPCTAHH\"," +
                "\"traceLogId\":\"29f70d09-e2a4-47ec-8d8a-302d3b819757\"}";
        CgwTransferRespDto transferRespDto = JsonUtil.toObject(msgText, CgwTransferRespDto.class);
        userWithdrawCgwDealBiz.userWithdrawMergeOneProcess(transferRespDto);
    }

    /**
     * 资金归集第二次异步通知
     */
    @Test
    public void userWithdrawMergeTwoProcessTest() {
        String msgText = "{\"bfBatchId\":\"1711121502000417962\",\"businessType\":1,\"code\":2,\"message\":\"Completed: Transfer of $60.00 to account:AC-6FNVXPCTAGH\",\"traceLogId\":\"cbc03735-3f11-4675-b017-9eb943e063ea\"}";
        CgwTransferResultDto cgwTransferResultDto = JsonUtil.toObject(msgText, CgwTransferResultDto.class);
        userWithdrawCgwDealBiz.userWithdrawMergeTwoProcess(cgwTransferResultDto);
    }

    /**
     * 处理收支明细通知
     */
    @Test
    public void dealPayeePaymentDetailTest() {

    }
}
