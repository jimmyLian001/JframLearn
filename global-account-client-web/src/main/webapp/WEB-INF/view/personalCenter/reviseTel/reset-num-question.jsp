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

    <div class="same-layout small">
        <div class="same-title">
            <div class="title-left fl">
                <i class="fl reset-pic"></i>
                <h2>修改绑定手机号</h2>
            </div>
        </div>
        <form method="post" action="${ctx}/userTel/resetPhoneNo/question.do" id="form-tel">
            <input type="hidden" id="serviceType" name="serviceType" value="${serviceType}">
            <input type="hidden" id="oldMobile" value="${oldMobile}">
            <div class="same-content">
                <div id="pagePromptsDiv" na style="display: none;text-align: center;color: red"></div>
                <div class="reset-pwd-content questions-info">
                    <div class="forget-item clearfix">
                        <div class="item-left fl">当前绑定手机号</div>
                        <div class="input-box fl">
                            <input type="text" id="currentPhoneNumber" name="currentPhoneNumber" maxlength="11"
                                   placeholder="请补全以下号码">
                            <div class="input-tip">
                                <s>请补全<span>${f:secrueqaEncrypt(oldMobile)}</span>的手机号</s>
                            </div>
                        </div>
                    </div>
                    <c:forEach items="${questions}" var="que" varStatus="q">
                        <input name="modules[${q.index}].questionNo" type="hidden" value="${que.questionNo}"/>
                        <div class="forget-item clearfix">
                            <div class="item-left fl">安全保护问题${q.index+1}</div>
                            <div class="item-right fl">
                                <span>${que.question}</span>
                            </div>
                        </div>
                        <div class="forget-item clearfix">
                            <div class="item-left fl">问题答案 <i style="color:red">*</i></div>
                            <div class="input-box fl">
                                <input type="text" name="modules[${q.index}].answer" id="${que.questionNo}"
                                       maxlength="64"
                                       placeholder="请输入安全保护问题答案">
                            </div>
                        </div>
                    </c:forEach>
                    <div class="btn-box">
                        <button id="reset_tel_sub" type="submit">下一步</button>
                        <button class="cancel-btn" type="button"
                                onclick="javascript:window.location='${ctx}/userTel/resetPhoneNo/apply.do'">
                            返回
                        </button>
                    </div>
                </div>
            </div>
        </form>

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
    function addTelRule() {
        <c:forEach items="${questions}" var="question">
        $("#${question.questionNo}").rules("add", {
            required: true,
            messages: {
                required: "请输入安全保护问题答案"
            }
        });
        </c:forEach>
    }
</script>