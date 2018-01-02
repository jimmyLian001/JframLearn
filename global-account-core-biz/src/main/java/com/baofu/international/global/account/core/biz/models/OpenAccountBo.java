package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ${desc}
 * <p>
 * 1.
 * </p>
 *
 * @author : yangjian
 * @version : 1.0.0
 * @date: 2017-12-24
 */
@Getter
@Setter
@ToString
public class OpenAccountBo {

    /**
     * 代理商商户号
     */
    private Long agentNo;

    /**
     * 用户号
     */
    private Long userNo;
}
