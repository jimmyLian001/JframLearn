package com.baofu.international.global.account.client.web.controller.qualified;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.client.common.constant.PageUrlDict;
import com.baofu.international.global.account.client.common.constant.RequestDict;
import com.baofu.international.global.account.client.common.util.DataDesensUtils;
import com.baofu.international.global.account.client.web.models.SessionVo;
import com.baofu.international.global.account.client.web.util.SessionUtil;
import com.baofu.international.global.account.core.facade.model.user.UserOrgReqDto;
import com.baofu.international.global.account.core.facade.user.OrgFacade;
import com.system.commons.result.Result;
import com.system.commons.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * <p>
 * 1、方法描述
 * </p>
 * ProjectName:global-account-client-parent
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/19
 */
@Slf4j
@Controller
@RequestMapping("qualified/")
public class QualifiedController {

    /**
     * 接口信息查询
     */
    @Autowired
    private OrgFacade orgFacade;

    /**
     * 用户资质信息查询
     *
     * @return 返回jsp和数据信息
     */
    @RequestMapping("qualifiedQuery")
    public ModelAndView qualifiedQuery() {

        ModelAndView modelAndView = new ModelAndView(PageUrlDict.QUALIFIED_MANAGER);
        try {
            SessionVo sessionVo = SessionUtil.getSessionVo();
            log.info("用户资质信息查询请求参数：{}", sessionVo.getUserNo());
            Result<List<UserOrgReqDto>> result = orgFacade.queryUserQualified(sessionVo.getUserNo(),
                    MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("用户资质信息查询返回结果：{}", result);

            ResultUtil.handlerResult(result);

            for (UserOrgReqDto userOrgReqDto : result.getResult()) {
                userOrgReqDto.setLegalIdNo(DataDesensUtils.dealSensBankCardNo(userOrgReqDto.getLegalIdNo()));
            }
            modelAndView.addObject(RequestDict.USER_TYPE, sessionVo.getUserType());
            modelAndView.addObject(RequestDict.PAGE_INFO, result.getResult());
        } catch (Exception e) {
            log.error("用户资质信息查询异常：", e);
        }
        return modelAndView;
    }

    /**
     * 资质信息操作成功跳转页面
     *
     * @return 返回JSP 页面地址
     */
    @RequestMapping(value = "{qualifiedRequestType}/qualifiedOperationSuccess", method = RequestMethod.GET)
    public ModelAndView qualifiedOperationSuccess(@PathVariable("qualifiedRequestType") String qualifiedRequestType) {
        ModelAndView modelAndView = new ModelAndView(PageUrlDict.QUALIFIED_OPERATION_SUCCESS);
        modelAndView.addObject("qualifiedRequestType", qualifiedRequestType);

        return modelAndView;
    }
}
