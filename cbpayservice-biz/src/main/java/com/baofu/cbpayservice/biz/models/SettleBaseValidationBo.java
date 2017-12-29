package com.baofu.cbpayservice.biz.models;

import com.baofu.cbpayservice.common.constants.Constants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * Excel 第二行内容校验
 * <p>
 * User: 不良人 Date:2017/4/5 ProjectName: cbpayservice Version: 1.0
 */

@Getter
@Setter
@ToString
public class SettleBaseValidationBo {

    /**
     * 商户名称
     */
    @NotBlank(message = "商户名称不能为空")
    private String memberName;

    /**
     * 商户编号
     */
    @NotBlank(message = "商户编号不能为空")
    @Pattern(regexp = Constants.NUMBER_REG, message = "商户编号只能填写数字")
    private String memberId;

    /**
     * 文件版本号
     */
    private String version;

}
