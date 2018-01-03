$().ready(function () {
    //默认日期为一个月
    choseDate($('#oneMonth'), 1)
    //隐藏交易查询信息
    $('#payId').hide();
    $('#paySelect').hide();
    $('#queryBtnPay').hide();
    $('#expPay').hide();

    $("#queryBtn").bind("click", function () {
        jpage.init();
    });

    // 分页开始

    var jpage = {
        total: 0,
        init: function () {
            jpage.pageInit(1);
            jpage.pageChange(1)
        },
        pageInit: function (total, pages) {
            $.jqPaginator('#pagination1', {
                totalCounts: total,
                pageSize: 5,
                visiblePages: pages,
                currentPage: 1,
                first: '<li class="first"><a href="javascript:void(0);">首页<\/a><\/li>',
                prev: '<li class="prev"><a href="javascript:void(0);"><i class="arrow arrow2"><\/i>上一页<\/a><\/li>',
                next: '<li class="next"><a href="javascript:void(0);">下一页<i class="arrow arrow3"><\/i><\/a><\/li>',
                last: '<li class="last"><a href="javascript:void(0);">末页<\/a><\/li>',
                page: '<li class="page"><a href="javascript:void(0);">{{page}}<\/a><\/li>',
                onPageChange: function (num, type) {
                    if (type == 'change') {
                        jpage.pageChange(num);
                    }
                }
            })
        },
        pageChange: function (num) {
            var accountType = $('#accountType').val();
            var account = $('#myAccount').val();
            var beginTime = $('#beginTime').val();
            var endTime = $('#endTime').val();
            var flag = 3;
            var state = $('#state').val();
            $.ajax({
                url: ctx + "/trade/withdrawalListQuery",
                type: "post",
                data: {
                    currPageNum: num,
                    pageSize: 5,
                    accountType: accountType,
                    accountNo: account,
                    beginTime: beginTime,
                    endTime: endTime,
                    flag: flag,
                    state: state
                },
                dataType: "json",
                success: function (data) {
                    $('#withBody').empty();
                    if (data.pageData.total > 0) {
                        jpage.total = parseInt(data.pageData.total);
                        jpage.pages = parseInt(data.pageData.pages);
                        $.each(data.pageData.resultList, function (key, val) {
                            var row = "<tr>\n" +
                                "<td>" + val.applyTime + "</td>\n" +
                                "<td>" + val.storeName + "</td>\n" +
                                "<td>" + val.bankCard + "</td>\n" +
                                "<td>$" + val.withdrawalMoney + "</td>\n" +
                                "<td>$" + val.withdrawalFee + "</td>\n" +
                                "<td>" + val.tradeRate + "</td>\n" +
                                "<td>" + "¥" + val.sucMoney + "</td>\n" +
                                "<td>" + val.finishTime + "</td>\n" +
                                "<td class=\'" + val.clazz + "\'  >\n" +
                                "<span>" + val.state + "</span>\n" +
                                "</td>\n" +
                                "</tr>";
                            $('#withBody').append(row);
                        });
                        $('#pagination1').jqPaginator('option', {
                            totalCounts: jpage.total,
                            pages: jpage.pages
                        });
                    }
                }
            });
        }
    };
    jpage.init();
})