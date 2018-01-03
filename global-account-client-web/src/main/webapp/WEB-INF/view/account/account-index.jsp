<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>账户</title>
    <script type="text/javascript">
        $(function () {
            $("#index").addClass("active");
            //默认显示币种
            $(".ccyList a[ccy='${showCcy}']").addClass("active");
        });
    </script>
    <script src="${ctx}/business/account/js/accountMain.js" type="application/javascript"></script>
</head>
<body>
<c:if test='${userType == 1}'>
    <c:set var="centerAuthUrl" value="${ctx}/auth/personAuthEditPage.do"/>
    <c:set var="authUrl1" value="${ctx}/auth/1/personAuthAddPage.do"/>
    <c:set var="authUrl2" value="${ctx}/auth/2/personAuthAddPage.do"/>
</c:if>
<c:if test='${userType == 2}'>
    <c:set var="centerAuthUrl" value="${ctx}/auth/1/orgAuthEditPage.do"/>
    <c:set var="authUrl1" value="${ctx}/auth/1/orgAuthAddPage.do"/>
    <c:set var="authUrl2" value="${ctx}/auth/2/orgAuthAddPage.do"/>
</c:if>
<div class="login-layout">
    <%@ include file="/WEB-INF/view/common/header.jsp" %>
    <input type="hidden" id="centerAuthUrl" value="${ctx}${centerAuthUrl}">
    <div class="same-layout">
        <div class="same-title">
            <div class="title-left title-pic fl">
                <i class="fl"></i>
                <h2>账户余额</h2>
            </div>
        </div>
        <div class="same-content bottom-content">
            <ul class="clearfix account-balance">
                <li>
                    <p class="total-amount">美金总金额</p>
                    <p class="main-color money bold">$${accBalance.balance != null? accBalance.balance : "0.00"}</p>
                    <p>≈￥${accBalance.rmbBalance != null? accBalance.rmbBalance : "0.00"}</p>
                </li>
                <c:if test='${userType == 2}'>
                    <li>
                        <p class="total-amount">欧元总金额</p>
                         <p class="main-color money bold">€${eurAccBalance.balance != null? eurAccBalance.balance : "0.00"}</p>
                         <p>≈￥${eurAccBalance.rmbBalance != null? eurAccBalance.rmbBalance : "0.00"}</p>
                    </li>
                    <li>
                        <p class="total-amount">英镑总金额</p>
                        <p class="main-color money bold">￡0.00</p>
                        <p>≈￥0.00</p>
                    </li>
                </c:if>
                <li>
                    <p class="total-amount">日元总金额</p>
                    <p class="main-color money bold">¥0.00</p>
                    <p>≈￥0.00</p>
                </li>
            </ul>
        </div>
        <div class="same-title">
            <div class="title-left title-pic fl">
                <i class="fl shop-pic"></i>
                <h2>我的店铺</h2>
            </div>
            <div class="title-right search-input fr">
                <input id="condition" type="text" placeholder="搜索店铺名称">
                <span id="search"></span>
            </div>

        </div>
        <div class="same-content bottom-content">
            <div class="shop-content">
                <ul class="clearfix shop-ul ccyList">
                    <li>
                        <a ccy="USD">
                            <p>美国站</p>
                            <img src="${ctx}/resource/images/amazon.png" alt="amazon">
                        </a>
                    </li>
                    <c:if test='${userType == 2}'>
                        <li>
                            <a ccy="EUR">
                                <p>欧洲站</p>
                                <img src="${ctx}/resource/images/amazon.png" alt="amazon">
                            </a>
                        </li>
                        <li>
                            <a ccy="GBP">
                                <p>英国站</p>
                                <img src="${ctx}/resource/images/amazon.png" alt="amazon">
                            </a>
                        </li>
                    </c:if>
                    <li>
                        <a ccy="JPY">
                            <p>日本站</p>
                            <img src="${ctx}/resource/images/amazon.png" alt="amazon">
                        </a>
                    </li>
                </ul>
                <c:if test='${ authStatus ==0}'>
                    <div class="btn-box login-btn">
                        <c:if test="${ authStatus ==0}">
                            <button onclick=" window.location='${ctx}${authUrl1}'">去完成实名认证</button>
                        </c:if>
                    </div>
                </c:if>
            </div>
        </div>


        <c:if test='${authStatus==2 || authStatus==1 || authStatus==3}'>
            <c:if test="${accountFlag}">
                <div class="same-title">
                    <ul class="clearfix shop-info-ul shop-info">
                        <li class="first">店铺名称</li>
                        <li class="second">境外收款账户</li>
                        <li class="third">可提现金额</li>
                        <li class="fourth">提现中金额</li>
                        <li class="fifth">操作</li>
                    </ul>
                </div>
                <div class="same-content bottom-content shop-info-content shop-info">
                    <table>
                        <tbody></tbody>
                    </table>
                </div>
            </c:if>
            <c:if test="${!accountFlag}">
                <div id="noAccountShow" class="same-content bottom-content">
                    <div class="shop-content">
                        <div class="btn-box login-btn">
                            <button onclick="javascript:window.location='${ctx}/account/applyAccPage'">暂无账户，前往开通
                            </button>
                        </div>
                    </div>
                </div>
            </c:if>
        </c:if>
    </div>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
</body>
</html>