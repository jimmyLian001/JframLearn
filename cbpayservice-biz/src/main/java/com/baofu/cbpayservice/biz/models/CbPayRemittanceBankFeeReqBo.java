package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 付汇入账费用更新请求参数
 * User: dxy Date:2017/3/23 ProjectName: cbpay-service Version: 1.0
 */
@Setter
@Getter
@ToString
public class CbPayRemittanceBankFeeReqBo implements Serializable {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 汇款批次号
     */
    private String batchNo;

    /**
     * 入账费用
     */
    private BigDecimal bankFee;

    /**
     * 更新人员
     */
    private String updateBy;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 入账费用审核状态
     */
    private int bankFeeStatus;

    /**
     * 凭证dfs文件id
     */
    private Long receiptId;

    /**
     * 购汇方式
     */
    private Integer exchangeType;
}
