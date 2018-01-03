<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge，chrome=1">
    <meta name="renderer" content="webkit">
    <title>申请境外收款账户</title>
    <link rel="stylesheet" href="${ctx}/resource/css/personal-center.css?version=${version}">
    <link rel="stylesheet" href="${ctx}/resource/css/account.css?version=${version}">
    <script src="${ctx}/resource/js/common/plugins/verify/jquery.validate.min.js?version=${version}"></script>
    <script src="${ctx}/resource/js/common/plugins/verify/messages_zh.min.js?version=${version}"></script>
    <script src="${ctx}/business/account/js/apply-account-service.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
            $("#my-account-home").addClass("main-color");
        });
    </script>
</head>
<body>
<%@ include file="/WEB-INF/view/common/header.jsp" %>
<form id="infoForm" action="${ctx}/account/bindShop.do" method="get" enctype="multipart/form-data">
    <div class="reg-wrap pt20">
        <div class="breadcrumb w1200">
            <a href="${ctx}/account/index.do">我的账户</a>
            <span> > </span>
            <a href="javascript:;" class="current-details">申请境外收款账户</a>
        </div>
        <div class="w1200">
            <div class="apply-con">
                <div class="common-title clearfix">
                    <h3>申请境外收款账户</h3>
                </div>
                <div class="virtual-box">
                    <div class="virtual-cards clearfix">
                        <div class="cards-title">
                            <span>统一提现至</span>
                        </div>
                        <div class="cards-list ">
                            <c:forEach var="bankInfo" items="${account.bankInfo}">
                                <div class="cards-item ng-scope" ng-repeat="item in bankList">
                                    <span class="bank-infor">
                                       <img class="bankicon" src="${ctx}/resource/images/bank/${bankInfo.bankCode}.png"
                                            alt="pic" onerror="onImgError(this)"/>
                                            <c:out value="${bankInfo.cardName}"/>
                                        <p class="card-no ng-binding"><c:out value="${bankInfo.cardNo}"/></p>
                                        <p class="card-name ng-binding"><c:out value="${bankInfo.userName}"/></p>
                                     </span>
                                </div>
                            </c:forEach>
                            <div>
                                <input id="authStatus" name="authStatus" size="10" style="display: none;"
                                       value="${account.authStatus}"/>
                            </div>
                            <div class="cards-item addbankcard ng-scope"
                                 ng-if="mainAffiliateInfo.kycStatus =='OPS_AUTH_SUCCESS'">
                                <a ui-sref="bankcard.bind" target="_blank"
                                   href="${ctx}/withdrawBankCard/bankCardIndex.do"><span></span>添加新的提现银行卡</a>
                            </div>
                        </div>
                    </div>
                    <div class="virtual-item">
                        <div class="virtual-row clearfix">
                            <div class="virtual-col-6"> 境外账户开户主体</div>
                            <div class="virtual-col-18 ">
                                <div class="card-sm"><span class="ng-binding"> ${account.userName}</span></div>
                            </div>

                            </span>
                        </div>
                        <div class="virtual-row clearfix">
                            <div class="virtual-col-6">申请收款账户币种</div>
                            <div class="virtual-col-18">
                                <div class=" card ng-scope disabled" ng-repeat="item in apply.country"
                                     ng-class="{active: item.isActive, disabled: item.disabled}"
                                     ng-click="vm.selectAccountHandle(item)">
                                    <a href="javascript:;" class="card-account"> <img
                                            src="${ctx}/resource/images/USD.png" alt="pic"> 美元收款账户</a>
                                </div>
                            </div>
                        </div>
                        <div class="ui-form-item">
                            <div class="arg-link">
                                <div><p style="color: #ff2628;align-content: center">${errormsg}</p></div>
                            </div>
                            <div class="sub-btn">
                                <button id="applybnt">提交</button>
                                <span class="back-btn"
                                      onclick="javascript:window.location='${ctx}/account/index.do'">返回</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
</form>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
</body>
</html>
