package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 批次关系查询参数
 * User: feng_jiang
 * Date:2017/9/9
 * ProjectName: cbPayService
 * Version: 1.0
 */
@Getter
@Setter
@ToString
public class AccountRelationBo {
    /**
     * 业务类型
     */
    private Integer bizType;

    /**
     * 批次号
     */
    private Long batchNo;

    /**
     * 明细编号
     */
    private Long detailId;

}
