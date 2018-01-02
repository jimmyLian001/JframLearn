package com.baofu.international.global.account.core.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.dal.model.UserWithdrawApplyDo;
import com.baofu.international.global.account.core.dal.model.UserWithdrawSumDo;
import com.baofu.international.global.account.core.facade.UserWithdrawQueryFacade;
import com.baofu.international.global.account.core.facade.model.user.PageDataRespDto;
import com.baofu.international.global.account.core.facade.model.UserWithdrawApplyQueryReqDto;
import com.baofu.international.global.account.core.facade.model.UserWithdrawSumQueryReqDto;
import com.baofu.international.global.account.core.manager.UserWithdrawQueryManager;
import com.baofu.international.global.account.core.manager.UserWithdrawRelationManager;
import com.baofu.international.global.account.core.service.convert.UserWithdrawQueryConvert;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description:用户提现查询 FacadeImpl
 * <p/>
 * Created by liy on 2017/11/22 ProjectName：account
 */
@Slf4j
@Service
public class UserWithdrawQueryFacadeImpl implements UserWithdrawQueryFacade {

    /**
     * 提现关系信息记录
     */
    @Autowired
    private UserWithdrawRelationManager relationManager;


    /**
     * 提现申请
     */
    @Autowired
    private UserWithdrawQueryManager queryManager;


    /**
     * 用户提现汇总查询
     *
     * @param reqDto 查询条件对象
     * @param logId  日志ID
     * @return 结果集
     */
    @Override
    public Result<PageDataRespDto> userWithdrawSumQuery(UserWithdrawSumQueryReqDto reqDto, String logId) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        log.error("用户提现汇总查询,参数:{}", reqDto);
        Result<PageDataRespDto> result;
        try {
            ParamValidate.validateParams(reqDto);
            UserWithdrawSumDo sumDo = UserWithdrawQueryConvert.toUserWithdrawSumDo(reqDto);
            PageHelper.offsetPage(sumDo.getStartRow(), sumDo.getPageSize());
            Page<UserWithdrawSumDo> pageDate = (Page<UserWithdrawSumDo>) queryManager
                    .selectUserWithdrawSumPageInfo(sumDo);
            result = new Result<>(UserWithdrawQueryConvert.convertToSumList(pageDate));
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("用户提现汇总查询,异常", e);
        }
        log.info("用户提现汇总查询,结果:{},{}条", result.isSuccess(), result.getResult().getResultList().size());
        return result;
    }

    /**
     * 用户提现申请查询
     *
     * @param reqDto 查询条件对象
     * @param logId  日志ID
     * @return 结果集
     */
    @Override
    public Result<PageDataRespDto> userWithdrawApplyQuery(UserWithdrawApplyQueryReqDto reqDto, String logId) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, logId);
        log.info("用户提现申请查询,参数:{}", reqDto);
        Result<PageDataRespDto> result;
        try {
            ParamValidate.validateParams(reqDto);
            List<Long> withdrawId = relationManager.queryWithdrawIdByBatchNo(reqDto.getWithdrawBatchId());
            UserWithdrawApplyDo applyDo = UserWithdrawQueryConvert.toUserWithdrawApplyDo(reqDto, withdrawId);
            PageHelper.offsetPage(applyDo.getStartRow(), applyDo.getPageSize());
            Page<UserWithdrawApplyDo> pageDate = (Page<UserWithdrawApplyDo>) queryManager
                    .selectUserWithdrawApplyPageInfo(applyDo, withdrawId);
            result = new Result<>(UserWithdrawQueryConvert.convertToApplyList(pageDate));
        } catch (Exception e) {
            result = ExceptionUtils.getResponse(e);
            log.error("用户提现申请查询,异常", e);
        }
        log.info("用户提现申请查询,结果:{},{}条", result.isSuccess(), result.getResult().getResultList().size());
        return result;
    }
}
