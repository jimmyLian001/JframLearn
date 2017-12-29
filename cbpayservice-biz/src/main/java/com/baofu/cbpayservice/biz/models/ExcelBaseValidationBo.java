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
public class ExcelBaseValidationBo {

    /**
     * 商户编号
     */
    @NotBlank(message = "商户编号不能为空")
    @Pattern(regexp = Constants.NUMBER_REG, message = "商户编号只能填写数字")
    private String memberId;

    /**
     * 版本号
     */
    @NotBlank(message = "版本号不能为空")
    @Pattern(regexp = "1.0", message = "版本号只能填写1.0")
    private String version;

    /**
     * 行业类型
     */
    @NotBlank(message = "行业类型不能为空")
    @Pattern(regexp = "(01)|(02)|(03)|(04)|(05)", message = "行业类型填写不正确")
    private String careerType;

}
