package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 描述：用户提现文件上传参数
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 **/
@Getter
@Setter
@ToString
public class UserWithdrawFileUploadReqBo implements Serializable {

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 文件系统DFS上传的ID
     */
    private Long dfsFileId;

    /**
     * 文件类型 0-用户提现明细
     */
    private String fileType;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 操作人
     */
    private String createBy;

    /**
     * 文件批次号
     */
    private Long fileBatchNo;

    /**
     * 商户名称
     */
    private String userName;

    /**
     * 提现订单号
     */
    private Long withdrawOrderId;

    /**
     * 汇款流水号
     */
    private String incomeNo;

    /**
     * 前台人工输入的币种
     */
    private String manCcy;

    /**
     * 前台手动输入的汇款金额
     */
    private BigDecimal manTotalMoney;

    /**
     * 申请ID
     */
    private Long applyId;

    /**
     * 汇入汇款编号(异步通知用)
     */
    private String incomeNoTwo;

    /**
     * 发起方（如：API或商户前台）
     */
    private String sponsor;

    /**
     * 日志
     */
    private String traceLogId;
}
