package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.biz.models.CbPayChannelFeeBo;
import com.baofu.cbpayservice.biz.models.CbPaySelectChannelFeePageBo;
import com.baofu.cbpayservice.dal.models.FiCbPayChannelFeeDo;

import java.util.List;

/**
 * 跨境人民币渠道费用配置操作接口
 * <p>
 * 1、查询渠道成本总计路数
 * 2、分页查询
 * 3、新增渠道成本配置
 * 4、修改渠道成本配置
 * 5、查询渠道成本配置
 * </p>
 * User: wanght Date:2016/11/10 ProjectName: cbpay-service Version: 1.0
 */
public interface CbPayChannelFeeBiz {

    /**
     * 查询渠道成本总计路数
     *
     * @param channelId 渠道id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 总记录数
     */
    Integer selectCount(Long channelId, String beginTime, String endTime);

    /**
     * 分页查询
     *
     * @param cbPaySelectChannelFeePageDto 分页查询请求参数
     * @return 返回是否成功
     */
    List<FiCbPayChannelFeeDo> selectPageList(CbPaySelectChannelFeePageBo cbPaySelectChannelFeePageDto);

    /**
     * 新增渠道成本配置
     *
     * @param cbPayChannelFeeDto 新增请求参数
     */
    void addChannelFee(CbPayChannelFeeBo cbPayChannelFeeDto);

    /**
     * 修改渠道成本配置
     *
     * @param cbPayChannelFeeDto 修改请求参数
     */
    void editChannelFee(CbPayChannelFeeBo cbPayChannelFeeDto);

    /**
     * 查询渠道成本配置
     *
     * @param recordId 记录编号
     * @return 返回是否成功
     */
    FiCbPayChannelFeeDo queryChannelFeeByKey(Long recordId);

    /**
     * 查询渠道成本配置
     *
     * @param channelId 渠道id
     * @param status    状态
     * @return 查询结果
     */
    FiCbPayChannelFeeDo queryChannelFee(Long channelId, String status);
}
