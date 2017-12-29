package com.baofu.cbpayservice.biz.impl;

import com.baofoo.cache.service.facade.model.CacheMemberGroupDto;
import com.baofu.cbpayservice.biz.CbPayCommonBiz;
import com.baofu.cbpayservice.biz.convert.CbPayCostConvert;
import com.baofu.cbpayservice.biz.models.ChannelCostCalBo;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.ChannelFeeStatus;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.dal.models.FiCbPayChannelCostDo;
import com.baofu.cbpayservice.dal.models.FiCbPayChannelFeeDo;
import com.baofu.cbpayservice.dal.models.FiCbPayRemittanceAdditionDo;
import com.baofu.cbpayservice.manager.*;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 网关公共服务信息
 * <p>
 * 1、根据商户号加币种查询渠道编号
 * 2、渠道成本计算
 * </p>
 * User: 香克斯 Date:2017/4/24 ProjectName: cbpay-service Version: 1.0
 */
@Slf4j
@Component
public class CbPayCommonBizImpl implements CbPayCommonBiz {

    /**
     * 缓存服务
     */
    @Autowired
    private CbPayCacheManager cacheManager;

    /**
     * 渠道分组信息查询
     */
    @Autowired
    private ChannelGroupManager channelGroupManager;

    /**
     * cbpay渠道成本配置操作服务
     */
    @Autowired
    private FiCbPayChannelFeeManager fiCbPayChannelFeeManager;

    /**
     * CbPay渠道成本操作服务
     */
    @Autowired
    private FiCbPayChannelCostManager fiCbPayChannelCostManager;

    /**
     * 汇款订单信息操作服务
     */
    @Autowired
    private CbPayRemittanceManager cbPayRemittanceManager;

    /**
     * 根据商户号加币种查询渠道编号
     *
     * @param memberId    　商户编号
     * @param ccy         　币种
     * @param channelType 　渠道类型
     * @return 渠道编号
     */
    @Override
    public Long queryChannelId(Long memberId, String ccy, Integer channelType) {

        //查询商户分组缓存信息
        List<CacheMemberGroupDto> cacheMemberGroupList = cacheManager.getMemberGroup(memberId.intValue());
        if (CollectionUtils.isEmpty(cacheMemberGroupList)) {
            log.error("查询商户分组信息为空，商户编号：{}", memberId);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00127);
        }

        List<Integer> groupIdList = channelGroupManager.queryGroupId(ccy, channelType);
        for (CacheMemberGroupDto cacheMemberGroupDto : cacheMemberGroupList) {

            if (!groupIdList.contains(cacheMemberGroupDto.getGroupId())) {
                continue;
            }
            Long channelId = channelGroupManager.queryChannelId(cacheMemberGroupDto.getGroupId(), ccy);
            log.info("商户号：{},分组编号：{},币种：{}，查询到渠道为：{}", memberId, cacheMemberGroupDto.getGroupId(), ccy,
                    channelId);
            return channelId;
        }
        log.error("商户号：{},币种：{},未配置商户分组信息", memberId, ccy);
        throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00127);
    }

    /**
     * 渠道成本计算
     *
     * @param channelCostCalBo 渠道成本计算业务逻辑处理请求参数
     */
    @Override
    public BigDecimal channelCostCal(ChannelCostCalBo channelCostCalBo) {

        //计算渠道成本手续费
        BigDecimal channelFee = channelCostCalFee(channelCostCalBo);

        //参数信息转换
        FiCbPayChannelCostDo fiCbPayChannelCostDo = CbPayCostConvert.paramConvert(channelCostCalBo, channelFee);
        log.info("渠道成本新增信息：{}", fiCbPayChannelCostDo);

        //添加渠道陈本信息记录
        fiCbPayChannelCostManager.addChannelCost(fiCbPayChannelCostDo);

        return channelFee;
    }

    /**
     * 渠道成本计算并返回手续费
     *
     * @param channelCostCalBo 渠道成本计算业务逻辑处理请求参数
     * @return 渠道成本手续费
     */
    @Override
    public BigDecimal channelCostCalFee(ChannelCostCalBo channelCostCalBo) {

        log.info("渠道成本计算请求参数信息：{}", channelCostCalBo);

        //查询渠道成本计算规则
        FiCbPayChannelFeeDo fiCbPayChannelFeeDo = fiCbPayChannelFeeManager.queryChannelFee(channelCostCalBo.getChannelId(),
                ChannelFeeStatus.TRUE.getCode());
        log.info("渠道成本配置:{}", fiCbPayChannelFeeDo);
        if (fiCbPayChannelFeeDo == null) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0063);
        }
        // 查询汇款附加信息
        FiCbPayRemittanceAdditionDo additionDo = cbPayRemittanceManager.queryRemittanceAddition(
                channelCostCalBo.getBatchNo(), channelCostCalBo.getMemberId());
        // 判断订单附加信息是否存在
        if (additionDo == null) {
            log.info("memberId:{},batchNo:{},该汇款附加信息不存在", channelCostCalBo.getMemberId(), channelCostCalBo.getBatchNo());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0086);
        }
        //平安渠道
        if (Constants.PINGAN_BANK_AISLE_ID.equals(String.valueOf(channelCostCalBo.getChannelId()).substring(0, 5))) {
            // 平安百分比
            BigDecimal fee = fiCbPayChannelFeeDo.getChargeValue().multiply(channelCostCalBo.getOrderAmt()).divide(
                    new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            // 小于最小金额
            if (fee.compareTo(fiCbPayChannelFeeDo.getMinMoney()) == -1) {
                fee = fiCbPayChannelFeeDo.getMinMoney();
            }
            // 大于最大金额
            if (fee.compareTo(fiCbPayChannelFeeDo.getMaxMoney()) == 1) {
                fee = fiCbPayChannelFeeDo.getMaxMoney();
            }

            log.info("费用承担方式：{}", additionDo.getCostBorne());
            if (additionDo.getCostBorne().equals("3")) {
                fee = fee.add(new BigDecimal("200"));
            }
            log.info("手续费类型：{}，手续费：{}", fiCbPayChannelFeeDo.getChargeType(), fee);
            return fee;
        }
        //中行渠道
        else if (Constants.CHINA_BANK_AISLE_ID.equals(String.valueOf(channelCostCalBo.getChannelId()).substring(0, 5))) {
            //是否远洋
            BigDecimal fixAmt = "YES".equals(additionDo.getOceanic()) ? fiCbPayChannelFeeDo.getAbroadFixedMoney()
                    : fiCbPayChannelFeeDo.getFixedMoney();
            // 中行百分比
            BigDecimal feeCbTemp = channelCostCalBo.getOrderAmt().divide(new BigDecimal("1000"), 2, RoundingMode.HALF_UP);
            BigDecimal feeCb = feeCbTemp.divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP);

            // 小于最小金额
            if (feeCb.compareTo(fiCbPayChannelFeeDo.getMinMoney()) == -1) {
                feeCb = fiCbPayChannelFeeDo.getMinMoney();
            }
            // 大于最大金额
            if (feeCb.compareTo(fiCbPayChannelFeeDo.getMaxMoney()) == 1) {
                feeCb = fiCbPayChannelFeeDo.getMaxMoney();
            }
            feeCb = feeCb.add(fixAmt);
            log.info("手续费类型：{}，手续费：{}", fiCbPayChannelFeeDo.getChargeType(), feeCb);
            return feeCb;
        } else {
            log.error("暂不支持的银行通道：{}", channelCostCalBo.getChannelId());
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00153);
        }
    }

}
