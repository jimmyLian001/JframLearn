package com.baofu.international.global.account.core.facade;

import com.baofu.international.global.account.core.facade.model.ManagerCategoryRespDto;
import com.system.commons.result.Result;

import java.util.List;

/**
 * 经营类别信息API服务
 * <p>
 * 1、查询经营类别信息
 * </p>
 *
 * @author : hetao  Date: 2017/11/08 ProjectName: globalaccount Version: 1.0
 */
public interface ManagerCategoryInfoFacade {

    /**
     * 查询经营类别信息
     *
     * @param categoryId 类别编码，可以为空
     * @param traceLogId 日志ID
     * @return 经营类别信息列表
     */
    Result<List<ManagerCategoryRespDto>> queryCategory(String categoryId, String traceLogId);

}
