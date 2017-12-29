package com.baofu.cbpayservice.facade;

import com.baofu.cbpayservice.facade.models.FiCbpayMemberFtpDto;
import com.system.commons.result.Result;

/**
 * <p>
 * 1、添加FTP信息
 * 2、根据记录编号查询FTP信息
 * 3、根据记录编号更新表信息
 * </p>
 * User: 康志光 Date: 2017/7/21 ProjectName: cbpay-customs-service Version: 1.0
 */
public interface CbPayMemberFtpFacade {

    /**
     * 添加FTP信息
     *
     * @param fiCbpayMemberFtpDto ftp信息对象
     * @param traceLogId          日志ID
     * @return Result<Boolean>
     */
    Result<Boolean> create(FiCbpayMemberFtpDto fiCbpayMemberFtpDto, String traceLogId);

    /**
     * 根据记录编号查询FTP信息
     *
     * @param recordId   记录编号
     * @param traceLogId 日志ID
     * @return Result<FiCbpayMemberFtpDto>
     */
    Result<FiCbpayMemberFtpDto> searchByRecordId(Long recordId, String traceLogId);

    /**
     * 根据商户号查询FTP信息
     *
     * @param memberId   记录编号
     * @param traceLogId 日志ID
     * @return Result<FiCbpayMemberFtpDto>
     */
    Result<FiCbpayMemberFtpDto> searchByMemberId(Long memberId, String traceLogId);

    /**
     * 根据记录编号更新表信息
     *
     * @param fiCbpayMemberFtpDto ftp信息对象
     * @param traceLogId          日志ID
     * @return Result<Boolean>
     */
    Result<Boolean> modifyByRecordId(FiCbpayMemberFtpDto fiCbpayMemberFtpDto, String traceLogId);

}
