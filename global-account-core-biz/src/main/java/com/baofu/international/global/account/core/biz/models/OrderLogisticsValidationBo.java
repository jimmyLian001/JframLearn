package com.baofu.international.global.account.core.biz.models;

import com.baofu.international.global.account.core.common.constant.Constants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * 描述：提现订单运单信息校验对象
 * <p>
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 */
@Getter
@Setter
@ToString
public class OrderLogisticsValidationBo {

    /**
     * 快递公司编号（物流公司编号）
     */
    @NotBlank(message = "快递公司编号不能为空")
    @Length(max = 32, message = "快递公司编号长度不能超过32位")
    private String logisticsCompanyNumber;

    /**
     * 物流单号（运单号）
     */
    @NotBlank(message = "运单号不能为空")
    @Length(max = 32, message = "运单号长度不能超过32位")
    private String logisticsNumber;

    /**
     * 收货人姓名
     */
    @NotBlank(message = "收货人姓名不能为空")
    @Length(max = 64, message = "收货人姓名长度不能超过64位")
    private String consigneeName;

    /**
     * 收货人联系方式
     */
    @NotBlank(message = "收货人联系方式不能为空")
    @Length(max = 16, message = "收货人联系方式长度不能超过16位")
    private String consigneeContact;

    /**
     * 收货人地址
     */
    @NotBlank(message = "收货人地址不能为空")
    @Length(max = 512, message = "收货人地址长度不能超过512位")
    private String consigneeAddress;

    /**
     * 发货日期
     */
    @NotBlank(message = "发货日期不能为空")
    @Pattern(regexp = Constants.YYYY_MM_DD + "|" + Constants.YYYYMMDD, message = "发货日期填写异常")
    private String deliveryDate;
}
