package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.biz.models.ProxyCustomsMqBo;

import java.util.List;
import java.util.Map;

/**
 * 提现文件校验
 * <p>
 * User: 不良人 Date:2017/5/11 ProjectName: cbpayservice Version: 1.0
 */
public interface CbPayOrderRemitCheckBiz {

    /**
     * 提现文件校验
     *
     * @param list             文件
     * @param proxyCustomsMqBo 代理结算请求参数
     * @return 错误信息
     */
    Map<Integer, StringBuffer> check(List<Object[]> list, ProxyCustomsMqBo proxyCustomsMqBo);
}
