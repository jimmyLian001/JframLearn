<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge，chrome=1">
    <meta name="renderer" content="webkit">
    <title>交易查询页面</title>
    <link rel="stylesheet" href="${ctx}/resource/css/font-awesome.min.css?version=${version}">
    <script src="${ctx}/resource/js/date/WdatePicker.js?version=${version}"></script>
    <script src="${ctx}/resource/js/common/plugins/paging/jqpaginator.min.js?version=${version}"></script>
    <link rel="stylesheet" href="${ctx}/resource/css/bootstrap.min.css"/>
    <link href="${ctx}/resource/css/base.css" rel="stylesheet" media="screen" type="text/css">
    <link href="${ctx}/resource/css/main.css" rel="stylesheet" media="screen" type="text/css">
</head>
<style>

    .pagination .page {
        border-left: 0px solid #919191;
        border-right: 0px solid #919191;
    }

</style>
<body>

<div class="login-layout">
    <%@ include file="/WEB-INF/view/common/header.jsp" %>
    <div class="same-layout">
        <div class="query-container">
            <div class="query query-top clearfix">
                <%--查询类型按钮--%>
                <div class="query-tab fl">
                    <ul>
                        <li>
                            <a href="javascript:void (0)" id="withdrawalId" class="active"
                               onclick="changeStyle()">提现记录</a>
                        </li>
                        <li>
                            <a href="javascript:void (0)" id="accDetailId" class="active1" onclick="changeStyle1()"
                            >账户明细</a>
                        </li>
                    </ul>
                </div>
                <%--查询条件部分--%>

                <form id="exportExcel" method="post">
                    <input type="hidden" id="type" name="type">
                    <div class="filtering-content">
                        <div class="query-filtering fl main block" style="margin-left: 100px;">
                            <div class="clearfix">
                                <div class="filtering-item fl">
                                    <div class="filtering-left fl">平台</div>
                                    <div class="fl">
                                        <select class="select-box" name="accountType" id="accountType"
                                                onChange="getNextClassify()">
                                            <option value="" selected>全部平台</option>
                                            <c:forEach var="obj" items="${accTypeList}">
                                                <option value="${obj.key}">${obj.value}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="filtering-item fl">
                                    <div class="filtering-left fl">账户</div>
                                    <div class="fl">
                                        <select name="myAccount" id="myAccount" class="select-box">
                                            <option value="" selected>全部账号</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="filtering-item fl" id="withSelect">
                                    <div class="filtering-left fl">状态</div>
                                    <div class="fl">
                                        <select name="state" id="state" class="select-box">
                                            <option class="selected-item"
                                                    ${'4'eq tradeQueryVo.state? 'selected':'' }value="4">
                                                全部
                                            </option>
                                            <option class="selected-item" ${'1'eq tradeQueryVo.state?'selected':'' }
                                                    value="1">
                                                提现处理中
                                            </option>
                                            <option class="selected-item" ${'2'eq tradeQueryVo.state?'selected':'' }
                                                    value="2">
                                                提现成功
                                            </option>
                                            <option class="selected-item" ${'3'eq tradeQueryVo.state?'selected':'' }
                                                    value="3">
                                                提现失败
                                            </option>
                                        </select>
                                    </div>
                                </div>
                                <div class="filtering-item fl none" id="paySelect">
                                    <div class="filtering-left fl">收支</div>
                                    <div class="fl">
                                        <select class="select-box" id="flag" name="flag">
                                            <option value="5">全部</option>
                                            <option  ${'1'eq tradeQueryVo.flag?'selected':'' }
                                                    value="1">
                                                入账
                                            </option>
                                            <option  ${'2'eq tradeQueryVo.flag?'selected':'' }
                                                    value="2">
                                                出账
                                            </option>
                                        </select>
                                    </div>
                                </div>
                                <a class="fl more" href="#" onclick="dateView()">更多+</a>
                            </div>
                            <div class="clearfix none" id="dateChose">
                                <div class="filtering-item fl">
                                    <div class="filtering-left fl">时间</div>
                                    <div class="filtering-right fl">
                                        <a href="javascript:;" style="width: 180px;">
                                            <input type="text" name="beginTime" id="beginTime"
                                                   value="${tradeQueryVo.beginTime}"
                                                   onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',
                                   alwaysUseStartDate:true})">
                                        </a>
                                        <span>-</span>
                                        <a href="javascript:;" style="width: 180px;">
                                            <input type="text" name="endTime" id="endTime"
                                                   value="${tradeQueryVo.endTime}"
                                                   onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',
                                   alwaysUseStartDate:true})">
                                        </a>
                                    </div>
                                </div>
                                <div class="date-item fl">
                                    <ul class="clearfix">
                                        <li id="today8">
                                            <a href="javascript:void (0)" onclick="choseDate($('#today'),8);"
                                               id="today">今天</a>
                                        </li>
                                        <li id="oneMonth1" class="active">
                                            <a href="javascript:void (0)" onclick="choseDate($('#oneMonth'),1);"
                                               id="oneMonth">1个月</a>
                                        </li>
                                        <li id="threeMonth3">
                                            <a href="javascript:void (0)" onclick="choseDate($('#threeMonth'),3);"
                                               id="threeMonth">3个月</a>
                                        </li>
                                        <li id="sixMonth6">
                                            <a href="javascript:void (0)" onclick="choseDate($('#sixMonth'),6);"
                                               id="sixMonth">6个月</a>
                                        </li>
                                        <li id="oneYear7">
                                            <a href="javascript:void (0)" onclick="choseDate($('#oneYear'),7);"
                                               id="oneYear">1年</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="clearfix">
                                <div class="btn-box fl">
                                    <button id="queryBtn" type="button">查询</button>
                                    <button id="queryBtnPay" type="button" onclick="paymentDetailPage()">查询</button>
                                    <button type="button" class="cancel-btn" onclick="resetData()">重置</button>
                                </div>
                                <div class="export-btn fr">
                                    <button id="expWith" onclick="exportExcel(2)">导出报表</button>
                                    <button id="expPay" onclick="exportExcel(1)">导出报表</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <div class="query query-bottom" id="withId">
                <div class="query-item main block ">
                    <div class="table-box">
                        <table style="text-align: center;">
                            <thead>
                            <tr>
                                <th width="100px" style="text-align: center">时间</th>
                                <th width="100px" style="text-align: center">店铺名称</th>
                                <th width="100px" style="text-align: center">收款账户</th>
                                <th width="100px" style="text-align: center">金额</th>
                                <th width="100px" style="text-align: center">手续费</th>
                                <th width="100px" style="text-align: center">汇率</th>
                                <th width="100px" style="text-align: center">实际到账金额</th>
                                <th width="100px" style="text-align: center">完成时间</th>
                                <th width="100px" style="text-align: center">状态</th>
                            </tr>
                            </thead>
                            <tbody id="withBody">
                            </tbody>
                        </table>
                    </div>
                    <div class="clearfix">
                        <ul class="pagination" id="pagination1"></ul>
                    </div>
                </div>
            </div>
            <div class="query query-bottom none" id="payId">
                <div class="query-item main block ">
                    <div class="table-box">
                        <table style="text-align: center;">
                            <thead>
                            <tr>
                                <th style="text-align: center">时间</th>
                                <th style="text-align: center">收款账号</th>
                                <th style="text-align: center">入账金额</th>
                                <th style="text-align: center">出账金额</th>
                                <th style="text-align: center">备注</th>
                            </tr>
                            </thead>
                            <tbody id="payBody">
                            </tbody>
                        </table>
                    </div>
                    <div class="clearfix">
                        <ul class="pagination" id="pagination2"></ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/view/common/footer.jsp" %>
<script src="${ctx}/business/account/js/tradeQuery.js" type="application/javascript"></script>
</body>
</html>
<script type="application/javascript">

    $(function () {
        $('#detailQuery').addClass('active');
        $('#index').removeClass('active');
        $('#bankCard').removeClass('active');
        $('#account').removeClass('active');
    })


    //改变字条颜色 提现
    function changeStyle() {
        $('#withdrawalId').removeClass('active1').addClass('active');
        $('#accDetailId').removeClass('active').addClass('active1');
        $('#payId').hide();
        $('#withId').show();
        $('#paySelect').hide();
        $('#withSelect').show();
        $('#queryBtnPay').hide();
        $('#queryBtn').show();
        $('#expWith').show();
        $('#expPay').hide();
    }

    //改变字条颜色 账户
    function changeStyle1() {
        $('#accDetailId').removeClass('active1').addClass('active');
        $('#withdrawalId').removeClass('active').addClass('active1');
        $('#payId').show();
        $('#withId').hide();
        $('#paySelect').show();
        $('#withSelect').hide();
        $('#queryBtnPay').show();
        $('#queryBtn').hide();
        $('#expWith').hide();
        $('#expPay').show();
        paymentDetailPage();
    }

    //显示日期搜索
    function dateView() {
        if ($("#dateChose").is(":hidden")) {
            $('#dateChose').show();
            return;
        }
        if ($("#dateChose").is(":visible")) {
            $('#dateChose').hide();
            return;
        }
    }

    //flag:  0:今天  1:1个月 3:3个月 6:6个月 7:1年
    function choseDate(obj, flag) {
        var beginStr = " 00:00:00";
        var endStr = " 23:59:59";
        var beginTime;
        var endTime;
        beginTime = getNowFormatDate(flag) + beginStr;
        endTime = getNowFormatDate(0) + endStr;
        $("#beginTime").val(beginTime);
        $("#endTime").val(endTime);
    }


    //获取日期  flag:  0:今天  1:1个月 3:3个月 6:6个月 7:1年
    function getNowFormatDate(flag) {
        var date = new Date();
        var seperator = "-";
        if (flag == 1) {
            date.setMonth(date.getMonth() - 1);
            $('#oneMonth1').addClass("active").siblings().removeClass("active");
        } else if (flag == 3) {
            date.setMonth(date.getMonth() - 3);
            $('#threeMonth3').addClass("active").siblings().removeClass("active");
        } else if (flag == 6) {
            date.setMonth(date.getMonth() - 6);
            $('#sixMonth6').addClass("active").siblings().removeClass("active");
        } else if (flag == 7) {
            date.setMonth(date.getMonth() - 12);
            $('#oneYear7').addClass("active").siblings().removeClass("active");
        } else if (flag == 8) {
            $('#today8').addClass("active").siblings().removeClass("active");
        }
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentDate = date.getFullYear() + seperator + month + seperator + strDate;
        return currentDate;
    }


    //下拉框连动
    function getNextClassify() {
        //获取下拉框主键
        var accountType = $('#accountType').find('option:selected').val();
        //清空二级目录
        $('#myAccount').empty();
        var classNext = $('#myAccount');
        $.ajax({
            type: "post",
            dataType: "JSON",
            data: {
                ccy: accountType
            },
            url: "${ctx}/trade/queryBankAccNoByCcy",
            success: function (data) {
                if (data.data.length > 0) {
                    for (var i = 0; i < data.data.length; i++) {
                        classNext.append("<option value=" + data.data[i].userAccNo + ">" + data.data[i].userAccNo
                            + " - " + data.data[i].storeName + "</option>");
                    }
                } else {
                    classNext.append("<option value='' selected>全部账号</option>");
                }
            }
        });
    }

    //交易查询分页
    function paymentDetailPage() {

        var jpage = {
            total: 0,
            init: function () {
                jpage.pageInit(1);
                jpage.pageChange(1)
            },
            pageInit: function (total, pages) {
                $.jqPaginator('#pagination2', {
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
                var totalCounts = 0;
                var accountType = $('#accountType').val();
                var account = $('#myAccount').val();
                var beginTime = $('#beginTime').val();
                var endTime = $('#endTime').val();
                var flag = $('#flag').val();
                $.ajax({
                    url: ctx + "/trade/tradeListQuery",
                    type: "post",
                    data: {
                        currPageNum: num,
                        pageSize: 5,
                        accountType: accountType,
                        accountNo: account,
                        beginTime: beginTime,
                        endTime: endTime,
                        flag: flag
                    },
                    dataType: "json",
                    success: function (data) {
                        $('#payBody').empty();
                        if (data.pageData.total > 0) {
                            jpage.total = parseInt(data.pageData.total);
                            jpage.pages = parseInt(data.pageData.pages);
                            $.each(data.pageData.resultList, function (key, val) {
                                var row = "<tr>\n" +
                                    "<td>" + val.tradeTime + "</td>\n" +
                                    "<td>" + val.accountNumber + "</td>\n" +
                                    "<td>" + val.tradeInAccMoney + "</td>\n" +
                                    "<td>" + val.tradeOutAccMoney + "</td>\n" +
                                    "<td>" + val.remark + "</td>\n" +
                                    "</tr>";
                                $('#payBody').append(row);
                            });
                            $('#pagination2').jqPaginator('option', {
                                totalCounts: jpage.total,
                                pages: jpage.pages
                            });
                        }
                    }
                });
            }
        };
        jpage.init();
    }

    /**
     * 导出EXCEL  1:交易 2：提现
     *
     * @param type
     */
    function exportExcel(type) {
        $('#exportExcel').attr("action", "${ctx}/trade/exportExcel");
        if (type == 1) {
            $('#type').val(type);
        } else if (type == 2) {
            $('#type').val(type);
        }
        $('#exportExcel').submit();
    }

    //重置按钮
    function resetData() {
        $("#myAccount").empty();
        $("<option value=''>全部账号</option>").appendTo($("#myAccount"));
        $("#accountType").val('');
        //提现状态
        $("#state").val("4");
        //出入账查询标志
        $("#flag").val("5");
        choseDate($('#oneMonth'), 1)
    }

</script>