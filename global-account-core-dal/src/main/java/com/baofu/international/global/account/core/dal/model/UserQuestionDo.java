package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserQuestionDo {

    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 问题序号
     */
    private Byte questionSequence;

    /**
     * 问题编号
     */
    private Long questionNo;

    /**
     * 答案
     */
    private String answer;

    /**
     * 创建时间
     */
    private String question;


}