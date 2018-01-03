package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * SecurityReqVo
 * <p>
 * </p>
 *
 * @author : wuyazi
 * @date:2017/11/23
 * @version: 1.0.0
 */
@Getter
@Setter
@ToString
public class SecurityReqVo {

    /**
     * 验证码
     */
    private String verifyCode;

    /**
     * 参数
     */
    private Map<String, String> params;


}
