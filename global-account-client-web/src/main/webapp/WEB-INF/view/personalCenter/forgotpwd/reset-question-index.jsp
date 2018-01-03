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
</head>
<body>
<div class="login-layout">
    <%@ include file="/WEB-INF/view/common/header.jsp" %>
    <div class="same-layout small">
        <div class="same-title">
            <div class="title-left fl">
                <i class="fl reset-pic"></i>
                <h2>修改安全保护问题</h2>
            </div>
        </div>
        <div class="same-content reset-content">
            <div class="reset-account">您正在为账户 <span>${fn:substring(loginNo,0,3)}****${fn:substring(loginNo,fn:length(loginNo)-4,fn:length(loginNo))}</span>
                修改安全保护问题，请选择修改方式：
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
                                            <p>如果你的${fn:substring(userInfo.loginNo,0,3)}****${fn:substring(userInfo.loginNo,fn:length(userInfo.loginNo)-4,fn:length(userInfo.loginNo))}邮箱还在正常使用，请选择此方式</p>
                                        </c:when>
                                        <c:when test="${userInfo.realnameStatus==null && userInfo.loginType==2}">
                                            <p class="msm">通过短信验证码</p>
                                            <p>如果你的${fn:substring(userInfo.loginNo,0,3)}****${fn:substring(userInfo.loginNo,fn:length(userInfo.loginNo)-4,fn:length(userInfo.loginNo))}手机还在正常使用，请选择此方式</p>
                                        </c:when>
                                        <c:when test="${userInfo.mobileNo!=null}">
                                            <p class="msm">通过短信验证码</p>
                                            <p>如果你的${fn:substring(userInfo.mobileNo,0,3)}****${fn:substring(userInfo.mobileNo,fn:length(userInfo.mobileNo)-4,fn:length(userInfo.mobileNo))}手机还在正常使用，请选择此方式</p>
                                        </c:when>
                                        <c:when test="${userInfo.mobileNo==null && userInfo.email!=null }">
                                            <p class="msm">通过邮箱验证码</p>
                                            <p>如果你的${fn:substring(userInfo.email,0,3)}****${fn:substring(userInfo.email,fn:length(userInfo.email)-4,fn:length(userInfo.email))}邮箱还在正常使用，请选择此方式</p>
                                        </c:when>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="btn-box login-btn">
                                <button onclick=" javascript: window.location='${ctx}/person/center/modifyQuestion/reset-question-sms.do'">
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
                                <button onclick="javascript: window.location='${ctx}/person/center/modifyQuestion/reset-question-answer.do'">
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
</body>
</html>