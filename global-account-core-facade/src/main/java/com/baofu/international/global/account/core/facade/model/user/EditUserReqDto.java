package com.baofu.international.global.account.core.facade.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户认证状态请求对象
 * <p>
 * description: 更新用户认证状态请求对象
 * <p/>
 * Created by  陶伟超 on 2017/11/7 ProjectName：account-core
 */
@Getter
@Setter
@ToString
public class EditUserReqDto implements Serializable {

    /**
     * 会员号
     */
    @NotNull(message = "不能为空")
    private String userInfoNo;

    /**
     * 认证失败理由
     */
    private String failReason;

    /**
     * 认证状态
     */
    private String authStatus;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 解锁登录状态
     */
    private String userState;

    /**
     * 登录密码错误次数
     */
    private String loginPwdErrorNum;


}
