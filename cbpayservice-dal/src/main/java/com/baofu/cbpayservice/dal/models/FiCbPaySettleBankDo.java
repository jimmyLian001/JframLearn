package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class FiCbPaySettleBankDo extends BaseDo {

    /**
     * 记录编号
     */
    private Long recordId;

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 结算币种
     */
    private String settleCcy;

    /**
     * 银行账户编号
     */
    private String bankAccNo;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行账户名称
     */
    private String bankAccName;

    /**
     * 收款人地址
     */
    private String payeeAddress;

    /**
     * 汇款费用承担方:1-商户承担，2-共同承担，3-宝付承担
     */
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
    private String oceanicFlag;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 结算状态：1-不可结算；2-可结算
     */
    private int settleStatus;

    /**
     * 购汇方式
     */
    private Integer exchangeType;

    /**
     * 开户行地址
     */
    private String bankAddress;

    /**
     * 账户类型：1-企业账户，2-个人账户
     */
    private Integer bankAccType;

    /**
     * 当结算币种是澳元的，银行需要该字段进行汇款
     */
    private String bsbNumber;

    /**
     * 备案主体编号
     */
    private String entityNo;
    /**
     * 国别
     */
    private String countryCode;
}