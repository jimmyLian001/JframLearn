package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User: yangjian  Date: 2017-11-11 ProjectName:  Version: 1.0
 */
@Setter
@Getter
@ToString
public class StoreAccInfReqBo {

    /**
     * 用户号
     */
    private Long userNo;
    /**
     * 用户类型
     */
    private int userType;
    /**
     * 店铺号
     */
    private Long storeNo;
    /**
     * 店铺名
     */
    private String storeName;
}
