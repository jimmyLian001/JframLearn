package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 国际收支申报信息
 * User: 香克斯  Date: 2016/10/28 ProjectName: cbpayservice-dispatch Version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class FiCbPayChannelGroupDo extends BaseDo {

    /**
     * 记录编号
     */
    private Long recordId;

    /**
     * 币种
     */
    private String ccy;

    /**
     * 商户组编号
     */
    private Long groupId;

    /**
     * 渠道编号
     */
    private Long channelId;

    /**
     * 渠道类型：0-购付汇渠道，1-收款渠道
     */
    private Integer channelType;
}