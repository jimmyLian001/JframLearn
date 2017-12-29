package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 提现文件上传参数对象
 * <p>
 * User: wanght Date:2017/5/11 ProjectName: cbpayservice Version: 1.0
 */
@Setter
@Getter
@ToString
public class CbPaySumFileDto implements Serializable {

    /**
     * 商户编号
     */
    @NotNull(message = "商户编号不能为空")
    private Long memberId;

    /**
     * 文件名称
     */
    @NotBlank(message = "开始时间不能为空")
    private String beginTime;

    /**
     * 文件名称
     */
    @NotBlank(message = "结束时间不能为空")
    private String endTime;

    /**
     * 操作人
     */
    @NotBlank(message = "操作人不能为空")
    private String createBy;
}
