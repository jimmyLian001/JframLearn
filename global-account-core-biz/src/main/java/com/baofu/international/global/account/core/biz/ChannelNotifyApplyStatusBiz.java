package com.baofu.international.global.account.core.biz;

import com.baofu.international.global.account.core.biz.models.ChannelNotifyApplyAccountBo;

/**
 * User: yangjian  Date: 2017-11-07 ProjectName:  Version: 1.0
 */
public interface ChannelNotifyApplyStatusBiz {

    /**
     * 接收渠道第一次MQ消息新增记录
     *
     * @param channelNotifyApplyAccountBo channelNotifyApplyAccountBo
     */
    void addApplyAccountInfo(ChannelNotifyApplyAccountBo channelNotifyApplyAccountBo);

    /**
     * 接收渠道第二次MQ消息更新账号和路由编号
     *
     * @param channelNotifyApplyAccountBo channelNotifyApplyAccountBo
     */
    void updateApplyAccountInfo(ChannelNotifyApplyAccountBo channelNotifyApplyAccountBo);
}
