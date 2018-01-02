package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class TSysBankInfoDto implements Serializable {
    /**
     * 发卡行名称
     */
    private String bankName;

    /**
     * 发卡行代码
     */
    private String bankCode;

    /**
     * 银行名称简称
     */
    private String bankAbbreName;

    /**
     * 银行图标
     */
    private String iconCode;

    /**
     * 启动状态：1-启动，2-禁用
     */
    private int state;
}