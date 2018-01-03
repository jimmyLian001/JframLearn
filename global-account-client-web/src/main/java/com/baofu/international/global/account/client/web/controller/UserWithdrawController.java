package com.baofu.international.global.account.client.web.controller;

import com.baofu.international.global.account.client.common.constant.PageUrlDict;
import com.baofu.international.global.account.client.common.constant.RequestDict;
import com.baofu.international.global.account.client.common.enums.CcySymbolEnum;
import com.baofu.international.global.account.client.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.client.common.enums.RealNameStatusEnum;
import com.baofu.international.global.account.client.common.enums.UserTypeEnum;
import com.baofu.international.global.account.client.service.RealNameAuthService;
import com.baofu.international.global.account.client.service.SecurityConfigService;
import com.baofu.international.global.account.client.service.UserWithdrawService;
import com.baofu.international.global.account.client.service.WithdrawBankCardService;
import com.baofu.international.global.account.client.service.models.UserInfoBo;
import com.baofu.international.global.account.client.service.models.UserWithdrawReqDto;
import com.baofu.international.global.account.client.web.models.AjaxResult;
import com.baofu.international.global.account.client.web.models.SessionVo;
import com.baofu.international.global.account.client.web.models.UserWithdrawReqVo;
import com.baofu.international.global.account.client.web.models.WithdrawCashFeeRespVo;
import com.baofu.international.global.account.client.web.util.SessionUtil;
import com.baofu.international.global.account.core.facade.model.TSysBankInfoDto;
import com.baofu.international.global.account.core.facade.model.res.WithdrawAccountRespDto;
import com.baofu.international.global.account.core.facade.model.res.WithdrawCashFeeRespDto;
import com.google.common.collect.Lists;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import com.system.commons.utils.BeanCopyUtil;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户提现
 * <p>
 * 1、方法描述
 * </p>
 * User: feng_jiang  Date: 2017/11/6 ProjectName:account-client  Version: 1.0
 */
@Slf4j
@Controller
@RequestMapping("withdraw/")
public class UserWithdrawController {

    /**
     * 用户提现服务
     */
    @Autowired
    private UserWithdrawService userWithdrawService;

    /**
     * 银行卡管理业务接口
     */
    @Autowired
    private WithdrawBankCardService bankCardService;

    /**
     * 安全设置service
     */
    @Autowired
    private SecurityConfigService securityConfigService;

    /**
     * 实名认证服务
     */
    @Autowired
    private RealNameAuthService realNameAuthService;

    /**
     * 用户提现请求
     *
     * @return 返回页面和参数信息
     */
    @RequestMapping(value = "apply", method = RequestMethod.GET)
    public ModelAndView userWithdraw() {
        ModelAndView modelAndView = new ModelAndView(PageUrlDict.USER_WITHDRAW_APPLY);
        try {
            SessionVo user = SessionUtil.getSessionVo();
            //查询用户店铺账户信息
            List<WithdrawAccountRespDto> userWithdrawAccountList = userWithdrawService.queryWithdrawAccountInfo(user.getUserNo());
            modelAndView.addObject(RequestDict.USER_STORE_ACCOUNT, userWithdrawAccountList);
            modelAndView.addObject(RequestDict.USER_WITHDRAW_STORE_LIST, new ArrayList<UserWithdrawReqVo>());
        } catch (Exception e) {
            log.info("用户提现请求页面异常:", e);
            modelAndView.setViewName(PageUrlDict.ERROR_PAGE);
        }
        return modelAndView;
    }

    /**
     * 用户提现请求
     *
     * @return 返回页面和参数信息
     */
    @RequestMapping(value = "withdrawFee", method = RequestMethod.POST)
    public ModelAndView withdrawCashFee(UserWithdrawReqVo userWithdrawReqVo) {
        ModelAndView modelAndView = new ModelAndView(PageUrlDict.WITHDRAW_APPLY_RESULT);
        BigDecimal totalAmount = BigDecimal.ZERO;
        try {
            SessionVo user = SessionUtil.getSessionVo();
            String[] storeArray = userWithdrawReqVo.getStoreNo().split(",");
            String[] withdrawCcyArray = userWithdrawReqVo.getWithdrawCcy().split(",");
            String[] withdrawAmtArray = userWithdrawReqVo.getWithdrawAmt().split(",");
            List<UserWithdrawReqDto> list = Lists.newArrayList();
            UserWithdrawReqDto reqDto;
            for (int i = 0; i < storeArray.length; i++) {
                reqDto = new UserWithdrawReqDto();
                reqDto.setStoreNo(Long.valueOf(storeArray[i]));
                reqDto.setWithdrawCcy(withdrawCcyArray[i]);
                reqDto.setWithdrawAmt(new BigDecimal(withdrawAmtArray[i]));
                list.add(reqDto);
            }
            //计算用户提现手续费
            List<WithdrawCashFeeRespDto> cashFeeRespDtoList = userWithdrawService.withdrawCashFee(user.getUserNo(), list);
            List<WithdrawCashFeeRespVo> feeRespVos = Lists.newArrayList();
            for (WithdrawCashFeeRespDto feeRespDto : cashFeeRespDtoList) {
                totalAmount = totalAmount.add(feeRespDto.getDestAmt());
                WithdrawCashFeeRespVo feeRespVo = BeanCopyUtil.objConvert(feeRespDto, WithdrawCashFeeRespVo.class);
                feeRespVo.setCcySymbol(CcySymbolEnum.getSymbol(feeRespVo.getWithdrawCcy()));
                feeRespVos.add(feeRespVo);
            }
            List<TSysBankInfoDto> bankInfoDtoList = bankCardService.findSysBankInfo(user.getUserNo());
            UserInfoBo userInfoBo = securityConfigService.getUserInfo(String.valueOf(user.getLoginNo()));
            modelAndView.addObject("cashTrialList", feeRespVos);
            modelAndView.addObject("cashTrialListStr", JsonUtil.toJSONString(cashFeeRespDtoList));
            modelAndView.addObject("paramList", JsonUtil.toJSONString(list));
            modelAndView.addObject("totalAmount", totalAmount);
            modelAndView.addObject("accountNum", cashFeeRespDtoList.size());
            modelAndView.addObject("userType", user.getUserType());
            modelAndView.addObject("name", userInfoBo.getName());
            modelAndView.addObject("orgName", userInfoBo.getOrgName());
            modelAndView.addObject("bankInfoDtoList", bankInfoDtoList);

        } catch (Exception e) {
            log.info("用户提请求页面异常:", e);
            modelAndView.setViewName(PageUrlDict.ERROR_PAGE);
        }
        return modelAndView;
    }

    /**
     * 用户是否可提现、是否设置支付密码查询
     *
     * @return 返回查询结果
     */
    @ResponseBody
    @RequestMapping(value = "userWithdrawStatusQuery", method = RequestMethod.POST)
    public AjaxResult<Map<String, Integer>> userWithdrawStatusQuery() {
        AjaxResult<Map<String, Integer>> ajaxResult = new AjaxResult<>();
        int retCode;
        try {
            SessionVo sessionVo = SessionUtil.getSessionVo();
            //认证状态
            Integer realNameStatus;
            //查询是否实名认证状态
            if (UserTypeEnum.PERSONAL.getType() == sessionVo.getUserType()) {
                realNameStatus = realNameAuthService.queryUserPersonalByUserNo(sessionVo.getUserNo()).getRealnameStatus();
            } else {
                realNameStatus = realNameAuthService.queryUserOrgByUserNo(sessionVo.getUserNo()).getRealnameStatus();
            }
            log.info("用户号：{},实名认证状态：{}", sessionVo.getUserNo(), realNameStatus);
            if (realNameStatus == RealNameStatusEnum.NOT.getState()) {
                throw new BizServiceException(CommonErrorCode.PARAMETER_VALID_NOT_PASS, "您还未实名认证，请先实名认证通过之后再发起提现");
            }
            if (realNameStatus == RealNameStatusEnum.WAIT.getState()) {
                throw new BizServiceException(CommonErrorCode.PARAMETER_VALID_NOT_PASS, "您正在实名认证中，请等待实名认证成功之后方可提现");
            }
            List<WithdrawAccountRespDto> userWithdrawAccountList = userWithdrawService.queryWithdrawAccountInfo(sessionVo.getUserNo());
            if (!CollectionUtils.isEmpty(userWithdrawAccountList)) {
                retCode = securityConfigService.payPwdExist(sessionVo.getUserNo()) ? 1 : 3;
            } else {
                retCode = 2;
            }
            ajaxResult.setCode(retCode);
        } catch (Exception e) {
            ajaxResult.setCode(0);
            ajaxResult.setMessage(ExceptionUtils.getErrorMsg(e));
            log.error("用户是否可提现、是否设置支付密码查询异常：", e);
        }

        log.info("用户是否可提现、是否设置支付密码查询返回结果：{}", ajaxResult);
        return ajaxResult;
    }

    /**
     * 用户提现请求
     *
     * @return 返回页面和参数信息
     */
    @RequestMapping(value = "backApply", method = RequestMethod.GET)
    public ModelAndView userWithdrawBack(@RequestParam(value = "userWithdrawStoreStr") String userWithdrawStoreStr) {
        ModelAndView modelAndView = new ModelAndView(PageUrlDict.USER_WITHDRAW_APPLY);
        try {
            SessionVo user = SessionUtil.getSessionVo();
            //记录返回信息
            List<UserWithdrawReqVo> userWithdrawStoreList = JsonUtil.toList(userWithdrawStoreStr, UserWithdrawReqVo.class);
            //查询用户店铺账户信息
            List<WithdrawAccountRespDto> userWithdrawAccountList = userWithdrawService.queryWithdrawAccountInfo(user.getUserNo());
            modelAndView.addObject(RequestDict.USER_STORE_ACCOUNT, userWithdrawAccountList);
            modelAndView.addObject(RequestDict.USER_WITHDRAW_STORE_LIST, userWithdrawStoreList);
        } catch (Exception e) {
            log.info("用户提现请求页面异常:", e);
            modelAndView.setViewName(PageUrlDict.ERROR_PAGE);
        }
        return modelAndView;
    }
}