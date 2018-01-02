package com.baofu.international.global.account.core.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.util.BeanCopyUtils;
import com.baofu.international.global.account.core.dal.mapper.PaymentDetailMapper;
import com.baofu.international.global.account.core.dal.mapper.UserWithdrawApplyMapper;
import com.baofu.international.global.account.core.dal.model.*;
import com.baofu.international.global.account.core.facade.TradeQueryFacade;
import com.baofu.international.global.account.core.facade.model.*;
import com.baofu.international.global.account.core.facade.model.user.PageDataRespDto;
import com.baofu.international.global.account.core.manager.UserAccountManager;
import com.baofu.international.global.account.core.service.convert.TradeQueryParamConvert;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.system.commons.exception.BizServiceException;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 交易查询
 *
 * @author 莫小阳  on 2017/11/7.
 */
@Slf4j
@Service
public class TradeQueryFacadeImpl implements TradeQueryFacade {


    /**
     * 交易查询 Mapper
     */
    @Autowired
    private PaymentDetailMapper tPaymentDetailMapper;

    /**
     * 提现查询 Mapper
     */
    @Autowired
    private UserWithdrawApplyMapper tUserWithdrawApplyMapper;

    /**
     * 用户账户信息操作服务类
     */
    @Autowired
    private UserAccountManager userAccountManager;

    /**
     * 交易查询
     *
     * @param tradeQueryReqDto 查询条件对象
     * @param traceLogId       日志ID
     * @return 结果
     */
    @Override
    public Result<PageDataRespDto> tradeAccQuery(TradeQueryReqDto tradeQueryReqDto, String traceLogId) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("call 交易查询开始，查询条件参数信息：{}", tradeQueryReqDto);
        Result<PageDataRespDto> result;
        try {
            // 校验数据是否合法
            ParamValidate.validateParams(tradeQueryReqDto);
            PageHelper.offsetPage(tradeQueryReqDto.getCurrPageNum() * tradeQueryReqDto.getPageSize(), tradeQueryReqDto.getPageSize());
            Page<TradeAccQueryDo> pageDate =
                    (Page<TradeAccQueryDo>) tPaymentDetailMapper.tradeAccQuery(TradeQueryParamConvert.convertToDo(tradeQueryReqDto));
            result = new Result<>(TradeQueryParamConvert.convertToList(pageDate));
            log.info("call 结果：{}", result);
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("call 交易查询发生异常，异常信息：", e);
        }
        log.info("call 交易查询结束");
        return result;

    }

    /**
     * 提现查询
     *
     * @param tradeQueryReqDto 查询条件对象
     * @param traceLogId       日志ID
     * @return 结果
     */
    @Override
    public Result<PageDataRespDto> withdrawalQuery(TradeQueryReqDto tradeQueryReqDto, String traceLogId) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("call 提现查询开始，查询条件参数信息：{}", tradeQueryReqDto);
        Result<PageDataRespDto> result;
        try {
            ParamValidate.validateParams(tradeQueryReqDto);
            PageHelper.offsetPage(tradeQueryReqDto.getCurrPageNum() * tradeQueryReqDto.getPageSize(), tradeQueryReqDto.getPageSize());
            Page<WithdrawalQueryDo> pageDate =
                    (Page<WithdrawalQueryDo>) tUserWithdrawApplyMapper.withdrawalQuery(TradeQueryParamConvert.convertToDo(tradeQueryReqDto));
            result = new Result<>(TradeQueryParamConvert.withdrawalConvertToList(pageDate));
            log.info("call 结果：{}", result);
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("call 提现查询发生异常，异常信息：{}", e);
        }
        log.info("call 提现查询结束");
        return result;
    }

    /**
     * 管控台 ——》 子账户收支明细查询
     *
     * @param subAccTradeDetailQueryReqDto 参数
     * @param traceLogId                   日志ID
     * @return 结果
     */
    @Override
    public Result<PageDataRespDto> subAccTradeDetailQuery(SubAccTradeDetailQueryReqDto subAccTradeDetailQueryReqDto, String traceLogId) {
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("call 子账户收支明细查询开始，查询条件参数信息：{}", subAccTradeDetailQueryReqDto);
        Result<PageDataRespDto> result;
        try {
            PageHelper.offsetPage(subAccTradeDetailQueryReqDto.getCurrPageNum() * subAccTradeDetailQueryReqDto.getPageSize(),
                    subAccTradeDetailQueryReqDto.getPageSize());
            Page<SubAccTradeDetailDo> pageDate = (Page<SubAccTradeDetailDo>) tPaymentDetailMapper.subAccTradeDetailQuery(
                    BeanCopyUtils.objectConvert(subAccTradeDetailQueryReqDto, SubAccTradeDetailQueryReqDo.class));
            result = new Result<>(TradeQueryParamConvert.subAccTradeDetailConvertToList(pageDate));
            log.info("call 子账户收支明细查询结果信息：{}", result);
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("call 子账户收支明细查询发生异常，异常信息：{}", e);
        }
        log.info("call 子账户收支明细查询结束");
        return result;
    }


    /**
     * 根据用户号查询用户账户信息
     *
     * @param userNo     查询条件对象
     * @param userAccNo  用户账号
     * @param traceLogId 日志ID
     * @return 结果
     */
    @Override
    public Result<List<TUserPayeeAccountDto>> queryPayeeAccount(Long userNo, String userAccNo, String traceLogId) {
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("call 根据用户号查询用户账户信息开始，查询条件参数信息：userNo={},userAccNo={}", userNo, userAccNo);
        Result<List<TUserPayeeAccountDto>> result;
        try {
            if (userNo == null) {
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190211);
            }
            List<UserPayeeAccountDo> userPayeeAccountDoList = userAccountManager.queryPayeeAccount(userNo, userAccNo);
            result = new Result<>(BeanCopyUtils.listConvert(userPayeeAccountDoList, TUserPayeeAccountDto.class));
            log.info("call 结果信息：{}", result);
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("call 根据用户号查询用户账户信息发生异常，异常信息：{}", e);
        }
        log.info("call 根据用户号查询用户账户信息结束");
        return result;
    }

    /**
     * 根据用户号和币种查询账户
     *
     * @param userAccInfoReqDto 用户信息
     * @param traceLogId        日志ID
     * @return 结果
     */
    @Override
    public Result<List<UserStoreInfoRespDto>> queryUserStoreByCcy(UserAccInfoReqDto userAccInfoReqDto, String traceLogId) {
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("call 根据用户号和币种查询账户开始，查询条件参数信息：{}", userAccInfoReqDto);
        Result<List<UserStoreInfoRespDto>> result;
        try {
            //参数校验
            ParamValidate.validateParams(userAccInfoReqDto);
            UserAccInfoReqDo userAccInfoReqDo = BeanCopyUtils.objectConvert(userAccInfoReqDto, UserAccInfoReqDo.class);
            List<UserStoreInfoRespDo> list = userAccountManager.queryUserStoreByCcy(userAccInfoReqDo);

            result = new Result<>(BeanCopyUtils.listConvert(list, UserStoreInfoRespDto.class));
            log.info("call 结果信息：{}", result);
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("call 根据用户号和币种查询账户发生异常，异常信息：{}", e);
        }
        log.info("call 根据用户号和币种查询账户结束");
        return result;
    }
}
