//定义Id 适用于方法传参
var idLoginNo = 'loginNo';
var idCaptcha = 'captcha';
var idLoginPwd = 'loginPwd';
var idLoginPwdAgain = 'loginPwdAgain';

//初始化加载
$(function () {

    //基本信息
    $("#loginNo").blur(function () {
        checkMobilePhone(this.id);
    });
    $("#captcha").blur(function () {
        checkCaptcha(this.id);
    });
    $("#loginPwd").blur(function () {
        checkLoginPwd(this.id);
    });
    $("#loginPwdAgain").blur(function () {
        checkLoginPwdAgain(this.id, idLoginPwd);
    });
    //安全问题
    $("#questionNoOne").change(function () {
        checkQuestionNo(this.id);
    });
    $("#answerOne").blur(function () {
        checkAnswer(this.id);
    });
    $("#questionNoTwo").change(function () {
        checkQuestionNo(this.id);
    });
    $("#answerTwo").blur(function () {
        checkAnswer(this.id);
    });
    $("#questionNoThree").change(function () {
        checkQuestionNo(this.id);
    });
    $("#answerThree").blur(function () {
        checkAnswer(this.id);
    });
});

//********************************基本信息相关******************************

//发送手机验证码
function sendSms(thisId) {

    if (!checkMobilePhone(idLoginNo)) {
        return;
    }
    $.ajax({
        url: ctx + "/register/ajaxSendSmsCaptcha.do",
        type: "post",
        data: {loginNo: $('#loginNo').val()},
        success: function (data) {
            console.info(data);
            if (data.code == 0) {
                topTip(data.message);
            } else {
                time(thisId, '60');
            }
        },
        error: function (data) {
            topTip("系统繁忙，请稍后再试");
        }
    });
}

//校验全部基本信息
function checkAll() {
    var flag = true;
    if (!checkMobilePhone(idLoginNo)) {
        flag = false;
    }
    if (!checkCaptcha(idCaptcha)) {
        flag = false;
    }
    if (!checkLoginPwd(idLoginPwd)) {
        flag = false;
    }
    if (!checkLoginPwdAgain(idLoginPwdAgain, idLoginPwd)) {
        flag = false;
    }
    return flag;
}

//下一步(校验短信验证码)
function nextSubmit() {
    $('#nextSubmit').attr("disabled", true);
    if (!checkAll()) {
        $('#nextSubmit').attr("disabled", false);
        return;
    }
    $.ajax({
        cache: true,
        url: ctx + "/register/ajaxCheckSmsCaptcha.do",
        type: "POST",
        data: {
            loginNo: $('#loginNo').val(),
            captcha: $('#captcha').val(),
            loginPwd: $('#loginPwd').val(),
            loginPwdAgain: $('#loginPwdAgain').val()
        },
        async: false,
        success: function (data) {
            if (data.code == 0) {
                topTip(data.message);
            } else {
                $('#loginNoHidden').val($('#loginNo').val());
                $("#regPersonalFrom").find("input[type='text']").val("");
                $("#regPersonalFrom").find("input[type='password']").val("");
                $("#nextForm").submit();
            }
            $('#nextSubmit').attr("disabled", false);
        },
        error: function () {
            topTip("系统繁忙，请稍后再试");
            $('#nextSubmit').attr("disabled", false);
        },
    });
}

//*****************************************安全问题相关******************************

//校验全部安全信息
function checkAllSecure() {
    var flag = true;
    if (!checkQuestionNo('questionNoOne')) {
        flag = false;
    }
    if (!checkAnswer('answerOne')) {
        flag = false;
    }
    if (!checkQuestionNo('questionNoTwo')) {
        flag = false;
    }
    if (!checkAnswer('answerTwo')) {
        flag = false;
    }
    if (!checkQuestionNo('questionNoThree')) {
        flag = false;
    }
    if (!checkAnswer('answerThree')) {
        flag = false;
    }
    return flag;
}

//下一步
function submitCreate() {
    $('#submitCreate').attr("disabled", true);
    if (!checkAllSecure()) {
        $('#submitCreate').attr("disabled", false);
        return;
    }
    $.ajax({
        cache: true,
        url: ctx + "/register/ajaxCreatePersonalUser.do",
        type: "POST",
        data: $("#personalQuestionForm").serialize(),
        async: false,
        success: function (data) {
            if (data.code == 0) {
                topTip(data.message);
            } else {
                window.location = ctx + "/login/toIndexPage.do";
            }
            $('#submitCreate').attr("disabled", false);
        },
        error: function () {
            topTip("系统繁忙，请稍后再试");
            $('#submitCreate').attr("disabled", false);
        }
    });

}