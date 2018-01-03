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
                <h2>修改安全保护问题</h2>
            </div>
        </div>
        <form action="${ctx}/person/center/modifyQuestion/validteCode.do" method="post">
            <input type="hidden" name="msgType" value="6"/>
            <div class="same-content">
                <div class="same-item clearfix">
                    <div class="item-left fl">用户名</div>
                    <div class="item-right fl">${fn:substring(loginNo,0,3)}****${fn:substring(loginNo,fn:length(loginNo)-4,fn:length(loginNo))}</div>
                </div>
                <div class="same-item clearfix">
                    <div class="item-left fl">验证码<i style="color:red">*</i></div>
                    <div class="verification-code-box input-box verification-bank clearfix fl">
                        <input name="verifyCode" class="fl" type="text" placeholder="输入验证码">
                        <a class="fl sms-code" style="cursor: pointer">获取验证</a>
                    </div>
                </div>
                <div class="btn-box item-btn">
                    <button>下一步</button>
                    <button class="cancel-btn" type="button"
                            onclick="javascript:window.location='${ctx}/person/center/modifyQuestion/index.do'">返回
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
</body>
</html>