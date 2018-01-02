package com.baofu.international.global.account.core.facade.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 企业用户请求DTO
 * <p>
 * 1、企业用户请求DTO
 * </p>
 * User: 陶伟超 Date: 2017/11/4 ProjectName: account-core Version: 1.0.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class UserOrgReqDto extends BaseDTO {
    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 申请编号
     */
    private Long userInfoNo;

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
     * 营业执照文件DFS编号
     */
    private Long idDfsId;

    /**
     * 证件号(营业执照号)
     */
    private String idNo;

    /**
     * 经营类别
     */
    private String managementCategory;

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
    private Long taxRegistrationCertDfsId;

    /**
     * 组织机构代码证照片DFS编号
     */
    private Long orgCodeCertDfsId;

    /**
     * 法人姓名
     */
    private String legalName;

    /**
     * 法人证件类型：1-身份证
     */
    private Integer legalIdType;

    /**
     * 法人证件号
     */
    private String legalIdNo;

    /**
     * 法人身份证照片正面DFS编号
     */
    private Long legalIdFrontDfsId;

    /**
     * 法人身份证照片反面DFS编号
     */
    private Long legalIdReverseDfsId;

    /**
     * 法人证件生效日期
     */
    private Date legalCertStartDate;

    /**
     * 法人证件失效期
     */
    private Date legalCertExpiryDate;

    /**
     * 地址
     */
    private String address;

    /**
     * 邮编
     */
    private String postCode;
    /**
     * 认证状态：0-未认证；1-认证中 2-已认证 默认为未认证
     */
    private Integer realnameStatus;

    /**
     * 手机号(登录手机号)
     */
    private String phoneNumber;

    /**
     * 邮箱(登录邮箱)
     */
    private String email;
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
     * 是否长期, 是-Y，否-N
     */
    private String longTerm;
    /**
     * 卡信息表主键
     */
    private Long bankCardRecordNo;
    /**
     * 认证失败原因
     */
    private String remarks;
}

