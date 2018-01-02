package com.baofu.international.global.account.core.facade.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @param <T> 泛型对象
 * @author 莫小阳  on 2017/11/8.
 */
@Getter
@Setter
@ToString
public class PageDataRespDto<T> implements Serializable {


    /**
     * 页面展示记录
     */
    private List<T> resultList;


    /**
     * 当前页
     */
    private int pageNum;

    /**
     * 数据总页数
     */
    private int pages;


    /**
     * 总记录数
     */
    private long total;


}
