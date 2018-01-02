package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * User: yangjian  Date: 2017-11-04 ProjectName:  Version: 1.0
 */
@Getter
@Setter
@ToString
public class ApplyAccountRepDto implements Serializable {

    private static final long serialVersionUID = -6912262384042759030L;
    /**
     * 主体名称
     */
    private String name;
    /**
     * 主体英文名
     */
    private String enName;
    /**
     * 法人账号
     */
    private String legalNo;
    /**
     * 授权状态 隐藏
     */
    private Integer realNameStatus;
    /**
     * 开户主体编号 隐藏
     */
    private Long userInfoNo;
    /**
     * 资质编号
     */
    private Long qualifiedNo;
}
