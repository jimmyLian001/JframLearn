package com.test.biz;

import com.baofu.international.global.account.core.biz.external.SmsSendBizImpl;
import com.test.frame.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * <p>
 * 1、方法描述
 * </p>
 * User: 香克斯  Date: 2017/11/4 ProjectName:account-core  Version: 1.0
 */
public class SmsSendBizTest extends Base {

    /**
     *
     */
    @Autowired
    private SmsSendBizImpl smsSendBiz;

    /**
     * 短信测试发送
     */
    @Test(enabled = false)
    public void smsSend() {
        smsSendBiz.sendSms("18516240098", "短信测试发送");
    }
}
