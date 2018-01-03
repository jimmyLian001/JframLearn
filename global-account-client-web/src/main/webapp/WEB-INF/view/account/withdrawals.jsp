<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge，chrome=1">
    <meta name="renderer" content="webkit">
    <title>个人用户注册</title>
    <link href="${ctx}/resource/css/font-awesome.min.css" rel="stylesheet" media="screen" type="text/css">
    <link rel="stylesheet" href="${ctx}/resource/css/errorStyle.css?version=${version}">
    <script src="${ctx}/business/auth/authAdd.js/register/js/auth.js?version=${version}"></script>
    <script src="${ctx}/resource/js/common/plugins/verify/jquery.validate.min.js?version=${version}"></script>
    <script src="${ctx}/resource/js/common/plugins/verify/messages_zh.min.js?version=${version}"></script>
    <script src="${ctx}/resource/js/common/jquery.form.js?version=${version}"></script>
</head>
<body>
<%@ include file="/WEB-INF/view/common/header.jsp" %>
<script>
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
        //校验文件类型
        if(!checkFileExt(fileName)){
            showErrorMessage("订单明细文件格式不正确，仅支持【xlsx,xls】格式","error");
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
            checkBaseInfo();
        }
    }
    /**校验基础信息**/
    function checkBaseInfo() {
        $.ajax({
            url: ctx + "/withdraw/confirmCheck",
            type: "post",
            data: {withdrawAmt: $('#withdrawAmt').val(), withdrawCcy:$('#withdrawCcy').val(), payPwd:$('#payPassword').val(),bankCardRecordNo:$('#recordNo').val()},
            success: function (data) {
                if (data.success=='true') {
                    $("#uploadform").submit();
                } else {
                    showErrorMessage(data.errorMsg,"error");
                    setWithdrawBtn(true);
                }
            },
            error: function (data) {
                showErrorMessage("操作失败，请重试或联系管理员","error");
            }
        });
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
                                window.location="${ctx}/withdraw/withdrawResult?withdrawCcy=${withdrawCcy}";
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
    /**
     *校验文件后缀是否合法
     **/
    function checkFileExt(filename) {
        var arr = ["xlsx","xls"];  /**csv后续补充**/
        var index = filename.lastIndexOf(".");
        var ext = filename.substr(index+1);
        for(var i=0;i<arr.length;i++)  {
            if(ext == arr[i]) {
                return true;
            }
        }
        return false;
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
        }, 6000);
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

</script>
<div class="reg-wrap pt20">
    <div class="breadcrumb w1200">
        <a href="javascript:;">我的账户</a>
        <span> > </span>
        <a href="javascript:;" class="current-details">提现</a>
    </div>
    <form id="uploadform" encType="multipart/form-data" method="post" action="${ctx}/withdraw/updateCheckFile">
        <input type="hidden" id="balance" name="balance"/>
        <input type="hidden" id="withdrawCcy" name="withdrawCcy" value="${withdrawCcy}"/>
        <!--提现账号（虚拟钱包ID）-->
        <input type="hidden" id="withdrawAccNo" name="withdrawAccNo"/>
        <!--银行卡持有人-->
        <input type="hidden" id="bankCardHolder" name="bankCardHolder"/>
        <!--银行卡记录号-->
        <input type="hidden" id="recordNo" name="recordNo"/>
        <div class="w1200">
            <div class="apply-con">
                <div class="common-title clearfix">
                    <h3 class="fl">提现到银行卡</h3>
                </div>
                <div class="ui-form-box ">
                    <div class="ui-form-item">
                        <label class="ui-form-label">选择提现账户<i>*</i></label>
                        <div class="ui-input-block">
                            <select class="ui-input ui-select " id="accountSelect">
                                <option value="" class="select-default">店铺名称-账户</option>
                                <c:forEach items="${userStoreAccount}" var="storeAccount" varStatus="index">
                                    <option value="${storeAccount.walletId}">
                                        <c:out value="${storeAccount.storeName}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="ui-form-item form-text-box">
                        <label class="ui-form-text">账户总金额</label>
                        <div class="ui-text-block">
                            <span class="bold"> ${balanceAmt} USD</span>
                        </div>
                    </div>
                    <div class="ui-form-item">
                        <label class="ui-form-label mt25">提现银行<i>*</i></label>
                        <div class="ui-input-block">
                            <c:forEach items="${userBankCard}" var="bankCard" varStatus="index" >
                                <div class="bank-card-radio" id="${bankCard.cardNo}" recordNo="${bankCard.recordNo}">
                                    <img src="${ctx}/resource/images/bank/${bankCard.bankCode}.png" alt="pic" onerror="onImgError(this)">
                                    <div class="bank-infor">
                                        <p class="bank-card-name"><c:out value="${bankCard.bankName}"/>(CNY)</p>
                                        <p class="bank-card-num"><c:out value="${bankCard.cardNo}"/></p>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="ui-form-item">
                        <label class="ui-form-label">提现金额<i>*</i></label>
                        <div class="ui-input-block">
                            <input type="text" id="withdrawAmt" name="withdrawAmt" maxlength="15"
                                   t_value="" o_value="" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
                                   placeholder="请输入提现金额" class="ui-input" onblur="calculateFee()">
                            <button class="sms-code currency" disabled>USD</button>
                            <div style="color: red;float: left; margin-left: 1%" id="balanceNotEnougth"></div>
                        </div>
                        <div class="ui-form-tip warning">
                            <i class="fa fa-minus-circle" aria-hidden="true"></i>
                            <span>请输入短信验证码</span>
                        </div>
                    </div>
                    <div class="ui-form-item">
                        <label class="ui-form-label">订单明细<i>*</i></label>
                        <div class="ui-input-block border">
                            <input type="text" value="" id="show_text_check"  disabled="disabled">
                            <input type="file" id="uploadFile" name="file" class="upload-file-input" onchange="fileUploadChange('uploadFile','show_text_check')"
                                   onclick="fileUploadChange('uploadFile','show_text_check')">
                            <label class="upload-file-label">选择文件</label>
                            <a href="${ctx}/withdraw/downloadOrderDetailModel">下载明细模板</a>
                        </div>
                        <div class="ui-form-tip warning" >
                            <i class="fa fa-minus-circle" aria-hidden="true"></i>
                            <span>请选择订单明细文档</span>
                        </div>
                    </div>
                    <div class="ui-form-item form-text-box">
                        <label class="ui-form-text">提现手续费</label>
                        <div class="ui-text-block">
                            <input type="text" id="fee" name="fee" disabled="true" readonly="true" value="0.00"
                                   maxlength="40" style="width: 52px;"/>
                            USD
                        </div>
                        <div style="color: red;float: left; margin-left: 1%" id="withdrawAmtError" class="ay3"></div>
                    </div>
                    <div class="ui-form-item">
                        <label class="ui-form-label">请输入支付密码<i>*</i></label>
                        <div class="ui-input-block">
                            <input type="password" id="payPassword" maxlength="6" placeholder="请输入6位支付密码" class="ui-input">
                        </div>
                        <div class="ui-form-tip warning">
                            <i class="fa fa-minus-circle" aria-hidden="true"></i>
                            <span>请输入对公银行账户</span>
                        </div>
                        <div class="sub-btn">
                            <a class="back-btn" href="javascript:void(0);" id="btnSubmit" onclick="doWithDraw(this);">提交</a>
                            <span class="back-btn" onclick="javascript:window.location='${ctx}/account/index.do'">返回</span>
                        </div>
                        <br/>
                        <div id="errorMessage" class="center"></div>
                        <div id="failFileDiv" class="right" style="display:none">
                            <div style="margin-left: 215px;">
                                <img src="${ctx}/resource/images/tanhao.png"  name="showStep" width="20" height="15"/>
                                文件数据有误
                                <a href="javascript:void(0)" id="downloadFileInfo">
                                    <span style="color:rgb(0,176,240);">，点击下载原因。</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
</body>
</html>