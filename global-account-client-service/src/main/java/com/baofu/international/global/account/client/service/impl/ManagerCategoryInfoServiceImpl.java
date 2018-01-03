package com.baofu.international.global.account.client.service.impl;

import com.baofu.international.global.account.client.service.ManagerCategoryInfoService;
import com.baofu.international.global.account.core.facade.ManagerCategoryInfoFacade;
import com.baofu.international.global.account.core.facade.model.ManagerCategoryRespDto;
import com.system.commons.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
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
public class ManagerCategoryInfoServiceImpl implements ManagerCategoryInfoService {

    /**
     * 省市地区信息服务
     */
    @Autowired
    private ManagerCategoryInfoFacade managerCategoryInfoFacade;

    /**
     * 查询省信息
     *
     * @param categoryId 省编码，可以为空
     * @return 省信息列表
     */
    @Override
    public List<ManagerCategoryRespDto> queryManagementCategory(String categoryId) {

        Result<List<ManagerCategoryRespDto>> result = managerCategoryInfoFacade.queryCategory(categoryId,
                MDC.get(MDCPropertyConsts.TRACE_LOG_ID));

        return result.getResult();
    }

}
