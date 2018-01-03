package com.baofu.international.global.account.client.web.controller;

import com.baofu.international.global.account.client.common.constant.CommonDict;
import com.baofu.international.global.account.client.common.util.DataDesensUtils;
import com.baofu.international.global.account.client.service.WithdrawBankCardService;
import com.baofu.international.global.account.client.service.models.BankInfoDto;
import com.baofu.international.global.account.client.web.models.BaseModelAndView;
import com.baofu.international.global.account.client.web.models.BaseResult;
import com.baofu.international.global.account.client.web.models.SessionVo;
import com.baofu.international.global.account.client.web.util.PwdEncryptUtil;
import com.baofu.international.global.account.client.web.util.SessionUtil;
import com.baofu.international.global.account.core.facade.model.UserWithdrawDetailDto;
import com.baofu.international.global.account.core.facade.model.user.UserBankCardInfoDto;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 提现服务
 * <p>
 * 1.发送验证码
 * 2.添加银行卡
 * 3.异步刷新银行卡信息
 * 4.用户提现确认
 * 5.用户提现成功
 * </p>
 *
 * @author : 不良人
 * @version : 1.0.0
 * @date : 2017/11/5
 */
@Slf4j
@Controller
@RequestMapping("withdrawals/")
public class WithdrawController {

    /**
     * 成功
     */
    private static final String SUCCESS = "SUCCESS";

    /**
     * 提现成功页面
     */
    private static final String SUCCESS_PAGE = "withdrawals/withdrawalsSuccess";

    /**
     * 银行卡列表页面
     */
    private static final String BANK_CARD_LIST_PAGE = "withdrawals/addBankcard";

    /**
     * 银行卡管理业务接口
     */
    @Autowired
    private WithdrawBankCardService bankCardService;

    /**
     * 发送验证码
     *
     * @return boolean 发送成功
     */
    @ResponseBody
    @RequestMapping(value = "sendCode.do", method = RequestMethod.POST)
    public BaseResult sendCode() {
        BaseResult result;
        try {
            SessionVo sessionVo = SessionUtil.getSessionVo();
            log.info("[用户提现添加银行卡] 发送验证码: 参数:{}", sessionVo);
            //发送验证码
            bankCardService.autoSendCode(sessionVo.getLoginNo());
            result = BaseResult.setSuccess(SUCCESS);
        } catch (Exception e) {
            log.info("[用户提现添加银行卡] 发送验证码异常", e);
            result = BaseResult.setFail(ExceptionUtils.getErrorMsg(e));
        }

        log.info("[用户提现添加银行卡] 发送验证码,返回结果:{}", result);
        return result;
    }

    /**
     * 添加银行卡
     *
     * @return 返回
     */
    @ResponseBody
    @RequestMapping(value = "addBankCard.do", method = RequestMethod.POST)
    public BaseResult userWithdrawConfirmCheck(BankInfoDto bankInfoDto,
                                               @RequestParam(value = "validateCode") String validateCode) {

        log.info("[添加银行卡] 添加银行卡，请求参数:{}", bankInfoDto);
        BaseResult result;
        try {
            SessionVo user = SessionUtil.getSessionVo();
            //校验验证码
            bankCardService.verifyCode(String.valueOf(user.getLoginNo()), validateCode);
            //添加银行卡
            bankCardService.addBankCard(bankInfoDto, user.getUserNo());
            result = BaseResult.setSuccess(SUCCESS);
        } catch (Exception e) {
            log.error("[添加银行卡] 添加银行卡异常", e);
            result = BaseResult.setFail(ExceptionUtils.getErrorMsg(e));
        }

        log.info("[添加银行卡] 添加银行卡,返回结果:{}", result);
        return result;
    }

    /**
     * 异步刷新银行卡信息
     *
     * @return 返回
     */
    @RequestMapping(value = "findBankInfo.do", method = RequestMethod.POST)
    public ModelAndView getBankInfo() {

        BaseModelAndView view;
        try {
            SessionVo user = SessionUtil.getSessionVo();
            List<UserBankCardInfoDto> bankInfoDtoList = bankCardService.getBankInfoList(user.getUserNo());
            view = BaseModelAndView.setSuccess(BANK_CARD_LIST_PAGE);
            for (UserBankCardInfoDto bankCardInfoDto : bankInfoDtoList) {
                bankCardInfoDto.setCardHolder(DataDesensUtils.dealSensAccName(bankCardInfoDto.getCardHolder()));
            }
            view.addObject("bankInfoDtoList", bankInfoDtoList);
        } catch (Exception e) {
            log.error("[异步刷新银行卡信息] 异常", e);
            view = BaseModelAndView.setFail("", ExceptionUtils.getErrorMsg(e));
        }

        log.info("[异步刷新银行卡信息] 返回结果:{}", view);
        return view;
    }

    /**
     * 用户提现确认
     *
     * @param payPwd            支付密码
     * @param confirmDtoListStr 用户提现确认请求参数信息
     * @return 提现结果
     */
    @ResponseBody
    @RequestMapping(value = "confirm.do", method = RequestMethod.POST)
    public BaseResult userWithdrawConfirm(@RequestParam(value = "confirmDtoListStr") String confirmDtoListStr,
                                          @RequestParam(value = "recordNo") String recordNo,
                                          @RequestParam(value = "payPwd") String payPwd) {

        log.info("[用户提现确认] 请求参数:{}", confirmDtoListStr.replaceAll(CommonDict.WRAP, ""));
        BaseResult result;
        try {
            if (StringUtils.isBlank(recordNo)) {
                throw new BizServiceException(CommonErrorCode.PARAMETER_VALID_NOT_PASS,
                        "请选择提现到账银行卡，如您还未绑定银行卡信息，请先绑定银行卡");
            }
            SessionVo user = SessionUtil.getSessionVo();
            List<UserWithdrawDetailDto> confirmDtoList = JsonUtil.toList(confirmDtoListStr, UserWithdrawDetailDto.class);
            payPwd = PwdEncryptUtil.paymentEncrypt(payPwd);
            bankCardService.userWithdrawConfirm(payPwd, confirmDtoList, user.getUserNo(), recordNo);
            result = BaseResult.setSuccess(SUCCESS);
        } catch (Exception e) {
            log.error("[用户提现确认] 用户提现异常", e);
            result = BaseResult.setFail(ExceptionUtils.getErrorMsg(e));
        }

        log.info("[用户提现确认] 用户提现确认,返回结果:{}", result);
        return result;
    }

    /**
     * 用户提现成功
     *
     * @return 提现结果
     */
    @ResponseBody
    @RequestMapping(value = "success.do", method = RequestMethod.GET)
    public ModelAndView userWithdrawConfirm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(SUCCESS_PAGE);
        return modelAndView;
    }
}
