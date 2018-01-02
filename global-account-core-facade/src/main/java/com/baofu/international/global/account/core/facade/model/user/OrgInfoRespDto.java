package com.baofu.international.global.account.core.facade.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 企业用户基础信息
 * <p>
 * </p>
 *
 * @author hetao
 * @version 1.0.0
 * @date 2017.12.04
 */
@Setter
@Getter
@ToString(callSuper = true)
public class OrgInfoRespDto extends BaseDTO {
    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 申请编号
     */
    private Long userInfoNo;

    /**
     * 客户号
     */
    private Long customerNo;

    /**
     * 客户状态 0-冻结 1-正常 2-失效
     */
    private Integer state;

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
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String phoneNumber;

    /**
     * 认证状态：0-未认证；1-认证中 2-已认证 3认证失败 默认为未认证
     */
    private Integer realnameStatus;

    /**
     * 备注 认证失败原因
     */
    private String remarks;

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
}