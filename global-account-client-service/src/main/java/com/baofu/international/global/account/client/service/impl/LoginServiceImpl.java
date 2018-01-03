package com.baofu.international.global.account.client.service.impl;

import com.baofu.international.global.account.client.service.LoginService;
import com.baofu.international.global.account.core.facade.UserLoginFacade;
import com.baofu.international.global.account.core.facade.model.UserLoginReqDTO;
import com.baofu.international.global.account.core.facade.model.UserLoginRespDTO;
import com.system.commons.result.Result;
import com.system.commons.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户登录
 * <p>
 * 1.用户登录服务
 * </p>
 *
 * @author : 不良人
 * @version : 1.0.0
 * @date : 2017/11/5
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    /**
     * 用户登录接口
     */
    @Autowired
    private UserLoginFacade userLoginFacade;

    /**
     * 用户登录服务
     *
     * @param loginNo  用户名
     * @param loginPwd 密码
     * @param loginIp  登录IP
     * @return 登录信息
     */
    @Override
    public UserLoginRespDTO loginService(String loginNo, String loginPwd, String loginIp) {
        UserLoginReqDTO reqDTO = new UserLoginReqDTO();
        reqDTO.setLoginNo(loginNo);
        reqDTO.setLoginPwd(loginPwd);
        reqDTO.setLoginIp(loginIp);
        log.info("用户登录服务,loginNo：{},loginIp:{}", reqDTO.getLoginNo(), reqDTO.getLoginIp());
        Result<UserLoginRespDTO> result = userLoginFacade.userLogin(reqDTO, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("用户登录服务,响应信息：{}", result);
        ResultUtil.handlerResult(result);

        return result.getResult();
    }

    /**
     * 根据登录号查询登录信息
     *
     * @param loginNo 登录号
     * @return 返回结果
     */
    @Override
    public UserLoginRespDTO queryLoginInfo(String loginNo) {

        log.info("根据登录号查询登录信息,登录号：{}", loginNo);
        Result<UserLoginRespDTO> result = userLoginFacade.findLoginInfo(loginNo, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("根据登录号查询登录信息,返回结果：{}", result);
        ResultUtil.handlerResult(result);

        return result.getResult();
    }
}
