package com.baofu.international.global.account.core.dal.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 更新用户认证状态请求对象
 * <p>
 * description: 更新用户认证状态请求对象
 * <p/>
 * Created by  陶伟超 on 2017/11/7 ProjectName：account-core
 */
@Getter
@Setter
@ToString
public class EditUserReqDo {

    /**
     * 会员号
     */
    private String userInfoNo;

    /**
     * 认证失败理由
     */
    private String failReason;

    /**
     * 认证状态：0-未认证；1-认证中 2-已认证 默认为未认证
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
