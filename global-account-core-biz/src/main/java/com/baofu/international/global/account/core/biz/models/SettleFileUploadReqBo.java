package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 结汇文件上传参数
 * <p>
 * User: 不良人 Date:2017/1/4 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class SettleFileUploadReqBo implements Serializable {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 文件系统DFS上传的ID
     */
    private Long dfsFileId;

    /**
     * 文件类型 0-汇款文件 1-结汇文件
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
    private String memberName;

    /**
     * 结汇OrderId(FiCbPaySettle中OrderId)或者提现订单号
     */
    private Long settleOrderId;

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
     * 11-结汇匹配成功，12-用于wyre收款账户处理
     */
    private Integer flag;

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
