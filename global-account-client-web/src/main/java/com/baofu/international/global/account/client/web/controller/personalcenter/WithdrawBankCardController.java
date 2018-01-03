package com.baofu.international.global.account.client.web.controller.personalcenter;

import com.baofu.international.global.account.client.common.assist.RedisManagerImpl;
import com.baofu.international.global.account.client.common.constant.CommonDict;
import com.baofu.international.global.account.client.common.constant.NumberDict;
import com.baofu.international.global.account.client.common.constant.NumberStrDict;
import com.baofu.international.global.account.client.common.constant.PageUrlDict;
import com.baofu.international.global.account.client.common.constant.RequestDict;
import com.baofu.international.global.account.client.common.enums.UserTypeEnum;
import com.baofu.international.global.account.client.service.RealNameAuthService;
import com.baofu.international.global.account.client.service.WithdrawBankCardService;
import com.baofu.international.global.account.client.web.models.AjaxResult;
import com.baofu.international.global.account.client.web.models.BaseResult;
import com.baofu.international.global.account.client.web.models.SessionVo;
import com.baofu.international.global.account.client.web.util.SessionUtil;
import com.baofu.international.global.account.core.facade.SendVerifyCodeFacade;
import com.baofu.international.global.account.core.facade.UserBankCardFacade;
import com.baofu.international.global.account.core.facade.UserOrgFacade;
import com.baofu.international.global.account.core.facade.UserPersonalFacade;
import com.baofu.international.global.account.core.facade.model.AddCompanyBankCardApplyDto;
import com.baofu.international.global.account.core.facade.model.AddPersonalBankCardApplyDto;
import com.baofu.international.global.account.core.facade.model.SendCodeRespDto;
import com.baofu.international.global.account.core.facade.model.TSysBankInfoDto;
import com.baofu.international.global.account.core.facade.model.user.OrgInfoRespDto;
import com.baofu.international.global.account.core.facade.model.user.UserBankCardInfoDto;
import com.baofu.international.global.account.core.facade.model.user.UserPersonalDto;
import com.google.common.collect.Maps;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 提现银行卡维护控制
 * <p/>
 *
 * @author lian zd
 * @date :2017/11/4 ProjectName: account-client Version:1.0
 */
@Slf4j
@Controller
@RequestMapping("withdrawBankCard/")
public class WithdrawBankCardController {

    /**
     * 银行卡管理业务接口
     */
    @Autowired
    private UserBankCardFacade userBankCardFacade;

    /**
     * 缓存服务
     */
    @Autowired
    private RedisManagerImpl redisManager;

    /**
     * 企业实名信息
     */
    @Autowired
    private UserOrgFacade userOrgFacade;

    /**
     * 个人用户实名信息
     */
    @Autowired
    private UserPersonalFacade userPersonalFacade;

    /**
     * 验证码服务facade
     */
    @Autowired
    private SendVerifyCodeFacade sendVerifyCodeFacade;

    /**
     * 实名认证服务
     */
    @Autowired
    private RealNameAuthService realNameAuthService;

    /**
     * 提现银行卡业务处理
     */
    @Autowired
    WithdrawBankCardService withdrawBankCardService;

    /**
     * 用户输入验证码
     */
    private static final String MESSAGE_CODE = "messageCode";

    /**
     * 用户类型
     */
    private static final String USER_TYPE = "userType";

    /**
     * 验证校验错误日志打印
     */
    private static final String MESSAGE_DODE_WRONG_LOG = "验证码不正确，实际验证码：{}，输入验证码：{}";

    /**
     * 验证校验错误提示给用户信息
     */
    private static final String MESSAGE_DODE_WRONG_WARN_TO_PAGE = "验证码输入不正确";


    /**
     * 提现银行卡管理首页
     *
     * @return 结果
     */
    @RequestMapping(value = "bankCardIndex.do", method = RequestMethod.GET)
    public ModelAndView bankCardIndex() {

        log.info("请求进入提现银行卡首页");
        ModelAndView mv = new ModelAndView(PageUrlDict.BANK_CARD_INDEX_PAGE);
        try {
            SessionVo sessionVo = SessionUtil.getSessionVo();
            Result<List<UserBankCardInfoDto>> result = userBankCardFacade.findUserBankCardInfo(sessionVo.getUserNo(),
                    MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("用户银行卡信息查询返回结果：{}", result);
            ResultUtil.handlerResult(result);
            String userInfoNo;
            Integer realNameStatus;
            if (UserTypeEnum.PERSONAL.getType() == sessionVo.getUserType()) {
                UserPersonalDto dto = realNameAuthService.queryUserPersonalByUserNo(sessionVo.getUserNo());
                userInfoNo = dto.getUserInfoNo().toString();
                realNameStatus = dto.getRealnameStatus();
            } else {
                OrgInfoRespDto dto = realNameAuthService.queryUserOrgByUserNo(sessionVo.getUserNo());
                userInfoNo = dto.getUserInfoNo().toString();
                realNameStatus = dto.getRealnameStatus();
            }
            if (realNameStatus.intValue() == NumberDict.TWO) {
                mv.addObject("page", result.getResult());
            }
            mv.addObject("userInfoNo", userInfoNo);
            mv.addObject(USER_TYPE, sessionVo.getUserType());
            mv.addObject(RequestDict.LOGIN_NO, sessionVo.getLoginNo());
            log.info("请求进入提现银行卡首页成功");
        } catch (Exception e) {
            log.error("进入提现银行卡首页失败：{}", e);
            mv.addObject("msg", CommonDict.ERROR_FLAG_RETURN);
        }
        mv.addObject("msg", CommonDict.SUCCESS_FLAG_RETURN);
        return mv;
    }

    /**
     * 请求进入添加个人用户银行卡首页
     *
     * @param mp 页面参数
     * @return 结果
     */
    @RequestMapping(value = "addBankCardPersonalIndex.do", method = RequestMethod.GET)
    public String addBankCardPersonalIndex(ModelMap mp) {

        SessionVo sessionVo = SessionUtil.getSessionVo();
        try {
            Result<List<UserBankCardInfoDto>> result = userBankCardFacade.findUserBankCardInfo(sessionVo.getUserNo(),
                    MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("用户银行卡信息查询返回结果：{}", result);
            ResultUtil.handlerResult(result);
            mp.addAttribute("page", result.getResult());
            Result<UserPersonalDto> byUserNo = userPersonalFacade.findByUserNo(sessionVo.getUserNo(), MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("call 查询用户实名认证状态返回结果：{}", byUserNo);
            ResultUtil.handlerResult(byUserNo);
            UserPersonalDto userPersonalDto = byUserNo.getResult();
            mp.addAttribute(RequestDict.LOGIN_NO, sessionVo.getLoginNo());
            mp.addAttribute("data", userPersonalDto);
            mp.addAttribute("name", userPersonalDto.getName());
            mp.addAttribute("currentPhoneNumber", userPersonalDto.getPhoneNumber());
            mp.addAttribute(USER_TYPE, sessionVo.getUserType());
        } catch (Exception e) {
            mp.addAttribute(RequestDict.LOGIN_NO, sessionVo.getLoginNo());
            mp.addAttribute(USER_TYPE, sessionVo.getUserType());
            log.error("call 跳转个人添加银行卡页面页面异常，异常信息：", e);
            return PageUrlDict.BANK_CARD_INDEX_PAGE;
        }
        return PageUrlDict.BANK_CARD_ADD_PERSONAL_INDEX_PAGE;
    }


    /**
     * 只添加个人银行卡方法
     *
     * @param userName    用户名
     * @param bankCardNo  银行卡
     * @param messageCode 验证码短信
     * @return 结果
     */
    @ResponseBody
    @RequestMapping(value = "addBankCardPersonal.do", method = RequestMethod.POST)
    public AjaxResult<String> addBankCardPersonal(String userName, String bankCardNo, String messageCode) {

        log.info("请求进入添加个人银行卡业务请求");
        AjaxResult<String> ajaxResult = new AjaxResult<>();
        try {
            SessionVo sessionVo = SessionUtil.getSessionVo();
            //验证码认证
            String messageCodeRel = redisManager.queryObjectByKey(CommonDict.BANKCARD_ADD_PERSONAL_BANKCARD.concat(sessionVo.getLoginNo()));
            if (StringUtils.isEmpty(messageCodeRel)) {
                log.info(CommonDict.MESSAGE_CODE_EXPIRE_OR_UNDEFINE);
                throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, CommonDict.MESSAGE_CODE_EXPIRE_OR_UNDEFINE);
            }
            boolean verifyCodeResult = verifyCode(CommonDict.BANKCARD_ADD_PERSONAL_BANKCARD, sessionVo.getLoginNo(), messageCode);
            if (!verifyCodeResult) {
                log.info("验证码有误，实际验证码：{}，输入验证码：{}", messageCodeRel.replaceAll(CommonDict.WRAP, ""),
                        messageCode.replaceAll(CommonDict.WRAP, ""));
                throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, MESSAGE_DODE_WRONG_WARN_TO_PAGE);
            }
            //添加银行卡请求
            AddPersonalBankCardApplyDto dto = new AddPersonalBankCardApplyDto();
            dto.setAccType(NumberStrDict.TWO);
            dto.setUserNo(String.valueOf(sessionVo.getUserNo()));
            dto.setCardHolder(userName);
            dto.setCardNo(bankCardNo);
            log.info("添加个人银行卡请求信息：{}", dto);
            Result<Boolean> result = userBankCardFacade.addPersonBankCard(dto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("添加个人银行卡返回结果：{}", result);
            ResultUtil.handlerResult(result);
            redisManager.deleteObject(CommonDict.BANKCARD_MESSAGE_CODE_SEND_YES + CommonDict.BANKCARD_ADD_PERSONAL_BANKCARD.concat(sessionVo.getLoginNo()));
            ajaxResult.setCode(0);
            ajaxResult.setMessage("添加银行卡成功");
        } catch (Exception e) {
            log.error("call 添加个人银行卡失败，跳转到个人银行卡首页列表，失败信息：{}", e);
            ajaxResult.setCode(1);
            ajaxResult.setMessage(ExceptionUtils.getErrorMsg(e));
        }
        log.info("添加银行卡结束，返回前端信息：{}", ajaxResult);
        return ajaxResult;
    }


    /**
     * 添加银行卡企业首页
     *
     * @return 结果
     */
    @RequestMapping(value = "addBankCardCompanyIndex.do", method = RequestMethod.GET)
    public String addBankCardCompanyIndex(ModelMap mp) {

        log.info("请求进入添加企业银行卡首页");
        SessionVo sessionVo = SessionUtil.getSessionVo();
        try {
            Result<List<UserBankCardInfoDto>> result = userBankCardFacade.findUserBankCardInfo(sessionVo.getUserNo(),
                    MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("用户银行卡信息查询返回结果：{}", result);
            ResultUtil.handlerResult(result);
            mp.addAttribute(RequestDict.LOGIN_NO, sessionVo.getLoginNo());
            mp.addAttribute("page", result.getResult());
            Result<OrgInfoRespDto> orgInfo = userOrgFacade.findByUserNo(sessionVo.getUserNo(), MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("用户企业机构信息查询返回结果：{}", orgInfo);
            ResultUtil.handlerResult(orgInfo);
            OrgInfoRespDto orgInfoRespDto = orgInfo.getResult();
            //查询系统支持发卡行类型
            Result<List<TSysBankInfoDto>> bankInfoResult = userBankCardFacade.
                    findSysBankInfo(sessionVo.getUserNo(), MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("用户查询系统支持银行卡类型信息返回结果：{}", bankInfoResult);
            ResultUtil.handlerResult(bankInfoResult);
            mp.addAttribute("sysBankPage", bankInfoResult.getResult());
            mp.addAttribute("companyName", orgInfoRespDto.getName());
            mp.addAttribute("name", orgInfoRespDto.getLegalName());
        } catch (Exception e) {
            mp.addAttribute(USER_TYPE, sessionVo.getUserType());
            log.error("call 跳转企业添加银行卡页面页面异常，异常信息：{}", e);
            return PageUrlDict.BANK_CARD_INDEX_PAGE;
        }
        return PageUrlDict.BANK_CARD_ADD_ENTERPRISE_INDEX_PAGE;
    }

    /**
     * 添加银行卡企业业务受理
     *
     * @param request 参数
     * @return 结果
     */
    @ResponseBody
    @RequestMapping(value = "addBankCardCompanyTP.do", method = RequestMethod.POST)
    public Map<String, String> addBankCardCompany(HttpServletRequest request) {

        log.info("进入添加企业对公银行账户受理");
        Map<String, String> re = Maps.newHashMap();
        try {
            SessionVo sessionVo = SessionUtil.getSessionVo();
            String messageCode = redisManager.queryObjectByKey(CommonDict.BANKCARD_ADD_COMPANY_BANKCARD.concat(sessionVo.getLoginNo()));
            if (StringUtils.isEmpty(messageCode)) {
                log.info(CommonDict.MESSAGE_CODE_EXPIRE_OR_UNDEFINE);
                throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, CommonDict.MESSAGE_CODE_EXPIRE_OR_UNDEFINE);
            }
            boolean verifyCodeResult = verifyCode(CommonDict.BANKCARD_ADD_COMPANY_BANKCARD, sessionVo.getLoginNo(), request.getParameter(MESSAGE_CODE));
            if (!verifyCodeResult) {
                log.info(MESSAGE_DODE_WRONG_LOG, messageCode.replaceAll(CommonDict.WRAP, ""),
                        request.getParameter(MESSAGE_CODE).replaceAll(CommonDict.WRAP, ""));
                throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, MESSAGE_DODE_WRONG_WARN_TO_PAGE);
            }
            //添加银行卡请求
            AddCompanyBankCardApplyDto dto = new AddCompanyBankCardApplyDto();
            dto.setAccType(1);
            dto.setUserNo(sessionVo.getUserNo());
            dto.setCardHolder(request.getParameter("name"));
            dto.setCardNo(request.getParameter("bankCardNo"));
            dto.setBankCode(request.getParameter("bankCode"));
            dto.setBankBranchName(request.getParameter("bankBranchName"));
            log.info("添加企业对公银行卡请求信息：{}", dto);
            Result<Boolean> result = userBankCardFacade.addCompanyPublicBankCard(dto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("添加企业对公银行卡返回结果：{}", result);
            ResultUtil.handlerResult(result);
            redisManager.deleteObject(CommonDict.BANKCARD_MESSAGE_CODE_SEND_YES + CommonDict.BANKCARD_ADD_COMPANY_BANKCARD + sessionVo.getLoginNo());
            re.put("msg", CommonDict.SUCCESS_FLAG_RETURN);
        } catch (Exception e) {
            log.error("添加企业对公银行卡失败", e);
            re.put("errorMsg", ExceptionUtils.getErrorMsg(e));
            re.put("msg", CommonDict.ERROR_FLAG_RETURN);
        }
        return re;
    }

    /**
     * 删除银行卡首页
     *
     * @param recordNo 编号
     * @param mp       模型
     * @return 结果
     */
    @RequestMapping(value = "deleteBankCardIndex.do", method = RequestMethod.GET)
    public String delBankCardIndex(String recordNo, ModelMap mp) {
        log.info("call 删除银行卡的编号为：{}", recordNo);
        SessionVo sessionVo;
        UserBankCardInfoDto userBankCardInfoDto = null;
        try {
            if (StringUtils.isBlank(recordNo)) {
                throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, "参数不合法");
            }
            sessionVo = SessionUtil.getSessionVo();
            Result<List<UserBankCardInfoDto>> result = userBankCardFacade.findUserBankCardInfo(sessionVo.getUserNo(),
                    MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            ResultUtil.handlerResult(result);
            for (UserBankCardInfoDto dto : result.getResult()) {
                if (String.valueOf(dto.getRecordNo()).equals(recordNo)) {
                    userBankCardInfoDto = dto;
                    break;
                }
            }
            mp.addAttribute("card", userBankCardInfoDto);
            mp.addAttribute(RequestDict.LOGIN_NO, sessionVo.getLoginNo());
        } catch (Exception e) {
            log.error("call 跳转删除银行卡页面失败,{}", e);
        }
        return PageUrlDict.BANK_CARD_REMOVE_INDEX_PAGE;
    }

    /**
     * 删除银行卡业务受理
     *
     * @param recordNo    银行卡编号
     * @param messageCode 短信
     * @return 结果
     */
    @ResponseBody
    @RequestMapping(value = "deleteBankCard.do", method = RequestMethod.POST)
    public AjaxResult<String> delBankCard(String recordNo, String messageCode) {

        log.info("进入删除银行卡业务处理");
        AjaxResult<String> ajaxResult = new AjaxResult<>();
        try {
            SessionVo sessionVo = SessionUtil.getSessionVo();
            //验证码认证
            String messageCodeRel = redisManager.queryObjectByKey(CommonDict.BANKCARD_REMOVE_BANKCARD.concat(sessionVo.getLoginNo()));
            if (StringUtils.isEmpty(messageCodeRel)) {
                log.info(CommonDict.MESSAGE_CODE_EXPIRE_OR_UNDEFINE);
                throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, CommonDict.MESSAGE_CODE_EXPIRE_OR_UNDEFINE);
            }
            boolean verifyCodeResult = verifyCode(CommonDict.BANKCARD_REMOVE_BANKCARD, sessionVo.getLoginNo(), messageCode);
            if (!verifyCodeResult) {
                log.info(MESSAGE_DODE_WRONG_LOG, messageCodeRel.replaceAll(CommonDict.WRAP, ""),
                        messageCode.replaceAll(CommonDict.WRAP, ""));
                throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, MESSAGE_DODE_WRONG_WARN_TO_PAGE);
            }
            //请求后台删除银行卡
            Result<Boolean> result3 = userBankCardFacade.deleteBankCard(sessionVo.getUserNo(), Long.valueOf(recordNo), MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("删除银行卡业务接口处理结果：{}", result3);
            ResultUtil.handlerResult(result3);
            if (!result3.getResult()) {
                log.info("删除银行卡业务接口core处理失败");
                throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, "删除银行卡业务接口core处理失败");
            }
            redisManager.deleteObject(CommonDict.BANKCARD_MESSAGE_CODE_SEND_YES + CommonDict.BANKCARD_REMOVE_BANKCARD + sessionVo.getLoginNo());
            ajaxResult.setCode(0);
            ajaxResult.setMessage("添加银行卡成功");
        } catch (Exception e) {
            log.error("删除银行卡处理失败", e);
            ajaxResult.setCode(1);
            ajaxResult.setMessage(ExceptionUtils.getErrorMsg(e));
        }
        return ajaxResult;
    }


    /**
     * 添加企业法人银行卡受理
     *
     * @param request 请求参数
     * @return 结果
     */
    @RequestMapping(value = "addBankCardCompanyPersonal.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> addBankCardCompanyPersonal(HttpServletRequest request) {

        log.info("请求进入添加企业法人银行业务处理");
        Map<String, String> re = Maps.newHashMap();
        try {
            SessionVo sessionVo = SessionUtil.getSessionVo();
            //验证码认证
            String messageCode = redisManager.queryObjectByKey(CommonDict.BANKCARD_ADD_PERSONAL_BANKCARD.concat(sessionVo.getLoginNo()));
            if (StringUtils.isEmpty(messageCode)) {
                log.info(CommonDict.MESSAGE_CODE_EXPIRE_OR_UNDEFINE);
                throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, CommonDict.MESSAGE_CODE_EXPIRE_OR_UNDEFINE);
            }
            boolean verifyCodeResult = verifyCode(CommonDict.BANKCARD_ADD_PERSONAL_BANKCARD, sessionVo.getLoginNo(), request.getParameter(MESSAGE_CODE));
            if (!verifyCodeResult) {
                log.info(MESSAGE_DODE_WRONG_LOG, messageCode.replaceAll(CommonDict.WRAP, ""),
                        request.getParameter(MESSAGE_CODE).replaceAll(CommonDict.WRAP, ""));
                throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, MESSAGE_DODE_WRONG_WARN_TO_PAGE);
            }
            //添加银行卡请求
            AddPersonalBankCardApplyDto dto = new AddPersonalBankCardApplyDto();
            dto.setAccType("2");
            dto.setUserNo(String.valueOf(sessionVo.getUserNo()));
            dto.setCardHolder(request.getParameter("name"));
            dto.setCardNo(request.getParameter("bankCardNo"));
            log.info("添加企业法人银行卡请求信息：{}", dto);
            Result<Boolean> result = userBankCardFacade.addPersonBankCard(dto, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("添加企业法人银行卡返回结果：{}", result);
            ResultUtil.handlerResult(result);
            redisManager.deleteObject(CommonDict.BANKCARD_MESSAGE_CODE_SEND_YES + CommonDict.BANKCARD_ADD_PERSONAL_BANKCARD + sessionVo.getLoginNo());
            re.put("msg", CommonDict.SUCCESS_FLAG_RETURN);
        } catch (Exception e) {
            log.error("添加企业法人银行卡失败", e);
            re.put("errorMsg", ExceptionUtils.getErrorMsg(e));
            re.put("msg", CommonDict.ERROR_FLAG_RETURN);
        }
        return re;
    }

    /**
     * 发送验证码
     *
     * @return boolean 发送成功
     */
    @ResponseBody
    @RequestMapping(value = "sendCode.do", method = RequestMethod.POST)
    public BaseResult sendCode(HttpServletRequest request) {
        BaseResult result;
        try {
            SessionVo sessionVo = SessionUtil.getSessionVo();
            log.info("[提现银行卡] 发送验证码: 参数:{}", sessionVo);
            String serviceType = request.getParameter("serviceType");
            String bankCardTailNo = request.getParameter("bankCardTailNo");
            Result<SendCodeRespDto> sendResult = withdrawBankCardService.bankCardAutoSendCode(serviceType, bankCardTailNo, sessionVo.getLoginNo());
            if (!sendResult.isSuccess() || !sendResult.getResult().getSendFlag()) {
                log.info("验证码发送失败，发送结果：{}", sendResult);
                throw new BizServiceException(CommonErrorCode.REMOTE_SERVICE_INVOKE_FAIL, "验证码发送失败");
            }
            result = BaseResult.setSuccess("成功");
        } catch (Exception e) {
            log.error("[提现银行卡] 发送验证码异常", e);
            result = BaseResult.setFail(ExceptionUtils.getErrorMsg(e));
        }
        log.info("[提现银行卡] 发送验证码,返回结果:{}", result);
        return result;
    }

    /**
     * 验证码校验
     *
     * @param serviceType 业务类型
     * @param loginNo     登录号
     * @param code        用户输入验证码
     * @return 验证结果
     */
    public boolean verifyCode(String serviceType, String loginNo, String code) {
        try {
            Result<Boolean> result = sendVerifyCodeFacade.checkCode(serviceType.concat(loginNo)
                    , code, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("验证码校验结果：{}", result);
            return result.isSuccess() && result.getResult();
        } catch (Exception e) {
            log.error("验证验证码异常：{}", e);
            return false;
        }
    }
}