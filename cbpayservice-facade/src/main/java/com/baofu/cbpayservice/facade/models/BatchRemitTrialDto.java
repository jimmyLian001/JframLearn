package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 批量汇款参数
 * <p>
 * User: 不良人 Date:2017/9/22 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class BatchRemitTrialDto implements Serializable {

    private static final long serialVersionUID = -2366512052241293870L;
    /**
     * 目标币种
     */
    @NotBlank(message = "目标币种不能为空")
    private String targetCcy;

    /**
     * 汇款金额
     */
    @NotNull(message = "汇款金额不能为空")
    @Min(value = 1, message = "汇款金额不能小于1")
    private BigDecimal remitAmt;

    /**
     * 商戶备案主体编号
     */
    @NotBlank(message = "商戶备案主体编号不能为空")
    private String entityNo;

    /**
     * 商户在宝付系统开通的唯一商户编号
     */
    @NotNull(message = "商户编号不能为空")
    private Long memberId;

    /**
     * 创建人
     */
    @NotBlank(message = "创建人不能为空")
    private String createBy;

    /**
     * 商戶账户信息
     */
    @NotBlank(message = "商戶账户信息不能为空")
    private String accountInfo;
}
