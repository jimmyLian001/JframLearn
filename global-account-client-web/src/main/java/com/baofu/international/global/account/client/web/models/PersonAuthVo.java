package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 个人实名认证页面信息
 * <p>
 * @author : hetao Date:2017/11/06 ProjectName: account-client Version: 1.0
 */
@Getter
@Setter
@ToString
public class PersonAuthVo implements Serializable {

    /**
     * 基本信息编号
     */
    private String userInfoNo;

    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 姓名
     */
    private String cardHolder;

    /**
     * 英文姓名
     */
    private String englishName;

    /**
     * 身份证号
     */
    private String idNo;

    /**
     * 有效期
     */
    private String certExpiryDate;

    /**
     * 经营类别
     */
    private String managementCategory;

    /**
     * 职业
     */
    private String occupation;

    /**
     * 省
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 地区
     */
    private String area;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 登录名
     */
    private String loginNo;

    /**
     * 用户名
     */
    private Long userNo;

    /**
     * 邮编
     */
    private String postCode;
}
