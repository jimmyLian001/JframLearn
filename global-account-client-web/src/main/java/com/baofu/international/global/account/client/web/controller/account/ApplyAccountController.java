package com.baofu.international.global.account.client.web.controller.account;

import com.baofu.international.global.account.client.common.constant.CommonDict;
import com.baofu.international.global.account.client.common.constant.NumberDict;
import com.baofu.international.global.account.client.common.constant.PageUrlDict;
import com.baofu.international.global.account.client.common.constant.RequestDict;
import com.baofu.international.global.account.client.common.enums.PlatformEnum;
import com.baofu.international.global.account.client.common.util.BeanCopyUtils;
import com.baofu.international.global.account.client.service.AccountService;
import com.baofu.international.global.account.client.service.ManagerCategoryInfoService;
import com.baofu.international.global.account.client.service.UserStoreAccountService;
import com.baofu.international.global.account.client.service.impl.ApplyAccountInfoServiceImpl;
import com.baofu.international.global.account.client.web.models.AjaxResult;
import com.baofu.international.global.account.client.web.models.ApplyAccountReqVo;
import com.baofu.international.global.account.client.web.models.ModifyStoreReqVo;
import com.baofu.international.global.account.client.web.models.SessionVo;
import com.baofu.international.global.account.client.web.util.SessionUtil;
import com.baofu.international.global.account.core.facade.model.ApplyAccountRepDto;
import com.baofu.international.global.account.core.facade.model.ApplyAccountReqDto;
import com.baofu.international.global.account.core.facade.model.ManagerCategoryRespDto;
import com.baofu.international.global.account.core.facade.model.StoreInfoModifyReqDto;
import com.baofu.international.global.account.core.facade.model.res.StoreAccountInfoRepDto;
import com.baofu.international.global.obtain.facade.models.UserStoreCheckReqDto;
import com.system.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * <p>
 * 申请境外收款账户
 * </p>
 * User: yangjian  Date: 2017-11-04 ProjectName:  Version: 1.0
 */
@Slf4j
@Controller
@RequestMapping("/account/")
public class ApplyAccountController {

    /**
     *
     */
    @Autowired
    private ApplyAccountInfoServiceImpl applyAccountInfoService;

    /**
     * 用户店铺信息
     */
    @Autowired
    private UserStoreAccountService userStoreAccountService;

    /**
     * 经营范围
     */
    @Autowired
    private ManagerCategoryInfoService managerCategoryInfoService;


    /**
     * 账户服务
     */
    @Autowired
    private AccountService accountService;

    /**
     * 申请开通收款账户页面
     */
    private static final String APPLY_PAGES = "account/applyAccount";

    /**
     * 用户店铺信息补充
     */
    private static final String MODIFY_STORE = "account/modifyStoreInfo";


    /**
     * 申请成功界面
     */
    private static final String MODIFY_STORE_SUCCESS = "account/modify-store-success";

    /**
     * 申请成功界面
     */
    private static final String APPLY_SUCCESS = "account/apply-success";

    /**
     * 申请开户客户信息展示
     *
     * @return 返回JSP页面和响应参数信息
     */
    @RequestMapping(value = "/applyAccPage", method = RequestMethod.GET)
    public ModelAndView applyAccount() {

        ModelAndView modelAndView = new ModelAndView(APPLY_PAGES);
        try {
            SessionVo sessionVo = SessionUtil.getSessionVo();
            //查询用户主体
            List<ApplyAccountRepDto> applyAccountInfo = applyAccountInfoService.getApplyAccountInfo(
                    sessionVo.getUserNo(), sessionVo.getUserType(), null);
            //查询经营范围
            List<ManagerCategoryRespDto> managerCategoryRespDtoList = managerCategoryInfoService.queryManagementCategory(null);
            modelAndView.addObject("accQualifiedList", applyAccountInfo);
            modelAndView.addObject("categoryList", managerCategoryRespDtoList);
            modelAndView.addObject(RequestDict.LOGIN_NO, sessionVo.getLoginNo());
            modelAndView.addObject("userType", sessionVo.getUserType());
        } catch (Exception e) {
            log.error("global-账户申请初始页面-异常：", e);
            modelAndView = new ModelAndView(PageUrlDict.ERROR_PAGE);
        }
        return modelAndView;
    }

    /**
     * 更新用户店铺信息页面
     *
     * @param storeNo 店铺号
     * @return 返回JSP页面和响应参数信息
     */
    @RequestMapping(value = "/{storeNo}/modifyStorePage", method = RequestMethod.GET)
    public ModelAndView modifyStorePage(@PathVariable("storeNo") String storeNo) {

        ModelAndView modelAndView = new ModelAndView(MODIFY_STORE);
        try {
            SessionVo sessionVo = SessionUtil.getSessionVo();
            log.info("更新用户店铺信息页面:用户号：{},店铺号：{}", sessionVo.getUserNo(), storeNo);
            StoreAccountInfoRepDto storeAccountInfoRepDto = accountService.queryStoreAccountInfo(sessionVo.getUserNo(),
                    sessionVo.getUserType(), Long.valueOf(storeNo));
            modelAndView.addObject("accDetail", storeAccountInfoRepDto);
            //查询用户主体
            List<ApplyAccountRepDto> applyAccountInfo = applyAccountInfoService.getApplyAccountInfo(
                    sessionVo.getUserNo(), sessionVo.getUserType(), storeAccountInfoRepDto.getQualifiedNo());
            //查询经营范围
            List<ManagerCategoryRespDto> managerCategoryRespDtoList = managerCategoryInfoService.queryManagementCategory(null);
            modelAndView.addObject("accQualifiedList", applyAccountInfo);
            modelAndView.addObject("categoryList", managerCategoryRespDtoList);
        } catch (Exception e) {
            log.error("global-更新用户店铺信息页面-异常：", e);
            modelAndView = new ModelAndView(PageUrlDict.ERROR_PAGE);
        }
        return modelAndView;
    }


    /**
     * 用户店铺信息补充
     *
     * @param modifyStoreReqVo 收款账户申请开通请求参数信息
     * @return Ajax请求返回结果
     */
    @ResponseBody
    @RequestMapping(value = "modifyStore", method = RequestMethod.POST)
    public AjaxResult<String> modifyStore(ModifyStoreReqVo modifyStoreReqVo) {

        AjaxResult<String> ajaxResult = new AjaxResult<>();
        try {
            log.info("用户店铺信息补充页面请求参数信息：{}", modifyStoreReqVo);

            SessionVo sessionVo = SessionUtil.getSessionVo();
            UserStoreCheckReqDto userStoreCheckReqDto = BeanCopyUtils.objectConvert(modifyStoreReqVo, UserStoreCheckReqDto.class);
            userStoreCheckReqDto.setPlatformNo(CommonDict.AMAZON);
            userStoreCheckReqDto.setCountry(modifyStoreReqVo.getSiteId());
            //用户店铺信息校验
            userStoreAccountService.checkUserSellerInfo(userStoreCheckReqDto);

            StoreInfoModifyReqDto storeInfoModifyReqDto = BeanCopyUtils.objectConvert(modifyStoreReqVo, StoreInfoModifyReqDto.class);
            storeInfoModifyReqDto.setUserNo(sessionVo.getUserNo());
            storeInfoModifyReqDto.setStoreExist(CommonDict.EXIST);
            storeInfoModifyReqDto.setAwsAccessKey(modifyStoreReqVo.getAccessKey());
            accountService.modifyUserStore(storeInfoModifyReqDto);

            ajaxResult.setCode(NumberDict.ZERO);
        } catch (Exception e) {
            log.error("global-用户店铺信息补充-异常：", e);
            ajaxResult.setCode(1);
            ajaxResult.setMessage(ExceptionUtils.getErrorMsg(e));
        }
        log.info("用户店铺信息补充Ajax返回结果:{}", ajaxResult);
        return ajaxResult;
    }

    /**
     * 申请创建账户controller
     *
     * @param applyAccountReqVo 收款账户申请开通请求参数信息
     * @return Ajax请求返回结果
     */
    @ResponseBody
    @RequestMapping(value = "applyAccount", method = RequestMethod.POST)
    public AjaxResult<String> applyAccountResult(ApplyAccountReqVo applyAccountReqVo) {

        AjaxResult<String> ajaxResult = new AjaxResult<>();
        try {
            log.info("收款账户申请开通页面请求参数信息：{}", applyAccountReqVo);
            SessionVo sessionVo = SessionUtil.getSessionVo();
            ApplyAccountReqDto applyAccountReqDto = BeanCopyUtils.objectConvert(applyAccountReqVo, ApplyAccountReqDto.class);
            PlatformEnum platformEnum = PlatformEnum.getPlatformNameByCountry(applyAccountReqVo.getSiteId());
            applyAccountReqDto.setCcy(platformEnum.getCcy());
            applyAccountReqDto.setStorePlatform(platformEnum.getPlatformCode());
            applyAccountReqDto.setAwsAccessKey(applyAccountReqVo.getAccessKey());
            applyAccountReqDto.setUserNo(sessionVo.getUserNo());
            applyAccountReqDto.setUserType(sessionVo.getUserType());
            if (CommonDict.EXIST.equals(applyAccountReqVo.getStoreExist())) {
                UserStoreCheckReqDto userStoreCheckReqDto = BeanCopyUtils.objectConvert(applyAccountReqVo, UserStoreCheckReqDto.class);
                userStoreCheckReqDto.setPlatformNo(CommonDict.AMAZON);
                userStoreCheckReqDto.setCountry(applyAccountReqVo.getSiteId());
                //用户店铺信息校验
                userStoreAccountService.checkUserSellerInfo(userStoreCheckReqDto);
            } else {
                applyAccountReqDto.setSecretKey(CommonDict.EMPTY_STR);
                applyAccountReqDto.setSellerId(CommonDict.EMPTY_STR);
                applyAccountReqDto.setAwsAccessKey(CommonDict.EMPTY_STR);
            }
            //用户申请账户信息添加
            applyAccountInfoService.applyAccountCreate(applyAccountReqDto);
            ajaxResult.setCode(NumberDict.ZERO);
        } catch (Exception e) {
            log.error("global-申请创建账户-异常：", e);
            ajaxResult.setCode(1);
            ajaxResult.setMessage(ExceptionUtils.getErrorMsg(e));
        }
        log.info("申请创建账户Ajax返回结果:{}", ajaxResult);
        return ajaxResult;
    }

    /**
     * 开通收款账户成功跳转页面
     */
    @RequestMapping(value = "applyAccSuccess", method = RequestMethod.GET)
    public String applyAccSuccess() {

        //跳转至收款账户开通成功页面
        return APPLY_SUCCESS;
    }

    /**
     * 开通收款账户成功跳转页面
     */
    @RequestMapping(value = "modifyStoreSuccess", method = RequestMethod.GET)
    public String modifyStoreSuccess() {

        return MODIFY_STORE_SUCCESS;
    }
}
