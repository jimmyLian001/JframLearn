package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.biz.models.ProxyCustomsMqBo;

import java.util.List;

/**
 * 汇款文件处理服务
 * <p>
 * User: 不良人 Date:2017/6/22 ProjectName: cbpayservice Version: 1.0
 */
public interface FileProcessBiz {

    /**
     * 汇款文件处理
     *
     * @param proxyCustomsMqBo mq内容
     * @param list             汇款文件内容
     */
    void process(ProxyCustomsMqBo proxyCustomsMqBo, List<Object[]> list);
}
