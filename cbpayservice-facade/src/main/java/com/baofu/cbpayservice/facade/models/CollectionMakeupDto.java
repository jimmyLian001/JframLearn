package com.baofu.cbpayservice.facade.models;

import com.baofu.cbpayservice.common.constants.Constants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商户汇款到宝付备付金账户通知对象
 * <p>
 * User: 不良人 Date:2017/4/13 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class CollectionMakeupDto implements Serializable {

    /**
     * 商户编号
     */
    @NotNull(message = "商户编号不能为空")
    private Long memberId;

    /**
     * 宝付渠道编号
     */
    @NotNull(message = "渠道编号不能为空")
    private Long channelId;

    /**
     * 商户外币汇入编号,由银行通知时填入
     */
    @NotBlank(message = "商户外币汇入编号不能为空")
    private String incomeNo;

    /**
     * 汇入金额
     */
    @NotNull(message = "汇入金额不能为空")
    @Digits(integer = 17, fraction = 2, message = "汇入金额异常")
    @DecimalMin(value = Constants.ZERO, message = "汇入金额必须大于0",inclusive = false)
    @DecimalMax(value = Constants.MAX_QUATO, message = "汇入金额不能大于99999999999999999.99")
    private BigDecimal incomeAmt;

    /**
     * 汇入币种
     */
    @NotBlank(message = "汇入币种不正确")
    private String incomeCcy;

    /**
     * 汇入时间
     */
    @NotNull(message = "汇入时间不能为空")
    private Date incomeAt;

    /**
     * 银行名称
     */
    private String bankName;

}
