package com.baofu.cbpayservice.biz.impl;

import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwExchangeRateResultDto;
import com.baofu.cbpayservice.biz.*;
import com.baofu.cbpayservice.biz.convert.BatchRemitBizConvert;
import com.baofu.cbpayservice.biz.integration.cm.CmClearBizImpl;
import com.baofu.cbpayservice.biz.models.*;
import com.baofu.cbpayservice.common.constants.NumberConstants;
import com.baofu.cbpayservice.common.enums.*;
import com.baofu.cbpayservice.dal.mapper.FiCbPayOrderAdditionMapper;
import com.baofu.cbpayservice.dal.mapper.FiCbPayOrderItemMapper;
import com.baofu.cbpayservice.dal.mapper.FiCbPayOrderMapper;
import com.baofu.cbpayservice.dal.mapper.FiCbpayFileUploadMapper;
import com.baofu.cbpayservice.dal.models.*;
import com.baofu.cbpayservice.manager.CbPayRemittanceManager;
import com.baofu.cbpayservice.manager.CbPaySettleBankManager;
import com.baofu.cbpayservice.manager.OrderIdManager;
import com.baofu.order.pick.OrderPick;
import com.baofu.order.pick.model.Order;
import com.baofu.order.pick.model.OrderDetails;
import com.baofu.order.pick.model.PickOrderSum;
import com.google.common.collect.Lists;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.MDC;
import org.slf4j.helpers.SystemMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 批量汇款接口
 * <p>
 * </p>
 *
 * @author 不良人 Date:2017/10/20 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class BatchRemitBizImpl implements BatchRemitBiz {

    /**
     * 发送mq服务
     */
    @Autowired
    private MqSendServiceImpl mqSendService;

    /**
     * 代理报关服务
     */
    @Autowired
    private ProxyCustomsBiz proxyCustomsBiz;

    /**
     * 主键ID生成服务
     */
    @Autowired
    private OrderIdManager orderIdManager;

    /**
     * 查询账户可用余额服务类
     */
    @Autowired
    private CmClearBizImpl cmClearBizImpl;

    /**
     * 订单服务类
     */
    @Autowired
    private FiCbPayOrderMapper fiCbPayOrderMapper;

    /**
     * 文件批次服务
     */
    @Autowired
    private FiCbpayFileUploadMapper uploadMapper;

    /**
     * 文件批次服务
     */
    @Autowired
    private ExchangeRateQueryBiz exchangeRateQueryBiz;

    /**
     * 渠道服务
     */
    @Autowired
    private CbPayChannelFacadeBiz cbPayChannelFacadeBiz;

    /**
     * 网关公共服务信息
     */
    @Autowired
    private CbPayCommonBiz cbPayCommonBiz;

    /**
     * 跨境调用清结算Biz层相关操作
     */
    @Autowired
    private CbPayCmBiz cbPayCmBiz;

    /**
     * 跨境人民币订单信息
     */
    @Autowired
    private FiCbPayOrderMapper orderMapper;

    /**
     * 跨境人民币订单信息
     */
    @Autowired
    private FiCbPayOrderItemMapper itemMapper;

    /**
     * 跨境人民币订单信息
     */
    @Autowired
    private FiCbPayOrderAdditionMapper additionMapper;

    /**
     * 跨境人民币订单信息
     */
    @Autowired
    private CbPayRemittanceManager cbPayRemittanceManager;

    /**
     * 多币种账户信息数据服务接口
     */
    @Autowired
    private CbPaySettleBankManager bankManager;

    /**
     * 锁
     */
    @Autowired
    private LockBiz lockBiz;

    /**
     * 批量汇款文件上传
     *
     * @param remitFileBo 文件参数
     * @return 文件批次号
     */
    @Override
    public Long batchRemitFileUpload(RemitFileBo remitFileBo) {

        Long fileBatchNo = orderIdManager.orderIdCreate();
        //文件批次插入
        proxyCustomsBiz.insertFileUpload(BatchRemitBizConvert.toFiCbPayFileUploadBo(remitFileBo, fileBatchNo));
        //发送Mq消息
        ProxyCustomsMqBo proxyCustomsMqBo = BatchRemitBizConvert.toProxyCustomsMqBo(remitFileBo, fileBatchNo);
        mqSendService.sendMessage(MqSendQueueNameEnum.CBPAY_PROXY_CUSTOMS_QUEUE_NAME, proxyCustomsMqBo);
        log.info("call 批量汇款文件上传，生产者，消息队列：{},内容为：{}",
                MqSendQueueNameEnum.CBPAY_PROXY_CUSTOMS_QUEUE_NAME, proxyCustomsMqBo);

        return fileBatchNo;
    }

    /**
     * @param memberId 商户号
     * @return 账户余额
     */
    @Override
    public AccountBalanceRespBo queryBalance(Long memberId) {

        if (memberId == null) {
            log.error("call 查询账户余额，商户号不允许为空");
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0017);
        }
        //账户可用余额
        QueryBalanceBo accountBalanceReq = new QueryBalanceBo(memberId.intValue(), NumberConstants.ONE);
        BigDecimal accountAmount = cmClearBizImpl.queryBalance(accountBalanceReq);
        log.info("call 商户{}账户可用余额：{}", memberId, accountAmount);
        //剩余订单可用金额
        BigDecimal orderAmount = fiCbPayOrderMapper.countOrderAmountByMemberId(memberId);
        log.info("call 商户{}剩余订单可用金额：{}", memberId, orderAmount);

        return new AccountBalanceRespBo(orderAmount, accountAmount);
    }

    /**
     * 批量汇款创建文件批次
     *
     * @param batchRemitBo 汇款文件批次参数
     * @param flag         标识
     * @param key          锁key
     * @return 文件批次号
     */
    @Override
    public Long createFileBatch(BatchRemitBo batchRemitBo, Boolean flag, String key) {

        Long fileBatchNo = orderIdManager.orderIdCreate();
        try {
            //创建文件批次
            List<Order> details = orderMapper.selectBatchRemitOrder(batchRemitBo.getMemberId());
            OrderDetails orderList = new OrderDetails();
            orderList.addAll(details);
            OrderPick orderPick = new OrderPick();
            log.info("[批量汇款] 创建文件批次,调用凑单服务，汇款金额:{},订单总条数:{},batchNo:{}",
                    batchRemitBo.getRemitAmt(), orderList.size(), String.valueOf(fileBatchNo));
            PickOrderSum pick = orderPick.pick(batchRemitBo.getRemitAmt(), orderList, String.valueOf(fileBatchNo),
                    MDC.get(SystemMarker.TRACE_LOG_ID));
            log.info("[批量汇款] 创建文件批次,调用凑单服务,返回商户订单汇总信息，汇款金额:{},订单总条数:{}",
                    pick.getMerchantOrderSum().getTotalAmt(), pick.getMerchantOrderSum().getTotalNum());
            if (pick.isGenerateSysOrder()) {
                log.info("[批量汇款] 创建文件批次,调用凑单服务,返回系统订单汇总信息，汇款金额:{},订单总条数:{}",
                        pick.getSystemOrderSum().getTotalAmt(), pick.getSystemOrderSum().getTotalNum());
            }
            FiCbPayFileUploadDo uploadDo = BatchRemitBizConvert.toFiCbPayFileUploadDo(batchRemitBo, pick, fileBatchNo);
            uploadMapper.insert(uploadDo);
            //更新跨境订单
            if (!CollectionUtils.isEmpty(pick.getMerchantOrderSum().getOrderDetails())) {
                List<Long> orderIdList = Lists.newArrayList();
                for (Order order : pick.getMerchantOrderSum().getOrderDetails()) {
                    orderIdList.add(order.getOrderId());
                }
                orderMapper.batchRemitUpdate(orderIdList, fileBatchNo, batchRemitBo.getMemberId());
            }
            //插入订单信息
            insertSystemOrder(pick, batchRemitBo, fileBatchNo);
            //发送Mq消息，创建文件
            CreateOrderDetailBo detailBo = BatchRemitBizConvert.toCreateOrderDetailBo(batchRemitBo, fileBatchNo);
            mqSendService.sendMessage(MqSendQueueNameEnum.CREATE_BATCH_REMIT_DETAIL_QUEUE_NAME, detailBo);
            log.info("[批量量汇款创建文件批次批] 生产者，消息队列：{},内容为：{}",
                    MqSendQueueNameEnum.CREATE_BATCH_REMIT_DETAIL_QUEUE_NAME, detailBo);
        } catch (Exception e) {
            if (flag) {
                lockBiz.unLock(key);
            }
            log.error("[批量量汇款创建文件批次批] 异常", e);
        }
        return fileBatchNo;
    }

    /**
     * 试算
     *
     * @param batchRemitBos 试算参数
     */
    @Override
    public List<BatchRemitTrialBo> batchRemitTrial(List<BatchRemitBo> batchRemitBos) {

        List<BatchRemitTrialBo> trialBoList = Lists.newArrayList();
        for (BatchRemitBo batchRemitBo : batchRemitBos) {
            Long channelId = cbPayCommonBiz.queryChannelId(batchRemitBo.getMemberId(), batchRemitBo.getTargetCcy(),
                    ChannelTypeEnum.PURCHASE_PAYMENT.getCode());
            CgwExchangeRateResultDto exchangeRate = cbPayChannelFacadeBiz.getExchangeRate(channelId,
                    batchRemitBo.getTargetCcy());
            if (exchangeRate.getCode() != 1) {
                log.error("[批量量汇款-试算] 渠道查汇失败,商户号：{}, 币种：{}", batchRemitBo.getMemberId(),
                        batchRemitBo.getTargetCcy());
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00123);
            }
            log.info("[批量量汇款-试算] 查询汇率：{}", exchangeRate.getSellRateOfCcy());
            //计算浮动汇率
            ExchangeRateQueryBo exchangeRateQueryBo = exchangeRateQueryBiz.queryFloatRate(batchRemitBo.getMemberId(),
                    batchRemitBo.getTargetCcy(), exchangeRate, batchRemitBo.getEntityNo());
            BigDecimal rate = exchangeRateQueryBo.getSellRateOfCcy();
            //结算金额=出款金额/交易汇率;
            BigDecimal balanceAllMoney = batchRemitBo.getRemitAmt().divide(rate, NumberConstants.TWO,
                    BigDecimal.ROUND_DOWN);
            // 查询手续费
            Long proxyOrderId = orderIdManager.orderIdCreate();
            int functionId = FunctionEnum.getFunctionId(RemittanceOrderType.PROXY_REMITTANCE_ORDER.getCode(),
                    batchRemitBo.getTargetCcy());
            int productId = FunctionEnum.getProductId(RemittanceOrderType.PROXY_REMITTANCE_ORDER.getCode());
            MemberFeeResBo feeResult = cbPayCmBiz.getFeeResult(String.valueOf(proxyOrderId), batchRemitBo.getRemitAmt(),
                    batchRemitBo.getMemberId(), functionId, productId, MDC.get(SystemMarker.TRACE_LOG_ID));

            BatchRemitTrialBo remitTrialBo = BatchRemitBizConvert.toBatchRemitTrialBo(rate, balanceAllMoney, feeResult,
                    batchRemitBo);
            trialBoList.add(remitTrialBo);
        }

        log.info("[批量量汇款-试算] 返回结果：{}", trialBoList);
        return trialBoList;
    }

    /**
     * 创建系统订单
     *
     * @param pick         系统订单信息
     * @param batchRemitBo 汇款信息
     * @param fileBatchNo  文件批次号
     */
    private void insertSystemOrder(PickOrderSum pick, BatchRemitBo batchRemitBo, Long fileBatchNo) {

        if (pick.isGenerateSysOrder()) {
            List<FiCbPayOrderDo> orderDoList = Lists.newArrayList();
            List<FiCbPayOrderItemDo> itemDoList = Lists.newArrayList();
            List<FiCbPayOrderAdditionDo> additionDoList = Lists.newArrayList();
            for (Order order : pick.getSystemOrderSum().getOrderDetails()) {
                Long orderId = orderIdManager.orderIdCreate();
                FiCbPayOrderDo orderDo = BatchRemitBizConvert.toFiCbPayOrderDo(order, orderId, batchRemitBo, fileBatchNo);
                FiCbPayOrderItemDo itemDo = BatchRemitBizConvert.toFiCbPayOrderItemDo(orderDo);
                FiCbPayOrderAdditionDo additionDo = BatchRemitBizConvert.additionConvert(orderDo);
                orderDoList.add(orderDo);
                itemDoList.add(itemDo);
                additionDoList.add(additionDo);
            }
            orderMapper.batchInsert(orderDoList);
            itemMapper.batchInsert(itemDoList);
            additionMapper.batchInsert(additionDoList);
        }
    }

    /**
     * 创建失败汇款订单
     *
     * @param batchRemitBo 汇款订单请求信息
     */
    @Override
    public void createErrorRemitOrder(BatchRemitBo batchRemitBo) {
        Long batchNo = orderIdManager.orderIdCreate();
        log.info("[创建批量汇款汇款订单]- 创建失败汇款订单参数：{},批次号{}", batchRemitBo, batchNo);
        //查询商户币种账户信息
        FiCbPaySettleBankDo fiCbPaySettleBankDo = bankManager.selectBankAccByEntityNo(batchRemitBo.getMemberId(),
                batchRemitBo.getTargetCcy(), batchRemitBo.getEntityNo());
        log.info("查询商户币种账户信息：{}", fiCbPaySettleBankDo);
        cbPayRemittanceManager.createRemittanceOrder(BatchRemitBizConvert.remittanceParamConvert(batchRemitBo,
                batchNo, fiCbPaySettleBankDo));
        cbPayRemittanceManager.createRemittanceAddition(BatchRemitBizConvert.additionParamConvert(batchRemitBo,
                batchNo, fiCbPaySettleBankDo));
    }
}
