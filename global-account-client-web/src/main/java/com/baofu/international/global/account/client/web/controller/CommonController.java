package com.baofu.international.global.account.client.web.controller;

import com.baofu.international.global.account.client.common.constant.PageUrlDict;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 公共请求服务
 * <p>
 * 1、打开帮助中心页面
 * </p>
 * ProjectName:account-client
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/5
 */
@Controller
@RequestMapping(value = "/common/")
public class CommonController {

    /**
     * 打开帮助中心页面
     *
     * @return 返回页面和数据信息
     */
    @RequestMapping(value = "help", method = RequestMethod.GET)
    public String openHelpCenter() {
        return PageUrlDict.HELP_CENTER_PAGE;
    }


    /**
     * @return 跳转到用户协议页面
     */
    @RequestMapping(value = "agreement", method = RequestMethod.GET)
    public String toAgreeContentPage() {
        return PageUrlDict.TO_AGREE_CONTENT_PAGE;
    }

    /**
     * @return 跳转到用户协议页面
     */
    @RequestMapping(value = "bindStoreExample/{ccy}", method = RequestMethod.GET)
    public String bindStoreExample(@PathVariable("ccy") String ccy) {

        return PageUrlDict.BIND_STORE_EXAMPLE + ccy;
    }
}
