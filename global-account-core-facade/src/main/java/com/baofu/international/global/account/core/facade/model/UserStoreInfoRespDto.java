package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 商户店铺信息
 *
 * @author 莫小阳  on 2017/11/21.
 */
@Getter
@Setter
@ToString
public class UserStoreInfoRespDto implements Serializable {
    private static final long serialVersionUID = -6494435468826760441L;

    /**
     * 商户境外银行账号
     */
    private String userAccNo;

    /**
     * 商户名称
     */
    private String storeName;

}


