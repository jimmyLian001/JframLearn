<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge，chrome=1">
    <meta name="renderer" content="webkit">
    <title>安全设置</title>
    <link rel="stylesheet" href="${ctx}/resource/css/personal-center.css?version=${version}">
    <script src="${ctx}/resource/js/common/common.js?version=${version}"></script>
    <script src="${ctx}/resource/js/userInfo/reviseTel.js?version=${version}"></script>
</head>
<body>
<div class="people-center">
    <div class="center-content w1200 clearfix">
        <div class="center-right fl">
            <div class="c-r-title">设置绑定手机号</div>
            <div class="revise-content pt40">
                <div class="reset-result">
                    <img src="${ctx}/resource/images/error.png" alt="pic">
                    <h3>设置绑定手机号失败！！！</h3>
                    <h4>${errorMessage}</h4>
                    <div class="sub-btn result-btn">
                        <button class="btn-shadow" onclick="javascript:window.location='${ctx}/person/center/index.do'">
                            点我继续设置
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>