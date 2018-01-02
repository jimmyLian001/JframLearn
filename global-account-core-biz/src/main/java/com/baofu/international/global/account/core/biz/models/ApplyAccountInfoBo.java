package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User: yangjian  Date: 2017-11-04 ProjectName:  Version: 1.0
 */
@Getter
@Setter
@ToString
public class ApplyAccountInfoBo {

    /**
     * 主体名称
     */
    private String name;
    /**
     * 主体英文名
     */
    private String enName;
    /**
     *
     */
    private String legalNo;
    /**
     *
     */
    private Integer realNameStatus;

    /**
     * 开户主体编号
     */
    private Long userInfoNo;

    /**
     * 资质申请编号
     */
    private Long qualifiedNo;
}
