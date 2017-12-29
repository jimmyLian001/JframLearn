package com.baofu.cbpayservice.manager.impl;

import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.dal.mapper.CreateRemitMapper;
import com.baofu.cbpayservice.dal.models.*;
import com.baofu.cbpayservice.manager.CreateRemitFileManager;
import com.google.common.collect.Lists;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 创建汇款文件manager
 * <p>
 * </p>
 * User: 不良人 Date:2017/10/20 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class CreateRemitFileManagerImpl implements CreateRemitFileManager {

    /**
     * 创建汇款文件
     */
    @Autowired
    private CreateRemitMapper createRemitMapper;

    /**
     * 批量汇款查询电商订单
     *
     * @param fileBatchNo 文件批次
     * @return 订单信息
     */
    @Override
    public List<ElectronicCommerceDo> queryElectronicCommerce(Long fileBatchNo) {
        List<ElectronicCommerceDo> ecList = createRemitMapper.queryElectronicCommerce(fileBatchNo);
        if(CollectionUtils.isEmpty(ecList)){
            log.info("call 查询电商批量汇款订单为空");
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0055);
        }
        return ecList;
    }

    /**
     * 批量汇款查询机票订单
     *
     * @param fileBatchNo 文件批次
     * @return 订单信息
     */
    @Override
    public List<AirTicketsDo> queryAirTickets(Long fileBatchNo) {

        List<AirTicketsDo> atList = createRemitMapper.queryAirTickets(fileBatchNo);
        if(CollectionUtils.isEmpty(atList)){
            log.info("call 查询机票批量汇款订单为空");
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0055);
        }
        return atList;
    }

    /**
     * 批量汇款查询留学订单
     *
     * @param fileBatchNo 文件批次
     * @return 订单信息
     */
    @Override
    public List<StudyAbroadDo> queryStudyAbroad(Long fileBatchNo) {

        List<StudyAbroadDo> saList = createRemitMapper.queryStudyAbroad(fileBatchNo);
        if(CollectionUtils.isEmpty(saList)){
            log.info("call 查询留学批量汇款订单为空");
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0055);
        }
        return saList;
    }

    /**
     * 批量汇款查询酒店订单
     *
     * @param fileBatchNo 文件批次
     * @return 订单信息
     */
    @Override
    public List<HotelDo> queryHotel(Long fileBatchNo) {

        List<HotelDo> hotelList = createRemitMapper.queryHotel(fileBatchNo);
        if(CollectionUtils.isEmpty(hotelList)){
            log.info("call 查询酒店批量汇款订单为空");
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0055);
        }
        return hotelList;
    }


    /**
     * 批量汇款查询旅游订单
     *
     * @param fileBatchNo 文件批次
     * @return 订单信息
     */
    @Override
    public List<TourismDo> queryTourism(Long fileBatchNo) {

        List<TourismDo> tourismList = createRemitMapper.queryTourism(fileBatchNo);
        if(CollectionUtils.isEmpty(tourismList)){
            log.info("call 查询旅游批量汇款订单为空");
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0055);
        }
        return tourismList;
    }
}
