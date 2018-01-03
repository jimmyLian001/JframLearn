<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>企业用户注册</title>
    <link href="${ctx}/resource/images/icon.ico" rel="shortcut icon">
</head>
<body>
<div class="login-layout register-layout">
    <%@ include file="/WEB-INF/view/register/registerHead.jsp" %>
    <div class="register-content">
        <from id="regOrgFrom" action="#" method="post">
            <div class="login-title">企业用户注册</div>
            <div class="input-box">
                <i></i>
                <input type="text" placeholder="请输入邮箱" name="loginNo" id="loginNo"
                       autocomplete="off" maxlength="64">
            </div>
            <div class="verification-code-box input-box clearfix">
                <i class="code-iocn"></i>
                <input class="fl" type="text" placeholder="输入验证码" name="captcha" id="captcha"
                       maxlength="6" autocomplete="off">
                <a class="fl" href="javascript:;" onclick="sendEmail(this.id)" id="sendEmail">获取验证</a>
                <div class="input-tip"></div>
            </div>
            <div class="input-box">
                <i class="password-iocn"></i>
                <input type="password" placeholder="密码由6-16位字母、数字或符号组成" id="loginPwd" name="loginPwd"
                       maxlength="16" autocomplete="off">
            </div>
            <div class="input-box">
                <i class="password-iocn"></i>
                <input type="password" placeholder="请再次输入密码" id="loginPwdAgain" name="loginPwdAgain"
                       maxlength="16" autocomplete="off">
            </div>
        </from>
        <div class="btn-box login-btn">
            <button onclick="nextSubmit()" id="nextSubmit">下一步</button>
        </div>
        <div class="login-link">
            已有账号，<a class="main-color" href="${ctx}/login/index.do">马上登录</a>
        </div>
    </div>

    <form id="nextForm" action="${ctx}/register/orgQuestionPage.do" method="post">
        <input type="hidden" name="loginNo" id="loginNoHidden">
    </form>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
</body>
</html>
<script src="${ctx}/business/register/js/register.js?version=${version}"></script>
<script src="${ctx}/business/register/js/registerOrg.js?version=${version}"></script>

