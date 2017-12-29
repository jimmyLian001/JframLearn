package com.baofu.cbpayservice.biz.impl;

import com.baofu.cbpayservice.biz.CbPayRemittanceFileProcessBiz;
import com.baofu.cbpayservice.biz.convert.ProxyCustomConvert;
import com.baofu.cbpayservice.biz.models.*;
import com.baofu.cbpayservice.biz.validation.ValidationUtil;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.dal.mapper.*;
import com.baofu.cbpayservice.dal.models.*;
import com.baofu.cbpayservice.manager.FiCbPayOrderManager;
import com.baofu.cbpayservice.manager.OrderIdManager;
import com.baofu.cbpayservice.manager.RedisManager;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 功能：汇款文件上传具体处理
 * User: feng_jiang Date:2017/7/7
 */
@Slf4j
@Service
public class CbPayRemittanceFileProcessBizImpl implements CbPayRemittanceFileProcessBiz {

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
     * 跨境订单商品信息服务（电商）
     */
    @Autowired
    private FiCbPayOrderItemMapper fiCbPayOrderItemMapper;

    /**
     * 跨境订单附加信息服务(机票)
     */
    @Autowired
    private FiCbPayOrderTicketsMapper fiCbPayOrderTicketsMapper;

    /**
     * 跨境订单附加信息服务(留学)
     */
    @Autowired
    private FiCbPayOrderStudyAbroadMapper fiCbPayOrderStudyAbroadMapper;
    /**
     * 跨境订单附加信息服务(酒店)
     */
    @Autowired
    private FiCbPayOrderHotelMapper fiCbPayOrderHotelMapper;
    /**
     * 跨境订单附加信息服务(旅游)
     */
    @Autowired
    private FiCbPayOrderTourismMapper fiCbPayOrderTourismMapper;
    /**
     * 跨境订单运单服务
     */
    @Autowired
    private FiCbpayOrderLogisticsMapper fiCbpayOrderLogisticsMapper;
    /**
     * 跨境订单附加信息服务
     */
    @Autowired
    private FiCbPayOrderAdditionMapper fiCbPayOrderAdditionMapper;

    /**
     * 缓存服务
     */
    @Autowired
    private RedisManager redisManager;

    @Override
    public <T> RemitFileCheckResultBo dataParseCheck(ProxyCustomsMqBo proxyCustomsMqBo, List<Object[]> list, String careerType) {
        switch (Integer.parseInt(careerType)) {
            case 1://电商
                return this.proxyCustomsParseCheck(proxyCustomsMqBo, list);
            case 2://机票
                return this.airRemitParseCheck(proxyCustomsMqBo, list);
            case 3://留学
                return this.studyAbroadRemitParseCheck(proxyCustomsMqBo, list);
            case 4://酒店
                return this.hotelRemitParseCheck(proxyCustomsMqBo, list);
            case 5://旅游
                return this.tourismRemitParseCheck(proxyCustomsMqBo, list);
        }
        return new RemitFileCheckResultBo();
    }

    @Override
    public int batchInsertData(RemitFileCheckResultBo remitFileCheckResultBo, ProxyCustomsMqBo proxyCustomsMqBo) {
        switch (Integer.parseInt(remitFileCheckResultBo.getCareerType())) {
            case 1://电商
                return this.batchInsertLogisticsData(remitFileCheckResultBo, proxyCustomsMqBo);
            case 2://机票
                return this.batchInsertAirRemitData(remitFileCheckResultBo, proxyCustomsMqBo);
            case 3://留学
                return this.batchInsertStudyAbroadRemitData(remitFileCheckResultBo, proxyCustomsMqBo);
            case 4://酒店
                return this.batchInsertHotelRemitData(remitFileCheckResultBo, proxyCustomsMqBo);
            case 5://旅游
                return this.batchInsertTourismRemitData(remitFileCheckResultBo, proxyCustomsMqBo);
        }
        return 0;
    }

    /**
     * 功能：航旅批量保存数据
     *
     * @param remitFileCheckResultBo 汇款文件校验结果
     * @param proxyCustomsMqBo       mq内容
     */
    private int batchInsertAirRemitData(RemitFileCheckResultBo remitFileCheckResultBo, ProxyCustomsMqBo proxyCustomsMqBo) {
        Long startTime = System.currentTimeMillis();
        List<AirRemitValidateBo> airRemitValidateBoList = (List<AirRemitValidateBo>) remitFileCheckResultBo.getData();
        int totalCount = airRemitValidateBoList.size();    //excel文件内容总条数
        //跨境订单集合
        List<FiCbPayOrderDo> fiCbPayOrderDoList = Lists.newArrayList();
        //跨境订单附加信息
        List<FiCbPayOrderAdditionDo> fiCbPayOrderAdditionDoList = Lists.newArrayList();
        //航旅信息集合
        List<FiCbPayOrderTicketsDo> fiCbPayOrderTicketsDoList = Lists.newArrayList();
        for (int i = 0; i < totalCount; i++) {
            Long orderId = orderIdManager.orderIdCreate();
            AirRemitValidateBo airRemitValidateBo = airRemitValidateBoList.get(i);
            //跨境订单
            FiCbPayOrderDo fiCbPayOrderDo = ProxyCustomConvert.trans2FiCbPayOrderDo(airRemitValidateBo, proxyCustomsMqBo, orderId, remitFileCheckResultBo.getCareerType());
            //跨境订单附加信息
            FiCbPayOrderAdditionDo fiCbPayOrderAdditionDo = ProxyCustomConvert.orderAdditionDoConvert(airRemitValidateBo, orderId);
            //跨境订单商品信息
            List<FiCbPayOrderTicketsDo> cbPayOrderTicketsDoList = ProxyCustomConvert.paramConvert(airRemitValidateBo, orderId);

            fiCbPayOrderDoList.add(fiCbPayOrderDo);
            fiCbPayOrderAdditionDoList.add(fiCbPayOrderAdditionDo);
            fiCbPayOrderTicketsDoList.addAll(cbPayOrderTicketsDoList);
            //订单信息和订单附加信息每1000条添加到一个List中
            if (fiCbPayOrderDoList.size() % Constants.EXCEL_MAX_COUNT == 0 || i == totalCount - 1) {
                fiCbPayOrderMapper.batchInsert(fiCbPayOrderDoList);
                fiCbPayOrderAdditionMapper.batchInsert(fiCbPayOrderAdditionDoList);
                fiCbPayOrderDoList = Lists.newArrayList();
                fiCbPayOrderAdditionDoList = Lists.newArrayList();
            }
            //航旅信息每1000条添加到一个List中
            if (fiCbPayOrderTicketsDoList.size() % Constants.EXCEL_MAX_COUNT == 0 || fiCbPayOrderTicketsDoList.size() > Constants.EXCEL_MAX_COUNT || i == totalCount - 1) {
                fiCbPayOrderTicketsMapper.batchInsert(fiCbPayOrderTicketsDoList);
                fiCbPayOrderTicketsDoList = Lists.newArrayList();
            }
            if (i == totalCount - 1) {//释放内存
                airRemitValidateBoList = Lists.newArrayList();
            }
            redisManager.insertObject((double) (i + 1) / totalCount / 2 + 0.5, proxyCustomsMqBo.getFileBatchNo().toString());
        }
        log.info("call 航旅跨境订单总条数{}, 写入数据库总时间：{}", totalCount, System.currentTimeMillis() - startTime);
        return totalCount;
    }

    /**
     * 功能：电商批量保存数据
     *
     * @param remitFileCheckResultBo 汇款文件校验结果
     * @param proxyCustomsMqBo       mq内容
     */
    private int batchInsertLogisticsData(RemitFileCheckResultBo remitFileCheckResultBo, ProxyCustomsMqBo proxyCustomsMqBo) {
        Long startTime = System.currentTimeMillis();
        List<ProxyCustomsValidateBo> proxyCustomsValidateBo = (List<ProxyCustomsValidateBo>) remitFileCheckResultBo.getData();
        int totalCount = proxyCustomsValidateBo.size();//excel文件内容总条数
        //跨境订单集合
        List<FiCbPayOrderDo> fiCbPayOrderDoList = Lists.newArrayList();
        //跨境订单商品信息集合
        List<FiCbPayOrderItemDo> fiCbPayOrderItemDoList = Lists.newArrayList();
        //跨境订单附加信息
        List<FiCbPayOrderAdditionDo> fiCbPayOrderAdditionDoList = Lists.newArrayList();
        //跨境订单运单信息集合
        List<FiCbpayOrderLogisticsDo> fiCbPayOrderLogisticsDoList = Lists.newArrayList();

        for (int i = 0; i < totalCount; i++) {
            Long orderId = orderIdManager.orderIdCreate();
            ProxyCustomsValidateBo proxyCustomsBo = proxyCustomsValidateBo.get(i);
            //跨境订单
            FiCbPayOrderDo fiCbPayOrderDo = ProxyCustomConvert.trans2FiCbPayOrderDo(proxyCustomsBo, proxyCustomsMqBo, orderId, remitFileCheckResultBo.getCareerType());
            //跨境订单附加信息
            FiCbPayOrderAdditionDo fiCbPayOrderAdditionDo = ProxyCustomConvert.orderAdditionDoConvert(proxyCustomsBo, orderId);
            //跨境订单运单信息
            FiCbpayOrderLogisticsDo fiCbpayOrderLogisticsDo = ProxyCustomConvert.toFiCbpayOrderLogisticsDo(proxyCustomsBo, orderId);
            //跨境订单商品信息
            List<FiCbPayOrderItemDo> cbPayOrderItemDoList = ProxyCustomConvert.paramConvert(proxyCustomsBo, orderId);

            fiCbPayOrderDoList.add(fiCbPayOrderDo);
            fiCbPayOrderAdditionDoList.add(fiCbPayOrderAdditionDo);
            fiCbPayOrderItemDoList.addAll(cbPayOrderItemDoList);
            if (fiCbpayOrderLogisticsDo != null) {
                fiCbPayOrderLogisticsDoList.add(fiCbpayOrderLogisticsDo);
            }
            //订单信息和订单附加信息每1000条添加到一个List中
            if (fiCbPayOrderDoList.size() % Constants.EXCEL_MAX_COUNT == 0 || i == totalCount - 1) {
                fiCbPayOrderMapper.batchInsert(fiCbPayOrderDoList);
                fiCbPayOrderAdditionMapper.batchInsert(fiCbPayOrderAdditionDoList);
                fiCbPayOrderDoList = Lists.newArrayList();
                fiCbPayOrderAdditionDoList = Lists.newArrayList();
            }
            //商品信息每1000条添加到一个List中
            if (fiCbPayOrderItemDoList.size() % Constants.EXCEL_MAX_COUNT == 0 || cbPayOrderItemDoList.size() > Constants.EXCEL_MAX_COUNT || i == totalCount - 1) {
                fiCbPayOrderItemMapper.batchInsert(fiCbPayOrderItemDoList);
                fiCbPayOrderItemDoList = Lists.newArrayList();
            }
            //订单信息运单信息每1000条添加到一个List中
            if (fiCbPayOrderLogisticsDoList.size() % Constants.EXCEL_MAX_COUNT == 0 || i == totalCount - 1) {
                if (!fiCbPayOrderLogisticsDoList.isEmpty()) {
                    fiCbpayOrderLogisticsMapper.batchInsert(fiCbPayOrderLogisticsDoList);
                    fiCbPayOrderLogisticsDoList = Lists.newArrayList();
                }
            }
            if (i == totalCount - 1) { //释放内存
                proxyCustomsValidateBo = Lists.newArrayList();
            }
            redisManager.insertObject((double) (i + 1) / totalCount / 2 + 0.5, proxyCustomsMqBo.getFileBatchNo().toString());
        }
        log.info("call 电商跨境订单总条数{}, 写入数据库总时间：{}", totalCount, System.currentTimeMillis() - startTime);
        return totalCount;
    }

    /**
     * 功能：留学批量保存数据
     *
     * @param remitFileCheckResultBo 汇款文件校验结果
     * @param proxyCustomsMqBo       mq内容
     */
    private int batchInsertStudyAbroadRemitData(RemitFileCheckResultBo remitFileCheckResultBo, ProxyCustomsMqBo proxyCustomsMqBo) {
        Long startTime = System.currentTimeMillis();
        List<CbPayOrderStudyAbroadValidateBo> remitValidateBoList = (List<CbPayOrderStudyAbroadValidateBo>) remitFileCheckResultBo.getData();
        int totalCount = remitValidateBoList.size();    //excel文件内容总条数
        //跨境订单集合
        List<FiCbPayOrderDo> fiCbPayOrderDoList = Lists.newArrayList();
        //跨境订单附加信息
        List<FiCbPayOrderAdditionDo> fiCbPayOrderAdditionDoList = Lists.newArrayList();
        //留学信息集合
        List<FiCbPayOrderStudyAbroadDo> fiCbPayOrderStudyAbroadDoList = Lists.newArrayList();
        for (int i = 0; i < totalCount; i++) {
            Long orderId = orderIdManager.orderIdCreate();
            CbPayOrderStudyAbroadValidateBo remitValidateBo = remitValidateBoList.get(i);
            //跨境订单
            FiCbPayOrderDo fiCbPayOrderDo = ProxyCustomConvert.trans2FiCbPayOrderDo(remitValidateBo, proxyCustomsMqBo, orderId, remitFileCheckResultBo.getCareerType());
            //跨境订单附加信息
            FiCbPayOrderAdditionDo fiCbPayOrderAdditionDo = ProxyCustomConvert.orderAdditionDoConvert(remitValidateBo, orderId);
            //跨境订单商品信息
            List<FiCbPayOrderStudyAbroadDo> cbPayOrderStudyAbroadDoList = ProxyCustomConvert.paramConvert(remitValidateBo, orderId);
            fiCbPayOrderDoList.add(fiCbPayOrderDo);
            fiCbPayOrderAdditionDoList.add(fiCbPayOrderAdditionDo);
            fiCbPayOrderStudyAbroadDoList.addAll(cbPayOrderStudyAbroadDoList);
            //订单信息和订单附加信息每1000条添加到一个List中
            if (fiCbPayOrderDoList.size() % Constants.EXCEL_MAX_COUNT == 0 || i == totalCount - 1) {
                fiCbPayOrderMapper.batchInsert(fiCbPayOrderDoList);
                fiCbPayOrderAdditionMapper.batchInsert(fiCbPayOrderAdditionDoList);
                fiCbPayOrderDoList = Lists.newArrayList();
                fiCbPayOrderAdditionDoList = Lists.newArrayList();
            }
            //航旅信息每1000条添加到一个List中
            if (fiCbPayOrderStudyAbroadDoList.size() % Constants.EXCEL_MAX_COUNT == 0 || fiCbPayOrderStudyAbroadDoList.size() > Constants.EXCEL_MAX_COUNT || i == totalCount - 1) {
                fiCbPayOrderStudyAbroadMapper.batchInsert(fiCbPayOrderStudyAbroadDoList);
                fiCbPayOrderStudyAbroadDoList = Lists.newArrayList();
            }
            if (i == totalCount - 1) {//释放内存
                remitValidateBoList = Lists.newArrayList();
            }
            redisManager.insertObject((double) (i + 1) / totalCount / 2 + 0.5, proxyCustomsMqBo.getFileBatchNo().toString());
        }
        log.info("call 留学跨境订单总条数{}, 写入数据库总时间：{}", totalCount, System.currentTimeMillis() - startTime);
        return totalCount;
    }

    /**
     * 功能：酒店批量保存数据
     *
     * @param remitFileCheckResultBo 汇款文件校验结果
     * @param proxyCustomsMqBo       mq内容
     */
    private int batchInsertHotelRemitData(RemitFileCheckResultBo remitFileCheckResultBo, ProxyCustomsMqBo proxyCustomsMqBo) {
        Long startTime = System.currentTimeMillis();
        List<CbPayOrderHotelValidateBo> remitValidateBoList = (List<CbPayOrderHotelValidateBo>) remitFileCheckResultBo.getData();
        int totalCount = remitValidateBoList.size();    //excel文件内容总条数
        //跨境订单集合
        List<FiCbPayOrderDo> fiCbPayOrderDoList = Lists.newArrayList();
        //跨境订单附加信息
        List<FiCbPayOrderAdditionDo> fiCbPayOrderAdditionDoList = Lists.newArrayList();
        //留学信息集合
        List<FiCbPayOrderHotelDo> fiCbPayOrderHotelDoList = Lists.newArrayList();
        for (int i = 0; i < totalCount; i++) {
            Long orderId = orderIdManager.orderIdCreate();
            CbPayOrderHotelValidateBo remitValidateBo = remitValidateBoList.get(i);
            //跨境订单
            FiCbPayOrderDo fiCbPayOrderDo = ProxyCustomConvert.trans2FiCbPayOrderDo(remitValidateBo, proxyCustomsMqBo, orderId, remitFileCheckResultBo.getCareerType());
            //跨境订单附加信息
            FiCbPayOrderAdditionDo fiCbPayOrderAdditionDo = ProxyCustomConvert.orderAdditionDoConvert(remitValidateBo, orderId);
            //跨境订单商品信息
            List<FiCbPayOrderHotelDo> cbPayOrderStudyAbroadDoList = ProxyCustomConvert.paramConvert(remitValidateBo, orderId);
            fiCbPayOrderDoList.add(fiCbPayOrderDo);
            fiCbPayOrderAdditionDoList.add(fiCbPayOrderAdditionDo);
            fiCbPayOrderHotelDoList.addAll(cbPayOrderStudyAbroadDoList);
            //订单信息和订单附加信息每1000条添加到一个List中
            if (fiCbPayOrderDoList.size() % Constants.EXCEL_MAX_COUNT == 0 || i == totalCount - 1) {
                fiCbPayOrderMapper.batchInsert(fiCbPayOrderDoList);
                fiCbPayOrderAdditionMapper.batchInsert(fiCbPayOrderAdditionDoList);
                fiCbPayOrderDoList = Lists.newArrayList();
                fiCbPayOrderAdditionDoList = Lists.newArrayList();
            }
            //航旅信息每1000条添加到一个List中
            if (fiCbPayOrderHotelDoList.size() % Constants.EXCEL_MAX_COUNT == 0 || fiCbPayOrderHotelDoList.size() > Constants.EXCEL_MAX_COUNT || i == totalCount - 1) {
                fiCbPayOrderHotelMapper.batchInsert(fiCbPayOrderHotelDoList);
                fiCbPayOrderHotelDoList = Lists.newArrayList();
            }
            if (i == totalCount - 1) {//释放内存
                remitValidateBoList = Lists.newArrayList();
            }
            redisManager.insertObject((double) (i + 1) / totalCount / 2 + 0.5, proxyCustomsMqBo.getFileBatchNo().toString());
        }
        log.info("call 酒店跨境订单总条数{}, 写入数据库总时间：{}", totalCount, System.currentTimeMillis() - startTime);
        return totalCount;
    }

    /**
     * 功能：旅游批量保存数据
     *
     * @param remitFileCheckResultBo 汇款文件校验结果
     * @param proxyCustomsMqBo       mq内容
     */
    private int batchInsertTourismRemitData(RemitFileCheckResultBo remitFileCheckResultBo, ProxyCustomsMqBo proxyCustomsMqBo) {
        Long startTime = System.currentTimeMillis();
        List<CbPayOrderTourismValidateBo> remitValidateBoList = (List<CbPayOrderTourismValidateBo>) remitFileCheckResultBo.getData();
        int totalCount = remitValidateBoList.size();    //excel文件内容总条数
        //跨境订单集合
        List<FiCbPayOrderDo> fiCbPayOrderDoList = Lists.newArrayList();
        //跨境订单附加信息
        List<FiCbPayOrderAdditionDo> fiCbPayOrderAdditionDoList = Lists.newArrayList();
        //留学信息集合
        List<FiCbPayOrderTourismDo> fiCbPayOrderTourismDoList = Lists.newArrayList();
        for (int i = 0; i < totalCount; i++) {
            Long orderId = orderIdManager.orderIdCreate();
            CbPayOrderTourismValidateBo remitValidateBo = remitValidateBoList.get(i);
            //跨境订单
            FiCbPayOrderDo fiCbPayOrderDo = ProxyCustomConvert.trans2FiCbPayOrderDo(remitValidateBo, proxyCustomsMqBo, orderId, remitFileCheckResultBo.getCareerType());
            //跨境订单附加信息
            FiCbPayOrderAdditionDo fiCbPayOrderAdditionDo = ProxyCustomConvert.orderAdditionDoConvert(remitValidateBo, orderId);
            //跨境订单商品信息
            List<FiCbPayOrderTourismDo> cbPayOrderStudyAbroadDoList = ProxyCustomConvert.paramConvert(remitValidateBo, orderId);
            fiCbPayOrderDoList.add(fiCbPayOrderDo);
            fiCbPayOrderAdditionDoList.add(fiCbPayOrderAdditionDo);
            fiCbPayOrderTourismDoList.addAll(cbPayOrderStudyAbroadDoList);
            //订单信息和订单附加信息每1000条添加到一个List中
            if (fiCbPayOrderDoList.size() % Constants.EXCEL_MAX_COUNT == 0 || i == totalCount - 1) {
                fiCbPayOrderMapper.batchInsert(fiCbPayOrderDoList);
                fiCbPayOrderAdditionMapper.batchInsert(fiCbPayOrderAdditionDoList);
                fiCbPayOrderDoList = Lists.newArrayList();
                fiCbPayOrderAdditionDoList = Lists.newArrayList();
            }
            //航旅信息每1000条添加到一个List中
            if (fiCbPayOrderTourismDoList.size() % Constants.EXCEL_MAX_COUNT == 0 || fiCbPayOrderTourismDoList.size() > Constants.EXCEL_MAX_COUNT || i == totalCount - 1) {
                fiCbPayOrderTourismMapper.batchInsert(fiCbPayOrderTourismDoList);
                fiCbPayOrderTourismDoList = Lists.newArrayList();
            }
            if (i == totalCount - 1) {//释放内存
                remitValidateBoList = Lists.newArrayList();
            }
            redisManager.insertObject((double) (i + 1) / totalCount / 2 + 0.5, proxyCustomsMqBo.getFileBatchNo().toString());
        }
        log.info("call 旅游跨境订单总条数{}, 写入数据库总时间：{}", totalCount, System.currentTimeMillis() - startTime);
        return totalCount;
    }

    /**
     * 功能：电商excel 数据解析与校验
     *
     * @param proxyCustomsMqBo 代理对象
     * @param list             excel内容
     * @return 校验结果
     */
    private RemitFileCheckResultBo<List<ProxyCustomsValidateBo>> proxyCustomsParseCheck(ProxyCustomsMqBo proxyCustomsMqBo, List<Object[]> list) {
        long startTime = System.currentTimeMillis();
        RemitFileCheckResultBo retBo = new RemitFileCheckResultBo();
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<String> memberTransIds = Lists.newArrayList();
        Map<String, Integer> memberTransMap = Maps.newHashMap();
        Map<Integer, StringBuilder> errorMsgMap = Maps.newHashMap();
        List dataList = new ArrayList<>();

        int listSize = list.size();
        String memberTransId;
        ProxyCustomsValidateBo proxyCustomsValidateBo;
        for (int i = Constants.NO_CONTENT_LINE; i < listSize; i++) {
            proxyCustomsValidateBo = ProxyCustomConvert.excelServiceradeCustomBos(list.get(i));
            memberTransId = proxyCustomsValidateBo.getMemberTransId();                             //商户请求流水号
            totalAmount = this.getTotalAmount(totalAmount, proxyCustomsValidateBo.getOrderMoney());
            //校验基本信息
            StringBuilder errorMsg = ValidationUtil.valid(retBo.getOrderType(), i, proxyCustomsValidateBo);
            if (errorMsg.length() == 0) {
                dataList.add(proxyCustomsValidateBo);
            } else {
                errorMsgMap.put(i, errorMsg);
            }
            this.checkMemberTransId(proxyCustomsMqBo, memberTransIds, memberTransMap, errorMsgMap, listSize, memberTransId, i);
            redisManager.insertObject((double) (i + 1) / (listSize - Constants.NO_CONTENT_LINE) / 2, proxyCustomsMqBo.getFileBatchNo().toString());
        }
        this.dataAssignment(retBo, totalAmount, errorMsgMap, dataList, listSize);
        log.info("call 电商代理报关服务biz层 校验结束,处理总时长：{}", System.currentTimeMillis() - startTime);
        return retBo;
    }

    /**
     * 功能：航旅excel 数据解析与校验
     *
     * @param proxyCustomsMqBo 代理对象
     * @param list             excel内容
     * @return 校验结果
     */
    private RemitFileCheckResultBo<List<AirRemitValidateBo>> airRemitParseCheck(ProxyCustomsMqBo proxyCustomsMqBo, List<Object[]> list) {
        long startTime = System.currentTimeMillis();
        RemitFileCheckResultBo retBo = new RemitFileCheckResultBo();
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<String> memberTransIds = Lists.newArrayList();
        Map<String, Integer> memberTransMap = Maps.newHashMap();
        Map<Integer, StringBuilder> errorMsgMap = Maps.newHashMap();
        List dataList = new ArrayList<>();

        int listSize = list.size();
        String memberTransId;
        AirRemitValidateBo airRemitValidateBo;
        for (int i = Constants.NO_CONTENT_LINE; i < listSize; i++) {
            airRemitValidateBo = ProxyCustomConvert.toAirRemitValidateBo(list.get(i));
            totalAmount = this.getTotalAmount(totalAmount, airRemitValidateBo.getOrderMoney());
            memberTransId = airRemitValidateBo.getMemberTransId();
            //校验基本信息
            StringBuilder errorMsg = ValidationUtil.airRemitValid(i, airRemitValidateBo);
            if (errorMsg.length() == 0) {
                dataList.add(airRemitValidateBo);
            } else {
                errorMsgMap.put(i, errorMsg);
            }
            this.checkMemberTransId(proxyCustomsMqBo, memberTransIds, memberTransMap, errorMsgMap, listSize, memberTransId, i);
            redisManager.insertObject((double) (i + 1) / (listSize - Constants.NO_CONTENT_LINE) / 2, proxyCustomsMqBo.getFileBatchNo().toString());
        }
        this.dataAssignment(retBo, totalAmount, errorMsgMap, dataList, listSize);
        log.info("call 航旅代理报关服务biz层 校验结束,处理总时长：{}", System.currentTimeMillis() - startTime);
        return retBo;
    }

    /**
     * 功能：酒店excel 数据解析与校验
     *
     * @param proxyCustomsMqBo 代理对象
     * @param list             excel内容
     * @return 校验结果
     */
    private RemitFileCheckResultBo<List<CbPayOrderHotelValidateBo>> hotelRemitParseCheck(ProxyCustomsMqBo proxyCustomsMqBo, List<Object[]> list) {
        long startTime = System.currentTimeMillis();
        RemitFileCheckResultBo retBo = new RemitFileCheckResultBo();
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<String> memberTransIds = Lists.newArrayList();
        Map<String, Integer> memberTransMap = Maps.newHashMap();
        Map<Integer, StringBuilder> errorMsgMap = Maps.newHashMap();
        List dataList = new ArrayList<>();

        int listSize = list.size();
        String memberTransId;
        CbPayOrderHotelValidateBo hotelValidateBo;
        for (int i = Constants.NO_CONTENT_LINE; i < listSize; i++) {
            hotelValidateBo = ProxyCustomConvert.toCbPayOrderHotelValidateBo(list.get(i));
            totalAmount = this.getTotalAmount(totalAmount, hotelValidateBo.getOrderMoney());
            memberTransId = hotelValidateBo.getMemberTransId();
            //校验基本信息
            StringBuilder errorMsg = ValidationUtil.hotelRemitValid(i, hotelValidateBo);
            if (errorMsg.length() == 0) {
                dataList.add(hotelValidateBo);
            } else {
                errorMsgMap.put(i, errorMsg);
            }
            this.checkMemberTransId(proxyCustomsMqBo, memberTransIds, memberTransMap, errorMsgMap, listSize, memberTransId, i);
            redisManager.insertObject((double) (i + 1) / (listSize - Constants.NO_CONTENT_LINE) / 2, proxyCustomsMqBo.getFileBatchNo().toString());
        }
        this.dataAssignment(retBo, totalAmount, errorMsgMap, dataList, listSize);
        log.info("call 酒店代理报关服务biz层 校验结束,处理总时长：{}", System.currentTimeMillis() - startTime);
        return retBo;
    }

    /**
     * 功能：留学excel 数据解析与校验
     *
     * @param proxyCustomsMqBo 代理对象
     * @param list             excel内容
     * @return 校验结果
     */
    private RemitFileCheckResultBo<List<CbPayOrderStudyAbroadValidateBo>> studyAbroadRemitParseCheck(ProxyCustomsMqBo proxyCustomsMqBo, List<Object[]> list) {
        long startTime = System.currentTimeMillis();
        RemitFileCheckResultBo retBo = new RemitFileCheckResultBo();
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<String> memberTransIds = Lists.newArrayList();
        Map<String, Integer> memberTransMap = Maps.newHashMap();
        Map<Integer, StringBuilder> errorMsgMap = Maps.newHashMap();
        List dataList = new ArrayList<>();

        int listSize = list.size();
        String memberTransId;
        CbPayOrderStudyAbroadValidateBo studyAbroadValidateBo;
        for (int i = Constants.NO_CONTENT_LINE; i < listSize; i++) {
            studyAbroadValidateBo = ProxyCustomConvert.toCbPayOrderStudyAbroadValidateBo(list.get(i));
            totalAmount = this.getTotalAmount(totalAmount, studyAbroadValidateBo.getOrderMoney());
            memberTransId = studyAbroadValidateBo.getMemberTransId();
            //校验基本信息
            StringBuilder errorMsg = ValidationUtil.studyAbroadRemitValid(i, studyAbroadValidateBo);
            if (errorMsg.length() == 0) {
                dataList.add(studyAbroadValidateBo);
            } else {
                errorMsgMap.put(i, errorMsg);
            }
            this.checkMemberTransId(proxyCustomsMqBo, memberTransIds, memberTransMap, errorMsgMap, listSize, memberTransId, i);
            redisManager.insertObject((double) (i + 1) / (listSize - Constants.NO_CONTENT_LINE) / 2, proxyCustomsMqBo.getFileBatchNo().toString());
        }
        this.dataAssignment(retBo, totalAmount, errorMsgMap, dataList, listSize);
        log.info("call 留学代理报关服务biz层 校验结束,处理总时长：{}", System.currentTimeMillis() - startTime);
        return retBo;
    }

    /**
     * 功能：旅游excel 数据解析与校验
     *
     * @param proxyCustomsMqBo 代理对象
     * @param list             excel内容
     * @return 校验结果
     */
    private RemitFileCheckResultBo<List<CbPayOrderTourismValidateBo>> tourismRemitParseCheck(ProxyCustomsMqBo proxyCustomsMqBo, List<Object[]> list) {
        {
            long startTime = System.currentTimeMillis();
            RemitFileCheckResultBo retBo = new RemitFileCheckResultBo();
            BigDecimal totalAmount = BigDecimal.ZERO;
            List<String> memberTransIds = Lists.newArrayList();
            Map<String, Integer> memberTransMap = Maps.newHashMap();
            Map<Integer, StringBuilder> errorMsgMap = Maps.newHashMap();
            List dataList = new ArrayList<>();

            int listSize = list.size();
            String memberTransId;
            CbPayOrderTourismValidateBo tourismValidateBo;
            for (int i = Constants.NO_CONTENT_LINE; i < listSize; i++) {
                tourismValidateBo = ProxyCustomConvert.toCbPayOrderTourismValidateBo(list.get(i));
                totalAmount = this.getTotalAmount(totalAmount, tourismValidateBo.getOrderMoney());
                memberTransId = tourismValidateBo.getMemberTransId();
                //校验基本信息
                StringBuilder errorMsg = ValidationUtil.tourismRemitValid(i, tourismValidateBo);
                if (errorMsg.length() == 0) {
                    dataList.add(tourismValidateBo);
                } else {
                    errorMsgMap.put(i, errorMsg);
                }
                this.checkMemberTransId(proxyCustomsMqBo, memberTransIds, memberTransMap, errorMsgMap, listSize, memberTransId, i);
                redisManager.insertObject((double) (i + 1) / (listSize - Constants.NO_CONTENT_LINE) / 2, proxyCustomsMqBo.getFileBatchNo().toString());
            }
            this.dataAssignment(retBo, totalAmount, errorMsgMap, dataList, listSize);
            log.info("call 旅游代理报关服务biz层 校验结束,处理总时长：{}", System.currentTimeMillis() - startTime);
            return retBo;
        }
    }

    /**
     * 校验商户流水号
     *
     * @param proxyCustomsMqBo
     * @param memberTransIds
     * @param memberTransMap
     * @param errorMsgMap
     * @param listSize
     * @param memberTransId
     * @param i
     */
    private void checkMemberTransId(ProxyCustomsMqBo proxyCustomsMqBo, List<String> memberTransIds,
                                    Map<String, Integer> memberTransMap, Map<Integer, StringBuilder> errorMsgMap,
                                    int listSize, String memberTransId, int i) {
        Integer rowNum = memberTransMap.get(memberTransId);
        if (rowNum != null && (errorMsgMap.get(i) == null || errorMsgMap.get(i).length() == 0)) {
            StringBuilder stringBuffer = new StringBuilder();
            stringBuffer.setLength(0);
            stringBuffer.append("第");
            stringBuffer.append(i + 1);
            stringBuffer.append("行：该行和第");
            stringBuffer.append(memberTransMap.get(memberTransId) + 1).
                    append("行商户流水号重复,流水号：").append(memberTransId);
            stringBuffer.append("\r\n");
            errorMsgMap.put(i, stringBuffer);
        } else if (rowNum != null) {
            int start = errorMsgMap.get(i).indexOf("\r");
            errorMsgMap.get(i).insert(start, "|该行和第" + (memberTransMap.get(memberTransId) + 1) +
                    "行商户流水号重复,流水号：").append(memberTransId);
        }
        //校验订单号是否存在
        memberTransMap.put(memberTransId, i);
        memberTransIds.add(memberTransId);
        if (memberTransIds.size() == 200 || i == listSize - 1) {
            checkMemberTransId(proxyCustomsMqBo, memberTransIds, errorMsgMap, memberTransMap);
            memberTransIds.clear();
        }
    }

    /**
     * 功能：部分公共赋值操作
     *
     * @return void
     */
    private void dataAssignment(RemitFileCheckResultBo retBo, BigDecimal totalAmount, Map<Integer, StringBuilder> errorMsgMap, List dataList, int listSize) {
        StringBuilder errorBuffer = new StringBuilder();
        for (Integer i : errorMsgMap.keySet()) {
            errorBuffer.append(errorMsgMap.get(i));
        }
        retBo.setErrorMsg(errorBuffer);
        retBo.setTotalAmount(totalAmount);
        retBo.setData(dataList);
        retBo.setErrorCount(errorMsgMap.size());
        retBo.setSuccessCount(listSize - errorMsgMap.size() - Constants.NO_CONTENT_LINE);
    }

    /**
     * 功能：统计总金额
     *
     * @param totalAmount
     * @param paymentMoney
     * @return
     */
    private BigDecimal getTotalAmount(BigDecimal totalAmount, String paymentMoney) {
        if (StringUtils.isNotBlank(paymentMoney) && ProxyCustomConvert.isNumber(paymentMoney) && paymentMoney.length() < 22) {
            totalAmount = totalAmount.add(new BigDecimal(paymentMoney));
        }
        return totalAmount;
    }

    /**
     * 校验订单号是否在数据库存在
     *
     * @param proxyCustomsMqBo 代理对象
     * @param memberTransIds   需要校验的商户订单号
     * @param errorMsgMap      错误信息集合
     * @param memberTransMap   商户订单集合
     */
    protected void checkMemberTransId(ProxyCustomsMqBo proxyCustomsMqBo, List<String> memberTransIds,
                                      Map<Integer, StringBuilder> errorMsgMap, Map<String, Integer> memberTransMap) {

        List<String> merchantTrans = fiCbPayOrderManager.selectMerchantTrans(proxyCustomsMqBo.getMemberId(), memberTransIds);

        if (merchantTrans != null && !merchantTrans.isEmpty()) {
            for (String memberTransId : merchantTrans) {
                if (errorMsgMap.get(memberTransMap.get(memberTransId)) == null) {
                    StringBuilder errorBuffer = new StringBuilder();
                    errorMsgMap.put(memberTransMap.get(memberTransId), errorBuffer.append("第").
                            append(memberTransMap.get(memberTransId) + 1).
                            append("行:商户流水号存在,流水号").append(memberTransId).append("\r\n"));
                } else {
                    int i = errorMsgMap.get(memberTransMap.get(memberTransId)).indexOf("\r");
                    errorMsgMap.get(memberTransMap.get(memberTransId)).
                            insert(i, "|商户流水号存在,流水号").append(memberTransId);
                }
            }
            memberTransIds.clear();
        }
    }

}
