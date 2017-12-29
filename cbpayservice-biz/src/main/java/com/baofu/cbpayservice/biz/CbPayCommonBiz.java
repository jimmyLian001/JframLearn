package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.biz.models.ChannelCostCalBo;

import java.math.BigDecimal;

/**
 * 网关公共服务信息
 * <p>
 * 1、根据商户号加币种查询渠道编号
 * 2、渠道成本计算
 * </p>
 * User: 香克斯 Date:2017/4/24 ProjectName: cbpay-service Version: 1.0
 */
public interface CbPayCommonBiz {

    /**
     * 根据商户号加币种查询渠道编号
     *
     * @param memberId    　商户编号
     * @param ccy         　币种
     * @param channelType 　渠道类型
     * @return 渠道编号
     */
    Long queryChannelId(Long memberId, String ccy, Integer channelType);

    /**
     * 渠道成本计算并保存渠道成本结果信息表
     *
     * @param channelCostCalBo 渠道成本计算业务逻辑处理请求参数
     * @return 渠道成本
     */
    BigDecimal channelCostCal(ChannelCostCalBo channelCostCalBo);

    /**
     * 渠道成本计算并返回手续费
     *
     * @param channelCostCalBo 渠道成本计算业务逻辑处理请求参数
     * @return 渠道成本手续费
     */
    BigDecimal channelCostCalFee(ChannelCostCalBo channelCostCalBo);
}
