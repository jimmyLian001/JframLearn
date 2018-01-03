package com.baofu.international.global.account.client.service.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

/**
 * 个人实名认证页面信息
 * <p>
 * @author : hetao Date:2017/11/06 ProjectName: account-client Version: 1.0
 */
@Getter
@Setter
@ToString
public class PersonAuthReq {

    /**
     * 登录名
     */
    private String loginNo;

    /**
     * 用户名
     */
    private Long userNo;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 基本信息编号
     */
    private Long userInfoNo;

    /**
     * 身份证正面照
     */
    private MultipartFile idFrontImage;

    /**
     * 身份证反面照
     */
    private MultipartFile idReverseImage;

    /**
     * 卡号
     */
    private String cardNo;

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
     * 邮编
     */
    private String postCode;
}
