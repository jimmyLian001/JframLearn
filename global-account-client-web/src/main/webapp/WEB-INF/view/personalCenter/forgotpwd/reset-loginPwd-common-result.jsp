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
                <h2>${result.title}</h2>
            </div>
        </div>
        <div class="same-content">
            <div class="success-box">
                <c:if test="${result.successFlag}">
                    <img src="${ctx}/resource/images/ok.png" alt="success">
                </c:if>
                <c:if test="${!result.successFlag}">
                    <img src="${ctx}/resource/images/error.png" alt="success">
                </c:if>

                <p>${result.content}</p>
                <div class="btn-box item-btn success-btn">
                    <button onclick="window.history.go(-1)">返回</button>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
</body>
</html>