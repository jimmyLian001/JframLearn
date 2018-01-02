package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Created by 莫小阳 on 2017/11/11.
 */
@Setter
@Getter
@ToString
public class UserAccInfoReqDo {

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 币种
     */
    private String ccy;

    /**
     * 渠道方虚拟账户
     */
    private String walletId;

}
