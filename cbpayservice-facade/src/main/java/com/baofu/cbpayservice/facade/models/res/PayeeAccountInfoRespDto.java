package com.baofu.cbpayservice.facade.models.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * description:收款人账户信息 RespDto
 * <p/>
 * Created by liy on 2017/9/11
 */
@Getter
@Setter
@ToString
public class PayeeAccountInfoRespDto implements Serializable {

    private static final long serialVersionUID = -6127767784125015686L;

    /**
     * 银行账户编号
     */
    private String bankAccNo;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行路由编号
     */
    private String routingNumber;

    /**
     * 收款人姓名
     */
    private String payeeName;

    /**
     * 账户余额
     */
    private BigDecimal accBalance;

    /**
     * 银行账户地址
     */
    private String bankAddress;

    /**
     * 账户名称
     */
    private String memberAccNo;

}