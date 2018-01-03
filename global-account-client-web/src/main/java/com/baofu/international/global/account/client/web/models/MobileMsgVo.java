package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by luoping on 2017/11/29 0029.
 */
@Setter
@Getter
@ToString
public class MobileMsgVo {
    /**
     * 当前手机号
     */
    private String currentPhoneNumber;
    /**
     * 新手机号
     */
    private String afterFixPhoneNumber;
    /**
     * 验证码
     */
    private String messageCode;
    /**
     * 业务类型
     */
    private String serviceType;
}
