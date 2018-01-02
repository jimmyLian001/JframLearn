package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 企业认证信息
 * <p>
 * </p>
 *
 * @author : hetao  Date: 2017/11/7 ProjectName:account-core  Version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class OrgInfoReqBo {

    /**
     * 申请编号
     */
    private String loginNo;

    /**
     * 认证申请编号
     */
    private Long authApplyNo;

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 客户状态 0-冻结 1-正常 2-失效
     */
    private int state;

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
    private int idType;

    /**
     * 证件号(营业执照号)
     */
    private String idNo;

    /**
     * 营业执照文件DFS编号
     */
    private Long idDfsId;

    /**
     * 营业执照开业日期
     */
    private Date licenseBeginDate;

    /**
     * 营业执照到期日
     */
    private Date licenseEndDate;

    /**
     * 税务登记证照片DFS编号
     */
    private Long taxRegistrationCertificateDfsId;

    /**
     * 组织机构代码证照片DFS编号
     */
    private Long orgCodeCertificateDfsId;

    /**
     * 法人姓名
     */
    private String legalName;

    /**
     * 法人身份证照片正面DFS编号
     */
    private Long legalIdFrontDfsId;

    /**
     * 法人身份证照片反面DFS编号
     */
    private Long legalIdReverseDfsId;

    /**
     * 法人证件号
     */
    private String legalIdNo;

    /**
     * 法人证件生效日期
     */
    private Date legalCertStartDate;

    /**
     * 法人证件失效期
     */
    private Date legalCertExpiryDate;

    /**
     * 邮编
     */
    private String postCode;

    /**
     * 地址
     */
    private String address;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 县市区
     */
    private String area;

    /**
     * 请求类型 1:来自我的账户主页请求 2:来自点击申请开户请求 3:来自新增资质请求
     */
    private int requestType;

    /**
     * 用户信息编号
     */
    private Long userInfoNo;

    /**
     * 邮箱
     */
    private String email;
}