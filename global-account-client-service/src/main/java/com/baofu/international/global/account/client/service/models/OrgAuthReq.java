package com.baofu.international.global.account.client.service.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

/**
 * 企业实名认证页面信息
 * <p>
 * @author : hetao Date:2017/11/07 ProjectName: account-client Version: 1.0
 */
@Getter
@Setter
@ToString
public class OrgAuthReq {

    /**
     * 登录名
     */
    private String loginNo;

    /**
     * 基本信息编码
     */
    private Long userInfoNo;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户名
     */
    private Long userNo;

    /**
     * 身份证正面照
     */
    private MultipartFile idFrontImage;

    /**
     * 身份证反面照
     */
    private MultipartFile idReverseImage;

    /**
     * 英文姓名
     */
    private String englishName;

    /**
     * 营业执照照片
     */
    private MultipartFile licenseImage;

    /**
     * 税务登记证照片
     */
    private MultipartFile taxRegCertImage;

    /**
     * 组织机构代码证照片
     */
    private MultipartFile orgCodeCertImage;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 法人姓名
     */
    private String legalName;

    /**
     * 法人证件号
     */
    private String legalNo;

    /**
     * 营业执照号
     */
    private String licenseNo;

    /**
     * 企业证件类型：1-多证合一营业执照 2-普通营业执照
     */
    private int licenseType;

    /**
     * 身份证正面照dfsId
     */
    private Long idFrontDfsId;

    /**
     * 身份证反面照dfsId
     */
    private Long idReverseDfsId;

    /**
     * 营业执照
     */
    private Long licenseDfsId;

    /**
     * 组织机构代码证照dfsId
     */
    private Long orgCodeCertDfsId;

    /**
     * 税务登记证照dfsId
     */
    private Long taxRegCertDfsId;

    /**
     * 地址
     */
    private String address;

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
     * 邮编
     */
    private String postCode;

    /**
     * 请求类型 1:来自我的账户主页请求 2:来自点击申请开户请求 3:来自新增资质请求
     */
    private int requestType;
}
