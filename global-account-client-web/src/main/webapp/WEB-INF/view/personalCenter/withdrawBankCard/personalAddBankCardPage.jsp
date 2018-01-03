<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge，chrome=1">
    <meta name="renderer" content="webkit">
    <title>个人添加银行卡页面</title>
</head>
<body>
<div class="login-layout">
    <%@ include file="/WEB-INF/view/common/header.jsp" %>

    <form method="post" id="formData" action="${ctx}/withdrawBankCard/addBankCardPersonal.do">
        <input type="hidden" name="userName" id="userName" value="${name}">
        <div class="same-layout small">
            <div class="same-title">
                <div class="title-left fl">
                    <i class="fl"></i>
                    <h2>提现银行卡管理</h2>
                </div>
            </div>
            <div class="same-content">
                <div class="same-item clearfix">
                    <div class="item-left fl">姓名</div>
                    <div class="item-right fl">${name}</div>
                </div>
                <div class="same-item clearfix">
                    <div class="item-left fl">银行卡号</div>
                    <div class="input-box fl">
                        <input type="text" maxlength="23" placeholder="请输入银行卡号" id="bankCardNo" name="bankCardNo"
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
                        <a class="fl" href="#" onclick=getMessageCode(this.id) id="codeId">获取验证码</a>
                        <div class="input-tip none" id="PhoneCodeErr">
                            <em class="fl"></em>
                            <span>验证码不能为空/输入格式不正确</span>
                        </div>
                    </div>
                </div>
                <div class="btn-box item-btn">
                    <button onclick="addBankCardPersonal()" type="button" id="addBankCardPersonalSubmit">确认</button>
                    <button class="cancel-btn" type="button"
                            onclick="javascript:window.location='${ctx}/withdrawBankCard/bankCardIndex.do'">返回
                    </button>
                </div>
            </div>
        </div>
    </form>
</div>

<%@ include file="/WEB-INF/view/common/footer.jsp" %>
</html>
<script type="application/javascript">
    var bankCardNoBefore;
    $("#formData").ajaxForm({
        dataType: "JSON",
        success: function (data) {
            checkLoginOut(data);
            if (data.code == 0) {
                window.location.href = ctx + "/withdrawBankCard/bankCardIndex.do";
            } else {
                $("#addBankCardPersonalSubmit").attr('disabled', false);
                $("input[name='bankCardNo']").val(bankCardNoBefore);
                errorMsgShow(data.message);
            }
        }, error: function () {
            $("#addBankCardPersonalSubmit").attr('disabled', false);
            $("input[name='bankCardNo']").val(bankCardNoBefore);
            errorMsgShow("请求服务器异常，请稍后重试");
        }
    });

    $(function () {
        //设置手机号
        bankCardAddBlank("bankCardNo");
        $("#bankCard").addClass("active");
    })

    //提交表单
    function addBankCardPersonal() {
        bankCardNoBefore = $('#bankCardNo').val();
        var bankCardNo = $('#bankCardNo').val().replace(/\s+/g, "");
        if (!checkForm()) {
            return;
        }
        $("input[name='bankCardNo']").val(bankCardNo);
        $("#addBankCardPersonalSubmit").attr('disabled', true);
        $("#formData").submit();

    }

    /**
     * 添加银行卡账号提交前校验
     */
    function checkForm() {
        var bankCardNo = $('#bankCardNo').val().replace(/\s+/g, "");
        var messageCode = $('#messageCode').val();
        if ($.isEmptyObject(messageCode) || !regMessageCode.test(messageCode)
            || $.isEmptyObject(bankCardNo) || !regLegalPerson.test(bankCardNo)) {
            checkValideCode();
            checkBankCard();
            return false;
        } else {
            return true;
        }
    }

    //个人银行卡正则
    var regLegalPerson = new RegExp("^([1-9]{1})(\\d{11,18})$");

    //校验银行卡
    function checkBankCard() {
        var bankCardNo = $('#bankCardNo').val().replace(/\s+/g, "");
        if ($.isEmptyObject(bankCardNo) || !regLegalPerson.test(bankCardNo)) {
            $('#bankCardErr').show();
        }
    }

    //隐藏提示信息
    function hiddenBankCardErr() {
        $('#bankCardErr').hide();
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
     * 获取验证码
     */
    function getMessageCode(thisId) {
        $.ajax({
            type: "POST",
            url: ctx + "/withdrawBankCard/sendCode.do",
            data: {
                serviceType: "addPersonBankCard"
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
<script src="${ctx}/resource/js/register/countdown.js?version=${version}"></script>
<script src="${ctx}/business/bankcard/bankCardCommon.js?version=${version}"></script>