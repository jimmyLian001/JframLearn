package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * User: yangjian  Date: 2017-06-07 ProjectName:cbpay-service   Version: 1.0
 */
@Getter
@Setter
@ToString
public class CbPayCancelOrderDto implements Serializable {

    /**
     * 文件批次号
     */
    @NotNull(message = "文件批次号不能为空")
    private Long fileBatchNo;

    /**
     * 更新人
     */
    @NotBlank(message = "更新人不能为空")
    private String updateBy;
}
