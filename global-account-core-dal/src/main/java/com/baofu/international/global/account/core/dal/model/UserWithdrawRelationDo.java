package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class UserWithdrawRelationDo extends BaseDo {
    /**
     * 关系唯一编号
     */
    private Long relationId;

    /**
     * 业务类型：1-批量提现
     */
    private int bizType;

    /**
     * 提现批次号
     */
    private Long withdrawBatchId;

    /**
     * 提现明细编号
     */
    private Long withdrawId;
}