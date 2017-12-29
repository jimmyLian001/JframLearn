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
public class ChannelGroupModifyReqDto implements Serializable {

    /**
     * 记录编号
     */
    @NotNull(message = "记录编号不能为空")
    private Long recordId;

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
     * 更新人
     */
    @NotBlank(message = "更新人不能为空")
    private String updateBy;

}
