<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
    <c:set var="LoginJSVersion" value="1.0.0"/>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>登录</title>
    <link href="${ctx}/resource/images/icon.ico" rel="shortcut icon">
    <script src="${ctx}/resource/js/common/plugins/verify/jquery.validate.min.js?version=${version}"></script>
</head>
<body>
<script src="${ctx}/business/login/login.js?version=${LoginJSVersion}"></script>
<div class="login-layout">
    <%@ include file="/WEB-INF/view/common/contact.jsp" %>
    <form id="loginForm">
    <div class="register-content">
        <div class="login-title">用户登录</div>
        <div class="input-box">
            <i></i>
            <input type="text"  id="loginNo" name="loginNo" placeholder="手机/邮箱" maxlength="64">
        </div>
        <div class="input-box">
            <i class="password-iocn"></i>
            <input type="password"  id="loginPwd" name="loginPwd" placeholder="登录密码" maxlength="32">
        </div>
        <div class="verification-code-box input-box clearfix" style="display: none">
            <i class="code-iocn"></i>
            <input class="fl" type="text" id="validateCode" name="validateCode" placeholder="输入验证码" maxlength="4">
            <img src="" id="imgVerify" onclick="getVerify()" alt="点击更换验证码">
        </div>
        <div class="btn-box login-btn">
            <button type="submit">登录</button>
        </div>
        <div class="more-box">
            <ul class="clearfix">
                <li class="forget-pwd">
                    <a href="${ctx}/resetPwd/resetPassword/first.do">忘记密码</a>
                </li>
                <li>
                    <a href="${ctx}/index">注册账号</a>
                </li>
            </ul>
        </div>
    </div>
    </form>
</div>
<!--顶部提示消息-->
<div class="top-tip hidden" id="errorMsgDiv">
    <img src="${ctx}/resource/images/warning-icon.png" alt="pic">
    <span></span>
    <i class="close-btn fr"></i>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
</body>
</html>