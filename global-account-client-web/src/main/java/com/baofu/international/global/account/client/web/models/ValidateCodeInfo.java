package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * description:
 * <p/>
 * Created by liy on 2017/11/5 0005 ProjectName：account-core
 */
@Getter
@Setter
@ToString
public class ValidateCodeInfo {

    /**
     * session
     */
    private String session;

    /**
     * 图片宽
     */
    private int imgWidth;

    /**
     * 图片高
     */
    private int imgHeight;

    /**
     * 干扰线数量
     */
    private int lineNum;

    /**
     * 随机产生数字与字母组合的字符串
     */
    private String randStr;

    /**
     * 随机产生字符数量
     */
    private int randNum;

    /**
     * 字体大小
     */
    private int fontSize;
}
