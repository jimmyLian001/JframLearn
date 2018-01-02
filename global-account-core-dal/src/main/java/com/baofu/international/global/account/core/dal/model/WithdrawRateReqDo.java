package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Created by yimo on 2017/11/23.
 */
@Getter
@Setter
@ToString
public class WithdrawRateReqDo {

    private Long recordId;
    /**
     * 用户号
     */
    private String userNo;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 银行账户币种
     */
    private String bankAccCcy;

    /**
     * 当前页
     */
    private int pageNo;

    /**
     * 每页显示行数
     */
    private int pageSize = 20;

    /**
     * 费率设置状态
     */
    private long rateSetState;

    /**
     * 费率
     */
    private String rate;

    /**
     * 日志ID
     */
    private String tranceLogID;

    private String createBy;

    private String updateBy;
}
