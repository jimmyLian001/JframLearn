package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class SysSecrueqaInfoDo extends BaseDo {
    /**
     * 问题编号
     */
    private Long questionNo;

    /**
     * 问题
     */
    private String question;

    /**
     * 问题类型：1-问题一，2-问题二，3-问题三
     */
    private Integer questionType;
}