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
    <script src="${ctx}/resource/js/security/securityCommon.js?version=${version}"></script>
</head>
<body>
<div class="login-layout">
    <%@ include file="/WEB-INF/view/common/header.jsp" %>
    <div class="same-layout small">
        <div class="same-title">
            <div class="title-left fl">
                <i class="fl reset-pic"></i>
                <h2>修改登录密码</h2>
            </div>
        </div>
        <form method="post" action="${ctx}/person/center/modifyPassword/second.do" type="login">
            <div class="same-content">
                <div class="same-item clearfix">
                    <div class="item-left fl">用户</div>
                    <div class="item-right fl">
                        ${fn:substring(loginNo,0,3)}****${fn:substring(loginNo,fn:length(loginNo)-4,fn:length(loginNo))}
                    </div>
                </div>
                <div class="same-item clearfix">
                    <div class="item-left fl">当前登录密码<i style="color: red">*</i></div>
                    <div class="input-box fl">
                        <input id="oldPassword" name="oldPassword" type="password" placeholder="请输入当前登录密码" maxlength="16">
                    </div>
                </div>
                <div class="same-item clearfix">
                    <div class="item-left fl">新登录密码<i style="color: red">*</i></div>
                    <div class="input-box fl">
                        <input id="firstPwd" name="firstPwd" type="password" placeholder="请输入新登录密码" maxlength="16">
                    </div>
                </div>
                <div class="same-item clearfix">
                    <div class="item-left fl">重复密码<i style="color: red">*</i></div>
                    <div class="input-box fl">
                        <input id="secondPwd" name="secondPwd" type="password" placeholder="请输入重复密码" maxlength="16">
                    </div>
                </div>
                <div class="btn-box item-btn">
                    <button type="submit">下一步</button>
                    <button class="cancel-btn" type="button"
                            onclick="javascript:window.location='${ctx}/person/center/index.do'">返回
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
</body>
</html>