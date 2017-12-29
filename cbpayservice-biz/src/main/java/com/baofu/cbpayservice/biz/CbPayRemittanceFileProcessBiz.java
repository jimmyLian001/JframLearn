package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.biz.models.ProxyCustomsMqBo;
import com.baofu.cbpayservice.biz.models.RemitFileCheckResultBo;

import java.util.List;

/**
 * 功能：汇款文件处理解析
 * User: feng_jiang Date:2017/7/7
 */
public interface CbPayRemittanceFileProcessBiz {
    /**
     * 功能：excel 数据解析与校验
     *
     * @param proxyCustomsMqBo 代理对象
     * @param list             excel内容
     * @param careerType       行业类型
     * @return 校验结果
     */
    <T> RemitFileCheckResultBo dataParseCheck(ProxyCustomsMqBo proxyCustomsMqBo, List<Object[]> list, String careerType);

    /**
     * 功能：批量保存数据
     *
     * @param remitFileCheckResultBo 汇款文件校验结果
     * @param proxyCustomsMqBo       mq内容
     * @return 订单总条数
     */
    int batchInsertData(RemitFileCheckResultBo remitFileCheckResultBo, ProxyCustomsMqBo proxyCustomsMqBo);

}
