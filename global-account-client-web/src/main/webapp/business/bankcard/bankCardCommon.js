/**
 * 判断session是否过期，过期则跳转至登录页面
 * @param data ajax返回数据
 */
function checkLoginOut(data) {
    if (data && data.code == 999) {
        window.location.href = ctx + "/login/index.do";
        return;
    }
}


/**
 * 用户实名认证状态查询
 * @param requestUrl 如果已经实名成功了跳转的页面
 * @param requestType 请求类型
 */
function userRealNameStatusQuery(requestUrl, requestType, userInfoNo) {
    $.ajax({
        url: ctx + "/auth/userRealNameStatusQuery",
        type: "POST",
        dataType: "JSON",
        success: function (data) {
            if (data && data.code == 999) {
                window.location.href = ctx + "/login/index.do";
                return;
            } else if (data.code == 1) {
                errorMsgShow(data.message);
            } else if (data.code == 0 && (data.obj.realNameStatus == 0)) {
                modalShow("您还未通过实名认证", "实名通过后才能添加提现银行卡", "去完成实名认证", function () {
                    var realNameUrl = ctx + "/auth/" + requestType + "/personAuthAddPage.do";
                    if (data.obj.userType == 2) {
                        realNameUrl = ctx + "/auth/" + requestType + "/orgAuthAddPage.do";
                    }
                    window.location.href = realNameUrl;
                });
            } else if (data.code == 0 && (data.obj.realNameStatus == 3)) {
                modalShow("实名认证审核失败", "实名通过后才能添加提现银行卡", "去补充资料完成实名认证", function () {
                    var realNameUrl = ctx + "/auth/personAuthEditPage.do?userInfoNo=" + userInfoNo;
                    if (data.obj.userType == 2) {
                        realNameUrl = ctx + "/auth/1/orgAuthEditPage.do?userInfoNo=" + userInfoNo;
                    }
                    window.location.href = realNameUrl;
                });
            } else if (data.code == 0 && data.obj.realNameStatus == 2) {
                window.location.href = requestUrl;
            } else if (data.code == 0 && data.obj.realNameStatus == 1) {
                errorMsgShow("用户正在实名审核中，暂时无法添加银行卡。请等待实名认证审核通过后添加提现银行卡");
            }
        },
        error: function () {
            errorMsgShow("操作失败，请重试或联系管理员");
        }
    });
}