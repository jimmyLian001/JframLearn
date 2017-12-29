package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 新增渠道成本信息
 * User: wanght Date:2017/10/20 ProjectName: cbpay-service Version: 1.0
 */
@Setter
@Getter
@ToString
public class CbPayChannelFeeDto implements Serializable {

    /**
     * 记录编号
     */
    private Long recordId;

    /**
     * 渠道id
     */
    @NotNull(message = "渠道号不能为空")
    private Long channelId;

    /**
     * 国外固定金额手续费
     */
    private BigDecimal abroadFixedMoney;

    /**
     * 港澳台固定金额手续费
     */
    private BigDecimal fixedMoney;

    /**
     * 计费类型,RATIO：比率
     */
    @NotBlank(message = "计费类型不能为空")
    private String chargeType;

    /**
     * 计费值
     */
    private BigDecimal chargeValue;

    /**
     * 最低手续费
     */
    private BigDecimal minMoney;

    /**
     * 最高手续费
     */
    private BigDecimal maxMoney;

    /**
     * 状态：INIT:初始，TRUE:启用，FALSE:失效
     */
    @NotBlank(message = "计费类型不能为空")
    private String status;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 创建时间
     */
    private String createAt;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改时间
     */
    private String updateAt;

    /**
     * 修改人
     */
    private String updateBy;

}
