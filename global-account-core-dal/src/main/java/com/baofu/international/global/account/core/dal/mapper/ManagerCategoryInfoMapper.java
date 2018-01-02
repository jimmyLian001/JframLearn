package com.baofu.international.global.account.core.dal.mapper;

import com.baofu.international.global.account.core.dal.model.ManagerCategoryInfoDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 经营类别信息服务
 * <p>
 * 1、查询经营类别信息
 * </p>
 *
 * @author : hetao  Date: 2017/11/08 ProjectName: globalaccount Version: 1.0
 */
public interface ManagerCategoryInfoMapper {
    /**
     * 查询经营类别信息
     *
     * @param categoryId 类别编码，可以为空
     * @return 经营类别信息列表
     */
    List<ManagerCategoryInfoDo> queryCategory(@Param("categoryId") String categoryId);
}