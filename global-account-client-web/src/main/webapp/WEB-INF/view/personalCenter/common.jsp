<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script>

    var loginRegex = /^(?![a-zA-z]+$)(?!\d+$)(?![!@#$%^&*()_+`\-={}:";'<>?,.\/]+$)[a-zA-Z\d!@#$%^&*()_+`\-={}:";'<>?,.\/]{6,16}$/;
    var paymentRegex = /^\d{6}$/;

    var pageContent = {
        submitFlag: true,
        times: -1,
        sendCode: function (obj, busiType) {
            $("input[name='verifyCode']").val("");
            $.ajax({
                type: "post",
                url: "${ctx}/person/center/sendCode2.do",
                data: {
                    "loginNo": $("input[name='loginNo']").val(),
                    "busiType": busiType
                },
                async: false,
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                success: function (result) {
                    if (result) {
                        pageContent.times = 60;
                        $(obj).attr("disabled", true);
                        if (pageContent.times == 0) {
                            $(obj).html("获取验证码");
                        }
                        $(obj).html("重新发送(<span id='times'>" + pageContent.times + "</span>s)")
                    } else {
                        alert("发送验证码失败!")
                    }
                }
            });
        },
        change: function () {
            $("input").bind("change", function () {
                if ($("form").attr("type") == 'payment' || $("form").attr("type") == 'login') {
                    $("form").find("#noSameDiv").html("两次密码不一致").hide();
                    $("input[name='oldPassword']").parent().next().html("请输入新登录密码").hide();
                }
                if ($(this).val() == "") {
                    $(this).parent().next().show();
                    pageContent.submitFlag = false
                } else {
                    $(this).parent().next().hide();
                    pageContent.submitFlag = true
                }
            })
        },
        submit: function () {
            $("form").bind("submit", function () {
                var isOk = true;
                $("input").each(function () {
                    if ($(this).val() == "") {
                        $(this).trigger("change")
                        isOk = false
                    }
                })
                return pageContent.submitFlag && isOk && pageContent.checkPwd(this);
            })
        },
        checkPwd: function (obj) {
            $(obj).find("#noSameDiv").html("两次密码不一致").hide();
            $("input[name='oldPassword']").parent().next().html("请输入新登录密码").hide();
            if ($(obj).attr("type") == 'payment') {
                if (!paymentRegex.test($("input[name='firstPwd']").val())
                    || !paymentRegex.test($("input[name='secondPwd']").val())) {
                    $(obj).find("#noSameDiv").html("支付密码由6位数字组成").show();
                    return false
                }
            }

            if ($(obj).attr("type") == 'login') {
                if (typeof($("input[name='oldPassword']").val()) != "undefined"
                    && !loginRegex.test($("input[name='oldPassword']").val())) {
                    $("input[name='oldPassword']").parent().next().html("旧密码由6-16位字母、数字或符号组成").show();
                    return false;
                }
                if (!loginRegex.test($("input[name='firstPwd']").val())
                    || !loginRegex.test($("input[name='secondPwd']").val())) {
                    $(obj).find("#noSameDiv").html("登录密码由6-16位字母、数字或符号组成").show();
                    return false
                }
            }

            if ($("input[name='firstPwd']").val() != "" && $("input[name='secondPwd']").val() != "" &&
                $("input[name='firstPwd']").val() != $("input[name='secondPwd']").val()) {
                $(obj).find("#noSameDiv").show();
                return false
            }
            return true;
        },
        timeLoop: function (time) {
            if (pageContent.times > 0) {
                pageContent.times = time - 1;
                $("#times").text(pageContent.times);
            } else if (pageContent.times == 0) {
                $(".sms-code").attr("disabled", false);
                $(".sms-code").html("点击免费获取")
            }
        },
        getVerify: function getVerify() {
            $("#imgVerify").attr("src", ctx + "/common/getVerify.do?" + Math.random());
        },
        maxlength: function (event, maxcount) {
            var event = event || window.event;
            var target = event.target || event.srcElement;
            var keyCode = event.charCode || event.keyCode;
            // 8 - backspace , 46 - delete
            if (keyCode != 8 && keyCode != 46) {
                if (target.value.length >= maxcount) {
                    return false;
                }
            }
            if (target.value.length > maxcount) {
                target.value = node.value.substr(0, maxcount);
            }
            return true;
        }
    };

    $(function () {
        pageContent.getVerify();
        pageContent.submit();
        pageContent.change();
        setInterval(function () {
            pageContent.timeLoop(pageContent.times)
        }, 1000)
    })

    function submitFfrom(data, submitButton, url) {
        $('#resetPayPwdByQuestionFrom').submit(function () {
                $.ajax({
                    type: "POST",
                    url: url,
                    data: data,
                    success: function (data) {
                        if (data.msg == 'success') {
                            reviseTelResultDisplay(data, submitButton, $("#paymentPwdIndex"));
                        } else {
                            submitFfrom().attr('disabled', false);
                            showPagePrompts("error", data.errorMessage);
                        }
                    },
                    error: function () {
                        showPagePrompts("error", "操作失败，请重试或联系管理员");
                    }
                });
            }
        );
    }

</script>