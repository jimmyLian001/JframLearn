package com.baofu.international.global.account.client.web.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ${title}
 * <p>
 * User: 蒋文哲 Date: 2017/11/8 Version: 1.0
 * </p>
 */
@Getter
@Setter
@ToString
public class PageVo {
    /**
     * 页码
     */
    private int pageNum;
    /**
     * 页面笔数
     */
    private int pageSize;

}
