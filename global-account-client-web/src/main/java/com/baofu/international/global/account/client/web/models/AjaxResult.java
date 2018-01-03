package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * description:ajax返回
 * <p/>
 *
 * @author :liy on 2017/11/21
 * @version :1.0.0
 */
@Getter
@Setter
@ToString
public class AjaxResult<T> implements Serializable {

    private static final long serialVersionUID = -8700235566010570056L;

    /**
     * code
     */
    private int code;

    /**
     * message
     */
    private String message;

    /**
     * obj
     */
    private T obj;

    public AjaxResult() {
    }

    public AjaxResult(int code) {
        this.code = code;
    }

    public AjaxResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public AjaxResult(int code, String message, T obj) {
        this.code = code;
        this.message = message;
        this.obj = obj;
    }

}
