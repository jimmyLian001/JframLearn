package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 渠道成本查询请求信息
 * <p>
 * User: wanght Date:2017/10/20 ProjectName: cbpay-service Version: 1.0
 */
@Setter
@Getter
@ToString
public class CbPaySelectChannelFeePageBo implements Serializable {

    /**
     * 渠道id
     */
    private Long channelId;

    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 分页码
     */
    private int pageNo;

    /**
     * 每页条数
     */
    private int pageSize;
}
