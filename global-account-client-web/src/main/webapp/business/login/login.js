$(function () {
    //首次获取验证码
    validate();
    checkFlag();
});

function validate() {
    $("#loginForm").validate({
        onkeyup: false,
        rules: {     //配置验证规则，key就是被验证的dom对象，value就是调用验证的方法(也是json格式)
            loginNo: {
                required: true,  //必填。如果验证方法不需要参数，则配置为true error
                checkLogin: true
            },
            loginPwd: {
                required: true
            },
            validateCode: {
                required: true,
                remote: {
                    type: "POST",
                    url: ctx + "/common/checkValidateCode.do",
                    data: {
                        validateCode: function () {
                            return $("#validateCode").val();
                        },
                        key: "LOGIN_SESSION"
                    }
                }
            }
        },
        messages: {
            loginNo: {
                required: "请输入用户名"
            },
            loginPwd: {
                required: "请输入密码"
            },
            validateCode: {
                required: "请输入验证码",
                remote: "验证码填写错误"
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
            element.parent().find('img').after(error);
        },
        success: function (label) {
            label.parent().remove();
        },
        submitHandler: function () {
            submitForm();
            return;
        }
    });
}

$.validator.addMethod("checkLogin", function (value, element, params) {
    var checkEmail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
    var phoneReg = /^1[34578]\d{9}$/;
    return this.optional(element) || (checkEmail.test(value)) || phoneReg.test(value);
}, "用户名不正确");

//获取验证码
function getVerify() {
    $("#imgVerify").attr("src", ctx + "/common/getVerify.do?" + Math.random() + "&key=LOGIN_SESSION");
}

function submitForm() {
    $.ajax({
        url: ctx + "/login/login.do",
        type: "POST",
        data: $("#loginForm").serialize(),
        success: function (data) {
            if (data.flag) {
                window.location.href = ctx + "/account/index.do"
            } else {
                $('#errorMsgDiv').addClass("top-tip show").find("span").text(data.msg);
                if (data.object > 3) {
                    $('.verification-code-box').css('display', 'block');
                    $('#flag').val(data.object);
                    $('#validateCode').val("");
                    getVerify();
                }
                setTimeout(function () {
                    $('#errorMsgDiv').removeClass("top-tip show").addClass("top-tip hidden")
                }, 3000);
            }
        },
        error: function () {
            $('.login-tip').css('display', 'block');
            $('#errorMsg').text("系统繁忙，请稍后再试");
        }
    });
}

function checkFlag() {

    $.ajax({
        url: ctx + "/login/checkFlag.do",
        type: "POST",
        data: $("#loginForm").serialize(),
        success: function (data) {
            if (data > 3) {
                $('.verification-code-box').css('display', 'block');
                $('#flag').val(data.object);
                $('#validateCode').val("");
                getVerify();
            }
        }
    });
}