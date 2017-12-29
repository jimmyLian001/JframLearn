package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 修改商户币种账户信息对象
 * <p>
 * User: 不良人 Date:2017/4/26 ProjectName: feature-BFO-793-170426-zwb Version: 1.0
 */

@Setter
@Getter
@ToString
public class ModifySettleBankBo {

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
     * 更新人
     */
    private String updateBy;

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
    private String bankAccType;

    /**
     * 当结算币种是澳元的，银行需要该字段进行汇款
     */
    private String bsbNumber;
    /**
     * 国别 当账户类型是“个人”时，国别必填
     */
    private String countryCode;
}
