package com.baofu.cbpayservice.facade.models;

import com.baofu.cbpayservice.common.constants.Constants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * wyre商户提现请求参数
 * <p>
 * User: 不良人 Date:2017/9/8 ProjectName: cbpayservice Version: 1.0
 **/
@Getter
@Setter
@ToString
public class MemberWithdrawCashDto implements Serializable {

    private static final long serialVersionUID = 5903764019319361153L;

    /**
     * 商户编号
     */
    @NotNull(message = "商户编号[memberId]不能为空")
    private Long memberId;

    /**
     * 转账金额
     */
    @NotNull(message = "转账金额[transferAmt]不能为空")
    @DecimalMin(value = Constants.MIN_AMT, message = "订单金额填写不正确")
    @DecimalMax(value = Constants.MAX_QUATO, message = "订单金额填写不正确")
    private BigDecimal transferAmt;

    /**
     * 订单明细文件ID
     */
    @NotNull(message = "订单明细文件ID[orderDetailFileId]不能为空")
    private Long orderDetailFileId;

    /**
     * 文件名称
     */
    @NotBlank(message = "文件名称[fileName]不能为空")
    private String fileName;

    /**
     * 操作人
     */
    @NotBlank(message = "操作人[createBy]不能为空")
    private String createBy;
}
