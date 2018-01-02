package com.baofu.international.global.account.core.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.CardBinBiz;
import com.baofu.international.global.account.core.biz.models.CardBinInfoRespBo;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.facade.CardBinFacade;
import com.baofu.international.global.account.core.facade.model.CardBinInfoRespDto;
import com.baofu.international.global.account.core.service.convert.CardBinConvert;
import com.system.commons.exception.BizServiceException;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 卡bin操作接口
 * <p>
 * 1,根据卡bin查询数据
 * </p>
 *
 * @author : hetao  Date: 2017/11/04 ProjectName: globalaccount Version: 1.0
 */
@Slf4j
@Service
public class CardBinFacadeImpl implements CardBinFacade {

    /**
     * 卡bin操作服务接口
     */
    @Autowired
    private CardBinBiz cardBinBiz;

    /**
     * 根据卡bin查询数据
     *
     * @param cardBin    卡bin
     * @param traceLogId 日志ID
     */
    @Override
    public Result<CardBinInfoRespDto> queryCardBin(String cardBin, String traceLogId) {
        Result<CardBinInfoRespDto> result;
        try {
            MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
            log.info("根据卡bin查询数据,param:{}", cardBin);

            if (StringUtils.isEmpty(cardBin)) {
                throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190014);
            }

            CardBinInfoRespBo cardBinInfoRespBo = cardBinBiz.queryCardBin(cardBin);
            result = new Result<>(CardBinConvert.cardBinRespConvert(cardBinInfoRespBo));
        } catch (Exception e) {
            log.error("根据卡bin查询数据异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("根据卡bin查询结果:{}", result);
        return result;
    }
}
