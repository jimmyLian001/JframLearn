package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 提现参数
 * <p>
 * User: 不良人 Date:2017/9/8 ProjectName: cbpayservice Version: 1.0
 **/
@Getter
@Setter
@ToString
public class MemberWithdrawCashBo {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 转账金额
     */
    private BigDecimal transferAmt;

    /**
     * 订单明细文件ID
     */
    private Long orderDetailFileId;

    /**
     * 转账手续费
     */
    private BigDecimal transferFee;

    /**
     * 操作人
     */
    private String createBy;

    /**
     * 文件名称
     */
    private String fileName;
}
