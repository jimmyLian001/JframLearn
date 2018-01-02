package com.baofu.international.global.account.core.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.UserBalBiz;
import com.baofu.international.global.account.core.biz.UserPayeeAccountBiz;
import com.baofu.international.global.account.core.biz.models.PageBo;
import com.baofu.international.global.account.core.biz.models.UserAccountBalBo;
import com.baofu.international.global.account.core.biz.models.UserStoreAccountBo;
import com.baofu.international.global.account.core.common.constant.NumberDict;
import com.baofu.international.global.account.core.common.enums.StoreExistEnum;
import com.baofu.international.global.account.core.common.util.BeanCopyUtils;
import com.baofu.international.global.account.core.dal.model.UserPayeeAccountApplyDo;
import com.baofu.international.global.account.core.dal.model.UserPayeeAccountDo;
import com.baofu.international.global.account.core.dal.model.UserStoreDo;
import com.baofu.international.global.account.core.facade.UserStoreAccountFacade;
import com.baofu.international.global.account.core.facade.model.PageDto;
import com.baofu.international.global.account.core.facade.model.StoreAccountReqDto;
import com.baofu.international.global.account.core.facade.model.StoreInfoModifyReqDto;
import com.baofu.international.global.account.core.facade.model.res.StoreAccountInfoRepDto;
import com.baofu.international.global.account.core.facade.model.res.UserStoreAccountRespDto;
import com.baofu.international.global.account.core.facade.model.res.UserStoreAccountsDto;
import com.baofu.international.global.account.core.manager.ApplyAccountManager;
import com.baofu.international.global.account.core.manager.UserAccountManager;
import com.baofu.international.global.account.core.manager.UserStoreManager;
import com.baofu.international.global.account.core.service.convert.StoreAccServiceConvert;
import com.google.common.collect.Lists;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户店铺账户信息操作
 * <p>
 * 1、根据用户编号 查询店铺收款账户信息
 * 2、根据用户编号币种查询店铺收款账户信息
 * 3、查询用户店铺账户信息分页
 * </p>
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/11/29
 */
@Slf4j
@Service
public class UserStoreAccountServiceImpl implements UserStoreAccountFacade {

    /**
     * 用户收款账户相关Biz接口
     */
    @Autowired
    private UserPayeeAccountBiz userPayeeAccountBiz;

    /**
     * 用户店铺信息
     */
    @Autowired
    private UserStoreManager userStoreManager;

    /**
     * 账户申请
     */
    @Autowired
    private ApplyAccountManager applyAccountManager;

    /**
     * 账户相关操作
     */
    @Autowired
    private UserBalBiz userBalBiz;

    /**
     * 用户收款账户信息
     */
    @Autowired
    private UserAccountManager userAccountManager;

    /**
     * 根据用户编号 查询店铺收款账户信息
     *
     * @param userNo     用户编号
     * @param traceLogId 日志编号
     * @return 返回店铺收款信息结合
     */
    @Override
    public Result<List<UserStoreAccountRespDto>> queryUserStoreAccount(Long userNo, String traceLogId) {

        Result<List<UserStoreAccountRespDto>> result;
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("call 根据用户编号和用户店铺账户查询店铺收款账户信息,用户号 ：{} ", userNo);
        try {
            List<UserStoreAccountBo> userStoreAccountBoList = userPayeeAccountBiz.queryUserStorePayAccount(userNo);
            List<UserStoreAccountRespDto> userStoreAccountList = Lists.newArrayList();
            //循环转换
            for (UserStoreAccountBo userStoreAccountBo : userStoreAccountBoList) {
                userStoreAccountList.add(BeanCopyUtils.objectConvert(userStoreAccountBo, UserStoreAccountRespDto.class));
            }
            result = new Result<>(userStoreAccountList);
        } catch (Exception e) {
            log.error("call 根据用户编号和用户店铺账户查询店铺收款账户信息异常:{}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 根据用户编号和用户店铺账户查询店铺收款账户信息返回结果：{}", result);
        return result;
    }

    /**
     * 根据用户编号币种查询店铺收款账户信息
     *
     * @param userNo     用户编号
     * @param ccy        币种
     * @param traceLogId 日志编号
     * @return 返回店铺收款信息结合
     */
    @Override
    public Result<List<UserStoreAccountRespDto>> queryUserStoreAccount(Long userNo, String ccy, String traceLogId) {
        Result<List<UserStoreAccountRespDto>> result;
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("call 根据用户编号和用户店铺账户查询店铺收款账户信息,用户号 ：{}，币种:{}", userNo, ccy);
        try {
            List<UserStoreAccountBo> userStoreAccountBoList = userPayeeAccountBiz.queryUserStorePayAccount(userNo, ccy);
            List<UserStoreAccountRespDto> userStoreAccountList = Lists.newArrayList();
            //循环转换
            for (UserStoreAccountBo userStoreAccountBo : userStoreAccountBoList) {
                userStoreAccountList.add(BeanCopyUtils.objectConvert(userStoreAccountBo, UserStoreAccountRespDto.class));
            }
            result = new Result<>(userStoreAccountList);
        } catch (Exception e) {
            log.error("call 根据用户编号和用户店铺账户查询店铺收款账户信息异常:{}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 根据用户编号和用户店铺账户查询店铺收款账户信息异常返回结果：{}", result);
        return result;
    }

    /**
     * 查询用户店铺账户信息分页
     *
     * @param storeAccountReqDto 参数
     * @param pageNum            页面数
     * @param pageSize           页面条数
     * @return 用户店铺信息集合
     */
    @Override
    public Result<PageDto<UserStoreAccountsDto>> queryStoreAccountForPage(StoreAccountReqDto storeAccountReqDto, int pageNum, int pageSize, String traceLogId) {
        Result<PageDto<UserStoreAccountsDto>> result;
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        log.info("call 根据用户编号和用户店铺账户查询用户店铺账户信息,参数 ：{} ", storeAccountReqDto);
        try {
            UserStoreDo userStoreDo = new UserStoreDo();
            userStoreDo.setUserNo(storeAccountReqDto.getUseNo());
            userStoreDo.setStoreName(storeAccountReqDto.getStoreName());
            userStoreDo.setStoreState(storeAccountReqDto.getStoreState());
            userStoreDo.setTradeCcy(storeAccountReqDto.getCcy());
            PageBo<UserStoreAccountBo> pageBo = userPayeeAccountBiz.queryStoreAccountForPage(userStoreDo, storeAccountReqDto.getUserType(), pageNum, pageSize);
            ArrayList<UserStoreAccountsDto> userStoreAccountRespDtoList = Lists.newArrayList();
            //循环转换
            for (UserStoreAccountBo userStoreAccountBo : pageBo.getList()) {
                userStoreAccountRespDtoList.add(StoreAccServiceConvert.convert(userStoreAccountBo, storeAccountReqDto));
            }

            PageDto<UserStoreAccountsDto> pageDto = new PageDto<>();
            pageDto.setList(userStoreAccountRespDtoList);
            pageDto.setTotalSize(pageBo.getTotalSize());
            result = new Result<>(pageDto);
        } catch (Exception e) {
            log.error("call 根据用户编号和用户店铺账户查询用户店铺账户信息异常:{}", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("call 根据用户编号和用户店铺账户查询用户店铺账户信息返回结果：{}", result);
        return result;
    }

    /**
     * 根据用户店铺号查询店铺信息
     * add by yangjian
     *
     * @param userNo  用户编号
     * @param storeNo 店铺编号
     * @return 结果
     */
    @Override
    public Result<StoreAccountInfoRepDto> queryStoreAccountInfo(Long userNo, Long storeNo, String traceLogId) {
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        Result<StoreAccountInfoRepDto> result;
        StoreAccountInfoRepDto storeAccountDto;
        try {
            log.info("查询店铺信息，店铺号：{}", storeNo);
            UserStoreDo storeDo = userStoreManager.queryByStoreNo(storeNo);
            log.info("查询余额，店铺号：{}，客户号：{}", storeNo, userNo);
            UserPayeeAccountApplyDo userPayeeAccountApplyDo = applyAccountManager.queryApplyAccountByStoreNo(storeNo);
            if (userPayeeAccountApplyDo.getStatus() != NumberDict.TWO) {
                log.info("用户店铺还未开通收款账户成功，查询结束");
                storeAccountDto = BeanCopyUtils.objectConvert(storeDo, StoreAccountInfoRepDto.class);
                storeAccountDto.setQualifiedNo(userPayeeAccountApplyDo.getQualifiedNo());
                storeAccountDto.setAccountBal(BigDecimal.ZERO);
                storeAccountDto.setAvaliableAmt(BigDecimal.ZERO);
                storeAccountDto.setWithdrawProcessAmt(BigDecimal.ZERO);
                storeAccountDto.setCcy(userPayeeAccountApplyDo.getCcy());
            } else {
                //查询账户余额
                UserAccountBalBo userAccountBalBo = userBalBiz.queryUserAccountBal(userNo, userPayeeAccountApplyDo.getAccountNo());
                log.info("查询账户，店铺：{},账户号：{}", storeNo, userPayeeAccountApplyDo.getAccountNo());
                UserPayeeAccountDo userPayeeAccountDo = userAccountManager.queryUserAccount(userNo, userPayeeAccountApplyDo.getAccountNo());
                storeAccountDto = StoreAccServiceConvert.paramConvert(userPayeeAccountDo, storeDo, userAccountBalBo);
            }
            result = new Result<>(storeAccountDto);
        } catch (Exception e) {
            log.error("global-service-查询店铺信息异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("global-service-查询店铺信息结果：{}", result);
        return result;
    }

    /**
     * 根据用户店铺号查询店铺信息
     * add by yangjian
     *
     * @param storeInfoModifyReqDto reqDto
     * @return 结果
     */
    @Override
    public Result<Boolean> modifyStoreInfo(StoreInfoModifyReqDto storeInfoModifyReqDto, String traceLogId) {

        Result<Boolean> result;
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        try {
            //用户选择已有店铺时校验用户卖家编号是否已经存在
            if (StoreExistEnum.YES.getCode().equals(storeInfoModifyReqDto.getStoreExist())) {
                userStoreManager.checkSellerIdIsExist(storeInfoModifyReqDto.getSellerId());
            }
            log.info("更新用戶店鋪信息，請求參數信息：{}", storeInfoModifyReqDto);
            userStoreManager.modifyUserStore(BeanCopyUtils.objectConvert(storeInfoModifyReqDto, UserStoreDo.class));

            result = new Result<>(Boolean.TRUE);
        } catch (Exception e) {
            log.error("更新用戶店鋪信息异常", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("更新用戶店鋪信息，返回結果：{}", result);
        return result;
    }
}
