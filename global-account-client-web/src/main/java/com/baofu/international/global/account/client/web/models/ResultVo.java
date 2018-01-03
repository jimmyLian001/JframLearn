package com.baofu.international.global.account.client.web.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 处理结果VO
 * <p>
 * Created by kangzhiguang on 2017/11/5 0005.
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
public class ResultVo {

    /**
     * 处理标识
     */
    private Boolean successFlag;

    /**
     * 退出标识
     */
    private Boolean loginOutFlag;

    /**
     * 主题
     */
    private String title;

    /**
     * 提示内容
     */
    private String content;

    /**
     * 返回地址
     */
    private String returnUrl;


}
