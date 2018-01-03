package com.baofu.international.global.account.client.service.impl;

import com.baofu.international.global.account.client.service.UserBankCardInfoService;
import com.baofu.international.global.account.core.facade.UserBankCardFacade;
import com.baofu.international.global.account.core.facade.model.TSysBankInfoDto;
import com.system.commons.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 省市地区信息服务
 * <p>
 * 1、查询省信息
 * </p>
 * @author : hetao  Date: 2017/11/08 ProjectName: account-client Version: 1.0
 */
@Service
@Slf4j
public class UserBankCardInfoServiceImpl implements UserBankCardInfoService {

    /**
     * 省市地区信息服务
     */
    @Autowired
    private UserBankCardFacade userBankCardFacade;

    /**
     * 查询省信息
     *
     * @param traceLogId 日志ID
     * @return 省信息列表
     */
    @Override
    public List<TSysBankInfoDto> queryUserBankCardInfo(String traceLogId) {
        Result<List<TSysBankInfoDto>> result = userBankCardFacade.findSysBankInfo(null, traceLogId);
        return result.getResult();
    }
}
