package com.baofu.international.global.account.client.service.impl;

import com.baofu.international.global.account.client.service.UserBankCardService;
import com.baofu.international.global.account.core.facade.model.user.UserBankCardInfoDto;
import com.baofu.international.global.account.core.facade.user.UserBankCardFacade;
import com.system.commons.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户银行卡相关管理
 * <p>
 * 1、根据用户编号查询用户绑定银行卡信息
 * </p>
 * User: 香克斯  Date: 2017/11/7 ProjectName:account-client  Version: 1.0
 */
@Slf4j
@Service
public class UserBankCardServiceImpl implements UserBankCardService {

    /**
     * 用户银行卡服务外部接口
     */
    @Autowired
    private UserBankCardFacade userBankCardFacade;

    /**
     * 根据用户编号查询用户绑定银行卡信息
     *
     * @param userNo 用户编号
     * @return 返回银行卡集合信息
     */
    @Override
    public List<UserBankCardInfoDto> queryUserBankCard(Long userNo) {

        log.info("根据用户编号查询用户绑定银行卡信息,用户编号：{}", userNo);
        Result<List<UserBankCardInfoDto>> result = userBankCardFacade.findUserBankCardInfo(userNo,
                MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("根据用户编号查询用户绑定银行卡信息,返回信息：{}", result);

        return result.getResult();
    }

    /**
     * 根据用户编号查询用户绑定银行卡信息
     *
     * @param recordId 卡信息表主键
     * @return 返回银行卡信息
     */
    @Override
    public UserBankCardInfoDto queryUserDefaultBankCard(Long userNo, Long recordId) {
        log.info("根据用户编号查询用户绑定银行卡信息,用户编号：{}", recordId);
        Result<UserBankCardInfoDto> result = userBankCardFacade.findUserBankCard(userNo, recordId,
                MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("根据用户编号查询用户绑定银行卡信息,返回信息：{}", result);

        return result.getResult();
    }
}
