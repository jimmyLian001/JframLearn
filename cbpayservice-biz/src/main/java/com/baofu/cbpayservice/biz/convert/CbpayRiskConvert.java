package com.baofu.cbpayservice.biz.convert;

import com.baofoo.cache.service.facade.model.CacheMemberDto;
import com.baofoo.rm.request.CBOrderRequest;
import com.baofoo.rm.response.CBOrderAuditResponse;
import com.baofu.cbpayservice.common.enums.ProductAndFunctionEnum;
import com.baofu.cbpayservice.dal.models.*;

/**
 * 参数转换
 * User: wdj Date:2017/04/27 ProjectName: cbpayservice Version: 1.0
 */
public final class CbpayRiskConvert {

    private CbpayRiskConvert() {

    }

    /**
     * 参数转换
     *
     * @param fiCbPayOrderDo          订单信息
     * @param fiCbPayOrderAdditionDo  订单明细信息
     * @param fiCbPayOrderItemsInfoDo 订单项信息
     * @param fiCbpayOrderLogisticsDo 收货人信息
     * @param cacheMemberDto          商户缓存信息
     * @return 风控请求对象
     */
    public static CBOrderRequest toCBOrderConvert(FiCbPayOrderDo fiCbPayOrderDo, FiCbPayOrderAdditionDo fiCbPayOrderAdditionDo,
                                                  FiCbPayOrderItemsInfoDo fiCbPayOrderItemsInfoDo,
                                                  FiCbpayOrderLogisticsDo fiCbpayOrderLogisticsDo, CacheMemberDto cacheMemberDto) {


        CBOrderRequest cbOrderRequest = new CBOrderRequest();
        cbOrderRequest.setOrderId(fiCbPayOrderDo.getOrderId());
        cbOrderRequest.setMemberId(fiCbPayOrderDo.getMemberId());
        //产品ID
        cbOrderRequest.setProductId(fiCbPayOrderDo.getProductId());
        //功能ID
        cbOrderRequest.setFunctionId(fiCbPayOrderDo.getFunctionId());
        //商户名称
        cbOrderRequest.setMemberName(cacheMemberDto != null ? cacheMemberDto.getName() : "");
        cbOrderRequest.setExtTraceNo(fiCbPayOrderDo.getMemberTransId());
        //商品名称
        cbOrderRequest.setProdName(fiCbPayOrderItemsInfoDo != null ? fiCbPayOrderItemsInfoDo.getCommodityName() : "");
        //产品数量
        cbOrderRequest.setProdQuantity(fiCbPayOrderItemsInfoDo != null ? fiCbPayOrderItemsInfoDo.getCommodityAmount() : "");
        //产品价格
        cbOrderRequest.setProdPrice(fiCbPayOrderItemsInfoDo != null ? fiCbPayOrderItemsInfoDo.getCommodityPrice() : "");
        cbOrderRequest.setOrderType(fiCbPayOrderDo.getOrderType());
        cbOrderRequest.setTxnAmt(fiCbPayOrderDo.getTransMoney().doubleValue());
        cbOrderRequest.setTxnCurrency(fiCbPayOrderDo.getTransCcy());
        cbOrderRequest.setTxnTime(fiCbPayOrderDo.getTransTime());
        //支付IP
        cbOrderRequest.setTxnIp(fiCbPayOrderAdditionDo != null ? fiCbPayOrderAdditionDo.getClientIp() : "");
        //注册会员帐号
        cbOrderRequest.setUserAcct(cacheMemberDto != null ? cacheMemberDto.getAccountName() : "");
        // 支付帐号(银行卡)
        cbOrderRequest.setPan(fiCbPayOrderAdditionDo != null ? fiCbPayOrderAdditionDo.getBankCardNo() : "");
        // 持卡人身份证号
        cbOrderRequest.setChId(fiCbPayOrderAdditionDo != null ? fiCbPayOrderAdditionDo.getIdCardNo() : "");
        // 持卡人姓名
        cbOrderRequest.setChName(fiCbPayOrderAdditionDo != null ? fiCbPayOrderAdditionDo.getIdHolder() : "");
        //  持卡人手机
        cbOrderRequest.setChMobile(fiCbPayOrderAdditionDo != null ? (fiCbPayOrderAdditionDo.getMobile() != null ?
                fiCbPayOrderAdditionDo.getMobile() : "") : "");

        // 收货人姓名
        cbOrderRequest.setConsignee(fiCbpayOrderLogisticsDo != null ? fiCbpayOrderLogisticsDo.getConsigneeName() : "");
        //收货人联系方式
        cbOrderRequest.setConsigneePhone(fiCbpayOrderLogisticsDo != null ? fiCbpayOrderLogisticsDo.getConsigneeContact() : "");
        //收货地址
        cbOrderRequest.setShippingAddress(fiCbpayOrderLogisticsDo != null ? fiCbpayOrderLogisticsDo.getConsigneeAddress() : "");
        //物流公司编号
        cbOrderRequest.setExpressCorpCode(fiCbpayOrderLogisticsDo != null ? fiCbpayOrderLogisticsDo.getLogisticsCompanyNumber() : "");
        // 物流单号
        cbOrderRequest.setTrackingNo(fiCbpayOrderLogisticsDo != null ? fiCbpayOrderLogisticsDo.getLogisticsNumber() : "");
        return cbOrderRequest;
    }

    /**
     * 订单信息组装
     *
     * @param fiCbPayOrderDo       订单信息对象
     * @param cbOrderAuditResponse 风控返回对象
     * @param orderType            订单类型
     * @return 返回风险订单
     */
    public static CbPayRiskOrderDo toRiskOrder(FiCbPayOrderDo fiCbPayOrderDo, CBOrderAuditResponse cbOrderAuditResponse, int orderType) {

        CbPayRiskOrderDo cbPayRiskOrderDo = new CbPayRiskOrderDo();
        cbPayRiskOrderDo.setOrderId(fiCbPayOrderDo.getOrderId());
        cbPayRiskOrderDo.setMemberId(fiCbPayOrderDo.getMemberId());
        cbPayRiskOrderDo.setMemberTransId(fiCbPayOrderDo.getMemberTransId());
        cbPayRiskOrderDo.setOrderAmt(fiCbPayOrderDo.getTransMoney());
        cbPayRiskOrderDo.setOrderCcy(fiCbPayOrderDo.getTransCcy());
        cbPayRiskOrderDo.setRiskLevel("低风险".equals(cbOrderAuditResponse.getRiskLevel()) ? 1 :
                ("中风险".equals(cbOrderAuditResponse.getRiskLevel()) ? 2 : 3));
        cbPayRiskOrderDo.setFakeFlag(2);
        cbPayRiskOrderDo.setWayBillFlag(3);
        cbPayRiskOrderDo.setRiskRule(cbOrderAuditResponse.getHitRule());
        cbPayRiskOrderDo.setArtifiStatus(0);
        cbPayRiskOrderDo.setMemberTransDate(fiCbPayOrderDo.getTransTime());
        cbPayRiskOrderDo.setBatchNo(fiCbPayOrderDo.getBatchNo());
        cbPayRiskOrderDo.setOrderType(orderType);

        return cbPayRiskOrderDo;
    }

    /**
     * 结汇订单请求风控报文组装
     *
     * @param fiCbPaySettleOrderDo    结汇订单信息
     * @param fiCbPayOrderItemsInfoDo 商品信息
     * @param fiCbpayOrderLogisticsDo 物流信息
     * @param cacheMemberDto          商户缓存信息
     * @return 结果
     */
    public static CBOrderRequest toCBOrderConvertOfSettle(FiCbPaySettleOrderDo fiCbPaySettleOrderDo,
                                                          FiCbPayOrderItemsInfoDo fiCbPayOrderItemsInfoDo,
                                                          FiCbpayOrderLogisticsDo fiCbpayOrderLogisticsDo,
                                                          CacheMemberDto cacheMemberDto) {

        CBOrderRequest cbOrderRequest = new CBOrderRequest();
        cbOrderRequest.setOrderId(fiCbPaySettleOrderDo.getOrderId());
        cbOrderRequest.setMemberId(fiCbPaySettleOrderDo.getMemberId());
        //产品ID
        cbOrderRequest.setProductId(ProductAndFunctionEnum.FUNCTION_SETTLE.getProductId());
        //功能ID
        cbOrderRequest.setFunctionId(ProductAndFunctionEnum.FUNCTION_SETTLE.getFunctionId());
        //商户名称
        cbOrderRequest.setMemberName(cacheMemberDto != null ? cacheMemberDto.getName() : "");
        cbOrderRequest.setExtTraceNo(fiCbPaySettleOrderDo.getMemberTransId());
        //商品名称
        cbOrderRequest.setProdName(fiCbPayOrderItemsInfoDo != null ? fiCbPayOrderItemsInfoDo.getCommodityName() : "");
        //产品数量
        cbOrderRequest.setProdQuantity(fiCbPayOrderItemsInfoDo != null ? fiCbPayOrderItemsInfoDo.getCommodityAmount() : "");
        //产品价格
        cbOrderRequest.setProdPrice(fiCbPayOrderItemsInfoDo != null ? fiCbPayOrderItemsInfoDo.getCommodityPrice() : "");
        cbOrderRequest.setOrderType("");
        cbOrderRequest.setTxnAmt(fiCbPaySettleOrderDo.getOrderAmt().doubleValue());
        cbOrderRequest.setTxnCurrency(fiCbPaySettleOrderDo.getOrderCcy());
        cbOrderRequest.setTxnTime(fiCbPaySettleOrderDo.getMemberTransDate());
        //支付IP
        cbOrderRequest.setTxnIp("");
        //注册会员帐号
        cbOrderRequest.setUserAcct(cacheMemberDto != null ? cacheMemberDto.getAccountName() : "");
        // 支付帐号(银行卡)
        cbOrderRequest.setPan(fiCbPaySettleOrderDo.getPayeeAccNo());
        // 持卡人身份证号
        cbOrderRequest.setChId(fiCbPaySettleOrderDo.getPayeeIdNo());
        // 持卡人姓名
        cbOrderRequest.setChName(fiCbPaySettleOrderDo.getPayeeName());
        //  持卡人手机
        cbOrderRequest.setChMobile("");

        // 收货人姓名
        cbOrderRequest.setConsignee(fiCbpayOrderLogisticsDo != null ? fiCbpayOrderLogisticsDo.getConsigneeName() : "");
        //收货人联系方式
        cbOrderRequest.setConsigneePhone(fiCbpayOrderLogisticsDo != null ? fiCbpayOrderLogisticsDo.getConsigneeContact() : "");
        //收货地址
        cbOrderRequest.setShippingAddress(fiCbpayOrderLogisticsDo != null ? fiCbpayOrderLogisticsDo.getConsigneeAddress() : "");
        //物流公司编号
        cbOrderRequest.setExpressCorpCode(fiCbpayOrderLogisticsDo != null ? fiCbpayOrderLogisticsDo.getLogisticsCompanyNumber() : "");
        // 物流单号
        cbOrderRequest.setTrackingNo(fiCbpayOrderLogisticsDo != null ? fiCbpayOrderLogisticsDo.getLogisticsNumber() : "");

        return cbOrderRequest;

    }

    /**
     * 订单信息组装
     *
     * @param fiCbPaySettleOrderDo 订单信息对象
     * @param cbOrderAuditResponse 风控返回对象
     * @param orderType            订单类型
     * @return 返回风险订单
     */
    public static CbPayRiskOrderDo toRiskOrderSettle(FiCbPaySettleOrderDo fiCbPaySettleOrderDo, CBOrderAuditResponse cbOrderAuditResponse,
                                                     int orderType) {

        CbPayRiskOrderDo cbPayRiskOrderDo = new CbPayRiskOrderDo();
        cbPayRiskOrderDo.setOrderId(fiCbPaySettleOrderDo.getOrderId());
        cbPayRiskOrderDo.setMemberId(fiCbPaySettleOrderDo.getMemberId());
        cbPayRiskOrderDo.setMemberTransId(fiCbPaySettleOrderDo.getMemberTransId());
        cbPayRiskOrderDo.setOrderAmt(fiCbPaySettleOrderDo.getOrderAmt());
        cbPayRiskOrderDo.setOrderCcy(fiCbPaySettleOrderDo.getOrderCcy());
        cbPayRiskOrderDo.setRiskLevel("低风险".equals(cbOrderAuditResponse.getRiskLevel()) ? 1 :
                ("中风险".equals(cbOrderAuditResponse.getRiskLevel()) ? 2 : 3));
        cbPayRiskOrderDo.setFakeFlag(2);
        cbPayRiskOrderDo.setWayBillFlag(3);
        cbPayRiskOrderDo.setRiskRule(cbOrderAuditResponse.getHitRule());
        cbPayRiskOrderDo.setArtifiStatus(0);
        cbPayRiskOrderDo.setMemberTransDate(fiCbPaySettleOrderDo.getMemberTransDate());
        cbPayRiskOrderDo.setBatchNo(fiCbPaySettleOrderDo.getIncomeNo());
        cbPayRiskOrderDo.setOrderType(orderType);

        return cbPayRiskOrderDo;

    }

}
