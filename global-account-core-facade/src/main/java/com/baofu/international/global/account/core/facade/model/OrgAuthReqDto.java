package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

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
public class OrgAuthReqDto implements Serializable {

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
    private String companyName;

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
     * 法人证件号
     */
    private String legalIdNo;

}
