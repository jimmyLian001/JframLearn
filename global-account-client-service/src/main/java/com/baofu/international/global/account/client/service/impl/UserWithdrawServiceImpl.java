package com.baofu.international.global.account.client.service.impl;

import com.baofu.international.global.account.client.service.UserWithdrawService;
import com.baofu.international.global.account.client.service.models.UserWithdrawReqDto;
import com.baofu.international.global.account.core.facade.UserWithdrawFacade;
import com.baofu.international.global.account.core.facade.model.UserWithdrawFeeDetailDto;
import com.baofu.international.global.account.core.facade.model.UserWithdrawFeeDto;
import com.baofu.international.global.account.core.facade.model.res.WithdrawAccountRespDto;
import com.baofu.international.global.account.core.facade.model.res.WithdrawCashFeeRespDto;
import com.system.commons.result.Result;
import com.system.commons.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 1、方法描述
 * </p>
 * User: feng_jiang  Date: 2017/11/8 ProjectName:account-client  Version: 1.0
 */
@Slf4j
@Service
public class UserWithdrawServiceImpl implements UserWithdrawService {

    /**
     * 用户提现服务dubbo接口
     */
    @Autowired
    private UserWithdrawFacade userWithdrawFacade;

    /**
     * 查询提现店铺账户余额信息
     *
     * @param userNo 用户号
     */
    @Override
    public List<WithdrawAccountRespDto> queryWithdrawAccountInfo(Long userNo) {
        log.info("call 用户提现 查询提现店铺账户余额信息参数信息：{}", userNo);
        Result<List<WithdrawAccountRespDto>> listResult = userWithdrawFacade.queryWithdrawAccountInfo(userNo, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("call 用户提现 查询提现店铺账户余额信息返回结果：{}", listResult);
        ResultUtil.handlerResult(listResult);
        return listResult.getResult();
    }

    /**
     * 提现手续费、费率试算
     *
     * @param userNo 用户号
     * @param list   用户提现信息
     */
    @Override
    public List<WithdrawCashFeeRespDto> withdrawCashFee(Long userNo, List<UserWithdrawReqDto> list) {
        log.info("call 用户提现 提现手续费、费率试算参数信息：{}", userNo);
        UserWithdrawFeeDto userWithdrawFeeDto = new UserWithdrawFeeDto();
        userWithdrawFeeDto.setUserNo(userNo);
        List<UserWithdrawFeeDetailDto> detailList = new ArrayList();
        UserWithdrawFeeDetailDto dto;
        Result<List<WithdrawAccountRespDto>> withdrawAccountRespList = userWithdrawFacade.queryWithdrawAccountInfo(userNo, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        ResultUtil.handlerResult(withdrawAccountRespList);
        List<WithdrawAccountRespDto> withdrawAccountList = withdrawAccountRespList.getResult();
        for (WithdrawAccountRespDto withdrawAccountRespDto : withdrawAccountList) {
            for (UserWithdrawReqDto userWithdrawReqDto : list) {
                if (withdrawAccountRespDto.getCcy().equals(userWithdrawReqDto.getWithdrawCcy())
                        && withdrawAccountRespDto.getStoreNo().equals(userWithdrawReqDto.getStoreNo())) {
                    dto = new UserWithdrawFeeDetailDto();
                    dto.setStoreNo("" + withdrawAccountRespDto.getStoreNo());
                    dto.setWithdrawCcy(withdrawAccountRespDto.getCcy());
                    dto.setWithdrawAmt(userWithdrawReqDto.getWithdrawAmt());
                    dto.setAccountNo(withdrawAccountRespDto.getUserAccNo());
                    detailList.add(dto);
                }
            }
        }
        userWithdrawFeeDto.setUserWithdrawFeeDetailDtoList(detailList);
        Result<List<WithdrawCashFeeRespDto>> listResult = userWithdrawFacade.withdrawCashFee(userWithdrawFeeDto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("call 用户提现 提现手续费、费率试算返回结果：{}", listResult);
        ResultUtil.handlerResult(listResult);
        return listResult.getResult();
    }
}
