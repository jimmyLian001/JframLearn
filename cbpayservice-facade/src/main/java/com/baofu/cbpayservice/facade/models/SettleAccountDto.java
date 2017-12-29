package com.baofu.cbpayservice.facade.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 结汇文件上传参数
 * <p>
 * User: lian zd Date:2017/7/28  ProjectName: cbpayservice Version: 1.0
 */
@Getter
@Setter
@ToString
public class SettleAccountDto implements Serializable {

    /**
     * 商户编号
     */
    @NotNull(message = "商户编号不能为空")
    private Long memberId;

    /**
     * 汇款人名称
     */
    @NotNull(message = "汇款人名称不能为空")
    private String incomeAccountName;

    /**
     * 汇款人账户
     */
    @NotNull(message = "汇款人账户不能为空")
    private String incomeAccountNo;

    /**
     * 汇款人常驻国家
     */
    @NotNull(message = "汇款人常驻国家不能为空")
    private String incomeCountry;

    /**
     * 操作人
     */
    @NotBlank(message = "操作人不能为空")
    private String createBy;
}
