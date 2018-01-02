package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString(callSuper = true)
public class AuthPersonDo extends BaseDo {
    /**
     * 认证申请编号
     */
    private Long authApplyNo;

    /**
     * 认证申请编号
     */
    private Long authReqNo;

    /**
     * 状态 1-申请 2-认证中 3-认证失败 4-认证成功
     */
    private int authStatus;

    /**
     * 身份证号
     */
    private String idNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 英文名
     */
    private String enName;

    /**
     * 性别 M-男性 F-女性 N-未知
     */
    private String sex;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 地址
     */
    private String address;

    /**
     * 证件签发日期
     */
    private Date certStartDate;

    /**
     * 证件到期日
     */
    private Date certExpiryDate;
}