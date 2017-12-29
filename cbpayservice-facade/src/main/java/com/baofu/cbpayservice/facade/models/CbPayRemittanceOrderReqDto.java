package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 跨境人民币汇款请求参数信息
 * User: wanght Date:2016/11/10 ProjectName: asias-icpaygate Version: 1.0
 */
@Setter
@Getter
@ToString
public class CbPayRemittanceOrderReqDto implements Serializable {

    /**
     * 商户编号
     */
    @NotNull(message = "商户编号不能为空")
    private Long memberId;

    /**
     * 文件批次号不能为空
     */
    private List<Long> batchNoList;

    /**
     * 创建人
     */
    @NotBlank(message = "创建人不能为空")
    private String create_by;

    /**
     * 更新人
     */
    @NotBlank(message = "更新人不能为空")
    private String update_by;

    /**
     * 备注
     */
    private String remark;

    /**
     * 开始时间
     */
    @NotNull(message = "开始时间")
    private String beginTime;

    /**
     * 结束时间
     */
    @NotNull(message = "结束时间")
    private String endTime;

    /**
     * 币种
     */
    @NotBlank(message = "币种不能为空")
    private String targetCcy;

    /**
     * 购汇方式
     */
    @NotBlank(message = "定额类型不能为空")
    private String exchangeType;
}
