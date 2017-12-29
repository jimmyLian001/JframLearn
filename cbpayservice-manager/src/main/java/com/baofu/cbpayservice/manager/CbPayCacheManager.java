package com.baofu.cbpayservice.manager;

import com.baofoo.cache.service.facade.cgw.model.ChannelCacheDTO;
import com.baofoo.cache.service.facade.model.*;

import java.util.List;

/**
 * 缓存信息Manager
 * <p>
 * 1、查询终端缓存
 * 2、查询商户银行信息缓存
 * 3、查询商户分组缓存
 * 4、查询功能缓存
 * 5、查询商户信息缓存
 * 6、查询跨境商户信息缓存
 * </p>
 * User: wanght Date:2016/12/26 ProjectName: cbpayservice Version: 1.0
 */
public interface CbPayCacheManager {

    /**
     * 查询终端缓存
     *
     * @param terminalId 终端id
     */
    CacheTerminalDto getTerminal(Integer terminalId);

    /**
     * 查询商户银行信息缓存
     *
     * @param memberId 商户id
     */
    CacheCBMemberBankDto getCBMemberBank(Integer memberId);

    /**
     * 查询商户分组缓存
     *
     * @param memberId 订单参数信息
     */
    List<CacheMemberGroupDto> getMemberGroup(Integer memberId);

    /**
     * 查询功能缓存
     *
     * @param memberId  订单参数信息
     * @param productId 产品id
     */
    List<CacheFunctionDto> getProductFunctions(Integer memberId, Integer productId);

    /**
     * 查询商户信息缓存
     *
     * @param memberId 订单参数信息
     */
    CacheMemberDto getMember(Integer memberId);

    /**
     * 查询跨境商户信息缓存
     *
     * @param memberId 订单参数信息
     */
    CacheCBMemberDto getCBMember(Integer memberId);

    /**
     * 查询功能缓存
     *
     * @param memberId   订单参数信息
     * @param productId  产品id
     * @param functionId 功能编号
     */
    CacheFunctionDto getProductFunctions(Integer memberId, Integer productId, Integer functionId);

    /**
     * 根据渠道id获取渠道信息
     *
     * @param channelId 渠道id
     * @return channel info
     */
    ChannelCacheDTO getChannelCache(Integer channelId);
}
