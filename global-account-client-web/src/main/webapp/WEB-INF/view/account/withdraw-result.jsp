<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="../common/common.jsp"/>
    <script type="text/javascript">
        $(function () {
            $("#my-account-home").addClass("main-color");
        });
    </script>
</head>
<body>
<jsp:include page="../common/header.jsp"/>
<div class="reg-wrap pt20">
    <div class="breadcrumb w1200">
        <a href="${ctx}/account/index.do">我的账户</a>
        <span> > </span>
        <a href="javascript:;" class="current-details">提现</a>
    </div>
    <div class="w1200">
        <div class="apply-con">
            <div class="common-title clearfix">
                <h3>提现到银行卡</h3>
            </div>
            <div class="revise-content pt40">
                <div class="reset-result">
                    <img src="${ctx}/resource/images/ok.png" alt="pic">
                    <h3>提现申请成功,请耐心等待！</h3>
                    <div class="sub-btn result-btn">
                        <button class="btn-shadow" onclick="javascript:window.location='${ctx}/withdraw/apply?withdrawCcy=${withdrawCcy}'">前往提现到银行卡
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>