package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 渠道提现明细管理后台查询，响应Dto
 *
 * @author dxy  on 2017/11/21.
 */
@Getter
@Setter
@ToString
public class WithdrawDistributeRespDto implements Serializable {

    private static final long serialVersionUID = 9080284733461535484L;

    /**
     * 提现申请号
     */
    private String withdrawId;

    /**
     * 用户号
     */
    private String userNo;

    /**
     * 商户名称
     */
    private String userName;

    /**
     * 姓名
     */
    private String bankCardHolder;

    /**
     * 卡号
     */
    private String bankCardNo;

    /**
     * 下发金额
     */
    private BigDecimal distributeAmount;

    /**
     * 提现状态　：0-待提现；1-提现处理中，2-提现成功，3-提现失败(宝付转账)
     */
    private String withdrawStatus;

    /**
     * 汇款流水号
     */
    private String batchNo;

    /**
     * 下发订单号
     */
    private String distributeId;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 完成时间
     */
    private Date finishTime;

}
