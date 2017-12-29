package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.biz.models.CbPayBatchFileUpLoadBo;
import com.baofu.cbpayservice.biz.models.FiCbpayFileUploadBo;
import com.baofu.cbpayservice.biz.models.ProxyCustomsBo;
import com.baofu.cbpayservice.biz.models.ProxyCustomsV2Bo;

import java.util.List;

/**
 * 代理报关biz层服务
 * 1、代理报关服务
 * User: 不良人 Date:2017/1/4 ProjectName: cbpayservice Version: 1.0
 */
public interface ProxyCustomsBiz {

    /**
     * 代理跨境结算订单上报
     *
     * @param customsBo 非宝付支付单跨境订单上报参数对象
     */
    Long apiProxyCustom(ProxyCustomsBo customsBo);

    /**
     * 插入文件批次
     *
     * @param fiCbpayFileUploadBo 文件批次对象
     */
    Long insertFileUpload(FiCbpayFileUploadBo fiCbpayFileUploadBo);

    /**
     * 批量更新文件批次状态
     */
    void batchUpdateFileStatus(CbPayBatchFileUpLoadBo cbPayFileUpLoadBo);

    /**
     * 查询文件批次包含币种数
     *
     * @param batchFileIdList 文件批次id集合
     */
    List<String> queryAmlCcy(List<Long> batchFileIdList);

    /**
     * 跨境支付订单上报V2
     *
     * @param proxyCustomsV2Bo 请求参数
     * @return orderId
     */
    Long apiProxyCustomV2(ProxyCustomsV2Bo proxyCustomsV2Bo);
}
