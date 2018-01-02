package com.baofu.international.global.account.core.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.OccupationInfoBiz;
import com.baofu.international.global.account.core.dal.model.OccupationInfoDo;
import com.baofu.international.global.account.core.facade.OccupationInfoFacade;
import com.baofu.international.global.account.core.facade.model.OccupationRespDto;
import com.baofu.international.global.account.core.service.convert.OccupationInfoRespConvert;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 职业信息API服务
 * <p>
 * 1、查询职业信息
 * </p>
 *
 * @author : hetao  Date: 2017/11/08 ProjectName: globalaccount Version: 1.0
 */
@Slf4j
@Service
public class OccupationInfoFacadeImpl implements OccupationInfoFacade {

    /**
     * 职业查询服务接口
     */
    @Autowired
    private OccupationInfoBiz occupationInfoBiz;

    /**
     * 查询职业信息
     *
     * @param occupationId 职业编码，可以为空
     * @param traceLogId   日志ID
     * @return 省信息列表
     */
    @Override
    public Result<List<OccupationRespDto>> queryOccupation(String occupationId, String traceLogId) {
        Result<List<OccupationRespDto>> result;
        try {
            MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
            log.info("查询职业信息,param:{}", occupationId);

            List<OccupationInfoDo> list = occupationInfoBiz.queryOccupation(occupationId);
            List<OccupationRespDto> resultList = OccupationInfoRespConvert.occupationRespConvert(list);

            result = new Result<>(resultList);
        } catch (Exception e) {
            log.error("查询职业信息异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("查询职业信息结果:{}", result);
        return result;
    }
}
