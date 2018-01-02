package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户店铺信息补全
 * User: yangjian  Date: 2017-11-11 ProjectName:  Version: 1.0
 */
@Getter
@Setter
@ToString
public class StoreInfoModifyReqDto implements Serializable {

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 店铺号
     */
    private Long storeNo;

    /**
     * 店铺名字
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
    private String awsAccessKey;

    /**
     * 秘钥
     */
    private String secretKey;

    /**
     * 店铺是否存在
     */
    private String storeExist;
}
