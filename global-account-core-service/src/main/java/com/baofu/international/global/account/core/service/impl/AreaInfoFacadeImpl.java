package com.baofu.international.global.account.core.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.AreaInfoBiz;
import com.baofu.international.global.account.core.dal.model.AreaInfoDo;
import com.baofu.international.global.account.core.dal.model.CityInfoDo;
import com.baofu.international.global.account.core.dal.model.ProvinceInfoDo;
import com.baofu.international.global.account.core.facade.AreaInfoFacade;
import com.baofu.international.global.account.core.facade.model.AreaRespDto;
import com.baofu.international.global.account.core.facade.model.CityRespDto;
import com.baofu.international.global.account.core.facade.model.ProvinceRespDto;
import com.baofu.international.global.account.core.service.convert.AreaInfoRespConvert;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
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
 *
 * @author : hetao  Date: 2017/11/08 ProjectName: globalaccount Version: 1.0
 */
@Slf4j
@Service
public class AreaInfoFacadeImpl implements AreaInfoFacade {

    /**
     * 省市地区查询服务接口
     */
    @Autowired
    private AreaInfoBiz areaInfoBiz;

    /**
     * 查询省信息
     *
     * @param provinceId 省编码，可以为空
     * @param traceLogId 日志ID
     * @return 省信息列表
     */
    @Override
    public Result<List<ProvinceRespDto>> queryProvince(String provinceId, String traceLogId) {
        Result<List<ProvinceRespDto>> result;
        try {
            MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
            log.info("查询省信息,param:{}", provinceId);

            List<ProvinceInfoDo> list = areaInfoBiz.queryProvince(provinceId);
            List<ProvinceRespDto> resultList = AreaInfoRespConvert.provinceRespConvert(list);

            result = new Result<>(resultList);
        } catch (Exception e) {
            log.error("查询省信息异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("查询省信息结果:{}", result);
        return result;
    }

    /**
     * 查询城市信息
     *
     * @param cityId     城市编码
     * @param traceLogId 日志ID
     * @return 城市信息列表
     */
    @Override
    public Result<List<CityRespDto>> queryCity(String cityId, String traceLogId) {
        Result<List<CityRespDto>> result;
        try {
            MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
            log.info("查询城市信息,param:{}", cityId);

            List<CityInfoDo> list = areaInfoBiz.queryCity(cityId);
            List<CityRespDto> resultList = AreaInfoRespConvert.cityRespConvert(list);

            result = new Result<>(resultList);
        } catch (Exception e) {
            log.error("查询城市信息异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("查询城市信息结果:{}", result);
        return result;
    }

    /**
     * 查询地区信息
     *
     * @param areaId     地区编码
     * @param traceLogId 日志ID
     * @return 地区信息列表
     */
    @Override
    public Result<List<AreaRespDto>> queryArea(String areaId, String traceLogId) {
        Result<List<AreaRespDto>> result;
        try {
            MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
            log.info("查询地区信息,param:{}", areaId);

            List<AreaInfoDo> list = areaInfoBiz.queryArea(areaId);
            List<AreaRespDto> resultList = AreaInfoRespConvert.areaRespConvert(list);

            result = new Result<>(resultList);
        } catch (Exception e) {
            log.error("查询地区信息异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("查询地区信息结果:{}", result);
        return result;
    }
}
