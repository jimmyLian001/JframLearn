package com.baofu.international.global.account.core.biz.impl;

import com.baofoo.cbcgw.facade.api.gw.CgwReceiptFacade;
import com.baofoo.cbcgw.facade.dict.BaseResultEnum;
import com.baofoo.cbcgw.facade.dto.gw.base.CgwBaseRespDto;
import com.baofoo.cbcgw.facade.dto.gw.request.CgwCreateUserReqDto;
import com.baofoo.commons.utils.JsonUtil;
import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.ApplyAccountBiz;
import com.baofu.international.global.account.core.biz.MqSendService;
import com.baofu.international.global.account.core.biz.convert.ApplyAccountBizConvert;
import com.baofu.international.global.account.core.biz.models.*;
import com.baofu.international.global.account.core.common.constant.*;
import com.baofu.international.global.account.core.common.enums.*;
import com.baofu.international.global.account.core.dal.model.*;
import com.baofu.international.global.account.core.manager.*;
import com.baofu.international.global.agent.core.facade.AgentUserRelationFacade;
import com.google.common.collect.Lists;
import com.system.commons.exception.BizServiceException;
import com.system.commons.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * User: yangjian  Date: 2017-11-04 ProjectName:  Version: 1.0
 */
@Service
@Slf4j
public class ApplyAccountBizImpl implements ApplyAccountBiz {

    /**
     * 配置中心
     */
    @Autowired
    private ConfigDict configDict;

    /**
     *
     */
    @Autowired
    private ApplyAccountManager applyAccountManager;

    /**
     * 渠道服务
     */
    @Autowired
    private CgwReceiptFacade cgwReceiptFacade;

    /**
     * 订单创建
     */
    @Autowired
    private OrderIdManager orderIdManager;

    /**
     * redis
     */
    @Autowired
    private RedisManager redisManager;

    /**
     *
     */
    @Autowired
    private UserInfoManager userInfoManager;
    /**
     *
     */
    @Autowired
    private AccQualifiedManager accQualifiedManager;
    /**
     *
     */
    @Autowired
    private UserStoreManager userStoreManager;

    /**
     * 渠道路由获取
     */
    @Autowired
    private ChannelRouteBizImpl channelRouteBiz;

    /**
     * 企业用户Manager服务
     */
    @Autowired
    private UserOrgManager userOrgManager;

    /**
     * 个人用户Manager服务
     */
    @Autowired
    private UserPersonalManager userPersonalManager;

    /**
     * 代理商用户关系对外接口
     */
    @Autowired
    private AgentUserRelationFacade agentUserRelationFacade;
    /**
     * 发送mq服务
     */
    @Autowired
    private MqSendService mqSendService;

    /**
     * 查询出当前用户的银行卡信息
     *
     * @param userNo   userNo
     * @param userType userType
     * @return 返回结果
     */
    @Override

    public List<ApplyAccountInfoBo> getApplyAccountPageInfo(Long userNo, Integer userType) {

        List<ApplyAccountInfoBo> result = new ArrayList<>();
        log.info("global-biz-根据用户号和用户类型获取相关信息：");
        log.info("global-biz-根据用户号：{}，用户类型：{}", userNo, userType);
        List<AccQualifiedDo> list = accQualifiedManager.queryQualifiedByUserNo(userNo);
        log.info("global-biz-用户{}的资质条数：{}", userNo, list.size());
        if (userType == UserTypeEnum.ORG.getType()) {
            log.info("global-biz-企业用户根据用户号查询主体信息");
            for (AccQualifiedDo accQualifiedDo : list) {
                UserOrgDo userOrgDo = userOrgManager.queryByUserInfoNo(accQualifiedDo.getUserInfoNo());
                result.add(ApplyAccountBizConvert.paramConvert(userOrgDo, accQualifiedDo.getQualifiedNo()));
            }
        } else {
            log.info("global-biz-个人用户根据用户号查询主体信息");
            UserInfoDo userInfoDo = userInfoManager.selectUserInfoByUserNo(userNo);
            UserPersonalDo personalDo = userPersonalManager.queryByUserInfoNo(userInfoDo.getUserInfoNo());
            result.add(ApplyAccountBizConvert.paramConvert(personalDo, list.get(NumberDict.ZERO).getQualifiedNo()));
        }
        return result;
    }


    /**
     * 申请跨境账号
     *
     * @param applyAccountBo applyAccountBo
     */
    @Override
    public void applyAccount(ApplyAccountBo applyAccountBo) {

        //渠道路由获取
        ChannelRouteBo channelRouteBo = channelRouteBiz.channelRouteQuery(applyAccountBo.getCcy());
        log.info("global-biz-添加申请开户记录：");
        UserPayeeAccountApplyDo accountApplyDo = ApplyAccountBizConvert.paramConvertToApply(applyAccountBo, channelRouteBo.getChannelId());
        if (!applyAccountManager.getApplyAccountByApplyNo(accountApplyDo).isEmpty()) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_220005, "您选择的开户主体正在开户中或已开户成功，请确认");
        }
        if (!channelRouteBo.getLinkedModel()) {
            accountApplyDo.setStatus(AccApplyStatusEnum.INIT.getCode());
        }
        //用户选择已有店铺时校验用户卖家编号是否已经存在
        if (StoreExistEnum.YES.getCode().equals(applyAccountBo.getStoreExist())) {
            userStoreManager.checkSellerIdIsExist(applyAccountBo.getSellerId());
        }
        applyAccountManager.addApplyRecourd(accountApplyDo);
        log.info("global-biz-发送渠道申请开户：");
        this.sendMqMessage(accountApplyDo.getUserNo());
        log.info("global-biz-添加绑定店铺信息：");
        userStoreManager.addUserStore(ApplyAccountBizConvert.paramConvertToStore(applyAccountBo));
        if (applyAccountBo.getRealNameStatus() != RealNameStatusEnum.SUCCESS.getState()) {
            log.info("global-biz-资质状态：{}(1：审核中 2：已审核)，需要先审核才能开通", applyAccountBo.getRealNameStatus());
            return;
        }
        if (!channelRouteBo.getLinkedModel()) {
            log.info("渠道为线下模式渠道，开户不发送渠道，参数信息：{}", channelRouteBo);
            return;
        }
        getCreateUserResult(applyAccountBo);
    }

    /**
     * 根据id查询用户的相关信息
     *
     * @param qualifiedNo 资质编号
     * @return 返回结果
     */
    @Override
    public List<ApplyAccountBo> getApplyInfo(Long qualifiedNo) {

        List<ApplyAccountBo> result = Lists.newArrayList();
        //查询用户资质信息
        AccQualifiedDo accQualifiedDo = accQualifiedManager.queryQualifiedByAQualifiedNo(qualifiedNo);
        //查询用户类型
        UserInfoDo userInfoDo = userInfoManager.selectUserInfoByUserNo(accQualifiedDo.getUserNo());
        log.info("global-biz-根据资质编号查询待审核申的请记录");
        List<UserPayeeAccountApplyDo> applyDoList = applyAccountManager.queryApplyAccountByQualifiedNo(qualifiedNo);
        log.info("global-biz-根据资质编号查询待审核的记录有:{}条", applyDoList.size());

        for (UserPayeeAccountApplyDo applyDo : applyDoList) {
            ApplyAccountBo applyAccountBo = new ApplyAccountBo();
            UserStoreDo storeDo = userStoreManager.queryByStoreNo(applyDo.getStoreNo());
            applyAccountBo.setUserNo(applyDo.getUserNo());
            applyAccountBo.setApplyId(applyDo.getApplyId());
            applyAccountBo.setStoreName(storeDo.getStoreName());
            applyAccountBo.setQualifiedNo(applyDo.getQualifiedNo());
            applyAccountBo.setCcy(applyDo.getCcy());
            log.info("global-biz-根据客户的类型获取客户的证件申请信息，申请编号：{}", applyAccountBo.getApplyId());
            if (UserTypeEnum.ORG.getType() == userInfoDo.getUserType()) {
                log.info("global-biz-个人客户信息查询:");
                ApplyAccountBizConvert.paramConvert(userOrgManager.queryByUserInfoNo(accQualifiedDo.getUserInfoNo()), applyAccountBo);
            } else {
                log.info("global-biz-企业客户信息查询:");
                ApplyAccountBizConvert.paramConvert(userPersonalManager.queryByUserInfoNo(accQualifiedDo.getUserInfoNo()), applyAccountBo);
            }
            result.add(applyAccountBo);
        }
        return result;
    }

    /**
     * 发送渠道开户
     *
     * @param applyAccountBo 请求参数信息
     */
    @Override
    public void getCreateUserResult(ApplyAccountBo applyAccountBo) {
        //渠道路由获取
        ChannelRouteBo channelRouteBo = channelRouteBiz.channelRouteQuery(applyAccountBo.getCcy());
        if (!channelRouteBo.getLinkedModel()) {
            log.info("渠道为线下模式渠道，开户不发送渠道，参数信息：{}", channelRouteBo);
            return;
        }
        CgwCreateUserReqDto reqDto = ApplyAccountBizConvert.toCgwCreateUserReqDto(applyAccountBo, configDict.getGateHost(), channelRouteBo.getChannelId());
        log.info("global-biz-请求渠道申请开户信息:{}", reqDto);
        CgwBaseRespDto respDto = cgwReceiptFacade.createUser(reqDto);
        log.info("call 收款账户开户渠道响应结果：{}", respDto);
        UserPayeeAccountApplyDo userPayeeAccountApplyDo = new UserPayeeAccountApplyDo();
        userPayeeAccountApplyDo.setApplyId(applyAccountBo.getApplyId());
        userPayeeAccountApplyDo.setStatus(AccApplyStatusEnum.HANDLING.getCode());
        if (respDto.getCode() != BaseResultEnum.SUCCESS.getCode()) {
            userPayeeAccountApplyDo.setStatus(AccApplyStatusEnum.FAIL.getCode());
        }
        applyAccountManager.modifyApplyStatus(userPayeeAccountApplyDo);
        //同步响应失败时，流程结束
        if (AccApplyStatusEnum.FAIL.getCode() == userPayeeAccountApplyDo.getStatus()) {
            log.error("收款账户开户渠道响应失败,更新申请状态和店铺状态,流程结束，更新信息：{}", userPayeeAccountApplyDo);
            return;
        }
        if (NumberStrDict.ONE.equals(applyAccountBo.getMemberType())) {
            //营业执照缓存
            String redisKey = RedisDict.GLOBAL_ACCOUNT_IMAGE + applyAccountBo.getUserNo() + ":" + applyAccountBo.getLicenseDfsId();
            redisManager.insertObject(FlagEnum.TRUE.getCode(), redisKey, Constants.DFS_TIME_OUT);
        }
        //身份证缓存
        String passportKey = RedisDict.GLOBAL_ACCOUNT_IMAGE + applyAccountBo.getUserNo() + ":" + applyAccountBo.getPassportDfsId();
        redisManager.insertObject(FlagEnum.TRUE.getCode(), passportKey, Constants.DFS_TIME_OUT);
    }

    /**
     * 根据id查询用户的相关信息
     *
     * @param applyReqBo applyReqBo
     * @return 返回结果
     */
    @Override
    public ApplyAccountBo getApplyInfoByUserNo(ApplyAccountReqBo applyReqBo) {

        ApplyAccountBo applyAccountBo = ApplyAccountBizConvert.paramConvert(applyReqBo);
        applyAccountBo.setStoreNo(orderIdManager.orderIdCreate());
        applyAccountBo.setApplyId(orderIdManager.orderIdCreate());

        log.info("global-biz-用户：{}客户类型：{}", applyReqBo.getUserNo(), applyReqBo.getUserType());
        //查询用户资质信息
        AccQualifiedDo accQualifiedDo = accQualifiedManager.queryQualifiedByAQualifiedNo(applyAccountBo.getQualifiedNo());
        if (accQualifiedDo == null) {
            log.error("用户资质信息查询为空，资质编号：{}", applyAccountBo.getQualifiedNo());
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190404);
        }
        if (!accQualifiedDo.getUserNo().equals(applyAccountBo.getUserNo())) {
            log.error("用户资质信息异常，资质编号：{},用户编号:{}", applyAccountBo.getQualifiedNo(), applyAccountBo.getUserNo());
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190404);
        }
        log.info("global-biz-根据客户的类型获取客户的证件申请信息，申请编号：{}", applyAccountBo.getApplyId());
        if (UserTypeEnum.ORG.getType() == applyReqBo.getUserType()) {
            log.info("global-biz-个人客户信息查询:");
            ApplyAccountBizConvert.paramConvert(userOrgManager.queryByUserInfoNo(accQualifiedDo.getUserInfoNo()), applyAccountBo);
        } else {
            log.info("global-biz-企业客户信息查询:");
            ApplyAccountBizConvert.paramConvert(userPersonalManager.queryByUserInfoNo(accQualifiedDo.getUserInfoNo()), applyAccountBo);
        }
        return applyAccountBo;
    }

    /**
     * 查询代理No发送mq
     *
     * @param userNo 用户号
     */
    public void sendMqMessage(Long userNo) {
        Result<Long> result = agentUserRelationFacade.queryAgentNoByUserNo(userNo, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("查询代理商账号发送mq，查询结果：{}", result.isSuccess());
        if (result.isSuccess()) {
            OpenAccountBo accountBo = new OpenAccountBo();
            accountBo.setAgentNo(result.getResult());
            accountBo.setUserNo(userNo);
            mqSendService.sendMessage(MqSendQueueNameEnum.GLOBAL_ACCOUNT_OPEN_SUCCESS_QUEUE_NAME, JsonUtil.toJSONString(accountBo));
        }
    }
}
