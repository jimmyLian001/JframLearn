package com.baofu.international.global.account.core.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 1、通知用户信息模版
 * </p>
 * User: 香克斯  Date: 2017/11/29 ProjectName:account-core  Version: 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotifyTemplateConst {

    /**
     * 账户开通结果通知模版
     */
    public static final String ACCOUNT_OPEN_RESULT = "恭喜您，您已成功开通${CCY}收款账户，请登录https://global.baofu.com/  查看收款账户详情。客服热线：021-68819999-8636";
}
