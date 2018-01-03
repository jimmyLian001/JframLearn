//企业对公账号正则
var regToPublic = new RegExp("^[0-9]*(\\d{6,})$");
//企业法人银行卡正则
var regLegalPerson = new RegExp("^([1-9]{1})(\\d{11,18})$");
//验证码正则
var regMessageCode = new RegExp("^\\d{6}$");

$(function () {
    bankCardAddBlank("bankCardNoLegalPerson");
    bankCardAddBlank("bankCardNo");
    $("#bankCard").addClass("active");
});
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

//校验验证码企业法人
function checkValideCodePerson() {
    var messageCode = $('#messageCodeLegalPerson').val();
    if ($.isEmptyObject(messageCode) || !regMessageCode.test(messageCode)) {
        $('#PhoneCodeErrPerson').show();
    }
}

//隐藏提示信息企业法人验证码
function hiddenPhoneCodePerson() {
    $('#PhoneCodeErrPerson').hide();
}

//校验验开户行
function checkBankCode() {
    var bankCode = $('#bankCode').val();
    if ($.isEmptyObject(bankCode)) {
        $('#bankCodeErr').show();
    }
}

//隐藏提示开户行错误信息
function hiddenBankCode() {
    $('#bankCodeErr').hide();
}

//校验验支行
function checkBankBranchName() {
    var bankBranchName = $('#bankBranchName').val();
    if ($.isEmptyObject(bankBranchName)) {
        $('#bankBranchNameErr').show();
    }
}

//隐藏提示支行错误信息
function hiddenBankBranchName() {
    $('#bankBranchNameErr').hide();
}

//校验银行卡
function checkBankCard() {
    var bankCardNo = $('#bankCardNo').val().replace(/\s+/g,"");
    if ($.isEmptyObject(bankCardNo) || !regToPublic.test(bankCardNo)) {
        $('#bankCardErr').show();
    }
}

//隐藏提示信息
function hiddenBankCardErr() {
    $('#bankCardErr').hide();
}

//校验银行卡
function checkBankCardPerson() {
    var bankCardNo = $('#bankCardNoLegalPerson').val().replace(/\s+/g,"");
    if ($.isEmptyObject(bankCardNo) || !regLegalPerson.test(bankCardNo)) {
        $('#bankCardNoPersonErr').show();
    }
}

//隐藏提示信息
function hiddenBankCardPersonErr() {
    $('#bankCardNoPersonErr').hide();
}

/**
 * 清空企业法人银行卡用户输入信息
 */
function clearLegalPersonDisplay() {
    $("#bankCardNoLegalPerson").val("");
    $("#messageCodeLegalPerson").val("");
    $('#PhoneCodeErrPerson').hide();
    $('#bankCardNoPersonErr').hide();
}

/**
 * 清空企业对公银行卡用户输入信息
 */
function clearCompanyDivDisplay() {
    $("#bankCode").val("");
    $("#bankBranchName").val("");
    $("#bankCardNo").val("");
    $("#messageCode").val("");
    $('#bankCodeErr').hide();
    $('#bankBranchNameErr').hide();
    $('#bankCardErr').hide();
    $('#PhoneCodeErr').hide();
}

/**
 * 添加企业对公银行账号提交前校验
 */
function checkCompanyForm() {
    var messageCode = $('#messageCode').val();
    var bankCode = $('#bankCode').val();
    var bankBranchName = $('#bankBranchName').val();
    var bankCardNo = $('#bankCardNo').val().replace(/\s+/g,"");
    if ($.isEmptyObject(messageCode) || $.isEmptyObject(bankCode) || $.isEmptyObject(bankBranchName) ||
        $.isEmptyObject(bankCardNo) || !regMessageCode.test(messageCode) || !regToPublic.test(bankCardNo)) {
        checkValideCode();
        checkBankCode();
        checkBankBranchName();
        checkBankCard();
        return false;
    } else {
        return true;
    }
}

/**
 * 添加企业法人银行提交前校验
 */
function checkLegalPersonForm() {
    var messageCode = $('#messageCodeLegalPerson').val();
    var bankCardNo = $('#bankCardNoLegalPerson').val().replace(/\s+/g,"");
    if ($.isEmptyObject(messageCode) || $.isEmptyObject(bankCardNo) || !regMessageCode.test(messageCode)
        || !regLegalPerson.test(bankCardNo)) {
        checkValideCodePerson();
        checkBankCardPerson();
        return false;
    } else {
        return true;
    }
}

/**
 * 添加企业法人银行卡
 */
function addBankCardCompanyLPCompany() {
    var messageCode = $("#messageCodeLegalPerson").val();
    var bankCardNo = $("#bankCardNoLegalPerson").val().replace(/\s+/g,"");
    var currentPhoneNumber = $("#currentPhoneNumber").val();
    var name = $("#name").val();
    if (!checkLegalPersonForm()) {
        return;
    }
    $("#addBankCardLegalPersonSubmit").attr('disabled', true);
    $.ajax({
        type: "POST",
        url: ctx + "/withdrawBankCard/addBankCardCompanyPersonal.do",
        data: {
            name: name,
            messageCode: messageCode,
            currentPhoneNumber: currentPhoneNumber,
            bankCardNo: bankCardNo
        },
        dataType: "JSON",
        success: function (data) {
            checkLoginOut(data);
            if (data.msg == 'success') {
                window.location = ctx + '/withdrawBankCard/bankCardIndex.do';
            } else {
                $("#addBankCardLegalPersonSubmit").attr('disabled', false);
                errorMsgShow(data.errorMsg);
            }
        },
        error: function () {
            $("#addBankCardLegalPersonSubmit").attr('disabled', false);
            errorMsgShow("操作失败，请重试或联系管理员");
        }
    });
}

/**
 * 添加企业对公银行卡
 */
function addBankCardCompanyToPublish() {
    var messageCode = $("#messageCode").val();
    var bankCardNo = $("#bankCardNo").val().replace(/\s+/g,"");
    var currentPhoneNumber = $("#currentPhoneNumber").val();
    var bankCode = $("#bankCode").val();
    var bankBranchName = $("#bankBranchName").val();
    var name = $("#name").val();
    if (!checkCompanyForm()) {
        return;
    }
    $("#addBankCardSubmit").attr('disabled', true);
    $.ajax({
        type: "POST",
        url: ctx + "/withdrawBankCard/addBankCardCompanyTP.do",
        data: {
            name: name,
            bankCode: bankCode,
            messageCode: messageCode,
            currentPhoneNumber: currentPhoneNumber,
            bankBranchName: bankBranchName,
            bankCardNo: bankCardNo
        },
        dataType: "JSON",
        success: function (data) {
            checkLoginOut(data);
            if (data.msg == 'success') {
                window.location = ctx + '/withdrawBankCard/bankCardIndex.do';
            } else {
                $("#addBankCardSubmit").attr('disabled', false);
                errorMsgShow(data.errorMsg);
            }
        },
        error: function () {
            $("#addBankCardSubmit").attr('disabled', false);
            errorMsgShow("操作失败，请重试或联系管理员");
        }
    });
}

/**
 * 获取验证码
 */
function getMessageCode(thisId, serviceType) {
    $.ajax({
        type: "POST",
        url: ctx + "/withdrawBankCard/sendCode.do",
        data: {
            serviceType: serviceType
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