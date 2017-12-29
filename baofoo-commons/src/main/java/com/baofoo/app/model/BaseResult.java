package com.baofoo.app.model;


import org.apache.commons.lang.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 基础返回类
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BaseResult implements Serializable {

    /**
     * 成功
     */
    public static final int CODE_SUCCESS = 1;
    /**
     * 失败
     */
    public static final int CODE_FAIL = 0;
    /**
     * 发生异常
     */
    public static final int CODE_EXCEPTION = -1;
    /**
     * 成功
     */
    public static final String MESSAGE_SUCCESS = "成功";
    /**
     * 失败
     */
    public static final String MESSAGE_FAIL = "失败";
    /**
     * 发生异常
     */
    public static final String MESSAGE_EXCEPTION = "发生异常";
    public static final BaseResult SUCCESS_RESULT = new BaseResult(
            CODE_SUCCESS, MESSAGE_SUCCESS);
    public static final BaseResult FAIL_RESULT = new BaseResult(CODE_FAIL,
            MESSAGE_FAIL);
    public static final BaseResult EXCEPTION_RESULT = new BaseResult(
            CODE_EXCEPTION, MESSAGE_EXCEPTION);
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int code;
    private String message;

    /**
     *
     */
    public BaseResult() {

    }

    public BaseResult(int code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
