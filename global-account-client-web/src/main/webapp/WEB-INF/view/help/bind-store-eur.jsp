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
                <h2>账号绑定Amazon欧洲站店铺操作指引</h2>
                <div class="agreement-item">
                    <h3>1.登录Amazon卖家后台，点击右上角的“账户信息”——点击中间的“存款方式”如下图。</h3>
                    <img src="${ctx}/resource/images/bind-e-1.png" alt="pic">
                </div>
                <div class="agreement-item">
                    <h3>2.点击“编辑”</h3>
                    <img src="${ctx}/resource/images/bind-e-2.png" alt="pic">
                </div>
                <div class="agreement-item">
                    <h3>3.银行所在地选择德国，填写在“BIC”和IBAN；持有人姓名，点击“提交”</h3>
                    <img src="${ctx}/resource/images/bind-e-3.png" alt="pic">
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
</body>
</html>