package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 测试类
 * User: 欧西涛  Date: 2015/10/11 ProjectName: globalaccount Version: 1.0
 */
@Setter
@Getter
@ToString
public class UserInfoReqDTO extends BaseDTO {

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户年龄
     */
    private Integer userAge;
}
