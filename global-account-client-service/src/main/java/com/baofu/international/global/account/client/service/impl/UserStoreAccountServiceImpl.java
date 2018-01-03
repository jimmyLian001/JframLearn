package com.baofu.international.global.account.client.service.impl;

import com.baofu.international.global.account.client.service.UserStoreAccountService;
import com.baofu.international.global.account.core.facade.UserStoreAccountFacade;
import com.baofu.international.global.account.core.facade.model.res.UserStoreAccountRespDto;
import com.baofu.international.global.obtain.facade.UserStoreRegisterFacade;
import com.baofu.international.global.obtain.facade.models.UserStoreCheckReqDto;
import com.google.common.collect.Lists;
import com.system.commons.result.Result;
import com.system.commons.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户店铺收款账户相关服务
 * <p>
 * 1、根据用户号查询所有可用的店铺和收款账户信息
 * </p>
 * User: 香克斯  Date: 2017/11/7 ProjectName:account-client  Version: 1.0
 */
@Slf4j
@Service
public class UserStoreAccountServiceImpl implements UserStoreAccountService {

    /**
     * 用户店铺收款账户信息
     */
    @Autowired
    private UserStoreAccountFacade userStoreAccountFacade;

    /**
     * 用户店铺信息注册校验
     */
    @Autowired
    private UserStoreRegisterFacade userStoreRegisterFacade;

    /**
     * 根据用户号查询所有可用的店铺和收款账户信息
     *
     * @param userNo 用户编号
     * @return 用户店铺信息
     */
    @Override
    public List<UserStoreAccountRespDto> queryUserStoreAccount(Long userNo, String ccy) {

        log.info("根据用户号查询所有可用的店铺和收款账户信息，用户号：{}", userNo);
        //查询用户店铺收款账户信息
        Result<List<UserStoreAccountRespDto>> result = userStoreAccountFacade.queryUserStoreAccount(userNo,
                MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("根据用户号查询所有可用的店铺和收款账户信息，返回结果：{}", result);
        ResultUtil.handlerResult(result);
        //判断币种是否为空，如果为空情况直接返回
        if (StringUtils.isBlank(ccy)) {
            return result.getResult();
        }
        //循环过滤币种
        List<UserStoreAccountRespDto> userStoreAccountRespDtoList = Lists.newArrayList();
        for (UserStoreAccountRespDto userStoreAccountRespDto : result.getResult()) {
            if (ccy.equals(userStoreAccountRespDto.getCcy())) {
                userStoreAccountRespDtoList.add(userStoreAccountRespDto);
            }
        }
        return userStoreAccountRespDtoList;
    }

    /**
     * 校验用户店铺信息是否正确
     *
     * @param userStoreCheckReqDto 校验参数信息
     */
    @Override
    public void checkUserSellerInfo(UserStoreCheckReqDto userStoreCheckReqDto) {

        log.info("用户店铺信息校验请求参数信息：{}", userStoreCheckReqDto);
        Result<Boolean> result = userStoreRegisterFacade.userStoreCheck(userStoreCheckReqDto,
                MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("用户店铺信息校验返回结果：{}", result);

        ResultUtil.handlerResult(result);
    }
}
