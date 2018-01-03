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
    <script src="${ctx}/resource/js/common/plugins/verify/jquery.validate.min.js?version=${version}"></script>
</head>
<body>
<div class="login-layout">
    <%@ include file="/WEB-INF/view/common/header.jsp" %>
    <div class="same-layout small">
        <div class="same-title">
            <div class="title-left fl">
                <i class="fl reset-pic"></i>
                <h2>设置支付密码</h2>
            </div>
        </div>
        <div class="same-content">
            <div class="success-box">
                <img src="${ctx}/resource/images/ok.png" alt="success">
                <p>恭喜您，设置支付密码成功！</p>
                <div class="btn-box item-btn success-btn">
                    <button onclick="javascript:window.location='${ctx}/person/center/index.do'">返回</button>
                </div>
            </div>
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
<script type="text/javascript">
    $(function () {
    });
    /**
     * 用户绑定手机号
     */
    function bindTelApply() {
        var payPwd = $("#payPwd").val();
        var payPwdAgain = $("#payPwdAgain").val();
        var messageCode = $("#messageCode").val();
        var userNo = '${userNo}';
        $("#paymentPwdSubmit").attr('disabled', true);
        $.ajax({
                type: "POST",
                url: "${ctx}/person/center/createPayPwd.do",
                data: {
                    payPwd: payPwd,
                    payPwdAgain: payPwdAgain,
                    messageCode: messageCode,
                    userNo: userNo
                },
                success: function (data) {
                    if (data.msg == 'success') {
                        reviseTelResultDisplay(data.resultUrl, $("#paymentPwdSubmit"), $("#paymentPwdIndex"));
                    } else if (data.msg == 'failure') {
                        alert("failure")
                        reviseTelResultDisplay(data.resultUrl, $("#paymentPwdSubmit"), $("#paymentPwdIndex"));
                    } else {
                        $("#paymentPwdSubmit").attr('disabled', false);
                        showPagePrompts("error", data.errorMessage);
                    }
                },
                error: function () {
                    showPagePrompts("error", "操作失败，请重试或联系管理员");
                }
            }
        )
        ;
    }

    //    function validate() {
    //        $("#paymentPwdForm").validate({
    //            onblur: true,
    //            onkeyup: false,
    //            onclick: false,
    //            errorElement: "em",
    //            rules: {
    //                payPwd: {
    //                    required: true,
    //                    maxlength: 6,
    //                },
    //                payPwdAgain: {
    //                    required: true,
    //                    maxlength: 6
    //                },
    //                messageCode: {
    //                    required: true,
    //                    maxlength: 6
    //                }
    //            },
    //            messages: {
    //                messageCode: {
    //                    required: "请输入验证码",
    //                    numberFormat: "请输入6位数字的验证码"
    //                },
    //                payPwd: {
    //                    required: "请输入支付密码",
    //                    length: "请输入6位数字组成的支付密码",
    //                    numberFormat: "请输入6位数字组成的支付密码"
    //                },
    //                payPwdAgain: {
    //                    required: "请输入支付密码",
    //                    length: "请重复输入6位数字组成的支付密码",
    //                    numberFormat: "请重复输入6位数字组成的支付密码"
    //                }
    //
    //            },
    //            errorPlacement: function (error, element) {
    //                if (element.attr("name") == "fname" || element.attr("name") == "lname") {
    //                    error.insertAfter("#lastname");
    //                } else {
    //                    error.insertAfter(element);
    //                }
    //            }
    //        })
    //    }

</script>
<script src="${ctx}/resource/js/register/countdown.js?version=${version}"></script>