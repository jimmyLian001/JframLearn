<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>账户设置</title>
    <link href="${ctx}/resource/images/icon.ico" rel="shortcut icon">
    <script type="text/javascript">
        $(function () {
            $("#account").children("a").addClass("active");
        });
                </script>
                </head>
                <script type="text/javascript">
                    function updateInfo() {
                        window.location.href = "${ctx}/person/center/resetPaymentPwd/first.do";
                    }
                function updatePhone() {
                    window.location.href = "${ctx}/userTel/resetPhoneNo/apply.do";
                }
                </script>
                <body>
                <div class="login-layout">
                    <%@ include file="/WEB-INF/view/common/header.jsp" %>
                    <div class="same-layout">
                        <div class="same-title">
                            <div class="title-left setting-title fl">
                                <i class="fl"></i>
                                <h2>账户信息</h2>
                            </div>
                        </div>
                        <div class="same-middle  setting-middle">
                            <ul class="clearfix">
                                <li class="clearfix">
                                    <div class="user-info-left fl">用户</div>
                                    <div class="user-info-right">
                                        <span class="fl">${fn:substring(loginNo,0,3)}****${fn:substring(loginNo,fn:length(loginNo)-4,fn:length(loginNo))}</span>
                                        <c:if test="${securityConfigVo.authStatus==0}">
                                            <i class="no-real-name"></i>
                                        </c:if>
                                        <c:if test="${securityConfigVo.authStatus==1}">
                                            <i class="real-name"></i>
                                        </c:if>
                                        <c:if test="${securityConfigVo.authStatus==2}">
                                            <i class="already-real-name"></i>
                                        </c:if>
                                        <c:if test="${securityConfigVo.authStatus==3}">
                                            <i class="real-name-failure"></i>
                                        </c:if>
                                    </div>
                                </li>
                                <li class="clearfix">
                                    <div class="user-info-left fl">注册ID</div>
                                    <div class="user-info-right">
                                        <span>${userInfo.userNo}</span>
                                    </div>
                                </li>
                                <li class="clearfix">
                                    <div class="user-info-left fl">姓名</div>
                                    <div class="user-info-right">
                                        <span>${userInfo.name}</span>
                                    </div>
                                </li>
                                <li class="clearfix">
                                    <div class="user-info-left fl">证件号码</div>
                                    <div class="user-info-right">
                                        <span>
                                         <c:if test="${fn:length(userInfo.idNo)>10}">
                                                ${fn:substring(userInfo.idNo,0,6)}********${fn:substring(userInfo.idNo,fn:length(userInfo.idNo)-4,fn:length(userInfo.idNo))}
                                         </c:if>
                                        <c:if test="${fn:length(userInfo.idNo)<=10}">
                                            ${userInfo.idNo}
                                        </c:if>
                                        </span>
                                    </div>
                                </li>
                            </ul>
                            <c:if test="${securityConfigVo.authStatus==3}">
                                <div class="btn-box login-btn" style="">
                                    <a href="${ctx}/auth/personAuthEditPage.do?userInfoNo=${userInfo.userInfoNo}">补充实名信息</a>
                                </div>
                            </c:if>
                        </div>
                        <div class="same-content setting-content">
                            <div class="clearfix setting-type">
                <div class="security-setting-left fl">
                    <div class="setting-item fl">登录密码</div>
                    <div class="setting-suggest fl">建议您定期更换密码，保证您的账户安全</div>
                </div>
                <div class="security-setting-right fr">
                    <ul class="clearfix">
                        <c:if test="${securityConfigVo.loginPassStatus=='0'}">
                            <li class="main-color">
                                <a href="javascript:;">未设置</a>
                            </li>
                            <li class="line"></li>
                            <li> 修改</li>
                        </c:if>
                        <c:if test="${securityConfigVo.loginPassStatus=='1'}">
                            <li> 已设置</li>
                            <li class="line"></li>
                            <li class="main-color">
                                <a href="${ctx}/person/center/modifyPassword/first.do">修改</a>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </div>
            <div class="clearfix setting-type">
                <div class="security-setting-left fl">
                    <div class="setting-item fl">支付密码</div>
                    <div class="setting-suggest fl">建议您定期更换密码，保证您的账户安全</div>
                </div>
                <div class="security-setting-right fr">
                    <ul class="clearfix">
                        <c:if test="${securityConfigVo.payPassStatus=='0'}">
                            <li class="main-color">
                                <a href="${ctx}/person/center/toPaymentPwd.do">未设置</a>
                            </li>
                            <li class="line"></li>
                            <li>修改</li>
                        </c:if>
                        <c:if test="${securityConfigVo.payPassStatus=='1'}">
                            <li>已设置</li>
                            <li class="line"></li>
                            <li class="main-color">
                                <a href="javascript:void(0);" onclick="updateInfo()"
                                   class="modification main-color">修改</a>
                            </li>
                        </c:if>

                    </ul>
                </div>
            </div>
            <div class="clearfix setting-type">
                <div class="security-setting-left fl">
                    <div class="setting-item fl">绑定手机</div>
                    <div class="setting-suggest fl">找回密码以及其他验证操作也将通过短信发送到此手机</div>
                </div>
                <div class="security-setting-right fr">
                    <ul class="clearfix">
                        <c:if test="${securityConfigVo.mobileBandStatus=='0'}">
                            <li class="main-color">
                                <a href="${ctx}/userTel/bindPhoneNoToApply.do">未设置</a>
                            </li>
                            <li class="line"></li>
                            <li>修改</li>
                        </c:if>
                        <c:if test="${securityConfigVo.mobileBandStatus=='1'}">
                            <li>已设置</li>
                            <li class="line"></li>
                            <li class="main-color">
                                <a href="javascript:void(0);" onclick="updatePhone()"
                                   class="modification main-color">修改</a>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </div>
            <div class="clearfix setting-type">
                <div class="security-setting-left fl">
                    <div class="setting-item fl">安全问题</div>
                    <div class="setting-suggest fl">安全保护问题可以帮助你修改支付密码和修改绑定手机</div>
                </div>
                <div class="security-setting-right fr">
                    <ul class="clearfix">
                        <c:if test="${securityConfigVo.questionsStatus=='0'}">
                            <li class="main-color">
                                <a href="javascript:;">未设置</a>
                            </li>
                            <li class="line"></li>
                            <li>修改</li>
                        </c:if>
                        <c:if test="${securityConfigVo.questionsStatus=='1'}">
                            <li>已设置</li>
                            <li class="line"></li>
                            <li class="main-color">
                                <a href="${ctx}/person/center/modifyQuestion/index.do">修改</a>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
</body>
</html>