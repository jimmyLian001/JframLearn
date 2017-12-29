package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 运营线下上传结汇汇款凭证信息业务对象
 * <p/>
 * User: lian zd Date:2017/9/20 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class SettlePaymentFileUploadBo {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 汇入汇款编号
     */
    private String incomeNo;

    /**
     * 申请编号
     */
    private Long applyNo;

    /**
     * 汇款凭证dfsId（商户前台传入）
     */
    private Long paymentFileId;

}
