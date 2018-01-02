package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 渠道开户返回结果对象转换
 * <p>
 * 1、渠道开户返回结果对象转换
 * </p>
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/11/29
 */
@Getter
@Setter
@ToString
public class ChannelNotifyApplyAccountBo {

    /**
     * 申请编号
     */
    private Long applyNo;

    /**
     * 返回编码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 路由编号
     */
    private String routingNumber;

    /**
     * 银行账户
     */
    private String bankAccNo;

    /**
     * 银行账户名
     */
    private String bankAccName;

    /**
     * 成功标识
     */
    private Integer succFlag;

    /**
     * 银行虚拟编号
     */
    private String walletId;
}
