package com.baofu.international.global.account.core.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.dal.model.ChannelWithdrawDetailsDo;
import com.baofu.international.global.account.core.dal.model.UserWithdrawDetailsDo;
import com.baofu.international.global.account.core.dal.model.WithdrawDetailsQueryDo;
import com.baofu.international.global.account.core.dal.model.WithdrawDistributeDo;
import com.baofu.international.global.account.core.facade.WithdrawDetailsQueryFacade;
import com.baofu.international.global.account.core.facade.model.ChannelWithdrawDetailsRespDto;
import com.baofu.international.global.account.core.facade.model.UserWithdrawDetailsRespDto;
import com.baofu.international.global.account.core.facade.model.WithdrawDetailsReqDto;
import com.baofu.international.global.account.core.facade.model.WithdrawDistributeRespDto;
import com.baofu.international.global.account.core.manager.UserWithdrawQueryManager;
import com.baofu.international.global.account.core.manager.WithdrawDetailsManager;
import com.google.common.collect.Lists;
import com.system.commons.result.PageRespDTO;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 提现明细管理后台查询接口 实现
 * <p>
 * 1.用户提现明细 后台查询
 * 2.渠道提现明细 后台查询
 * 3.提现下发 后台查询
 * </p>
 *
 * @author dxy
 * @date 2017/11/21
 */
@Slf4j
@Service
public class WithdrawDetailsQueryFacadeImpl implements WithdrawDetailsQueryFacade {

    /**
     * withdrawDetailsManager
     */
    @Autowired
    private WithdrawDetailsManager withdrawDetailsManager;

    /**
     * userWithdrawQueryManager
     */
    @Autowired
    private UserWithdrawQueryManager userWithdrawQueryManager;

    /**
     * 用户提现明细 后台查询 facade
     *
     * @param req WithdrawDetailsReqDto
     * @return PageResoDto
     */
    @Override
    public Result<PageRespDTO<UserWithdrawDetailsRespDto>> userWithdrawDetailsQuery(WithdrawDetailsReqDto req, String traceLogId) {
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("用户提现明细查询，请求参数 req:{}", req);
        Result<PageRespDTO<UserWithdrawDetailsRespDto>> result;
        PageRespDTO<UserWithdrawDetailsDo> respPage;
        try {
            ParamValidate.validateParams(req);
            WithdrawDetailsQueryDo reqQuery = new WithdrawDetailsQueryDo();
            BeanUtils.copyProperties(req, reqQuery);
            respPage = withdrawDetailsManager.userWithdrawDetailsQuery(reqQuery);
            List<UserWithdrawDetailsRespDto> withdrawDetailsRespDTOList = Lists.newArrayList();
            for (UserWithdrawDetailsDo temp : respPage.getResults()) {
                UserWithdrawDetailsRespDto respDTO = new UserWithdrawDetailsRespDto();
                BeanUtils.copyProperties(temp, respDTO);
                respDTO.setUserName(userWithdrawQueryManager.getRealNameByUserNo(Long.valueOf(temp.getUserNo())));
                withdrawDetailsRespDTOList.add(respDTO);
            }

            PageRespDTO<UserWithdrawDetailsRespDto> respPageRespDTO =
                    new PageRespDTO<>(respPage.getTotalSize(), req.getPageCount(), withdrawDetailsRespDTOList);
            result = new Result<>(respPageRespDTO);
            log.info("用户提现明细查询，返回参数 result:{},totalSize:{}", result.isSuccess(), result.getResult().getTotalSize());
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("用户提现明细查询异常", e);
        }
        return result;
    }


    /**
     * 渠道提现明细 后台查询 facade
     *
     * @param req WithdrawDetailsReqDto
     * @return PageResoDto
     */
    @Override
    public Result<PageRespDTO<ChannelWithdrawDetailsRespDto>> channelWithdrawDetailsQuery(WithdrawDetailsReqDto req, String traceLogId) {
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("渠道提现明细查询，请求参数 req:{}", req);
        Result<PageRespDTO<ChannelWithdrawDetailsRespDto>> result;
        PageRespDTO<ChannelWithdrawDetailsDo> respPage;
        try {
            ParamValidate.validateParams(req);
            WithdrawDetailsQueryDo reqQuery = new WithdrawDetailsQueryDo();
            BeanUtils.copyProperties(req, reqQuery);
            respPage = withdrawDetailsManager.channelWithdrawDetailsQuery(reqQuery);
            List<ChannelWithdrawDetailsRespDto> withdrawDetailsRespDTOList = Lists.newArrayList();
            for (ChannelWithdrawDetailsDo temp : respPage.getResults()) {
                ChannelWithdrawDetailsRespDto respDTO = new ChannelWithdrawDetailsRespDto();
                BeanUtils.copyProperties(temp, respDTO);
                respDTO.setUserName(userWithdrawQueryManager.getRealNameByUserNo(Long.valueOf(temp.getUserNo())));
                withdrawDetailsRespDTOList.add(respDTO);
            }

            PageRespDTO<ChannelWithdrawDetailsRespDto> respPageRespDTO =
                    new PageRespDTO<>(respPage.getTotalSize(), req.getPageCount(), withdrawDetailsRespDTOList);
            result = new Result<>(respPageRespDTO);
            log.info("渠道提现明细查询，返回参数 result:{},totalSize:{}", result.isSuccess(), result.getResult().getTotalSize());
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("渠道提现明细查询异常", e);
        }
        return result;
    }

    /**
     * 提现下发 后台查询 facade
     *
     * @param req WithdrawDetailsReqDto
     * @return PageResoDto
     */
    @Override
    public Result<PageRespDTO<WithdrawDistributeRespDto>> withdrawDistributeQuery(WithdrawDetailsReqDto req, String traceLogId) {
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("提现下发明细查询，请求参数 req:{}", req);
        Result<PageRespDTO<WithdrawDistributeRespDto>> result;
        PageRespDTO<WithdrawDistributeDo> respPage;
        try {
            ParamValidate.validateParams(req);
            WithdrawDetailsQueryDo reqQuery = new WithdrawDetailsQueryDo();
            BeanUtils.copyProperties(req, reqQuery);
            respPage = withdrawDetailsManager.withdrawDistributeQuery(reqQuery);
            List<WithdrawDistributeRespDto> withdrawDetailsRespDTOList = Lists.newArrayList();
            for (WithdrawDistributeDo temp : respPage.getResults()) {
                WithdrawDistributeRespDto respDTO = new WithdrawDistributeRespDto();
                BeanUtils.copyProperties(temp, respDTO);
                respDTO.setUserName(userWithdrawQueryManager.getRealNameByUserNo(Long.valueOf(temp.getUserNo())));
                withdrawDetailsRespDTOList.add(respDTO);
            }

            PageRespDTO<WithdrawDistributeRespDto> respPageRespDTO =
                    new PageRespDTO<>(respPage.getTotalSize(), req.getPageCount(), withdrawDetailsRespDTOList);
            result = new Result<>(respPageRespDTO);
            log.info("提现下发明细查询，返回参数 result:{},totalSize:{}", result.isSuccess(), result.getResult().getTotalSize());
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("提现下发明细查询异常", e);
        }
        return result;
    }
}
