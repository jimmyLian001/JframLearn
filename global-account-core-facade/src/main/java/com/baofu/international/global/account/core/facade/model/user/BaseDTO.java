package com.baofu.international.global.account.core.facade.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 分页基础类
 */
@Setter
@Getter
@ToString(callSuper = true)
public class BaseDTO implements Serializable {

    /**
     * 分页码
     */
    private Integer pageNo = 1;

    /**
     * 条数
     */
    private Integer pageSize = 20;

    /**
     * 记录左侧栏
     */
    private String liTag;

    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 创建时间
     */
    private Date createAt;

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
