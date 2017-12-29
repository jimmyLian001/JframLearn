package com.baofu.cbpayservice.service.convert;

import com.baofu.cbpayservice.dal.models.FiCbPayChannelGroupDo;
import com.baofu.cbpayservice.facade.models.ChannelGroupAddReqDto;
import com.baofu.cbpayservice.facade.models.ChannelGroupModifyReqDto;

/**
 * 渠道分组信息转换
 * <p>
 * </p>
 * User: 香克斯 Date:2017/4/24 ProjectName: cbpay-service Version: 1.0
 */
public class ChannelGroupConvert {

    /**
     * 渠道分组信息新增参数转换
     *
     * @param channelGroupAddReqDto 渠道分组信息新增接口参数信息
     * @param orderId               记录编号
     * @return 渠道分组信息新增参数信息
     */
    public static FiCbPayChannelGroupDo paramConvert(ChannelGroupAddReqDto channelGroupAddReqDto, Long orderId) {

        FiCbPayChannelGroupDo fiCbPayChannelGroupDo = new FiCbPayChannelGroupDo();
        fiCbPayChannelGroupDo.setCcy(channelGroupAddReqDto.getCcy());
        fiCbPayChannelGroupDo.setGroupId(channelGroupAddReqDto.getGroupId());
        fiCbPayChannelGroupDo.setChannelId(channelGroupAddReqDto.getChannelId());
        fiCbPayChannelGroupDo.setRemarks(channelGroupAddReqDto.getRemarks());
        fiCbPayChannelGroupDo.setCreateBy(channelGroupAddReqDto.getCreateBy());
        fiCbPayChannelGroupDo.setUpdateBy(channelGroupAddReqDto.getCreateBy());
        fiCbPayChannelGroupDo.setRecordId(orderId);
        fiCbPayChannelGroupDo.setChannelType(channelGroupAddReqDto.getChannelType() == null ? 0
                : channelGroupAddReqDto.getChannelType());
        return fiCbPayChannelGroupDo;
    }

    /**
     * 渠道分组信息更新参数转换
     *
     * @param channelGroupModifyReqDto 渠道分组信息更新接口参数信息
     * @return 渠道分组信息更新参数信息
     */
    public static FiCbPayChannelGroupDo paramConvert(ChannelGroupModifyReqDto channelGroupModifyReqDto) {

        FiCbPayChannelGroupDo fiCbPayChannelGroupDo = new FiCbPayChannelGroupDo();
        fiCbPayChannelGroupDo.setRecordId(channelGroupModifyReqDto.getRecordId());
        fiCbPayChannelGroupDo.setCcy(channelGroupModifyReqDto.getCcy());
        fiCbPayChannelGroupDo.setGroupId(channelGroupModifyReqDto.getGroupId());
        fiCbPayChannelGroupDo.setChannelId(channelGroupModifyReqDto.getChannelId());
        fiCbPayChannelGroupDo.setRemarks(channelGroupModifyReqDto.getRemarks());
        fiCbPayChannelGroupDo.setUpdateBy(channelGroupModifyReqDto.getUpdateBy());

        return fiCbPayChannelGroupDo;
    }
}
