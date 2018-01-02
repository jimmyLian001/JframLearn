package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 企业认证信息
 * <p>
 * </p>
 *
 * @author : hetao  Date: 2017/11/4 ProjectName:account-core  Version: 1.0
 */
@Setter
@Getter
@ToString
public class OrgAuthReqBo {

    /**
     * 申请编号
     */
    private Long authApplyNo;

    /**
     * 请求渠道流水号
     */
    private Long authReqNo;

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 登录名
     */
    private String loginNo;

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
    private Integer idType;

    /**
     * 证件号(营业执照号)
     */
    private String idNo;

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
     * 账户类型 1-对公账户 2-法人账户
     */
    private Integer publicFlag;

    /**
     * 法人银行卡号
     */
    private String bankCardNo;

    /**
     * 认证状态
     */
    private int authStatus;

    /**
     * 备注
     */
    private String remarks;

}
