package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 描述：内卡返回自己下发结果
 * User: feng_jiang Date:2017/11/ ProjectName: globalaccount-core Version: 1.0
 */
@Getter
@Setter
@ToString
public class UserDistributeApplyRespBo implements Serializable {

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
