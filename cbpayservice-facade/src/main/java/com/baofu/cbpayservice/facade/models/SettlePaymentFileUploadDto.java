package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 运营线下上传结汇汇款凭证请求对象
 * <p/>
 * User: lian zd Date:2017/9/20 ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class SettlePaymentFileUploadDto implements Serializable {

    /**
     * 商户编号
     */
    @NotNull(message = "商户编号不能为空")
    private Long memberId;

    /**
     * 汇入汇款编号
     */
    @NotBlank(message = "汇入汇款编号不能为空")
    private String incomeNo;

    /**
     * 申请编号
     */
    @NotNull(message = "申请编号不能为空")
    private Long applyNo;

    /**
     * 汇款凭证dfsId（商户前台传入）
     */
    @NotNull(message = "汇款凭证dfsId不能为空")
    private Long paymentFileId;
}
