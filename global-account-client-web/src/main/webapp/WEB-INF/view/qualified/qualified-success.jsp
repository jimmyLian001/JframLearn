<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>账户</title>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
</head>
<body>
<div class="login-layout">
    <%@ include file="/WEB-INF/view/common/header.jsp" %>
    <div class="same-layout small">
        <div class="same-title">
            <div class="title-left fl">
                <i class="fl reset-pic"></i>
                <c:if test="${qualifiedRequestType == 1}">
                    <h2>新增持卡人信息</h2>
                </c:if>
                <c:if test="${qualifiedRequestType == 2}">
                    <h2>持有人信息更新</h2>
                </c:if>
            </div>
        </div>
        <div class="same-content">
            <div class="success-box">
                <img src="${ctx}/resource/images/ok.png" alt="success">
                <c:if test="${qualifiedRequestType == 1}">
                    <h2>持有人信息提交成功</h2>
                </c:if>
                <c:if test="${qualifiedRequestType == 2}">
                    <h2>持有人信息修改成功</h2>
                </c:if>
                <p>审核将在1-2个工作日之内完成</p>
                <div class="btn-box item-btn success-btn">
                    <button type="button" onclick="window.location.href='${ctx}/account/index.do'">返回账户首页</button>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
</body>
</html>