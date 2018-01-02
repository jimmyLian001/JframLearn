package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class UserStoreDo extends BaseDo {

    /**
     * 店铺编号
     */
    private Long storeNo;

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 店铺所在平台
     */
    private String storePlatform;

    /**
     * 店铺链接
     */
    private String storeUrl;

    /**
     * 店铺状态 0-冻结 1-正常 2-失效
     */
    private Integer storeState;

    /**
     * 店铺交易币种
     */
    private String tradeCcy;

    /**
     * 店铺是否存在
     */
    private String storeExist;

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
}