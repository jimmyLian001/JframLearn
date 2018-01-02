package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString(callSuper = true)
public class AuthOrgDo extends BaseDo {
    /**
     * 认证申请编号
     */
    private Long authApplyNo;

    /**
     * 请求渠道流水号
     */
    private Long authReqNo;

    /**
     * 状态 1-申请 2-认证中 3-认证失败 4-认证成功
     */
    private int authStatus;

    /**
     * 企业名称
     */
    private String name;

    /**
     * 企业英文名称
     */
    private String enName;

    /**
     * 企业证件类型：1-多证合一营业执照 2-普通营业执照
     */
    private int idType;

    /**
     * 证件号(营业执照号)
     */
    private String idNo;

    /**
     * 营业执照开业日期
     */
    private Date licenseBeginAt;

    /**
     * 营业执照到期日
     */
    private Date licenseEndAt;

    /**
     * 法人姓名
     */
    private String legalName;

    /**
     * 法人证件类型：1-身份证
     */
    private int legalIdType;

    /**
     * 法人证件号
     */
    private String legalIdNo;

    /**
     * 用户信息编号
     */
    private Long userInfoNo;
}