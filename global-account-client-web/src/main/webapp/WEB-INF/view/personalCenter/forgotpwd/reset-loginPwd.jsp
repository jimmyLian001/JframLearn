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
    <script src="${ctx}/resource/js/security/resetLogin.js?version=${version}"></script>
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
        <form method="post" action="${ctx}/resetPwd/resetPassword/reset">
            <input type="hidden" name="loginNo" value="${loginNo}"/>
            <div class="same-content">
                <div class="reset-pwd-content questions-info">
                    <div class="forget-item clearfix">
                        <div class="item-left fl">密码<i style="color:red">*</i></div>
                        <div class="input-box fl">
                            <input id="firstPwd" name="firstPwd" type="password" placeholder="请填写密码" maxlength="16">
                        </div>
                    </div>
                    <div class="forget-item clearfix">
                        <div class="item-left fl">重复密码<i style="color:red">*</i></div>
                        <div class="input-box fl">
                            <input id="secondPwd" name="secondPwd" type="password" placeholder="请填写重复密码" maxlength="16">
                        </div>
                    </div>
                    <div class="btn-box">
                        <button type="submit">下一步</button>
                        <button class="cancel-btn" type="button"
                                onclick="window.history.go(-1)"> 返回
                        </button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
</body>
</html>