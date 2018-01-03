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
                <h2>店铺信息补充</h2>
            </div>
        </div>
        <div class="same-content">
            <div class="success-box">
                <img src="${ctx}/resource/images/ok.png" alt="success">
                <p>店铺信息补充完成</p>
                <div class="btn-box item-btn success-btn">
                    <button class="cancel-btn" onclick="window.location.href='${ctx}/account/index.do'">返回账户首页
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
</body>
</html>