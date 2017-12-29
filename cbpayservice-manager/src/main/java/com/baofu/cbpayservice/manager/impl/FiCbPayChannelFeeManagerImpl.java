package com.baofu.cbpayservice.manager.impl;

import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.dal.mapper.FiCbPayChannelFeeMapper;
import com.baofu.cbpayservice.dal.models.CbPaySelectChannelFeePageDo;
import com.baofu.cbpayservice.dal.models.FiCbPayChannelFeeDo;
import com.baofu.cbpayservice.manager.FiCbPayChannelFeeManager;
import com.baofu.cbpayservice.manager.RedisManager;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 渠道成本配置操作Manager服务实现
 * <p>
 * 1、查询渠道成本配置信息
 * </p>
 * User: wanght Date:2016/11/30 ProjectName: asias-icpservice Version: 1.0
 */
@Slf4j
@Repository
public class FiCbPayChannelFeeManagerImpl implements FiCbPayChannelFeeManager {

    /**
     * 渠道手续费规则redis key 前缀
     */
    private static final String CHANNEL_FEE_REDIS_KEY = "CBPAY:CHANNEL:FEE:";
    /**
     * 数据库生成宝付订单号服务
     */
    @Autowired
    private FiCbPayChannelFeeMapper fiCbPayChannelFeeMapper;
    /**
     * redis 服务
     */
    @Autowired
    private RedisManager redisManager;

    /**
     * 查询渠道成本配置信息
     *
     * @param channelId 渠道号
     * @param status    状态
     * @return 返回商户缓存信息
     */
    @Override
    public FiCbPayChannelFeeDo queryChannelFee(Long channelId, String status) {

        String redisKey = CHANNEL_FEE_REDIS_KEY + channelId;

        FiCbPayChannelFeeDo fiCbPayChannelFeeDo = fiCbPayChannelFeeMapper.selectChannelFee(channelId, status);
        if (fiCbPayChannelFeeDo == null) {
            log.error("渠道编号：{},查询渠道成本计算规则为空", channelId);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0063);
        }
        log.info("渠道编号：{},查询数据库信息返回为：{}", channelId, fiCbPayChannelFeeDo);
        //新增redis 缓存
        redisManager.insertObject(fiCbPayChannelFeeDo, redisKey);
        return fiCbPayChannelFeeDo;
    }

    /**
     * 根据主键查询渠道成本配置信息
     *
     * @param recordId 渠道号
     * @return 渠道成本配置信息
     */
    @Override
    public FiCbPayChannelFeeDo queryChannelFeeByKey(Long recordId) {
        return fiCbPayChannelFeeMapper.selectChannelFeeByKey(recordId);
    }

    /**
     * 汇款渠道管理查询总数
     *
     * @param channelId  渠道id
     * @param beginTime  开始时间
     * @param endTime    结束时间
     * @return 总记录数
     */
    @Override
    public Integer selectCount(Long channelId, String beginTime, String endTime) {
        return fiCbPayChannelFeeMapper.selectCount(channelId, beginTime, endTime);
    }

    /**
     * 分页查询
     *
     * @param cbPaySelectChannelFeePageDo 分页查询请求参数
     * @return 查询结果
     */
    @Override
    public List<FiCbPayChannelFeeDo> selectPageList(CbPaySelectChannelFeePageDo cbPaySelectChannelFeePageDo) {
        return fiCbPayChannelFeeMapper.selectPageList(cbPaySelectChannelFeePageDo);
    }

    /**
     * 新增渠道成本配置
     *
     * @param fiCbPayChannelFeeDo 新增请求参数
     */
    @Override
    public void addChannelFee(FiCbPayChannelFeeDo fiCbPayChannelFeeDo) {
        ParamValidate.checkUpdate(fiCbPayChannelFeeMapper.addChannelFee(fiCbPayChannelFeeDo), "新增渠道成本配置失败");
    }

    /**
     * 汇款订单商户审核
     *
     * @param fiCbPayChannelFeeDo 修改请求参数
     */
    @Override
    public void editChannelFee(FiCbPayChannelFeeDo fiCbPayChannelFeeDo) {
        fiCbPayChannelFeeMapper.editChannelFee(fiCbPayChannelFeeDo);
    }
}
