package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * </p>
 * User: 康志光 Date: 2017/3/15 ProjectName: cbpay-customs-service Version: 1.0
 */
@Setter
@Getter
@ToString
public class SettleOrderListBo {

    /**
     * 校验错误信息
     */
    private StringBuilder errorMsg;

    /**
     * 代理报关记录
     */
    private List<CbPaySettleOrderValidateBo> cbPaySettleOrderValidateBos;

    /**
     * 结算总金额
     */
    private BigDecimal totalAmount;

    /**
     * 数据校验错误条数
     */
    private int errorCount;

    /**
     * 数据校验成功条数
     */
    private int successCount;

    /**
     * 文件版本
     */
    private String version;

}
