package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户登录密码修改，重置BO
 * <p>
 * User: 康志光 Date: 2017/11/04 Version: 1.0
 * </p>
 */
@Setter
@Getter
@ToString
public class UserAnswerReqBo {

    /**
     * 登录号
     */
    private String loginNo;


    /**
     * 答案编号
     */
    private Integer questionSequence;

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 安全问题编号
     */
    private Long questionNo;

    /**
     * 答案
     */
    private String answer;

    /**
     * 更新人
     */
    private String updateBy;


}