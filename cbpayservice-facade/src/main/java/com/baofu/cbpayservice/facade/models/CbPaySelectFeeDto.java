package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 试算参数信息
 * <p>
 * User: 不良人 Date:2016/11/10 ProjectName: asias-icpaygate Version: 1.0
 */
@Setter
@Getter
@ToString
public class CbPaySelectFeeDto implements Serializable {

    /**
     * 商户编号
     */
    @NotNull(message = "商户编号不能为空")
    private Long memberId;

    /**
     * 文件批次号不能为空
     */
    @NotNull(message = "文件批次号集合不能为空")
    @Size(min = 1,message = "文件批次号集合size不能为0")
    private List<Long> fileBatchNoList;

    /**
     * 商戶备案主体编号
     */
    @NotBlank(message = "商戶备案主体编号不能为空")
    private String entityNo;

    /**
     * 币种
     */
    @NotBlank(message = "币种不能为空")
    private String targetCcy;
}
