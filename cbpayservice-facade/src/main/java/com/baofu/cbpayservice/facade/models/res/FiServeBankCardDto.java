package com.baofu.cbpayservice.facade.models.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 四要素认证订单信息
 * User: 香克斯 Date:2016/9/23 ProjectName: cbpayservice Version: 1.0
 */
@Setter
@Getter
@ToString
public class FiServeBankCardDto implements Serializable {

    /**
     * 主键ID编号
     */
    private Long id;

    /**
     * 宝付订单号
     */
    private Long orderId;

    /**
     * 宝付交易号
     */
    private String tradeNo;

    /**
     * 宝付业务流水号
     */
    private String businessNo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 备注
     */
    private String remark;

    /**
     * 银行卡号(加密)
     */
    private String bankCardNo;

    /**
     * 持卡人姓名
     */
    private String bankCardName;

    /**
     * 持卡人身份证号(加密)
     */
    private String bankCardIdCard;
    /**
     * 绑定手机号
     */
    private String bankCardMobile;

    /**
     * 有效年份(加密)
     */
    private String bankCardYear;

    /**
     * 有效月份(加密)
     */
    private String bankCardMonth;

    /**
     * 银行卡code(加密)
     */
    private String bankCardCode;

    /**
     * 订单金额
     */
    private BigDecimal orderMoney;

    /**
     * 成功金额
     */
    private BigDecimal succMoney;

    /**
     * 成功状态(-1-失败,0-未处理,1-成功)
     */
    private Boolean succFlag;

    /**
     * 成功时间
     */
    private Date succTime;

    /**
     * 订单状态
     */
    private Byte orderState;

    /**
     * 通知清算(0-未处理,成功)
     */
    private Byte cmFlag;

    /**
     * 支付方式
     */
    private Integer payId;

    /**
     * 渠道编号
     */
    private Integer channelId;
}