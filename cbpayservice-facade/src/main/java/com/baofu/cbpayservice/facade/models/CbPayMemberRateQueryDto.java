package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * User: yangjian  Date: 2017-05-15 ProjectName:  Version: 1.0
 */
@Getter
@Setter
@ToString
public class CbPayMemberRateQueryDto implements Serializable {

    private static final long serialVersionUID = -3856911310944034113L;

    /**
     * 记录编号
     */
    @NotNull(message = "记录编号不能为空")
    private Long recordId;

}
