package com.test.biz;

import com.baofu.international.global.account.core.biz.external.EmailSendBizImpl;
import com.baofu.international.global.account.core.biz.models.EmailReqBo;
import com.google.common.collect.Lists;
import com.test.frame.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * <p>
 * 1、方法描述
 * </p>
 * User: 香克斯  Date: 2017/11/4 ProjectName:account-core  Version: 1.0
 */
public class EmailSendBizTest extends Base {

    /**
     * 发送邮件统一服务
     */
    @Autowired
    private EmailSendBizImpl emailSendBiz;


    @Test
    public void emailSend() {
        EmailReqBo emailReqBo = new EmailReqBo();
        emailReqBo.setMailAddressTO(Lists.newArrayList("xiangkesi@baofu.com"));
        emailReqBo.setSubject("测试发送邮件");
        emailReqBo.setContent("【宝付支付】恭喜您，您已成功开通美元收款账户，请登录https://global.baofu.com/  查看收款账户详情。客服热线：021-68819999-8636");
        emailSendBiz.emailSend(emailReqBo);
    }
}
