package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 渠道分组新增请求参数信息
 * User: 香克斯 Date:2017/4/24 ProjectName: cbpay-service Version: 1.0
 */
@Setter
@Getter
@ToString
public class ChannelGroupAddReqDto implements Serializable {

    /**
     * 币种
     */
    @NotBlank(message = "币种信息不能为空")
    private String ccy;

    /**
     * 商户组编号
     */
    @NotNull(message = "商户分组编号不能为空")
    private Long groupId;

    /**
     * 渠道编号
     */
    @NotNull(message = "渠道编号不能为空")
    private Long channelId;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 创建人
     */
    @NotBlank(message = "创建人不能为空")
    private String createBy;

    /**
     * 渠道类型：0-购付汇渠道，1-收款渠道
     */
    private Integer channelType;

}
