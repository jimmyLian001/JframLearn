package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 基础返回信息
 *
 * @author: 不良人 Date:2017/11/6 ProjectName: account-client Version: 1.0
 */
@Setter
@Getter
@ToString
public class BaseResult {

    /**
     * 返回状态
     */
    private Boolean flag;

    /**
     * 返回描述信息，可以存放异常信息
     */
    private String msg;

    /**
     * 返回描述信息，可以存放异常信息
     */
    private Object object;

    /**
     * 设置成功
     *
     * @param msg 描述信息
     * @return 返回对象
     */
    public static BaseResult setSuccess(String msg) {
        BaseResult result = new BaseResult();
        result.setMsg(msg);
        result.setFlag(Boolean.TRUE);
        return result;
    }

    /**
     * 设置失败
     *
     * @param msg 描述信息
     * @return 返回对象
     */
    public static BaseResult setFail(String msg) {
        BaseResult result = new BaseResult();
        result.setMsg(msg);
        result.setFlag(Boolean.FALSE);
        return result;
    }

    /**
     * 设置成功
     *
     * @param object 描述信息
     * @return 返回对象
     */
    public static BaseResult setSuccessExt(Object object) {
        BaseResult result = new BaseResult();
        result.setFlag(Boolean.TRUE);
        result.setObject(object);
        return result;
    }

    /**
     * 设置失败
     *
     * @param msg 描述信息
     * @return 返回对象
     */
    public static BaseResult setFailExt(String msg, Object object) {
        BaseResult result = new BaseResult();
        result.setMsg(msg);
        result.setObject(object);
        result.setFlag(Boolean.FALSE);
        return result;
    }
}
