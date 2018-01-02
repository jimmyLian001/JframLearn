package com.baofu.international.global.account.core.facade.model;

import com.system.commons.result.PageReqDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户提现明细管理后台查询，请求Dto
 *
 * @author dxy  on 2017/11/21.
 */

@Getter
@Setter
@ToString
public class WithdrawDetailsReqDto extends PageReqDTO implements Serializable {

    private static final long serialVersionUID = 9080294733361536485L;

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
