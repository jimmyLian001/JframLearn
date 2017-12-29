package com.baofu.cbpayservice.service.convert;

import com.baofoo.cbcgw.facade.dto.gw.request.CgwVerifyMerchantReqDto;
import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwExchangeRateResultDto;
import com.baofu.cbpayservice.biz.models.CbPayRemittanceAuditReqBo;
import com.baofu.cbpayservice.biz.models.CbPayRemittanceBankFeeReqBo;
import com.baofu.cbpayservice.biz.models.CbPayRemittanceReqBo;
import com.baofu.cbpayservice.biz.models.CbPayRemtStatusChangeReqBo;
import com.baofu.cbpayservice.dal.models.FiCbPayMemberApiRqstDo;
import com.baofu.cbpayservice.facade.models.*;
import com.baofu.cbpayservice.facade.models.res.CbPayFeeRespDto;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service层参数转换
 * <p>
 * 1、跨境人民币汇款接口请求参数转换成Biz层请求参数信息
 * </p>
 * User: wanght Date:2016/11/10 ProjectName: cbpays-ervice Version: 1.0
 */
public class CbPayRemittanceConvert {

    /**
     * 跨境汇款订单审核接口请求参数转换成Biz层请求参数信息
     *
     * @param cbPayRemittanceAuditReqDto 跨境汇款订单审核接口请求参数
     * @return Biz层请求参数信息
     */
    public static CbPayRemittanceAuditReqBo auditParamConvert(CbPayRemittanceAuditReqDto cbPayRemittanceAuditReqDto) {
        CbPayRemittanceAuditReqBo cbPayRemittanceAuditReqBo = new CbPayRemittanceAuditReqBo();
        cbPayRemittanceAuditReqBo.setMemberId(cbPayRemittanceAuditReqDto.getMemberId());
        cbPayRemittanceAuditReqBo.setAuditBy(cbPayRemittanceAuditReqDto.getAuditBy());
        cbPayRemittanceAuditReqBo.setAuditStatus(cbPayRemittanceAuditReqDto.getAuditStatus());
        cbPayRemittanceAuditReqBo.setBatchNo(cbPayRemittanceAuditReqDto.getBatchNo());
        return cbPayRemittanceAuditReqBo;
    }

    /**
     * 跨境汇款订单状态更新接口请求参数转换成Biz层请求参数信息
     *
     * @param cbPayRemtStatusChangeReqDto 跨境汇款订单审核接口请求参数
     * @return Biz层请求参数信息
     */
    public static CbPayRemtStatusChangeReqBo statusChangeParamConvert(CbPayRemtStatusChangeReqDto cbPayRemtStatusChangeReqDto) {
        CbPayRemtStatusChangeReqBo cbPayRemtStatusChangeReqBo = new CbPayRemtStatusChangeReqBo();
        cbPayRemtStatusChangeReqBo.setBatchNo(cbPayRemtStatusChangeReqDto.getBatchNo());
        cbPayRemtStatusChangeReqBo.setMemberId(cbPayRemtStatusChangeReqDto.getMemberId());
        cbPayRemtStatusChangeReqBo.setAuditStatus(cbPayRemtStatusChangeReqDto.getAuditStatus());
        cbPayRemtStatusChangeReqBo.setChannelStatus(cbPayRemtStatusChangeReqDto.getChannelStatus());
        cbPayRemtStatusChangeReqBo.setUpdateBy(cbPayRemtStatusChangeReqDto.getUpdateBy());
        cbPayRemtStatusChangeReqBo.setBeforeAuditStatus(cbPayRemtStatusChangeReqDto.getBeforeAuditStatus());
        cbPayRemtStatusChangeReqBo.setRemarks(cbPayRemtStatusChangeReqDto.getRemarks());
        return cbPayRemtStatusChangeReqBo;
    }

    /**
     * 跨境汇款订单接口请求参数转换成Biz层请求参数信息
     *
     * @param cbPayRemittanceOrderReqDto 跨境汇款订单接口请求参数
     * @return Biz层请求参数信息
     */
    public static CbPayRemittanceReqBo orderParamConvertV2(CbPayRemittanceOrderReqV2Dto cbPayRemittanceOrderReqDto) {

        CbPayRemittanceReqBo cbPayRemittanceReqBo = new CbPayRemittanceReqBo();
        cbPayRemittanceReqBo.setMemberId(cbPayRemittanceOrderReqDto.getMemberId());
        cbPayRemittanceReqBo.setCreateBy(cbPayRemittanceOrderReqDto.getCreateBy());
        cbPayRemittanceReqBo.setRemark(cbPayRemittanceOrderReqDto.getRemark());
        cbPayRemittanceReqBo.setBatchFileIdList(cbPayRemittanceOrderReqDto.getBatchFileIdList());
        cbPayRemittanceReqBo.setTargetCcy(cbPayRemittanceOrderReqDto.getTargetCcy());
        cbPayRemittanceReqBo.setEntityNo(cbPayRemittanceOrderReqDto.getEntityNo());
        // 跨境人民币汇款
        cbPayRemittanceReqBo.setOrderType("14");
        // 订单版本(1：v3.4  2：v4.0)
        cbPayRemittanceReqBo.setOrderVersion(2);

        return cbPayRemittanceReqBo;
    }

    /**
     * 查汇参数转换
     *
     * @param exchangeRate     渠道返回对象
     * @param totalTranceMoney 出款金额
     * @param totalFeeMoney    交易手续费
     * @param totalAllMoney    交易总金额
     * @param balanceAllMoney  结算金额
     * @param accountMoney     账户余额
     * @param rate             费率
     * @return CbPayFeeRespDto
     */
    public static CbPayFeeRespDto feeParamConvert(CgwExchangeRateResultDto exchangeRate, BigDecimal totalTranceMoney,
                                                  BigDecimal totalFeeMoney, BigDecimal totalAllMoney,
                                                  BigDecimal balanceAllMoney, BigDecimal accountMoney, BigDecimal rate) {

        CbPayFeeRespDto cbPayFeeRespDto = new CbPayFeeRespDto();
        BigDecimal bd = new BigDecimal("100");
        cbPayFeeRespDto.setSourceCurrency(exchangeRate.getSourceCurrency());
        cbPayFeeRespDto.setTargetCurrency(exchangeRate.getTargetCurrency());
        cbPayFeeRespDto.setBuyRateOfCcy(exchangeRate.getBuyRateOfCcy().divide(bd, 8, BigDecimal.ROUND_DOWN));
        cbPayFeeRespDto.setSellRateOfCcy(rate);
        cbPayFeeRespDto.setBuyRateOfCash(exchangeRate.getBuyRateOfCash().divide(bd, 8, BigDecimal.ROUND_DOWN));
        cbPayFeeRespDto.setSellRateOfCash(exchangeRate.getSellRateOfCash().divide(bd, 8, BigDecimal.ROUND_DOWN));

        cbPayFeeRespDto.setTransTotalMoney(totalAllMoney);
        cbPayFeeRespDto.setTransMoney(totalTranceMoney);
        cbPayFeeRespDto.setTransFee(totalFeeMoney);
        cbPayFeeRespDto.setBalanceMoney(balanceAllMoney);
        cbPayFeeRespDto.setAccountMoney(accountMoney);

        return cbPayFeeRespDto;
    }

    /**
     * 付汇入账费用更新接口请求参数转换成Biz层请求参数信息
     *
     * @param cbPayRemittanceBankFeeReqDto 付汇入账费用更新请求参数
     * @return Biz层请求参数信息
     */
    public static CbPayRemittanceBankFeeReqBo bankFeeParamConvert(CbPayRemittanceBankFeeReqDto cbPayRemittanceBankFeeReqDto) {

        CbPayRemittanceBankFeeReqBo cbPayRemittanceBankFeeReqBo = new CbPayRemittanceBankFeeReqBo();
        cbPayRemittanceBankFeeReqBo.setMemberId(cbPayRemittanceBankFeeReqDto.getMemberId());
        cbPayRemittanceBankFeeReqBo.setBatchNo(cbPayRemittanceBankFeeReqDto.getBatchNo());
        cbPayRemittanceBankFeeReqBo.setBankFee(cbPayRemittanceBankFeeReqDto.getBankFee());
        cbPayRemittanceBankFeeReqBo.setUpdateBy(cbPayRemittanceBankFeeReqDto.getUpdateBy());
        cbPayRemittanceBankFeeReqBo.setRemarks(cbPayRemittanceBankFeeReqDto.getRemarks());
        cbPayRemittanceBankFeeReqBo.setBankFeeStatus(cbPayRemittanceBankFeeReqDto.getBankFeeStatus());
        cbPayRemittanceBankFeeReqBo.setReceiptId(cbPayRemittanceBankFeeReqDto.getReceiptId());

        return cbPayRemittanceBankFeeReqBo;
    }

    /**
     * 商户备案检测接口请求参数转换
     *
     * @param cbPayRemittanceOrderReqV2Dto 付汇入账费用更新请求参数
     * @param channelId                    渠道号
     * @return 商户备案检测接口请求DTO
     */
    public static CgwVerifyMerchantReqDto verifyMerchantParamConvert(CbPayRemittanceOrderReqV2Dto cbPayRemittanceOrderReqV2Dto, Long channelId) {

        CgwVerifyMerchantReqDto cgwVerifyMerchantReqDto = new CgwVerifyMerchantReqDto();
        cgwVerifyMerchantReqDto.setChannelId(channelId.intValue());
        cgwVerifyMerchantReqDto.setMemberId(cbPayRemittanceOrderReqV2Dto.getMemberId().toString());
        cgwVerifyMerchantReqDto.setChildMemberId(cbPayRemittanceOrderReqV2Dto.getEntityNo());
        cgwVerifyMerchantReqDto.setCurrency(cbPayRemittanceOrderReqV2Dto.getTargetCcy());

        return cgwVerifyMerchantReqDto;
    }

    /**
     * 商户备案检测接口请求参数转换
     *
     * @param apiCbPayRemittanceOrderReqDto 付汇入账费用更新请求参数
     * @param channelId                     渠道号
     * @param entityNo                      商戶备案主体编号
     * @return 商户备案检测接口请求DTO
     */
    public static CgwVerifyMerchantReqDto verifyMerchantParamConvert(ApiCbPayRemittanceOrderReqDto apiCbPayRemittanceOrderReqDto,
                                                                     Long channelId, String entityNo) {

        CgwVerifyMerchantReqDto cgwVerifyMerchantReqDto = new CgwVerifyMerchantReqDto();
        cgwVerifyMerchantReqDto.setChannelId(channelId.intValue());
        cgwVerifyMerchantReqDto.setMemberId(apiCbPayRemittanceOrderReqDto.getMemberId().toString());
        cgwVerifyMerchantReqDto.setChildMemberId(entityNo);
        cgwVerifyMerchantReqDto.setCurrency(apiCbPayRemittanceOrderReqDto.getRemitCcy());

        return cgwVerifyMerchantReqDto;
    }


    /**
     * 将List<String> ->  List<Long>
     *
     * @param fileBatchNos 文件批次号
     * @return 结果
     */
    public static List<Long> strToLong(List<String> fileBatchNos) {
        List<Long> list = Lists.newArrayList();
        for (String fileBatchNo : fileBatchNos) {
            list.add(Long.parseLong(fileBatchNo));
        }
        return list;
    }

    /**
     * 跨境汇款订单接口请求参数转换成Biz层请求参数信息
     *
     * @param apiCbPayRemittanceOrderReqDto 跨境汇款订单接口请求参数
     * @param entityNo                      商户备案主体编号
     * @param fileBatchNos                  文件批次号
     * @return Biz层请求参数信息
     */
    public static CbPayRemittanceReqBo orderParamConvertApi(ApiCbPayRemittanceOrderReqDto apiCbPayRemittanceOrderReqDto,
                                                            List<Long> fileBatchNos, String entityNo) {

        CbPayRemittanceReqBo cbPayRemittanceReqBo = new CbPayRemittanceReqBo();
        cbPayRemittanceReqBo.setMemberId(apiCbPayRemittanceOrderReqDto.getMemberId());
        cbPayRemittanceReqBo.setCreateBy("SYSTEM");
        cbPayRemittanceReqBo.setRemark("");
        cbPayRemittanceReqBo.setBatchFileIdList(fileBatchNos);
        cbPayRemittanceReqBo.setTargetCcy(apiCbPayRemittanceOrderReqDto.getRemitCcy());
        cbPayRemittanceReqBo.setEntityNo(entityNo);
        // 跨境人民币汇款
        cbPayRemittanceReqBo.setOrderType("14");
        // 订单版本(1：v3.4  2：v4.0)
        cbPayRemittanceReqBo.setOrderVersion(2);
        //商户请求宝付系统的申请订单号，由商户保证此单号唯一
        cbPayRemittanceReqBo.setRemitApplyNo(apiCbPayRemittanceOrderReqDto.getRemitApplyNo());
        cbPayRemittanceReqBo.setOriginalCcy(apiCbPayRemittanceOrderReqDto.getOriginalCcy());
        cbPayRemittanceReqBo.setOriginalAmt(apiCbPayRemittanceOrderReqDto.getOriginalAmt());
        cbPayRemittanceReqBo.setBankAccName(apiCbPayRemittanceOrderReqDto.getBankAccName());
        cbPayRemittanceReqBo.setBankAccNo(apiCbPayRemittanceOrderReqDto.getBankAccNo());
        cbPayRemittanceReqBo.setBankName(apiCbPayRemittanceOrderReqDto.getBankName());
        cbPayRemittanceReqBo.setRemitCcy(apiCbPayRemittanceOrderReqDto.getRemitCcy());
        cbPayRemittanceReqBo.setTerminalId(apiCbPayRemittanceOrderReqDto.getTerminalId());
        cbPayRemittanceReqBo.setNotifyUrl(apiCbPayRemittanceOrderReqDto.getNotifyUrl());
        return cbPayRemittanceReqBo;
    }

    /**
     * 保存商户请求数据
     *
     * @param apiCbPayRemittanceOrderReqDto 商户请求参数
     * @return 结果
     */
    public static FiCbPayMemberApiRqstDo convertApiReqParam(ApiCbPayRemittanceOrderReqDto apiCbPayRemittanceOrderReqDto) {
        FiCbPayMemberApiRqstDo fiCbPayMemberApiRqstDo = new FiCbPayMemberApiRqstDo();
        fiCbPayMemberApiRqstDo.setMemberId(apiCbPayRemittanceOrderReqDto.getMemberId());
        fiCbPayMemberApiRqstDo.setTerminalId(apiCbPayRemittanceOrderReqDto.getTerminalId());
        fiCbPayMemberApiRqstDo.setMemberReqId(apiCbPayRemittanceOrderReqDto.getRemitApplyNo());
        fiCbPayMemberApiRqstDo.setBusinessType("12");
        fiCbPayMemberApiRqstDo.setNotifyUrl(apiCbPayRemittanceOrderReqDto.getNotifyUrl());
        fiCbPayMemberApiRqstDo.setProcessStatus(1);
        return fiCbPayMemberApiRqstDo;
    }

    /**
     * 商户备案检测接口请求参数转换
     *
     * @param batchRemitDto 付汇入账费用更新请求参数
     * @param channelId     渠道号
     * @return 商户备案检测接口请求DTO
     */
    public static CgwVerifyMerchantReqDto verifyMerchantConvert(BatchRemitDto batchRemitDto, Long channelId) {

        CgwVerifyMerchantReqDto cgwVerifyMerchantReqDto = new CgwVerifyMerchantReqDto();
        cgwVerifyMerchantReqDto.setChannelId(channelId.intValue());
        cgwVerifyMerchantReqDto.setCurrency(batchRemitDto.getTargetCcy());
        cgwVerifyMerchantReqDto.setMemberId(batchRemitDto.getMemberId().toString());
        cgwVerifyMerchantReqDto.setChildMemberId(batchRemitDto.getEntityNo());

        return cgwVerifyMerchantReqDto;
    }
}
