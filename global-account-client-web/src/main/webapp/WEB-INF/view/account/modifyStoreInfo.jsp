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
        $(function () {
            //Ajaxform 提交方式
            ajaxFormSubmit(function (data) {
                window.location.href = ctx + "/account/modifyStoreSuccess";
            });
        });
    </script>
</head>
<body>
<div class="login-layout">
    <%@ include file="/WEB-INF/view/common/header.jsp" %>
    <form action="${ctx}/account/modifyStore" method="post" class="validate" id="ajaxForm"
          onsubmit="return $(this).valid()">
        <input type="hidden" name="storeExist" id="storeExist" value="Y"/>
        <input type="hidden" name="storeNo" value="${accDetail.storeNo}"/>
        <div class="authentication-layout register-content">
            <div class="authentication-title">店铺信息完善</div>
            <div class="authentication-container">
                <div class="questions-info">
                    <div class="forget-item clearfix">
                        <div class="item-left aut-left fl">开户主体</div>
                        <div class="item-right fl">
                            <c:forEach items="${accQualifiedList}" var="accQualified" varStatus="index">
                                <div class="householder-information  active fl">
                                    <p>姓名：<c:out value="${accQualified.name}"/></p>
                                    <p>英文名称：<c:out value="${accQualified.enName}"/></p>
                                    <p>证件号码：<c:out value="${accQualified.legalNo}"/></p>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="forget-item site-item clearfix">
                        <div class="item-left aut-left fl  select-site">选择站点</div>
                        <div class="item-right site-right fl">
                            <ul class="clearfix shop-ul site-ul">
                                <c:if test="${accDetail.ccy=='USD'}">
                                    <li>
                                        <a class="active" href="javascript:void(0)" onclick="siteSelect('US');">
                                            <input type="hidden" name="siteId" value="US"/>
                                            <p>美国站</p>
                                            <img src="${ctx}/resource/images/amazon.png" alt="amazon">
                                        </a>
                                    </li>
                                </c:if>
                                <c:if test="${accDetail.ccy=='JPY'}">
                                    <li>
                                        <a href="javascript:siteSelect('JP');" class="active">
                                            <input type="hidden" name="siteId" value="JP"/>
                                            <p>日本站</p>
                                            <img src="${ctx}/resource/images/amazon.png" alt="amazon">
                                        </a>
                                    </li>
                                </c:if>
                                <c:if test="${accDetail.ccy=='EUR'}">
                                    <li>
                                        <a href="javascript:siteSelect('EU');" class="active">
                                            <input type="hidden" name="siteId" value="EU"/>
                                            <p>欧洲站</p>
                                            <img src="${ctx}/resource/images/amazon.png" alt="amazon">
                                        </a>
                                    </li>
                                </c:if>
                                <c:if test="${accDetail.ccy=='GBP'}">
                                    <li>
                                        <a href="javascript:siteSelect('UK');" class="active">
                                            <input type="hidden" name="siteId" value="UK"/>
                                            <p>英国站</p>
                                            <img src="${ctx}/resource/images/amazon.png" alt="amazon">
                                        </a>
                                    </li>
                                </c:if>
                            </ul>
                        </div>
                    </div>
                    <div class="forget-item clearfix">
                        <div class="item-left aut-left fl">店铺情况</div>
                        <div class="item-right bank-btn account-btn shop-btn fl">
                            <button type="button" class="active">已有店铺</button>
                        </div>
                    </div>
                    <div class="shop-content">
                        <div class="main block">
                            <div class="forget-item clearfix">
                                <div class="item-left aut-left fl">店铺名称</div>
                                <div class="input-box fl">
                                    <input type="text" name="storeName" class="required" value="${accDetail.storeName}"
                                           maxlength="64"
                                           placeholder="请输入店铺名称">
                                    <div class="input-tip none">
                                        <em class="fl"></em>
                                        <span>以上内容不能为空/输入格式不正确</span>
                                    </div>
                                </div>
                            </div>
                            <div class="forget-item clearfix">
                                <div class="item-left aut-left fl">经营范围</div>
                                <div class="input-box fl">
                                    <mvc:select path="accDetail.managementCategory" items="${categoryList}"
                                                cssClass="required select-box" itemValue="categoryId"
                                                itemLabel="categoryName"/>
                                    <div class="input-tip none">
                                        <em class="fl"></em>
                                        <span>以上内容不能为空/输入格式不正确</span>
                                    </div>
                                </div>
                            </div>
                            <div class="main block" id="sellerInfo">
                                <div class="forget-item clearfix">
                                    <div class="item-left aut-left fl">卖家编号</div>
                                    <div class="input-box fl">
                                        <input type="text" name="sellerId" class="required" maxlength="64"
                                               placeholder="请输入卖家编号">
                                        <div class="input-tip none">
                                            <em class="fl"></em>
                                            <span>以上内容不能为空/输入格式不正确</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="forget-item clearfix">
                                    <div class="item-left aut-left fl">访问键编码</div>
                                    <div class="input-box fl">
                                        <input type="text" name="accessKey" class="required" maxlength="128"
                                               placeholder="请输入访问键编码">
                                        <div class="input-tip none">
                                            <em class="fl"></em>
                                            <span>以上内容不能为空/输入格式不正确</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="forget-item clearfix">
                                    <div class="item-left aut-left fl">密钥</div>
                                    <div class="input-box fl">
                                        <input type="text" name="secretKey" class="required" maxlength="128"
                                               placeholder="请输入密钥">
                                        <div class="input-tip none">
                                            <em class="fl"></em>
                                            <span>以上内容不能为空/输入格式不正确</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="btn-box">
                                <button type="submit" id="submitApplyAccount">提交</button>
                                <button type="button" class="cancel-btn" onclick="window.history.go(-1)">返回</button>
                            </div>
                        </div>
                    </div>
                    <div class="binding-instructions">
                        <p>为什么进行店铺绑定？</p>
                        <p>1、绑定店铺是为了满足中国外汇监管的合规要求。</p>
                        <p>2、您绑定店铺所提供的所有商业信息，宝付承诺会予以严格保密，不会将资料提供给与您收款服务无关的任何单位或个人，宝付亦不会将商业信息用于收款外的任何其他商业用途。</p>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
</body>
</html>