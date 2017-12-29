package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 跨境人民币汇款订单附加信息
 * User: wanght Date:2017/03/24 ProjectName: cbpay-service Version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class FiCbPayRemittanceAdditionDo extends BaseDo {

    /**
     * 汇款批次号
     */
    private String batchNo;

    /**
     * 商户编号
     */
    private Long memberNo;

    /**
     * 是否远洋  是：YES   否：NO
     */
    private String oceanic;

    /**
     * 费用承担方
     */
    private String costBorne;

    /**
     * 备案明细dfs文件id
     */
    private Long dfsFileId;

    /**
     * 结算银行名称
     */
    private String bankName;

    /**
     * 结算银行账户名称
     */
    private String bankAccName;

    /**
     * 结算银行账户号
     */
    private String bankAccNo;

    /**
     * 商戶备案主体编号
     */
    private String entityNo;
}