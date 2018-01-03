package com.baofu.international.global.account.client.service.impl;

import com.baofu.international.global.account.client.service.OccupationInfoService;
import com.baofu.international.global.account.core.facade.OccupationInfoFacade;
import com.baofu.international.global.account.core.facade.model.OccupationRespDto;
import com.system.commons.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 省市地区信息服务
 * <p>
 * 1、查询省信息
 * 2、查询市信息
 * 3、查询地区信息
 * </p>
 * @author : hetao  Date: 2017/11/08 ProjectName: account-client Version: 1.0
 */
@Service
@Slf4j
public class OccupationInfoServiceImpl implements OccupationInfoService {

    /**
     * 省市地区信息服务
     */
    @Autowired
    private OccupationInfoFacade occupationInfoFacade;

    /**
     * 查询省信息
     *
     * @param occupationId 省编码，可以为空
     * @param traceLogId   日志ID
     * @return 省信息列表
     */
    @Override
    public List<OccupationRespDto> queryOccupation(String occupationId, String traceLogId) {
        Result<List<OccupationRespDto>> result = occupationInfoFacade.queryOccupation(occupationId, traceLogId);
        return result.getResult();
    }
}
