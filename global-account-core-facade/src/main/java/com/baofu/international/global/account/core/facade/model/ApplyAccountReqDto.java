package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


/**
 * <p>
 * 境外账户申请
 * </p>
 * <p>
 * User: yangjian Date:2017/11/04 ProjectName: cbpayservice Version: 1.0
 */
@Setter
@Getter
@ToString
public class ApplyAccountReqDto implements Serializable {

    private static final long serialVersionUID = 7307938687578556034L;

    /**
     * 商户编号
     */
    private Long userNo;

    /**
     * 开户币种
     */
    private String ccy;

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
     * 店铺名称
     */
    private String storeName;

    /**
     * 店铺所在平台
     */
    private String storePlatform;

    /**
     * 用户类型: 1-个人 2-企业
     */
    private Integer userType;

    /**
     * 店铺是否存在Y-是，N-否
     */
    private String storeExist;

    /**
     * 资质编号
     */
    private Long qualifiedNo;

}
