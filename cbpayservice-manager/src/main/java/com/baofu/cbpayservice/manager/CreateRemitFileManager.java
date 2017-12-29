package com.baofu.cbpayservice.manager;

import com.baofu.cbpayservice.dal.models.*;

import java.util.List;

/**
 * 创建汇款文件manager
 * <p>
 * </p>
 * User: 不良人 Date:2017/10/20 ProjectName: cbpayservice Version: 1.0
 */
public interface CreateRemitFileManager {

    /**
     * 批量汇款查询电商订单
     *
     * @param fileBatchNo 文件批次
     * @return 订单信息
     */
    List<ElectronicCommerceDo> queryElectronicCommerce(Long fileBatchNo);

    /**
     * 批量汇款查询机票订单
     *
     * @param fileBatchNo 文件批次
     * @return 订单信息
     */
    List<AirTicketsDo> queryAirTickets(Long fileBatchNo);

    /**
     * 批量汇款查询留学订单
     *
     * @param fileBatchNo 文件批次
     * @return 订单信息
     */
    List<StudyAbroadDo> queryStudyAbroad(Long fileBatchNo);

    /**
     * 批量汇款查询酒店订单
     *
     * @param fileBatchNo 文件批次
     * @return 订单信息
     */
    List<HotelDo> queryHotel(Long fileBatchNo);

    /**
     * 批量汇款查询旅游订单
     *
     * @param fileBatchNo 文件批次
     * @return 订单信息
     */
    List<TourismDo> queryTourism(Long fileBatchNo);
}
