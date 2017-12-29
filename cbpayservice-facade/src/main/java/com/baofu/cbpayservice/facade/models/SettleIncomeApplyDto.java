package com.baofu.cbpayservice.facade.models;

import com.baofu.cbpayservice.common.constants.Constants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 外汇汇入申请参数
 * <p>
 * User: 不良人 Date:2017/4/15 ProjectName: cbpayservice Version: 1.0
 */

@Getter
@Setter
@ToString
public class SettleIncomeApplyDto implements Serializable {

    /**
     * 商户编号
     */
    @NotNull(message = "商户编号不能为空")
    private Long memberId;

    /**
     * 终端号
     */
    private Integer terminalId;

    /**
     * 商户外币汇入编号,由商户填入（TT编号）
     */
    @NotBlank(message = "商户汇款流水号不能为空")
    @Length(max = 32, message = "商户汇款流水号长度异常")
    private String incomeNo;

    /**
     * 订单金额
     */
    @NotNull(message = "订单金额不能为空")
    @Digits(fraction = 2, integer = 17, message = "订单金额填写异常")
    @DecimalMin(value = Constants.ZERO, message = "订单金额必须大于0")
    @DecimalMax(value = Constants.MAX_QUATO, message = "订单金额不能大于99999999999999999.99")
    private BigDecimal orderAmt;

    /**
     * 订单币种
     */
    @NotBlank(message = "订单币种不能为空")
//    @Pattern(regexp = "(USD)|(EUR)|(HKD)|(JPY)|(AUD)|(GBP)|(CAD)",message = "汇入币种暂不支持")
    private String orderCcy;

    /**
     * 汇款人名称
     */
    @NotBlank(message = "汇款人名称不能为空")
    private String incomeAccountName;

    /**
     * 汇入账户（宝付备付金账户）
     */
    @NotBlank(message = "汇入账户不能为空")
    @Length(max = 32, message = "汇入账户长度异常")
    private String incomeAccount;

    /**
     * 汇款凭证dfs文件Id
     */
    @NotBlank(message = "汇款凭证不能为空")
    private String paymentFileId;

    /**
     * 汇款人国别
     */
    @NotBlank(message = "付款人国别不能为空")
    @Pattern(regexp = "[A-Z]{3}", message = "付款人国别填写异常")
    private String remittanceCountry;

    /**
     * 汇款人帐号
     */
    @NotBlank(message = "付款人帐号不能为空")
    @Length(max = 64, message = "付款人帐号长度异常")
    private String remittanceAcc;

    /**
     * 汇款人名称
     */
    @NotBlank(message = "付款人名称不能为空")
    @Length(max = 64, message = "付款人名称长度异常")
    private String remittanceName;

    /**
     * 收款行
     */
    @NotBlank(message = "收款行不能为空")
    @Length(max = 64, message = "收款行长度异常")
    private String incomeBankName;

    /**
     * 通知地址
     */
    private String notifyUrl;

    /**
     * 结汇订单DFS文件ID
     */
    @NotNull(message = "结汇订单明细文件编号不能为空")
    private Long settleDFSId;

    /**
     * 文件名称
     */
    @NotBlank(message = "订单明细文件名称不能为空")
    private String fileName;

    /**
     * 商户名称
     */
    private String memberName;

}