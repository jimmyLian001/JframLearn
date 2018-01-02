package com.baofu.international.global.account.core.facade.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@ToString
public class UserBankCardInfoDto implements Serializable {

    /**
     * ID主键
     */
    private Long id;

    /**
     * 记录编号
     */
    private Long recordNo;

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 账户类型 1-对公，2-对私
     */
    private Integer accType;

    /**
     * 银行卡持有人
     */
    private String cardHolder;

    /**
     * 银行账号
     */
    private String cardNo;

    /**
     * 开户行名称
     */
    private String bankName;

    /**
     * 银行卡类型
     */
    private Integer cardType;

    /**
     * 状态 -1-冻结 0-待激活 1-已激活 2-失效
     */
    private Integer state;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    private String updateBy;

    /**
     * 更新人
     */
    private Date updateAt;

    /**
     * 银行简称
     */
    private String bankCode;
}