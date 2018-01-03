<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge，chrome=1">
    <meta name="renderer" content="webkit">
    <title>提现</title>
</head>
<script>
    function itemClick(tar) {
        if (tar.checked) {
            if ($("#user_withdraw_table").find("td input[name='withdrawBox']:enabled").length
                == $("#user_withdraw_table").find("td input[name='withdrawBox']:checked").length) {
                $("#user_withdraw_all").prop("checked", "checked");
            }
        } else {
            $("#user_withdraw_all").removeAttr("checked");
        }
        setInputEnable($("#withdraw" + $(tar).attr("checkIndex")), tar.checked)
    }

    function allClick(obj) {
        if (obj.checked) {
            $("#user_withdraw_table").find("td input[type=checkbox]").prop("checked", "checked");
        } else {
            $("#user_withdraw_table").find("td input[type=checkbox]").removeAttr("checked");
        }
        $("#user_withdraw_table").find("td input[type=checkbox]").each(function () {
            setInputEnable($("#withdraw" + $(this).attr("checkIndex")), $(this).prop("checked"))
        });
    }

    /**
     * 用户提现试算
     **/
    function withdrawFee() {
        var checkFlag = true;
        if ($("#user_withdraw_table").find("td input:checked").length == 0) {
            errorMsgShow("请至少选择一个店铺进行提现");
            checkFlag = false;
        }
        var withdrawMinAmtJson = {USD: 50, EUR: 50, JPY: 5000, GBP: 50};
        $("#user_withdraw_table").find("td input:checked").each(function () {
            var withdraw = $("#withdraw" + $(this).attr("checkIndex")).val();
            var balanceAmt = $(this).attr("balanceAmt");
            var storeName = $(this).attr("storeName");
            if (withdraw == "" || withdraw == undefined) {
                errorMsgShow("店铺【" + storeName + "】提现金额不能为空");
                checkFlag = false;
            } else if (withdrawMinAmtJson[$(this).attr("withdrawCcy")] > parseFloat(withdraw)) {
                errorMsgShow("店铺【" + storeName + "】起提金额为" + withdrawMinAmtJson[$(this).attr("withdrawCcy")]);
                checkFlag = false;
            } else if (parseFloat(withdraw) > parseFloat(balanceAmt)) {
                errorMsgShow("店铺【" + storeName + "】提现金额大于可提余额");
                checkFlag = false;
            }
        });
        if (checkFlag) {
            var arrayObj = $("#user_withdraw_table").find("td input:checked").toArray();
            var storeNoArray = $.map(arrayObj, function (n) {
                return $(n).attr("storeNo");
            }).join(",");
            var withdrawCcyArray = $.map(arrayObj, function (n) {
                return $(n).attr("withdrawCcy");
            }).join(",");
            var withdrawAmtArray = $.map(arrayObj, function (n) {
                return $("#withdraw" + $(n).attr("checkIndex")).val();
            }).join(",");
            $("#withdrawCcyArray").val(withdrawCcyArray);
            $("#withdrawAmtArray").val(withdrawAmtArray);
            $("#storeNoArray").val(storeNoArray);
            $("#withdrawForm").attr("action", "${ctx}/withdraw/withdrawFee");
            $("#withdrawForm").submit();
        }
    }

    /**
     * withdrawObj input框
     * flag true 可用，false 不可用
     **/
    function setInputEnable(withdrawObj, flag) {
        if (flag) {
            withdrawObj.css('background-color', '#f8fafe');
            withdrawObj.attr("disabled", false);
        } else {
            withdrawObj.css('background-color', '#CCCCCC');
            withdrawObj.attr("disabled", true);
        }
    }
</script>
<body>
<div class="login-layout">
    <%@ include file="/WEB-INF/view/common/header.jsp" %>
    <form method="post" id="withdrawForm">
        <input type="hidden" id="withdrawCcyArray" name="withdrawCcy"/>
        <input type="hidden" id="storeNoArray" name="storeNo"/>
        <input type="hidden" id="withdrawAmtArray" name="withdrawAmt"/>
        <div class="same-layout">
            <div class="same-title">
                <div class="title-left withdrawals-title fl">
                    <i class="fl"></i>
                    <h2>确认提现金额</h2>
                </div>
            </div>
            <div class="same-content bottom-content withdrawals-content">
                <div class="table-box withdrawals-table-box">
                    <table id="user_withdraw_table">
                        <thead>
                        <tr>
                            <th class="withdrawals-li">
                                <input type="checkbox" class="checkbox" id="user_withdraw_all"
                                       onclick="allClick(this)"/>
                                <label>店铺名称</label>
                            </th>
                            <th>收款账号</th>
                            <th>可提余额</th>
                            <th>提现金额</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${userStoreAccount}" var="storeAccount" varStatus="status">
                            <tr>
                                <c:set var="isDoing" value="0"/>
                                <c:forEach items="${userWithdrawStoreList}" var="withdrawStoreBack" varStatus="status2">
                                    <c:if test="${storeAccount.storeNo == withdrawStoreBack.storeNo}">
                                        <c:set var="isDoing" value="1"/>
                                        <c:set var="backWithdrawAmt" value="${withdrawStoreBack.withdrawAmt}"/>
                                    </c:if>
                                </c:forEach>
                                <c:if test="${isDoing=='0'}">
                                    <td class="withdrawals-li">
                                        <input type="checkbox" name="withdrawBox" onclick='itemClick(this);'
                                               class="checkbox" storeNo="${storeAccount.storeNo}"
                                               withdrawCcy="${storeAccount.ccy}" balanceAmt="${storeAccount.balanceAmt}"
                                               checkIndex="${status.index}" storeName="${storeAccount.storeName}"/>
                                        <label>${storeAccount.storeName}</label>
                                    </td>
                                    <td>${storeAccount.bankAccNo}</td>
                                    <td>
                                        <c:if test="${storeAccount.ccy == 'USD'}">$</c:if>
                                        <c:if test="${storeAccount.ccy == 'JPY'}">￥</c:if>
                                        <c:if test="${storeAccount.ccy == 'EUR'}">€</c:if>
                                        <c:if test="${storeAccount.ccy == 'GBP'}">￡</c:if>
                                            ${storeAccount.balanceAmt}
                                    </td>
                                    <td class="tbody-last-td">
                                        <input type="text" maxlength="15" disabled style="background-color:#CCCCCC"
                                               id="withdraw${status.index}"
                                               t_value="" o_value=""
                                               withdrawCcy="${storeAccount.ccy}"
                                               onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
                                               onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"/>
                                    </td>
                                </c:if>
                                <c:if test="${isDoing=='1'}">
                                    <td class="withdrawals-li">
                                        <input type="checkbox" name="withdrawBox" checked onclick='itemClick(this);'
                                               class="checkbox" storeNo="${storeAccount.storeNo}"
                                               withdrawCcy="${storeAccount.ccy}" balanceAmt="${storeAccount.balanceAmt}"
                                               checkIndex="${status.index}" storeName="${storeAccount.storeName}"/>
                                        <label>${storeAccount.storeName}</label>
                                    </td>
                                    <td>${storeAccount.bankAccNo}</td>
                                    <td>
                                        <c:if test="${storeAccount.ccy == 'USD'}">$</c:if>
                                        <c:if test="${storeAccount.ccy == 'JPY'}">￥</c:if>
                                        <c:if test="${storeAccount.ccy == 'EUR'}">€</c:if>
                                        <c:if test="${storeAccount.ccy == 'GBP'}">￡</c:if>
                                            ${storeAccount.balanceAmt}
                                    </td>
                                    <td class="tbody-last-td">
                                        <input type="text" maxlength="15" id="withdraw${status.index}"
                                               value="${backWithdrawAmt}"
                                               t_value="" o_value=""
                                               withdrawCcy="${storeAccount.ccy}"
                                               onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
                                               onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"/>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="btn-box login-btn step-btn">
                    <button type="button" onclick="withdrawFee();">下一步</button>
                </div>
            </div>
        </div>
    </form>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
</body>
</html>