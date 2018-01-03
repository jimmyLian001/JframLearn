package com.baofu.international.global.account.client.web.controller.account;

import com.baofu.international.global.account.client.common.constant.PageUrlDict;
import com.baofu.international.global.account.client.common.constant.RequestDict;
import com.baofu.international.global.account.client.common.enums.CcySymbolEnum;
import com.baofu.international.global.account.client.common.util.BeanCopyUtils;
import com.baofu.international.global.account.client.common.util.DataDesensUtils;
import com.baofu.international.global.account.client.service.AccountService;
import com.baofu.international.global.account.client.service.SecurityConfigService;
import com.baofu.international.global.account.client.service.models.UserInfoBo;
import com.baofu.international.global.account.client.web.models.AjaxResult;
import com.baofu.international.global.account.client.web.models.SessionVo;
import com.baofu.international.global.account.client.web.models.StoreInfoVo;
import com.baofu.international.global.account.client.web.util.SessionUtil;
import com.baofu.international.global.account.core.facade.model.AccBalanceDto;
import com.baofu.international.global.account.core.facade.model.PageDto;
import com.baofu.international.global.account.core.facade.model.StoreInfoModifyReqDto;
import com.baofu.international.global.account.core.facade.model.res.StoreAccountInfoRepDto;
import com.baofu.international.global.account.core.facade.model.res.UserStoreAccountsDto;
import com.google.common.collect.Lists;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * <p>
 * 账户首页
 * </p>
 *
 * @author : wuyazi
 * @version : 1.0.0
 * @date : 2017/11/06
 */
@Slf4j
@Controller
@RequestMapping("/account")
public class AccountController {

    /**
     * 账户服务
     */
    @Autowired
    private AccountService accountService;

    /**
     * 安全设置服务
     */
    @Autowired
    private SecurityConfigService securityConfigService;

    /**
     * 币种显示优先级
     */
    private final static List<String> CCY_SORE_LIST = Lists.newArrayList("USD", "EUR", "GBP", "JPY");


    /**
     * 首页
     *
     * @return ModelAndView 响应
     */
    @RequestMapping(value = "index.do", method = RequestMethod.GET)
    public String index(ModelMap model) {
        try {
            SessionVo sessionVo = SessionUtil.getSessionVo();
            //获取账户余额
            AccBalanceDto accBalanceDto = accountService.getCcyBalance(sessionVo.getUserNo(), CcySymbolEnum.USD.getCode());
            log.info("用户余额信息:{}", accBalanceDto);
            model.put("accBalance", accBalanceDto);
            //获取账户余额
            AccBalanceDto eurAccBalanceDto = accountService.getCcyBalance(sessionVo.getUserNo(), CcySymbolEnum.EUR.getCode());
            log.info("用户余额信息:{}", accBalanceDto);
            model.put("eurAccBalance", eurAccBalanceDto);

            //获取用户或企业个人信息
            UserInfoBo userInfoBo = securityConfigService.getUserInfo(sessionVo.getLoginNo());
            log.info("用户实名信息:{}", accBalanceDto);
            model.put("userType", sessionVo.getUserType());
            model.put("authStatus", userInfoBo.getRealnameStatus());
            List<String> userCcyList = accountService.queryAccApplyCcy(sessionVo.getUserNo());
            model.put("accountFlag", Boolean.FALSE);
            //是否申请开通收款账户标识
            if (!CollectionUtils.isEmpty(userCcyList)) {
                model.put("accountFlag", Boolean.TRUE);
                model.put("showCcy", ccySoreShow(userCcyList));
            }
        } catch (Exception e) {
            log.error("我的账户首页打开异常：", e);
        }

        return "account/account-index";
    }

    /**
     * 币种优先级排序显示
     *
     * @param userCcyList 用户申请的开通币种集合
     * @return 返回要显示的币种信息
     */
    private String ccySoreShow(List<String> userCcyList) {
        for (String ccy : CCY_SORE_LIST) {
            if (userCcyList.contains(ccy)) {
                return ccy;
            }
        }
        return CcySymbolEnum.USD.getCode();
    }

    /**
     * 获取店铺账户信息
     *
     * @param storeName 店铺账户名称
     * @param pageNum   页码
     * @param pageSize  条数
     * @return 返回JSP地址
     */
    @ResponseBody
    @RequestMapping(value = "/query.do", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
    public String getStoreAccountList(String storeName, String ccy, int pageNum, int pageSize) {

        SessionVo sessionVo = SessionUtil.getSessionVo();
        //获取店铺账户信息
        PageDto<UserStoreAccountsDto> result = accountService.queryStoreAccountForPage(sessionVo.getUserNo(),
                sessionVo.getUserType(), storeName, ccy, pageNum, pageSize);

        return JsonUtil.toJSONString(result);
    }

    /**
     * 店铺账户详细信息
     *
     * @param map     map
     * @param storeNo storeNo
     */
    @RequestMapping(value = "/{storeNo}/accountdetail", method = RequestMethod.GET)
    public String getAccountDetail(@PathVariable("storeNo") String storeNo, ModelMap map) {
        String jspUrl = null;
        try {
            SessionVo sessionVo = SessionUtil.getSessionVo();
            StoreAccountInfoRepDto storeAccountInfoRepDto = accountService.queryStoreAccountInfo(sessionVo.getUserNo(),
                    sessionVo.getUserType(), Long.valueOf(storeNo));
            if (CcySymbolEnum.USD.getCode().equals(storeAccountInfoRepDto.getCcy())) {
                jspUrl = PageUrlDict.ACCOUNT_DETAIL_USD;
            }
            if (CcySymbolEnum.EUR.getCode().equals(storeAccountInfoRepDto.getCcy())) {
                jspUrl = PageUrlDict.ACCOUNT_DETAIL_EUR;
            }
            StoreInfoVo storeInfoVo = BeanCopyUtils.objectConvert(storeAccountInfoRepDto, StoreInfoVo.class);
            storeInfoVo.setAwsAccessKey(DataDesensUtils.dealSensBankCardNo(storeInfoVo.getAwsAccessKey()));
            storeInfoVo.setSecretKey(DataDesensUtils.dealSensBankCardNo(storeInfoVo.getSecretKey()));
            map.put("accDetail", storeInfoVo);
            map.put(RequestDict.LOGIN_NO, sessionVo.getLoginNo());
        } catch (Exception e) {
            log.error("店铺账户详细信息查询异常：", e);
            jspUrl = PageUrlDict.ERROR_PAGE;
        }
        return jspUrl;
    }

    /**
     * 更新店铺名字
     *
     * @param storeNo   storeNo
     * @param storeName storeName
     * @return 返回名字
     */
    @ResponseBody
    @RequestMapping(value = "modifyStoreName", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public AjaxResult<String> getShopName(Long storeNo, String storeName) {
        AjaxResult<String> ajaxResult = new AjaxResult<>();
        try {
            log.info("更新用户店铺名称，店铺号：{}.店铺名：{}", storeNo, storeName);
            SessionVo sessionVo = SessionUtil.getSessionVo();

            StoreInfoModifyReqDto storeInfoModifyReqDto = new StoreInfoModifyReqDto();
            storeInfoModifyReqDto.setUserNo(sessionVo.getUserNo());
            storeInfoModifyReqDto.setStoreName(storeName);
            storeInfoModifyReqDto.setStoreNo(storeNo);

            accountService.modifyUserStore(storeInfoModifyReqDto);
            ajaxResult.setCode(0);
        } catch (Exception e) {
            log.error("更新店铺名字", e);
            ajaxResult.setCode(1);
            ajaxResult.setMessage(ExceptionUtils.getErrorMsg(e));
        }
        log.info("更新用户店铺名称返回结果：{}", ajaxResult);
        return ajaxResult;
    }

}
