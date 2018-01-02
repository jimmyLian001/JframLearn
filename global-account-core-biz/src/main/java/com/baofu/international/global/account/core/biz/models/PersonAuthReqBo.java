package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 1、方法描述
 * </p>
 * User: 香克斯  Date: 2017/11/4 ProjectName:account-core  Version: 1.0
 */
@Setter
@Getter
@ToString
public class PersonAuthReqBo {

    /**
     * 用户名
     */
    private Long userNo;

    /**
     * 认证申请编号
     */
    private Long authApplyNo;

    /**
     * 请求渠道流水号
     */
    private Long authReqNo;

    /**
     * 身份证号码
     */
    private String idCardNo;

    /**
     * 身份证姓名
     */
    private String idCardName;

    /**
     * 卡号
     */
    private String bankCardNo;

    /**
     * 认证状态
     */
    private int authStatus;

    /**
     * 认证描述
     */
    private String remarks;

    /**
     * 登录名
     */
    private String loginNo;

    /**
     * 卡类型1借记卡 2货记卡 3预付卡 4准贷记卡
     */
    private int bankCardType;
}
