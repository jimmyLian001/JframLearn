package com.baofu.cbpayservice.biz.impl;

import com.baofu.cbpayservice.biz.CbPayChannelFeeBiz;
import com.baofu.cbpayservice.biz.convert.CbPayChannelFeeConvert;
import com.baofu.cbpayservice.biz.models.CbPayChannelFeeBo;
import com.baofu.cbpayservice.biz.models.CbPaySelectChannelFeePageBo;
import com.baofu.cbpayservice.dal.models.FiCbPayChannelFeeDo;
import com.baofu.cbpayservice.manager.FiCbPayChannelFeeManager;
import com.baofu.cbpayservice.manager.OrderIdManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 跨境人民币汇款操作接口
 * <p>
 * 1、创建汇款订单
 * 2、汇款订单审核
 * 3、汇款订单状态更新
 * </p>
 * User: wanght Date:2016/11/10 ProjectName: cbpay-service Version: 1.0
 */
@Slf4j
@Service
public class CbPayChannelFeeBizImpl implements CbPayChannelFeeBiz {

    /**
     * 渠道成本配置manager
     */
    @Autowired
    private FiCbPayChannelFeeManager fiCbPayChannelFeeManager;

    /**
     * 宝付订单号生成manager
     */
    @Autowired
    private OrderIdManager orderIdManager;

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
        return fiCbPayChannelFeeManager.selectCount(channelId, beginTime, endTime);
    }

    /**
     * 分页查询
     *
     * @param cbPaySelectChannelFeePageBo 分页查询请求参数
     * @return 返回是否成功
     */
    @Override
    public List<FiCbPayChannelFeeDo> selectPageList(CbPaySelectChannelFeePageBo cbPaySelectChannelFeePageBo) {
        return fiCbPayChannelFeeManager.selectPageList(CbPayChannelFeeConvert.selectPageListParamConvert(cbPaySelectChannelFeePageBo));
    }

    /**
     * 新增渠道成本配置信息
     *
     * @param cbPayChannelFeeBo 新增请求参数
     */
    @Override
    public void addChannelFee(CbPayChannelFeeBo cbPayChannelFeeBo) {
        cbPayChannelFeeBo.setRecordId(orderIdManager.orderIdCreate());
        fiCbPayChannelFeeManager.addChannelFee(CbPayChannelFeeConvert.addParamConvert(cbPayChannelFeeBo));
    }

    /**
     * 汇款订单商户审核
     *
     * @param cbPayChannelFeeBo 修改请求参数
     */
    @Override
    public void editChannelFee(CbPayChannelFeeBo cbPayChannelFeeBo) {
        fiCbPayChannelFeeManager.editChannelFee(CbPayChannelFeeConvert.editParamConvert(cbPayChannelFeeBo));
    }

    /**
     * 汇款订单商户审核
     *
     * @param recordId   记录编号
     * @return 返回是否成功
     */
    @Override
    public FiCbPayChannelFeeDo queryChannelFeeByKey(Long recordId) {
        return fiCbPayChannelFeeManager.queryChannelFeeByKey(recordId);
    }

    /**
     * 根据商户号查询商户缓存信息
     *
     * @param channelId 渠道号
     * @param status    状态
     * @return 返回商户缓存信息
     */
    @Override
    public FiCbPayChannelFeeDo queryChannelFee(Long channelId, String status) {
        return fiCbPayChannelFeeManager.queryChannelFee(channelId, status);
    }

}
