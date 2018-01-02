package com.baofu.international.global.account.core.facade.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户资质响应对象
 * <p>
 * description: 用户资质响应对象
 * <p/>
 * Created by  陶伟超 on 2017/11/7 ProjectName：account-core
 */
@Getter
@Setter
@ToString
public class UserQualifiedDto implements Serializable {

    /**
     * 用户号
     */
    private String userNo;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 实名状态（0-未认证；1-认证中 2-已认证 默认为未认证）
     */
    private Integer realnameStatus;

    /**
     * 申请编号
     */
    private Long userInfoNo;

    /**
     * 资质Id
     */
    private Long qualifiedNo;

}
