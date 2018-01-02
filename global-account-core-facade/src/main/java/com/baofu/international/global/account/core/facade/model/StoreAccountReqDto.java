package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * description:
 * <p/>
 * Created by kangzhiguang on 2017/11/08 0006 ProjectName：account-core
 */
@Getter
@Setter
@ToString
public class StoreAccountReqDto implements Serializable {

    /**
     * 用户号
     */
    @NotNull(message = "用户号不能为空")
    private Long useNo;

    /**
     * 用户类型 1-个人  2- 企业
     */
    private Integer userType;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 账户
     */
    private String accountNo;

    /**
     * 店铺状态
     */
    private Integer storeState;

    /**
     * 账户币种
     */
    private String ccy;
}
