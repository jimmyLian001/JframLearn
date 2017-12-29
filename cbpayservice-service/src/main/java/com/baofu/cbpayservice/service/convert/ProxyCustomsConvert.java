package com.baofu.cbpayservice.service.convert;

import com.baofu.cbpayservice.biz.models.*;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.UploadFileAuditStatus;
import com.baofu.cbpayservice.common.enums.UploadFileOrderType;
import com.baofu.cbpayservice.common.enums.UploadFileStatus;
import com.baofu.cbpayservice.dal.models.FiCbPayFileUploadDo;
import com.baofu.cbpayservice.facade.models.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 代理报关参数转换
 * <p/>
 * User: 不良人 Date:2017/1/5 ProjectName: cbpayservice Version: 1.0
 */
public class ProxyCustomsConvert {

    /**
     * api 数据转换
     *
     * @param apiProxyCustomsDto
     * @return
     */
    public static ProxyCustomsBo toProxyCustomsBo(ApiProxyCustomsDto apiProxyCustomsDto) {

        ProxyCustomsBo proxyCustomsBo = new ProxyCustomsBo();
        proxyCustomsBo.setMemberTransId(apiProxyCustomsDto.getMemberTransId());
        proxyCustomsBo.setOrderCcy(apiProxyCustomsDto.getOrderCcy());
        proxyCustomsBo.setOrderMoney(apiProxyCustomsDto.getOrderAmt());
        proxyCustomsBo.setPaymentCcy(apiProxyCustomsDto.getTransCcy());
        proxyCustomsBo.setPaymentMoney(apiProxyCustomsDto.getTransAmt());
        proxyCustomsBo.setIdHolder(apiProxyCustomsDto.getIdName());
        proxyCustomsBo.setIdCardNo(apiProxyCustomsDto.getIdCardNo());
        proxyCustomsBo.setBankCardNo(apiProxyCustomsDto.getBankCardNo());
        proxyCustomsBo.setMobile(apiProxyCustomsDto.getMobile());
        proxyCustomsBo.setMemberId(apiProxyCustomsDto.getMemberId());
        proxyCustomsBo.setTerminalId(apiProxyCustomsDto.getTerminalId());
        proxyCustomsBo.setProductId(apiProxyCustomsDto.getProductId());
        proxyCustomsBo.setFunctionId(apiProxyCustomsDto.getFunctionId());
        proxyCustomsBo.setClientIp(apiProxyCustomsDto.getClientIp());
        proxyCustomsBo.setCbpayGoodsItemBos(convertGoods(apiProxyCustomsDto.getCbpayGoodsItemDtos()));
        return proxyCustomsBo;
    }

    /**
     * 商品信息转换
     *
     * @param cbpayGoodsItemDtos 商品信息集合
     * @return 转换结果
     */
    public static List<CbpayGoodsItemBo> convertGoods(List<CbpayGoodsItemDto> cbpayGoodsItemDtos) {
        List<CbpayGoodsItemBo> cbpayGoodsItemBoList = new ArrayList<>();
        for (CbpayGoodsItemDto cbpayGoodsItemDto : cbpayGoodsItemDtos) {
            CbpayGoodsItemBo goodsItemBo = new CbpayGoodsItemBo();
            goodsItemBo.setGoodsPrice(cbpayGoodsItemDto.getGoodsPrice());
            goodsItemBo.setGoodsName(cbpayGoodsItemDto.getGoodsName());
            goodsItemBo.setGoodsNum(cbpayGoodsItemDto.getGoodsNum());
            cbpayGoodsItemBoList.add(goodsItemBo);
        }
        return cbpayGoodsItemBoList;
    }

    /**
     * 上传文件信息转换
     *
     * @param proxyCustomsDto 代理报关参数对象
     * @param fileBatchNo     文件批次号
     * @return 文件批次信息
     */
    public static FiCbpayFileUploadBo toFiCbPayFileUploadBo(ProxyCustomsDto proxyCustomsDto, Long fileBatchNo) {

        FiCbpayFileUploadBo fiCbpayFileUploadBo = new FiCbpayFileUploadBo();
        fiCbpayFileUploadBo.setFileName(proxyCustomsDto.getFileName());
        fiCbpayFileUploadBo.setRecordCount(Integer.parseInt(Constants.ZERO));
        fiCbpayFileUploadBo.setSuccessCount(Integer.parseInt(Constants.ZERO));
        fiCbpayFileUploadBo.setFailCount(Integer.parseInt(Constants.ZERO));
        fiCbpayFileUploadBo.setStatus(UploadFileStatus.PROCESSING.getCode());
        fiCbpayFileUploadBo.setDfsFileId(proxyCustomsDto.getDfsFileId());
        fiCbpayFileUploadBo.setFileType(proxyCustomsDto.getFileType());
        fiCbpayFileUploadBo.setMemberId(proxyCustomsDto.getMemberId());
        fiCbpayFileUploadBo.setCreateBy(proxyCustomsDto.getCreateBy());
        fiCbpayFileUploadBo.setAuditStatus(UploadFileAuditStatus.INIT.getCode());
        fiCbpayFileUploadBo.setFileBatchNo(fileBatchNo);
        fiCbpayFileUploadBo.setOrderType(UploadFileOrderType.SERVICE_TRADE.getCode());
        return fiCbpayFileUploadBo;
    }

    /**
     * facade层参数转换成biz请求参数
     *
     * @param cbPayRemittanceOrderReqV2Dto 请求参数
     * @param status                       文件状态
     * @return 转换结果
     */
    public static CbPayBatchFileUpLoadBo batchUpdateStatusConvert(CbPayRemittanceOrderReqV2Dto cbPayRemittanceOrderReqV2Dto, String status) {
        CbPayBatchFileUpLoadBo cbPayFileUpLoadBo = new CbPayBatchFileUpLoadBo();
        cbPayFileUpLoadBo.setMemberId(cbPayRemittanceOrderReqV2Dto.getMemberId());
        cbPayFileUpLoadBo.setBatchFileIdList(cbPayRemittanceOrderReqV2Dto.getBatchFileIdList());
        cbPayFileUpLoadBo.setStatus(status);
        cbPayFileUpLoadBo.setUpdateBy(cbPayRemittanceOrderReqV2Dto.getCreateBy());
        return cbPayFileUpLoadBo;
    }


    /**
     * facade层参数转换成biz请求参数
     *
     * @param apiCbPayRemittanceOrderReqDto 请求参数
     * @param status                        文件状态
     * @param fileBatchNos                  文件批次号
     * @return 转换结果
     */
    public static CbPayBatchFileUpLoadBo batchUpdateStatusConvert(ApiCbPayRemittanceOrderReqDto apiCbPayRemittanceOrderReqDto,
                                                                  List<Long> fileBatchNos, String status) {
        CbPayBatchFileUpLoadBo cbPayFileUpLoadBo = new CbPayBatchFileUpLoadBo();
        cbPayFileUpLoadBo.setMemberId(apiCbPayRemittanceOrderReqDto.getMemberId());
        cbPayFileUpLoadBo.setBatchFileIdList(fileBatchNos);
        cbPayFileUpLoadBo.setStatus(status);
        cbPayFileUpLoadBo.setUpdateBy("SYSTEM");
        return cbPayFileUpLoadBo;
    }


    /**
     * 代理跨境结算mq消息内容对象
     *
     * @param proxyCustomsDto 代理跨境结算请求内容
     * @param fileBatchNo     文件批次号
     * @return 代理跨境结算对象
     */
    public static ProxyCustomsMqBo toProxyCustomsMqBo(ProxyCustomsDto proxyCustomsDto, Long fileBatchNo) {

        ProxyCustomsMqBo proxyCustomsMqBo = new ProxyCustomsMqBo();
        proxyCustomsMqBo.setFileBatchNo(fileBatchNo);
        proxyCustomsMqBo.setMemberId(proxyCustomsDto.getMemberId());
        proxyCustomsMqBo.setDfsFileId(proxyCustomsDto.getDfsFileId());
        proxyCustomsMqBo.setFileType(proxyCustomsDto.getFileType());
        proxyCustomsMqBo.setCreateBy(proxyCustomsDto.getCreateBy());
        return proxyCustomsMqBo;
    }

    /**
     * 跨境支付订单数据转换
     *
     * @param apiProxyCustomsV2Dto 跨境支付订单上报参数
     * @return 跨境支付订单参数
     */
    public static ProxyCustomsV2Bo toProxyCustomsV2Bo(ApiProxyCustomsV2Dto apiProxyCustomsV2Dto) {

        ProxyCustomsV2Bo proxyCustomsV2Bo = new ProxyCustomsV2Bo();
        proxyCustomsV2Bo.setMemberTransId(apiProxyCustomsV2Dto.getMemberTransId());
        proxyCustomsV2Bo.setOrderCcy(apiProxyCustomsV2Dto.getOrderCcy());
        proxyCustomsV2Bo.setOrderMoney(apiProxyCustomsV2Dto.getOrderAmt());
        proxyCustomsV2Bo.setIdHolder(apiProxyCustomsV2Dto.getIdName());
        proxyCustomsV2Bo.setIdCardNo(apiProxyCustomsV2Dto.getIdCardNo());
        proxyCustomsV2Bo.setBankCardNo(apiProxyCustomsV2Dto.getBankCardNo());
        proxyCustomsV2Bo.setMobile(apiProxyCustomsV2Dto.getMobile());
        proxyCustomsV2Bo.setMemberId(apiProxyCustomsV2Dto.getMemberId());
        proxyCustomsV2Bo.setTerminalId(apiProxyCustomsV2Dto.getTerminalId());
        proxyCustomsV2Bo.setClientIp(apiProxyCustomsV2Dto.getClientIp());
        proxyCustomsV2Bo.setGoodsInfo(apiProxyCustomsV2Dto.getGoodsInfo().replace("\n", "").replace("\r\n", ""));
        proxyCustomsV2Bo.setIdType(apiProxyCustomsV2Dto.getIdType());
        proxyCustomsV2Bo.setIndustryType(apiProxyCustomsV2Dto.getIndustryType());
        proxyCustomsV2Bo.setTradeTime(apiProxyCustomsV2Dto.getTransTime());

        return proxyCustomsV2Bo;
    }

    /**
     * 跨境汇款文件处理进度对象参数转换
     *
     * @param fileUploadDo       文件处理结果对象
     * @param processingSchedule 文件处理进度
     * @return 文件处理进度结果对象
     */
    public static FiCbPayFileUploadRespDto toFiCbPayFileUploadRespDto(FiCbPayFileUploadDo fileUploadDo, String processingSchedule) {
        FiCbPayFileUploadRespDto fileUploadRespDto = new FiCbPayFileUploadRespDto();
        fileUploadRespDto.setProcessingSchedule(processingSchedule);
        fileUploadRespDto.setFileBatchNo(fileUploadDo.getFileBatchNo());
        fileUploadRespDto.setDfsFileId(fileUploadDo.getDfsFileId());
        fileUploadRespDto.setTotalAmount(fileUploadDo.getTotalAmount());
        fileUploadRespDto.setCareerType(fileUploadDo.getCareerType());
        fileUploadRespDto.setCreateBy(fileUploadDo.getCreateBy());
        fileUploadRespDto.setFileName(fileUploadDo.getFileName());
        fileUploadRespDto.setErrorFileId(fileUploadDo.getErrorFileId());
        fileUploadRespDto.setMemberId(fileUploadDo.getMemberId());
        fileUploadRespDto.setOrderType(fileUploadDo.getOrderType());
        fileUploadRespDto.setCreateBy(fileUploadDo.getCreateBy());
        fileUploadRespDto.setCreateAt(fileUploadDo.getCreateAt());
        fileUploadRespDto.setUpdateAt(fileUploadDo.getUpdateAt());
        fileUploadRespDto.setUpdateBy(fileUploadDo.getUpdateBy());
        fileUploadRespDto.setStatus(fileUploadDo.getStatus());
        return fileUploadRespDto;
    }
}
