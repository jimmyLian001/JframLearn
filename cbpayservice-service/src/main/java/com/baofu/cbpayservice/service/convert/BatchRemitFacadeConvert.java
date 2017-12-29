package com.baofu.cbpayservice.service.convert;

import com.baofu.cbpayservice.biz.models.*;
import com.baofu.cbpayservice.common.constants.NumberConstants;
import com.baofu.cbpayservice.common.enums.CcyEnum;
import com.baofu.cbpayservice.common.enums.UploadFileType;
import com.baofu.cbpayservice.facade.models.BatchRemitDto;
import com.baofu.cbpayservice.facade.models.BatchRemitTrialDto;
import com.baofu.cbpayservice.facade.models.ProxyCustomsDto;
import com.baofu.cbpayservice.facade.models.res.BatchRemitTrialRespDto;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.List;

/**
 * 批量汇款接口参数转换
 * <p>
 * </p>
 * User: 不良人 Date:2017/10/20 ProjectName: cbpayservice Version: 1.0
 */
public final class BatchRemitFacadeConvert {

    private BatchRemitFacadeConvert() {
    }

    /**
     * 批量汇款文件上传信息
     *
     * @param proxyCustomsDto 文件参数
     * @return 返回参数
     */
    public static RemitFileBo toRemitFileBo(ProxyCustomsDto proxyCustomsDto) {
        RemitFileBo remitFileBo = new RemitFileBo();
        remitFileBo.setMemberId(proxyCustomsDto.getMemberId());
        remitFileBo.setDfsFileId(proxyCustomsDto.getDfsFileId());
        remitFileBo.setFileType(UploadFileType.BATCH_REMIT_FILE.getCode());
        remitFileBo.setFileName(proxyCustomsDto.getFileName());
        remitFileBo.setCreateBy(proxyCustomsDto.getCreateBy());
        return remitFileBo;
    }

    /**
     * facade层参数转换成biz请求参数
     *
     * @param batchRemitDto 请求参数
     * @param status        文件状态
     * @param fileBatchNo   文件批次号
     * @return 转换结果
     */
    public static CbPayBatchFileUpLoadBo batchUpdateStatusConvert(BatchRemitDto batchRemitDto, String status,
                                                                  Long fileBatchNo) {
        List<Long> fileBatchNoList = Lists.newArrayList();
        fileBatchNoList.add(fileBatchNo);
        CbPayBatchFileUpLoadBo cbPayFileUpLoadBo = new CbPayBatchFileUpLoadBo();
        cbPayFileUpLoadBo.setMemberId(batchRemitDto.getMemberId());
        cbPayFileUpLoadBo.setBatchFileIdList(fileBatchNoList);
        cbPayFileUpLoadBo.setStatus(status);
        cbPayFileUpLoadBo.setUpdateBy(batchRemitDto.getCreateBy());
        return cbPayFileUpLoadBo;
    }

    /**
     * 跨境汇款订单接口请求参数转换成Biz层请求参数信息
     *
     * @param batchRemitDto 跨境汇款订单接口请求参数
     * @param fileBatchNo   文件批次号
     * @return Biz层请求参数信息
     */
    public static Object orderParamConvertV2(BatchRemitDto batchRemitDto, Long fileBatchNo) {
        List<Long> fileBatchNoList = Lists.newArrayList();
        fileBatchNoList.add(fileBatchNo);
        CbPayRemittanceReqBo cbPayRemittanceReqBo = new CbPayRemittanceReqBo();
        cbPayRemittanceReqBo.setMemberId(batchRemitDto.getMemberId());
        cbPayRemittanceReqBo.setCreateBy(batchRemitDto.getCreateBy());
        cbPayRemittanceReqBo.setBatchFileIdList(fileBatchNoList);
        cbPayRemittanceReqBo.setTargetCcy(batchRemitDto.getTargetCcy());
        cbPayRemittanceReqBo.setEntityNo(batchRemitDto.getEntityNo());
        cbPayRemittanceReqBo.setSourceType(NumberConstants.ONE);
        cbPayRemittanceReqBo.setOriginalAmt(batchRemitDto.getRemitAmt());
        // 跨境人民币汇款
        cbPayRemittanceReqBo.setOrderType("14");
        // 订单版本(1：v3.4  2：v4.0)
        cbPayRemittanceReqBo.setOrderVersion(NumberConstants.TWO);

        return cbPayRemittanceReqBo;
    }

    /**
     * 创建汇款文件批次参数转换
     *
     * @param batchRemitDto 汇款参数请求
     * @return 创建汇款文件批次参数
     */
    public static BatchRemitBo toBatchRemitBo(BatchRemitDto batchRemitDto) {

        BatchRemitBo batchRemitBo = new BatchRemitBo();
        batchRemitBo.setTargetCcy(batchRemitDto.getTargetCcy());
        batchRemitBo.setRemitAmt(batchRemitDto.getRemitAmt());
        batchRemitBo.setEntityNo(batchRemitDto.getEntityNo());
        batchRemitBo.setMemberId(batchRemitDto.getMemberId());
        batchRemitBo.setCreateBy(batchRemitDto.getCreateBy());
        batchRemitBo.setFileName(batchRemitDto.getFileName());
        return batchRemitBo;
    }

    /**
     * 试算参数转换
     *
     * @param batchRemitDtoList 汇款参数请求
     * @return 试算参数集合
     */
    public static List<BatchRemitBo> toBatchRemitBoList(List<BatchRemitTrialDto> batchRemitDtoList) {

        List<BatchRemitBo> batchRemitBoList = Lists.newArrayList();
        for (BatchRemitTrialDto remitDto : batchRemitDtoList) {
            BatchRemitBo batchRemitBo = new BatchRemitBo();
            batchRemitBo.setTargetCcy(remitDto.getTargetCcy());
            batchRemitBo.setRemitAmt(remitDto.getRemitAmt());
            batchRemitBo.setEntityNo(remitDto.getEntityNo());
            batchRemitBo.setMemberId(remitDto.getMemberId());
            batchRemitBo.setAccountInfo(remitDto.getAccountInfo());
            batchRemitBoList.add(batchRemitBo);
        }
        return batchRemitBoList;
    }

    /**
     * 批量汇款试算参数转换
     *
     * @param trialBoList 查询参数
     * @return 返回参数
     */
    public static List<BatchRemitTrialRespDto> toTrialRespDto(List<BatchRemitTrialBo> trialBoList) {

        List<BatchRemitTrialRespDto> respDtoList = Lists.newArrayList();
        for (BatchRemitTrialBo trialBo : trialBoList) {
            BatchRemitTrialRespDto dto = new BatchRemitTrialRespDto();
            dto.setRemitAmt(trialBo.getRemitAmt());
            dto.setTargetCcy(trialBo.getTargetCcy());
            dto.setTargetAmt(trialBo.getTargetAmt());
            if (CcyEnum.JPY.getKey().equals(dto.getTargetCcy()) && trialBo.getTargetAmt() != null) {
                dto.setTargetAmt(trialBo.getTargetAmt().setScale(NumberConstants.ZERO, BigDecimal.ROUND_DOWN));
            }
            dto.setAccountInfo(trialBo.getAccountInfo());
            dto.setTransferFee(trialBo.getTransferFee());
            dto.setSellRateOfCcy(trialBo.getSellRateOfCcy());
            respDtoList.add(dto);
        }
        return respDtoList;
    }
}