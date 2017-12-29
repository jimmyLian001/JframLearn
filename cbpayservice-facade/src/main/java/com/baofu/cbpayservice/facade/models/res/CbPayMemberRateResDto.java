package com.baofu.cbpayservice.facade.models.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * User: yangjian  Date: 2017-05-15 ProjectName:  Version: 1.0
 */
@Getter
@Setter
@ToString
public class CbPayMemberRateResDto {
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
     * 系统ID编号
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    private Date updateAt;

    /**
     * 更新人
     */
    private String updateBy;
}
