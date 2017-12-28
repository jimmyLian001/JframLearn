package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.BaseTest;
import com.baofu.cbpayservice.biz.convert.CbPaySettleConvert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述
 * <p>
 * User: 不良人 Date:2017/9/22 ProjectName: cbpayservice Version: 1.0
 */
public class NotifyBizTest extends BaseTest {

    /**
     * 通知服务
     */
    @Autowired
    private NotifyBiz notifyBiz;

    /**
     * 测试通知商户
     */
    @Test
    public void notifyMemberTest() {
        String memberId = "100018529";
        String terminalId = "100001204";
        String notifyUrl = "http://localhost:8088/test";
        String context = "这是个测试";
   //     notifyBiz.notifyMember(memberId, terminalId, notifyUrl, context);

//        CbPaySettleConvert.getContextVerify(10829184L);
    }
}
