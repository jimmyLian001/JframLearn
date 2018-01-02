package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 个人认证信息
 *
 * @author : hetao  Date: 2017/11/06 ProjectName: globalaccount Version: 1.0
 */
@Setter
@Getter
@ToString
public class PersonInfoReqBo {

    /**
     * 登录名
     */
    private String loginNo;

    /**
     * 认证申请编号
     */
    private Long authApplyNo;

    /**
     * 用户信息编号
     */
    private Long userInfoNo;

    /**
     * 用户名
     */
    private Long userNo;

    /**
     * 客户状态 0-冻结 1-正常 2-失效
     */
    private Integer state;

    /**
     * 卡号
     */
    private String bankCardNo;

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
    private String idName;

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
     * 证件生效日期
     */
    private Date certStartDate;

    /**
     * 证件失效日期
     */
    private Date certExpiryDate;

    /**
     * 是否长期, 是-Y，否-N
     */
    private String longTerm;

    /**
     * 经营类别
     */
    private String managementCategoryType;

    /**
     * 职业
     */
    private String occupation;

    /**
     * 邮编
     */
    private String postCode;

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
     * 邮箱
     */
    private String email;
}
