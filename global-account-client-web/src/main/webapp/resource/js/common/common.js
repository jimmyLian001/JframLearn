$(function () {
    // 新增银行卡-企业
    $('.bank-btn').find('button').click(function () {
        $(this).addClass('active').siblings('button').removeClass('active');
        var index = $(this).index();
        $('.bank-tab-content>div').eq(index).addClass('block').siblings().removeClass('block');
        $('.bank-tab-content>div').find('.input-box>input').val('');
    });
    /*// 上传照片页
    $('.account-btn').find('button').click(function () {
        $(this).addClass('active').siblings('button').removeClass('active');
        var index = $(this).index();
        $('.license-content>div').eq(index).addClass('block').siblings().removeClass('block');
    });*/
    // 点击图片查看
    var picExampleMask = $('.mask');
    $('.picture-example').click(function () {
        var imgUrl = $(this).attr('data-imgUrl');
        picExampleMask.show().find('img').attr('src', imgUrl);
    });
    picExampleMask.click(function () {
        $(this).hide();
    });
    $('.close-btn').click(function () {
        $('.top-tip').removeClass('show')
    });

    //  明细查询
    $('.query-tab>ul>li').find('a').click(function () {
        $(this).addClass('active').parent().siblings('li').find('a').removeClass('active');
        var index = $(this).parent().index();
        $('.filtering-content>div').eq(index).addClass('block').siblings().removeClass('block');
        $('.query-bottom>div').eq(index).addClass('block').siblings().removeClass('block');
    });
    // 个人申请
    $('.shop-btn').find('button').click(function () {
        $(this).addClass('active').siblings('button').removeClass('active');
        var index = $(this).index();
        $('.shop-content>div').eq(index).addClass('block').siblings().removeClass('block');
    });
    // 店铺
    $('.shop-ul>li>a').click(function () {
        if (!$(this).is(".notSelect")) {
            $(this).addClass('active').parent().siblings('li').find('a').removeClass('active');
        }
    });
    // 模态框
    var modalBox = $('.modal-box');
    modalBox.click(function () {
        modalBox.addClass('none');
    });
    $('.popup-box').click(function (e) {
        e.stopPropagation();
    })
    //校验框架
    var validateForm = $("form");
    if (validateForm.is(".validate")) {
        //设置文本框验证功能
        validateForm.validate({
            // 错误插入位置，以及插入的内容
            errorPlacement: function (error, element) {
                var obj = $(element).parent().find(".input-tip");
                obj.removeClass("none");
                obj.find("span").html(error);
            }, // 验证成功后调用的方法
            success: function (label) {
                var a = label[0].htmlFor;
                $("[name=" + a + "]").parent().find(".input-tip").addClass("none");
                $(label).parent().find(".input-tip").addClass("none");
            }, onkeyup: function (element) {

                return $(element).valid();
            }, onfocusin: function (element) {
                return $(element).valid()
            }, ignore: ":hidden"
        })
        ;
    }
});

/**
 * 错误信息显示
 * @param msg 需要显示的错误内容
 */
function errorMsgShow(msg) {
    $("#topTipErrMsg").find("#errorMsg").html(msg);
    $(".top-tip").addClass("show");
    var time = setTimeout(function () {
        $(".top-tip").removeClass("show");
        clearInterval(time);
    }, 3000)
}


/**
 * 用户实名认证状态查询
 * @param requestUrl 如果已经实名成功了跳转的页面
 * @param requestType 请求类型
 */
function userRealNameStatusQuery(requestUrl, requestType) {
    sendRequest(
        ctx + "/auth/userRealNameStatusQuery",
        {},
        function (data) {
            if (data.code == 1) {
                errorMsgShow(data.message);
            } else if (data.code == 0 && !data.obj.realNameStatus) {
                modalShow("您还未通过实名认证", "实名通过后才能添加提现银行卡", "去完成实名认证", function () {
                    var realNameUrl = ctx + "/auth/" + requestType + "/personAuthAddPage.do";
                    if (data.obj.userType == 2) {
                        realNameUrl = ctx + "/auth/" + requestType + "/orgAuthAddPage.do";
                    }
                    window.location.href = realNameUrl;
                });
            } else {
                window.location.href = requestUrl;
            }
        }
    );
}

/**
 * 用户实名认证状态查询
 * @param requestUrl 如果已经实名成功了跳转的页面
 * @param requestType 请求类型
 */
function userRealNameStatusQuery2(requestUrl, requestType) {
    sendRequest(
        ctx + "/auth/userRealNameStatusQuery",
        {},
        function (data) {
            if (data.code == 1) {
                errorMsgShow(data.message);
            } else if (data.code == 0 && data.obj.realNameStatus == 0) {
                modalShow("您还未通过实名认证", "实名通过后才能申请境外账号", "去完成实名认证", function () {
                    var realNameUrl = ctx + "/auth/" + requestType + "/personAuthAddPage.do";
                    if (data.obj.userType == 2) {
                        realNameUrl = ctx + "/auth/" + requestType + "/orgAuthAddPage.do";
                    }
                    window.location.href = realNameUrl;
                });
            } else {
                window.location.href = requestUrl;
            }
        }
    );
}

/**
 * 用户是否可提现
 * @param requestUrl 如果已经开通店铺跳转的提现页面
 * @param requestType 请求类型
 */
function userWithdrawStatusQuery(withdrawUrl, pwdUrl) {
    sendRequest(
        ctx + "/withdraw/userWithdrawStatusQuery",
        {},
        function (data) {
            if (data.code == 0) {
                errorMsgShow(data.message);
            } else if (data.code == 2) {
                errorMsgShow("暂无店铺无法提现，请您先开通店铺");
            } else if (data.code == 3) {
                modalShow("", "您未设置支付密码", "去设置支付密码", function () {
                    window.location.href = pwdUrl;
                });
            } else {
                window.location.href = withdrawUrl;
            }
        }
    );
}

/**
 * 模式框弹出
 * @param title 弹出提示框标题
 * @param text 提示内容
 * @param buttonText 按钮显示名称
 * @param buttionFunction 点击按钮执行的事件
 */
function modalShow(title, text, buttonText, buttonFunction) {
    var modalBox = $('.modal-box');
    //设置标题
    modalBox.find("#modal-box-title").html(title);
    //设置内容
    modalBox.find("#modal-box-text").html(text);
    //设置按钮上的信息
    modalBox.find("#modal-box-button").html(buttonText);

    modalBox.toggleClass('none');
    //$('body').css('overflow', 'hidden');
    $('.popup-box').addClass('dropdown');
    modalBox.find("#modal-box-button").click(function () {
        buttonFunction()
    });
}


/**
 * @param requestUrl 访问地址
 * @param paramData 访问参数
 * @param callback 回调函数
 */
function sendRequest(requestUrl, paramData, callback) {
    $.ajax({
        async: true,
        cache: false,
        type: "POST",
        dataType: "json",
        url: requestUrl,
        data: paramData,
        success: function (data) {
            try {
                if (data && data.code == 999) {
                    console.log("用户登录session超时，退出登录");
                    window.location.href = ctx + "/login/index.do";
                    return false;
                }
                callback(data);
            } catch (e) {
                console.log(e)
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            errorMsgShow("请求服务器异常，请稍后重试");
        }
    });
}


/**
 * Ajax form 表单提交
 */
function ajaxFormSubmit(callback) {
    $("#ajaxForm").ajaxForm({
        success: function (data) {
            //返回错误码为1时异常，不为1时代表其他处理方式，其他处理方式可从调用方传入
            if (data.code == 1) {
                errorMsgShow(data.message)
            } else {
                callback(data);
            }
        }, error: function (jqXHR, textStatus, errorThrown) {
            errorMsgShow("请求服务器异常，请稍后重试");
        }
    });
}

/**
 * 站点选择
 */
function siteSelect(site) {
    $(".small-prompt-box").addClass("none");
    if ("US" != site && "EU"!=site) {
        $("." + site).removeClass("none");
        return false;
    }
    $("#siteId").val(site);
}

/**
 * trim
 */
function trim(id) {
    var object = $("#" + id);
    object.val($.trim(object.val()));
}

    /**
    * 银行卡号每输入四位空格
    */
function bankCardAddBlank(thisId) {
    $("#"+thisId).on("keydown",function(){
        //获取当前光标的位置
        var caret = this.selectionStart;
        //获取当前的value
        var value = this.value;
        //从左边沿到坐标之间的空格数
        var sp =  (value.slice(0, caret).match(/\s/g) || []).length;
        //去掉所有空格
        var nospace = value.replace(/\s/g, '');
        //重新插入空格
        var curVal = this.value = nospace.replace(/\D+/g,"").replace(/(\d{4})/g, "$1 ").trim();
        //从左边沿到原坐标之间的空格数
        var curSp = (curVal.slice(0, caret).match(/\s/g) || []).length;
        //修正光标位置
        this.selectionEnd = this.selectionStart = caret + curSp - sp;
    });
}

/**
 * spaceCheck
 */
function checkSpace(id) {
    var str = id.replace(/\s{1,}|\t/g, "");
    return str.length != 0
}