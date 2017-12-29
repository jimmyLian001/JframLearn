package com.baofu.cbpayservice.biz.convert;

import com.baofoo.cache.service.facade.utils.SecurityUtil;
import com.baofoo.member.manager.common.utils.DateUtils;
import com.baofu.cbpayservice.biz.models.*;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.dal.models.*;

/**
 * 创建文件对象转换
 * <p>
 * </p>
 * User: 不良人 Date:2017/10/20 ProjectName: cbpayservice Version: 1.0
 */
public class CreateRemitFileBizConvert {

    /**
     * 电商参数转换
     *
     * @param ec 查询出结果
     * @return 转换后参数
     */
    public static ElectronicCommerceBo toElectronicCommerceBo(ElectronicCommerceDo ec) {

        ElectronicCommerceBo electronicCommerceBo = new ElectronicCommerceBo();
        electronicCommerceBo.setMemberTransId(ec.getMemberTransId());
        electronicCommerceBo.setOrderCcy(ec.getOrderCcy());
        electronicCommerceBo.setOrderMoney(ec.getOrderMoney());
        electronicCommerceBo.setTransTime(DateUtils.pareDate(ec.getTransTime(), DateUtils.DATE_TIME_FORMAT));
        electronicCommerceBo.setPayeeIdType(ec.getPayeeIdType().toString());
        electronicCommerceBo.setIdHolder(ec.getIdHolder());
        electronicCommerceBo.setIdCardNo(SecurityUtil.desDecrypt(ec.getIdCardNo(), Constants.CARD_DES_PASSWD));
        electronicCommerceBo.setBankCardNo(SecurityUtil.desDecrypt(ec.getBankCardNo(), Constants.CARD_DES_PASSWD));
        electronicCommerceBo.setMobile(ec.getMobile());
        electronicCommerceBo.setProductName(ec.getProductName());
        electronicCommerceBo.setProductNum(ec.getProductNum());
        electronicCommerceBo.setProductPrice(ec.getProductPrice());
        electronicCommerceBo.setLogisticsCompanyNumber(ec.getLogisticsCompanyNumber());
        electronicCommerceBo.setLogisticsNumber(ec.getLogisticsNumber());
        electronicCommerceBo.setConsigneeName(ec.getConsigneeName());
        electronicCommerceBo.setConsigneeContact(ec.getConsigneeContact());
        electronicCommerceBo.setConsigneeAddress(ec.getConsigneeAddress());
        electronicCommerceBo.setDeliveryDate(DateUtils.pareDate(ec.getDeliveryDate(), DateUtils.DATE_SHOW_FORMAT));
        return electronicCommerceBo;
    }

    /**
     * 机票参数转换
     *
     * @param at 查询出结果
     * @return 转换后参数
     */
    public static AirTicketsBo toAirTicketsBo(AirTicketsDo at) {

        AirTicketsBo airTicketsBo = new AirTicketsBo();
        airTicketsBo.setMemberTransId(at.getMemberTransId());
        airTicketsBo.setOrderCcy(at.getOrderCcy());
        airTicketsBo.setOrderMoney(String.valueOf(at.getOrderMoney()));
        airTicketsBo.setTransTime(DateUtils.pareDate(at.getTransTime(), DateUtils.DATE_TIME_FORMAT));
        airTicketsBo.setPayeeIdType(String.valueOf(at.getPayeeIdType()));
        airTicketsBo.setIdHolder(at.getIdHolder());
        airTicketsBo.setIdCardNo(SecurityUtil.desDecrypt(at.getIdCardNo(), Constants.CARD_DES_PASSWD));
        airTicketsBo.setBankCardNo(SecurityUtil.desDecrypt(at.getBankCardNo(), Constants.CARD_DES_PASSWD));
        airTicketsBo.setMobile(at.getMobile());
        airTicketsBo.setFlightNumber(at.getFlightNumber());
        airTicketsBo.setAddress(at.getAddress());
        airTicketsBo.setTakeoffTime(at.getTakeoffTime());
        return airTicketsBo;
    }

    /**
     * 留学参数转换
     *
     * @param studyAbroadDo 查询出结果
     * @return 转换后参数
     */
    public static RemitStudyAbroadBo toRemitStudyAbroadBo(StudyAbroadDo studyAbroadDo) {
        RemitStudyAbroadBo studyAbroadBo = new RemitStudyAbroadBo();
        studyAbroadBo.setMemberTransId(studyAbroadDo.getMemberTransId());
        studyAbroadBo.setOrderCcy(studyAbroadDo.getOrderCcy());
        studyAbroadBo.setOrderMoney(String.valueOf(studyAbroadDo.getOrderMoney()));
        studyAbroadBo.setTransTime(DateUtils.pareDate(studyAbroadDo.getTransTime(), DateUtils.DATE_TIME_FORMAT));
        studyAbroadBo.setPayeeIdType(String.valueOf(studyAbroadDo.getPayeeIdType()));
        studyAbroadBo.setIdHolder(studyAbroadDo.getIdHolder());
        studyAbroadBo.setIdCardNo(SecurityUtil.desDecrypt(studyAbroadDo.getIdCardNo(), Constants.CARD_DES_PASSWD));
        studyAbroadBo.setBankCardNo(SecurityUtil.desDecrypt(studyAbroadDo.getBankCardNo(), Constants.CARD_DES_PASSWD));
        studyAbroadBo.setSchoolCountryCode(studyAbroadDo.getSchoolCountryCode());
        studyAbroadBo.setSchoolName(studyAbroadDo.getSchoolName());
        studyAbroadBo.setStudentName(studyAbroadDo.getStudentName());
        studyAbroadBo.setStudentId(studyAbroadDo.getStudentId());
        studyAbroadBo.setAdmissionNoticeId(studyAbroadDo.getAdmissionNoticeId());
        return studyAbroadBo;
    }

    /**
     * 酒店参数转换
     *
     * @param hotelDo 查询出结果
     * @return 转换后参数
     */
    public static RemitHotelBo toRemitHotelBo(HotelDo hotelDo) {

        RemitHotelBo remitHotelBo = new RemitHotelBo();
        remitHotelBo.setMemberTransId(hotelDo.getMemberTransId());
        remitHotelBo.setOrderCcy(hotelDo.getOrderCcy());
        remitHotelBo.setOrderMoney(String.valueOf(hotelDo.getOrderMoney()));
        remitHotelBo.setTransTime(DateUtils.pareDate(hotelDo.getTransTime(), DateUtils.DATE_TIME_FORMAT));
        remitHotelBo.setPayeeIdType(String.valueOf(hotelDo.getPayeeIdType()));
        remitHotelBo.setIdHolder(hotelDo.getIdHolder());
        remitHotelBo.setIdCardNo(SecurityUtil.desDecrypt(hotelDo.getIdCardNo(), Constants.CARD_DES_PASSWD));
        remitHotelBo.setBankCardNo(SecurityUtil.desDecrypt(hotelDo.getBankCardNo(), Constants.CARD_DES_PASSWD));
        remitHotelBo.setMobile(hotelDo.getMobile());
        remitHotelBo.setHotelCountryCode(hotelDo.getHotelCountryCode());
        remitHotelBo.setHotelName(hotelDo.getHotelName());
        remitHotelBo.setCheckInDate(hotelDo.getCheckInDate());
        remitHotelBo.setNightCount(hotelDo.getNightCount());
        return remitHotelBo;
    }

    /**
     * 旅游参数转换
     *
     * @param tourismDo 查询出结果
     * @return 转换后参数
     */
    public static RemitTourismBo toRemitTourismBo(TourismDo tourismDo) {

        RemitTourismBo remitTourismBo = new RemitTourismBo();
        remitTourismBo.setMemberTransId(tourismDo.getMemberTransId());
        remitTourismBo.setOrderCcy(tourismDo.getOrderCcy());
        remitTourismBo.setOrderMoney(String.valueOf(tourismDo.getOrderMoney()));
        remitTourismBo.setTransTime(DateUtils.pareDate(tourismDo.getTransTime(), DateUtils.DATE_TIME_FORMAT));
        remitTourismBo.setPayeeIdType(String.valueOf(tourismDo.getPayeeIdType()));
        remitTourismBo.setIdHolder(tourismDo.getIdHolder());
        remitTourismBo.setIdCardNo(SecurityUtil.desDecrypt(tourismDo.getIdCardNo(), Constants.CARD_DES_PASSWD));
        remitTourismBo.setBankCardNo(SecurityUtil.desDecrypt(tourismDo.getBankCardNo(), Constants.CARD_DES_PASSWD));
        remitTourismBo.setMobile(tourismDo.getMobile());
        remitTourismBo.setTravelAgencyName(tourismDo.getTravelAgencyName());
        remitTourismBo.setTravelCountryCode(tourismDo.getTravelCountryCode());
        remitTourismBo.setTripDate(tourismDo.getTripDate());
        remitTourismBo.setTourDays(tourismDo.getTourDays());
        return remitTourismBo;
    }

}
