package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 功能：内卡提现反馈参数
 *
 * @author : feng_jiang
 * @version : Version:1.0.0  date: 2017/12/19
 **/
@Getter
@Setter
@ToString
public class UserDistributeApiDto extends BaseDTO {

    /**
     * 商户订单号
     */
    private String transNo;

    /**
     * 交易处理状态
     */
    private Long state;

    /**
     * 备注（错误信息）
     */
    private String transRemark;

}
