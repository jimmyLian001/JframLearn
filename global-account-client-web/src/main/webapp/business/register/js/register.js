//手机号校验
function checkMobilePhone(id) {
    var val = $('#' + id).val();
    if (val == '') {
        inputError(id, '用户名不能为空');
        return false;
    }
    var ret = /^0?(13|14|15|17|18)[0-9]{9}$/;
    if (!ret.test(val)) {
        inputError(id, '用户名格式不正确');
        return false
    }
    inputSuccess(id);
    return true;
}

//邮箱校验
function checkEmail(id) {
    var val = $('#' + id).val();
    if (val == '') {
        inputError(id, '用户名不能为空');
        return false;
    }
    var ret = /\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/;
    if (!ret.test(val)) {
        inputError(id, '用户名格式不正确');
        return false
    }
    inputSuccess(id);
    return true;
}

//验证码校验
function checkCaptcha(id) {
    var val = $('#' + id).val();
    if (val == '') {
        inputTipError(id, '验证码不能为空');
        return false;
    }
    inputTipSuccess(id);
    return true;
}

//密码校验
function checkLoginPwd(id) {
    var val = $('#' + id).val();
    if (val == '') {
        inputError(id, '密码不能为空');
        return false;
    }
    var ret = /^(?![a-zA-z]+$)(?!\d+$)(?![!@#$%^&*()_+`\-={}:";'<>?,.\/]+$)[a-zA-Z\d!@#$%^&*()_+`\-={}:";'<>?,.\/]{6,32}$/;
    if (!ret.test(val)) {
        inputError(id, '密码格式不正确，密码由6-16位字母、数字或符号组成');
        return false
    }
    inputSuccess(id);
    return true;
}

//密码校验2
function checkLoginPwdAgain(id,ids) {
    var val = $('#' + id).val();
    if (val == '') {
        inputError(id, '密码不能为空');
        return false;
    }
    var vals = $('#' + ids).val();
    if (vals != val) {
        inputError(id, '两次密码不一致');
        return false;
    }
    var ret = /^(?![a-zA-z]+$)(?!\d+$)(?![!@#$%^&*()_+`\-={}:";'<>?,.\/]+$)[a-zA-Z\d!@#$%^&*()_+`\-={}:";'<>?,.\/]{6,32}$/;
    if (!ret.test(val)) {
        inputError(id, '密码格式不正确，密码由6-16位字母、数字或符号组成');
        return false
    }
    inputSuccess(id);
    return true;
}

//校验安全问题
function checkQuestionNo(id) {
    var val = $('#' + id).val();
    if (val == '') {
        inputError(id, '请选择安全保护问题');
        return false;
    }
    inputSuccess(id);
    return true;
}

//校验安全问题答案
function checkAnswer(id) {
    var val = $('#' + id).val().trim();
    if (val == '') {
        inputError(id, '不能为空');
        return false;
    }
    inputSuccess(id);
    return true;
}

//输入失败
var div = "<div class='input-tip'><em class='fl'></em><span></span></div>";
function inputError(id, val) {
    $('#' + id).next().remove();
    $('#' + id).after(div);
    $('#' + id).parent().find('div').find('span').text(val);
}

//输入成功
function inputSuccess(id) {
    $('#' + id).next().remove()
}

//输入失败(固定标签)
var divTip = "<em class='fl'></em><span></span>";
function inputTipError(id, val) {
    $('#' + id).parent().find('div').children().remove();
    $('#' + id).parent().find('div').append(divTip);
    $('#' + id).parent().find('div').find('span').text(val);
}

//输入成功(固定标签)
function inputTipSuccess(id) {
    $('#' + id).parent().find('div').children().remove();
}

//顶部错误提示
var errorWait;
function topTip(val) {

    clearTime();
    $('#topTipErrMsg').addClass("show").find("span").text(val);
    errorWait = setTimeout(function () {
        $('#topTipErrMsg').removeClass("show").find("span").text("");
    }, 3000)
}

//停止错误提示倒计时
function clearTime(){
    clearTimeout(errorWait);
}



//验证码倒计时
var waitTime = 0;
var timerWait;
//回调方法
function time(thisId,time) {
    if(waitTime != 0){
        return;
    }
    waitTime = time;
    setTime(thisId)
}

//倒计时
function setTime(thisId) {

    if (waitTime == 0) {
        $('#loginNo').attr("disabled", false);
        $('#' + thisId).html('获取验证');
    } else {
        $('#loginNo').attr("disabled", true);
        $('#' + thisId).html('重新发送(' + waitTime + "s)");
        waitTime--;
        timerWait = setTimeout(function () {
            setTime(thisId)
        }, 1000)
    }
}