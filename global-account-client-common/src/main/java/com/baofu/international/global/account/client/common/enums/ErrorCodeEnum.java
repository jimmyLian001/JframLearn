package com.baofu.international.global.account.client.common.enums;

import com.system.commons.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误码枚举类
 * <p>
 * 1.设置错误描述信息
 * </p>
 *
 * @author : 不良人
 * @version : 1.0.0
 * @date : 2017/12/4
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum implements ErrorCode {

    /**
     * 支付密码错误
     */
    ERROR_CODE_800001("800001", "支付密码错误"),

    /**
     * 账户无可用余额
     */
    ERROR_CODE_800002("800002", "账户无可用余额"),

    /**
     * 发送验证码失败
     */
    ERROR_CODE_800003("800003", "发送验证码失败"),

    /**
     * 身份证正面照超过2M
     */
    ERROR_CODE_800004("800004", "身份证正面照超过2M"),

    /**
     * 身份证反面照超过2M
     */
    ERROR_CODE_800005("800005", "身份证反面照超过2M"),

    /**
     * 营业执照超过2M
     */
    ERROR_CODE_800006("800006", "营业执照超过2M"),

    /**
     * 税务登记证照超过2M
     */
    ERROR_CODE_800007("800007", "税务登记证照超过2M"),

    /**
     * 组织机构代码证照超过2M
     */
    ERROR_CODE_800008("800008", "组织机构代码证照超过2M"),

    /**
     * 代付API申请返回密文解密失败
     */
    ERROR_CODE_800009("800009", "代付API申请返回密文解密失败"),
    ;

    /**
     * 错误码
     */
    private String errorCode;


    /**
     * 异常描述
     */
    private String errorDesc;

    /**
     * 设置错误描述信息
     *
     * @param errorMsg 错误信息
     */
    @Override
    public void setErrorDesc(String errorMsg) {
        this.errorDesc = errorMsg;
    }
}
