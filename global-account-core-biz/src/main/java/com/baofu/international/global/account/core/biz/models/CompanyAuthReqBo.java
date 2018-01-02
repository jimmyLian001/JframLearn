package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 1、方法描述
 * </p>
 * User: 香克斯  Date: 2017/11/4 ProjectName:account-core  Version: 1.0
 */
@Setter
@Getter
@ToString
public class CompanyAuthReqBo {
    /**
     * 商户订单号
     */
    private Long authApplyNo;
    /**
     * 企业名称
     */
    private String entName;
    /**
     * 法人身份证号码
     */
    private String frCid;
    /**
     * 法人姓名
     */
    private String frName;

}
