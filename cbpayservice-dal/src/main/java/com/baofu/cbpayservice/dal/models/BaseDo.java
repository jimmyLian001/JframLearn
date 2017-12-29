package com.baofu.cbpayservice.dal.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 公共参数信息
 * User: 香克斯 Date:2016/10/25 ProjectName: asias-icpaygate Version: 1.0
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
}
