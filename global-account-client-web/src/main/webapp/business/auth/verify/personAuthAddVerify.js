$().ready(function () {
    $.validator.addMethod("checkEmail", function (value, element) {
        var regex = /\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/;
        return this.optional(element) || (regex.test(value));
    }, "邮箱格式不正确");

    $.validator.addMethod("checkIdNo", function (value, element) {
        return this.optional(element) || identityCodeValid(value);
    }, "身份证格式不正确");

    $.validator.addMethod("checkNo", function (value, element) {
        var regexNo = /^[0-9]*$/;
        return this.optional(element) || (regexNo.test(value));
    }, "格式不正确，请输入数字");

    $.validator.addMethod("checkSpace", function (value, element) {
        return this.optional(element) || (checkSpace(value));
    }, "格式不正确，请重新输入");

    $("#personAuthForm").validate({
        errorElement: "em",
        rules: {
            cardHolder: {
                required: true,
                checkSpace: true
            },
            cardNo: {
                required: true,
                rangelength: [16, 19],
                checkNo: true
            },
            englishName: {
                required: true,
                checkSpace: true
            },
            email: {
                required: true,
                checkEmail: true
            },
            idNo: {
                required: true,
                checkIdNo: true
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
            cardHolder: "请输入姓名",
            cardNo: {
                required: "请输入银行卡号",
                rangelength: "请输入正确银行卡号"
            },
            email: "请输入邮箱",
            englishName: "请输入英文名称",
            idNo: {
                required: "请输入身份证号",
                rangelength: "请输入正确身份证号"
            },
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
            var flag = checkFile($('#idFrontImage').val(), '正面照');
            if (flag) {
                flag = checkFile($('#idReverseImage').val(), '反面照');
            }
            if (flag) {
                flag = checkAddress($('#address').val(), '请输入详细地址');
            }
            if (flag) {
                $("#person_reg_sub").attr("disabled", true);
                trim("cardHolder");
                trim("englishName");
                $(form).ajaxSubmit({
                    type: 'post',
                    dataType: "json",
                    async: false,
                    success: function (data) {
                        if (data.code == 1) {
                            $("#person_reg_sub").attr("disabled", false);
                            errorMsgShow(data.message);
                        } else {
                            var requestType = $('#requestType').val();
                            $("#personAuthForm").resetForm();
                            $("#idFront").attr("src", "");
                            $("#idReverse").attr("src", "");
                            $("#idFront").attr("style", "");
                            $("#idReverse").attr("style", "");

                            // 我的账户首页
                            if (requestType == '1') {
                                window.location.href = ctx + "/account/index.do"
                            }
                            // 开户申请页面
                            if (requestType == '2') {
                                window.location.href = ctx + "/account/applyAccPage";
                            }
                        }
                    }
                });
            }
        }
    });
    jQuery.validator.addMethod("isRegion", function (value, element) {
        return $('#province').val() != '' && $('#area').val() != '' && $('#city').val();
    }, "请正确选择省，市，区");

    function checkFile(file, text) {
        if (file == '' || file == undefined) {
            $('#div_error').show();
            $('#span_error').html('请上传身份证' + text);
            return false;
        }
        var extStart = file.lastIndexOf(".");
        var ext = file.substring(extStart, file.length).toUpperCase();
        if (ext != '.JPG' && ext != '.GIF' && ext != '.PNG') {
            $('#div_error').show();
            $('#span_error').html('请上传正确格式');
            return false;
        }
        $('#div_error').hide();
        return true;
    }

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
