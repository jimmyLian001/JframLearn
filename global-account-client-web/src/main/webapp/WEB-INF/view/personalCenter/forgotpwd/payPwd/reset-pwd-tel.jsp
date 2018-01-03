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
    <script src="${ctx}/resource/js/payPwd/payPwd.js?version=${version}"></script>
    <script src="${ctx}/resource/js/register/countdown.js?version=${version}"></script>
</head>
<body>
<div class="login-layout">
    <%@ include file="/WEB-INF/view/common/header.jsp" %>

    <div class="same-layout small">
        <div class="same-title">
            <div class="title-left fl">
                <i class="fl reset-pic"></i>
                <h2>重置支付密码</h2>
            </div>
        </div>
        <div class="same-content" id="paymentPwdIndex">
            <form action="${ctx}/person/center/resetPaymentPwd/phone.do" method="post" id="pay_pwd_form">
                <div id="pagePromptsDiv" na style="display: none;text-align: center;color: red"></div>
                <div class="same-item clearfix">
                    <div class="item-left fl">用户名</div>
                    <input type="hidden" id="msgType" name="msgType" value="${msgType}">
                    <div class="item-right fl"><span>${f:secrueqaEncrypt(loginNo)}</span></div>
                </div>
                <div class="same-item clearfix">
                    <div class="item-left fl">验证码 <i style="color:red">*</i></div>
                    <div class="verification-code-box input-box verification-bank clearfix fl">
                        <input class="fl" type="text" id="messageCode" name="messageCode" maxlength="6"
                               placeholder="输入验证码">
                        <a class="fl" id="sms_code">获取验证</a>
                        <div class='input-tip'></div>
                    </div>

                </div>
                <div class="btn-box item-btn">
                    <button type="submit" id="pay_pwd_Sub">下一步</button>
                    <button class="cancel-btn" type="button"
                            onclick="javascript:window.location='${ctx}/person/center/resetPaymentPwd/first.do'">
                        返回
                    </button>
                </div>
            </form>
        </div>
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

<script src="${ctx}/resource/js/register/countdown.js?version=${version}"></script>