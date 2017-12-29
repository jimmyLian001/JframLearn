package com.baofu.cbpayservice.facade.models.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 二要素认证订单信息
 * User: 香克斯 Date:2016/9/23 ProjectName: cbpayservice Version: 1.0
 */
@Setter
@Getter
@ToString
public class ServeIdCardResDto implements Serializable {

    /**
     * ID编号
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
     * 记录版本号
     */
    private Long version;

    /**
     * 备注
     */
    private String remark;

    /**
     * 身份证号
     */
    private String idCardNo;

    /**
     * 身份证姓名
     */
    private String idCardName;

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
     * 通知清算(0-未处理,1-成功)
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

    /**
     * 身份证照片
     */
    private String idCardPhoto;
}