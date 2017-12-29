package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * User: yangjian  Date: 2017-05-15 ProjectName:  Version: 1.0
 */
@Getter
@Setter
@ToString
public class CbPayMemberRateAddDto implements Serializable {

    private static final long serialVersionUID = -68360483761291950L;

    /**
     * 商户编号
     */
    @NotNull(message = "会员号不能为空")
    private Long memberId;

    /**
     * 币种
     */
    @NotBlank(message = "币种信息不能为空")
    private String ccy;

    /**
     * 商户浮动汇率百分比
     */
    @Min(value = 0, message = "商户浮动汇率百分比不能小于0")
    @Max(value = 1, message = "商户浮动汇率百分比不能大于1")
    private BigDecimal memberRate;

    /**
     * 创建人
     */
    @NotBlank(message = "创建人不能为空")
    private String createBy;

    /**
     * 更新人
     */
    @NotBlank(message = "更新人不能为空")
    private String updateBy;

    /**start add by feng_jiang 2017/08/07 **/
    /**
     * 状态：1-启用，2-失效
     */
    @NotNull(message = "商户浮动汇率状态不能为空")
    private Integer status;
    /**
     * 浮动值设置方式
     */
    @NotBlank(message = "浮动值设置方式不能为空")
    private String rateSetType;
    /**
     * 商户浮动汇率bp
     */
    @Min(value = 0, message = "商户浮动汇率bp不能小于0")
    @Max(value = 100, message = "商户浮动汇率bp不能大于100")
    private Long memberRateBp;
    /**
     * 业务类型
     */
    @NotBlank(message = "业务类型不能为空")
    private String businessType;
    /**
     * 开始时间
     */
    @NotBlank(message = "开始时间不能为空")
    private String beginDate;
    /**
     * 结束时间
     */
    @NotBlank(message = "结束时间不能为空")
    private String endDate;
    /**end add by feng_jiang 2017/08/07 **/
}
