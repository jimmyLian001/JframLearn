package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 莫小阳  on 2017/11/21.
 */
@Setter
@Getter
public class UserStoreInfoRespDo {

    /**
     * 商户境外银行账号
     */
    private String userAccNo;

    /**
     * 商户名称
     */
    private String storeName;
}
