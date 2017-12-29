package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User: yangjian  Date: 2017-06-07 ProjectName:cbpay-service   Version: 1.0
 */
@Getter
@Setter
@ToString
public class CbPayCancelOrderBo {

    /**
     * 文件批次号
     */
    private Long fileBatchNo;

    /**
     * 状态
     */
    private String status;

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 更新人
     */
    private String updateBy;

}
