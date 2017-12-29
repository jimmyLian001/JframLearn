package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.biz.models.ProxyCustomsMqBo;

/**
 * 跨境汇款文件处理BIZ
 * <p/>
 * User: lian zd Date:2017/10/26 ProjectName: cbpayservice Version:1.0
 */
public interface CbPayRemitFileProcessBiz {

    /**
     * 跨境汇款文件校验和保存入库
     * 将文件校验消费方法
     * @param proxyCustomsMqBo 代理跨境结算mq消息内容对象
     */
    void remitFileProcess(ProxyCustomsMqBo proxyCustomsMqBo);

}
