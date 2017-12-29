package com.baofu.cbpayservice.dal.mapper;

import com.baofu.cbpayservice.dal.models.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 创建汇款文件
 * <p>
 * </p>
 * User: 不良人 Date:2017/10/20 ProjectName: cbpayservice Version: 1.0
 */
public interface CreateRemitMapper {

    /**
     * 批量汇款查询电商订单
     *
     * @param fileBatchNo 文件批次
     * @return 订单信息
     */
    List<ElectronicCommerceDo> queryElectronicCommerce(@Param("fileBatchNo") Long fileBatchNo);

    /**
     * 批量汇款查询机票订单
     *
     * @param fileBatchNo 文件批次
     * @return 订单信息
     */
    List<AirTicketsDo> queryAirTickets(@Param("fileBatchNo") Long fileBatchNo);

    /**
     * 批量汇款查询留学订单
     *
     * @param fileBatchNo 文件批次
     * @return 订单信息
     */
    List<StudyAbroadDo> queryStudyAbroad(@Param("fileBatchNo") Long fileBatchNo);

    /**
     * 批量汇款查询酒店订单
     *
     * @param fileBatchNo 文件批次
     * @return 订单信息
     */
    List<HotelDo> queryHotel(@Param("fileBatchNo") Long fileBatchNo);


    /**
     * 批量汇款查询旅游订单
     *
     * @param fileBatchNo 文件批次
     * @return 订单信息
     */
    List<TourismDo> queryTourism(@Param("fileBatchNo") Long fileBatchNo);
}
