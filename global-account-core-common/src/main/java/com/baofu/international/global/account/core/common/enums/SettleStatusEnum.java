package com.baofu.international.global.account.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 1、汇入汇款申请处理状态
 * </p>
 * User: feng_jiang  Date: 2017/11/15 ProjectName:account-client  Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum SettleStatusEnum {

    /**
     * 申请处理中
     */
    APPLY_PROCESS("1", "申请处理中"),

    /**
     * 申请处理失败
     */
    APPLY_FAIL("2", "申请处理失败"),

    /**
     * 申请处理成功
     */
    APPLY_SUCCESS("3", "申请处理成功"),

    /**
     * 结汇处理中
     */
    SETTLE_PROCESS("4", "结汇处理中"),

    /**
     * 结汇失败
     */
    SETTLE_FAIL("5", "结汇失败"),

    /**
     * 结汇成功
     */
    SETTLE_SUCCESS("6", "结汇成功"),

    /**
     * 文件处理失败
     */
    FILE_FAIL("7", "用户上传提现明细文件处理失败");

    /**
     * code
     */
    private String code;

    /**
     * 描述
     */
    private String desc;

    /**
     * 根据代码获取对应描述
     *
     * @param code 代码
     * @return 结汇状态描述
     */
    public static String getEnumsByCode(String code) {
        for (SettleStatusEnum enums : values()) {
            if (code.equals(enums.getCode())) {
                return enums.getDesc();
            }
        }
        return "";
    }
}
