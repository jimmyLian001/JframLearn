package com.baofu.cbpayservice.service.convert;

import com.baofu.cbpayservice.biz.models.CbPayCancelOrderBo;
import com.baofu.cbpayservice.biz.models.CbPayOrderItemBo;
import com.baofu.cbpayservice.biz.models.CbPayOrderReqBo;
import com.baofu.cbpayservice.common.util.StringUtils;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderAdditionDo;
import com.baofu.cbpayservice.dal.models.FiCbPayOrderDo;
import com.baofu.cbpayservice.facade.models.CbPayCancelOrderDto;
import com.baofu.cbpayservice.facade.models.CbPayOrderItemReqDto;
import com.baofu.cbpayservice.facade.models.CbPayOrderReqDto;
import com.baofu.cbpayservice.facade.models.res.CbPayOrderRespDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Service层参数转换
 * <p>
 * 1、跨境订单接口请求参数转换成Biz层请求参数信息
 * </p>
 * User: 香克斯 Date:2016/10/25 ProjectName: cbpayservice Version: 1.0
 */
public final class CbPayOrderConvert {

    private CbPayOrderConvert() {

    }

    /**
     * 跨境订单接口请求参数转换成Biz层请求参数信息
     *
     * @param cbPayOrderReqDto 跨境订单接口请求参数
     * @return Biz层请求参数信息
     */
    public static CbPayOrderReqBo paramConvert(CbPayOrderReqDto cbPayOrderReqDto) {

        CbPayOrderReqBo cbPayOrderReqBo = new CbPayOrderReqBo();
        cbPayOrderReqBo.setOrderCcy(cbPayOrderReqDto.getOrderCcy());
        cbPayOrderReqBo.setPayId(cbPayOrderReqDto.getPayId());
        cbPayOrderReqBo.setChannelId(cbPayOrderReqDto.getChannelId());
        cbPayOrderReqBo.setMemberId(cbPayOrderReqDto.getMemberId());
        cbPayOrderReqBo.setMemberTransId(cbPayOrderReqDto.getMemberTransId());
        cbPayOrderReqBo.setTerminalId(cbPayOrderReqDto.getTerminalId());
        cbPayOrderReqBo.setFunctionId(cbPayOrderReqDto.getFunctionId());
        cbPayOrderReqBo.setProductId(cbPayOrderReqDto.getProductId());
        cbPayOrderReqBo.setOrderMoney(cbPayOrderReqDto.getOrderMoney());
        cbPayOrderReqBo.setTransTime(cbPayOrderReqDto.getTransTime());
        cbPayOrderReqBo.setNotifyType(cbPayOrderReqDto.getNotifyType());
        cbPayOrderReqBo.setReturnUrl(cbPayOrderReqDto.getReturnUrl());
        cbPayOrderReqBo.setCommodityName(StringUtils.stringFilter(cbPayOrderReqDto.getCommodityName()));
        cbPayOrderReqBo.setCommodityAmount(cbPayOrderReqDto.getCommodityAmount());
        cbPayOrderReqBo.setPageReturnUrl(cbPayOrderReqDto.getPageReturnUrl());
        cbPayOrderReqBo.setTransMoney(cbPayOrderReqDto.getTransMoney());
        cbPayOrderReqBo.setOrderType(cbPayOrderReqDto.getOrderType());
        cbPayOrderReqBo.setTransCcy(cbPayOrderReqDto.getTransCcy());
        cbPayOrderReqBo.setTransRate(cbPayOrderReqDto.getTransRate());
        cbPayOrderReqBo.setAdditionalInfo(cbPayOrderReqDto.getAdditionalInfo());
        cbPayOrderReqBo.setCbPayOrderItemBos(paramConvert(cbPayOrderReqDto.getCbPayOrderItemReqDtoList()));
        cbPayOrderReqBo.setMobile(cbPayOrderReqDto.getMobile());
        cbPayOrderReqBo.setIdHolder(cbPayOrderReqDto.getIdHolder());
        cbPayOrderReqBo.setBankCardNo(cbPayOrderReqDto.getBankCardNo());
        cbPayOrderReqBo.setIdCardNo(cbPayOrderReqDto.getIdCardNo());
        cbPayOrderReqBo.setClientIp(cbPayOrderReqDto.getClientIp());
        cbPayOrderReqBo.setClient(cbPayOrderReqDto.getClient());

        return cbPayOrderReqBo;
    }

    /**
     * 商品信息转换
     *
     * @param cbPayOrderItemReqDtoList 需要转换的对象
     * @return 返回结果
     */
    public static List<CbPayOrderItemBo> paramConvert(List<CbPayOrderItemReqDto> cbPayOrderItemReqDtoList) {

        List<CbPayOrderItemBo> cbPayOrderItemBoList = new ArrayList<>();
        for (CbPayOrderItemReqDto cbPayOrderItemReqDto : cbPayOrderItemReqDtoList) {
            CbPayOrderItemBo cbPayOrderItemBo = new CbPayOrderItemBo();
            cbPayOrderItemBo.setCommodityName(StringUtils.stringFilter(cbPayOrderItemReqDto.getCommodityName()));
            cbPayOrderItemBo.setCommodityPrice(cbPayOrderItemReqDto.getCommodityPrice());
            cbPayOrderItemBo.setCommodityAmount(cbPayOrderItemReqDto.getCommodityAmount());
            cbPayOrderItemBoList.add(cbPayOrderItemBo);
        }

        return cbPayOrderItemBoList;
    }

    /**
     * 订单查询返回参数信息转换
     *
     * @param fiCbPayOrderDo 数据库查询参数信息
     * @param orderAddition  订单附加信息
     * @return 接口返回信息
     */
    public static CbPayOrderRespDto paramConvert(FiCbPayOrderDo fiCbPayOrderDo, FiCbPayOrderAdditionDo orderAddition) {
        CbPayOrderRespDto cbPayOrderRespDto = new CbPayOrderRespDto();
        cbPayOrderRespDto.setOrderId(fiCbPayOrderDo.getOrderId());
        cbPayOrderRespDto.setMemberId(fiCbPayOrderDo.getMemberId());
        cbPayOrderRespDto.setMemberTransId(fiCbPayOrderDo.getMemberTransId());
        cbPayOrderRespDto.setOrderMoney(fiCbPayOrderDo.getOrderMoney());
        cbPayOrderRespDto.setOrderCcy(fiCbPayOrderDo.getOrderCcy());
        cbPayOrderRespDto.setTransMoney(fiCbPayOrderDo.getTransMoney());
        cbPayOrderRespDto.setTransCcy(fiCbPayOrderDo.getTransCcy());
        cbPayOrderRespDto.setProductId(fiCbPayOrderDo.getProductId());
        cbPayOrderRespDto.setFunctionId(fiCbPayOrderDo.getFunctionId());
        cbPayOrderRespDto.setPayId(fiCbPayOrderDo.getPayId());
        cbPayOrderRespDto.setChannelId(fiCbPayOrderDo.getChannelId());
        cbPayOrderRespDto.setPayStatus(fiCbPayOrderDo.getPayStatus());
        cbPayOrderRespDto.setOrderCompleteTime(fiCbPayOrderDo.getOrderCompleteTime());
        cbPayOrderRespDto.setTransRate(fiCbPayOrderDo.getTransRate());
        cbPayOrderRespDto.setTerminalId(fiCbPayOrderDo.getTerminalId());
        cbPayOrderRespDto.setTransTime(fiCbPayOrderDo.getTransTime());
        cbPayOrderRespDto.setTransFee(fiCbPayOrderDo.getTransFee());
        cbPayOrderRespDto.setOrderType(fiCbPayOrderDo.getOrderType());
        cbPayOrderRespDto.setNotifyStatus(fiCbPayOrderDo.getNotifyStatus());
        cbPayOrderRespDto.setOrderId(orderAddition.getOrderId());
        cbPayOrderRespDto.setClientIp(orderAddition.getClientIp());
        cbPayOrderRespDto.setNotifyType(orderAddition.getNotifyType());
        cbPayOrderRespDto.setServerNotifyUrl(orderAddition.getServerNotifyUrl());
        cbPayOrderRespDto.setPageNotifyUrl(orderAddition.getPageNotifyUrl());
        cbPayOrderRespDto.setAdditionInfo(orderAddition.getAdditionInfo());
        cbPayOrderRespDto.setIdCardNo(orderAddition.getIdCardNo());
        cbPayOrderRespDto.setIdHolder(orderAddition.getIdHolder());
        cbPayOrderRespDto.setBankCardNo(orderAddition.getBankCardNo());
        cbPayOrderRespDto.setMobile(orderAddition.getMobile());

        return cbPayOrderRespDto;
    }

    /**
     * 转换DTO参数为BO
     *
     * @param cbPayCancelOrderDto 取消订单参数
     * @return 返回转换结果
     */
    public static CbPayCancelOrderBo paramConvert(CbPayCancelOrderDto cbPayCancelOrderDto) {

        CbPayCancelOrderBo cbPayCancelOrderBo = new CbPayCancelOrderBo();
        cbPayCancelOrderBo.setFileBatchNo(cbPayCancelOrderDto.getFileBatchNo());
        cbPayCancelOrderBo.setUpdateBy(cbPayCancelOrderDto.getUpdateBy());

        return cbPayCancelOrderBo;
    }
}
