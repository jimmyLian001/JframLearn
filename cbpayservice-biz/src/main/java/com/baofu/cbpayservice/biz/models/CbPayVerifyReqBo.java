package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 身份认证请求信息
 * User: wanght Date:2017/1/12 ProjectName: cbpay-service Version: 1.0
 */
@Setter
@Getter
@ToString
public class CbPayVerifyReqBo implements Serializable {

    /**
     * 商户号
     */
    private Long memberId;

    /**
     * 终端号
     */
    private String terminalId;

    /**
     * 商户订单号
     */
    private String transId;

    /**
     * 交易日期 14位
     */
    private String transDate;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 姓名
     */
    private String idName;
}
