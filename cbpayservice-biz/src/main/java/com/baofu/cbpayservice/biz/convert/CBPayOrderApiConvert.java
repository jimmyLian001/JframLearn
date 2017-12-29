package com.baofu.cbpayservice.biz.convert;

import com.baofoo.cache.service.facade.utils.SecurityUtil;
import com.baofu.cbpayservice.biz.models.*;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.FlagEnum;
import com.baofu.cbpayservice.common.enums.ProductAndFunctionEnum;
import com.baofu.cbpayservice.common.util.StringUtils;
import com.baofu.cbpayservice.dal.models.*;
import com.google.common.collect.Lists;
import com.system.commons.utils.DateUtil;
import com.system.commons.utils.JsonUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 跨境支付订单API上报接口
 * <p>
 * User: 不良人 Date:2017/7/14 ProjectName: cbpayservice Version: 1.0
 */
public final class CBPayOrderApiConvert {

    private CBPayOrderApiConvert() {

    }

    /**
     * 跨境订主单信息转换
     *
     * @param proxyCustomsV2Bo 跨境订单请求参数
     * @param orderId          订单号
     * @return 跨境订主单信息
     */
    public static FiCbPayOrderDo toFiCbPayOrderDo(ProxyCustomsV2Bo proxyCustomsV2Bo, Long orderId) {

        FiCbPayOrderDo fiCbPayOrderDo = new FiCbPayOrderDo();
        fiCbPayOrderDo.setOrderId(orderId);
        fiCbPayOrderDo.setOrderCcy(proxyCustomsV2Bo.getOrderCcy());
        fiCbPayOrderDo.setTransCcy(proxyCustomsV2Bo.getOrderCcy());
        fiCbPayOrderDo.setMemberId(proxyCustomsV2Bo.getMemberId());
        fiCbPayOrderDo.setTransTime(proxyCustomsV2Bo.getTradeTime());
        fiCbPayOrderDo.setOrderMoney(proxyCustomsV2Bo.getOrderMoney());
        fiCbPayOrderDo.setTransMoney(proxyCustomsV2Bo.getOrderMoney());
        fiCbPayOrderDo.setMemberTransId(proxyCustomsV2Bo.getMemberTransId());
        fiCbPayOrderDo.setCareerType(proxyCustomsV2Bo.getIndustryType());
        fiCbPayOrderDo.setTerminalId(proxyCustomsV2Bo.getTerminalId().intValue());

        fiCbPayOrderDo.setTransRate(BigDecimal.ONE);
        fiCbPayOrderDo.setTransFee(BigDecimal.ZERO);
        fiCbPayOrderDo.setOrderCompleteTime(new Date());
        fiCbPayOrderDo.setPayId(BigDecimal.ZERO.intValue());
        fiCbPayOrderDo.setPayStatus(FlagEnum.TRUE.getCode());
        fiCbPayOrderDo.setOrderType(Constants.API_PROXY_CUSTOMS);
        fiCbPayOrderDo.setAckStatus(BigDecimal.ROUND_CEILING);
        fiCbPayOrderDo.setChannelId(BigDecimal.ZERO.intValue());
        fiCbPayOrderDo.setAmlStatus(BigDecimal.ZERO.intValue());
        fiCbPayOrderDo.setProductId(ProductAndFunctionEnum.PRO_1016.getProductId());
        fiCbPayOrderDo.setFunctionId(ProductAndFunctionEnum.PRO_1016.getFunctionId());

        return fiCbPayOrderDo;
    }

    /**
     * 跨境订单附加信息转换
     *
     * @param proxyCustomsV2Bo 跨境订单请求参数
     * @param orderId          订单号
     * @return 跨境订主单信息
     */
    public static FiCbPayOrderAdditionDo toFiCbPayOrderAdditionDo(ProxyCustomsV2Bo proxyCustomsV2Bo, Long orderId) {

        FiCbPayOrderAdditionDo fiCbPayOrderAdditionDo = new FiCbPayOrderAdditionDo();
        //宝付订单号
        fiCbPayOrderAdditionDo.setOrderId(orderId);
        //银行卡号
        fiCbPayOrderAdditionDo.setBankCardNo(SecurityUtil.desEncrypt(proxyCustomsV2Bo.getBankCardNo(), Constants.CARD_DES_PASSWD));
        //身份证号
        fiCbPayOrderAdditionDo.setIdCardNo(SecurityUtil.desEncrypt(proxyCustomsV2Bo.getIdCardNo().toUpperCase(), Constants.CARD_DES_PASSWD));
        //持卡人姓名
        fiCbPayOrderAdditionDo.setIdHolder(proxyCustomsV2Bo.getIdHolder());
        //银行预留手机号
        fiCbPayOrderAdditionDo.setMobile(proxyCustomsV2Bo.getMobile());
        //请求IP地址
        fiCbPayOrderAdditionDo.setClientIp(BigDecimal.ZERO.toString());
        //收款人证件类型：1-身份证
        fiCbPayOrderAdditionDo.setPayeeIdType(Integer.parseInt(proxyCustomsV2Bo.getIdType()));
        return fiCbPayOrderAdditionDo;
    }

    /**
     * 跨境订单上报商品信息转换
     *
     * @param orderItemList 请求参数信息
     * @param orderId       跨境订单号
     * @return 商品信息集合
     */
    public static List<FiCbPayOrderItemDo> toFiCbPayOrderItemDoList(List<OrderItemBo> orderItemList,
                                                                    Long orderId) {

        List<FiCbPayOrderItemDo> fiCbPayOrderItemDoList = Lists.newArrayList();
        for (OrderItemBo orderItemBo : orderItemList) {
            FiCbPayOrderItemDo fiCbPayOrderItemDo = new FiCbPayOrderItemDo();
            fiCbPayOrderItemDo.setOrderId(orderId);
            fiCbPayOrderItemDo.setCommodityAmount(orderItemBo.getGoodsNum());
            fiCbPayOrderItemDo.setCommodityName(StringUtils.stringFilter(orderItemBo.getGoodsName()));
            fiCbPayOrderItemDo.setCommodityPrice(orderItemBo.getGoodsPrice());
            fiCbPayOrderItemDoList.add(fiCbPayOrderItemDo);
        }

        return fiCbPayOrderItemDoList;
    }

    /**
     * 跨境订单上报运单信息转换
     *
     * @param cbpayGoodsInfoItemDto 请求参数信息
     * @param orderId               跨境订单号
     * @return 运单信息集合
     */
    public static FiCbpayOrderLogisticsDo toFiCbpayOrderLogisticsDo(CbpayGoodsInfoItemDto cbpayGoodsInfoItemDto, Long orderId) {

        FiCbpayOrderLogisticsDo orderLogisticsDo = new FiCbpayOrderLogisticsDo();

        orderLogisticsDo.setOrderId(orderId);
        orderLogisticsDo.setConsigneeAddress(cbpayGoodsInfoItemDto.getConsigneeAddress());
        orderLogisticsDo.setConsigneeContact(cbpayGoodsInfoItemDto.getConsigneeContact());
        orderLogisticsDo.setConsigneeName(cbpayGoodsInfoItemDto.getConsigneeName());
        orderLogisticsDo.setDeliveryDate(DateUtil.parse(cbpayGoodsInfoItemDto.getDeliveryDate(), DateUtil.datePattern));
        orderLogisticsDo.setLogisticsNumber(cbpayGoodsInfoItemDto.getLogisticsNumber());
        orderLogisticsDo.setLogisticsCompanyNumber(cbpayGoodsInfoItemDto.getLogisticsCompanyNumber());

        orderLogisticsDo.setCreateAt(new Date());
        orderLogisticsDo.setUpdateAt(new Date());

        return orderLogisticsDo;
    }

    /**
     * 跨境订单上报航空信息转换
     *
     * @param proxyCustomsV2Bo 请求参数信息
     * @param orderId          跨境订单号
     * @return 商品信息集合
     */
    public static List<FiCbPayOrderTicketsDo> toFiCbPayOrderTicketsDo(ProxyCustomsV2Bo proxyCustomsV2Bo, Long orderId) {

        List<FiCbPayOrderTicketsDo> fiCbPayOrderItemDoList = Lists.newArrayList();
        List<PlaneTicketBo> planeTicketBos = JsonUtil.toList(proxyCustomsV2Bo.getGoodsInfo(), PlaneTicketBo.class);
        for (PlaneTicketBo planeTicketBo : planeTicketBos) {
            FiCbPayOrderTicketsDo fiCbPayOrderTicketsDo = new FiCbPayOrderTicketsDo();
            fiCbPayOrderTicketsDo.setOrderId(orderId);
            fiCbPayOrderTicketsDo.setAddress(planeTicketBo.getDepartureLocationDestination());
            fiCbPayOrderTicketsDo.setFlightNumber(planeTicketBo.getFlightNumber());
            fiCbPayOrderTicketsDo.setTakeoffTime(DateUtil.parse(planeTicketBo.getDepartureTime(), DateUtil.ymdhm));
            fiCbPayOrderItemDoList.add(fiCbPayOrderTicketsDo);
        }

        return fiCbPayOrderItemDoList;
    }

    /**
     * 跨境订单上报留学信息转换
     *
     * @param proxyCustomsV2Bo 请求参数信息
     * @param orderId          跨境订单号
     * @return 留学信息集合
     */
    public static List<FiCbPayOrderStudyAbroadDo> toFiCbPayOrderStudyAbroadDo(ProxyCustomsV2Bo proxyCustomsV2Bo, Long orderId) {

        List<FiCbPayOrderStudyAbroadDo> fiCbPayOrderStudyAbroadDos = Lists.newArrayList();
        List<StudyAbroadBo> studyAbroadBos = JsonUtil.toList(proxyCustomsV2Bo.getGoodsInfo(), StudyAbroadBo.class);
        for (StudyAbroadBo studyAbroadBo : studyAbroadBos) {
            FiCbPayOrderStudyAbroadDo fiCbPayOrderStudyAbroadDo = new FiCbPayOrderStudyAbroadDo();
            fiCbPayOrderStudyAbroadDo.setOrderId(orderId);
            fiCbPayOrderStudyAbroadDo.setSchoolName(studyAbroadBo.getSchoolName());
            fiCbPayOrderStudyAbroadDo.setStudentName(studyAbroadBo.getStudentName());
            fiCbPayOrderStudyAbroadDo.setStudentId(studyAbroadBo.getStudentNumber());
            fiCbPayOrderStudyAbroadDo.setSchoolCountryCode(studyAbroadBo.getStudyCounty());
            fiCbPayOrderStudyAbroadDo.setAdmissionNoticeId(studyAbroadBo.getStudentOfferNumber());
            fiCbPayOrderStudyAbroadDos.add(fiCbPayOrderStudyAbroadDo);
        }

        return fiCbPayOrderStudyAbroadDos;
    }

    /**
     * 跨境订单上报酒店信息转换
     *
     * @param proxyCustomsV2Bo 请求参数信息
     * @param orderId          跨境订单号
     * @return 酒店信息集合
     */
    public static List<FiCbPayOrderHotelDo> toFiCbPayOrderHotelDo(ProxyCustomsV2Bo proxyCustomsV2Bo, Long orderId) {

        List<FiCbPayOrderHotelDo> fiCbPayOrderHotelDoList = Lists.newArrayList();
        List<HotelBo> hotelBos = JsonUtil.toList(proxyCustomsV2Bo.getGoodsInfo(), HotelBo.class);
        for (HotelBo hotelBo : hotelBos) {
            FiCbPayOrderHotelDo fiCbPayOrderStudyAbroadDo = new FiCbPayOrderHotelDo();
            fiCbPayOrderStudyAbroadDo.setOrderId(orderId);
            fiCbPayOrderStudyAbroadDo.setHotelCountryCode(hotelBo.getLiveCountry());
            fiCbPayOrderStudyAbroadDo.setHotelName(hotelBo.getHotelName());
            fiCbPayOrderStudyAbroadDo.setCheckInDate(hotelBo.getCheckInDate());
            fiCbPayOrderStudyAbroadDo.setNightCount(hotelBo.getRoomNights());
            fiCbPayOrderHotelDoList.add(fiCbPayOrderStudyAbroadDo);
        }
        return fiCbPayOrderHotelDoList;
    }

    /**
     * 跨境订单上报旅游信息转换
     *
     * @param proxyCustomsV2Bo 请求参数信息
     * @param orderId          跨境订单号
     * @return 旅游信息集合
     */
    public static List<FiCbPayOrderTourismDo> toFiCbPayOrderTourismDo(ProxyCustomsV2Bo proxyCustomsV2Bo, Long orderId) {

        List<FiCbPayOrderTourismDo> fiCbPayOrderTourismDoList = Lists.newArrayList();
        List<TourismBo> tourismBos = JsonUtil.toList(proxyCustomsV2Bo.getGoodsInfo(), TourismBo.class);
        for (TourismBo tourismBo : tourismBos) {
            FiCbPayOrderTourismDo fiCbPayOrderTourismDo = new FiCbPayOrderTourismDo();
            fiCbPayOrderTourismDo.setOrderId(orderId);
            fiCbPayOrderTourismDo.setTravelAgencyName(tourismBo.getTravelAgencyName());
            fiCbPayOrderTourismDo.setTravelCountryCode(tourismBo.getTravelCountry());
            fiCbPayOrderTourismDo.setTripDate(tourismBo.getDepartureDate());
            fiCbPayOrderTourismDo.setTourDays(tourismBo.getDuration());
            fiCbPayOrderTourismDoList.add(fiCbPayOrderTourismDo);
        }

        return fiCbPayOrderTourismDoList;
    }
}
