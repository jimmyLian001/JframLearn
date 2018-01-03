<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="header agreement-header login-header mw1200">
    <div class="w1200 clearfix">
        <div class="header-left fl">
            <a href="${ctx}/index">
                <img src="${ctx}/resource/images/logo2.png" alt="logo">
            </a>
        </div>
        <div class="header-middle fl">
            <ul class="clearfix">
                <li>
                    <a href="javascript:;" style="font-size: 14px;color: #aaaaaa;">首页</a>
                </li>
                <li>
                    <a class="active2" href="${ctx}/trade/toTradeDetailQueryPage" style="font-size: 14px">明细查询</a>
                </li>
                <li>
                    <a href="javascript:window.location.href = '${ctx}/withdrawBankCard/bankCardIndex.do';"
                       style="font-size: 14px;color: #aaaaaa;">提现银行卡</a>
                </li>
                <li>
                    <a href="javascript:;" style="font-size: 14px;color: #aaaaaa;">账户设置</a>
                </li>
            </ul>
        </div>
        <div class="header-right fr">
            <div class="btn-box">
                <a class="extract-btn fl" id="withdrawals">
                    <i></i>
                    提取现金
                </a>
                <a class="add-btn fl" href="${ctx}/account/applyAccPage">
                    <i></i>
                    添加账户
                </a>
            </div>
        </div>
    </div>
</div>
<script type="application/javascript">
    $(function () {
        checkButton();
    });

    function checkButton() {
       /* $.ajax({
            url: ctx + "/withdrawals/checkButton.do",
            type: "POST",
            success: function (data) {
                if (data.flag) {
                    $("#withdrawals").attr('href', "${ctx}/register/register.do");
                } else {
                    $("#withdrawals").css('background-color', "#accaea");
                }
            }
        });*/
    }
</script>