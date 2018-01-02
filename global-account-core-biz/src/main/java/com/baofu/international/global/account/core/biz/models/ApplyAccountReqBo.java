package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User: yangjian  Date: 2017-11-06 ProjectName:  Version: 1.0
 */
@Getter
@Setter
@ToString
public class ApplyAccountReqBo {

    /**
     * 商户编号
     */
    private Long userNo;

    /**
     * 开户币种
     */
    private String ccy;

    /**
     * 商户网址
     */
    private String accountHolderWebsite;

    /**
     * 店铺英文名
     */
    private String storeName;

    /**
     * 店铺英文名
     */
    private String storePlatform;

    /**
     * 客户类型
     */
    private Integer userType;

    /**
     * 是否存在店铺Y 是  N 否
     */
    private String storeExist;

    /**
     * 资质编号
     */
    private Long qualifiedNo;

    /**
     * 经营范围
     */
    private String managementCategory;

    /**
     * 卖家编号
     */
    private String sellerId;

    /**
     * 访问键编码
     */
    private String awsAccessKey;

    /**
     * 秘钥
     */
    private String secretKey;

    /**
     * 实名认证状态
     */
    private Integer realNameStatus;
}
