<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge，chrome=1">
    <meta name="renderer" content="webkit">
    <title>银行卡列表首页</title>
    <script type="text/javascript">
        $(function () {
            $("#bankCard").addClass("active");
        });
    </script>
    <style type="text/css">
        .line-limit-length {
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
    </style>
</head>
<body>
<div id="reviseTelLastStepDiv">
    <div class="login-layout">
        <%@ include file="/WEB-INF/view/common/header.jsp" %>
        <div class="same-layout">
            <div class="same-title">
                <div class="title-left fl">
                    <i class="fl"></i>
                    <h2>提现银行卡管理</h2>
                </div>
                <div class="title-right fr">
                    <a href="javascript:addBankCard()">
                        <em class="bold">+</em>
                        <span>添加提现银行卡</span>
                    </a>
                </div>
            </div>
            <input type="hidden" name="userType" id="userType">
            <input type="hidden" name="recordNo" id="recordNo">
            <div class="same-content" style="overflow-y: auto;height:410px;">
                <ul class="clearfix bank-ul">
                    <c:forEach items="${page}" var="o" varStatus="st">
                        <li>
                            <div class="bank-list">
                                <div class="bank-list-top clearfix">
                                    <div class="bank-top-left fl">
                                        <div class="bank-logo fl">
                                            <img src="${ctx}/resource/images/bank/${o.bankCode}.png" alt="bank"
                                                 onerror="onImgError(this)">
                                        </div>
                                        <div class="bank-name fl">
                                            <p class="line-limit-length" title=${o.bankName}><em>${o.bankName}</em></p>
                                            <p class="line-limit-length" title=${o.cardHolder}><em>${o.cardHolder}</em>
                                            </p>
                                        </div>
                                    </div>
                                    <div class="btn-box delete-btn fr">
                                        <button onclick="deleteBankCard('${o.recordNo}')">
                                            删除
                                        </button>
                                    </div>
                                </div>
                                <div class="bank-list-bottom">
                                    <span>${o.cardNo}</span>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>

    </div>

    <%@ include file="/WEB-INF/view/common/footer.jsp" %>
</div>
</body>
</html>
<script type="application/javascript">

    $(function () {
        $('#userType').val("${userType}");
    });

    /**
     * 添加银行卡
     */
    function addBankCard() {
        var userType = $('#userType').val();
        var requestUrl;
        if (userType == 1) {
            requestUrl = ctx + "/withdrawBankCard/addBankCardPersonalIndex.do";
        } else if (userType == 2) {
            requestUrl = ctx + "/withdrawBankCard/addBankCardCompanyIndex.do";
        }
        //判断是否实名认证成功
        userRealNameStatusQuery(requestUrl, 1, '${userInfoNo}');
    }

    /**
     * 删除银行卡页面跳转
     * @param recordNo 银行卡记录号
     */
    function deleteBankCard(recordNo) {
        window.location.href = ctx + "/withdrawBankCard/deleteBankCardIndex.do?recordNo=" + recordNo;
    }
</script>
<script src="${ctx}/business/bankcard/bankCardCommon.js?version=${version}"></script>
<script src="${ctx}/business/account/js/apply-account-service.js?version=${version}"></script>