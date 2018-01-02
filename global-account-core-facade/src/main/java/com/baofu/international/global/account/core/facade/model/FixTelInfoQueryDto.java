package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 用户申请修改注册手机号申请对象信息
 * <p/>
 * User: lian zd Date:2017/11/4 ProjectName: account-core Version:1.0
 */
@Getter
@Setter
@ToString
public class FixTelInfoQueryDto extends BaseDTO {

    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    private String userNo;

}
