var pageContent = {
        submitFlag: true,
        times: -1,
        sendCurrentCode: function (obj) {
            $("input[name='messageCode']").val("");
            var currentPhoneNumber = $('#currentPhoneNumber').val();
            if (checkMobilePhone('currentPhoneNumber')) {
                $.ajax({
                    type: "post",
                    url: ctx + "/userTel/getMessageCode.do",
                    data: {
                        afterFixPhoneNumber: currentPhoneNumber,
                        serviceType: $('#serviceType').val()
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
            }
        },
        sendCode: function (obj) {
            $("input[name='messageCode']").val("");
            var afterFixPhoneNumber = $('#afterFixPhoneNumber').val();
            if (checkMobilePhone('afterFixPhoneNumber')) {
                $.ajax({
                    type: "post",
                    url: ctx + "/userTel/getMessageCode.do",
                    data: {
                        afterFixPhoneNumber: afterFixPhoneNumber,
                        serviceType: $('#serviceType').val()
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
            }
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
                pageContent.sendCurrentCode($("#sms_code"))
            });
            $("#sms_code").html("获取验证码")
            $("#sms_code").bind("click", function () {
                pageContent.sendCode($("#sms_code"))
            });
        },
        valid: function () {
            //用户名验证方法
            $.validator.addMethod("checkPhoneNumber", function (value, element) {
                var mobileRegex = /^[1][3,4,5,7,8][0-9]{9}$/;
                return this.optional(element) || (mobileRegex.test(value));
            }, "手机格式不正确");

            $("#form-tel").validate({
                debug: false,
                onfocusOut: true,
                focusInvalid: true,
                onkeyup: false,
                rules: {
                    messageCode: {
                        required: true,
                        digits: true

                    },
                    currentPhoneNumber: {
                        required: true,
                        checkPhoneNumber: true,
                        digits: true,
                        equalTo: "#oldMobile"
                    },
                    afterFixPhoneNumber: {
                        required: true,
                        maxlength: 11,
                        remote: {
                            url: ctx + "/userTel/verifyPhoneNo.do",     //后台处理程序
                            type: "post",               //数据发送方式
                            dataType: "json",           //接受数据格式
                            data: {                     //要传递的数据
                                afterFixPhoneNumber: function () {
                                    return $("#afterFixPhoneNumber").val();
                                }

                            }
                        }
                    }
                },
                messages: {
                    messageCode: {
                        required: "以上内容不能为空",
                        digits: "只能输入数字"
                    },
                    currentPhoneNumber: {
                        required: "手机号不能为空",
                        equalTo: "请输入当前绑定手机"
                    },
                    afterFixPhoneNumber: {
                        required: "手机号不能为空",
                        remote: "该手机已被绑定"
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
                    $("#reset_tel_sub").attr('disabled', true);
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
                                $("#reset_tel_sub").attr('disabled', false);
                                showPagePrompts("error", data.errorMessage);
                            }
                        },
                        error: function () {
                            $("#reset_tel_sub").attr('disabled', false);
                            $("#form-tel").resetForm();
                            showPagePrompts("error", "操作失败，请重试或联系管理员");
                        }
                    });
                }
            });
        }
    }
;

$(function () {
    pageContent.valid();
    pageContent.smsCodeBind();
    setInterval(function () {
        pageContent.timeLoop(pageContent.times)
    }, 1000)
    addTelRule();
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


//手机号校验
function checkMobilePhone(id) {
    var val = $('#' + id).val();
    if (val == '') {
        inputError(id, '手机号不能为空');
        return false;
    }
    var ret = /^0?(13|14|15|17|18)[0-9]{9}$/;
    if (!ret.test(val)) {
        inputError(id, '手机号格式不正确');
        return false
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

function removeRow() {
    $("#pagePromptsDiv").html("");
    $("#pagePromptsDiv").slideUp("slow");
}



