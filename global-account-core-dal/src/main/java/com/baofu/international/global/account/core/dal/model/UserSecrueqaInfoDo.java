package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class UserSecrueqaInfoDo extends BaseDo {
    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 问题序号
     */
    private Integer questionSequence;

    /**
     * 问题编号
     */
    private Long questionNo;

    /**
     * 答案
     */
    private String answer;

    /**
     * 答案状态 0-失效 1- 有效
     */
    private Integer state;
}