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
        <form method="post" action="${ctx}/person/center/modifyQuestion/modify.do">
            <div class="same-content">
                <div class="reset-pwd-content questions-info">

                    <div class="forget-item clearfix">
                        <div class="item-left fl">安全保护问题1</div>
                        <div class="item-right fl">
                            <select class="select-box" id="questionNoOne" name="questionNoOne" inputNo="answer1">
                                <option value="" selected>请选择安全保护问题</option>
                                <c:forEach items="${sysQuestionList}" var="questions" varStatus="status">
                                    <c:if test="${questions.questionType == 1}">
                                        <option value="${questions.questionNo}">${questions.question}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="forget-item clearfix">
                        <div class="item-left fl">问题答案<i style="color:red">*</i></div>
                        <div class="input-box fl">
                            <input id="answer1" name="params[]" type="text" placeholder="请填写答案" maxlength="64">
                        </div>
                    </div>


                    <div class="forget-item clearfix">
                        <div class="item-left fl">安全保护问题2</div>
                        <div class="item-right fl">
                            <select class="select-box" id="questionNoTwo" name="questionNoTwo" inputNo="answer2">
                                <option value="" selected>请选择安全保护问题</option>
                                <c:forEach items="${sysQuestionList}" var="questions" varStatus="status">
                                    <c:if test="${questions.questionType == 2}">
                                        <option value="${questions.questionNo}">${questions.question}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="forget-item clearfix">
                        <div class="item-left fl">问题答案<i style="color:red">*</i></div>
                        <div class="input-box fl">
                            <input id="answer2" name="params[]" type="text" placeholder="请填写答案" maxlength="64">
                        </div>
                    </div>


                    <div class="forget-item clearfix">
                        <div class="item-left fl">安全保护问题3</div>
                        <div class="item-right fl">
                            <select class="select-box" id="questionNoThree" name="questionNoThree" inputNo="answer3" >
                                <option value="" selected>请选择安全保护问题</option>
                                <c:forEach items="${sysQuestionList}" var="questions" varStatus="status">
                                    <c:if test="${questions.questionType == 3}">
                                        <option value="${questions.questionNo}">${questions.question}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="forget-item clearfix">
                        <div class="item-left fl">问题答案<i style="color:red">*</i></div>
                        <div class="input-box fl">
                            <input id="answer3" name="params[]" type="text" placeholder="请填写答案" maxlength="64">
                        </div>
                    </div>


                    <div class="btn-box">
                        <button>下一步</button>
                        <button class="cancel-btn" type="button"
                                onclick="javascript:window.location='${ctx}/person/center/modifyQuestion/index.do'"> 返回
                        </button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
</body>
</html>