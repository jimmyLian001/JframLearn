package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 添加跨境订单信息查询请求参数信息
 * User: 香克斯 Date:2016/10/25 ProjectName: asias-icpaygate Version: 1.0
 */
@Setter
@Getter
@ToString
public class CbPayOrderQueryReqDto implements Serializable {

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 商户订单号
     */
    private String memberTransId;

    /**
     * 宝付订单号
     */
    private Long orderId;
}
