package com.baofu.international.global.account.core.facade.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString(callSuper = true)
public class UserPersonalDto extends BaseDTO {

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 申请编号
     */
    private Long userInfoNo;

    /**
     * 证件类型：1-身份证
     */
    private Integer idType;

    /**
     * 证件号
     */
    private String idNo;

    /**
     * 客户姓名
     */
    private String name;

    /**
     * 客户英文名称
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
     * 备注(认证失败原因)
     */
    private String remarks;
    /**
     * 卡信息表主键
     */
    private Long bankCardRecordNo;
}