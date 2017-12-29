package com.baofu.cbpayservice.facade.models;

import com.baofu.cbpayservice.common.constants.Constants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 收款账户人工匹配参数
 * User: feng_jiang
 * Date:2017/10/21
 * ProjectName: cbPayService
 * Version: 1.0
 */
@Getter
@Setter
@ToString
public class AccountArtificialMatchDto implements Serializable {

    /**
     * 汇款流水号
     */
    @NotNull(message = "商户汇款流水号不能为空")
    private String orderId;

    /**
     * 汇入汇款编号
     */
    @NotNull(message = "汇入汇款编号不能为空")
    private String incomeNo;

    /**
     * 渠道
     */
    @NotNull(message = "渠道不能为空")
    private String  channelId;

    /**
     * 商户编号
     */
    @NotNull(message = "商户编号不能为空")
    private String memberId;
}
