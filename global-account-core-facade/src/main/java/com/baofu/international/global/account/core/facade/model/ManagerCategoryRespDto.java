package com.baofu.international.global.account.core.facade.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 经营类别信息API服务
 * <p>
 * 1、查询经营类别信息
 * </p>
 *
 * @author : hetao  Date: 2017/11/08 ProjectName: globalaccount Version: 1.0
 */
@Getter
@Setter
@ToString
public class ManagerCategoryRespDto implements Serializable {
    /**
     * 经营类别id
     */
    private String categoryId;

    /**
     * 经营类别名称
     */
    private String categoryName;
}
