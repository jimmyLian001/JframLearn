package com.baofu.international.global.account.client.service.impl;

import com.baofu.international.global.account.client.common.util.DataDesensUtils;
import com.baofu.international.global.account.core.facade.ApplyAccountFacade;
import com.baofu.international.global.account.core.facade.model.ApplyAccountRepDto;
import com.baofu.international.global.account.core.facade.model.ApplyAccountReqDto;
import com.google.common.collect.Lists;
import com.system.commons.result.Result;
import com.system.commons.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: yangjian  Date: 2017-11-05 ProjectName:  Version: 1.0
 */
@Slf4j
@Service
public class ApplyAccountInfoServiceImpl {

    /**
     * 开通收款账户相关dubbo服务
     */
    @Autowired
    private ApplyAccountFacade applyAccountFacade;

    /**
     * 申请收款账户页面展示信息
     *
     * @param userNo   用户号
     * @param userType 用户类型
     * @return 返回结果
     */
    public List<ApplyAccountRepDto> getApplyAccountInfo(Long userNo, Integer userType, Long qualifiedNo) {

        log.info("查询账户开户主体信息请求参数：用户号：{},用户类型：{}", userNo, userType);
        Result<List<ApplyAccountRepDto>> result = applyAccountFacade.getApplyAccountData(userNo, userType,
                MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("查询账户开户主体信息返回结果：{}", result);
        List<ApplyAccountRepDto> resultList = Lists.newArrayList();
        for (ApplyAccountRepDto repDto : result.getResult()) {
            if (qualifiedNo != null && !repDto.getQualifiedNo().equals(qualifiedNo)) {
                continue;
            }
            repDto.setLegalNo(DataDesensUtils.dealSensBankCardNo(repDto.getLegalNo()));
            resultList.add(repDto);
        }
        return resultList;
    }

    /**
     * 收款賬戶申请开通服务
     *
     * @param applyAccountReqDto reqDto
     */
    public void applyAccountCreate(ApplyAccountReqDto applyAccountReqDto) {

        log.info("申請开通收款账户请求参数：{}", applyAccountReqDto);
        Result<Boolean> result = applyAccountFacade.addApplyAccount(applyAccountReqDto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("申请开通收款账户返回结果：{}", result);
        ResultUtil.handlerResult(result);

    }
}
