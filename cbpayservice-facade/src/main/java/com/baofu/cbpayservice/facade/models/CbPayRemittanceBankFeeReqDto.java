package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 跨境付汇到账手续费更新请求参数信息
 * User: dxy Date:2017/3/23 ProjectName: asias-icpaygate Version: 1.0
 */
@Setter
@Getter
@ToString
public class CbPayRemittanceBankFeeReqDto implements Serializable {

    /**
     * 商户编号
     */
    @NotNull(message = "商户编号不能为空")
    private Long memberId;

    /**
     * 汇款批次号
     */
    @NotBlank(message = "汇款批次号不能为空")
    private String batchNo;

    /**
     * 更新人员
     */
    @NotBlank(message = "更新人员不能为空")
    private String updateBy;

    /**
     * 到账手续费
     */
    private BigDecimal bankFee;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 手续费审核状态
     */
    private int bankFeeStatus;

    /**
     * 凭证dfs文件id
     */
    private Long receiptId;

}
