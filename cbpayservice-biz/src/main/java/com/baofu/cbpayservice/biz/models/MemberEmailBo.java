package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 1、商户邮箱业务对象
 * </p>
 * User: 香克斯  Date: 2017/10/24 ProjectName:cbpay-service  Version: 1.0
 */
@Setter
@Getter
@ToString
public class MemberEmailBo {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 业务联系人
     */
    private String businessName;

    /**
     * 业务联系人手机
     */
    private String businessPhone;

    /**
     * 业务联系人QQ
     */
    private String businessQq;

    /**
     * 业务联系人邮箱
     */
    private String businessEmail;

    /**
     * 技术联系人
     */
    private String techName;

    /**
     * 技术联系人手机
     */
    private String techPhone;

    /**
     * 技术联系人QQ
     */
    private String techQq;

    /**
     * 技术联系人邮箱
     */
    private String techEmail;

    /**
     * 财务联系人
     */
    private String financeName;

    /**
     * 财务联系手机
     */
    private String financePhone;

    /**
     * 财务联系QQ
     */
    private String financeQq;

    /**
     * 财务联系邮箱
     */
    private String financeEmail;
}
