package com.baofu.cbpayservice.dal.mapper;

import com.baofu.cbpayservice.dal.models.FiCbPayChannelGroupDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 渠道分组对应关系信息
 * <p>
 * 1、新增渠道分组信息编号
 * 2、根据分组和币种查询渠道分组信息
 * </p>
 * User: 香克斯 Date:2017/4/24 ProjectName: cbpay-service Version: 1.0
 */
public interface ChannelGroupMapper {

    /**
     * 新增渠道分组信息编号
     *
     * @param fiCbPayChannelGroupDo 渠道分组信息
     * @return 返回受影响的行数
     */
    int insertChannelGroup(FiCbPayChannelGroupDo fiCbPayChannelGroupDo);

    /**
     * 根据分组和币种查询渠道分组信息
     *
     * @param groupId 分组编号
     * @param ccy     币种
     * @return 渠道分组信息
     */
    FiCbPayChannelGroupDo selectChannelGroup(@Param("groupId") Integer groupId, @Param("ccy") String ccy);

    /**
     * 根据币种查询所有分组信息
     *
     * @param ccy         币种
     * @param channelType 渠道类型
     * @return 返回分组集合
     */
    List<Integer> selectGroupList(@Param("ccy")String ccy, @Param("channelType")Integer channelType);

    /**
     * 更新渠道分组信息
     *
     * @param fiCbPayChannelGroupDo 渠道分组信息
     * @return 返回受影响的行数
     */
    int updateChannelGroup(FiCbPayChannelGroupDo fiCbPayChannelGroupDo);
}
