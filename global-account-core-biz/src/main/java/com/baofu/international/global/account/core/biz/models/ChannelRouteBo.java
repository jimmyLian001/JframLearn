package com.baofu.international.global.account.core.biz.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 1、渠道路由
 * </p>
 * ProjectName:global-account-core
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/18
 */
@Setter
@Getter
@ToString
public class ChannelRouteBo {

    /**
     * 渠道编号
     */
    private Long channelId;

    /**
     * 接入模式，TRUE：线上系统对接模式，FALSE线下人工操作模式
     */
    private Boolean linkedModel;

    /**
     * 渠道名称
     */
    private String channelName;
}
