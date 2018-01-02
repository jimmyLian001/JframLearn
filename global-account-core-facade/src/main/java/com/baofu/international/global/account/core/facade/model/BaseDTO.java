package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据基础模型
 * User: 欧西涛  Date: 2015/10/11 ProjectName: globalaccount Version: 1.0
 */
@Setter
@Getter
@ToString
public class BaseDTO implements Serializable {

    /**
     * 数据库主键
     */
    private String id;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 最后更新人
     */
    private String updatedBy;

    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 结束时间
     */
    private String endTime;

}
