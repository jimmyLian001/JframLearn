package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 批量提现订单创建参数
 * <p>
 * User: feng_jiang Date:2017/11/21 ProjectName: globalaccount-core Version: 1.0
 */
@Getter
@Setter
@ToString
public class BatchWithdrawBo {

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 商戶提现账户信息
     */
    private Long accountNo;

    /**
     * 提现币种
     */
    private String withdrawCcy;

    /**
     * 提现金额
     */
    private BigDecimal withdrawAmt;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 卖家编号
     */
    private String sellerId;

    /**
     * 收款人证件类型：1-身份证
     */
    private int payeeIdType;

    /**
     * 收款人证件号码
     */
    private String payeeIdNo;

    /**
     * 收款人证件姓名
     */
    private String payeeName;

    /**
     * 提现编号
     */
    private Long batchNo;
}
