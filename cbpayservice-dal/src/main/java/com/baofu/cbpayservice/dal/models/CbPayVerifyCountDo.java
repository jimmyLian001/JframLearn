package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 实名认证实体类
 * <p>
 * Created by 莫小阳 on 2017/5/31.
 */
@ToString
@Getter
@Setter
public class CbPayVerifyCountDo extends BaseDo {

    /**
     * 文件批次号
     */
    private Long fileBatchNo;

    /**
     * 认证笔数
     */
    private Integer verifyCount;

    /**
     * 认证成功笔数
     */
    private Integer verifyCountSuc;


    /**
     * 认证失败笔数
     */
    private Integer verifyCountFail;

    /**
     * 抽查状态：0：待抽查 1：已抽查
     */
    private Integer verifyStatus;

    /**
     * 订单类型：0：购汇订单 1：结汇订单
     */
    private Integer orderType;


    /**
     * 商户号
     */
    private Long memberId;


}
