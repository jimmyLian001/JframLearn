package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * <p>
 * 1、分页信息
 * </p>
 * User: 康志光  Date: 2017/11/08 ProjectName:account-core  Version: 1.0
 */
@Setter
@Getter
@ToString
public class PageBo<T> {

    /**
     * 集合信息
     */
    private List<T> list;

    /**
     * 总条数
     */
    private long totalSize;

}
