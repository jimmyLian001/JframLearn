package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class FiCbPayMemberRateDo extends BaseDo {

    /**
     * 记录编号
     */
    private Long recordId;

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 币种
     */
    private String ccy;

    /**
     * 商户浮动汇率百分比
     */
    private BigDecimal memberRate;

    /**
     * 状态：1-启用，2-失效
     */
    private Integer status;

    /**
     * 浮动汇率设置方式
     */
    private Integer rateSetType;

    /**
     * 商户浮动汇率bp
     */
    private Long memberRateBp;

    /**
     * 业务类型
     */
    private Integer businessType;

    /**
     * 开始时间
     */
    private Date beginDate;

    /**
     * 结束时间
     */
    private Date endDate;
}