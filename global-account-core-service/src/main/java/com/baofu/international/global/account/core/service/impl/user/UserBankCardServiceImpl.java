package com.baofu.international.global.account.core.service.impl.user;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.util.SecurityUtil;
import com.baofu.international.global.account.core.dal.model.UserBankCardInfoDo;
import com.baofu.international.global.account.core.facade.model.user.UserBankCardInfoDto;
import com.baofu.international.global.account.core.facade.user.UserBankCardFacade;
import com.baofu.international.global.account.core.manager.UserBankCardManager;
import com.baofu.international.global.account.core.service.convert.UserBankCartConvert;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 用户银行卡服务类
 * <p>
 * 1、根据用户号查询用户银行卡信息
 * 2、根据用户号、银行卡记录编号后查询用户银行卡信息
 * </p>
 * User: 陶伟超 Date: 2017/11/4 ProjectName: account-core Version: 1.0.0
 */
@Slf4j
@Component
public class UserBankCardServiceImpl implements UserBankCardFacade {

    /**
     * 用户银行卡manager
     */
    @Autowired
    private UserBankCardManager userBankCardManager;


    /**
     * 根据用户号查询用户银行卡信息
     *
     * @param userNo 会员号
     * @param logId  日志ID
     * @return list
     */
    @Override
    public Result<List<UserBankCardInfoDto>> findUserBankCardInfo(Long userNo, String logId) {

        Result<List<UserBankCardInfoDto>> results;
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        log.info("call 查询用户银行卡参数 用户号：{}", userNo);
        try {
            List<UserBankCardInfoDo> userBankCardInfoList = userBankCardManager.selectUserBankCardByUserNo(userNo);
            for (UserBankCardInfoDo userBankCardInfoDo : userBankCardInfoList) {
                //银行卡号解密
                String bankCardNo = SecurityUtil.desDecrypt(userBankCardInfoDo.getCardNo(), Constants.CARD_DES_KEY);
                userBankCardInfoDo.setCardNo(bankCardNo);
            }
            results = new Result<>(UserBankCartConvert.convertList(userBankCardInfoList));
        } catch (Exception e) {
            log.error("call 查询用户银行卡异常:{}", e);
            results = ExceptionUtils.getResponse(e);
        }
        log.info("call 查询用户银行卡返回结果：{}", results);
        return results;
    }

    /**
     * 根据用户号、银行卡记录编号后查询用户银行卡信息
     *
     * @param userNo           用户号
     * @param bankCardRecordNo 银行卡记录编号
     * @param traceLogId       日志ID
     * @return 返回银行卡信息
     */
    @Override
    public Result<UserBankCardInfoDto> findUserBankCard(Long userNo, Long bankCardRecordNo, String traceLogId) {

        Result<UserBankCardInfoDto> result;
        log.info("call 查询用户银行卡参数 用户号：{}，记录编号：{}", userNo, bankCardRecordNo);
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        try {
            UserBankCardInfoDo userBankCardInfo = userBankCardManager.selectUserBankCardByUserNo(userNo, bankCardRecordNo);
            result = new Result<>(UserBankCartConvert.doConvertObj(userBankCardInfo));
        } catch (Exception e) {
            log.error("call 根据用户号、银行卡记录编号后查询用户银行卡信息异常:{}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 查询用户银行卡返回结果：{}", result);
        return result;
    }


}
