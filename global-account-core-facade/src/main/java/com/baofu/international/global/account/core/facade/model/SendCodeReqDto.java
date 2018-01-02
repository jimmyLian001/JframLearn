package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * description:
 * <p/>
 * Created by liy on 2017/11/6 0006 ProjectName：account-core
 */
@Getter
@Setter
@ToString
public class SendCodeReqDto implements Serializable {

    /**
     * 手机号或邮箱
     */
    @NotNull(message = "手机号或邮箱不能为空")
    private String param;

    /**
     * 内容(验证码用#code#替换)
     */
    @NotNull(message = "内容不能为空")
    private String content;

    /**
     * redis
     */
    @NotNull(message = "redisKey不能为空")
    private String redisKey;

    /**
     * redis超时时间
     */
    private Long timeOut;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 邮件标题
     */
    private String emailTitle;
}
