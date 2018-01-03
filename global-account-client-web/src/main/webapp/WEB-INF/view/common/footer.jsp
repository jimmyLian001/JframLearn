<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="footer w1080">
    <div class="w1200">
        <div class="footer-about-us">
            <ul class="clearfix">
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
        <div class="footer-copyright">
            <p>宝付国际跨境收款&nbsp; COPYRIGHT &copy; 2008-2017 宝付网络科技（上海）有限公司 &nbsp; 版权所有 &nbsp;&nbsp; 沪ICP备09005103号-10</p>
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

<!--顶部提示消息-->
<div class="top-tip" id="topTipErrMsg">
    <img src="${ctx}/resource/images/warning-icon.png" alt="pic">
    <span id="errorMsg"></span>
    <i class="close-btn fr"></i>
</div>

<!-- 模态框 -->
<div class="modal-box none" id="modal-box">
    <div class="popup-box">
        <div class="popup-content">
            <p id="modal-box-title">您还未通过实名认证</p>
            <p id="modal-box-text">实名通过后才能申请境外账号</p>
            <img src="${ctx}/resource/images/modal-pic.png" alt="pic">
            <div class="btn-box login-btn">
                <button type="button" id="modal-box-button">去完成实名认证</button>
            </div>
        </div>
    </div>
</div>
