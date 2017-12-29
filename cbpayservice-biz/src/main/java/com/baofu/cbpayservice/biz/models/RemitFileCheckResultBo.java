package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 汇款文件校验结果
 * <p>
 * User: 不良人 Date: 2017/3/15 ProjectName: cbpay-customs-service Version: 1.0
 */
@Setter
@Getter
@ToString
public class RemitFileCheckResultBo<T> {

    /**
     * 校验错误信息
     */
    private StringBuilder errorMsg;

    /**
     * 代理报关记录
     */
    private T data;

    /**
     * 结算总金额
     */
    private BigDecimal totalAmount;

    /**
     * 文件类型：
     */
    private String orderType;

    /**
     * 数据校验错误条数
     */
    private int errorCount;

    /**
     * 数据校验成功条数
     */
    private int successCount;

    /**
     * 数据总条数
     */
    private int totalCount;

    /**
     * 行业类型
     */
    private String careerType;

}
