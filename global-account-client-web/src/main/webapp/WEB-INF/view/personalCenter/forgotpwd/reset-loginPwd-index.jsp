<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>忘记密码</title>
    <link href="${ctx}/resource/images/icon.ico" rel="shortcut icon">
    <script src="${ctx}/resource/js/security/resetLogin.js"></script>
    <script>
        $(function () {
            ajaxFormSubmit(function (data) {
                window.location.href = ctx + "/resetPwd/resetPassword/resetSelect";
            });
        });
    </script>
</head>
<body>
<div class="login-layout register-layout">
    <%@ include file="/WEB-INF/view/common/contact.jsp" %>
    <form method="post" action="${ctx}/resetPwd/resetPassword/second.do" id="ajaxForm">
        <div class="register-content forget-content">
            <div class="login-title">重置密码</div>
            <div class="forget-item clearfix">
                <div class="item-left fl">用户名<i style="color:red">*</i></div>
                <div class="input-box fl">
                    <i></i>
                    <input name="loginNo" type="text" placeholder="请输入用户名">
                </div>
            </div>
            <div class="forget-item">
                <div class="item-left fl">验证码<i style="color:red">*</i></div>
                <div class="verification-code-box input-box clearfix fl">
                    <i class="code-iocn"></i>
                    <input name="verifyCode" class="fl" type="text" placeholder="输入验证码">
                    <img id="imgVerify" src="" onclick="pageContent.getVerify()"/>
                </div>
            </div>
            <div class="btn-box forget-btn login-btn">
                <button>下一步</button>
            </div>
        </div>
    </form>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
</body>
</html>