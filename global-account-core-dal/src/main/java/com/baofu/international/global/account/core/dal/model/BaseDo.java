package com.baofu.international.global.account.core.dal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 公共参数信息
 * User: feng_jiang Date:2017/11/5 ProjectName: globalaccount-core Version: 1.0
 */
@Getter
@Setter
@ToString
public class BaseDo {

    /**
     * 系统ID编号
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 创建人
     */
    private String createBy = "SYSTEM";

    /**
     * 更新时间
     */
    private Date updateAt;

    /**
     * 更新人
     */
    private String updateBy = "SYSTEM";

    /**
     * 备注
     */
    private String remarks;

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
