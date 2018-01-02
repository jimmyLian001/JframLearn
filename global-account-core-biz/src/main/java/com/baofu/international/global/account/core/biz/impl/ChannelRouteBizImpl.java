package com.baofu.international.global.account.core.biz.impl;

import com.baofu.international.global.account.core.biz.models.ChannelRouteBo;
import com.baofu.international.global.account.core.common.constant.ConfigDict;
import com.baofu.international.global.account.core.common.enums.CcyEnum;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 1、方法描述
 * </p>
 * ProjectName:global-account-core
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/18
 */
@Slf4j
@Component
public class ChannelRouteBizImpl {


    /**
     * 配置中心
     */
    @Autowired
    private ConfigDict configDict;

    /**
     * 渠道路由获取
     *
     * @param ccy 币种
     */
    public ChannelRouteBo channelRouteQuery(String ccy) {
        ChannelRouteBo channelRouteBo = new ChannelRouteBo();
        if (CcyEnum.USD.getKey().equals(ccy)) {
            channelRouteBo.setChannelId(Long.valueOf(configDict.getWyreChannelId()));
            channelRouteBo.setLinkedModel(Boolean.TRUE);
            channelRouteBo.setChannelName("Wyre");
        } else if (CcyEnum.EUR.getKey().equals(ccy)) {
            channelRouteBo.setChannelId(Long.valueOf(configDict.getSkyeeChannelId()));
            channelRouteBo.setLinkedModel(Boolean.FALSE);
            channelRouteBo.setChannelName("Skyee");
        } else {
            throw new BizServiceException(CommonErrorCode.REQ_PARAM_VALUE_OUT_OF_LIMIT_RANG, "暂不支持此方式");
        }
        return channelRouteBo;
    }
}
