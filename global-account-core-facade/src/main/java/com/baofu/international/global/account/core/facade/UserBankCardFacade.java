package com.baofu.international.global.account.core.facade;

import com.baofu.international.global.account.core.facade.model.AddCompanyBankCardApplyDto;
import com.baofu.international.global.account.core.facade.model.AddPersonalBankCardApplyDto;
import com.baofu.international.global.account.core.facade.model.TSysBankInfoDto;
import com.baofu.international.global.account.core.facade.model.user.UserBankCardInfoDto;
import com.system.commons.result.Result;

import java.util.List;

/**
 * 收款账户用户银行卡管理接口
 * <p/>
 *
 * @author : lian zd
 * @date :2017/11/6 ProjectName: account-core Version:1.0
 */
public interface UserBankCardFacade {

    /**
     * 添加个人银行卡
     *
     * @param personalBankCardApplyDto 申请添加个人银行卡信息
     * @param traceLogId               日志id
     * @return 受理结果
     */
    Result<Boolean> addPersonBankCard(AddPersonalBankCardApplyDto personalBankCardApplyDto, String traceLogId);

    /**
     * 添加企业对公银行卡
     *
     * @param companyBankCardApplyDto 申请添加企业对公银行卡信息
     * @param traceLogId              日志id
     * @return 受理结果
     */
    Result<Boolean> addCompanyPublicBankCard(AddCompanyBankCardApplyDto companyBankCardApplyDto, String traceLogId);

    /**
     * 删除银行卡
     *
     * @param userNo     用户编号
     * @param recordNo   记录编号
     * @param traceLogId 日志id
     * @return 受理结果
     */
    Result<Boolean> deleteBankCard(Long userNo, Long recordNo, String traceLogId);

    /**
     * 根据用户号查询用户银行卡信息
     *
     * @param userNo 会员号
     * @param logId  日志ID
     * @return List
     */
    Result<List<UserBankCardInfoDto>> findUserBankCardInfo(Long userNo, String logId);

    /**
     * 查询系统支持发卡行类型
     *
     * @param userNo 会员号
     * @param logId  日志id
     * @return List
     */
    Result<List<TSysBankInfoDto>> findSysBankInfo(Long userNo, String logId);

}
