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
    <script src="${ctx}/resource/js/security/resetLogin.js?version=${version}"></script>
</head>
<body>
<div class="login-layout">
    <%@ include file="/WEB-INF/view/common/contact.jsp" %>
    <div class="same-layout small">
        <div class="same-title">
            <div class="title-left fl">
                <i class="fl reset-pic"></i>
                <h2>重置登录密码</h2>
            </div>
        </div>
        <form method="post" action="${ctx}/resetPwd/resetPassword/validateAnswer.do">
            <input type="hidden" name="loginNo" value="${loginNo}"/>
            <div class="same-content">
                <div class="reset-pwd-content questions-info">
                    <c:forEach items="${questions}" var="question" varStatus="status">
                        <div class="forget-item clearfix">
                            <div class="item-left fl">安全保护问题${status.count}</div>
                            <div class="item-right fl">
                                <span>${question.question}</span>
                            </div>
                        </div>
                        <div class="forget-item clearfix">
                            <div class="item-left fl">问题答案<i style="color:red">*</i></div>
                            <div class="input-box fl">
                                <input id="${question.questionNo}" name="params[${question.questionNo}]" type="text"
                                       placeholder="请填写答案" maxlength="64">
                            </div>
                        </div>
                    </c:forEach>
                    <div class="btn-box">
                        <button>下一步</button>
                        <button class="cancel-btn" type="button" onclick="window.history.go(-1)">返回
                        </button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<script>

    $(function () {

        function addRule() {
            <c:forEach items="${questions}" var="question">
            $("#${question.questionNo}").rules("add", {
                required: true,
                messages: {
                    required: "请填写答案"
                }
            });
            </c:forEach>
        }

        addRule();
    })
</script>

<%@ include file="/WEB-INF/view/common/footer.jsp" %>
</body>
</html>