package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 分控订单人工审核
 * User: wdj Date:2017/04/28 ProjectName: asias-icpaygate Version: 1.0
 */
@Setter
@Getter
@ToString
public class CbPayOrderRiskControlReqDto implements Serializable {

    /**
     * 宝付订单号
     */
    @NotNull(message = "宝付订单号不能为空")
    private Long orderId;

    /**
     * 商户编号
     */
    @NotNull(message = "商户编号不能为空")
    private Long memberId;

    /**
     * 商户订单号
     */
    private String memberTransId;

    /**
     * 伪造订单标识：0-真实订单；1-伪造订单   2:初始
     */
    private Integer fakeFlag;

    /**
     * 运单订单标识：0-真实运单；1-伪造运单；2-无运单信息 3初始
     */
    private Integer wayBillFlag;

    /**
     * 人工处理状态：0-待处理；1-伪造订单；2-真实订单
     */
    private Integer artifiStatus;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 更新人
     */
    @NotBlank(message = "更新人不能为空")
    private String updateBy;


}
