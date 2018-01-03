var balanceFlag = false;
$(function () {
    $("#uploadform").ajaxForm({
        success: function (data) {
            if (data.success == 1) {
                getCheckFileResult(data.redisKey, data);
            } else {
                showErrorMessage(data.errorMsg,"error");
                setWithdrawBtn(true);
            }
        }
    });
});
function doWithDraw() {
    var result = true;
    $("#failFileDiv").hide();
    var account  = $("#accountSelect").val();
    if (account == "") {
        showErrorMessage("请选择提现账户","error");
        return;
    }
    var card = $("div.current").attr("id");
    if (card == ""||card==undefined) {
        showErrorMessage("请选择提现银行卡","error");
        return ;
    }
    var withdrawAmt = $('#withdrawAmt').val();
    if (withdrawAmt == ""|| withdrawAmt==undefined) {
        showErrorMessage("提现金额不能为空","error");
        return;
    }
    var regExp =  new RegExp("(^[1-9]\\d{1,}\\.\\d{1,}$)|(^[1-9]\\d{1,}$)");
    if (!regExp.test(withdrawAmt)) {
        showErrorMessage("起提金额为$10","error");
        return;
    }
    //订单明细文件校验
    var fileName = $("#uploadFile").val();
    if (fileName == "") {
        showErrorMessage("订单明细不能为空","error");
        return;
    }
    var withdrawFee = $('#fee').val();
    if(withdrawFee == ""){
        showErrorMessage("提现手续费不能为空","error");
        return;
    }
    if (balanceFlag) {
        showErrorMessage("余额不足","error");
        return;
    }
    var payPassword = $('#payPassword').val();
    if(payPassword == ""){
        showErrorMessage("支付密码不能为空","error");
        result = false;
        return;
    }
    $("#withdrawAccNo").val(account);
    $("#recordNo").val($("div.current").attr("recordNo"));
    if (result) {
        setWithdrawBtn(false);
        $("#uploadform").submit();
    }
}
function getCheckFileResult(fileId, dataAll) {
    var timing = setInterval(function () {
        sendRequest("${ctx}/withdraw/getValidateResult", {
            fileBatchNo: fileId
        }, function (data) {
            if (data.flag == 1) {//成功
                clearInterval(timing);
                //文件提交
                sendRequest(
                    "${ctx}/withdraw/confirm",
                    {withdrawAmt: dataAll.withdrawAmt, fileId: dataAll.fileId, fileName: dataAll.fileName,
                        withdrawCcy:$('#withdrawCcy').val(), payPwd:$('#payPassword').val(),bankCardRecordNo:$('#recordNo').val(),
                        withdrawAccNo:$("#withdrawAccNo").val()},
                    function (data) {
                        if ('true'==data.success) {
                            window.location="${ctx}/withdraw/withdrawResult";
                        } else {
                            showErrorMessage(data.errorMsg,"error");
                        }
                        setWithdrawBtn(true);
                    }
                );
            } else if (data.flag == 2) {//失败
                clearInterval(timing);
                setWithdrawBtn(true);
            } else if (data.flag == 3) {//文件数据有误
                clearInterval(timing);
                $("#failFileDiv").show();
                setWithdrawBtn(true);
                $("#downloadFileInfo").click(function () {
                    doDownloadTxt(data.errorFileId);
                });
            } else if (date.flag == -1) {//异常
                clearInterval(timing);
                showErrorMessage(data.errorMsg,"error");
                setWithdrawBtn(true);
            }
        }, true);
    }, 5000);
}

/**设置按钮是否有效 true 按钮可用 false 按钮不可用**/
function setWithdrawBtn(flag){
    if(flag){
        $("#btnSubmit").attr("disabled", false);
        $("#btnSubmit").text('提现');
    }else{
        $("#btnSubmit").text('提现处理中');
        $("#btnSubmit").attr("disabled", true);
    }
}
//显示提示信息
function showErrorMessage(msg, type) {
    $("#errorMessage").html('<div class="alert_search ' + type +
        '" style="text-align: center;width:auto;float:left;">' + msg + "</div>");
    $(".alert_search").css("margin-left", (760 - $(".alert_search").outerWidth()) / 2 - 25);
    $(".alert_search").css("margin-bottom", "10px");
    $(".alert_search").click(function () {
        $(this).animate({
            opacity: 0
        }, "200", "linear", function () {
            $(this).remove();
        });
    });
    setTimeout(function () {
        $(".alert_search").slideUp("slow");
    }, 4000);
}
/**
 * 下载文件
 */
function doDownloadTxt(fileId) {
    location.href = "${ctx}/withdraw/downloadFile?fileId=" + fileId;
}

/**计算手续费**/
function calculateFee() {
    var withdrawAmt = $('#withdrawAmt').val();
    var regExp = new RegExp("(^[1-9]\\d{1,}\\.\\d{1,}$)|(^[1-9]\\d{1,}$)");
    if (!regExp.test(withdrawAmt)) {
        $('#fee').val("0.00");
        showErrorMessage("起提金额为$10","error");
        return;
    } else {
        $("#withdrawAmtError").html("");
    }
    var withdrawCcy = $('#withdrawCcy').val();
    $.ajax({
        url: ctx + "/withdraw/withdrawCashFee",
        type: "post",
        data: {withdrawAmt: withdrawAmt,withdrawCcy:withdrawCcy},
        success: function (data) {
            if (data.success) {
                var fee = data.fee;
                var balance = data.balance;
                $("#balance").val(balance);
                $('#fee').val(fee);
                if (parseFloat(withdrawAmt) + parseFloat(fee) > balance) {
                    balanceFlag = true;
                    $("#balanceNotEnougth").html('余额不足');
                    return;
                } else {
                    balanceFlag = false;
                    $("#balanceNotEnougth").html("");
                }
            } else {
            }
        },
        error: function (data) {
            showErrorMessage("操作失败，请重试或联系管理员","error");
        }
    });
}
/**
 * file 控件中的值放入到指定的Text文本框中
 * @param fileSource
 * @param targetText
 */
function fileUploadChange(fileSource, targetText) {
    $("#" + targetText).val($("#" + fileSource).val());
}