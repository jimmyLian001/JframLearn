package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 多币种账户信息对象
 * <p>
 * User: 不良人 Date:2017/4/26 ProjectName: cbpayservice Version: 1.0
 */
@Setter
@Getter
@ToString
public class CbPaySettleBankDto implements Serializable {

    private static final long serialVersionUID = 3153682049138472513L;

    /**
     * 商户编号
     */
    @NotNull(message = "商户编号不能为空")
    private Long memberId;

    /**
     * 结算币种
     */
    @NotBlank(message = "结算币种不能为空")
    @Length(max = 3, message = "结算币种长度不能超过3")
    private String settleCcy;

    /**
     * 银行账户编号
     */
    @NotBlank(message = "银行账户编号不能为空")
    @Length(max = 32, message = "银行账户编号长度不能超过32")
    private String bankAccNo;

    /**
     * 银行名称
     */
    @NotBlank(message = "银行名称不能为空")
    @Length(max = 128, message = "银行名称长度不能超过128")
    private String bankName;

    /**
     * 银行账户名称
     */
    @NotBlank(message = "银行账户名称不能为空")
    @Length(max = 64, message = "银行账户名称长度不能超过64")
    private String bankAccName;

    /**
     * 收款人地址
     */
    @NotBlank(message = "收款人地址不能为空")
    @Length(max = 256, message = "收款人地址长度不能超过256")
    private String payeeAddress;

    /**
     * 汇款费用承担方:1-商户承担，2-共同承担，3-宝付承担
     */
    @Pattern(regexp = "(1)|(2)|(3)")
    private String costBorne;

    /**
     * 收款行SWIFT_CODE
     */
    private String bankSwiftCode;

    /**
     * 收款行大额行号
     */
    private String bankLargeCode;

    /**
     * 跨境清算系统：CIPS-人民币跨境支付系统；HVPS-大额支付系统
     */
    @NotBlank(message = "跨境清算系统不能为空")
    @Length(max = 8, message = "跨境清算系统长度不能超过8")
    @Pattern(regexp = "(CIPS)|(HVPS)", message = "跨境清算系统只能填写CIPS或者HVPS")
    private String settleSystem;

    /**
     * 中转行SWIFT_CODE
     */
    private String middleSwiftCode;

    /**
     * 中转行银行名称
     */
    private String middleBankName;

    /**
     * 中转行国家
     */
    private String middleBankCountry;

    /**
     * 中转行行号
     */
    private String middleBankCode;

    /**
     * 远洋标识：YES-远洋（非港澳台），NO-近洋（港澳台）
     */
    @NotBlank(message = "远洋标识不能为空")
    @Pattern(regexp = "(YES)|(NO)", message = "远洋标识只能填写YES或者NO")
    @Length(max = 8, message = "远洋标识长度不能超过8")
    private String oceanicFlag;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 结算状态：1-不可结算；2-可结算
     */
    @NotBlank(message = "结算状态不能为空")
    @Length(max = 2, message = "结算状态长度不能超过2")
    @Pattern(regexp = "(1)|(2)", message = "结算状态只可以填写1或者2")
    private String settleStatus;

    /**
     * 创建人
     */
    @NotBlank(message = "创建人不能为空")
    @Length(max = 64, message = "创建人长度不能超过64")
    private String createBy;

    /**
     * 购汇方式
     */
    @NotBlank(message = "购汇类型不能为空")
    private String exchangeType;

    /**
     * 开户行地址
     */
    @Length(max = 256, message = "开户行地址长度不能超过256")
    private String bankAddress;

    /**
     * 账户类型：1-企业账户，2-个人账户
     */
    @NotBlank(message = "账户类型不能为空")
    @Pattern(regexp = "(1)|(2)", message = "账户类型只可以填写1或者2")
    private String bankAccType;

    /**
     * 当结算币种是澳元的，银行需要该字段进行汇款
     */
    private String bsbNumber;
    /**
     * 国别
     */
    @NotBlank(message = "国别不能为空")
    private String countryCode;
}
