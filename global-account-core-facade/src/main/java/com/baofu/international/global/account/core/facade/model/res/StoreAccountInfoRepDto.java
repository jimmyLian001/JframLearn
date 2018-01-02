package com.baofu.international.global.account.core.facade.model.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * User: yangjian  Date: 2017-11-11 ProjectName:  Version: 1.0
 */
@Getter
@Setter
@ToString
public class StoreAccountInfoRepDto implements Serializable {

    private static final long serialVersionUID = -6480917067319567158L;
    /**
     * 银行账户
     */
    private String bankAccNo;
    /**
     * 银行所在国家
     */
    private String bankCountry;
    /**
     * 路由编号
     */
    private String rountingNo;
    /**
     * 总金额
     */
    private BigDecimal accountBal;
    /**
     * 可用余额
     */
    private BigDecimal avaliableAmt;
    /**
     * 可提前余额
     */
    private BigDecimal withdrawProcessAmt;
    /**
     * 商铺名
     */
    private String storeName;
    /**
     * 店铺号
     */
    private Long storeNo;
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
     * 资质编号
     */
    private Long qualifiedNo;

    /**
     * 银行账户名称
     */
    private String bankAccName;

    /**
     * 币种
     */
    private String ccy;

    /**
     * 店铺是否存在
     */
    private String storeExist;
}
