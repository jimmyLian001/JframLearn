package com.baofu.international.global.account.core.biz.external;

import com.baofoo.cbcgw.facade.api.gw.CgwReceiptFacade;
import com.baofoo.cbcgw.facade.dto.channel.response.ChLookUserRespDto;
import com.baofoo.cbcgw.facade.dto.gw.request.CgwLookUpUserReqDto;
import com.baofoo.cbcgw.facade.dto.gw.response.result.CgwExchangeRateResultDto;
import com.baofu.international.global.account.core.biz.ChannelRateQueryBiz;
import com.baofu.international.global.account.core.biz.UserBalBiz;
import com.baofu.international.global.account.core.biz.impl.ChannelRouteBizImpl;
import com.baofu.international.global.account.core.biz.models.AccBalanceBo;
import com.baofu.international.global.account.core.biz.models.ChannelRouteBo;
import com.baofu.international.global.account.core.biz.models.UserAccountBalBo;
import com.baofu.international.global.account.core.common.constant.ConfigDict;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.util.BeanCopyUtils;
import com.baofu.international.global.account.core.dal.model.UserAccountBalDo;
import com.baofu.international.global.account.core.dal.model.UserPayeeAccountDo;
import com.baofu.international.global.account.core.manager.UserAccountBalManager;
import com.baofu.international.global.account.core.manager.UserAccountManager;
import com.system.commons.exception.BizServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 1、方法描述
 * </p>
 * User: 香克斯  Date: 2017/11/14 ProjectName:account-core  Version: 1.0
 */
@Slf4j
@Component
public class UserBalBizImpl implements UserBalBiz {

    /**
     * 渠道账户查询接口
     */
    @Autowired
    private CgwReceiptFacade cgwReceiptFacade;

    /**
     * 用户账户相关服务
     */
    @Autowired
    private UserAccountBizImpl userAccountBiz;

    /**
     * 用户账户相关
     */
    @Autowired
    private UserAccountBalManager userAccountBalManager;

    /**
     * 渠道汇率服务
     */
    @Autowired
    private ChannelRateQueryBiz channelRateQueryBiz;

    /**
     * 用户账户
     */
    @Autowired
    private UserAccountManager userAccountManager;

    /**
     * 配置中心
     */
    @Autowired
    private ConfigDict configDict;

    /**
     * 渠道路由
     */
    @Autowired
    private ChannelRouteBizImpl channelRouteBiz;

    /**
     * 查询账户余额
     *
     * @param userNo    用户号
     * @param accountNo 账户号
     * @return 返回账户信息
     */
    @Override
    public UserAccountBalBo queryUserAccountBal(Long userNo, Long accountNo) {
        //查询数据库信息
        UserAccountBalDo userAccountBalDo = userAccountBalManager.queryBalInfoByKeys(userNo, accountNo);
        if (userAccountBalDo == null) {
            log.info("call 用户号:{},账户号：{},用户账户余额信息不存在", userNo, accountNo);
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190019);
        }
        //查询账户信息
        UserPayeeAccountDo tPayeeAccountDo = userAccountManager.queryUserAccount(userNo, accountNo);
        if (tPayeeAccountDo == null || tPayeeAccountDo.getStatus() != NumberDict.ONE) {
            return BeanCopyUtils.objectConvert(userAccountBalDo, UserAccountBalBo.class);
        }
        ChannelRouteBo channelRouteBo = channelRouteBiz.channelRouteQuery(userAccountBalDo.getCcy());
        if (!channelRouteBo.getLinkedModel()) {
            log.info("线下渠道直接查询账户余额信息，不查询渠道方：{}", channelRouteBo);
            return BeanCopyUtils.objectConvert(userAccountBalDo, UserAccountBalBo.class);
        }
        //判断是否需要更新
        Long dateDifference = System.currentTimeMillis() - userAccountBalDo.getUpdateAt().getTime();
        if (dateDifference < NumberDict.THIRTY_MINUTES) {
            return BeanCopyUtils.objectConvert(userAccountBalDo, UserAccountBalBo.class);
        }
        //查询渠道账户余额
        BigDecimal accountBal = null;
        try {
            accountBal = this.queryBalanceAmt(userNo, tPayeeAccountDo.getChannelId().intValue(), tPayeeAccountDo.getWalletId());
        } catch (Exception e) {
            log.error("查询渠道账户余额异常，Exception：{}：", e);
        }
        UserAccountBalBo userAccountBalBo = new UserAccountBalBo();
        userAccountBalBo.setAccountBal(accountBal == null ? BigDecimal.ZERO : accountBal);
        userAccountBalBo.setAvailableAmt(accountBal == null ? BigDecimal.ZERO : (accountBal.subtract(userAccountBalDo.getWithdrawProcessAmt())).
                setScale(NumberDict.TWO, BigDecimal.ROUND_DOWN));
        userAccountBalBo.setWithdrawProcessAmt(userAccountBalDo.getWithdrawProcessAmt());
        userAccountBalBo.setUserNo(userNo);
        userAccountBalBo.setCcy(userAccountBalDo.getCcy());
        userAccountBalBo.setChannelId(userAccountBalDo.getChannelId());
        userAccountBalBo.setWaitAmt(BigDecimal.ZERO);
        userAccountBalBo.setAccountNo(userAccountBalDo.getAccountNo());
        if (accountBal != null) {
            userAccountBalDo.setAccountBal(accountBal);
            userAccountBalDo.setAvailableAmt(accountBal);
            userAccountBalManager.modifyAccountBal(userAccountBalDo);
        }
        return userAccountBalBo;
    }

    /**
     * 查询账户币种余额
     *
     * @param userNo 用户号
     * @param ccy    币种
     * @return AccBalanceBo 账户余额信息
     */
    @Override
    public AccBalanceBo queryCcyBalance(Long userNo, String ccy) {
        BigDecimal balanceAmt = sumUserAccBalance(userNo, ccy);
        CgwExchangeRateResultDto exchangeRateResultDto = channelRateQueryBiz.getExchangeRate(Long.valueOf(configDict.getBocSettleChannelId()),
                ccy);
        log.info("获取渠道汇率信息:{}", exchangeRateResultDto);
        BigDecimal rmbAmt = exchangeRateResultDto.getBuyRateOfCcy().multiply(balanceAmt);
        AccBalanceBo accBalanceBo = new AccBalanceBo();
        accBalanceBo.setCcy(ccy);
        accBalanceBo.setBalance(balanceAmt.divide(BigDecimal.ONE, NumberDict.TWO, BigDecimal.ROUND_DOWN));
        accBalanceBo.setRmbBalance(rmbAmt.divide(new BigDecimal("100"), NumberDict.TWO, BigDecimal.ROUND_DOWN));
        accBalanceBo.setEnteringBalance(BigDecimal.ZERO);
        log.info("{}币种账户余额信息:{}", ccy, accBalanceBo);
        return accBalanceBo;
    }

    /**
     * 查询用户账户余额
     *
     * @param userNo    用户号
     * @param channelId 渠道编号
     * @param walletId  渠道虚拟账户
     * @return 提现订单
     */

    public BigDecimal queryBalanceAmt(Long userNo, Integer channelId, String walletId) {

        //查询账户余额
        CgwLookUpUserReqDto upUserReqDto = new CgwLookUpUserReqDto();
        upUserReqDto.setUserId(walletId);
        upUserReqDto.setChannelId(channelId);
        log.info("call 调用渠道查询账户余额，请求参数{}", upUserReqDto);
        ChLookUserRespDto chLookUserRespDto = cgwReceiptFacade.lookUpUser(upUserReqDto);
        log.info("call 调用渠道查询账户余额，返回参数{}", chLookUserRespDto);
        if (chLookUserRespDto == null || chLookUserRespDto.getBalances() == null || chLookUserRespDto.getBalances().getUsd() == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal balanceAmt = chLookUserRespDto.getBalances().getUsd();
        log.info("call 查询账户：{},余额为:{}", userNo, balanceAmt);
        return balanceAmt;
    }

    /**
     * 功能：根据虚拟账号查询账户余额
     *
     * @param walletId  虚拟账号
     * @param channelId 渠道
     * @return 返回账户余额信息
     */
    @Override
    public UserAccountBalBo queryAccountBalByWalletId(String walletId, Long channelId) {

        UserPayeeAccountDo userPayeeAccountDo = userAccountBiz.queryUserAccountByWalletId(walletId, channelId);

        return this.queryUserAccountBal(userPayeeAccountDo.getUserNo(), userPayeeAccountDo.getAccountNo());
    }

    /**
     * 统计用户账户余额
     *
     * @param userNo 用户号
     * @param ccy    币种
     * @return 账户总额
     */
    private BigDecimal sumUserAccBalance(Long userNo, String ccy) {
        List<UserPayeeAccountDo> userPayeeAccountDoList = userAccountBiz.selectPayeeAccountByCcy(userNo, ccy);
        BigDecimal balanceAmt = BigDecimal.ZERO;
        for (UserPayeeAccountDo userPayeeAccountDo : userPayeeAccountDoList) {
            try {
                //查询子账户余额
                UserAccountBalBo userAccountBalBo = this.queryUserAccountBal(userNo, userPayeeAccountDo.getAccountNo());
                balanceAmt = balanceAmt.add(userAccountBalBo.getAccountBal());
            } catch (Exception e) {
                log.info("查询账户余额异常:{}", e);
            }
        }
        return balanceAmt;
    }
}
