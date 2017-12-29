package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 批量汇款参数
 * <p>
 * User: 不良人 Date:2017/9/22 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class BatchRemitBo {


    /**
     * 目标币种
     */
    private String targetCcy;

    /**
     * 汇款金额
     */
    private BigDecimal remitAmt;

    /**
     * 商戶备案主体编号
     */
    private String entityNo;

    /**
     * 商户在宝付系统开通的唯一商户编号
     */
    private Long memberId;

    /**
     * 商戶账户信息
     */
    private String accountInfo;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 汇款文件名称
     */
    private String fileName;

}
