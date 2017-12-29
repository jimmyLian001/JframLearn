package com.baofu.cbpayservice.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.baofoo.cache.service.facade.model.CacheMemberDto;
import com.baofoo.rm.request.CBOrderRequest;
import com.baofoo.rm.response.CBOrderAuditResponse;
import com.baofoo.rm.ws.dubbo.core.IRmRequestService;
import com.baofu.cbpayservice.biz.CbpayOrderRiskControlBiz;
import com.baofu.cbpayservice.biz.MqSendService;
import com.baofu.cbpayservice.biz.convert.CbpayRiskConvert;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.FlagEnum;
import com.baofu.cbpayservice.common.enums.MqSendQueueNameEnum;
import com.baofu.cbpayservice.common.util.StringUtil;
import com.baofu.cbpayservice.dal.models.*;
import com.baofu.cbpayservice.manager.*;
import com.google.common.collect.Lists;
import com.system.commons.utils.DateUtil;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 风控处理业务服务
 * <p>
 * 1、判断是否有redis标识
 * 2、创建redis标识
 * 3、跨境订单风控定时任务接口
 * </p>
 * User: wdj Date:2017/04/27 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class CbpayOrderRiskControlBizImpl implements CbpayOrderRiskControlBiz {

    /**
     * 购付汇 风控任务结束标志
     */
    private static final String CBPAYORDER_RISKCONTROL_END_FLAG = "CBPAYORDER_RISKCONTROL_END_FLAG";
    /**
     * 结汇  风控任务结束标志
     */
    private static final String CBPAYORDER_RISKCONTROL_END_FLAG_SETTLE = "CBPAYORDER_RISKCONTROL_END_FLAG_SETTLE";
    /**
     * 缓存操作服务
     */
    @Autowired
    private CbPayCacheManager cbPayCacheManager;
    /**
     * 发送MQ服务
     */
    @Autowired
    private MqSendService mqSendService;
    /**
     * 缓存服务
     */
    @Autowired
    private RedisManager redisManager;
    /**
     * 跨境订单服务
     */
    @Autowired
    private CbPayOrderManager cbPayOrderManager;
    /**
     * 结汇订单服务
     */
    @Autowired
    private CbPaySettleManager cbPaySettleManager;
    /**
     * 订单附件信息服务
     */
    @Autowired
    private OrderAdditionManager orderAdditionManager;
    /**
     * 跨境订单商品信息操作
     */
    @Autowired
    private OrderItemManager orderItemManager;
    /**
     * 跨境订单物流信息查询
     */
    @Autowired
    private CbpayLogisticsManager cbpayLogisticsManager;
    /**
     * 风控系统服务
     */
    @Autowired
    private IRmRequestService iRmRequestService;
    /**
     * 跨境风控订单服务
     */
    @Autowired
    private CbPayRiskOrderManager cbPayRiskOrderManager;
    /**
     * 汇款订单服务
     */
    @Autowired
    private CbPayRemittanceManager cbPayRemittanceManager;
    /**
     * 跨境订单风控批单次处理数量
     */
    @Value(("${orderCount}"))
    private Integer orderCount;

    /**
     * 跨境订单风控批单次处理数量
     */
    @Value(("${settle_orderCount}"))
    private Integer settleOrderCount;

    /**
     * 判断是否有redis标识
     *
     * @param traceLogId 日志ID
     * @return 返回结果
     */
    @Override
    public Boolean isComplete(String key, String traceLogId) {
        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        String value = redisManager.queryObjectByKey(key);
        return FlagEnum.TRUE.getCode().equals(value);
    }

    /**
     * 跨境订单风控定时任务接口实现
     *
     * @param traceLogId 日志ID
     */
    @Override
    public void doCbPayOrderRiskControl(String traceLogId, String date) {

        MDC.put(SystemMarker.TRACE_LOG_ID, traceLogId);
        //过滤后的批次集合
        List<String> newBatchNos = Lists.newArrayList();
        try {
            //添加该批次风控标识
            redisManager.insertObject(CBPAYORDER_RISKCONTROL_END_FLAG, Constants.CBPAYORDER_RISKCONTROL_END_FLAG);
            log.info("call 购付汇  创建本次风控任务标识成功！");
            //查询出当天汇款批次号集合
            List<String> batchNos = getBatchNos("today", 0);
            //查询出已经风控的批次集合
            List<String> hasAlreadyRiskBatches = redisManager.queryListByKey(Constants.CBPAYORDER_RISKCONTROL_ALREADYLIST, String.class);
            //重新风控的集合
            List<String> batchNoList = null;
            if (!StringUtil.isBlank(date) && checkDate(date)) {
                batchNoList = getBatchNos(date, 0);
            }
            if (!CollectionUtils.isEmpty(hasAlreadyRiskBatches)) {
                //如果batchNoList为空，需要将hasAlreadyRiskBatches去除掉batchNoList的集合，然后加入到新的需要风控的集合
                if (!CollectionUtils.isEmpty(batchNoList)) {
                    hasAlreadyRiskBatches.removeAll(batchNoList);
                    newBatchNos.addAll(batchNoList);
                }
                //将查询即将风控的批次号过滤掉已经风控的批次号
                for (String batchNo : batchNos) {
                    if (!hasAlreadyRiskBatches.contains(batchNo)) {
                        newBatchNos.add(batchNo);
                    }
                }
            } else {
                //第一次需要初始化缓存中的批次
                hasAlreadyRiskBatches = Lists.newArrayList();
                if (!CollectionUtils.isEmpty(batchNoList)) {
                    newBatchNos.addAll(batchNoList);
                }
                newBatchNos.addAll(batchNos);
            }
            if (CollectionUtils.isEmpty(newBatchNos)) {
                log.info("call 购付汇 当前没有需要风控的跨境订单,本次任务结束！");
                redisManager.deleteObject(Constants.CBPAYORDER_RISKCONTROL_END_FLAG);
                return;
            }
            log.info("call 购付汇 本次需要风控的汇款批大小为：{}", newBatchNos.size());
            hasAlreadyRiskBatches.addAll(newBatchNos);
            //将已风控的批次号缓存到redis中
            redisManager.insertObject(hasAlreadyRiskBatches, Constants.CBPAYORDER_RISKCONTROL_ALREADYLIST);
            log.info("call 购付汇 更新缓存汇款批次号成功！");
            //发送MQ处理消息进行订单风控
            mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_ORDER_RISKCONTROL_QUEUE_NAME, JSONObject.toJSONString(newBatchNos));
        } catch (Exception e) {
            log.error("call 购付汇    doCbPayOrderRiskControl 风控定时任务出现异常");
            if (!CollectionUtils.isEmpty(newBatchNos)) {
                List<String> hasAlreadyRiskBatches = redisManager.queryListByKey(Constants.CBPAYORDER_RISKCONTROL_ALREADYLIST, String.class);
                hasAlreadyRiskBatches.removeAll(newBatchNos);
                redisManager.insertObject(hasAlreadyRiskBatches, Constants.CBPAYORDER_RISKCONTROL_ALREADYLIST);
                log.error("call 购付汇   发生异常后更新redis缓存成功");
            }
            redisManager.deleteObject(Constants.CBPAYORDER_RISKCONTROL_END_FLAG);
            log.error("call 购付汇 redis结束标志清空成功，异常详情：{}", e);
        }
    }

    /**
     * 结汇订单风控  根据创建create_at 查询订单
     *
     * @param traceLogId 日志ID
     * @param date       日期
     */
    @Override
    public void doCbPayOrderRiskControlOfSettleV2(String traceLogId, String date) {
        try {
            //添加该批次风控标识
            redisManager.insertObject(CBPAYORDER_RISKCONTROL_END_FLAG_SETTLE, Constants.CBPAYORDER_RISKCONTROL_END_FLAG_SETTLE);
            String todayBegin = DateUtil.format(new Date(), DateUtil.smallDateFormat).concat(Constants.TIME);
            String todayEnd = DateUtil.format(new Date(), DateUtil.smallDateFormat).concat(Constants.TIMEEND);
            String oldBegin = null;
            String oldEnd = null;
            if (!StringUtil.isBlank(date) && checkDate(date)) {
                oldBegin = date.trim().concat(Constants.TIME);
                oldEnd = date.trim().concat(Constants.TIMEEND);
            }
            List<FiCbPaySettleOrderDo> needRiskControlSettleOrderList = cbPaySettleManager.queryNeedRiskControlOrderV2(
                    todayBegin, todayEnd, oldBegin, oldEnd, settleOrderCount);
            if (CollectionUtils.isEmpty(needRiskControlSettleOrderList)) {
                log.info("call V2结汇订单风控，暂时没有需要风控的结汇订单~");
            } else {
                doSettleRisk(needRiskControlSettleOrderList);
            }
        } catch (Exception e) {
            log.error("call doCbPayOrderRiskControlOfSettleV2 结汇异常详情：{}", e);
        } finally {
            redisManager.deleteObject(Constants.CBPAYORDER_RISKCONTROL_END_FLAG_SETTLE);
            log.info("call 结汇 redis结束标志清空成功");
        }

    }

    /**
     * 购付汇   处理订单风控的MQ
     *
     * @param batchNos 汇款批次号
     */
    @Override
    public void handelOrderRiskControl(List<String> batchNos) {

        try {
            log.info("call 购付汇 开始风控订单信息···");
            //循环处理汇款批次号
            for (String batchNo : batchNos) {
                //查询该批次有多少记录
                Long allOrderCount = cbPayOrderManager.queryCbPayOrderCountByBatchN0(batchNo);
                //查询出该汇款批次号对应的商户号
                Long memberId = cbPayOrderManager.queryMemberNoByBatchNo(batchNo);
                while (allOrderCount > 0) {
                    List<FiCbPayOrderDo> needRiskControlOrderList = cbPayOrderManager.queryNeedRiskControlOrder(memberId, batchNo, orderCount);
                    doRisk(needRiskControlOrderList);
                    allOrderCount = allOrderCount - needRiskControlOrderList.size();
                }
            }
        } catch (Exception e) {
            log.info("call 购付汇订单风控发生异常，清空本次汇款批次缓存，重新风控。");
            List<String> hasAlreadyRiskBatches = redisManager.queryListByKey(Constants.CBPAYORDER_RISKCONTROL_ALREADYLIST, String.class);
            hasAlreadyRiskBatches.removeAll(batchNos);
            redisManager.insertObject(hasAlreadyRiskBatches, Constants.CBPAYORDER_RISKCONTROL_ALREADYLIST);
            log.error("发生异常后更新redis汇款批次数据成功，异常详情：{}", e);
        } finally {
            //清空redis标识  本次任务结束
            log.info("call  购付汇    跨境订单风控定时任务结束");
            redisManager.deleteObject(Constants.CBPAYORDER_RISKCONTROL_END_FLAG);
        }
    }

    /**
     * 处理结汇订单风控任务
     *
     * @param batchNos 文件批次号
     */
    @Override
    public void handelSettleOrderRiskControl(List<String> batchNos) {

        try {
            //循环处理汇款批次号
            for (String batchNo : batchNos) {
                //查询该批次有多少记录
                Long allOrderCount = cbPaySettleManager.queryCbPaySettleOrderCountByBatchN0(batchNo);
                Long memberId = cbPaySettleManager.queryMemberIdByBatchNo(batchNo);
                while (allOrderCount > 0) {
                    List<FiCbPaySettleOrderDo> needRiskControlSettleOrderList = cbPaySettleManager.queryNeedRiskControlOrder(memberId, batchNo, settleOrderCount);
                    doSettleRisk(needRiskControlSettleOrderList);
                    allOrderCount = allOrderCount - needRiskControlSettleOrderList.size();
                }
            }
        } catch (Exception e) {
            log.info("call 结汇订单风控发生异常，清空本次结汇文件批次缓存，以待重新风控。");
            List<String> hasAlreadyRiskBatches = redisManager.queryListByKey(Constants.CBPAYORDER_RISKCONTROL_ALREADYLIST_SETTLE, String.class);
            hasAlreadyRiskBatches.removeAll(batchNos);
            redisManager.insertObject(hasAlreadyRiskBatches, Constants.CBPAYORDER_RISKCONTROL_ALREADYLIST_SETTLE);
            log.error("call 结汇  发生异常后更新redis汇款批次数据成功，异常详情：{}", e);
        } finally {
            //清空redis标识  本次任务结束
            log.info("call  结汇    跨境订单风控定时任务结束");
            redisManager.deleteObject(Constants.CBPAYORDER_RISKCONTROL_END_FLAG_SETTLE);
        }

    }

    /**
     * 调用风控接口进行订单风控  购付汇
     *
     * @param needRiskControlOrderList 风控订单信息
     */
    private void doRisk(List<FiCbPayOrderDo> needRiskControlOrderList) {
        for (FiCbPayOrderDo fiCbPayOrderDo : needRiskControlOrderList) {
            log.info("call 处理购付汇订单信息：{}", fiCbPayOrderDo);
            //根据宝付ID获取订单附加信息
            FiCbPayOrderAdditionDo fiCbPayOrderAdditionDo = orderAdditionManager.queryOrderAddition(fiCbPayOrderDo.getOrderId());
            //获取商品信息
            FiCbPayOrderItemsInfoDo fiCbPayOrderItemsInfoDo = orderItemManager.queryItemsInfo(fiCbPayOrderDo.getOrderId());
            //获取物流信息
            FiCbpayOrderLogisticsDo fiCbpayOrderLogisticsDo = cbpayLogisticsManager.queryOrderLogisticsByOrderId(fiCbPayOrderDo.getOrderId());
            //获取商户基本信息
            CacheMemberDto cacheMemberDto = cbPayCacheManager.getMember(fiCbPayOrderDo.getMemberId().intValue());
            log.info(" 查询商户信息缓存CacheMemberDto：{}", cacheMemberDto);
            //组装风控请求参数
            CBOrderRequest cbOrderRequest = CbpayRiskConvert.toCBOrderConvert(fiCbPayOrderDo, fiCbPayOrderAdditionDo,
                    fiCbPayOrderItemsInfoDo, fiCbpayOrderLogisticsDo, cacheMemberDto);
            log.info("call 请求风控请求参数信息：{}", JsonUtil.toJSONString(cbOrderRequest));
            //请求风控接口服务
            CBOrderAuditResponse cbOrderAuditResponse = iRmRequestService.cbRiskCheck(cbOrderRequest);
            log.info("风控返回数据信息：{}", JsonUtil.toJSONString(cbOrderAuditResponse));
            //审核拒绝  添加风险跨境订单
            if (cbOrderAuditResponse != null && Constants.REFUSE.equals(cbOrderAuditResponse.getResultCode())) {
                CbPayRiskOrderDo cbPayRiskOrderDo = CbpayRiskConvert.toRiskOrder(fiCbPayOrderDo, cbOrderAuditResponse, 1);
                cbPayRiskOrderManager.addRiskOrder(cbPayRiskOrderDo);
            }
            //更新跨境订单表，标志该笔订单已经风控
            fiCbPayOrderDo.setPayId(1);
            cbPayOrderManager.modifyCbPayOrder(fiCbPayOrderDo);
        }
    }

    /**
     * 循环风控结汇订单
     *
     * @param needRiskControlSettleOrderList 需要风控的结汇订单信息
     */
    private void doSettleRisk(List<FiCbPaySettleOrderDo> needRiskControlSettleOrderList) {

        for (FiCbPaySettleOrderDo fiCbPaySettleOrderDo : needRiskControlSettleOrderList) {
            log.info("call 处理结汇订单信息：{}", fiCbPaySettleOrderDo);
            //获取TT编号
            String incomeNo = cbPayOrderManager.queryIncomeNoByOrderId(fiCbPaySettleOrderDo.getOrderId());
            if (StringUtil.isBlank(incomeNo)) {
                log.info("汇入汇款编号为空，orderId = {}", fiCbPaySettleOrderDo.getOrderId());
                continue;
            }
            fiCbPaySettleOrderDo.setIncomeNo(incomeNo);
            //获取商品信息
            FiCbPayOrderItemsInfoDo fiCbPayOrderItemsInfoDo = orderItemManager.queryItemsInfo(fiCbPaySettleOrderDo.getOrderId());
            //获取物流信息
            FiCbpayOrderLogisticsDo fiCbpayOrderLogisticsDo = cbpayLogisticsManager.queryOrderLogisticsByOrderId(fiCbPaySettleOrderDo.getOrderId());
            //获取商户基本信息
            CacheMemberDto cacheMemberDto = cbPayCacheManager.getMember(fiCbPaySettleOrderDo.getMemberId().intValue());
            log.info("call  结汇 查询商户信息缓存CacheMemberDto：{}", cacheMemberDto);
            //组装风控请求参数
            CBOrderRequest cbOrderRequest = CbpayRiskConvert.toCBOrderConvertOfSettle(fiCbPaySettleOrderDo,
                    fiCbPayOrderItemsInfoDo, fiCbpayOrderLogisticsDo, cacheMemberDto);
            log.info("call 结汇  请求风控请求参数信息：{}", JsonUtil.toJSONString(cbOrderRequest));
            //请求风控接口服务
            CBOrderAuditResponse cbOrderAuditResponse = iRmRequestService.cbRiskCheck(cbOrderRequest);
            log.info("call 结汇  风控返回数据信息：{}", JsonUtil.toJSONString(cbOrderAuditResponse));
            //审核拒绝  添加风险跨境订单
            if (cbOrderAuditResponse != null && Constants.REFUSE.equals(cbOrderAuditResponse.getResultCode())) {
                CbPayRiskOrderDo cbPayRiskOrderDo = CbpayRiskConvert.toRiskOrderSettle(fiCbPaySettleOrderDo, cbOrderAuditResponse, 2);
                cbPayRiskOrderManager.addRiskOrder(cbPayRiskOrderDo);
            }
            //更新跨境订单表，标志该笔订单已经风控
            fiCbPaySettleOrderDo.setRisk_flag(1);
            cbPaySettleManager.modifyCbPaySettleOrder(fiCbPaySettleOrderDo);
        }

    }

    /**
     * 校验日期格式是否合法
     *
     * @param date 日期
     * @return
     */
    private boolean checkDate(String date) {
        log.info("需要校验的日期：{}", date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.parse(date.trim());
            return true;
        } catch (ParseException e) {
            log.error("输入的日期格式不正确，请输入正确的日期格式：yyyy-MM-dd，当前输入的日期为:{}", date);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据日期获取批次号
     *
     * @param date ：日期
     * @param type 0:购付汇  1: 结汇
     * @return 结果
     */
    private List<String> getBatchNos(String date, int type) {
        String beginDate;
        String endDate;
        List<String> result;
        if ("today".equals(date)) {
            beginDate = DateUtil.format(new Date(), DateUtil.smallDateFormat).concat(Constants.TIME);
            endDate = DateUtil.format(new Date(), DateUtil.smallDateFormat).concat(Constants.TIMEEND);
            result = type == 0 ? cbPayRemittanceManager.queryBatchNos(beginDate, endDate) : cbPaySettleManager.queryBatchNos(beginDate, endDate);
        } else {
            beginDate = date.trim().concat(Constants.TIME);
            endDate = date.trim().concat(Constants.TIMEEND);
            result = type == 0 ? cbPayRemittanceManager.queryBatchNos(beginDate, endDate) : cbPaySettleManager.queryBatchNos(beginDate, endDate);
        }
        return result;
    }

}
