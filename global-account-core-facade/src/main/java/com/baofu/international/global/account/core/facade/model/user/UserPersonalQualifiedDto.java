package com.baofu.international.global.account.core.facade.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class UserPersonalQualifiedDto implements Serializable {

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
     * 证件类型：1-身份证
     */
    private Byte idType;
    /**
     * 证件号
     */
    private String idNo;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 用户英文名称
     */
    private String enName;
    /**
     * 身份证照片正面DFS编号
     */
    private Long idFrontDfsId;
    /**
     * 身份证照片反面DFS编号
     */
    private Long idReverseDfsId;
    /**
     * 是否长期, 是-Y，否-N
     */
    private String longTerm;
    /**
     * 证件生效日期
     */
    private Date certStartDate;
    /**
     * 证件失效日期
     */
    private Date certExpiryDate;
    /**
     * 经营类别
     */
    private String managementCategoryType;
    /**
     * 职业
     */
    private String occupation;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String area;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 邮编
     */
    private String postCode;
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
     * 备注(认证失败时填写认证失败原因)
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
    /**
     * 认证时默认绑定银行卡编号
     */
    private Long bankCardRecordNo;

}