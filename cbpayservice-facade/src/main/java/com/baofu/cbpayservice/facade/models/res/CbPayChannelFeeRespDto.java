package com.baofu.cbpayservice.facade.models.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 渠道成本分页查询响应信息
 * User: wanght Date:2017/10/20 ProjectName: cbpay-service Version: 1.0
 */
@Setter
@Getter
@ToString
public class CbPayChannelFeeRespDto implements Serializable {

    /**
     * 记录编号
     */
    private Long recordId;

    /**
     * 汇款渠道
     */
    private Long channelId;

    /**
     * 国外固定金额手续费
     */
    private BigDecimal abroadFixedMoney;

    /**
     * 港澳台固定金额手续费
     */
    private BigDecimal fixedMoney;

    /**
     * 计费类型,RATIO：比率
     */
    private String chargeType;

    /**
     * 计费值
     */
    private BigDecimal chargeValue;

    /**
     * 最低手续费
     */
    private BigDecimal minMoney;

    /**
     * 最高手续费
     */
    private BigDecimal maxMoney;

    /**
     * 状态：INIT:初始，TRUE:启用，FALSE:失效
     */
    private String status;

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
     * 修改时间
     */
    private Date updateAt;

    /**
     * 修改人
     */
    private String updateBy;

}
