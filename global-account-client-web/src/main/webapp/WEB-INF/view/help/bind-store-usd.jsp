<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<c:set var="LoginJSVersion" value="1.0.1"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>账号绑定步骤</title>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
</head>
<body>
<div class="login-layout">
    <%@ include file="/WEB-INF/view/common/contact.jsp" %>
    <div class="same-content agreement-layout">
        <div class="agreement-wrap w1200">
            <div class="agreement-content bind-content">
                <h2>账号绑定Amazon北美站店铺操作指引</h2>
                <div class="agreement-item">
                    <h3>1、登录Amazon卖家后台，点击右上角的“账户信息”——点击中间的“存款方式”如下图。</h3>
                    <img src="${ctx}/resource/images/bind-n-1.png" alt="pic">
                </div>
                <div class="agreement-item">
                    <h3>2、点击右侧的“分配”或者“管理存款方式”</h3>
                    <img src="${ctx}/resource/images/bind-n-2.png" alt="pic">
                    <img src="${ctx}/resource/images/bind-n-3.png" alt="pic">
                </div>
                <div class="agreement-item">
                    <h3>3、将宝付国际银行账号9位收款路线号码（9-Digit Routing Number）、银行账号（Bank Account Number）、账号持有人姓名（Account Holder
                        Name）填入即可。部分亚马逊系统只要求填写新的收款账户信息，无需填写当前的收款账户信息，如下图。用户可根据亚马逊提供页面提示，填写相关信息即可。</h3>
                    <img src="${ctx}/resource/images/bind-n-4.png" alt="pic">
                </div>
                <div class="agreement-item">
                    <h3>4、点击设置存款方式，进入到成功设置界面，如下图，表示收款账户设置完成。</h3>
                    <img src="${ctx}/resource/images/bind-n-5.png" alt="pic">
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
</body>
</html>