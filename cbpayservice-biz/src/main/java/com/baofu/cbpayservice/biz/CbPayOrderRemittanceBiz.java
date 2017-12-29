package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.biz.models.CbPaySumFileMqBo;
import com.baofu.cbpayservice.biz.models.ProxyCustomsMqBo;

/**
 * 订单汇款(非文件上传订单)
 * <p>
 * <p>
 * User: 不良人 Date:2017/5/10 ProjectName: cbpayservice Version: 1.0
 */
public interface CbPayOrderRemittanceBiz {

    /**
     * 提现订单文件处理
     *
     * @param proxyCustomsMqBo mq文件处理对象
     */
    void fileProcess(ProxyCustomsMqBo proxyCustomsMqBo);

    /**
     * 根据时间创建汇款订单：查询商户订单信息
     *
     * @param cbPaySumFileMqBo 请求参数
     */
    void fileProcessByTime(CbPaySumFileMqBo cbPaySumFileMqBo);
}
