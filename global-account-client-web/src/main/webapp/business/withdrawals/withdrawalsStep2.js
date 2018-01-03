//企业对公账号正则
var regToPublic = new RegExp("^[0-9]*(\\d{6,})$");
//企业法人银行卡正则
var regLegalPerson = new RegExp("^([1-9]{1})(\\d{11,18})$");

//企业对公账号验证
$.validator.addMethod("checkRegToPublic", function (value, element, params) {
    value=value.replace(/\s+/g,"");
    return this.optional(element) || regToPublic.test(value);
}, "银行卡填写不正确");
//企业法人银行卡验证
$.validator.addMethod("checkRegLegalPerson", function (value, element, params) {
    value=value.replace(/\s+/g,"");
    return this.optional(element) || regLegalPerson.test(value) ;
}, "银行卡填写不正确");

$(function () {
    //校验绑定
    validate();
    //查询银行卡
    queryBankCard();
    //添加银行卡弹框
    addBankCardDiv();
    //银行卡号每隔四位添加空格
    bankCardAddBlank("legalPersonBankCard");
    bankCardAddBlank("enterpriseBankCard");
    bankCardAddBlank("personBankCard");
});

function queryBankCard() {
    $.ajax({
        url: ctx + "/withdrawals/findBankInfo.do",
        type: "POST",
        dateType: "html",
        success: function (data) {
            $('#bankCardList').html(data);
        }
    });
}

function validate() {
    //提现
    $("#confirm").validate({
        onkeyup: false,
        rules: {
            payPwd: {
                required: true,
            },
            recordNo: {
                required: true
            }
        },
        messages: {
            payPwd: {
                required: "请输入支付密码"
            },
            recordNo: {
                required: "请选择银行卡"
            }
        },
        wrapper: "div",
        errorElement: "span",
        errorPlacement: function (error, element) {
            element.parent().find("div").remove()
            error.find("span").removeClass("error")
            error.addClass("input-tip")
            error.prepend("<em class='fl'></em>")
            element.after(error);
        },
        success: function (label) {
            label.parent().remove();
        },
        submitHandler: function () {
            submitForm();
            return;
        }
    });

    //个人添加银行卡
    $("#personForm").validate({
        onkeyup: false,
        rules: {
            personBankCard: {
                required: true,
                checkRegLegalPerson:true
            },
            personValidateCode: {
                required: true
            }
        },
        messages: {
            payPwd: {
                required: "请输入银行卡号"
            },
            recordNo: {
                required: "输入验证码"
            }
        },
        wrapper: "div",
        errorElement: "span",
        errorPlacement: function (error, element) {
            element.parent().find("div").remove()
            error.find("span").removeClass("error")
            error.addClass("input-tip")
            error.prepend("<em class='fl'></em>")
            element.after(error);
            element.parent().find('a').after(error);
        },
        success: function (label) {
            label.parent().remove();
        },
        submitHandler: function () {
            addPersonBankCard();
            return;
        }
    });

    //添加对公信息
    $("#enterpriseForm").validate({
        onkeyup: false,
        rules: {
            bankOfDeposit: {
                required: true,
            },
            branchBank: {
                required: true,
            },
            enterpriseBankCard: {
                required: true,
                checkRegToPublic:true
            },
            enterpriseValidateCode: {
                required: true
            }
        },
        messages: {
            bankOfDeposit: {
                required: "请输入开户行"
            },
            branchBank: {
                required: "输入支行名称"
            },
            enterpriseBankCard: {
                required: "请输入银行卡号"
            },
            enterpriseValidateCode: {
                required: "请输入验证码"
            }
        },
        wrapper: "div",
        errorElement: "span",
        errorPlacement: function (error, element) {
            element.parent().find("div").remove()
            error.find("span").removeClass("error")
            error.addClass("input-tip")
            error.prepend("<em class='fl'></em>")
            element.after(error);
            element.parent().find('a').after(error);
        },
        success: function (label) {
            label.parent().remove();
        },
        submitHandler: function () {
            addEnterpriseBankCard();
            return;
        }
    });

    //法人银行卡
    $("#legalPersonForm").validate({
        onkeyup: false,
        rules: {
            legalPersonBankCard: {
                required: true,
                checkRegLegalPerson:true
            },
            legalPersonValidateCode: {
                required: true
            }
        },
        messages: {
            enterpriseBankCard: {
                required: "请输入银行卡号"
            },
            enterpriseValidateCode: {
                required: "请输入验证码"
            }
        },
        wrapper: "div",
        errorElement: "span",
        errorPlacement: function (error, element) {
            element.parent().find("div").remove()
            error.find("span").removeClass("error")
            error.addClass("input-tip")
            error.prepend("<em class='fl'></em>")
            element.after(error);
            element.parent().find('a').after(error);
        },
        success: function (label) {
            label.parent().remove();
        },
        submitHandler: function () {
            addLegalPersonBankCard();
            return;
        }
    });
}

function submitForm() {
    var params = [
        {name: 'payPwd', value: $("#payPwd").val()},
        {name: 'recordNo', value: $("input[name='recordNo']:checked").val()},
        {name: 'confirmDtoListStr', value: $("#data").val()}
    ];
    $.ajax({
        url: ctx + "/withdrawals/confirm.do",
        type: "POST",
        data: params,
        success: function (data) {
            if (data.flag) {
                window.location.href = ctx + "/withdrawals/success.do";
            } else {
                $('#topTipErrMsg').addClass("top-tip show").find("span").text(data.msg);
                setTimeout(function () {
                    $('#topTipErrMsg').removeClass("top-tip show").addClass("top-tip hidden")
                }, 3000);
            }
        }
    });
}

//弹出添加银行卡
function addBankCardDiv() {
    var userType = $("#userType").val();
    //绑定弹出事件
    $('.add-card-btn').click(function () {
        if (userType == '1') {
            openDiv('personDiv', 'personCard');
        } else {
            openDiv('enterpriseDiv', 'enterprise');
        }
    })

    //绑定关闭事件
    if (userType == '1') {
        $('#personCancel').click(function () {
            closeDiv('personDiv', 'personForm');
        });
    } else {
        $('#enterpriseCancel').click(function () {
            closeDiv('enterpriseDiv', 'enterpriseForm');
        });
        $('#legalPersonCancel').click(function () {
            closeDiv('enterpriseDiv', 'legalPersonForm');
        });
    }
}

//弹出框：modalBoxId 弹框的外层div的ID、bombBoxId需要弹出div的ID
function openDiv(modalBoxId, bombBoxId) {
    var modalBox = $('#' + modalBoxId);
    modalBox.toggleClass('none');
    $('#' + bombBoxId).addClass('dropdown');
    $('body').css('overflow', 'hidden');
}

//关闭弹出框：modalBoxId 弹框的外层div的ID、fromId 对应表单的Id
function closeDiv(modalBoxId, fromId) {
    var modalBox = $('#' + modalBoxId);
    modalBox.addClass('none');
    $('body').css('overflow', 'auto');
    $('#' + fromId)[0].reset();
}

/**
 * 获取验证码
 */
function getMessageCode(thisId) {
    $.ajax({
        type: "POST",
        url: ctx + "/withdrawals/sendCode.do",
        dataType: "JSON",
        success: function (data) {
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

//添加个人银行卡
function addPersonBankCard() {
    var params = [
        {name: 'name', value: $("#personName").text()},
        {name: 'bankCardNo', value: $("input[name='personBankCard']").val().replace(/\s+/g,"")},
        {name: 'validateCode', value: $("input[name='personValidateCode']").val()},
        {name: 'accType', value: 1}
    ];
    $.ajax({
        url: ctx + "/withdrawals/addBankCard.do",
        type: "POST",
        data: params,
        success: function (data) {
            if (data.flag) {
                closeDiv('personDiv', 'personForm');
                queryBankCard();
            } else {
                $('#topTipErrMsg').addClass("top-tip show").find("span").text(data.msg);
                setTimeout(function () {
                    $('#topTipErrMsg').removeClass("top-tip show").addClass("top-tip hidden")
                }, 3000);
            }
            clearRegisterTime('personSmsCode');
        }
    });
}

//添加对公账号
function addEnterpriseBankCard() {
    var params = [
        {name: 'name', value: $("#enterpriseName").text()},
        {name: 'bankCardNo', value: $("input[name='enterpriseBankCard']").val().replace(/\s+/g,"")},
        {name: 'validateCode', value: $("input[name='enterpriseValidateCode']").val()},
        {name: 'bankCode', value: $("#bankOfDeposit option:selected").val()},
        {name: 'branchBank', value: $("input[name='branchBank']").val()},
        {name: 'accType', value: 3}
    ];
    $.ajax({
        url: ctx + "/withdrawals/addBankCard.do",
        type: "POST",
        data: params,
        success: function (data) {
            if (data.flag) {
                closeDiv('enterpriseDiv', 'enterpriseForm');
                queryBankCard();
            } else {
                $('#topTipErrMsg').addClass("top-tip show").find("span").text(data.msg);
                setTimeout(function () {
                    $('#topTipErrMsg').removeClass("top-tip show").addClass("top-tip hidden")
                }, 3000);
            }
            clearRegisterTime('enterpriseSmsCode');
        }
    });
}

//添加法人银行卡
function addLegalPersonBankCard() {
    var params = [
        {name: 'name', value: $("#legalPersonName").text()},
        {name: 'bankCardNo', value: $("input[name='legalPersonBankCard']").val().replace(/\s+/g,"")},
        {name: 'validateCode', value: $("input[name='legalPersonValidateCode']").val()},
        {name: 'accType', value: 2}
    ];
    $.ajax({
        url: ctx + "/withdrawals/addBankCard.do",
        type: "POST",
        data: params,
        success: function (data) {
            if (data.flag) {
                closeDiv('enterpriseDiv', 'legalPersonForm');
                queryBankCard();
            } else {
                $('#topTipErrMsg').addClass("top-tip show").find("span").text(data.msg);
                setTimeout(function () {
                    $('#topTipErrMsg').removeClass("top-tip show").addClass("top-tip hidden")
                }, 3000);
            }
            clearRegisterTime('legalPersonSmsCode');
        }
    });
}

//返回
function back() {
    window.location.href = ctx + '/withdraw/backApply?userWithdrawStoreStr='+$("#paramList").val();
}