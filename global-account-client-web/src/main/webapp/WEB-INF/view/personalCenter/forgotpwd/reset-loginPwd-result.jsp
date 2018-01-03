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
    <%@ include file="/WEB-INF/view/common/contact.jsp" %>
    <div class="same-layout small">
        <div class="same-title">
            <div class="title-left fl">
                <i class="fl reset-pic"></i>
                <h2>重置登录密码</h2>
            </div>
        </div>
        <div class="same-content">
            <div class="success-box">
                <img src="${ctx}/resource/images/ok.png" alt="success">
                <p>恭喜您，重置登录密码成功！</p>
                <div class="btn-box item-btn success-btn">
                    <button onclick="javascript:window.location='${ctx}/login/index.do'">马上去登录</button>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
</body>
</html>