package com.baofu.cbpayservice.common.enums;

import com.system.commons.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * User: yangjian  Date: 2017-05-15 ProjectName:  Version: 1.0
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MemberRateEnum implements ErrorCode {

    /**
     * 已经存在该条记录
     */
    CBPYA_MEMBER_RATE_ERROR_001("CBPYA_MEMBER_RATE_ERROR_001", "会员该币种浮动汇率已存在"),
    CBPYA_MEMBER_RATE_ERROR_002("CBPYA_MEMBER_RATE_ERROR_002", "不存在该条记录无法更新"),;

    /**
     * 错误码
     */
    private String errorCode;


    /**
     * 异常描述
     */
    private String errorDesc;

    /**
     * 根据错误编码获取描述信息
     *
     * @param errorCode 错误编码
     * @return 错误描述
     */
    public static MemberRateEnum queryDesc(String errorCode) {
        for (MemberRateEnum errorCodeEnum : MemberRateEnum.values()) {
            if (errorCodeEnum.getErrorCode().equals(errorCode)) {
                return errorCodeEnum;
            }
        }
        return null;
    }

    /**
     * 设置错误描述信息
     *
     * @param errorMsg 错误信息
     */
    public void setErrorDesc(String errorMsg) {
        this.errorDesc = errorMsg;
    }
}


