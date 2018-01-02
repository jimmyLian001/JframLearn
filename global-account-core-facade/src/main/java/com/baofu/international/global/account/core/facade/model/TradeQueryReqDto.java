package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 莫小阳 on 2017/11/7.
 */
@Getter
@Setter
@ToString
public class TradeQueryReqDto implements Serializable {


    private static final long serialVersionUID = -5043811668946534831L;

    /**
     * 用户号
     */
    @NotNull(message = "用户号不能为空")
    private Long userNo;

    /**
     * 1：入账 2：出账  3：提现查询
     */
    private String flag;

    /**
     * 当前页
     */
    @NotNull(message = "当前页不能为空")
    private int currPageNum;

    /**
     * 每页记录数
     */
    @NotNull(message = "每页记录数不能为空")
    private int pageSize;


    /**
     * 账户类型
     */
    private String accountType;

    /**
     * 账户
     */
    private Long accountNo;

    /**
     * 状态
     */
    private String state;

    /**
     * 交易开始时间
     */
    private String beginTime;

    /**
     * 交易结束时间
     */
    private String endTime;


}
