package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * description:安全问题 RespDto
 * <p/>
 * Created by liy on 2017/11/7 ProjectName：account-core
 */
@Getter
@Setter
@ToString
public class SysSecrueqaInfoRespDto implements Serializable {

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
