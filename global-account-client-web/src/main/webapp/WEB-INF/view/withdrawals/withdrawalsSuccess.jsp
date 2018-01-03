<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>账户</title>
    <link href="${ctx}/resource/images/icon.ico" rel="shortcut icon">
</head>
<body>
<div class="login-layout">
    <%@ include file="/WEB-INF/view/common/header.jsp" %>
    <div class="same-layout small">
        <div class="same-title">
            <div class="title-left fl">
                <i class="fl reset-pic"></i>
                <h2>提现成功</h2>
            </div>
        </div>
        <div class="same-content">
            <div class="success-box">
                <img src="${ctx}/resource/images/ok.png" alt="success">
                <p>提现申请成功，等待入账</p>
                <div class="btn-box item-btn success-btn">
                    <button onclick="window.location.href = ctx + '/withdraw/apply'">继续提现</button>
                    <button class="cancel-btn" onclick="window.location.href=ctx+'/trade/toTradeDetailQueryPage'">
                        查看提现记录
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
</body>
</html>