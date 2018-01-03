<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>提现</title>
    <link href="${ctx}/resource/images/icon.ico" rel="shortcut icon">
</head>
<body>
<div class="login-layout">
    <%@ include file="/WEB-INF/view/common/header.jsp" %>
    <form id="confirm">
    <input type="hidden" id="data" value='${cashTrialListStr}'/>
    <input type="hidden" id="userType" value='${userType}'/>
    <input type="hidden" id="name" value='${name}'/>
    <input type="hidden" id="orgName" value='${orgName}'/>
    <input type="hidden" id="paramList" value='${paramList}'/>
    <div class="same-layout">
        <div class="same-title">
            <div class="title-left withdrawals-title fl">
                <i class="fl"></i>
                <h2>确认提现金额</h2>
            </div>
        </div>
        <div class="same-content bottom-content withdrawals-content">
            <div class="withdrawals-top">
                <ul class="clearfix">
                    <li class="same-width">账户数量</li>
                    <li class="same-width2">提现金额</li>
                    <li class="same-width3">提现费率</li>
                    <li class="same-width3">提现手续费</li>
                    <li class="same-width2">到账金额</li>
                    <li class="same-width2">总金额</li>
                </ul>
            </div>
            <div class="withdrawals-bottom clearfix">
                <div class="table-left same-width fl">${accountNum}</div>
                <div class="table-middle fl">
                    <ul class="cash-withdrawal-amount same-width2">
                        <c:forEach items="${cashTrialList}" var="cashTrial" varStatus="status">
                            <li>${cashTrial.ccySymbol}${cashTrial.withdrawAmt}</li>
                        </c:forEach>
                    </ul>
                    <ul class="cash-rate same-width3">
                        <c:forEach items="${cashTrialList}" var="cashTrial" varStatus="status">
                            <li>${cashTrial.transferRate}%</li>
                        </c:forEach>
                    </ul>
                    <ul class="commission-fee same-width3">
                        <c:forEach items="${cashTrialList}" var="cashTrial" varStatus="status">
                            <li>${cashTrial.ccySymbol}${cashTrial.withdrawFee}</li>
                        </c:forEach>
                    </ul>
                    <ul class="credited-amount same-width2">
                        <c:forEach items="${cashTrialList}" var="cashTrial" varStatus="status">
                            <li>￥${cashTrial.destAmt}</li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="table-right same-width2 fl">￥${totalAmount}</div>
            </div>
        </div>
        <div class="same-title">
            <div class="title-left withdrawals-title fl">
                <i class="fl"></i>
                <h2>提现到银行卡</h2>
            </div>
        </div>
        <div class="same-content bottom-content">
            <div class="add-bank-card">
                <a class="add-card-btn" href="javascript:;">
                    <i class="fl"></i>
                    <span>新增银行卡</span>
                </a>
            </div>
            <div class="withdrawals-bank-card" id="bankCardList">

            </div>
            <div class="withdrawals-pwd">
                <div class="same-item clearfix">
                    <div class="withdrawals-left fl">输入支付密码</div>
                    <div class="input-box fl">
                        <input type="password" id="payPwd" name="payPwd" placeholder="请输入支付密码" maxlength="6">
                    </div>
                </div>
            </div>
            <div class="btn-box withdrawals-btn">
                <button type="submit">确认提现</button>
                <button type="button" class="cancel-btn" onclick="back()">
                    返回
                </button>
            </div>
        </div>
    </div>
    </form>
</div>

<!-- 模态框 -->
<!-- 个人 -->
<form id="personForm">
<div class="modal-box none" id="personDiv">
    <div class="add-modal-box popup-box" id="personCard">
        <div class="modal-title">新增银行卡</div>
        <div class="add-modal-content">
            <div class="forget-item clearfix">
                <div class="item-left fl">姓名</div>
                <div class="item-right fl" id="personName" name="personName">${name}</div>
            </div>
            <div class="forget-item clearfix">
                <div class="item-left fl">银行卡号</div>
                <div class="input-box fl">
                    <i></i>
                    <input type="text" name="personBankCard" id="personBankCard" placeholder="请输入银行卡号" maxlength="23">
                </div>
            </div>
            <div class="forget-item clearfix">
                <div class="item-left fl">验证码</div>
                <div class="verification-code-box input-box clearfix fl">
                    <i class="code-iocn"></i>
                    <input class="fl" name="personValidateCode" type="text" placeholder="输入验证码" maxlength="6">
                    <a class="fl" href="javascript:;" onclick="getMessageCode(this.id );"
                       id="personSmsCode">获取验证</a>
                </div>
            </div>
            <div class="btn-box modal-btn">
                <button type="submit">确认</button>
                <button type="button" class="cancel-btn" id="personCancel">取消</button>
            </div>
        </div>
    </div>
</div>
</form>

<!-- 企业 -->
<div class="modal-box none" id="enterpriseDiv">
    <div class="add-modal-box popup-box" id="enterprise">
        <div class="modal-title">新增银行卡</div>
        <div class="add-modal-content">
            <div class="bank-tab">
                <div class="same-item clearfix">
                    <div class="item-left fl">账户类型</div>
                    <div class="item-right bank-btn fl">
                        <button class="active">企业对公账户</button>
                        <button>法人银行账户</button>
                    </div>
                </div>
            </div>
            <div class="bank-tab-content">
                <div class="bank-tab-item block">
                 <form id="enterpriseForm">
                    <div class="same-item clearfix">
                        <div class="item-left fl">企业名称</div>
                        <div class="item-right fl" id="enterpriseName">${orgName}</div>
                    </div>
                    <div class="same-item clearfix">
                        <div class="item-left fl">开户行</div>
                        <div class="input-box fl">
                            <select class="select-box" name="bankOfDeposit" id="bankOfDeposit">
                                <option value="">请选择开户行</option>
                                <c:forEach items="${bankInfoDtoList}" var="o" varStatus="st">
                                    <option value=${o.bankCode}>${o.bankName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="same-item clearfix">
                        <div class="item-left fl">支行名称</div>
                        <div class="input-box fl">
                            <input type="text" name="branchBank" id="branchBank" placeholder="请输入支行名称" maxlength="64">
                        </div>
                    </div>
                    <div class="same-item clearfix">
                        <div class="item-left fl">银行卡号</div>
                        <div class="input-box fl">
                            <input type="text" id="enterpriseBankCard" name="enterpriseBankCard" placeholder="请输入银行卡号" maxlength="29">
                        </div>
                    </div>
                    <div class="same-item clearfix">
                        <div class="item-left fl">验证码</div>
                        <div class="verification-code-box input-box verification-bank clearfix fl">
                            <input class="fl" name="enterpriseValidateCode" type="text" placeholder="输入验证码" maxlength="6">
                            <a class="fl" href="javascript:;" onclick="getMessageCode(this.id );"
                               id="enterpriseSmsCode">获取验证</a>
                        </div>
                    </div>
                    <div class="btn-box modal-btn">
                        <button type="submit">确认</button>
                        <button type="button" class="cancel-btn" id="enterpriseCancel">取消</button>
                    </div>
                </form>
                </div>
                <div class="bank-tab-item">
                <form id="legalPersonForm">
                    <div class="same-item clearfix">
                        <div class="item-left fl">法人姓名</div>
                        <div class="item-right fl"id="legalPersonName">${name}</div>
                    </div>
                    <div class="same-item clearfix">
                        <div class="item-left fl">银行卡号</div>
                        <div class="input-box fl">
                            <input type="text" id="legalPersonBankCard" name="legalPersonBankCard" placeholder="请输入银行卡号" maxlength="23">
                        </div>
                    </div>
                    <div class="same-item clearfix">
                        <div class="item-left fl">验证码</div>
                        <div class="verification-code-box input-box verification-bank clearfix fl">
                            <input class="fl" name="legalPersonValidateCode" type="text" placeholder="输入验证码" maxlength="6">
                            <a class="fl" href="javascript:;" onclick="getMessageCode(this.id );"
                               id="legalPersonSmsCode">获取验证</a>
                        </div>
                    </div>
                    <div class="btn-box modal-btn">
                        <button type="submit">确认</button>
                        <button type="button" class="cancel-btn" id="legalPersonCancel">取消</button>
                    </div>
                </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
<script src="${ctx}/business/withdrawals/withdrawalsStep2.js?version=${version}"></script>
<script src="${ctx}/resource/js/register/countdown.js?version=${version}"></script>
<script src="${ctx}/business/account/js/apply-account-service.js?version=${version}"></script>
</body>
</html>