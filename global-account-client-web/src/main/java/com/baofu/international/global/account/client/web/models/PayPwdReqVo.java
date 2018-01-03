package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by luoping on 2017/11/22 0022.
 */
@Getter
@Setter
@ToString
public class PayPwdReqVo {
    /**
     * 支付密码
     */
    private String payPwd;
    /**
     * 重复密码
     */
    private String payPwdAgain;
    /**
     * 验证码
     */
    private String messageCode;

}
