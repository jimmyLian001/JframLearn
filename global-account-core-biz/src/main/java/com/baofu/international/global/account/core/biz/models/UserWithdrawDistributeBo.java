package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 用户转账分发参数
 * User: feng_jiang Date:2017/11/13 ProjectName: globalaccount-core Version: 1.0
 **/
@Getter
@Setter
@ToString
public class UserWithdrawDistributeBo {

    /**
     * 商户汇款流水号
     */
    private String remitReqNo;

    /**
     * 请求对应业务编号
     */
    private Long businessNo;

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 状态
     */
    private String status;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 订单金额
     */
    private BigDecimal orderAmt;

    /**
     * 订单币种
     */
    private String orderCcy;

    /**
     * 文件处理失败原因
     */
    private String errorFileName;

}
