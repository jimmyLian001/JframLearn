package com.baofu.cbpayservice.biz.integration.cm;

import com.baofoo.clearing.facade.CmQueryService;
import com.baofoo.clearing.facade.CmService;
import com.baofoo.clearing.facade.enums.CmResultEnum;
import com.baofoo.clearing.facade.model.AccountBalanceReqDTO;
import com.baofoo.clearing.facade.model.ClearingRequestV2DTO;
import com.baofoo.clearing.facade.model.ClearingResponseDTO;
import com.baofoo.clearing.facade.model.Result;
import com.baofu.cbpayservice.biz.convert.CmParamConvert;
import com.baofu.cbpayservice.biz.models.ClearRequestBo;
import com.baofu.cbpayservice.biz.models.ClearingResponseBo;
import com.baofu.cbpayservice.biz.models.QueryBalanceBo;
import com.baofu.cbpayservice.common.enums.ClearAccResultEnum;
import com.system.commons.exception.BaseException;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 1、封装清算
 * 2、计费
 * 3、余额查询
 * </p>
 * User: yangjian  Date: 2017-05-23 ProjectName:cbpay-service  Version: 1.0
 */
@Slf4j
@Service
public class CmClearBizImpl {


    /**
     * 清算服务中心
     */
    @Autowired
    private CmService cmService;

    /**
     * 清算查询接口
     */
    @Autowired
    private CmQueryService cmQueryService;


    /**
     * 调用清算-收单服务
     *
     * @param clearRequestBo 清算服务参数
     * @return 返回清算结果
     */
    public ClearingResponseBo acquiringAccount(ClearRequestBo clearRequestBo) {

        try {
            ClearingRequestV2DTO clearingRequestV2DTO = CmParamConvert.cmRequestConvert(clearRequestBo);
            log.info("调用V2清算-收单服务clearingRequestV2DTO请求参数：{}", clearingRequestV2DTO);
            Result<ClearingResponseDTO> result = cmService.acquiringCmServiceRequestV2(clearingRequestV2DTO);
            log.info("调用V2清算-收单服务Result返回参数：{}", result);
            if (!CmResultEnum.CODE_SUCCESS.getCode().equals(result.getResult().getCmResultEnum().getCode())) {
                log.error("调用清算系统失败：{}", result);
                throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, result.getErrorMsg());
            }
            ClearingResponseBo clearingResponseBo = CmParamConvert.cmResponseConvert(result.getResult());
            log.info("转换返回参数result:{}", clearingResponseBo);
            return clearingResponseBo;
        } catch (BaseException e) {
            log.error("调用清算系统失败,失败异常：", e);
            throw e;
        } catch (Exception e) {
            log.error("调用v2清算-转账服务异常", e);
            throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, "请求清算系统异常");
        }
    }

    /**
     * 调用v2清算-转账服务
     *
     * @param clearRequestBo 清算服务参数
     * @return 返回清算结果
     */
    public ClearingResponseBo transferAccount(ClearRequestBo clearRequestBo) {

        ClearingResponseBo result;
        try {
            ClearingRequestV2DTO clearingRequestV2DTO = CmParamConvert.cmRequestConvert(clearRequestBo);
            log.info("调用V2清算-转账服务clearingRequestV2DTO请求参数：{}", clearingRequestV2DTO);
            Result<ClearingResponseDTO> clearingResponseResult = cmService.transferCmServiceRequestV2(clearingRequestV2DTO);
            log.info("调用V2清算-转账服务Result返回参数：{}", clearingResponseResult);
            result = CmParamConvert.cmResponseConvert(clearingResponseResult.getResult());
            log.info("转换返回参数result:{}", result);
            if (!ClearAccResultEnum.CODE_SUCCESS.getCode().equals(result.getClearAccResultEnum().getCode())) {
                log.info("调用清算系统失败：{}", result.getClearAccResultEnum().getDesc());
                throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, clearingResponseResult.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("调用v2清算-转账服务异常：{}", e);
            throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, "请求清算系统异常");
        }
        return result;

    }

    /**
     * 查询会员账户余额
     *
     * @param queryBalanceBo 对象里的参数 会员Id，账户类型
     * @return 返回结果
     */
    public BigDecimal queryBalance(QueryBalanceBo queryBalanceBo) {
        try {
            AccountBalanceReqDTO accountBalanceReqDTO = CmParamConvert.cmRequestConvert(queryBalanceBo);
            log.info("调用余额查询服务,accountBalanceReqDTO请求参数为：{}", accountBalanceReqDTO);
            Result<BigDecimal> result = cmQueryService.queryAccountBalance(accountBalanceReqDTO);
            log.info("调用余额查询服务,返回结果信息：{}", result);
            if (!result.isSuccess() || result.getResult() == null) {
                throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, "查询账户余额信息异常");
            }
            return result.getResult();
        } catch (Exception e) {
            log.error("请求账户余额查询异常：{}", e);
            throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, "请求账户余额系统异常");
        }
    }
}
