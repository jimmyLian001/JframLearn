package com.baofu.cbpayservice.manager.impl;

import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.dal.mapper.ChannelGroupMapper;
import com.baofu.cbpayservice.dal.models.FiCbPayChannelGroupDo;
import com.baofu.cbpayservice.manager.ChannelGroupManager;
import com.baofu.cbpayservice.manager.RedisManager;
import com.system.commons.exception.BizServiceException;
import com.system.commons.utils.ParamValidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 渠道分组对应关系信息
 * <p>
 * 1、新增渠道分组信息编号
 * 2、根据分组和币种查询渠道分组信息
 * 3、根据分组和币种查询渠道编号
 * </p>
 * User: 香克斯 Date:2017/4/24 ProjectName: cbpay-service Version: 1.0
 */
@Slf4j
@Repository
public class ChannelGroupManagerImpl implements ChannelGroupManager {

    /**
     * 渠道分组对应关系Mapper服务
     */
    @Autowired
    private ChannelGroupMapper channelGroupMapper;

    /**
     * 缓存服务
     */
    @Autowired
    private RedisManager redisManager;


    /**
     * 新增渠道分组信息编号
     *
     * @param fiCbPayChannelGroupDo 渠道分组信息
     */
    @Override
    public void addChannelGroup(FiCbPayChannelGroupDo fiCbPayChannelGroupDo) {

        ParamValidate.checkUpdate(channelGroupMapper.insertChannelGroup(fiCbPayChannelGroupDo),
                ErrorCodeEnum.RESULT_ERROR_BF00121);
    }

    /**
     * 根据分组和币种查询渠道分组信息
     *
     * @param groupId 分组编号
     * @param ccy     币种
     * @return 渠道分组信息
     */
    @Override
    public FiCbPayChannelGroupDo queryChannelGroup(Integer groupId, String ccy) {

        String redisKey = Constants.CHANNEL_GROUP_REDIS_KEY + groupId + ccy;
        FiCbPayChannelGroupDo channelGroup = redisManager.queryObjectByKey(redisKey, FiCbPayChannelGroupDo.class);
        if (channelGroup != null) {
            log.info("根据分组和币种查询渠道分组缓存信息：{}", channelGroup);
            return channelGroup;
        }

        channelGroup = channelGroupMapper.selectChannelGroup(groupId, ccy);
        if (channelGroup == null) {
            log.error("根据分组和币种查询渠道分组失败，查询条件：groupId:{},ccy:{}", groupId, ccy);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00126);
        }
        redisManager.insertObject(channelGroup, redisKey);
        return channelGroup;
    }

    /**
     * 根据分组和币种查询渠道编号
     *
     * @param groupId 分组编号
     * @param ccy     币种
     * @return 渠道编号
     */
    @Override
    public Long queryChannelId(Integer groupId, String ccy) {

        return queryChannelGroup(groupId, ccy).getChannelId();
    }

    /**
     * 查询币种支持所有的分组
     *
     * @param ccy         币种
     * @param channelType 渠道类型
     * @return 渠道编号集合
     */
    @Override
    public List<Integer> queryGroupId(String ccy, Integer channelType) {

        String redisKey = Constants.CHANNEL_GROUP_REDIS_KEY + ccy + Constants.REDIS_SYMBOL + channelType;
        List<Integer> groupIdList = redisManager.queryListByKey(redisKey, Integer.class);
        if (!CollectionUtils.isEmpty(groupIdList)) {
            log.info("根据币种查询所有分组缓存信息返回参数：{}", groupIdList);
            return groupIdList;
        }
        groupIdList = channelGroupMapper.selectGroupList(ccy, channelType);
        if (CollectionUtils.isEmpty(groupIdList)) {
            log.info("根据币种查询所有分组查询失败，返回信息为空，查询币种：{}", ccy);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF00126);
        }
        redisManager.insertObject(groupIdList, redisKey);
        return groupIdList;
    }

    /**
     * 更新渠道分组信息
     *
     * @param fiCbPayChannelGroupDo 渠道分组信息
     */
    @Override
    public void modifyChannelGroup(FiCbPayChannelGroupDo fiCbPayChannelGroupDo) {

        redisManager.deleteObject(Constants.CHANNEL_GROUP_REDIS_KEY + fiCbPayChannelGroupDo.getCcy());
        redisManager.deleteObject(Constants.CHANNEL_GROUP_REDIS_KEY + fiCbPayChannelGroupDo.getGroupId() +
                fiCbPayChannelGroupDo.getCcy());
        ParamValidate.checkUpdate(channelGroupMapper.updateChannelGroup(fiCbPayChannelGroupDo),
                ErrorCodeEnum.RESULT_ERROR_BF00128);
    }
}
