package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class AccQualifiedDo extends BaseDo {
    /**
     * 用户号
     */
    private Long userNo;

    /**
     * 用户信息编号
     */
    private Long userInfoNo;

    /**
     * 英文名
     */
    private Long qualifiedNo;
}