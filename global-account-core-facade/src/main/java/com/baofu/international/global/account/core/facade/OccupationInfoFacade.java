package com.baofu.international.global.account.core.facade;

import com.baofu.international.global.account.core.facade.model.OccupationRespDto;
import com.system.commons.result.Result;

import java.util.List;

/**
 * 职业信息API服务
 * <p>
 * 1、查询职业信息
 * </p>
 *
 * @author : hetao  Date: 2017/11/08 ProjectName: globalaccount Version: 1.0
 */
public interface OccupationInfoFacade {

    /**
     * 查询职业信息
     *
     * @param occupationId 职业编码，可以为空
     * @param traceLogId   日志ID
     * @return 职业信息列表
     */
    Result<List<OccupationRespDto>> queryOccupation(String occupationId, String traceLogId);

}
