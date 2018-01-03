<%--
  Created by IntelliJ IDEA.
  User: luoping
  Date: 2017/11/21 0021
  Time: 20:39
  To change this template use File | Settings | File Templates.
--%>
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
</head>
<body>
<div class="login-layout">
    <%@ include file="/WEB-INF/view/common/header.jsp" %>
    <div class="same-layout small" id="paymentPwdIndex">
        <form action="${ctx}/userTel/bindPhoneNoApply.do" method="post" id="form-tel">
            <input type="hidden" id="serviceType" name="serviceType" value="${serviceType}">
            <div class="same-title">
                <div class="title-left fl">
                    <i class="fl reset-pic"></i>
                    <h2>设置绑定手机</h2>
                </div>
            </div>
            <div class="same-content">
                <div id="pagePromptsDiv" na style="display: none;text-align: center;color: red"></div>
                <div class="same-item clearfix">
                    <div class="item-left fl">新手机号码 <i style="color:red">*</i></div>
                    <div class="input-box fl">
                        <input type="text" placeholder="请输入新手机号码" id="afterFixPhoneNumber"
                               name="afterFixPhoneNumber" maxlength="11" autocomplete="off">
                    </div>
                </div>
                <div class="same-item clearfix">
                    <div class="item-left fl">短信验证码 <i style="color:red">*</i></div>
                    <div class="verification-code-box input-box verification-bank clearfix fl">
                        <input class="fl" type="text" id="messageCode" maxlength="6" name="messageCode"
                               placeholder="输入验证码">
                        <a class="fl" id="sms_code">获取验证</a>
                        <div class='input-tip'></div>
                    </div>
                </div>
                <div class="btn-box item-btn">
                    <button id="reset_tel_sub" type="submit">下一步</button>
                    <button class="cancel-btn" type="button"
                            onclick="javascript:window.location='${ctx}/person/center/index.do'">返回
                    </button>
                </div>
            </div>
    </div>
    </form>
</div>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
<div class="contact-us">
    <ul>
        <li class="customer-service">
            <div class="customer-pic same-service"></div>
            <img class="code-pic none" src="${ctx}/resource/images/code.png" alt="code">
        </li>
        <li class="we-chat">
            <div class="chat-pic same-service"></div>
            <img class="num-pic none" src="${ctx}/resource/images/tel-num.png" alt="num">
        </li>
    </ul>
</div>
</body>
</html>
<<script src="${ctx}/resource/js/register/countdown.js?version=${version}"></script>