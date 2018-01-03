<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="header agreement-header login-header mw1200">
    <div class="w1200 clearfix">
        <div class="header-left fl">
            <a href="${ctx}/login/toIndexPage.do">
                <img src="${ctx}/resource/images/logo2.png" alt="logo">
            </a>
        </div>
        <div class="header-middle fl">
            <ul class="clearfix">
                <li>
                    <a id="index" href="${ctx}/login/toIndexPage.do">首页</a>
                </li>
                <li>
                    <a id="detailQuery" href="${ctx}/trade/toTradeDetailQueryPage">明细查询</a>
                </li>
                <li>
                    <a id="bankCard" href="${ctx}/withdrawBankCard/bankCardIndex.do">提现银行卡</a>
                </li>
                <li class="account-setting" id="account">
                    <a href="javascript:void(0);">账户设置</a>
                    <ul class="two-level-menu none">
                        <li>
                            <a href="${ctx}/person/center/index.do">账户信息</a>
                        </li>
                       <c:if test="${userType ==2}">
                           <li>
                               <a href="${ctx}/qualified/qualifiedQuery">持有人管理</a>
                           </li>
                       </c:if>
                        <li>
                            <a href="${ctx}/common/help" target="_blank">帮助中心</a>
                        </li>
                        <li>
                            <a href="javascript:loginOut();">退出</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
        <div class="header-right fr">
            <div class="btn-box">
                <a class="extract-btn fl" href="javascript:void(0);"
                   onclick="userWithdrawStatusQuery('/withdraw/apply','/person/center/toPaymentPwd.do');">
                    <i></i>
                    提现
                </a>
                <a class="add-btn fl" href="javascript:void(0);"
                   onclick="userRealNameStatusQuery2('/account/applyAccPage',2);">
                    <i></i>
                    添加账户
                </a>
            </div>
        </div>
    </div>
</div>