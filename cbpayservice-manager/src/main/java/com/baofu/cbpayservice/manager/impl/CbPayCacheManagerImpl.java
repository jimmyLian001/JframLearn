package com.baofu.cbpayservice.manager.impl;

import com.alibaba.fastjson.JSON;
import com.baofoo.Response;
import com.baofoo.cache.service.facade.MemberQueryFacade;
import com.baofoo.cache.service.facade.TerminalQueryFacade;
import com.baofoo.cache.service.facade.cgw.ChannelCacheFacade;
import com.baofoo.cache.service.facade.cgw.model.ChannelCacheDTO;
import com.baofoo.cache.service.facade.model.*;
import com.baofu.cbpayservice.common.enums.ErrorCodeEnum;
import com.baofu.cbpayservice.manager.CbPayCacheManager;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
 * User: 香克斯 Date:2016/10/25 ProjectName: asias-icpaygate Version: 1.0
 */
@Slf4j
@Repository
public class CbPayCacheManagerImpl implements CbPayCacheManager {

    /**
     * Ma终端缓存
     */
    @Autowired
    private TerminalQueryFacade terminalQueryFacade;

    /**
     * 商户缓存信息
     */
    @Autowired
    private MemberQueryFacade memberQueryFacade;

    /**
     * 渠道缓存信息
     */
    @Autowired
    private ChannelCacheFacade channelCacheFacade;

    /**
     * 查询终端缓存
     *
     * @param terminalId 终端id
     */
    @Override
    public CacheTerminalDto getTerminal(Integer terminalId) {
        // 查询终端缓存
        Response<CacheTerminalDto> terminalCache = terminalQueryFacade.getTerminal(terminalId);
        if (!terminalCache.isSuccess()) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0066);
        }

        log.info("查询终端缓存：{}", terminalCache);

        CacheTerminalDto cacheTerminalDto = terminalCache.getResult();
        if (cacheTerminalDto == null) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0066);
        }
        return cacheTerminalDto;
    }

    /**
     * 查询终端缓存
     *
     * @param memberId 订单参数信息
     */
    @Override
    public CacheCBMemberBankDto getCBMemberBank(Integer memberId) {
        Response<CacheCBMemberBankDto> memberCache = memberQueryFacade.getCBMemberBank(memberId);
        if (!memberCache.isSuccess()) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0097);
        }
        log.info("CacheCBMemberBankDto缓存：{}", JSON.toJSONString(memberCache));

        CacheCBMemberBankDto cacheCBMemberBankDto = memberCache.getResult();
        if (cacheCBMemberBankDto == null) {
            log.info("未查询到对应的跨境银行缓存信息。memberId:{}", memberId);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0097);
        }
        return cacheCBMemberBankDto;
    }

    /**
     * 查询商户分组缓存
     *
     * @param memberId 订单参数信息
     */
    @Override
    public List<CacheMemberGroupDto> getMemberGroup(Integer memberId) {

        Response<List<CacheMemberGroupDto>> memberGroupCache = memberQueryFacade.getMemberGroup(memberId);
        if (!memberGroupCache.isSuccess()) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0097);
        }
        log.info("CacheMemberGroupDto缓存：{}", JSON.toJSONString(memberGroupCache));

        return memberGroupCache.getResult();
    }

    /**
     * 查询功能缓存
     *
     * @param memberId  订单参数信息
     * @param productId 产品id
     */
    @Override
    public List<CacheFunctionDto> getProductFunctions(Integer memberId, Integer productId) {
        Response<List<CacheFunctionDto>> functionCache = memberQueryFacade.getProductFunctions(memberId, productId);
        if (!functionCache.isSuccess()) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0097);
        }
        log.info("CacheFunctionDto缓存：{}", JSON.toJSONString(functionCache));
        List<CacheFunctionDto> cacheFunctionDto = functionCache.getResult();
        if (cacheFunctionDto == null || cacheFunctionDto.size() == 0) {
            log.info("未查询到对应的功能信息。memberId:{},productId:{}", memberId, productId);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0093);
        }
        return cacheFunctionDto;
    }

    /**
     * 查询商户信息缓存
     *
     * @param memberId 订单参数信息
     */
    @Override
    public CacheMemberDto getMember(Integer memberId) {
        Response<CacheMemberDto> memberCache = memberQueryFacade.getMember(memberId);
        if (!memberCache.isSuccess()) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0097);
        }
        log.info("CacheMemberDto缓存：{}", JSON.toJSONString(memberCache));
        CacheMemberDto cacheMemberDto = memberCache.getResult();
        if (cacheMemberDto == null) {
            log.info("未查询到对应的商户缓存信息。memberId:{}", memberId);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0097);
        }
        return cacheMemberDto;
    }

    /**
     * 查询跨境商户信息缓存
     *
     * @param memberId 订单参数信息
     */
    @Override
    public CacheCBMemberDto getCBMember(Integer memberId) {
        Response<CacheCBMemberDto> cbMemberCache = memberQueryFacade.getCBMember(memberId);
        if (!cbMemberCache.isSuccess()) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0097);
        }
        log.info("CacheCBMemberDto缓存：{}", JSON.toJSONString(cbMemberCache));
        CacheCBMemberDto cacheMemberDto = cbMemberCache.getResult();
        if (cacheMemberDto == null) {
            log.info("未查询到跨境商户缓存信息。memberId:{}", memberId);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0097);
        }
        return cacheMemberDto;
    }

    /**
     * 查询功能缓存
     *
     * @param memberId   订单参数信息
     * @param productId  产品id
     * @param functionId 功能编号
     */
    @Override
    public CacheFunctionDto getProductFunctions(Integer memberId, Integer productId, Integer functionId) {

        //根据商户 +产品获取所有功能
        List<CacheFunctionDto> getProductFunctions = getProductFunctions(memberId, productId);
        for (CacheFunctionDto cacheFunctionDto : getProductFunctions) {
            if (cacheFunctionDto.getFunctionId() == functionId) {
                return cacheFunctionDto;
            }
        }
        log.error("商户号:{},产品编号：{},功能编号：{},未开通", memberId, productId, functionId);
        throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0016);
    }

    /**
     * 根据渠道id获取渠道信息
     *
     * @param channelId 渠道id
     * @return channel info
     */
    @Override
    public ChannelCacheDTO getChannelCache(Integer channelId) {
        Response<ChannelCacheDTO> cacheDTOResponse = channelCacheFacade.getCache(channelId);
        if (!cacheDTOResponse.isSuccess()) {
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0097);
        }
        log.info("ChannelCacheDTO缓存：{}", JSON.toJSONString(cacheDTOResponse));
        ChannelCacheDTO channelCacheDTO = cacheDTOResponse.getResult();
        if (channelCacheDTO == null) {
            log.info("未查询到渠道缓存信息。channelId:{}", channelId);
            throw new BizServiceException(ErrorCodeEnum.RESULT_ERROR_BF0097);
        }
        return channelCacheDTO;
    }
}
