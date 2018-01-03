<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>个人用户注册</title>
    <link href="${ctx}/resource/images/icon.ico" rel="shortcut icon">
</head>
<body>
<div class="login-layout register-layout">
    <%@ include file="/WEB-INF/view/register/registerHead.jsp" %>
    <div class="register-content forget-content question-content">
        <form id="personalQuestionForm" action="#" method="post">
            <input type="hidden" id="loginNo" name="loginNo" value="${loginNo}">
            <input type="hidden" id="loginPwd" name="loginPwd" value="${loginPwd}">
            <div class="login-title">个人用户注册</div>
            <div class="questions-info">
            <div class="forget-item clearfix">
                <div class="item-left fl">安全保护问题1</div>
                <div class="input-box fl">
                    <select class="select-box" id="questionNoOne" name="questionNoOne">
                        <option value="" selected>请选择安全保护问题</option>
                        <c:forEach items="${resultList}" var="resultList">
                            <c:if test="${resultList.questionType == 1 }">
                                <option value="${resultList.questionNo}">${resultList.question}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="forget-item clearfix">
                <div class="item-left fl">问题答案</div>
                <div class="input-box fl">
                    <input type="text" id="answerOne" name="answerOne" maxlength="64"
                           autocomplete="off" placeholder="请输入答案">
                </div>
            </div>
            <div class="forget-item clearfix">
                <div class="item-left fl">安全保护问题2</div>
                <div class="input-box fl">
                    <select class="select-box" id="questionNoTwo" name="questionNoTwo">
                        <option value="" selected>请选择安全保护问题</option>
                        <c:forEach items="${resultList}" var="resultList">
                            <c:if test="${resultList.questionType == 2}">
                                <option value="${resultList.questionNo}">${resultList.question}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="forget-item clearfix">
                <div class="item-left fl">问题答案</div>
                <div class="input-box fl">
                    <input type="text" id="answerTwo" name="answerTwo" maxlength="64"
                           utocomplete="off" placeholder="请输入答案">
                </div>
            </div>
            <div class="forget-item clearfix">
                <div class="item-left fl">安全保护问题3</div>
                <div class="input-box fl">
                    <select class="select-box" id="questionNoThree" name="questionNoThree">
                        <option value="" selected>请选择安全保护问题</option>
                        <c:forEach items="${resultList}" var="resultList">
                            <c:if test="${resultList.questionType == 3}">
                                <option value="${resultList.questionNo}">${resultList.question}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="forget-item clearfix">
                <div class="item-left fl">问题答案</div>
                <div class="input-box fl">
                    <input type="text" id="answerThree" name="answerThree" maxlength="64"
                           autocomplete="off" placeholder="请输入答案">
                </div>
            </div>
        </div>
        </form>
        <div class="btn-box forget-btn login-btn">
            <button onclick="submitCreate()" id="submitCreate">下一步</button>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
</body>
</html>
<script src="${ctx}/business/register/js/register.js?version=${version}"></script>
<script src="${ctx}/business/register/js/registerPersonal.js?version=${version}"></script>

