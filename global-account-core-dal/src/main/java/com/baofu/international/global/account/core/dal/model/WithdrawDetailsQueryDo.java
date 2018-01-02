package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 用户提现明细管理后台查询Do
 *
 * @author dxy  on 2017/11/21.
 */
@Getter
@Setter
@ToString
public class WithdrawDetailsQueryDo {

    /**
     * 当前页
     */
    private int currentPage;

    /**
     * 当前页显示条数
     */
    private int pageCount;

    /**
     * 开始日期
     */
    private Date startDate;

    /**
     * 结束日期
     */
    private Date endDate;

    /**
     * 用户号
     */
    private String userNo;

    /**
     * 提现申请号
     */
    private String withdrawId;

    /**
     * 币种
     */
    private String ccy;

    /**
     * 提现状态　：0-待提现；1-提现处理中，2-提现成功，3-提现失败(宝付转账)
     */
    private String withdrawStatus;

    /**
     * 转帐状态　:0-待转账；1-转账处理中，2-转账成功，3-转账失败
     */
    private String transferStatus;

    /**
     * 汇款流水号
     */
    private String batchNo;

    /**
     * 渠道
     */
    private String channel;
}
