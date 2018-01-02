package com.baofu.international.global.account.core.biz.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.cross.border.order.pick.OrderPick;
import com.baofu.international.cross.border.order.pick.model.Order;
import com.baofu.international.cross.border.order.pick.model.OrderDetails;
import com.baofu.international.cross.border.order.pick.model.PickOrderSum;
import com.baofu.international.global.account.core.biz.UserWithdrawOrderBiz;
import com.baofu.international.global.account.core.biz.models.BatchWithdrawBo;
import com.baofu.international.global.account.core.common.constant.ConfigDict;
import com.baofu.international.global.account.core.common.constant.Constants;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.enums.FileStateEnum;
import com.baofu.international.global.account.core.common.enums.UploadFileEnum;
import com.baofu.international.global.account.core.common.enums.UploadFileOrderEnum;
import com.baofu.international.global.account.core.common.util.SecurityUtil;
import com.baofu.international.global.account.core.dal.mapper.ExternalOrderItemMapper;
import com.baofu.international.global.account.core.dal.mapper.ExternalOrderMapper;
import com.baofu.international.global.account.core.dal.mapper.UserWithdrawFileUploadMapper;
import com.baofu.international.global.account.core.dal.mapper.UserWithdrawOrderMapper;
import com.baofu.international.global.account.core.dal.model.*;
import com.baofu.international.global.account.core.manager.OrderIdManager;
import com.baofu.international.global.account.core.manager.OrderItemManager;
import com.baofu.international.global.account.core.manager.UserWithdrawCashManager;
import com.baofu.international.global.account.core.manager.UserWithdrawOrderManager;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 功能：用户自主注册平台提现订单生成服务
 * User: feng_jiang Date:2017/11/20 ProjectName: globalaccount-core Version: 1.0
 **/
@Slf4j
@Service
public class UserWithdrawOrderBizImpl implements UserWithdrawOrderBiz {

    /**
     * 订单主键生成服务
     */
    @Autowired
    private OrderIdManager orderIdManager;

    /**
     * 提现订单信息
     */
    @Autowired
    private UserWithdrawOrderMapper userWithdrawOrderMapper;

    /**
     * 文件上传表操作类
     */
    @Autowired
    private UserWithdrawFileUploadMapper userWithdrawFileUploadMapper;

    /**
     * 用户提现商品表操作类
     */
    @Autowired
    private OrderItemManager orderItemManager;

    /**
     * 用户订单表操作类
     */
    @Autowired
    private
    ExternalOrderMapper externalOrderMapper;

    /**
     * 用户订单明细表操作类
     */
    @Autowired
    private
    ExternalOrderItemMapper externalOrderItemMapper;

    /**
     * 配置中心
     */
    @Autowired
    private ConfigDict configDict;

    /**
     * 用户前台提现操作类
     */
    @Autowired
    private UserWithdrawCashManager userWithdrawCashManager;

    /**
     * 提现订单明细Manager服务
     */
    @Autowired
    private UserWithdrawOrderManager userWithdrawOrderManager;

    /**
     * 功能：创建批量提现文件批次
     *
     * @param batchWithdrawBo 提现文件批次参数
     */
    @Override
    @Transactional
    public void createWithdrawFileBatch(BatchWithdrawBo batchWithdrawBo) {
        Long fileBatchNo = orderIdManager.orderIdCreate();
        //获取用户店铺订单信息
        List<ExternalOrderDo> externalOrderList = this.queryValidExternalOrder(batchWithdrawBo);
        batchWithdrawBo.setWithdrawAmt(batchWithdrawBo.getWithdrawAmt().multiply((BigDecimal.ONE.
                subtract(new BigDecimal(configDict.getWyreChannelFeeRate())))).setScale(NumberDict.TWO, BigDecimal.ROUND_DOWN));
        PickOrderSum pick = this.processPickOrder(batchWithdrawBo, fileBatchNo, externalOrderList);
        UserWithdrawFileUploadDo
                userWithdrawFileUploadDo = this.toUserWithdrawFileUploadDo(batchWithdrawBo, pick, fileBatchNo);
        userWithdrawFileUploadMapper.insert(userWithdrawFileUploadDo);
        if (CollectionUtils.isEmpty(pick.getMerchantOrderSum().getOrderDetails())) {
            log.info("凑单服务，未凑齐合适用户订单，用户号：{}，卖家编号：{}", batchWithdrawBo.getUserNo(), batchWithdrawBo.getSellerId());
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190030);
        }
        //新增提现订单
        List<Long> orderIdList = Lists.newArrayList();
        for (Order order : pick.getMerchantOrderSum().getOrderDetails()) {
            orderIdList.add(order.getOrderId());
        }
        //用户提现订单信息入库
        this.saveUserWithdrawOrder(batchWithdrawBo, fileBatchNo, pick, orderIdList);
        //更新已使用订单状态
        externalOrderMapper.batchUpdateExternalOrderState(orderIdList, NumberDict.ONE, batchWithdrawBo.getSellerId());
    }

    /**
     * 功能：获取有效外部订单（目前仅亚马孙平台）
     *
     * @param batchWithdrawBo 用户提现信息
     * @return 店铺可用订单
     */
    private List<ExternalOrderDo> queryValidExternalOrder(BatchWithdrawBo batchWithdrawBo) {
        BigDecimal userBal = externalOrderMapper.sumExternalOrderAmt(batchWithdrawBo.getSellerId());
        if (batchWithdrawBo.getWithdrawAmt().compareTo(userBal) > 0) {
            log.error("用户该店铺下可用订单订单金额不足，用户号：{}，卖家编号：{} ，账户号：{}",
                    batchWithdrawBo.getUserNo(), batchWithdrawBo.getSellerId(), batchWithdrawBo.getAccountNo());
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190032);
        }
        ExternalOrderDo externalOrderDo = new ExternalOrderDo();
        externalOrderDo.setSellerId(batchWithdrawBo.getSellerId());
        externalOrderDo.setUserNo(batchWithdrawBo.getUserNo());
        externalOrderDo.setState(NumberDict.ZERO);
        return externalOrderMapper.queryExternalOrderInfo(externalOrderDo);
    }

    /**
     * 功能：用户提现订单信息入库
     *
     * @param batchWithdrawBo 用户提现信息
     * @param fileBatchNo     文件批次号
     * @param pick            凑单数据
     * @param orderIdList     订单集合中使用到的订单
     */
    private void saveUserWithdrawOrder(BatchWithdrawBo batchWithdrawBo, Long fileBatchNo, PickOrderSum pick, List<Long> orderIdList) {
        List<ExternalOrderDo> externalOrderList = externalOrderMapper.selectBySellerId(batchWithdrawBo.getSellerId(),
                orderIdList, NumberDict.ZERO);
        List<UserWithdrawOrderDo> withdrawOrderDoList = Lists.newArrayList();
        HashMap<Long, Long> orderMap = Maps.newHashMap();
        for (ExternalOrderDo orderDo : externalOrderList) {
            UserWithdrawOrderDo userWithdrawOrderDo = new UserWithdrawOrderDo();
            BeanUtils.copyProperties(orderDo, userWithdrawOrderDo);
            userWithdrawOrderDo.setOrderId(orderIdManager.orderIdCreate());
            orderMap.put(orderDo.getOrderId(), userWithdrawOrderDo.getOrderId());
            userWithdrawOrderDo.setUserNo(batchWithdrawBo.getUserNo());
            userWithdrawOrderDo.setPayeeIdNo(batchWithdrawBo.getPayeeIdNo());
            userWithdrawOrderDo.setPayeeIdType(batchWithdrawBo.getPayeeIdType());
            userWithdrawOrderDo.setPayeeName(batchWithdrawBo.getPayeeName());
            userWithdrawOrderDo.setFileBatchNo(fileBatchNo);
            //宝付备付金账户号
            userWithdrawOrderDo.setPayeeAccNo(SecurityUtil.desEncrypt(configDict.getDestAccNo(), Constants.CARD_DES_KEY));
            withdrawOrderDoList.add(userWithdrawOrderDo);
        }
        //新增订单信息
        userWithdrawOrderMapper.batchInsert(withdrawOrderDoList);
        List<WithdrawOrderItemDo> itemDoList = Lists.newArrayList();
        //查询出订单明细信息
        List<ExternalOrderItemDo> externalItemDoList = externalOrderItemMapper.selectByOrderId(orderIdList);
        WithdrawOrderItemDo withdrawOrderItemDo;
        for (ExternalOrderItemDo itemDo : externalItemDoList) {
            withdrawOrderItemDo = new WithdrawOrderItemDo();
            BeanUtils.copyProperties(itemDo, withdrawOrderItemDo);
            withdrawOrderItemDo.setOrderId(orderMap.get(itemDo.getOrderId()));
            itemDoList.add(withdrawOrderItemDo);
        }
        orderItemManager.addBatchOrderItem(itemDoList);
        //插入差异信息
        this.insertSystemOrder(pick, withdrawOrderDoList.get(0), fileBatchNo);
    }

    /**
     * 根据提现明细批次号，更新已使用外部订单为未使用
     ***/


    /**
     * 功能：处理订单
     *
     * @param batchWithdrawBo   提现订单信息
     * @param fileBatchNo       提现批次号
     * @param externalOrderList 提现可用订单
     * @return 凑单处理结果
     * @throws Exception 凑单异常
     */
    private PickOrderSum processPickOrder(BatchWithdrawBo batchWithdrawBo, Long fileBatchNo, List<ExternalOrderDo> externalOrderList) {
        List<Order> details = Lists.newArrayList();
        Order pickOrder;
        for (ExternalOrderDo orderDo : externalOrderList) {
            pickOrder = new Order();
            pickOrder.setOrderId(orderDo.getOrderId());
            pickOrder.setOrderAmt(orderDo.getOrderAmt());
            pickOrder.setOrderDate(orderDo.getTradeAt());
            details.add(pickOrder);
        }
        OrderDetails orderList = new OrderDetails();
        orderList.addAll(details);
        OrderPick orderPick = new OrderPick();
        PickOrderSum pick = new PickOrderSum();
        try {
            log.info("[批量提现] 创建文件批次,调用凑单服务，提现到账金额:{},订单总条数:{},batchNo:{}",
                    batchWithdrawBo.getWithdrawAmt(), orderList.size(), String.valueOf(fileBatchNo));
            pick = orderPick.pick(batchWithdrawBo.getWithdrawAmt(), orderList, String.valueOf(fileBatchNo),
                    MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("[批量提现] 创建文件批次,调用凑单服务,返回商户订单汇总信息，提现到账金额:{},订单总条数:{}",
                    pick.getMerchantOrderSum().getTotalAmt(), pick.getMerchantOrderSum().getTotalNum());
            if (pick.isGenerateSysOrder()) {
                log.info("[批量提现] 创建文件批次,调用凑单服务,返回系统订单汇总信息，提现到账金额:{},订单总条数:{}",
                        pick.getSystemOrderSum().getTotalAmt(), pick.getSystemOrderSum().getTotalNum());
            }
        } catch (Exception e) {
            log.error("凑单服务异常，Exception：{}", e);
        }
        return pick;
    }

    /**
     * 功能：创建系统订单
     *
     * @param pick                系统订单信息
     * @param userWithdrawOrderDo 订单信息
     * @param fileBatchNo         文件批次号
     */
    private void insertSystemOrder(PickOrderSum pick, UserWithdrawOrderDo userWithdrawOrderDo, Long fileBatchNo) {
        if (pick.isGenerateSysOrder()) {
            List<UserWithdrawOrderDo> orderDoList = Lists.newArrayList();
            List<WithdrawOrderItemDo> itemDoList = Lists.newArrayList();
            UserWithdrawOrderDo orderDo;
            Long orderId;
            for (Order order : pick.getSystemOrderSum().getOrderDetails()) {
                orderId = orderIdManager.orderIdCreate();
                orderDo = new UserWithdrawOrderDo();
                BeanUtils.copyProperties(userWithdrawOrderDo, orderDo);
                orderDo.setOrderId(orderId);
                orderDo.setUserTransId(String.valueOf(orderId));
                orderDo.setTradeAt(new Date());
                orderDo.setFileBatchNo(fileBatchNo);
                orderDo.setOrderAmt(order.getOrderAmt());
                //宝付备付金账户号
                orderDo.setPayeeAccNo(SecurityUtil.desEncrypt(configDict.getDestAccNo(), Constants.CARD_DES_KEY));
                WithdrawOrderItemDo itemDo = this.toFiCbPayOrderItemDo(orderDo);
                orderDoList.add(orderDo);
                itemDoList.add(itemDo);
            }
            userWithdrawOrderMapper.batchInsert(orderDoList);
            orderItemManager.addBatchOrderItem(itemDoList);
        }
    }

    /**
     * 功能：批量汇款-创建文件批次信息
     *
     * @param bo          批量汇款参数
     * @param pick        凑单汇总
     * @param fileBatchNo 文件批次
     * @return 文件批次订单信息
     */
    public UserWithdrawFileUploadDo toUserWithdrawFileUploadDo(BatchWithdrawBo bo, PickOrderSum pick, Long fileBatchNo) {
        UserWithdrawFileUploadDo userWithdrawFileUploadDo = new UserWithdrawFileUploadDo();
        userWithdrawFileUploadDo.setFileName("批量提现_" + bo.getUserNo() + ".xls");
        userWithdrawFileUploadDo.setRecordCount(pick.getTotalNum());
        userWithdrawFileUploadDo.setSuccessCount(pick.getTotalNum());
        userWithdrawFileUploadDo.setTotalAmt(pick.getTotalAmt());
        userWithdrawFileUploadDo.setFileBatchNo(fileBatchNo);
        userWithdrawFileUploadDo.setUserNo(bo.getUserNo());
        userWithdrawFileUploadDo.setCreateBy(bo.getCreateBy());
        userWithdrawFileUploadDo.setDfsFileId(Long.valueOf("" + NumberDict.ZERO));
        userWithdrawFileUploadDo.setStatus(FileStateEnum.PROCESSING.getCode());
        userWithdrawFileUploadDo.setFailCount(BigDecimal.ZERO.intValue());
        userWithdrawFileUploadDo.setFileType(UploadFileEnum.SETTLE_FILE.getCode());
        userWithdrawFileUploadDo.setOrderType(UploadFileOrderEnum.ERROR_TYPE.getCode());
        userWithdrawFileUploadDo.setBatchNo("" + bo.getBatchNo());
        return userWithdrawFileUploadDo;
    }

    /**
     * 功能：生成系统订单商品信息
     *
     * @param orderDo 汇款订单
     * @return 商品信息
     */
    private WithdrawOrderItemDo toFiCbPayOrderItemDo(UserWithdrawOrderDo orderDo) {
        WithdrawOrderItemDo itemDo = new WithdrawOrderItemDo();
        BeanUtils.copyProperties(orderDo, itemDo);
        itemDo.setCommodityAmount(NumberDict.ONE);
        itemDo.setCommodityName("手续费返点");
        itemDo.setCommodityPrice(orderDo.getOrderAmt());
        return itemDo;
    }

    /**
     * 功能：根据提现批次号更新外部订单为可用（转账失败调用）
     *
     * @param withdrawId 提现文件批次号
     */
    public void updateExternalOrder(Long withdrawId) {
        log.error("用户提现失败，更新提现批次下对应外部订单状态为未使用，提现批次号：{}", withdrawId);
        List<UserWithdrawFileUploadDo> withdrawFileUploadDoList = userWithdrawCashManager.selectByBatch(String.valueOf(withdrawId));
        UserWithdrawFileUploadDo userWithdrawFileUploadDo = withdrawFileUploadDoList.get(0);
        List<UserWithdrawOrderQueryDo> userWithdrawOrderQueryDoList = userWithdrawOrderManager.queryOrderByFileBatchNo(
                userWithdrawFileUploadDo.getUserNo(), userWithdrawFileUploadDo.getFileBatchNo());
        List<String> orderIdList = Lists.newArrayList();
        for (UserWithdrawOrderQueryDo model : userWithdrawOrderQueryDoList){
            orderIdList.add(model.getUserTransId());
        }
        //更新已使用订单状态
        externalOrderMapper.batchUpdateExternalOrder(orderIdList, NumberDict.ZERO, userWithdrawFileUploadDo.getUserNo());
    }
}