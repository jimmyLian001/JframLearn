package com.baofu.cbpayservice.biz.models;

import com.baofu.cbpayservice.common.constants.Constants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * 收款账户人工匹配参数
 * User: feng_jiang
 * Date:2017/10/19
 * ProjectName: cbPayService
 * Version: 1.0
 */
@Getter
@Setter
@ToString
public class AccountArtificialMatchBo {

    /**
     * 汇款流水号
     */
    @NotBlank(message = "商户汇款流水号不能为空")
    @Pattern(regexp = Constants.NO_SPACE, message = "商户汇款流水号填写异常")
    @Length(max = 50, message = "商户汇款流水号长度异常")
    private String orderId;

    /**
     * 汇入汇款编号
     */
    @NotBlank(message = "汇入汇款编号不能为空")
    @Pattern(regexp = Constants.NO_SPACE, message = "汇入汇款编号填写异常")
    private String incomeNo;

    /**
     * 渠道
     */
    @NotBlank(message = "渠道不能为空")
    private String channelId;

    /**
     * 商户编号
     */
    @NotBlank(message = "商户编号不能为空")
    private String memberId;
}
