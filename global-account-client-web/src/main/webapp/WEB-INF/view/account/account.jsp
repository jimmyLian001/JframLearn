<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge，chrome=1">
    <meta name="renderer" content="webkit">
    <title>我的账户</title>
    <link rel="stylesheet" href="${ctx}/resource/css/personal-center.css?version=${version}">
    <link rel="stylesheet" href="${ctx}/resource/css/font-awesome.min.css?version=${version}">
    <link href="${ctx}/resource/css/bootstrap.min.css" rel="stylesheet" media="screen" type="text/css"/>
    <script src="${ctx}/resource/js/common/plugins/paging/jqpaginator.min.js?version=${version}"></script>
    <script src="${ctx}/resource/js/common/bootstrap.min.js?version=${version}"></script>
    <script type="text/javascript">
        $(function () {
            $("#my-account-home").addClass("main-color");
        });
    </script>
</head>
<body>
<%@ include file="/WEB-INF/view/common/header.jsp" %>
<c:set var="centerAuthUrl" value="${ctx}/person/authentication/index.do"/>
<c:if test='${userType == 1}'>
    <c:set var="authUrl" value="${ctx}/auth/personAuthPage.do?loginNo=${loginNo}&userNo=${userNo}"/>
</c:if>
<c:if test='${userType == 2}'>
    <c:set var="authUrl" value="${ctx}/auth/orgAuthPage.do?loginNo=${loginNo}&userNo=${userNo}"/>
</c:if>
<div class="user-infor">
    <div class="container clearfix w1200">
        <div class="user-infor-left fl">
            <h3 class="">
                <span class="real-name" title="${userName}">您好，${userName}</span>
                <c:choose>
                    <c:when test="${authStatus=='3'}">
                            <span class="not-pass same-icon">
                                <i></i>
                                <a href="${centerAuthUrl}">实名认证未通过</a>
                            </span>
                    </c:when>
                    <c:when test="${authStatus=='2'}">
                            <span class="pass same-icon">
                                <i class="pass-icon"></i>
                                <a style="text-decoration:none">已通过实名认证</a>
                            </span>
                    </c:when>
                    <c:when test="${authStatus=='1'}">
                        <span class="audit same-icon">
                            <i></i>
                            <a style="text-decoration:none">实名认证审核中</a>
                        </span>
                    </c:when>
                    <c:when test="${authStatus=='0'}">
                        <span class="not-pass same-icon">
                            <i></i>
                            <a href="${authUrl}">未进行实名认证</a>
                        </span>
                    </c:when>
                </c:choose>

            </h3>
            <p class="user-name">用户名：${loginNo}</p>
        </div>
        <c:if test='${authStatus!=2}'>
            <div class="apply-btn-grey fr" onclick="javascript:$('.full-mask').show()">
                <a style="text-decoration:none;">申请境外收款账户</a>
            </div>
        </c:if>
        <c:if test='${authStatus==2}'>
            <div class="apply-btn fr">
                <a href="${ctx}/account/applyAccPage" class="btn-shadow" style="text-decoration:none;a:hover;a:focus">申请境外收款账户</a>
            </div>
        </c:if>

    </div>
</div>

<div class="people-center">
    <div class="center-content w1200 clearfix">
        <div class="center-left fl">
            <div class="c-left-title">我的账户</div>
            <div class="c-left-menu">
                <p>提现速度：<span> T+0 当日到账</span></p>
                <p>账户总数：<span id="accCount"> 0 </span></p>
            </div>
        </div>

        <div class="center-right fl">
            <div class="account-wrap">
                <c:if test='${authStatus==0 || authStatus==3}'>
                    <div class="common-title clearfix">
                        <h3 class="fl">开启全球收款账户</h3>
                    </div>
                    <div class="flow-pic">
                        <img src="${ctx}/resource/images/open-flow.jpg" alt="pic">
                        <div class="flow-btn">
                            <c:if test="${authStatus==0}">
                                <a href="${authUrl}">去完成实名认证</a>
                            </c:if>
                            <c:if test="${authStatus==3}">
                                <a href="${centerAuthUrl}">去完成实名认证</a>
                            </c:if>
                        </div>
                    </div>
                </c:if>
                <div class="common-title clearfix">
                    <h3 class="fl">账户余额</h3>
                </div>
                <div class="balance-box">
                    <ul class="clearfix">
                        <li class="balance-item line fl">
                            <p class="main-color">$${accBalance.balance != null? accBalance.balance : "0.00"}</p>
                            <span>美元总金额</span>
                        </li>
                        <li class="balance-item line fl">
                            <p class="main-color">
                                ¥${accBalance.rmbBalance != null? accBalance.rmbBalance : "0.00"}</p>
                            <span>约人民币金额</span>
                        </li>
                        <c:if test="${accBalance.balance != null}">
                            <li class="balance-item fl">
                                <a href="${ctx}/withdraw/apply?withdrawCcy=${accBalance.ccy}" class="withdraw-btn-blue">提现</a>
                            </li>
                        </c:if>
                        <c:if test="${accBalance.balance == null}">
                            <li class="balance-item fl">
                                <a class="withdraw-btn" style="text-decoration:none">提现</a>
                            </li>
                        </c:if>
                    </ul>
                </div>
                <c:if test='${authStatus==2 || authStatus==1}'>
                    <div class="common-title clearfix">
                        <h3 class="fl">我的店铺</h3>
                        <div class="search-bar-new search-bar fr">
                            <input id="condition" type="text" placeholder="搜索店铺名称" maxlength="128"><span
                                id="search"></span>
                        </div>

                    </div>
                    <div class="my-shops clearfix">
                        <a href="#" class="main-color">
                            <img src="${ctx}/resource/images/amazon.png" alt="pic">
                            美国
                        </a>
                    </div>
                    <div class="entry-bottom shop-table">
                        <div class="table-box">
                            <table>
                                <thead>
                                <tr class="thead">
                                    <th>店铺名称</th>
                                    <th>境外收款账户</th>
                                    <th>可提现余额</th>
                                    <th>最新入账时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                            <div class="no-record none" id="noResult">
                                <img src="${ctx}/resource/images/no-record.png" alt="no-record">
                                <p>暂无记录，请调整查询条件</p>
                            </div>
                        </div>
                        <div class="pagination clearfix" style="margin-top: -15px;">
                            <div class="total-box fl">
                                <ul class="pagination" id="pagination1"></ul>
                            </div>
                        </div>
                        <form method="post" action="${ctx}/account/export.do" class="download-btn-global fr">
                            <input name="storeName" type="hidden"/>
                            <input id="download-btn" type="button" value="导出下载"
                                   style="margin-left: 20px;">
                        </form>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>

<c:if test='${authStatus!=2}'>
    <div class="full-mask" style="display: none">
        <div class="reg-dialog">
            <div class="reg-dialog-title">
                <span class="fl">提示信息</span>
                <span class="fr dialog-close-btn"><i class="fa fa-times"></i></span>
            </div>
            <div class="dialog-content">
                <div class="tip-container">
                    <p class="tip-msg">
                        <i class="fa fa-exclamation-circle" aria-hidden="true"></i>
                        您尚未通过实名认证,待审核通过后才能申请境外收款账号
                    </p>
                    <div class="sub-btn">
                        <a href="${centerAuthUrl}" class="btn-shadow">查看我的实名认证</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>

<%@ include file="/WEB-INF/view/common/footer.jsp" %>
<script src="${ctx}/business/account/js/accountMain.js" type="application/javascript"></script>
</body>
</html>