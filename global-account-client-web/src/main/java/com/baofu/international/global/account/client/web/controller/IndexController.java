package com.baofu.international.global.account.client.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 首页
 * <p>
 * 1、首页页面打开
 * </p>
 * ProjectName:account-client
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/5
 */
@Controller
@RequestMapping
public class IndexController {

    /**
     * 页面首页
     *
     * @return 返回首页信息
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {

        return "index";
    }
}
