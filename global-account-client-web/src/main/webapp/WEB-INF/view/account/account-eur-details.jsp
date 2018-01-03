<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge，chrome=1">
    <meta name="renderer" content="webkit">
    <title>我的账户</title>
    <script>

        //输入失败
        var div = "<div class='input-tip' style='margin-top:22px'><em class='fl'></em><span></span></div>";
        $(function () {
            // 账户详情
            var modifyStoreName = $('.modify-store-name'),
                storeInput = $('.store-input'),
                storeName = $('.store-name'),
                storeNameH2 = $('.store-name>h2'),
                newInputVal,
                newStoreNameVal;
            $('.modify-store-btn').click(function () {
                var storeNameVal = $('.store-name>h2').text();
                storeName.hide();
                modifyStoreName.show();
                storeInput.val(storeNameVal);
                newStoreNameVal = storeNameVal
            });
            storeInput.blur(function () {
                var inputVal = $(this).val();
                newInputVal = inputVal
                return newInputVal;
            });
            $('.confirm-btn').click(function () {
                var val = newInputVal;
                if (val.trim() == '') {
                    $("#btnBox").next().remove();
                    $("#btnBox").after(div);
                    $("#btnBox").parent().find('div').find('span').text('修改店铺名不能为空');
                    return false;
                }
                if (val.trim().length > 128) {
                    $("#btnBox").next().remove();
                    $("#btnBox").after(div);
                    $("#btnBox").parent().find('div').find('span').text('店铺名不能超过128个字符');
                    return false;
                }

                sendRequest(
                    ctx + "/account/modifyStoreName",
                    {storeNo: $("#storeNo").val(), storeName: newInputVal},
                    function (data) {
                        if (data.code == 1) {
                            errorMsgShow(data.message);
                        } else {
                            modifyStoreName.hide();
                            storeName.show();
                            storeNameH2.text(newInputVal);
                        }
                    }
                );
                $(".btn-box").next().remove();

            });

            $('.cancel-btn').click(function () {
                modifyStoreName.hide();
                storeName.show();
                storeInput.val(newStoreNameVal);
            });
        })
    </script>
</head>
<body>
<div class="login-layout">
    <%@ include file="/WEB-INF/view/common/header.jsp" %>
    <div class="same-layout details-layout">
        <input type="hidden" id="storeNo" value="${accDetail.storeNo}"/>
        <div class="same-title ">
            <div class="title-left fl">
                <i class="fl account-info-pic"></i>
                <h2>账户信息</h2>
            </div>
        </div>
        <div class="same-content details-content">
            <div>
                <div class="total-account">
                    <ul class="clearfix">
                        <li>
                            <p>账户总余额</p>
                            <p class="bold">€ ${accDetail.accountBal}</p>
                        </li>
                        <li>
                            <p>可提现金额</p>
                            <p class="bold">€ ${accDetail.avaliableAmt}</p>
                        </li>
                        <li class="last-li">
                            <p>提现中的金额</p>
                            <p class="bold"> €${accDetail.withdrawProcessAmt}</p>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="clearfix">
                <div class="account-title logo-title clearfix">
                    <i class="fl account-details-pic"></i>
                    <span>宝付国际收款账户信息</span>
                    <s>*讲此项信息填入亚马逊后台</s>
                </div>
                <div class="collection-account-info fl">
                    <ul class="clearfix">
                        <li class="clearfix">
                            <div class="list-left fl">
                                <p>BIC: </p>
                                <span></span>
                            </div>
                            <div class="list-right"><c:out value="${accDetail.rountingNo}"/></div>
                        </li>
                        <li class="clearfix">
                            <div class="list-left fl">
                                <p>IBAN: </p>
                                <span></span>
                            </div>
                            <div class="list-right"><c:out value="${accDetail.bankAccNo}"/></div>
                        </li>
                        <li class="clearfix">
                            <div class="list-left fl">
                                <p>账户持有人姓名: </p>
                                <span>Account Holder Name</span>
                            </div>
                            <div class="list-right"><c:out value="${accDetail.bankAccName}"/></div>
                        </li>
                    </ul>
                </div>
                <div class="how-to-bind fr">
                    <p class="bold bind-question">如何在亚马逊平台绑定账号？</p>
                    <p>您需要在Amazon卖家中心后台完成账号定，即可开始收款，请注意绑定收款账号的相关事项</p>
                    <a href="${ctx}/common/bindStoreExample/eur" target="_blank">点击查看账号绑定指引 > </a>
                </div>
            </div>
        </div>
        <div class="same-title">
            <i class="fl account-details-pic"></i>
            <div class="fl store-name">
                <h2 class="fl"><c:out value="${accDetail.storeName}"/></h2>
                <a class="modify-store-btn fl" href="javascript:;">点击修改名称</a>
            </div>
            <div class="fl modify-store-name input-box none">
                <input class="fl store-input" type="text">
                <div class="btn-box fl">
                    <button class="confirm-btn">确认</button>
                    <button class="cancel-btn">取消</button>
                </div>
            </div>
        </div>
        <div class="same-content details-content">
            <div class="account-title shop-title clearfix">
                <img src="${ctx}/resource/images/amazon2.png" alt="amazon">
                <span>&欧洲站</span>
                <span> &nbsp;&nbsp;&nbsp;&nbsp; 店铺信息</span>
            </div>
            <div class="shop-info collection-account-info">
                <ul>
                    <li class="clearfix">
                        <div class="list-left fl">
                            <p>卖家编号: </p>
                            <span>Seller ID</span>
                        </div>
                        <div class="list-right fl"><c:out value="${accDetail.sellerId}"/></div>
                    </li>
                    <li class="clearfix">
                        <div class="list-left fl">
                            <p>访问键编码: </p>
                            <span>ANS Access Key</span>
                        </div>
                        <div class="list-right fl"><c:out value="${accDetail.awsAccessKey}"/></div>
                    </li>
                    <li class="clearfix">
                        <div class="list-left fl">
                            <p>密钥: </p>
                            <span>Secret Key</span>
                        </div>
                        <div class="list-right fl"><c:out value="${accDetail.secretKey}"/></div>
                    </li>
                </ul>
                <c:if test="${accDetail.storeExist == 'N'}">
                    <div class="supplement-info">
                        <span>新店铺，补充店铺资料可启用提现功能</span>
                        <a href="${ctx}/account/${accDetail.storeNo}/modifyStorePage" class="supplement-btn">补充店铺信息</a>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
</body>
</html>