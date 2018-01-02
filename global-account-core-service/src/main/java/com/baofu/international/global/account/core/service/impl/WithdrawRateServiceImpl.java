package com.baofu.international.global.account.core.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.dal.mapper.UserWithdrawRateMapper;
import com.baofu.international.global.account.core.dal.model.WithdrawRateReqDo;
import com.baofu.international.global.account.core.dal.model.WithdrawRateRespDo;
import com.baofu.international.global.account.core.facade.WithdrawRateFacade;
import com.baofu.international.global.account.core.facade.model.user.PageDataRespDto;
import com.baofu.international.global.account.core.facade.model.WithdrawRateReqDto;
import com.baofu.international.global.account.core.facade.model.WithdrawRateRespDto;
import com.baofu.international.global.account.core.manager.OrderIdManager;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yimo on 2017/11/22.
 */
@Service
@Slf4j
public class WithdrawRateServiceImpl implements WithdrawRateFacade {
    @Autowired
    private UserWithdrawRateMapper userWithdrawRateMapper;
    @Autowired
    private OrderIdManager orderIdManager;

    @Override
    public Result<PageDataRespDto> searchWithdrawRateList(WithdrawRateReqDto reqDto) {
        log.info("call 提现费率开始，查询条件参数信息：{}", reqDto);
        Result<PageDataRespDto> result;
        try {
            // 校验数据是否合法
            ParamValidate.validateParams(reqDto);
            PageHelper.offsetPage(reqDto.getPageNo() * reqDto.getPageSize(), reqDto.getPageSize());
            Page<WithdrawRateRespDo> pageDate =
                    (Page<WithdrawRateRespDo>) userWithdrawRateMapper.searchWithdrawRateList(convertToDo(reqDto));
            result = new Result<>(convertToList(pageDate));
            log.info("call 结果信息：{}", result);
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("call 提现费率查询发生异常，异常信息：{}", e);
        }
        log.info("call 提现费率查询结束");
        return result;
    }

    @Override
    @Transactional
    public Result<Boolean> editWithdrawRate(WithdrawRateReqDto reqDto) {
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, reqDto.getTranceLogID());
        Result<Boolean> result;
        log.info("global-service-提现费率查询信息请求信息:{}", reqDto);
        try {
            userWithdrawRateMapper.updateByOrderId(editDtoToDo(reqDto));
            result = new Result<>(Boolean.TRUE);

        } catch (Exception e) {
            log.error("global-service-提现费率查询异常：", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("global-service-提现费率查询结果：{}", result);
        return result;
    }

    @Override
    @Transactional
    public Result<Boolean> addWithdrawRate(WithdrawRateReqDto reqDto) {
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, reqDto.getTranceLogID());
        Result<Boolean> result;
        log.info("global-service-提现费率查询信息请求信息:{}", reqDto);
        try {
            userWithdrawRateMapper.addWithdrawRate(addDtoconvertToDo(reqDto));
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("global-service-提现费率查询异常：", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("global-service-提现费率查询结果：{}", result);
        return result;
    }

    /**
     * WithdrawRateReqDto——》WithdrawRateReqDo
     *
     * @param reqDto 查询条件信息
     * @return 结果
     */
    private WithdrawRateReqDo convertToDo(WithdrawRateReqDto reqDto) {
        if (reqDto == null) {
            return null;
        }
        WithdrawRateReqDo reqDo = new WithdrawRateReqDo();
        reqDo.setPageNo(reqDto.getPageNo());
        reqDo.setUserType(reqDto.getUserType());
        reqDo.setUserNo(reqDto.getUserNo());
        reqDo.setRateSetState(reqDto.getRateSetState());
        reqDo.setBankAccCcy(reqDto.getBankAccCcy());
        reqDo.setRate(reqDto.getRate());
        return reqDo;
    }

    /**
     * WithdrawRateReqDto——》WithdrawRateReqDo
     *
     * @param reqDto 新增条件信息
     * @return 结果
     */
    private WithdrawRateReqDo addDtoconvertToDo(WithdrawRateReqDto reqDto) {
        if (reqDto == null) {
            return null;
        }
        WithdrawRateReqDo reqDo = new WithdrawRateReqDo();
        reqDo.setUserNo(reqDto.getUserNo());
        reqDo.setBankAccCcy(reqDto.getBankAccCcy());
        List<WithdrawRateRespDo> withdrawRateRespDolist = userWithdrawRateMapper.searchWithdrawRateList(reqDo);
        if (withdrawRateRespDolist.isEmpty()) {
            reqDo.setUserType(reqDto.getUserType());
            reqDo.setUserName(reqDto.getUserName());
            reqDo.setRateSetState(reqDto.getRateSetState());
            reqDo.setRate(reqDto.getRate());

            Long recordId = orderIdManager.orderIdCreate();
            reqDo.setRecordId(recordId);
            reqDo.setCreateBy(reqDto.getCreateBy());
            reqDo.setUpdateBy(reqDto.getUpdateBy());
        } else {
            throw new BizServiceException(CommonErrorCode.PARAMETER_VALID_NOT_PASS, "新增的用户数据已存在");
        }
        return reqDo;
    }

    /**
     * WithdrawRateReqDto——》WithdrawRateReqDo
     *
     * @param reqDto 修改条件信息
     * @return 结果
     */
    private WithdrawRateReqDo editDtoToDo(WithdrawRateReqDto reqDto) {
        if (reqDto == null) {
            return null;
        }
        WithdrawRateReqDo reqDo = new WithdrawRateReqDo();
        reqDo.setUserNo(reqDto.getUserNo());
        reqDo.setRateSetState(reqDto.getRateSetState());
        reqDo.setRate(reqDto.getRate());
        reqDo.setBankAccCcy(reqDto.getBankAccCcy());
        reqDo.setRecordId(reqDto.getRecordId());
        reqDo.setUpdateBy(reqDto.getUpdateBy());
        return reqDo;
    }

    /**
     * 参数转换   List<WithdrawRateRespDo> ——》List<WithdrawRateRespDto>
     *
     * @param pageData 结果参数
     * @return 结果
     */
    private PageDataRespDto convertToList(Page<WithdrawRateRespDo> pageData) {
        PageDataRespDto<WithdrawRateRespDto> pageDataRespDto = new PageDataRespDto<>();
        ArrayList<WithdrawRateRespDto> list = Lists.newArrayList();
        for (WithdrawRateRespDo tradeAccQueryDo : pageData.getResult()) {
            WithdrawRateRespDto withdrawRateRespDto = convertToDto(tradeAccQueryDo);
            list.add(withdrawRateRespDto);
        }
        pageDataRespDto.setResultList(list);
        //当前页
        pageDataRespDto.setPageNum(pageData.getPageNum());
        pageDataRespDto.setTotal(pageData.getTotal());
        pageDataRespDto.setPages(pageData.getPages());
        return pageDataRespDto;
    }

    /**
     * 参数转换  do ——》dto
     *
     * @param reqDo do参数
     * @return 结果
     */
    public static WithdrawRateRespDto convertToDto(WithdrawRateRespDo reqDo) {
        if (reqDo == null) {
            return null;
        }
        WithdrawRateRespDto reqDto = new WithdrawRateRespDto();
        reqDto.setRateSetState(reqDo.getRateSetState());
        reqDto.setUserType(reqDo.getUserType());
        reqDto.setUserNo(reqDo.getUserNo());
        reqDto.setUserName(reqDo.getUserName());
        reqDto.setRate(reqDo.getRate());
        reqDto.setBankAccCcy(reqDo.getBankAccCcy());
        reqDto.setRecordId(reqDo.getRecordId());
        return reqDto;
    }
}
