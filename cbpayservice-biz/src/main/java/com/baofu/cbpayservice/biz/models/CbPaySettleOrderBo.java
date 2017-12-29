package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CbPaySettleOrderBo {

    /**
     * 商户订单号
     */
    private String memberTransId;

    /**
     * 收款人证件类型：1-身份证
     */
    private String payeeIdType;

    /**
     * 收款人证件号码
     */
    private String payeeIdNo;

    /**
     * 收款人证件姓名
     */
    private String payeeName;

    /**
     * 收款人帐号
     */
    private String payeeAccNo;

    /**
     * 商户交易时间
     */
    private String memberTransDate;

    /**
     * 交易币种，一般是外币（交易币种）
     */
    private String orderCcy;

    /**
     * 交易金额（交易金额）
     */
    private String orderAmt;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品单价
     */
    private String goodsPrice;

    /**
     * 商品数量
     */
    private String goodsNum;

    /**
     * 商户编号
     */
    private String memberId;

    /**
     * 宝付订单号
     */
    private String orderId;

    /**
     * 快递公司编号（物流公司编号）
     */
    private String logisticsCompanyNumber;

    /**
     * 物流单号（运单号）
     */
    private String logisticsNumber;

    /**
     * 收货人姓名
     */
    private String consigneeName;

    /**
     * 收货人联系方式
     */
    private String consigneeContact;

    /**
     * 收货人地址
     */
    private String consigneeAddress;

    /**
     * 发货日期
     */
    private String deliveryDate;

    /**
     * version
     */
    private String version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CbPaySettleOrderBo that = (CbPaySettleOrderBo) o;
        if (memberTransId != null ? !memberTransId.equals(that.memberTransId) : that.memberTransId != null)
            return false;
        if (memberId != null ? !memberId.equals(that.memberId) : that.memberId != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = memberTransId != null ? memberTransId.hashCode() : 0;
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        return result;
    }
}