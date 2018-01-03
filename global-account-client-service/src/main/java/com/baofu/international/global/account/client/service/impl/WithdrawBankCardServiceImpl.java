package com.baofu.international.global.account.client.service.impl;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.client.common.assist.RedisManagerImpl;
import com.baofu.international.global.account.client.common.constant.CommonDict;
import com.baofu.international.global.account.client.common.constant.NumberDict;
import com.baofu.international.global.account.client.common.constant.NumberStrDict;
import com.baofu.international.global.account.client.common.constant.RedisDict;
import com.baofu.international.global.account.client.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.client.common.enums.SendCodeContentEnum;
import com.baofu.international.global.account.client.service.WithdrawBankCardService;
import com.baofu.international.global.account.client.service.models.BankInfoDto;
import com.baofu.international.global.account.core.facade.SendVerifyCodeFacade;
import com.baofu.international.global.account.core.facade.UserBankCardFacade;
import com.baofu.international.global.account.core.facade.UserPwdFacade;
import com.baofu.international.global.account.core.facade.UserWithdrawFacade;
import com.baofu.international.global.account.core.facade.model.*;
import com.baofu.international.global.account.core.facade.model.user.UserBankCardInfoDto;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import com.system.commons.result.Result;
import com.system.commons.utils.BeanCopyUtil;
import com.system.commons.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 提现服务
 * <p>
 * 1.添加银行卡信息
 * 2.查询用户银行卡信息
 * 3.发送验证码
 * 4.校验验证码
 * 7.确认提现
 * 8.查询开户行列表
 * </p>
 *
 * @author : 不良人
 * @version : 1.0.0
 * @date : 2017/11/5
 */
@Slf4j
@Service
public class WithdrawBankCardServiceImpl implements WithdrawBankCardService {

    /**
     * 银行卡管理业务接口
     */
    @Autowired
    private UserBankCardFacade userBankCardFacade;

    /**
     * 用户客户信息服务facade
     */
    @Autowired
    private UserPwdFacade userPwdFacade;

    /**
     * 验证码服务facade
     */
    @Autowired
    private SendVerifyCodeFacade sendVerifyCodeFacade;

    /**
     * 用户自主注册平台提现
     */
    @Autowired
    private UserWithdrawFacade userWithdrawFacade;

    /**
     * 缓存服务
     */
    @Autowired
    private RedisManagerImpl redisManager;

    /**
     * 添加银行卡信息
     *
     * @param bankInfoDto 添加银行卡请求参数
     * @param userNo      用户号
     */
    @Override
    public Boolean addBankCard(BankInfoDto bankInfoDto, Long userNo) {

        Result<Boolean> result;
        if (bankInfoDto.getAccType() == NumberDict.THREE) {
            AddCompanyBankCardApplyDto dto = new AddCompanyBankCardApplyDto();
            dto.setUserNo(userNo);
            dto.setAccType(NumberDict.ONE);
            dto.setBankCode(bankInfoDto.getBankCode());
            dto.setCardHolder(bankInfoDto.getName());
            dto.setCardNo(bankInfoDto.getBankCardNo());
            dto.setBankBranchName(bankInfoDto.getBranchBank());
            log.info("[添加银行卡] 添加企业对公银行卡接口,请求参数：{}", dto);
            result = userBankCardFacade.addCompanyPublicBankCard(dto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("[添加银行卡] 添加企业对公银行卡接口,返回参数：{}", result);
        } else {
            AddPersonalBankCardApplyDto dto = new AddPersonalBankCardApplyDto();
            dto.setAccType(NumberStrDict.TWO);
            dto.setCardNo(bankInfoDto.getBankCardNo());
            dto.setCardHolder(bankInfoDto.getName());
            dto.setUserNo(String.valueOf(userNo));
            log.info("[添加银行卡] 添加个人银行卡接口,请求参数：{}", dto);
            result = userBankCardFacade.addPersonBankCard(dto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("[添加银行卡] 添加个人银行卡接口,返回参数：{}", result);
        }
        ResultUtil.handlerResult(result);
        return result.getResult();
    }

    /**
     * 查询用户银行卡信息
     *
     * @param userNo 用户号
     */
    @Override
    public List<UserBankCardInfoDto> getBankInfoList(Long userNo) {

        log.info("[查询用户银行卡信息] 查询用户银行卡信息,请求参数：{}", userNo);
        Result<List<UserBankCardInfoDto>> result = userBankCardFacade.findUserBankCardInfo(userNo,
                MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("[查询用户银行卡信息] 查询用户银行卡信息,返回参数：{}", result);
        ResultUtil.handlerResult(result);
        return result.getResult();
    }

    /**
     * 发送验证码
     *
     * @param loginNo 登录号
     * @return 处理标识
     */
    @Override
    public SendCodeRespDto autoSendCode(String loginNo) {

        //发送验证码
        SendCodeReqDto sendCodeReqDto = new SendCodeReqDto();
        sendCodeReqDto.setParam(loginNo);
        sendCodeReqDto.setContent("验证码：#code#,您正在宝付国际跨境收款平台新增提现银行卡，请勿将验证码告诉他人！客服热线：021-68819999-8636");
        sendCodeReqDto.setTimeOut(NumberDict.TEN_MINUTE);
        sendCodeReqDto.setEmailTitle("宝付国际跨境收款-新增提现银行卡");
        sendCodeReqDto.setRedisKey(RedisDict.ACCOUNT_BANK_KEY.concat(loginNo));
        log.info("[添加银行卡] 发送验证码，请求参数：{}", sendCodeReqDto);
        Result<SendCodeRespDto> result = sendVerifyCodeFacade.autoSendCode(sendCodeReqDto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("[添加银行卡] 发送验证码，返回参数参数：{}", result);
        ResultUtil.handlerResult(result);
        SendCodeRespDto sendCodeRespDto = result.getResult();
        if (!sendCodeRespDto.getSendFlag()) {
            log.info("[添加银行卡] 发送验证码失败：{}", sendCodeRespDto.getErrorMsg());
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_800003, sendCodeRespDto.getErrorMsg());
        }
        return sendCodeRespDto;
    }

    /**
     * 校验验证码
     *
     * @param loginNo 用户号
     * @param code    验证码
     */
    @Override
    public void verifyCode(String loginNo, String code) {

        Result<Boolean> result = sendVerifyCodeFacade.checkCode(RedisDict.ACCOUNT_BANK_KEY.concat(loginNo), code,
                MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        ResultUtil.handlerResult(result);
    }

    /**
     * 提现
     *
     * @param payPwd         支付密码
     * @param confirmDtoList 提现信息
     */
    @Override
    public void userWithdrawConfirm(String payPwd, List<UserWithdrawDetailDto> confirmDtoList, Long userNo, String recordNo) {

        //验证支付密码
        Result<Boolean> pwdResult = userPwdFacade.checkPayPwd(payPwd, userNo, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        ResultUtil.handlerResult(pwdResult);
        //提现
        UserWithdrawDto userWithdrawDto = new UserWithdrawDto();
        userWithdrawDto.setUserNo(userNo);
        userWithdrawDto.setCreateBy(String.valueOf(userNo));
        userWithdrawDto.setBankCardRecordNo(Long.valueOf(recordNo));
        List<UserWithdrawDetailDto> detailDtoList = BeanCopyUtil.convertList(confirmDtoList, UserWithdrawDetailDto.class);
        userWithdrawDto.setUserWithdrawDetailDtoList(detailDtoList);
        log.info("[提现确认] 请求参数：{}", userWithdrawDto);
        Result<Boolean> result = userWithdrawFacade.userWithdrawCash(userWithdrawDto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("[提现确认] 返回参数：{}", result);
        ResultUtil.handlerResult(result);
    }

    /**
     * 查询开户行列表
     *
     * @param userNo 用户编号
     * @return 开户行列表
     */
    @Override
    public List<TSysBankInfoDto> findSysBankInfo(Long userNo) {

        //查询系统支持发卡行类型
        log.info("[提现确认] 请求参数：{}", userNo);
        Result<List<TSysBankInfoDto>> result = userBankCardFacade.findSysBankInfo(userNo,
                MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("[提现确认] 返回参数：{}", result);
        ResultUtil.handlerResult(result);
        return result.getResult();
    }

    /**
     * 提现银行卡解绑卡发送验证码
     *
     * @param serviceType    业务类型
     * @param bankCardTailNo 银行卡尾号
     * @param loginNo        登录号
     * @return 处理标识
     */
    @Override
    public Result<SendCodeRespDto> bankCardAutoSendCode(String serviceType, String bankCardTailNo, String loginNo) {

        String content;
        String emailTitle;
        if (CommonDict.BANKCARD_ADD_COMPANY_BANKCARD.equals(serviceType) || CommonDict.BANKCARD_ADD_PERSONAL_BANKCARD.equals(serviceType)) {
            //提现银行卡绑定
            content = SendCodeContentEnum.ADD_BANK_CARD.getMsgContent();
            emailTitle = SendCodeContentEnum.ADD_BANK_CARD.getTitle();
        } else if (CommonDict.BANKCARD_REMOVE_BANKCARD.equals(serviceType)) {
            //提现银行卡解绑
            content = SendCodeContentEnum.REMOVE_BANK_CARD.getMsgContent().replaceFirst(CommonDict.BANK_CARD_TAIL_NO_REPLACE, bankCardTailNo);
            emailTitle = SendCodeContentEnum.REMOVE_BANK_CARD.getTitle();
        } else {
            log.error("提现银行卡卡管理发送验证码业务类型非法：{}", serviceType);
            throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, "提现银行卡管理业务类型非法");
        }
        String messageCode = redisManager.queryObjectByKey(CommonDict.BANKCARD_MESSAGE_CODE_SEND_YES + serviceType + loginNo);
        if (!StringUtils.isEmpty(messageCode)) {
            throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, "60秒内仅能获取一次验证码，请稍后重试");
        }
        redisManager.insertObject(CommonDict.SUCCESS_FLAG_RETURN, CommonDict.BANKCARD_MESSAGE_CODE_SEND_YES +
                serviceType + loginNo, NumberDict.ONE_MINUTE);
        //发送验证码
        SendCodeReqDto sendCodeReqDto = new SendCodeReqDto();
        sendCodeReqDto.setParam(loginNo);
        sendCodeReqDto.setContent(content);
        sendCodeReqDto.setTimeOut(NumberDict.TEN_MINUTE);
        sendCodeReqDto.setRedisKey(serviceType.concat(loginNo));
        sendCodeReqDto.setEmailTitle(emailTitle);
        log.info("[提现银行卡] 发送验证码，请求参数：{}", sendCodeReqDto);
        Result<SendCodeRespDto> result1 = sendVerifyCodeFacade.autoSendCode(sendCodeReqDto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("[提现银行卡] 发送验证码，返回参数参数：{}", result1);
        return result1;
    }


}
