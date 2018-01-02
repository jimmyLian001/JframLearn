package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户登录密码修改，重置DTO
 * <p>
 * User: 康志光 Date: 2017/11/04 Version: 1.0
 * </p>
 */
@Setter
@Getter
@ToString
public class UserAnswerRespDTO extends BaseDTO {

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 安全问题编号
     */
    private Long questionNo;

    /**
     * 安全问题
     */
    private String question;

    /**
     * 安全问题答案
     */
    private String answer;

}
