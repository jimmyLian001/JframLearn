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
 * 收款账户开户信息对象
 * <p>
 * User: lian zd Date:2017/9/8 ProjectName: cbpayservice Version: 1.0
 */
@Setter
@Getter
@ToString
public class CbPayEstablishAccountDto implements Serializable {

    /**
     * 商户编号
     */
    @NotNull(message = "商户编号不能为空")
    private Long memberId;

    /**
     * 联系人姓
     */
    @NotBlank(message = "联系人姓不能为空")
    private String firstName;

    /**
     * 联系人名
     */
    @NotBlank(message = "联系人名不能为空")
    private String lastName;

    /**
     * 法人身份证号码
     */
    @NotBlank(message = "法人身份证号码不能为空")
    private String idNo;

    /**
     * 法人出生日期
     */
    @NotBlank(message = "法人出生日期不能为空")
    @Pattern(regexp = Constants.YYYY_MM_DD, message = "法人出生日期填写异常")
    private String birthDay;

    /**
     * 证件照DFS编号
     */
    @NotNull(message = "证件照DFS编号不能为空")
    private Long passportDfsId;

    /**
     * 证件类型
     */
    @NotNull(message = "证件类型不能为空")
    private String idType;

    /**
     * 营业执照DFS编号
     */
    @NotNull(message = "营业执照DFS编号不能为空")
    private Long licenseDfsId;

    /**
     * 企业所在城市
     */
    @NotNull(message = "企业所在城市不能为空")
    private String city;

    /**
     * 企业所在省份
     */
    @NotNull(message = "企业所在省份不能为空")
    private String province;

    /**
     * 企业地址
     */
    @NotNull(message = "企业地址不能为空")
    private String address;

    /**
     * 邮编
     */
    @NotNull(message = "邮编不能为空")
    private String postalCode;

    /**
     * 联系方式
     */
    @NotNull(message = "联系方式不能为空")
    private String phoneNumber;

    /**
     * 商户类型
     */
    @NotNull(message = "商户类型不能为空")
    private String memberType;

    /**
     * 商户英文名
     */
    @NotNull(message = "商户英文名不能为空")
    private String companyName;

    /**
     * 商户网址
     */
    @NotNull(message = "商户网址不能为空")
    private String accountHolderWebsite;

}
