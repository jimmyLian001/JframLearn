package com.baofu.international.global.account.client.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.client.service.AccountService;
import com.baofu.international.global.account.core.facade.ApplyAccountFacade;
import com.baofu.international.global.account.core.facade.UserStoreAccountFacade;
import com.baofu.international.global.account.core.facade.UserWithdrawFacade;
import com.baofu.international.global.account.core.facade.model.AccBalanceDto;
import com.baofu.international.global.account.core.facade.model.PageDto;
import com.baofu.international.global.account.core.facade.model.StoreAccountReqDto;
import com.baofu.international.global.account.core.facade.model.StoreInfoModifyReqDto;
import com.baofu.international.global.account.core.facade.model.res.StoreAccountInfoRepDto;
import com.baofu.international.global.account.core.facade.model.res.UserStoreAccountsDto;
import com.system.commons.result.Result;
import com.system.commons.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 账户中心service
 *
 * @author : 康志光 Date:2017/11/08 ProjectName: account-client Version: 1.0
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    /**
     * 店铺账户服务
     */
    @Autowired
    private UserStoreAccountFacade userStoreAccountFacade;

    /**
     * 用户币种余额手续费
     */
    @Autowired
    private UserWithdrawFacade userWithdrawFacade;

    /**
     * 申请境外银行卡账号dubbo服务
     */
    @Autowired
    private ApplyAccountFacade applyAccountFacade;


    /**
     * 获取账户余额
     *
     * @param userNo 用户号
     * @param ccy    币种
     * @return AccBalanceDto 账户余额
     */
    @Override
    public AccBalanceDto getCcyBalance(Long userNo, String ccy) {
        try {
            //获取账户余额
            Result<AccBalanceDto> result = userWithdrawFacade.queryCcyBalance(userNo, ccy, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            if (result.isSuccess() && result.getResult() != null) {
                return result.getResult();
            }
        } catch (Exception e) {
            log.error("获取账户余额异常:{}", e);
        }
        return new AccBalanceDto();
    }

    /**
     * 获取店铺账户分页信息
     *
     * @param userNo    用户号
     * @param storeName 店铺名称
     * @param pageNum   页码
     * @param pageSize  条数
     * @return PageDto 店铺账户数据
     */
    @Override
    public PageDto<UserStoreAccountsDto> queryStoreAccountForPage(Long userNo, Integer userType, String storeName, String ccy, int pageNum, int pageSize) {
        try {
            //获取店铺账户信息
            StoreAccountReqDto storeAccountReqDto = new StoreAccountReqDto();
            storeAccountReqDto.setUseNo(userNo);
            storeAccountReqDto.setUserType(userType);
            storeAccountReqDto.setStoreName(storeName);
            storeAccountReqDto.setCcy(ccy);
            log.info("获取店铺账户分页信息,请求参数信息：{}", storeAccountReqDto);
            Result<PageDto<UserStoreAccountsDto>> result = userStoreAccountFacade.queryStoreAccountForPage(
                    storeAccountReqDto, pageNum, pageSize, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("获取店铺账户分页信息,返回结果：{}", result);
            if (result.isSuccess() && result.getResult() != null) {
                return result.getResult();
            }
        } catch (Exception e) {
            log.error("异常:{}", e);
        }
        return null;
    }

    /**
     * 查询账户详情
     *
     * @param userNo   用户号
     * @param userType 用户类型
     * @param storeNo  店铺号
     */
    @Override
    public StoreAccountInfoRepDto queryStoreAccountInfo(Long userNo, Integer userType, Long storeNo) {

        log.info("账户详情查询请求查询参数：userNo:{},storeNo:{}", userNo, storeNo);
        Result<StoreAccountInfoRepDto> result = userStoreAccountFacade.queryStoreAccountInfo(userNo, storeNo,
                MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("账户详情查询请求查询返回结果：{}", result);
        ResultUtil.handlerResult(result);

        return result.getResult();
    }

    /**
     * 更新店铺信息
     */
    @Override
    public void modifyUserStore(StoreInfoModifyReqDto storeInfoModifyReqDto) {

        log.info("更新用户店铺信息请求参数信息：{}", storeInfoModifyReqDto);
        Result<Boolean> result = userStoreAccountFacade.modifyStoreInfo(storeInfoModifyReqDto,
                MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("更新用户店铺信息返回结果信息：{}", result);

        ResultUtil.handlerResult(result);

    }

    /**
     * 查询用户已经申请开通的币种
     *
     * @param userNo 用户号
     * @return 返回结果
     */
    @Override
    public List<String> queryAccApplyCcy(Long userNo) {

        log.info("查询用户已经申请开通的币种：{}", userNo);
        Result<List<String>> result = applyAccountFacade.queryAccApplyCcy(userNo,
                MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("查询用户已经申请开通的币种返回结果信息：{}", result);

        ResultUtil.handlerResult(result);

        return result.getResult();
    }
}
