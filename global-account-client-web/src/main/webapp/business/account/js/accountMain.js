$().ready(function () {
    var jpage = {
        total: 0,
        init: function () {
            jpage.pageChange(1, $(".ccyList a[class='active']").attr("ccy"));
            $("#search").bind("click", function (e) {
                jpage.pageChange(1, $(".ccyList a[class='active']").attr("ccy"));
            })
            $(".ccyList a").bind("click", function (e) {
                jpage.pageChange(1, $(this).attr("ccy"));
            })
        },
        pageChange: function (num, ccy) {
            $.ajax({
                url: ctx + "/account/query.do",
                type: "post",
                data: {
                    storeName: $("#condition").val(),
                    ccy: ccy,
                    pageNum: num,
                    pageSize: 10
                },
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                dataType: "json",
                success: function (data) {
                    $("table tbody").empty()
                    if (data != null) {
                        $.each(data.list, function (key, val) {
                            var name = typeof(val.name) == 'undefined' ? "" : val.name;
                            var accNo = typeof(val.bankAccNo) == 'undefined' ? "" : val.bankAccNo;
                            var ccy = typeof(val.ccy) == 'undefined' ? "" : val.ccy;
                            var availableAmt = typeof(val.availableAmt) == 'undefined' ? "" : val.availableAmt + " " + ccy;
                            var withdrawProcessAmt = typeof(val.withdrawProcessAmt) == 'undefined' ? "" : val.withdrawProcessAmt + " " + ccy;

                            var addUrl = ctx + "/account/" + val.storeNo + "/modifyStorePage";
                            var modifyUrl = "";
                            if (val.userType == 1) {
                                modifyUrl = ctx + "/auth/personAuthEditPage.do?userInfoNo=" + val.userInfoNo;
                            }
                            if (val.userType == 2) {
                                modifyUrl = ctx + "/auth/3/orgAuthEditPage.do?userInfoNo=" + val.userInfoNo;
                            }

                            var tr = "<tr><td class='first'><a href='" + ctx + "/account/" + val.storeNo + "/accountdetail'>" + val.storeName + "</a></td>";
                            //审核中
                            if (val.realnameStatus == 1) {
                                tr += "<td class='second'><p class='fz-c-red'> 审核中</p></td>";
                            }
                            //审核成功
                            if (val.realnameStatus == 2) {
                                //店铺开户失败
                                if (val.accountApplyStatus == 1) {
                                    tr += "<td class='second'><p class='fz-c-red'> 开户处理中</p></td>";
                                } else if (val.accountApplyStatus == 3) {
                                    tr += "<td class='second'><p class='fz-c-red'> 店铺开户失败</p></td>";
                                } else {
                                    tr += "<td class='second'><p >" + name + "  " + accNo + "</p></td>";
                                }
                            }
                            //审核失败
                            if (val.realnameStatus == 3) {
                                tr += "<td class='second'><p class='fz-c-red'> 审核失败</p></td>";
                            }
                            if (val.realnameStatus == 2 && val.accountApplyStatus == 2) {
                                tr += "<td class='third'>" + availableAmt + "</td><td class='fourth'>" + withdrawProcessAmt + "</td>";
                            } else {
                                tr += "<td class='third'></td><td class='fourth'></td>";
                            }
                            var controlTr = "";
                            var modifyTr = ""
                            //审核失败
                            if (val.realnameStatus == 3) {
                                modifyTr += "<button class='modify-btn' onclick=\"javascript: window.location='" + modifyUrl + "'\">修改</button>";
                            }
                            //无店铺
                            if (val.storeExist == "N") {
                                controlTr += "<button onclick=\"javascript: window.location='" + addUrl + "'\">补充店铺信息</button>"
                            }
                            tr += "<td class='btn-box fifth' >" + modifyTr + controlTr + "</td></tr>";
                            $("table tbody").append(tr);
                        });
                    }
                }
            });
        }
    };
    jpage.init();
})
