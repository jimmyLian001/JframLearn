<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>提现银行卡</title>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
    <link href="${ctx}/resource/images/icon.ico" rel="shortcut icon">
</head>
<body>
<div class="login-layout">
    <%@ include file="/WEB-INF/view/common/header.jsp" %>
    <div class="same-layout small">
        <div class="same-title">
            <div class="title-left fl">
                <i class="fl"></i>
                <h2>提现银行卡管理</h2>
            </div>
        </div>
        <div class="same-content small-content">
            <div class="bank-tab">
                <div class="same-item clearfix">
                    <div class="item-left fl">账户类型</div>
                    <div class="item-right bank-btn fl">
                        <button class="active" onclick=clearLegalPersonDisplay()>企业对公账户</button>
                        <button onclick=clearCompanyDivDisplay()>法人银行账户</button>
                    </div>
                </div>
            </div>
            <div class="bank-tab-content">
                <div class="bank-tab-item block">
                    <div class="same-item clearfix">
                        <div class="item-left fl">企业名称</div>
                        <div class="item-right fl">${companyName}</div>
                    </div>
                    <div class="same-item clearfix">
                        <div class="item-left fl">开户行</div>
                        <div class="input-box fl">
                            <select class="select-box" id="bankCode" name="bankCode"
                                    onblur="checkBankCode()" onfocus="hiddenBankCode()">
                                <option value="" class="select-default">请选择开户行</option>
                                <c:forEach items="${sysBankPage}" var="o" varStatus="st">
                                    <option value=${o.bankCode}>${o.bankName}</option>
                                </c:forEach>
                            </select>
                            <div class="input-tip none" id="bankCodeErr">
                                <em class="fl"></em>
                                <span>开户行不能为空</span>
                            </div>
                        </div>
                    </div>
                    <div class="same-item clearfix">
                        <div class="item-left fl">支行名称</div>
                        <div class="input-box fl">
                            <input type="text" maxlength="64" id="bankBranchName" name="bankBranchName" placeholder="请输入支行名称"
                                   onblur="checkBankBranchName()" onfocus="hiddenBankBranchName()">
                            <div class="input-tip none" id="bankBranchNameErr">
                                <em class="fl"></em>
                                <span>支行名称不能为空</span>
                            </div>
                        </div>
                    </div>
                    <div class="same-item clearfix">
                        <div class="item-left fl">银行卡号</div>
                        <div class="input-box fl">
                            <input type="text" maxlength="29" id="bankCardNo" name="bankCardNo" placeholder="请输入银行卡号"
                                   onblur="checkBankCard()" onfocus="hiddenBankCardErr()">
                            <div class="input-tip none" id="bankCardErr">
                                <em class="fl"></em>
                                <span>银行卡号不能为空/输入格式不正确</span>
                            </div>
                        </div>
                    </div>
                    <div class="same-item clearfix">
                        <div class="item-left fl">验证码</div>
                        <div class="verification-code-box input-box verification-bank clearfix fl">
                            <input class="fl" type="text" maxlength="6" placeholder="输入验证码" id="messageCode"
                                   name="messageCode"
                                   onblur="checkValideCode()" onfocus="hiddenPhoneCode()">
                            <a class="fl" href="#" onclick="getMessageCode(this.id ,'addCompanyBankCard');"
                               id="sms_Code">获取验证码</a>
                            <div class="input-tip none" id="PhoneCodeErr">
                                <em class="fl"></em>
                                <span>验证码不能为空/输入格式不正确</span>
                            </div>
                        </div>
                    </div>
                    <div class="btn-box item-btn">
                        <button onclick="addBankCardCompanyToPublish()" id="addBankCardSubmit">确认</button>
                        <button class="cancel-btn" type="button"
                                onclick="javascript:window.location='${ctx}/withdrawBankCard/bankCardIndex.do'">返回
                        </button>
                    </div>
                </div>
                <div class="bank-tab-item">
                    <div class="same-item clearfix">
                        <div class="item-left fl">企业法人姓名</div>
                        <div class="item-right fl">${name}</div>
                    </div>
                    <div class="same-item clearfix">
                        <div class="item-left fl">银行卡号</div>
                        <div class="input-box fl">
                            <input type="text" maxlength="23" id="bankCardNoLegalPerson" name="bankCardNoLegalPerson"
                                   placeholder="请输入银行卡号"
                                   onblur="checkBankCardPerson()" onfocus="hiddenBankCardPersonErr()">
                            <div class="input-tip none" id="bankCardNoPersonErr">
                                <em class="fl"></em>
                                <span>银行卡号不能为空/输入格式不正确</span>
                            </div>
                        </div>
                    </div>
                    <div class="same-item clearfix">
                        <div class="item-left fl">验证码</div>
                        <div class="verification-code-box input-box verification-bank clearfix fl">
                            <input class="fl" type="text" maxlength="6" placeholder="输入验证码" id="messageCodeLegalPerson"
                                   name="messageCodeLegalPerson"
                                   onblur="checkValideCodePerson()" onfocus="hiddenPhoneCodePerson()">
                            <a class="fl" href="#" onclick="getMessageCode(this.id ,'addPersonBankCard');"
                               id="smsCode">获取验证码</a>
                            <div class="input-tip none" id="PhoneCodeErrPerson">
                                <em class="fl"></em>
                                <span>验证码不能为空/输入格式不正确</span>
                            </div>
                        </div>
                    </div>
                    <div class="btn-box item-btn">
                        <button onclick="addBankCardCompanyLPCompany()" id="addBankCardLegalPersonSubmit">确认</button>
                        <button class="cancel-btn" type="button"
                                onclick="javascript:window.location='${ctx}/withdrawBankCard/bankCardIndex.do'">返回
                        </button>
                    </div>
                </div>
                <div><input style="display:none;" id="name" name="name" value="${name}"></div>
                <div><input style="display:none;" id="currentPhoneNumber" name="currentPhoneNumber"
                            value="${currentPhoneNumber}"></div>
            </div>
        </div>
    </div>
</div>
</body>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
</html>
<script src="${ctx}/resource/js/register/countdown.js?version=${version}"></script>
<script src="${ctx}/business/bankcard/addCompanyBankCard.js?version=${version}"></script>
<script src="${ctx}/business/bankcard/bankCardCommon.js?version=${version}"></script>