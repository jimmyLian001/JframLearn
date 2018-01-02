package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 1、分页查询条件时请求参数条件父类，使用到分页时集成此类
 * </p>
 * ProjectName:global-account-core
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/17
 */
@Setter
@Getter
@ToString
public class PageBase {

    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 分页号
     */
    private int pageNo;

    /**
     * 每页记录数
     */
    private int pageSize;

    /**
     * 获取开始索引
     *
     * @return 开始索引
     */
    public int getStartRow() {
        if (pageNo < 1) {
            pageNo = 1;
        }
        return (pageNo - 1) * pageSize;
    }

    /**
     * 获取结束索引
     *
     * @return 结束索引
     */
    public int getEndRow() {

        if (pageNo < 1) {
            pageNo = 1;
        }
        return pageNo * pageSize;
    }
}
