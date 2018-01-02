package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 验证码响应
 * <p/>
 * Created by kangzhiguang on 2017/11/6  ProjectName：account-core
 */
@Getter
@Setter
@ToString
public class SendCodeRespBo {

    /**
     * 手机号或者邮箱
     */
    private String sendAddress;

    /**
     * 发送地址类型 0 - 非手机和邮箱  1 - 手机   2 - 邮箱
     */
    private Integer addressType;

    /**
     * 发送成功标识 true-成功  false-失败
     */
    private Boolean sendFlag;

    /**
     * 失败提示
     */
    private String errorMsg;

}
