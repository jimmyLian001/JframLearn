package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.servlet.ModelAndView;

/**
 * 同步返回对象
 *
 * @author: 不良人 Date:2017/11/21 ProjectName: account-client Version: 1.0
 */
@Getter
@Setter
@ToString
public class BaseModelAndView extends ModelAndView{

    /**
     * 返回状态
     */
    private Boolean flag;

    /**
     * 返回描述信息，可以存放异常信息
     */
    private String msg;

    /**
     * 返回页面路径
     */
    private String path;

    /**
     * 设置成功
     *
     * @return 返回对象
     */
    public static BaseModelAndView setSuccess(String path) {
        BaseModelAndView view = new BaseModelAndView();
        view.setViewName(path);
        view.setFlag(Boolean.TRUE);
        return view;
    }

    /**
     * 设置失败
     *
     * @param msg   描述信息
     * @return 返回对象
     */
    public static BaseModelAndView setFail(String path,String msg) {
        BaseModelAndView view = new BaseModelAndView();
        view.setMsg(msg);
        view.setPath(path);
        view.setFlag(Boolean.FALSE);
        return view;
    }
}
