<%--
  Created by IntelliJ IDEA.
  User: luoping
  Date: 2017/11/21 0021
  Time: 20:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>账户设置</title>
    <link href="${ctx}/resource/images/icon.ico" rel="shortcut icon">
    <script src="${ctx}/resource/js/userInfo/reviseTel.js?version=${version}"></script>
    <script src="${ctx}/resource/js/common/plugins/verify/jquery.validate.min.js?version=${version}"></script>
</head>
<body>
<div class="login-layout">
    <%@ include file="/WEB-INF/view/common/header.jsp" %>
    <div class="same-layout small">
        <div class="same-title">
            <div class="title-left fl">
                <i class="fl reset-pic"></i>
                <h2>重置支付密码</h2>
            </div>
        </div>
        <div class="same-content reset-content">
            <div class="reset-account">您正在为账户 <span>${f:secrueqaEncrypt(loginNo)}</span>
                重置支付密码，请选择重置方式：
            </div>
            <div class="recharge-mode">
                <ul class="clearfix">
                    <li>
                        <div class="msm-mode">
                            <div class="mode-top clearfix">
                                <img class="fl" src="${ctx}/resource/images/phone-pic.png" alt="phone">
                                <div class="modify-hints fl">
                                    <c:choose>
                                        <c:when test="${userInfo.realnameStatus==null && userInfo.loginType==1}">
                                            <p class="msm">通过邮箱验证码</p>
                                            <p>
                                                如果你的<span>${f:secrueqaEncrypt(userInfo.loginNo)}</span>邮箱还在正常使用，请选择此方式
                                            </p>
                                        </c:when>
                                        <c:when test="${userInfo.realnameStatus==null && userInfo.loginType==2}">
                                            <p class="msm">通过短信验证码</p>
                                            <p>
                                                如果你的<span>${f:secrueqaEncrypt(userInfo.loginNo)}</span>手机还在正常使用，请选择此方式
                                            </p>
                                        </c:when>
                                        <c:when test="${userInfo.mobileNo!=null}">
                                            <p class="msm">通过短信验证码</p>
                                            <p>
                                                如果你的<span>${f:secrueqaEncrypt(userInfo.mobileNo)}</span>手机还在正常使用，请选择此方式
                                            </p>
                                        </c:when>
                                        <c:when test="${userInfo.mobileNo==null && userInfo.email!=null }">
                                            <p class="msm">通过邮箱验证码</p>
                                            <p>
                                                如果你的<span>${f:secrueqaEncrypt(userInfo.email)}</span>邮箱还在正常使用，请选择此方式</p>
                                        </c:when>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="btn-box login-btn">
                                <button href="javascript:void (0)" onclick="rechargePayPwd('phone')">
                                    立即修改
                                </button>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="msm-mode">
                            <div class="mode-top clearfix">
                                <img class="fl" src="${ctx}/resource/images/question-pic.png" alt="pic">
                                <div class="modify-hints fl">
                                    <p class="msm">通过回答安全问题</p>
                                    <p>如果你记得预留的安全保护问题，请选择此方式</p>
                                </div>
                            </div>
                            <div class="btn-box login-btn">
                                <button href="javascript:void (0)" onclick="rechargePayPwd('question')">
                                    立即修改
                                </button>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
<div class="contact-us">
    <ul>
        <li class="customer-service">
            <div class="customer-pic same-service"></div>
            <img class="code-pic none" src="${ctx}/resource/images/code.png" alt="code">
        </li>
        <li class="we-chat">
            <div class="chat-pic same-service"></div>
            <img class="num-pic none" src="${ctx}/resource/images/tel-num.png" alt="num">
        </li>
    </ul>
</div>
</body>
</html>
<script src="${ctx}/resource/js/register/countdown.js?version=${version}"></script>
<script type="text/javascript">
    function rechargePayPwd(rechargeType) {
        window.location = '${ctx}/person/center/resetPaymentPwd/second.do?rechargeType=' + rechargeType
    }
</script>

