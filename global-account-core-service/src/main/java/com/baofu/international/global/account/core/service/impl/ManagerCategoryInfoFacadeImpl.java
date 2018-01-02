package com.baofu.international.global.account.core.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.ManagerCategoryInfoBiz;
import com.baofu.international.global.account.core.dal.model.ManagerCategoryInfoDo;
import com.baofu.international.global.account.core.facade.ManagerCategoryInfoFacade;
import com.baofu.international.global.account.core.facade.model.ManagerCategoryRespDto;
import com.baofu.international.global.account.core.service.convert.ManagerCategoryInfoRespConvert;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 经营类别信息API服务
 * <p>
 * 1、查询经营类别信息
 * </p>
 *
 * @author : hetao  Date: 2017/11/08 ProjectName: globalaccount Version: 1.0
 */
@Slf4j
@Service
public class ManagerCategoryInfoFacadeImpl implements ManagerCategoryInfoFacade {

    /**
     * 经营类别查询服务接口
     */
    @Autowired
    private ManagerCategoryInfoBiz managerCategoryInfoBiz;

    /**
     * 查询经营类别信息
     *
     * @param categoryId 类别编码，可以为空
     * @param traceLogId 日志ID
     * @return 经营类别信息列表
     */
    @Override
    public Result<List<ManagerCategoryRespDto>> queryCategory(String categoryId, String traceLogId) {
        Result<List<ManagerCategoryRespDto>> result;
        try {
            MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
            log.info("查询经营类别信息,param:{}", categoryId);

            List<ManagerCategoryInfoDo> list = managerCategoryInfoBiz.queryCategory(categoryId);
            List<ManagerCategoryRespDto> resultList = ManagerCategoryInfoRespConvert.categoryRespConvert(list);

            result = new Result<>(resultList);
        } catch (Exception e) {
            log.error("查询经营类别信息异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("查询经营类别信息结果:{}", result);
        return result;
    }
}
