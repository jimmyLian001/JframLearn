package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class UserPayeeAccountDo extends BaseDo {

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 银行账户编号，提供给商户使用的银行账户
     */
    private String bankAccNo;

    /**
     * 银行路由编号，提供给商户使用的路由编号
     */
    private String routingNumber;

    /**
     * 币种信息
     */
    private String ccy;

    /**
     * 渠道方虚拟账户
     */
    private String walletId;

    /**
     * 账户状态：0-冻结，1-正常，2-失效
     */
    private int status;

    /**
     * 渠道编号
     */
    private Long channelId;

    /**
     * 资质编号
     */
    private Long qualifiedNo;

    /**
     * 账户编号
     */
    private Long accountNo;

    /**
     * 银行账户名称
     */
    private String bankAccName;
}