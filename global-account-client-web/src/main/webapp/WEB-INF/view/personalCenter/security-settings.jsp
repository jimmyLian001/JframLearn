<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge，chrome=1">
    <meta name="renderer" content="webkit">
    <title>安全设置</title>
    <link rel="stylesheet" href="${ctx}/resource/css/personal-center.css?version=${version}">

</head>
<body>
<%@ include file="/WEB-INF/view/common/header.jsp" %>
<div class="people-center">
    <div class="center-content w1200 clearfix">
        <%@ include file="/WEB-INF/view/common/personalLeft.jsp" %>
        <div class="center-right fl">
            <div class="c-r-title">安全设置</div>
            <div class="security-content">
                <div class="security-top">
                    <div class="account">
                        <div class="account-left fl">登录账户</div>
                        <span>${securityConfigVo.loginNo}</span>
                        <c:choose>
                            <c:when test="${securityConfigVo.authStatus=='2'}">
                                <span class="pass same-icon">
                                    <i class="pass-icon"></i>
                                    已通过实名认证
                                </span>
                            </c:when>
                            <c:when test="${securityConfigVo.authStatus=='3'}">
                                <span class="not-pass same-icon">
                                    <i></i>
                                    实名认证未通过
                                </span>
                            </c:when>
                            <c:when test="${securityConfigVo.authStatus=='1'}">
                                <span class="audit same-icon">
                                    <i></i>
                                    实名认证审核中
                                </span>
                            </c:when>
                            <c:when test="${securityConfigVo.authStatus=='0'}">
                                <span class="audit same-icon">
                                    <i></i>
                                    未进行实名认证
                                </span>
                            </c:when>
                        </c:choose>
                    </div>
                    <div class="account">
                        <div class="account-left fl">注册ID</div>
                        <span>${securityConfigVo.userNo}</span>
                    </div>
                </div>
                <div class="security-bottom">
                    <div class="about-password clearfix">
                        <div class="pwd-left fl">
                            <div class="p-l-item fl">登录密码</div>
                            <span>建议您定期更换密码，保证您的账户安全</span>
                        </div>
                        <div class="pwd-right fr">
                            <c:if test="${securityConfigVo.loginPwdStatus=='1'}">
                                <span class="set">已设置</span>
                                <span class="line"></span>
                            </c:if>
                            <c:if test="${securityConfigVo.loginPwdStatus=='0'}">
                                <span class="set">未设置</span>
                                <span class="line"></span>
                            </c:if>
                            <a href="${ctx}/person/center/modifyPassword/first.do"
                               class="modification main-color">修改</a>
                        </div>
                    </div>
                    <div class="about-password clearfix">
                        <div class="pwd-left fl">
                            <div class="p-l-item fl">支付密码</div>
                            <span>建议您定期更换密码，保证您的账户安全</span>
                        </div>
                        <div class="pwd-right fr">
                            <c:if test="${securityConfigVo.payPwdStatus=='1'}">
                                <span class="set">已设置</span>
                                <span class="line"></span>
                            </c:if>
                            <c:if test="${securityConfigVo.payPwdStatus=='0'}">
                                <span class="set">
                                <a href="#" onclick="topaymentPwd();" class="main-color">未设置</a>
                                </span>
                                <span class="line"></span>
                            </c:if>
                            <a href="javascript:void(0);" onclick="updateInfo()"
                               class="modification main-color">修改</a>
                        </div>
                    </div>
                    <div class="about-password clearfix">
                        <div class="pwd-left fl">
                            <div class="p-l-item fl">手机绑定</div>
                            <span>您已绑定手机${securityConfigVo.mobileNo}，可用于找回密码</span>
                        </div>
                        <div class="pwd-right fr">
                            <c:if test="${securityConfigVo.mobileBandStatus=='1'}">
                                <span class="set">已设置</span>
                                <span class="line"></span>
                            </c:if>
                            <c:if test="${securityConfigVo.mobileBandStatus=='0'}">
                                <span class="set">
                                <a href="${ctx}/userTel/bindPhoneNoToApply.do" class="main-color">未设置</a>
                                </span>
                                <span class="line"></span>
                            </c:if>
                            <a href="javascript:void(0);" onclick="updatePhone()"
                               class="modification main-color">修改</a>
                        </div>
                    </div>
                    <div class="about-password clearfix">
                        <div class="pwd-left fl">
                            <div class="p-l-item fl">密保问题</div>
                            <span>密码保护问题可以帮助您找回密码</span>
                        </div>
                        <div class="pwd-right fr">
                            <c:if test="${securityConfigVo.questionsStatus=='1'}">
                                <span class="set">已设置</span>
                                <span class="line"></span>
                            </c:if>
                            <c:if test="${securityConfigVo.questionsStatus=='0'}">
                                <span class="set">未设置</span>
                                <span class="line"></span>
                            </c:if>
                            <a href="${ctx}/person/center/modifyQuestion/index.do"
                               class="modification main-color">修改</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/view/common/footer.jsp" %>
</body>
</html>
<script type="text/javascript">


</script>
