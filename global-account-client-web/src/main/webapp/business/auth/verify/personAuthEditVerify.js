$().ready(function () {

    $.validator.addMethod("checkEmail", function (value, element) {
        var regex = /\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/;
        return this.optional(element) || (regex.test(value));
    }, "邮箱格式不正确");

    $.validator.addMethod("checkNo", function (value, element) {
        var regexNo = /^[0-9]*$/;
        return this.optional(element) || (regexNo.test(value));
    }, "格式不正确，请输入数字");

    $("#personAuthForm").validate({
        errorElement: "em",
        rules: {
            englishName: 'required',
            email: {
                required: true,
                checkEmail: true
            },
            area: {
                isRegion: true
            },
            postCode: {
                required: true,
                rangelength: [6, 6],
                checkNo: true
            }
        },
        messages: {
            englishName: "请输入英文名称",
            email: "请输入邮箱",
            postCode: {
                required: "请输入邮编",
                rangelength: "请输入正确的邮编"
            }
        },
        errorPlacement: function (error, element) {
            if (element.attr("name") == "fname" || element.attr("name") == "lname") {
                error.insertAfter("#lastname");
            } else {
                error.insertAfter(element);
            }
        },
        submitHandler: function (form) {
            var flag = checkAddress($('#address').val(), '请输入详细地址');
            if (flag) {
                $("#person_reg_sub").attr("disabled", true);
                $(form).ajaxSubmit({
                    type: 'post',
                    dataType: "json",
                    success: function (data) {
                        if (data.code == 1) {
                            $("#person_reg_sub").attr("disabled", false);
                            errorMsgShow(data.message);
                        } else {
                            $(form).resetForm();
                            $("#idFront").attr("src", "");
                            $("#idReverse").attr("src", "");
                            $("#idFront").attr("style", "");
                            $("#idReverse").attr("style", "");
                            window.location.href = ctx + "/account/index.do"
                        }
                    }
                });
            }
        }
    });
    jQuery.validator.addMethod("isRegion", function (value, element) {
        return $('#province').val() != '' && $('#area').val() != '' && $('#city').val();
    }, "请正确选择省，市，区");

    $('#agreeCheck').change(function () {
        if ($(this).is(':checked')) {
            $("#person_reg_sub").attr("disabled", false);
        } else {
            $("#person_reg_sub").attr("disabled", true);
        }
    });

    function checkAddress(address, text) {
        if (address == '' || address == undefined) {
            $('#address_error_div').show();
            $('#address_error_span').html(text);
            return false;
        }
        if (!checkSpace(address)) {
            $('#address_error_div').show();
            $('#address_error_span').html('格式不正确，请输入正确的地址');
            return false;
        }
        if (address.length > 128) {
            $('#address_error_div').show();
            $('#address_error_span').html('详细地址过长，不超过128个字符');
            return false;
        }
        return true;
    }
});
