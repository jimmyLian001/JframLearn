package com.baofu.international.global.account.core.facade.model.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 店铺帐号对象
 * </p>
 * User: kangzhiguang  Date: 2017/11/12 ProjectName:account-core  Version: 1.0
 */
@Setter
@Getter
@ToString
public class UserStoreAccountsDto implements Serializable {

    /**
     * 店铺编号
     */
    private String storeNo;

    /**
     * 账户号
     */
    private Long accountNo;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 币种信息
     */
    private String ccy;

    /**
     * 账户状态：0-冻结，1-正常，2-失效
     */
    private int status;

    /**
     * 银行账号
     */
    private String bankAccNo;

    /**
     * 更新时间
     */
    private String updateAt;

    /**
     * 店铺开户申请状态
     */
    private int accountApplyStatus;

    /**
     * 可提现金额
     */
    private BigDecimal availableAmt;

    /**
     * 提现中金额
     */
    private BigDecimal withdrawProcessAmt;

    /**
     * 备注
     */
    private String remark;


    /**
     * 用户名称
     */
    private String name;

    /**
     * 是否有店铺
     */
    private String storeExist;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 资质编号
     */
    private Long qualifiedNo;

    /**
     * 申请编号
     */
    private String userInfoNo;

    /**
     * 实名状态
     */
    private Integer realnameStatus;

}
