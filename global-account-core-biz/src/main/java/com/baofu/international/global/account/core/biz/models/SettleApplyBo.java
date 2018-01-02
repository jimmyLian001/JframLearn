package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 外汇汇入申请参数
 * <p>
 * User: 香克斯 Date:2017/07/14 ProjectName: cbpayservice Version: 1.0
 */
@Setter
@Getter
@ToString
public class SettleApplyBo implements Serializable {

    /**
     * 商户汇款流水号,由商户填入，商户汇款时在汇款凭证上的流水号
     * 1、商户汇款流水号不能为空
     * 2、商户汇款流水号长度不能超过32位
     */
    private String remitReqNo;

    /**
     * 订单金额
     * 1、订单金额不能为空
     * 2、商品单价填写异常，整数部分最大支持17位，小数位2位
     * 3、商品单价必须大于0
     * 4、商品单价不能大于99999999999999999.99
     */
    private BigDecimal orderAmt;

    /**
     * 订单币种
     * 1、订单币种不能为空，订单币种见国际货币标准
     */
    private String orderCcy;

    /**
     * 汇入账户（宝付备付金账户）
     * 1、汇入账户不能为空
     * 2、汇入账户长度不能大于32位
     */
    private String payeeAccount;

    /**
     * 汇款人国别
     * 1、付款人国别不能为空
     * 2、付款人国别填写只能是国际标准中的国家编码简写，三位英文大写字符
     */
    private String remitCountry;

    /**
     * 汇款人帐号
     * 1、汇款人帐号不能为空
     * 2、汇款人帐号长度不能超过64位
     */
    private String remitAcc;

    /**
     * 汇款人名称
     * 1、汇款人名称不能为空
     * 2、汇款人名称长度不能超过64位
     */
    private String remitName;

    /**
     * 收款行
     * 1、收款银行行不能为空
     * 2、收款银行长度不能超过64位
     */
    private String payeeBankName;

    /**
     * 通知地址
     * 1、通知地址不能为空
     */
    private String notifyUrl;

    /**
     * 汇款凭证文件文件名，如果文件传输方式为FTP时，此文件名为对应的FTP文件服务上的文件名称
     * 1、文件名称不能为空
     */
    private String voucherFileName;

    /**
     * 订单明细文件名，如果文件传输方式为FTP时，此文件名为对应的FTP文件服务上的文件名称
     * 订单明细文件名
     */
    private String detailFileName;

    /**
     * 文件上传类型,使用FTP模式时首先需把FTP信息提供给宝付系统配置
     * 1、文件上传类型不能为空
     * 1、文件上传支持HTTP或FTP方式
     */
    private String fileType;

    /**
     * 备注
     */
    private String remarks;
}
