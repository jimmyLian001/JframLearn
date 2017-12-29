package com.baofu.cbpayservice.service.impl;

import com.baofu.cbpayservice.biz.CbPayMemberFtpBiz;
import com.baofu.cbpayservice.biz.models.FiCbpayMemberFtpBo;
import com.baofu.cbpayservice.dal.models.FiCbpayMemberFtpDo;
import com.baofu.cbpayservice.facade.CbPayMemberFtpFacade;
import com.baofu.cbpayservice.facade.models.FiCbpayMemberFtpDto;
import com.baofu.cbpayservice.manager.FiCbpayMemberFtpManager;
import com.baofu.cbpayservice.service.convert.FiCbpayMemberFtpConvert;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 结汇操作接口服务实现
 * <p>
 * User: 不良人 Date:2017/4/13 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class CbPayMemberFtpFacadeImpl implements CbPayMemberFtpFacade {

    /**
     * 商户FTP BIZ服务
     */
    @Autowired
    private CbPayMemberFtpBiz cbPayMemberFtpBiz;

    /**
     * 商户FTP服务
     */
    @Autowired
    private FiCbpayMemberFtpManager fiCbpayMemberFtpManager;


    /**
     * 添加FTP信息
     *
     * @param fiCbpayMemberFtpDto ftp信息对象
     * @param traceLogId          日志ID
     * @return Result<Boolean>
     */
    @Override
    public Result<Boolean> create(FiCbpayMemberFtpDto fiCbpayMemberFtpDto, String traceLogId) {
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 创建FTP信息申请参数:{}", fiCbpayMemberFtpDto);
        Result<Boolean> result;
        try {
            FiCbpayMemberFtpBo fiCbpayMemberFtpBo = FiCbpayMemberFtpConvert.toFiCbpayMemberFtpBo(fiCbpayMemberFtpDto);
            cbPayMemberFtpBiz.create(fiCbpayMemberFtpBo);
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("call  创建FTP信息申请异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 创建FTP信息返回结果:{}", result);
        return result;
    }

    /**
     * 根据记录编号查询FTP信息
     *
     * @param recordId
     * @param traceLogId 日志ID
     * @return Result<FiCbpayMemberFtpDto>
     */
    @Override
    public Result<FiCbpayMemberFtpDto> searchByRecordId(Long recordId, String traceLogId) {
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 查询FTP信息申请参数:{}", recordId);
        Result<FiCbpayMemberFtpDto> result;
        try {
            FiCbpayMemberFtpDo fiCbpayMemberFtpDo = fiCbpayMemberFtpManager.searchByRecordId(recordId);
            result = new Result<>(FiCbpayMemberFtpConvert.toFiCbpayMemberFtpDto(fiCbpayMemberFtpDo));
        } catch (Exception e) {
            log.error("call  查询FTP信息申请异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 查询FTP信息返回结果:{}", result);
        return result;
    }

    /**
     * 根据商户号查询FTP信息
     *
     * @param memberId
     * @param traceLogId 日志ID
     * @return Result<FiCbpayMemberFtpDto>
     */
    @Override
    public Result<FiCbpayMemberFtpDto> searchByMemberId(Long memberId, String traceLogId) {
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 查询FTP信息申请参数:{}", memberId);
        Result<FiCbpayMemberFtpDto> result;
        try {
            FiCbpayMemberFtpDo fiCbpayMemberFtpDo = fiCbpayMemberFtpManager.searchByMemberId(memberId);
            result = new Result<>(FiCbpayMemberFtpConvert.toFiCbpayMemberFtpDto(fiCbpayMemberFtpDo));
        } catch (Exception e) {
            log.error("call  查询FTP信息申请异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 查询FTP信息返回结果:{}", result);
        return result;
    }

    /**
     * 根据记录编号更新表信息
     *
     * @param fiCbpayMemberFtpDto ftp信息对象
     * @param traceLogId          日志ID
     * @return Result<Boolean>
     */
    @Override
    public Result<Boolean> modifyByRecordId(FiCbpayMemberFtpDto fiCbpayMemberFtpDto, String traceLogId) {
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        log.info("call 修改FTP信息申请参数:{}", fiCbpayMemberFtpDto);
        Result<Boolean> result;
        try {
            FiCbpayMemberFtpDo fiCbpayMemberFtpDo = FiCbpayMemberFtpConvert.toFiCbpayMemberFtpDo(fiCbpayMemberFtpDto);
            fiCbpayMemberFtpManager.modifyByRecordId(fiCbpayMemberFtpDo);
            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("call  修改FTP信息申请异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 修改FTP信息返回结果:{}", result);
        return result;
    }
}
