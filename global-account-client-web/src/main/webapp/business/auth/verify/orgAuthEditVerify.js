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

    $("#orgAuthForm").validate({
        errorElement: "em",
        rules: {
            englishName: {
                required: true
            },
            email: {
                required: true,
                checkEmail: true
            },
            licenseNo: {
                required: true,
                checkSpace: true,
                rangelength: [9, 18]
            },
            legalName: {
                required: true
            },
            legalNo: {
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
            englishName: "请输入企业英文名称",
            licenseNo: {
                required: "请输入营业执照号",
                rangelength: "请输入正确的营业执照号"
            },
            legalName: "请输入法人姓名",
            email: "请输入邮箱",
            legalNo: {
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
            var flag = checkAddress($('#address').val(), '请输入详细地址');

            if (flag) {
                flag = checkFile($("input[name='multiLicenseType']").val(), $("input[name='idType']").val());
            }

            if (flag) {
                $('#licenseImage_error_div').hide();
                $('#taxRegCertImage_error_div').hide();
                $('#orgCodeCertImage_error_div').hide();
                $('#div_error').hide();
                $("#org_reg_sub").attr("disabled", true);

                trim("companyName");
                trim("englishName");
                trim("legalName");

                $(form).ajaxSubmit({
                    type: 'post',
                    dataType: "json",
                    success: function (data) {
                        if (data.code == 1) {
                            $("#org_reg_sub").attr("disabled", false);
                            errorMsgShow(data.message);
                        } else {
                            var requestType = $('#requestType').val();
                            $(form).resetForm();
                            $("#license").attr("src", "");
                            $("#taxRegCert").attr("src", "");
                            $("#orgCodeCert").attr("src", "");
                            $("#idFront").attr("src", "");
                            $("#idReverse").attr("src", "");
                            $("#license").attr("style", "");
                            $("#taxRegCert").attr("style", "");
                            $("#orgCodeCert").attr("style", "");
                            $("#idFront").attr("style", "");
                            $("#idReverse").attr("style", "");
                            if (requestType == '4') {
                                window.location.href = ctx + "/qualified/2/qualifiedOperationSuccess";
                            } else {
                                window.location.href = ctx + "/account/index.do"
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

    function checkFile(multiLicenseType, idType) {
        // 企业证件类型不变，则不进行校验
        var flag = true;
        // 普通营业执照时校验文件是否为空，营业执照文件不用校验
        if (multiLicenseType != idType && multiLicenseType == 2) {
            flag = check($('#taxRegCertImage').val(), '请上传税务登记证照片', 'taxRegCertImage_error_div', 'taxRegCertImage_error_span');
            if (flag) {
                flag = check($('#orgCodeCertImage').val(), '请上传组织机构代码证照片', 'orgCodeCertImage_error_div', 'orgCodeCertImage_error_span');
            }
        }
        return flag;
    }

    function check(file, text, divId, spanId) {
        if (file == '' || file == undefined) {
            $('#' + divId).show();
            $('#' + spanId).html(text);
            return false;
        }
        var extStart = file.lastIndexOf(".");
        var ext = file.substring(extStart, file.length).toUpperCase();
        if (ext != '.JPG' && ext != '.GIF' && ext != '.PNG') {
            $('#' + divId).show();
            $('#' + spanId).html('请上传正确格式');
            return false;
        }
        return true;
    }
});
