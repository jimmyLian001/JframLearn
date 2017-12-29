package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 跨境汇款批次文件总金额校验参数对象
 * <p/>
 * User: lian zd Date:2017/10/20 ProjectName: cbpayservice Version:1.0
 */
@Getter
@Setter
@ToString
public class ProxyTotalAmountCheckDto implements Serializable {

    /**
     * 商户编号
     */
    @NotNull(message = "商户编号不能为空")
    private Long memberId;

    /**
     * 操作人
     */
    @NotBlank(message = "操作人不能为空")
    private String createBy;

    /**
     * 待汇款文件批次集合
     */
    @NotNull(message = "文件批次号集合不能为空")
    @Size(min = 1, message = "文件批次号集合size不能为0")
    private List<Long> fileBatchNoList;

    /**
     * 汇款总金额
     */
    @NotNull(message = "汇款总金额不能为空")
    private BigDecimal totalAmount;

}
