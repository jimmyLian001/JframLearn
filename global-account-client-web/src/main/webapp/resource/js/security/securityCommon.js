var pageContent = {
    sendCode: function (obj) {
        $("input[name='verifyCode']").val("");
        pageContent.send();
    }, send: function () {
        sendRequest(
            ctx + "/person/center/sendCode.do",
            {"msgType": $("input[name='msgType']").val()},
            function (data) {
                if (!data.sendFlag) {
                    errorMsgShow(data.errorMsg);
                } else {
                    $(".sms-code").unbind("click");
                    var sendTime = 60;
                    var interval = setInterval(function () {
                        $(".sms-code").html("重新发送(<em id='times'>" + sendTime + "</em>s)");
                        sendTime--;
                        if (sendTime == 0) {
                            sendTime = 60
                            $(".sms-code").html("获取验证码");
                            $(".sms-code").click(function () {
                                pageContent.sendCode();
                            });
                            //结束
                            clearInterval(interval);
                        }
                    }, 1000);
                }
            }
        );
    },
    smsCodeBind: function () {
        $(".sms-code").html("获取验证码")
        $(".sms-code").bind("click", function () {
            pageContent.sendCode($(".sms-code"))
        });
    },
    valid: function () {
        //登录密码验证方法
        $.validator.addMethod("checkLoginPwd", function (value, element) {
            var regex = /^(?![a-zA-z]+$)(?!\d+$)(?![!@#$%^&*()_+`\-={}:";'<>?,.\/]+$)[a-zA-Z\d!@#$%^&*()_+`\-={}:";'<>?,.\/]{6,16}$/;
            return this.optional(element) || (regex.test(value));
        }, "密码格式不正确，密码由6-16位字母、数字或符号组成");

        //用户名验证方法
        $.validator.addMethod("checkLoginNo", function (value, element) {
            var mobileRegex = /^0?(13|14|15|17|18)[0-9]{9}$/;
            var emailRegex = /\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/;
            return this.optional(element) || (mobileRegex.test(value)) || (emailRegex.test(value));
        }, "用户名格式不正确");

        //自定义方法问题验证方法
        $.validator.addMethod("notEqualTo", function (value, obj, element) {
            var elementArr = element.split(";");
            for (var index in elementArr) {
                if ($(elementArr[index]).val() == value) {
                    return false;
                }
            }
            return true;
        }, "不能重复选择相同安保问题");

        $("form").validate({
            debug: false,
            onfocusOut: true,
            focusInvalid: true,
            onkeyup: false,
            rules: {
                loginNo: {
                    required: true,
                    checkLoginNo: true
                },
                verifyCode: {
                    required: true
                },
                oldPassword: {
                    required: true,
                    checkLoginPwd: true,
                    remote: {
                        url: ctx + "/person/center/modifyPassword/verifyPwd.do",     //后台处理程序
                        type: "post",               //数据发送方式
                        dataType: "json",           //接受数据格式
                        data: {                     //要传递的数据
                            username: function () {
                                return $("#oldPassword").val();
                            }
                        }
                    }
                },
                firstPwd: {
                    required: true,
                    checkLoginPwd: true
                },
                secondPwd: {
                    required: true,
                    checkLoginPwd: true,
                    equalTo: "#firstPwd"
                },
                questionNoOne: {
                    required: true,
                    notEqualTo: "#questionNoTwo option:selected;#questionNoThree option:selected"
                },
                questionNoTwo: {
                    required: true,
                    notEqualTo: "#questionNoOne option:selected;#questionNoThree option:selected"
                },
                questionNoThree: {
                    required: true,
                    notEqualTo: "#questionNoOne option:selected;#questionNoTwo option:selected"
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
            messages: {
                loginNo: {
                    required: "用户名不能为空"
                },
                verifyCode: {
                    required: "验证码不能为空"
                },
                oldPassword: {
                    required: "原密码不能为空",
                    remote: "当前登录密码错误"
                },
                firstPwd: {
                    required: "密码不能为空"
                },
                secondPwd: {
                    required: "重复密码不能为空",
                    equalTo: "两次密码不一致"
                },
                questionNoOne: {
                    required: "请选择密保问题"
                },
                questionNoTwo: {
                    required: "请选择密保问题"
                },
                questionNoThree: {
                    required: "请选择密保问题"
                }
            }
        })
        ;
    }
};

$(function () {
    pageContent.valid();
    pageContent.smsCodeBind();
    $("select").bind("change", function () {
        var inputNo = "#" + $(this).attr("inputNo");
        $(inputNo).attr("name", "params[" + $(this).val() + "]");
        $(inputNo).rules("add", {
            required: true,
            messages: {
                required: "密保答案不能为空"
            }
        });
    });
})







