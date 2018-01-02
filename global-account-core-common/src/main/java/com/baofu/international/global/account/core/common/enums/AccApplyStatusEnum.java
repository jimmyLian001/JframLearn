package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * <p>
 * 1、收款账户开通状态枚举
 * </p>
 * ProjectName:global-account-core
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/18
 */
@Getter
@ToString
@AllArgsConstructor
public enum AccApplyStatusEnum {

    /**
     * 待处理
     */
    INIT(0, "待处理"),
    /**
     * 处理中
     */
    HANDLING(1, "处理中"),
    /**
     * 开户成功
     */
    SUCCESS(2, "开户成功"),
    /**
     * 开户失败
     */
    FAIL(3, "开户失败"),
    /**
     * 账户关闭
     */
    CLOSE(4, "账户关闭");

    /**
     * key
     */
    private int code;

    /**
     * 描述
     */
    private String desc;
}
