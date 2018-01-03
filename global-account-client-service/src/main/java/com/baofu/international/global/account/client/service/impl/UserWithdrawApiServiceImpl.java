package com.baofu.international.global.account.client.service.impl;

import com.baofu.international.global.account.client.common.enums.SettleStatusEnum;
import com.baofu.international.global.account.client.service.UserWithdrawApiService;
import com.baofu.international.global.account.client.service.models.UserWithdrawDistributeReqBo;
import com.baofu.international.global.account.client.service.models.response.TransContent;
import com.baofu.international.global.account.client.service.models.response.TransReqDatas;
import com.baofu.international.global.account.client.service.models.response.TransRespBF40001Async;
import com.baofu.international.global.account.core.facade.UserWithdrawApiFacade;
import com.baofu.international.global.account.core.facade.model.UserDistributeApiDto;
import com.baofu.international.global.account.core.facade.model.UserSettleApplyDto;
import com.system.commons.result.Result;
import com.system.commons.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 1、用户结汇申请处理服务
 * </p>
 * User: feng_jiang  Date: 2017/11/24 ProjectName:account-client  Version: 1.0
 */
@Slf4j
@Service
public class UserWithdrawApiServiceImpl implements UserWithdrawApiService {


    /**
     * 用户结汇申请处理接口
     */
    @Autowired
    private UserWithdrawApiFacade userWithdrawApiFacade;

    /**
     * 功能：用户结汇申请处理
     * @param withdrawDistributeReqBo
     */
    @Override
    public void dealUserSettleApply(UserWithdrawDistributeReqBo withdrawDistributeReqBo) {
        log.info("call UserWithdrawFacade.userWithdrawDistribute 结汇申请处理状态:{}",
                SettleStatusEnum.getEnumsByCode(withdrawDistributeReqBo.getStatus()));
        UserSettleApplyDto dto = new UserSettleApplyDto();
        BeanUtils.copyProperties(withdrawDistributeReqBo, dto);
        log.info("call userWithdrawApiFacade.dealUserSettleApply Param:{}", dto);
        Result<Boolean> response = userWithdrawApiFacade.dealUserSettleApply(dto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("call userWithdrawApiFacade.dealUserSettleApply result:{}", response);
        ResultUtil.handlerResult(response);
    }

    /**
     * 功能：内卡下发处理
     * @param respDecrypt 内卡反馈内容
     */
    @Override
    public void dealUserWithdraw(String respDecrypt) {
        TransContent<TransRespBF40001Async> xmlObj = new TransContent();
        xmlObj = (TransContent<TransRespBF40001Async>) xmlObj.str2Obj(respDecrypt, TransRespBF40001Async.class);
        TransReqDatas<TransRespBF40001Async> withdrawObj = xmlObj.getTrans_reqDatas();
        List<TransRespBF40001Async> trans_reqDatas = withdrawObj.getTrans_reqDatas();
        if (!CollectionUtils.isEmpty(trans_reqDatas)) {
            TransRespBF40001Async bf40001Async = trans_reqDatas.get(0);
            UserDistributeApiDto distributeApiDto = new UserDistributeApiDto();
            distributeApiDto.setTransNo(bf40001Async.getTrans_no());
            distributeApiDto.setState(Long.valueOf(bf40001Async.getState()));
            distributeApiDto.setTransRemark(bf40001Async.getTrans_remark());
            Result<Boolean> response = userWithdrawApiFacade.dealWithdrawDistributeApply(distributeApiDto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            ResultUtil.handlerResult(response);
        }
    }
}
