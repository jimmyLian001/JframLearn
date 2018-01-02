package com.baofu.international.global.account.core.facade.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class UserOrgQualifiedDto implements Serializable {

    /**
     * ID自增主键
     */
    private Long id;
    /**
     * 用户号
     */
    private Long userNo;
    /**
     * 申请编号
     */
    private Long userInfoNo;
    /**
     * 资质编号
     */
    private Long qualifiedNo;
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
    private Byte idType;
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
    private Byte legalIdType;
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
     * 是否长期, 是-Y，否-N
     */
    private String longTerm;
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
     * 省(企业)
     */
    private String province;
    /**
     * 市(企业)
     */
    private String city;
    /**
     * 区(企业)
     */
    private String area;
    /**
     * 认证状态：0-未认证；1-认证中 2-已认证 3-认证失败 默认为未认证
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
     * 认证时默认绑定银行卡编号
     */
    private Long bankCardRecordNo;
    /**
     * 备注（认证失败时填写认证失败原因）
     */
    private String remarks;
    /**
     * 创建时间
     */
    private Date createAt;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 更新时间
     */
    private Date updateAt;
    /**
     * 更新人
     */
    private String updateBy;

}