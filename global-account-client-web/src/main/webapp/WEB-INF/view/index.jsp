<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <title>跨境官网</title>
    <link rel="stylesheet" href="resource/css/base.css?version=${version}">
    <link rel="stylesheet" href="resource/css/main.css?version=${version}">
    <link href="resource//images/icon.ico" rel="shortcut icon">
    <link rel="stylesheet" href="resource/css/jquery.fullPage.css?version=${version}">
    <!-- jQuery及其扩展插件 -->
    <script src="${ctx}/resource/js/common/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="resource/js/common/plugins/jquery.fullPage.min.js?version=${version}"></script>
    <script type="text/javascript">
        $(function () {
            $('#fullpage').fullpage();
        });
    </script>
</head>
<body>
<div id="fullpage">
    <div class="section">
        <div class="header mw1200">
            <div class="w1200 clearfix ">
                <div class="header-left fl">
                    <a href="${ctx}/index">
                        <img src="${ctx}/resource/images/index-logo.png" alt="logo">
                    </a>
                </div>
                <div class="header-right fr">
                    <ul class="fl">
                        <li>
                            <a href="http://www.baofu.com" target="_blank">了解宝付</a>
                        </li>
                        <li>
                            <a href="${ctx}/login/index.do">登录</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="cross-border-banner">
            <div class="w1200 banner-content w1080">
                <div class="banner-top">
                    <img src="resource/images/txt1.png" alt="pic">
                    <div class="register-box clearfix">
                        <a class="register user-btn fl" href="${ctx}/register/registerPersonalPage.do/1">个人注册</a>
                        <a class="register fl" href="${ctx}/register/registerOrgPage.do/2">企业注册</a>
                    </div>
                </div>
                <div class="banner-bottom">
                    <ul class="clearfix">
                        <li>
                            <i></i>
                            <h4>&nbsp;经济+</h4>
                            <p>收款费率1%封顶<br>大客户专属优惠</p>
                        </li>
                        <li>
                            <i class="convenient"></i>
                            <h4>&nbsp;便捷+</h4>
                            <p>多平台统一管理<br>一键提现 直达国内银行账户</p>
                        </li>
                        <li>
                            <i class="safe"></i>
                            <h4>&nbsp;安全+</h4>
                            <p>央行外管双监管<br>资金安全 放心无忧</p>
                        </li>
                        <li>
                            <i class="intimate"></i>
                            <h4>&nbsp;贴心+</h4>
                            <p>客户经理一对一服务<br>7*24小时客服极速响应</p>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="section">
        <div class="cross-border-content">
            <div class="cross-top">
                <h2 class="bold">专注于中国电商的跨境收款产品</h2>
                <p>通过为商户开立境外本地银行账户实现商户资金本地落款并进行跨境清算， <br> 提供7*24小时资金提现，最快T+0到账，加速回笼资金，为商户提供“灵活自助”的特色服务</p>
            </div>
            <div class="cross-middle">
                <div class="w1200">
                    <div class="receivables clearfix">
                        <div class="receivables-left fl">
                            <h2 class="h2 bold">您身边的收款专家</h2>
                            <p>只需 <span class="bold">3</span> 步，即可开始安全、经济的全球收款。 清晰友好、简单易用的操作平台让您更高效地 管理和提现。 </p>
                        </div>
                        <div class="receivables-right fr">
                            <img src="${ctx}/resource/images/computer.png" alt="pic">
                        </div>
                    </div>
                    <div class="flow">
                        <ul class="clearfix">
                            <li class="first-flow">
                                <div class="flow-left fl">
                                    <img src="${ctx}/resource/images/pic1.png" alt="pic">
                                </div>
                                <div class="flow-right fl">
                                    <h5>注册</h5>
                                    <p>注册宝付国际账号</p>
                                </div>
                            </li>
                            <li class="second-flow">
                                <div class="flow-left fl">
                                    <img src="${ctx}/resource/images/pic2.png" alt="pic">
                                </div>
                                <div class="flow-right fl">
                                    <h5>收款</h5>
                                    <p>接受全球收款</p>
                                    <span>支持 AMAZON 北美/日本/欧洲平台</span>
                                </div>
                            </li>
                            <li class="last-flow">
                                <div class="flow-left fl">
                                    <img src="${ctx}/resource/images/pic3.png" alt="pic">
                                </div>
                                <div class="flow-right fl">
                                    <h5>提现</h5>
                                    <p>7*24小时提现，实时到账国内银行账户</p>
                                    <span>支持国内主流银行</span>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="section">
        <div class="cross-bottom">
            <div class="w1200">
                <div class="company-info clearfix">
                    <div class="company-left fl">
                        <h2 class="h2 bold">宝安全 <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;付简单</h2>
                        <ul>
                            <li>上海跨境电子商务行业协会会员</li>
                            <li>跨境电子商务外汇支付业务试点企业</li>
                            <li>跨境电商20强企业</li>
                            <li>2017凤鸣奖优秀跨境支付服务商</li>
                        </ul>
                    </div>
                    <div class="company-right fr">
                        <img src="${ctx}/resource/images/pic4.png" alt="pic">
                    </div>
                </div>
            </div>
        </div>
        <div class="index-footer">
            <div class="w1200">
                <div class="footer-top clearfix">
                    <div class="footer-logo fl">
                        <img src="${ctx}/resource/images/index-logo.png" alt="pic">
                    </div>
                    <div class="tel-box fr">
                        <div class="tel-icon fl">
                            <img src="${ctx}/resource/images/tel.png" alt="tel-pic">
                        </div>
                        <p class="num">021-58395261</p>
                        <p>服务时间：7X24H</p>
                    </div>
                </div>
                <div class="footer-middle">
                    <ul>
                        <li>
                            <a href="http://www.baofu.com/static/about/about.html" target="_blank">关于我们</a>
                        </li>
                        <li>
                            <a href="${ctx}/common/agreement" target="_blank">服务协议</a>
                        </li>
                        <li>
                            <a href="http://www.baofu.com/static/about/lx.html" target="_blank">联系我们</a>
                        </li>
                    </ul>
                </div>
                <div class="footer-bottom">
                    <p>宝付国际跨境收款&nbsp; COPYRIGHT &copy; 2008-2017 宝付网络科技（上海）有限公司 &nbsp;版权所有 &nbsp; 沪ICP备09005103号-10</p>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="contact-us">
    <ul>
        <li class="customer-service">
            <div class="customer-pic same-service"></div>
            <img class="code-pic none" src="${ctx}/resource/images/code.png" alt="code">
        </li>
        <li class="we-chat">
            <div class="chat-pic same-service"></div>
            <img class="num-pic none" src="${ctx}/resource/images/tel-num.png" alt="num">
        </li>
    </ul>
</div>
</body>
</html>