package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 1、收款账户申请开通请求参数信息
 * </p>
 * User: 香克斯  Date: 2017/11/27 ProjectName:account-client  Version: 1.0
 */
@Setter
@Getter
@ToString
public class ApplyAccountReqVo {

    /**
     * 资质编号
     */
    private Long qualifiedNo;

    /**
     * 店铺标识，Y-是，N-否
     */
    private String storeExist;

    /**
     * 站点
     */
    private String siteId;

    /**
     * 店铺名称
     */
    private String storeName;

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
    private String accessKey;

    /**
     * 密钥
     */
    private String secretKey;
}
