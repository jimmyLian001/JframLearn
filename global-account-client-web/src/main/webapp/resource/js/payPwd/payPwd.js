var pageContent = {
    submitFlag: true,
    times: -1,
    sendCode: function (obj) {
        $("input[name='messageCode']").val("");
        $.ajax({
            type: "post",
            url: ctx + "/person/center/sendCode.do",
            data: {
                "loginNo": $("input[name='loginNo']").val(),
                "msgType": $("input[name='msgType']").val()
            },
            async: false,
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            success: function (result) {
                if (result) {
                    obj.unbind("click");
                    pageContent.times = 60;
                    $(obj).html("重新发送(<em id='times'>" + pageContent.times + "</em>s)")
                } else {
                    alert("发送验证码失败!")
                }
            }
        });
    },

    timeLoop: function (time) {
        if (pageContent.times > 0) {
            $("#sms_code").attr("disabled", true);
            pageContent.times = time - 1;
            $("#times").text(pageContent.times);
        } else if (pageContent.times == 0) {
            pageContent.times = -1;
            pageContent.smsCodeBind();
        }
    },
    smsCodeBind: function () {
        $("#sms_code").html("获取验证码")
        $("#sms_code").bind("click", function () {
            pageContent.sendCode($("#sms_code"))
        });
    },
    valid: function () {
        //登录密码验证方法
        $.validator.addMethod("checkLoginPwd", function (value, element) {
            var regex = /^[0-9]{6}$/;
            return this.optional(element) || (regex.test(value));
        }, "密码格式不正确，密码由6数字组成");

        $("#pay_pwd_form").validate({
            debug: false,
            onfocusOut: true,
            focusInvalid: true,
            onkeyup: false,
            rules: {
                messageCode: {
                    required: true,
                    digits: true
                },
                payPwd: {
                    required: true,
                    checkLoginPwd: true,
                    digits: true,
                    maxlength: 6
                },
                payPwdAgain: {
                    required: true,
                    checkLoginPwd: true,
                    digits: true,
                    maxlength: 6,
                    equalTo: "#payPwd"
                }
            },
            messages: {
                messageCode: {
                    required: "以上内容不能为空",
                    digits: "只能输入数字"
                },
                payPwd: {
                    required: "以上内容不能为空",
                    checkLoginPwd: "密码格式不正确，密码由6数字组成",
                    maxlength: "密码格式不正确，密码由6数字组成",
                    digits: "只能输入数字"
                },
                payPwdAgain: {
                    required: "以上内容不能为空",
                    checkLoginPwd: "密码格式不正确，密码由6数字组成",
                    equalTo: "两次输入密码不一致",
                    maxlength: "密码格式不正确，密码由6数字组成",
                    digits: "只能输入数字"
                }
            },
            wrapper: "div",
            errorElement: "span",
            errorPlacement: function (error, element) {
                element.parent().find("div").remove()
                error.find("span").removeClass("error")
                error.addClass("input-tip")
                error.prepend("<em class='fl'></em>")
                element.parent().append(error);
            },
            success: function (lable) {
                lable.parent().remove();
            },
            submitHandler: function (form) {
                $("#pay_pwd_Sub").attr('disabled', true);
                $(form).ajaxSubmit({
                    type: 'post',
                    dataType: "json",
                    async: false,
                    success: function (data) {
                        if (data && data.code == 999) {
                            console.log("用户登录session超时，退出登录");
                            window.location.href = ctx + "/login/index.do";
                            return false;
                        }
                        if (data.msg == 'success') {
                            reviseTelDisplay(data);
                        } else {
                            $("#pay_pwd_Sub").attr('disabled', false);
                            showPagePrompts("error", data.errorMessage);
                        }
                    },
                    error: function () {
                        $("#pay_pwd_Sub").attr('disabled', false);
                        $("#pay_pwd_form").resetForm();
                        showPagePrompts("error", "操作失败，请重试或联系管理员");
                    }
                });
            }
        });
    }
};

$(function () {
    pageContent.valid();
    pageContent.smsCodeBind();
    setInterval(function () {
        pageContent.timeLoop(pageContent.times)
    }, 1000)
    addPayPwdRule();
})

/**
 *
 */

function reviseTelDisplay(data) {
    window.location.href = ctx + "/userTel/resultPageJumps.do?phoneNumber=" + data.phoneNumber + "&resultUrl=" + data.resultUrl;
}


function showPagePrompts(showType, showContent) {
    var alertClass = "alert-block";
    $("#pagePromptsDiv").removeClass().addClass("pagePrompts");
    if (showType == 'error') {
        alertClass = "alert-error";
    } else if (showType == 'info') {
        alertClass = "alert-info";
    } else if (showType == 'success') {
        alertClass = "alert-success";
    } else {
        alertClass = "alert-block";
    }
    $("#pagePromptsDiv").html(
        '<div id="pagePromptsDivContent" class="alert ' + alertClass + '">'
        + showContent + '</div>');
    // 重新渲染
    $(".alert").click(function () {
        removeRow();
    });
    $("#pagePromptsDiv").slideDown("slow");
    // 一定时间后关闭提示框
    setTimeout('removeRow()', 3500);
}

function removeRow() {
    $("#pagePromptsDiv").html("");
    $("#pagePromptsDiv").slideUp("slow");
}







