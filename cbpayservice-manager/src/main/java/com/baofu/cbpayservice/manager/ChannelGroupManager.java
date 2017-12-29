package com.baofu.cbpayservice.manager;

import com.baofu.cbpayservice.dal.models.FiCbPayChannelGroupDo;

import java.util.List;

/**
 * 渠道分组对应关系信息
 * <p>
 * 1、新增渠道分组信息
 * 2、根据分组和币种查询渠道分组信息
 * 3、根据分组和币种查询渠道编号
 * </p>
 * User: 香克斯 Date:2017/4/24 ProjectName: cbpay-service Version: 1.0
 */
public interface ChannelGroupManager {

    /**
     * 新增渠道分组信息
     *
     * @param fiCbPayChannelGroupDo 渠道分组信息
     */
    void addChannelGroup(FiCbPayChannelGroupDo fiCbPayChannelGroupDo);

    /**
     * 根据分组和币种查询渠道分组信息
     *
     * @param groupId 分组编号
     * @param ccy     币种
     * @return 渠道分组信息
     */
    FiCbPayChannelGroupDo queryChannelGroup(Integer groupId, String ccy);

    /**
     * 根据分组和币种查询渠道编号
     *
     * @param groupId 分组编号
     * @param ccy     币种
     * @return 渠道编号
     */
    Long queryChannelId(Integer groupId, String ccy);

    /**
     * 查询币种支持所有的分组
     *
     * @param ccy         币种
     * @param channelType 渠道类型
     * @return 渠道编号集合
     */
    List<Integer> queryGroupId(String ccy, Integer channelType);

    /**
     * 更新渠道分组信息
     *
     * @param fiCbPayChannelGroupDo 渠道分组信息
     */
    void modifyChannelGroup(FiCbPayChannelGroupDo fiCbPayChannelGroupDo);
}
