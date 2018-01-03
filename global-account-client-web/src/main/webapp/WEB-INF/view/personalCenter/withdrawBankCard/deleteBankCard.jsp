<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge，chrome=1">
    <meta name="renderer" content="webkit">
    <title>删除银行卡页面</title>
    <script src="${ctx}/resource/js/register/countdown.js?version=${version}"></script>
</head>
<body>
<%@ include file="/WEB-INF/view/common/header.jsp" %>

<form method="post" id="deleteForm" action="${ctx}/withdrawBankCard/deleteBankCard.do">
    <input type="hidden" name="phoneNumber" id="phoneNumber" value="${phoneNumber}">
    <input type="hidden" name="recordNo" value="${card.recordNo}">
    <div class="login-layout" style="min-height: 650px;">
        <div class="same-layout small">
            <div class="same-title">
                <div class="title-left fl">
                    <i class="fl"></i>
                    <h2>提现银行卡管理</h2>
                </div>
            </div>
            <div class="same-content small-content">
                <div class="same-item clearfix">
                    <div class="item-left fl">银行卡信息</div>
                    <div class="bank-list fl">
                        <div class="bank-list-top clearfix">
                            <div class="bank-top-left fl">
                                <div class="bank-logo fl">
                                    <img src="${ctx}/resource/images/bank/${card.bankCode}.png" alt="bank"
                                         onerror="onImgError(this)">
                                </div>
                                <div class="bank-name fl" style="word-break: break-all">
                                    <p>${card.bankName}</p>
                                    <p>${card.cardHolder}</p>
                                </div>
                            </div>
                        </div>
                        <div class="bank-list-bottom">
                            <span>${card.cardNo}</span>
                        </div>
                    </div>
                </div>
                <div class="same-item verification-item clearfix">
                    <div class="item-left fl">验证码</div>
                    <div class="verification-code-box verification-bank input-box  clearfix fl">
                        <input class="fl" type="text" maxlength="6" placeholder="输入验证码" name="messageCode"
                               id="messageCode"
                               onblur="checkValideCode()" onfocus="hiddenPhoneCode()">
                        <a class="fl" href="#" onclick=getMessageCode(this.id) id="codeId">获取验证码</a>
                        <div class="input-tip none" id="PhoneCodeErr">
                            <em class="fl"></em>
                            <span>验证码不能为空/输入格式不正确</span>
                        </div>
                    </div>
                </div>
                <div class="btn-box item-btn">
                    <button id="deleteSubmit" type="button" onclick="deleteBankCard()">确认</button>
                    <button type="button" class="cancel-btn"
                            onclick="javascript:window.location='${ctx}/withdrawBankCard/bankCardIndex.do'">返回
                    </button>
                </div>
            </div>
        </div>
    </div>
</form>

</body>
</html>
<script type="application/javascript">

    $(function () {
        $("#bankCard").addClass("active");
        $("#deleteForm").ajaxForm({
            dataType: "JSON",
            success: function (data) {
                checkLoginOut(data);
                if (data.code == 0) {
                    window.location.href = ctx + "/withdrawBankCard/bankCardIndex.do";
                } else {
                    errorMsgShow(data.message);
                    $("#deleteSubmit").attr('disabled', false);
                }
            }, error: function () {
                $("#deleteSubmit").attr('disabled', false);
                errorMsgShow("请求服务器异常，请稍后重试");
            }
        });
    });

    /**
     * 删除银行卡
     */
    function deleteBankCard() {
        if (!checkForm()) {
            return;
        }
        $("#deleteSubmit").attr('disabled', true);
        $("#deleteForm").submit();
    }

    //验证码正则
    var regMessageCode = new RegExp("^\\d{6}$");

    //校验验证码
    function checkValideCode() {
        var messageCode = $('#messageCode').val();
        if ($.isEmptyObject(messageCode) || !regMessageCode.test(messageCode)) {
            $('#PhoneCodeErr').show();
        }
    }

    //隐藏提示信息验证码
    function hiddenPhoneCode() {
        $('#PhoneCodeErr').hide();
    }

    /**
     * 删除银行卡账号提交前校验
     */
    function checkForm() {
        var messageCode = $('#messageCode').val();
        if ($.isEmptyObject(messageCode) || !regMessageCode.test(messageCode)) {
            checkValideCode();
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取验证码
     */
    function getMessageCode(thisId) {
        var cardNo = '${card.cardNo}';
        cardNo = cardNo.substring(cardNo.length - 4, cardNo.length);
        $.ajax({
            type: "POST",
            url: ctx + "/withdrawBankCard/sendCode.do",
            data: {
                serviceType: "deleteBankCard",
                bankCardTailNo: cardNo
            },
            dataType: "JSON",
            success: function (data) {
                checkLoginOut(data);
                if (data.flag) {
                    time(thisId, '60');
                } else {
                    errorMsgShow(data.msg);
                }
            },
            error: function () {
                errorMsgShow("操作失败，请重试或联系管理员");
            }
        });
    }


</script>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
<script src="${ctx}/business/bankcard/bankCardCommon.js?version=${version}"></script>
<script src="${ctx}/business/account/js/apply-account-service.js?version=${version}"></script>