package com.baofu.international.global.account.core.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.UserBankCardBiz;
import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.util.SecurityUtil;
import com.baofu.international.global.account.core.dal.model.SysBankInfoDo;
import com.baofu.international.global.account.core.dal.model.UserBankCardInfoDo;
import com.baofu.international.global.account.core.facade.UserBankCardFacade;
import com.baofu.international.global.account.core.facade.model.AddCompanyBankCardApplyDto;
import com.baofu.international.global.account.core.facade.model.AddPersonalBankCardApplyDto;
import com.baofu.international.global.account.core.facade.model.TSysBankInfoDto;
import com.baofu.international.global.account.core.facade.model.user.UserBankCardInfoDto;
import com.baofu.international.global.account.core.manager.TSysBankInfoManager;
import com.baofu.international.global.account.core.manager.UserBankCardManager;
import com.baofu.international.global.account.core.service.convert.UserBankCardConvert;
import com.baofu.international.global.account.core.service.convert.UserBankCartConvert;
import com.google.common.collect.Lists;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收款账户用户银行卡管理接口实现
 * <p>
 *
 * @author : lian zd
 * @date :2017/11/6 ProjectName: account-core Version:1.0
 */
@Service
@Slf4j
public class UserBankCardFacadeImpl implements UserBankCardFacade {

    /**
     * 用户银行卡管理业务逻辑处理
     */
    @Autowired
    private UserBankCardBiz userBankCardBiz;

    /**
     * 用户银行卡manager
     */
    @Autowired
    private UserBankCardManager userBankCardManager;

    /**
     * 查询系统支持发卡行类型
     */
    @Autowired
    private TSysBankInfoManager sysBankInfoManager;

    /**
     * 添加个人银行卡
     *
     * @param personalBankCardApplyDto 申请添加个人银行卡信息
     * @param traceLogId               日志id
     * @return 受理结果
     */
    @Override
    public Result<Boolean> addPersonBankCard(AddPersonalBankCardApplyDto personalBankCardApplyDto, String traceLogId) {
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("call 用户申请添加个人银行卡受理开始：{}", personalBankCardApplyDto);
        Result<Boolean> result;
        try {
            ParamValidate.validateParams(personalBankCardApplyDto);
            userBankCardBiz.addPersonalBank(UserBankCardConvert.toAddPersonalBankCardBo(personalBankCardApplyDto));
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("call 用户申请添加个人银行卡处异常，异常信息：{}", e);
        }
        log.info("用户申请添加添加个人银行卡添加处理结果：{}", result);
        return result;
    }

    /**
     * 添加企业对公银行卡
     *
     * @param companyBankCardApplyDto 申请添加企业对公银行卡信息
     * @param traceLogId              日志id
     * @return 受理结果
     */
    @Override
    public Result<Boolean> addCompanyPublicBankCard(AddCompanyBankCardApplyDto companyBankCardApplyDto, String traceLogId) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("call 用户申请添加企业对公银行卡受理开始：{}", companyBankCardApplyDto);
        Result<Boolean> result;
        try {
            ParamValidate.validateParams(companyBankCardApplyDto);
            userBankCardBiz.addCompanyLegalPBC(UserBankCardConvert.toAddCompanyBankCardBo(companyBankCardApplyDto));
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("call 用户申请添加企业对公银行处异常，异常信息：{}", e);
        }
        log.info("用户申请添加企业对公银行处理结果：{}", result);
        return result;
    }

    /**
     * 删除银行卡
     *
     * @param userNo     用户编号
     * @param recordNo   记录编号
     * @param traceLogId 日志id
     * @return 受理结果
     */
    @Override
    public Result<Boolean> deleteBankCard(Long userNo, Long recordNo, String traceLogId) {
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("call 用户申请删除银行卡受理开始,用户号：{},记录编号：{}", userNo, recordNo);
        Result<Boolean> result;
        try {
            if (userNo == null || recordNo == null) {
                throw new BizServiceException(CommonErrorCode.PARAMETER_VALID_NOT_PASS, "用户号或记录编号为空");
            }
            userBankCardBiz.delBankCard(userNo, recordNo);
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("call 用户申请删除银行卡处异常，异常信息：{}", e);
        }
        log.info("用户申请删除银行卡处理结果：{}", result);
        return result;
    }

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
        log.info("call 查询用户银行卡参数 用户号：{}", userNo);
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        try {
            List<UserBankCardInfoDo> userBankCardInfoList = userBankCardManager.selectUserBankCardByUserNo(userNo);
            List<UserBankCardInfoDo> userBankCardInfoAbl = Lists.newArrayList();
            for (UserBankCardInfoDo list : userBankCardInfoList) {
                StringBuilder cardNo = new StringBuilder();
                String desCardNo = SecurityUtil.desDecrypt(list.getCardNo(), Constants.CARD_DES_KEY);
                if (desCardNo.length() >= NumberDict.TWELVE) {
                    cardNo.append(desCardNo.substring(0, 6)).append("**** ****").append(desCardNo.
                            substring(desCardNo.length() - 4, desCardNo.length()));
                } else {
                    cardNo.append(desCardNo);
                }
                list.setCardNo(cardNo.toString());
                userBankCardInfoAbl.add(list);
            }
            results = new Result<>(UserBankCartConvert.convertList(userBankCardInfoAbl));
        } catch (Exception e) {
            log.error("call 查询用户银行卡异常:{}", e);
            results = ExceptionUtils.getResponse(e);
        }
        log.info("call 查询用户银行卡返回结果：{}", results);
        return results;
    }

    /**
     * 查询系统支持发卡行类型
     *
     * @return List
     */
    @Override
    public Result<List<TSysBankInfoDto>> findSysBankInfo(Long userNo, String logId) {
        Result<List<TSysBankInfoDto>> results;
        log.info("call 查询系统支持发卡银行参数 用户号：{}", userNo);
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        try {
            List<SysBankInfoDo> userBankCardInfoList = sysBankInfoManager.querySysBankInfo();
            if (userBankCardInfoList.isEmpty()) {
                log.info("未查询到系统支持银行信息");
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190214);
            }
            results = new Result<>(UserBankCartConvert.convertToSysBankInfoList(userBankCardInfoList));
        } catch (Exception e) {
            log.error("call 查询系统支持发卡银行异常:{}", e);
            results = ExceptionUtils.getResponse(e);
        }
        log.info("call 查询系统支持发卡银行返回结果：{}", results);
        return results;
    }

}
