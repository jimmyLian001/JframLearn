package com.baofu.cbpayservice.biz.models;

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
public class CbPayMemberRateReqBo {

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

    /**start add by feng_jiang 2017/08/07 **/
    /**
     * 业务类型
     */
    private Integer businessType;
    /**
     * 浮动值设置方式
     */
    private Integer rateSetType;
    /**
     * 商户浮动汇率bp
     */
    private Long memberRateBp;
    /**
     * 开始时间
     */
    private Date beginDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**end add by feng_jiang 2017/08/07 **/
}
