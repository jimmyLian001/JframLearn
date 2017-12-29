package com.baofu.cbpayservice.biz.impl;

import com.baofu.cbpayservice.biz.CbPayOrderBiz;
import com.baofu.cbpayservice.biz.ProxyCustomsBiz;
import com.baofu.cbpayservice.biz.convert.CBPayOrderApiConvert;
import com.baofu.cbpayservice.biz.convert.ProxyCustomConvert;
import com.baofu.cbpayservice.biz.models.*;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.dal.mapper.*;
import com.baofu.cbpayservice.dal.models.*;
import com.baofu.cbpayservice.manager.FiCbPayOrderManager;
import com.baofu.cbpayservice.manager.OrderIdManager;
import com.baofu.cbpayservice.manager.ProxyCustomsManager;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 代理报关biz层服务
 * <p>
 * User: 不良人 Date:2017/1/4 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class ProxyCustomsBizImpl implements ProxyCustomsBiz {

    /**
     * 代理跨境结算服务
     */
    @Autowired
    private ProxyCustomsManager proxyCustomsManager;

    /**
     * 跨境订单服务
     */
    @Autowired
    private CbPayOrderBiz cbPayOrderBiz;

    /**
     * 跨境人民币订单信息
     */
    @Autowired
    private FiCbPayOrderManager fiCbPayOrderManager;

    /**
     * 创建宝付订单号
     */
    @Autowired
    private OrderIdManager orderIdManager;

    /**
     * 跨境订单信息操作
     */
    @Autowired
    private FiCbPayOrderMapper fiCbPayOrderMapper;

    /**
     * 跨境订单附加信息服务
     */
    @Autowired
    private FiCbPayOrderAdditionMapper fiCbPayOrderAdditionMapper;

    /**
     * 跨境订单商品信息服务
     */
    @Autowired
    private FiCbPayOrderItemMapper fiCbPayOrderItemMapper;

    /**
     * 跨境订单运单服务
     */
    @Autowired
    private FiCbpayOrderLogisticsMapper fiCbpayOrderLogisticsMapper;

    /**
     * 跨境订单机票服务
     */
    @Autowired
    private FiCbPayOrderTicketsMapper fiCbPayOrderTicketsMapper;

    /**
     * 跨境订单留学信息服务
     */
    @Autowired
    private FiCbPayOrderStudyAbroadMapper fiCbPayOrderStudyAbroadMapper;

    /**
     * 跨境订单酒店信息服务
     */
    @Autowired
    private FiCbPayOrderHotelMapper fiCbPayOrderHotelMapper;

    /**
     * 跨境订单旅游信息服务
     */
    @Autowired
    private FiCbPayOrderTourismMapper fiCbPayOrderTourismMapper;

    /**
     * 插入文件批次
     *
     * @param fiCbPayFileUploadBo 文件批次对象
     * @return 文件批次编号
     */
    @Override
    public Long insertFileUpload(FiCbpayFileUploadBo fiCbPayFileUploadBo) {

        return proxyCustomsManager.insert(ProxyCustomConvert.toFiCbPayFileUploadDo(fiCbPayFileUploadBo));
    }

    /**
     * 代理跨境订单上报
     *
     * @param customsBo 非宝付支付单跨境订单上报参数对象
     * @return 跨境订单编号
     */
    @Override
    @Transactional
    public Long apiProxyCustom(ProxyCustomsBo customsBo) {

        log.info("call 代理跨境订单上报服务biz层入参：{} ", customsBo);
        //订单ID
        Long orderId = orderIdManager.orderIdCreate();
        ProxyCustomsApiBo proxyCustomsApiBo = ProxyCustomConvert.toProxyCustomsMqBo(customsBo, orderId);
        //校验订单是否存在
        FiCbPayOrderDo fiCbPayOrderDo = fiCbPayOrderManager.queryByTransId(proxyCustomsApiBo.getMemberId(),
                proxyCustomsApiBo.getMemberTransId());
        if (fiCbPayOrderDo != null) {
            log.info("call 代理跨境订单上报，商户号：{},商户订单号：{},已存在。", proxyCustomsApiBo.getMemberId(),
                    proxyCustomsApiBo.getMemberTransId());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0083);

        }
        cbPayOrderBiz.addCbPayOrder(ProxyCustomConvert.cbPayOrderReqBo(proxyCustomsApiBo));
        log.info("call 代理跨境订单上报服务biz层返回订单ID：{}", orderId);

        return orderId;
    }

    /**
     * 批量更新文件批次状态
     */
    @Override
    public void batchUpdateFileStatus(CbPayBatchFileUpLoadBo cbPayFileUpLoadBo) {
        proxyCustomsManager.batchUpdateFileStatus(ProxyCustomConvert.batchUpdateStatusConvert(cbPayFileUpLoadBo));
    }

    /**
     * 查询文件批次包含币种数
     *
     * @param batchFileIdList 文件批次id集合
     */
    @Override
    public List<String> queryAmlCcy(List<Long> batchFileIdList) {
        return proxyCustomsManager.queryAmlCcy(batchFileIdList);
    }

    /**
     * 跨境支付订单上报V2
     *
     * @param proxyCustomsV2Bo 请求参数
     * @return 订单号
     */
    @Override
    @Transactional
    public Long apiProxyCustomV2(ProxyCustomsV2Bo proxyCustomsV2Bo) {

        log.info("call 跨境支付订单上报V2服务biz层入参：{} ", proxyCustomsV2Bo);
        Long orderId = orderIdManager.orderIdCreate();

        //校验订单是否存在
        FiCbPayOrderDo fiCbPayOrderDo = fiCbPayOrderManager.queryByTransId(proxyCustomsV2Bo.getMemberId(),
                proxyCustomsV2Bo.getMemberTransId());
        if (fiCbPayOrderDo != null) {
            log.info("call  跨境支付订单上报V2，商户号：{},商户订单号：{},已存在。", proxyCustomsV2Bo.getMemberId(),
                    proxyCustomsV2Bo.getMemberTransId());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0083);

        }

        //插入订单主信息
        fiCbPayOrderMapper.insert(CBPayOrderApiConvert.toFiCbPayOrderDo(proxyCustomsV2Bo, orderId));

        //插入订单附加信息
        fiCbPayOrderAdditionMapper.insert(CBPayOrderApiConvert.toFiCbPayOrderAdditionDo(proxyCustomsV2Bo, orderId));

        //插入订单额外信息
        additionalInformation(proxyCustomsV2Bo, orderId);
        log.info("call  跨境支付订单上报V2返回订单ID：{}", orderId);

        return orderId;
    }

    /**
     * 根据行业类型插入不同数据表
     *
     * @param proxyCustomsV2Bo 请求参数
     */
    private void additionalInformation(ProxyCustomsV2Bo proxyCustomsV2Bo, Long orderId) {

        switch (Integer.parseInt(proxyCustomsV2Bo.getIndustryType())) {

            //电商商品信息校验
            case 1:
                log.info("call 跨境支付订单上传，电商商品信息{},宝付订单号orderId{}", proxyCustomsV2Bo.getGoodsInfo(), orderId);

                //跨境订单上报运单信息
                CbpayGoodsInfoItemDto cbpayGoodsInfoItemDto = JsonUtil.toObject(proxyCustomsV2Bo.getGoodsInfo(),
                        CbpayGoodsInfoItemDto.class);
                fiCbpayOrderLogisticsMapper.insert(CBPayOrderApiConvert.toFiCbpayOrderLogisticsDo(cbpayGoodsInfoItemDto,
                        orderId));

                //跨境订单上报商品信息
                fiCbPayOrderItemMapper.batchInsert(CBPayOrderApiConvert.toFiCbPayOrderItemDoList(
                        cbpayGoodsInfoItemDto.getOrderItemList(), orderId));
                break;
            //机票信息校验
            case 2:
                log.info("call 跨境支付订单上传，机票信息{}", proxyCustomsV2Bo.getGoodsInfo());
                List<FiCbPayOrderTicketsDo> fiCbPayOrderTicketsDos = CBPayOrderApiConvert.
                        toFiCbPayOrderTicketsDo(proxyCustomsV2Bo, orderId);
                fiCbPayOrderTicketsMapper.batchInsert(fiCbPayOrderTicketsDos);
                break;
            //留学信息校验
            case 3:
                log.info("call 跨境支付订单上传，留学信息{}", proxyCustomsV2Bo.getGoodsInfo());
                List<FiCbPayOrderStudyAbroadDo> studyAbroadDos = CBPayOrderApiConvert.
                        toFiCbPayOrderStudyAbroadDo(proxyCustomsV2Bo, orderId);
                fiCbPayOrderStudyAbroadMapper.batchInsert(studyAbroadDos);
                break;
            //酒店信息校验
            case 4:
                log.info("call 跨境支付订单上传，酒店信息{}", proxyCustomsV2Bo.getGoodsInfo());
                List<FiCbPayOrderHotelDo> fiCbPayOrderHotelDos = CBPayOrderApiConvert.
                        toFiCbPayOrderHotelDo(proxyCustomsV2Bo, orderId);
                fiCbPayOrderHotelMapper.batchInsert(fiCbPayOrderHotelDos);
                break;
            //旅游信息校验
            case 5:
                log.info("call 跨境支付订单上传，旅游信息{}", proxyCustomsV2Bo.getGoodsInfo());
                List<FiCbPayOrderTourismDo> fiCbPayOrderTourismDos = CBPayOrderApiConvert
                        .toFiCbPayOrderTourismDo(proxyCustomsV2Bo, orderId);
                fiCbPayOrderTourismMapper.batchInsert(fiCbPayOrderTourismDos);
                break;

            default:
                throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00161);
        }
    }
}
