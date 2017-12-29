package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 通知商户宝付订单号集合
 * User: 香克斯 Date:2016/10/28 ProjectName: cbpayservice Version: 1.0
 */
@Setter
@Getter
@ToString
public class CbPayOrderNotifyReqDto implements Serializable {

    /**
     * 宝付订单号集合
     */
    @NotNull(message = "宝付订单号集合")
    private List<Long> orderId;

    /**
     * 操作人
     */
    @NotBlank(message = "操作人不能为空")
    private String operationBy;
}
