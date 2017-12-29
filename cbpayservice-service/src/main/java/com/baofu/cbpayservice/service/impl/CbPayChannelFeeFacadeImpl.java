package com.baofu.cbpayservice.service.impl;

import com.baofu.cbpayservice.biz.CbPayChannelFeeBiz;
import com.baofu.cbpayservice.dal.models.FiCbPayChannelFeeDo;
import com.baofu.cbpayservice.facade.CbPayChannelFeeFacade;
import com.baofu.cbpayservice.facade.models.CbPayChannelFeeDto;
import com.baofu.cbpayservice.facade.models.CbPaySelectChannelFeePageDto;
import com.baofu.cbpayservice.facade.models.res.CbPayChannelFeeRespDto;
import com.baofu.cbpayservice.service.convert.CbPayChannelFeeConvert;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 跨境人民币汇款操作接口
 * <p>
 * 1、创建汇款订单
 * 2、汇款订单审核
 * 3、汇款订单状态更新
 * </p>
 * User: wanght Date:2016/11/10 ProjectName: cbpay-service Version: 1.0
 */
@Slf4j
@Service
public class CbPayChannelFeeFacadeImpl implements CbPayChannelFeeFacade {

    /**
     * 渠道成本配置服务接口
     */
    @Autowired
    private CbPayChannelFeeBiz cbPayChannelFeeBiz;

    /**
     * 查询渠道成本配置总数
     *
     * @param channelId  渠道id
     * @param beginTime  开始时间
     * @param endTime    结束时间
     * @param traceLogId 日志id
     * @return 总记录数
     */
    @Override
    public Result<Integer> selectCount(Long channelId, String beginTime, String endTime, String traceLogId) {
        Result<Integer> result;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            log.info("call 查询渠道成本配置总数请求参数,channelId:{}, beginTime:{}, endTime:{}", channelId, beginTime, endTime);
            Integer count = cbPayChannelFeeBiz.selectCount(channelId, beginTime, endTime);
            result = new Result<>(count);
        } catch (Exception e) {
            log.error("call 查询渠道成本配置总数 Exception:", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 查询渠道成本配置总数 RESULT:{}", result);
        return result;
    }

    /**
     * 分页查询
     *
     * @param cbPaySelectChannelFeePageDto 分页查询请求参数
     * @param traceLogId                   日志ID
     * @return 查询结果
     */
    @Override
    public Result<List<CbPayChannelFeeRespDto>> selectPageList(CbPaySelectChannelFeePageDto cbPaySelectChannelFeePageDto, String traceLogId) {
        Result<List<CbPayChannelFeeRespDto>> result;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            log.info("call 渠道成本分页查询请求参数,{}", cbPaySelectChannelFeePageDto);

            List<FiCbPayChannelFeeDo> list = cbPayChannelFeeBiz.selectPageList(
                    CbPayChannelFeeConvert.selectPageListParamConvert(cbPaySelectChannelFeePageDto));

            result = new Result<>(CbPayChannelFeeConvert.pageListResultConvert(list));

            log.info("call 渠道成本分页查询成功");
        } catch (Exception e) {
            log.error("call 渠道成本分页查询 Exception:", e);
            result = ExceptionUtils.getResponse(e);
        }
        return result;
    }

    /**
     * 新增渠道成本配置
     *
     * @param cbPayChannelFeeDto 新增请求参数
     * @param traceLogId         日志ID
     * @return 返回是否成功
     */
    @Override
    public Result<Boolean> addChannelFee(CbPayChannelFeeDto cbPayChannelFeeDto, String traceLogId) {

        Result<Boolean> result;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            log.info("call 新增渠道成本配置请求参数:{}", cbPayChannelFeeDto);

            cbPayChannelFeeBiz.addChannelFee(CbPayChannelFeeConvert.addParamConvert(cbPayChannelFeeDto));

            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("call 新增渠道成本配置 Exception:", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 新增渠道成本配置 RESULT:{}", result);
        return result;
    }

    /**
     * 修改渠道成本配置
     *
     * @param cbPayChannelFeeDto 修改请求参数
     * @param traceLogId         日志ID
     * @return 返回是否成功
     */
    @Override
    public Result<Boolean> editChannelFee(CbPayChannelFeeDto cbPayChannelFeeDto, String traceLogId) {
        Result<Boolean> result;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            log.info("call 修改渠道成本配置请求参数:{}", cbPayChannelFeeDto);

            cbPayChannelFeeBiz.editChannelFee(CbPayChannelFeeConvert.editParamConvert(cbPayChannelFeeDto));

            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("call 修改渠道成本配置 Exception:", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 修改渠道成本配置 RESULT:{}", result);
        return result;
    }

    /**
     * 查询渠道成本配置信息
     *
     * @param recordId   记录编号
     * @param traceLogId 日志ID
     * @return 渠道成本配置信息
     */
    @Override
    public Result<CbPayChannelFeeRespDto> queryChannelFeeByKey(Long recordId, String traceLogId) {
        Result<CbPayChannelFeeRespDto> result;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            log.info("call 查询渠道成本配置请求参数:{}", recordId);

            FiCbPayChannelFeeDo fiCbPayChannelFeeDo = cbPayChannelFeeBiz.queryChannelFeeByKey(recordId);

            result = new Result<>(CbPayChannelFeeConvert.queryResultParamConvert(fiCbPayChannelFeeDo));
        } catch (Exception e) {
            log.error("call 查询渠道成本配置 Exception:", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 查询渠道成本配置 RESULT:{}", result);
        return result;
    }

    /**
     * 查询渠道成本配置信息
     *
     * @param channelId  渠道id
     * @param status     状态
     * @param traceLogId 日志ID
     * @return 渠道成本配置信息
     */
    @Override
    public Result<CbPayChannelFeeRespDto> queryChannelFee(Long channelId, String status, String traceLogId) {
        Result<CbPayChannelFeeRespDto> result;
        try {
            MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
            log.info("call 查询渠道成本配置请求参数:{}, {}", channelId, status);

            FiCbPayChannelFeeDo fiCbPayChannelFeeDo = cbPayChannelFeeBiz.queryChannelFee(channelId, status);

            result = new Result<>(CbPayChannelFeeConvert.queryResultParamConvert(fiCbPayChannelFeeDo));
        } catch (Exception e) {
            log.error("call 查询渠道成本配置 Exception:", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 查询渠道成本配置 RESULT:{}", result);
        return result;
    }
}
