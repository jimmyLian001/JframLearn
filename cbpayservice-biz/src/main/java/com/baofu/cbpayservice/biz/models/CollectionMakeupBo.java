package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商户汇款到宝付备付金账户通知对象
 * <p>
 * User: 不良人 Date:2017/4/13 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class CollectionMakeupBo implements Serializable {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 宝付渠道编号
     */
    private Long channelId;

    /**
     * 商户外币汇入编号,由银行通知时填入
     */
    private String incomeNo;

    /**
     * 汇入金额
     */
    private BigDecimal incomeAmt;

    /**
     * 汇入币种
     */
    private String incomeCcy;

    /**
     * 汇入时间
     */
    private Date incomeAt;

    /**
     * 银行名称
     */
    private String bankName;

}
