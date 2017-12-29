package com.baofu.cbpayservice.common.util;


import com.baofu.cbpayservice.common.enums.CmdStatusEnums;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;

import java.text.MessageFormat;


/**
 * dispatch状态工具类
 * <p>
 * 1、判断命令是否结束
 * 2、根据状态获取枚举类
 * </p>
 * User: 香克斯 Date: 2016/07/06 ProjectName: system-dispatch Version: 1.0
 */

public class StatusUtils {

    private static final String UN_SUPPORT_STATUS = "非法的[{0}]状态[{1}]";

    private static final String MSG = "命令";

    /**
     * 1、判断命令是否结束
     *
     * @param status 状态值
     * @return TRUE|FALSE
     */
    public static boolean isCmdEnd(String status) {

        CmdStatusEnums enums = getCmdStatusEnum(status);

        switch (enums) {
            case Initial:
            case Wait:
                return false;
            case Success:
            case Processing:
            case Failure:
                return true;
            default:
                throw new BizServiceException(CommonErrorCode.VALUE_NOT_SUPPORT,
                        MessageFormat.format(UN_SUPPORT_STATUS, MSG, status));
        }
    }

    /**
     * 2、根据状态获取枚举类
     *
     * @param status 状态值
     * @return CmdStatusEnums
     */
    public static CmdStatusEnums getCmdStatusEnum(String status) {
        CmdStatusEnums enums = CmdStatusEnums.getEnumsByCode(status);

        if (enums == null) {
            throw new BizServiceException(CommonErrorCode.VALUE_NOT_SUPPORT,
                    MessageFormat.format(UN_SUPPORT_STATUS, MSG, status));
        }

        return enums;
    }

}

