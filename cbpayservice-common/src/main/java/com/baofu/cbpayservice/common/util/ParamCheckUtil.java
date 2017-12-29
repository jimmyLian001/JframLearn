package com.baofu.cbpayservice.common.util;

import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import com.system.commons.utils.ParamValidate;

import java.util.List;

/**
 * 参数数据校验
 */
public final class ParamCheckUtil {
    private ParamCheckUtil() {
    }

    /**
     * 校验额外信息
     *
     * @param <T>  多个信息集合
     * @param list 多个信息集合
     */
    public static <T> void listCheck(List<T> list) {
        if (list == null || list.isEmpty() ) {
            throw new BizServiceException(CommonErrorCode.PARAMETER_VALID_NOT_PASS, "参数集合为空");
        }
        for (T t : list) {
            ParamValidate.validateParams(t);
        }
    }
}
