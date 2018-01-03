package com.baofu.international.global.account.client.service;

import com.baofu.international.global.account.core.facade.model.OccupationRespDto;

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
public interface OccupationInfoService {

    /**
     * 查询省信息
     *
     * @param provinceId 省编码，可以为空
     * @param traceLogId 日志ID
     * @return 省信息列表
     */
    List<OccupationRespDto> queryOccupation(String provinceId, String traceLogId);

}
