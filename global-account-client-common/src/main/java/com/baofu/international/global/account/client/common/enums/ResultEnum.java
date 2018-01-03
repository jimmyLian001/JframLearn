package com.baofu.international.global.account.client.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 安全中心页面提示和跳转枚举
 * <p>
 * </p>
 *
 * @author : kangzhiguang
 * @version : 1.0.0
 * @date : 2017/11/06
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {

    /**
     * 修改登录密码参数校验
     */
    MODIFY_LOGIN_PWD_SECOND_0(Boolean.FALSE, Boolean.FALSE, ResultEnum.MODIFY_LOGIN_PWD, "抱歉，两次输入密码不一致！", ResultEnum.PERSON_CENTER_INDEX, ResultEnum.RESULT_INDEX),

    /**
     * 修改登录密码失败
     */
    MODIFY_LOGIN_PWD_SECOND_1(Boolean.FALSE, Boolean.FALSE, ResultEnum.MODIFY_LOGIN_PWD, "抱歉，修改登录密码失败！", ResultEnum.MODIFY_LOGIN_PWD_INDEX, ResultEnum.RESULT_INDEX),

    /**
     * 修改登录密码成功
     */
    MODIFY_LOGIN_PWD_SECOND_2(Boolean.TRUE, Boolean.TRUE, ResultEnum.MODIFY_LOGIN_PWD, "恭喜您，修改登录密码成功", ResultEnum.PERSON_CENTER_INDEX, ResultEnum.RESULT_INDEX),

    /**
     * 修改登录密码-旧密码校验
     */
    MODIFY_LOGIN_PWD_SECOND_3(Boolean.FALSE, Boolean.FALSE, ResultEnum.MODIFY_LOGIN_PWD, "抱歉，旧密码有误", ResultEnum.MODIFY_LOGIN_PWD_INDEX, ResultEnum.RESULT_INDEX),

    /**
     * 修改安全问题答案 - 验证码验证
     */
    MODIFY_QUESTION_SECOND_0(Boolean.FALSE, Boolean.FALSE, ResultEnum.MODIFY_QUESTION_ANSWER, ResultEnum.CODE_VERIFY_FAILED, "person/center/modifyQuestion/reset-question-sms.do", ResultEnum.RESULT_INDEX),

    /**
     * 修改安全问题答案 - 修改成功
     */
    MODIFY_QUESTION_THREE_2(Boolean.TRUE, Boolean.FALSE, ResultEnum.MODIFY_QUESTION_ANSWER, "恭喜您，修改安全问题答案成功", ResultEnum.PERSON_CENTER_INDEX, ResultEnum.RESULT_INDEX),

    /**
     * 修改安全问题答案 - 原密保答案有误
     */
    MODIFY_QUESTION_THREE_3(Boolean.FALSE, Boolean.FALSE, ResultEnum.MODIFY_QUESTION_ANSWER, "抱歉，原密保答案有误！", "person/center/modifyQuestion/reset-question-answer.do", ResultEnum.RESULT_INDEX),

    /**
     * 重置登录密码 - 验证码验证
     */
    RESET_LOGIN_PWD_SECOND_0(Boolean.FALSE, Boolean.FALSE, ResultEnum.RESET_LOGIN_PWD, ResultEnum.CODE_VERIFY_FAILED, ResultEnum.RESET_LOGIN_PWD_INDEX, ResultEnum.RESET_LOGIN_PWD_RESULT),

    /**
     * 重置登录密码 - 登录号不存在
     */
    RESET_LOGIN_PWD_SECOND_1(Boolean.FALSE, Boolean.FALSE, ResultEnum.RESET_LOGIN_PWD, ResultEnum.LOGIN_NO_NOT_EXIST, ResultEnum.RESET_LOGIN_PWD_INDEX, ResultEnum.RESET_LOGIN_PWD_RESULT),

    /**
     * 重置登录密码 - 验证码不对
     */
    RESET_LOGIN_PWD_THREE_0(Boolean.FALSE, Boolean.FALSE, ResultEnum.RESET_LOGIN_PWD, ResultEnum.CODE_VERIFY_FAILED, ResultEnum.RESET_LOGIN_PWD_SMS, ResultEnum.RESET_LOGIN_PWD_RESULT),

    /**
     * 重置登录密码 - 登录号不存在
     */
    RESET_LOGIN_PWD_THREE_1(Boolean.FALSE, Boolean.FALSE, ResultEnum.RESET_LOGIN_PWD, ResultEnum.LOGIN_NO_NOT_EXIST, ResultEnum.RESET_LOGIN_PWD_SMS, ResultEnum.RESET_LOGIN_PWD_RESULT),

    /**
     * 重置登录密码 - 安全保密答案不对
     */
    RESET_LOGIN_PWD_THREE_2(Boolean.FALSE, Boolean.FALSE, ResultEnum.RESET_LOGIN_PWD, "抱歉，安全保密答案不对！", ResultEnum.RESET_LOGIN_PWD_QUESTION, ResultEnum.RESET_LOGIN_PWD_RESULT),

    /**
     * 重置登录密码 - 登录号不存在
     */
    RESET_LOGIN_PWD_THREE_3(Boolean.FALSE, Boolean.FALSE, ResultEnum.RESET_LOGIN_PWD, ResultEnum.LOGIN_NO_NOT_EXIST, ResultEnum.RESET_LOGIN_PWD_QUESTION, ResultEnum.RESET_LOGIN_PWD_RESULT),

    /**
     * 重置登录密码 - 重置登录密码失败
     */
    RESET_LOGIN_PWD_FOUR_0(Boolean.FALSE, Boolean.FALSE, ResultEnum.RESET_LOGIN_PWD, "抱歉，重置登录密码失败！", ResultEnum.RESET_LOGIN_PWD_INDEX, ResultEnum.RESET_LOGIN_PWD_RESULT),

    /**
     * 重置登录密码 - 参数校验
     */
    RESET_LOGIN_PWD_PARAMS_VALIDATE(Boolean.FALSE, Boolean.FALSE, ResultEnum.RESET_LOGIN_PWD, "抱歉，参数检验失败！", ResultEnum.RESET_LOGIN_PWD_INDEX, ResultEnum.RESET_LOGIN_PWD_RESULT),;

    /**
     * 修改登录密码
     */
    private static final String MODIFY_LOGIN_PWD = "修改登录密码";

    /**
     * 修改安全问题答案
     */
    private static final String MODIFY_QUESTION_ANSWER = "修改安全问题答案";

    /**
     * 重置登录密码
     */
    private static final String RESET_LOGIN_PWD = "重置登录密码";

    /**
     * 登录号不存在
     */
    private static final String LOGIN_NO_NOT_EXIST = "抱歉，该登录号不存在！";

    /**
     * 验证码不对
     */
    private static final String CODE_VERIFY_FAILED = "抱歉，验证码不对！";

    /**
     * 个人中心
     */
    private static final String PERSON_CENTER_INDEX = "person/center/index.do";

    /**
     * 结果响应界面
     */
    private static final String RESULT_INDEX = "/personalCenter/forgotpwd/common-result";

    /**
     * 重置密码首页
     */
    private static final String RESET_LOGIN_PWD_INDEX = "person/center/resetPassword/first.do";

    /**
     * 重置密码结果
     */
    private static final String RESET_LOGIN_PWD_RESULT = "/personalCenter/forgotpwd/reset-loginPwd-common-result";

    /**
     * 重置密码短信页面
     */
    private static final String RESET_LOGIN_PWD_SMS = "person/center/resetPassword/reset-loginPwd-sms.do";

    /**
     * 重置密码密保问题页面
     */
    private static final String RESET_LOGIN_PWD_QUESTION = "person/center/resetPassword/reset-loginPwd-answer.do";

    /**
     * 修改登录密码首页
     */
    private static final String MODIFY_LOGIN_PWD_INDEX = "person/center/modifyPassword/first.do";

    /**
     * 处理标识
     */
    private Boolean successFlag;

    /**
     * 退出标识
     */
    private Boolean loginOutFlag;

    /**
     * 主题
     */
    private String title;

    /**
     * 提示内容
     */
    private String content;

    /**
     * 返回地址
     */
    private String returnUrl;

    /**
     * 结果页面地址
     */
    private String resultUrl;
}
