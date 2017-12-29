package com.baofu.cbpayservice.biz.impl;

import com.baofu.cbpayservice.biz.CbPayOrderRemitCheckBiz;
import com.baofu.cbpayservice.biz.models.ProxyCustomsMqBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.manager.CbPayOrderRemittanceManage;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 提现文件校验
 * <p>
 * User: 不良人 Date:2017/5/11 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class CbPayOrderRemitCheckBizImpl implements CbPayOrderRemitCheckBiz {

    /**
     * 提现订单操作
     */
    @Autowired
    private CbPayOrderRemittanceManage cbPayOrderRemittanceManage;

    /**
     * 提现文件校验
     *
     * @param list 文件
     * @return 错误信息
     */
    @Override
    public Map<Integer, StringBuffer> check(List<Object[]> list, ProxyCustomsMqBo proxyCustomsMqBo) {

        List<Long> orderIdList = Lists.newArrayList();
        Map<Integer, StringBuffer> errorMap = Maps.newHashMap();
        Map<String, Integer> dataMap = Maps.newHashMap();
        for (int i = 1; i < list.size(); i++) {
            StringBuffer errorBuffer = new StringBuffer();
            String orderId = String.valueOf(list.get(i)[0]);

            if (StringUtils.isBlank(orderId) || !orderId.matches(Constants.NUMBER_REG)) {
                errorMap.put(i + 1, errorBuffer.append("第").append(i + 1).append("行:宝付订单号填写错误").append("\r\n"));
            } else {
                orderIdList.add(Long.parseLong(orderId));
                if (dataMap.get(orderId) != null) {
                    log.info("call 宝付订单号={}重复.", orderId);
                    errorMap.put(i + 1, errorBuffer.append("第").append(i + 1).append("行:宝付订单号不能重复").append("\r\n"));
                }
                dataMap.put(orderId, i);
            }

            //判断宝付订单是否存在
            if ((orderIdList.size() % Constants.EXCEL_MAX_COUNT == 0 || i == list.size() - 1) && orderIdList.size() > 0) {
                checkExitsOrder(orderIdList, errorMap, proxyCustomsMqBo.getMemberId(), dataMap);
            }
        }
        return errorMap;
    }

    /**
     * 判断订单是否存在
     *
     * @param orderIdList 订单集合
     * @param errorMap    错误信息集合
     */
    private void checkExitsOrder(List<Long> orderIdList, Map<Integer, StringBuffer> errorMap, Long memberId,
                                 Map<String, Integer> dataMap) {

        List<Long> orders = cbPayOrderRemittanceManage.queryByOrderId(memberId, orderIdList);
        if (orderIdList.size() == orders.size()) {
            return;
        }
        for (Long orderId : orderIdList)
            if (!orders.contains(orderId)) {
                log.info("call 宝付订单号={}不存在.", orderId);
                StringBuffer stringBuffer = errorMap.get(dataMap.get(String.valueOf(orderId)));
                if (stringBuffer == null) {
                    errorMap.put(dataMap.get(String.valueOf(orderId)), new StringBuffer().append("第")
                            .append(dataMap.get(String.valueOf(orderId)) + 1).append("行:宝付订单号不存在或该订单已确认").append("\r\n"));
                } else {
                    int i = stringBuffer.indexOf("\r");
                    errorMap.put(dataMap.get(String.valueOf(orderId)), stringBuffer.insert(i, "|宝付订单号不存在或该订单已确认"));
                }
            }

    }

}
