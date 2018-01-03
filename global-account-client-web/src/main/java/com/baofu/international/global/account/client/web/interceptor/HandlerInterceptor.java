package com.baofu.international.global.account.client.web.interceptor;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.client.common.constant.CommonDict;
import com.baofu.international.global.account.client.common.constant.NumberDict;
import com.baofu.international.global.account.client.web.models.AjaxResult;
import com.baofu.international.global.account.client.web.models.SessionVo;
import com.baofu.international.global.account.client.web.util.SessionUtil;
import com.google.common.base.Splitter;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

/**
 * spring 拦截器重写，用于生成日志ID信息
 * <p>
 * 1、拦截器重写
 * </p>
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/11/29
 */
@Slf4j
public class HandlerInterceptor extends HandlerInterceptorAdapter {

    /**
     * 请求系统时间Key，此key会放入想成变量中
     */
    private static final String REQ_TIME_KEY = "REQ_TIME_KEY";

    /**
     * 需要过滤的请求地址前缀
     */
    private static final String[] NOT_CHECK_PATH = new String[]{"register", "login", "index", "common", "resetPwd"};

    /**
     * This implementation always returns {@code true}.
     *
     * @param request  请求参数信息
     * @param response response对象
     * @param handler  对象信息
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //请求时间
        Long startIime = System.currentTimeMillis();
        MDC.put(REQ_TIME_KEY, startIime.toString());
        //设置请求和响应编码
        request.setCharacterEncoding(CommonDict.UTF_8);
        response.setCharacterEncoding(CommonDict.UTF_8);
        //设置日志ID
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, UUID.randomUUID().toString());
        String requestPath = request.getRequestURI();
        log.info("用户请求后台，地址：{}", requestPath);
        //判断是否合法，返回false时代表请求地址不需要登录也可以访问
        Boolean legalFlag = checkReqLegal(requestPath);

        //获取登录的用户信息
        SessionVo sessionVo = SessionUtil.getSessionVo();
        if (!legalFlag && sessionVo == null) {

            //判断是否属于Ajax请求
            if ("XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
                AjaxResult<String> ajaxResult = new AjaxResult<>();
                ajaxResult.setCode(999);
                ajaxResult.setMessage("登录Session超时");
                ajaxResult.setObj("登录Session超时");
                response.setHeader("Content-type", "text/html;charset=UTF-8");

                log.warn("用户登录超时，返回Ajax错误信息");
                response.getOutputStream().write(JsonUtil.toJSONString(ajaxResult).getBytes(StandardCharsets.UTF_8));
            } else {
                log.warn("用户登录超时，跳转至登录页面");
                request.getRequestDispatcher("/login/index.do").forward(request, response);
            }
            return Boolean.FALSE;
        }
        return super.preHandle(request, response, handler);
    }

    /**
     * 请求执行完成之后调用
     *
     * @param request  请求对象
     * @param response 返回对象
     * @param handler  处理对象
     * @param ex       异常
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        //间隔时间，后续可根据此时间做监控
        Long intervalTime = (System.currentTimeMillis() - Long.parseLong(MDC.get(REQ_TIME_KEY)));
        if (ex != null) {
            log.error("请求系统异常，异常内容：", ex);
        }
        log.info("本次请求总耗时：{}", intervalTime);
        MDC.clear();
    }

    /**
     * 判断请求是否合法
     *
     * @param requestPath 请求完整路径
     * @return 返回是否合法
     */
    private Boolean checkReqLegal(String requestPath) {
        int indexOf = StringUtils.indexOf(requestPath, '/');
        if (indexOf == 0) {
            requestPath = requestPath.substring(1);
        }
        //使用分隔符分开，分隔符为“/”
        List<String> stringList = Splitter.on("/").splitToList(requestPath);
        for (String noCheckUrl : NOT_CHECK_PATH) {
            if (StringUtils.equals(stringList.get(NumberDict.ZERO), noCheckUrl)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
