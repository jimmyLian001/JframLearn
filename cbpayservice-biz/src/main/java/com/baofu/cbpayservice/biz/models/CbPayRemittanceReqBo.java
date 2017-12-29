package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 跨境人民币汇款信息请求参数
 * User: wanght Date:2016/10/25 ProjectName: cbpay-service Version: 1.0
 */
@Setter
@Getter
@ToString
public class CbPayRemittanceReqBo implements Serializable {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 商户订单号
     */
    private String memberTransId;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 宝付订单号信息集合
     */
    private List<Long> orderIdList;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 订单版本
     */
    private Integer orderVersion;

    /**
     * 备注
     */
    private String remark;

    /**
     * dfs文件路径
     */
    private String dfsPath;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件批次号
     */
    private List<Long> batchFileIdList;

    /**
     * 购汇币种（需要购买的币种）
     */
    private String targetCcy;

    /**
     * 购汇方式
     */
    private Integer exchangeType;

    /**
     * 商户备案主体流水
     */
    private String entityNo;

    /**
     * 购付汇API：商户请求宝付系统的申请订单号，由商户保证此单号唯一
     */
    private String remitApplyNo;

    /**
     * API ：原始币种
     */
    private String originalCcy;

    /**
     * API原始金额
     */
    private BigDecimal originalAmt;

    /**
     * api：银行开户名
     */
    private String bankAccName;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行账号
     */
    private String bankAccNo;

    /**
     * 汇款币种
     */
    private String remitCcy;

    /**
     * 终端号
     */
    private Long terminalId;

    /**
     * 商户通知地址
     */
    private String notifyUrl;

    /**
     * 来源类型：1-批量汇款
     */
    private Integer sourceType;
}
