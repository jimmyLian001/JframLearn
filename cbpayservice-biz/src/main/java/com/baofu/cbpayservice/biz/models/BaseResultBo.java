package com.baofu.cbpayservice.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 返回商户结果信息
 * User: 香克斯 Date:2016/11/2 ProjectName: cbpaygate Version: 1.0
 */
@Setter
@Getter
@ToString
public class BaseResultBo implements Serializable {

    /**
     * 成功标识
     */
    private Boolean success = Boolean.TRUE;

    /**
     * 错误描述
     */
    private String errorMsg = "成功";

    /**
     * 返回结果信息
     */
    private String result;

    /**
     * 商户编号
     */
    private Long memberId;

    /**
     * 终端编号
     */
    private Integer terminalId;

    /**
     * 设置成功返回信息
     *
     * @param result 成功返回对象信息
     * @return 返回商户接口对象
     */
    public static BaseResultBo setSuccessResult(String result, Long memberId, Integer terminalId) {
        BaseResultBo baseResult = new BaseResultBo();
        baseResult.setResult(result);
        baseResult.setMemberId(memberId);
        baseResult.setTerminalId(terminalId);
        return baseResult;
    }

    /**
     * 设置错误信息
     *
     * @param errorMsg 错误描述
     * @return 返回商户接口对象
     */
    public static BaseResultBo setFailMsg(String errorMsg) {
        BaseResultBo baseResult = new BaseResultBo();
        baseResult.setErrorMsg(errorMsg);
        baseResult.setSuccess(Boolean.FALSE);
        return baseResult;
    }
}
