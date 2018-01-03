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
    <%@ include file="/WEB-INF/view/common/contact.jsp" %>
    <div class="same-layout small">
        <div class="same-title">
            <div class="title-left fl">
                <i class="fl reset-pic"></i>
                <h2>重置登录密码</h2>
            </div>
        </div>
        <div class="same-content reset-content">
            <div class="reset-account">您正在为账户
                <span>${loginNo}</span>
                重置登录密码，请选择修改方式：
            </div>
            <div class="recharge-mode">
                <ul class="clearfix">
                    <li>
                        <div class="msm-mode">
                            <div class="mode-top clearfix">
                                <img class="fl" src="${ctx}/resource/images/phone-pic.png" alt="phone">
                                <div class="modify-hints fl">
                                    <c:choose>
                                        <c:when test="${loginType==1}">
                                            <p class="msm">通过邮箱验证码</p>
                                            <p>
                                                如果你的${loginNo}邮箱还在正常使用，请选择此方式</p>
                                        </c:when>
                                        <c:when test="${loginType==2}">
                                            <p class="msm">通过短信验证码</p>
                                            <p>
                                                如果你的${loginNo}手机还在正常使用，请选择此方式</p>
                                        </c:when>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="btn-box login-btn">
                                <button onclick="window.location='${ctx}/resetPwd/resetPassword/1'">
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
                                <button onclick="window.location='${ctx}/resetPwd/resetPassword/2'">
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