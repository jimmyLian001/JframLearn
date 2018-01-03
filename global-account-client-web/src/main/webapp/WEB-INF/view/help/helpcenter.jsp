<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<c:set var="LoginJSVersion" value="1.0.1"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge，chrome=1">
    <meta name="renderer" content="webkit">
    <title>帮助中心</title>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
</head>
<body>
<div class="login-layout">
    <%@ include file="/WEB-INF/view/common/contact.jsp" %>
    <div class="same-content agreement-layout">
        <div class="agreement-wrap w1200">
            <div class="agreement-content">
                <h2>帮助中心</h2>
                <div class="agreement-item">
                    <h3>企业用户与个人用户有什么区别？</h3>
                    <p>如您是个人用户，只能提现至本人银行借记卡。</p>
                    <p>如您是企业用户，可提现至企业对公账户或法人账户。同时还可以申请子账户进行多主体管理。</p>
                </div>
                <div class="agreement-item">
                    <h3>个人用户申请收款账户，需要准备哪些材料？</h3>
                    <p>1.手机号码</p>
                    <p>2.身份证正反面彩色影印件</p>
                    <p>3.本人银行借记卡</p>
                </div>
                <div class="agreement-item">
                    <h3>企业用户申请收款账户，请准备以下材料？</h3>
                    <p>1.电子邮箱、手机号码</p>
                    <p>2.营业执照（三证合一企业或个体工商户）；营业执照、组织机构代码证、税务登记证（非三证合一企业）。</p>
                    <p>3.企业对公银行账户或法人个人账户</p>
                </div>
                <div class="agreement-item">
                    <h3>为什么收款账户开通失败了？怎样重新申请？</h3>
                    <p>常见的实名认证失败原因有：</p>
                    <p>1.企业名称填写错误</p>
                    <p>2.个人身份证件信息有误：身份证有效期填写错误或误勾长期、个人身份在正反面彩色影印件不清晰、填写的身份证有效期已到期、上传的身份证正反面信息有误。</p>
                    <p>3.企业证件信息有误：提供的组织机构代码证/税务登记证/营业执照。</p>
                    <p>4.绑定的店铺信息有误</p>
                    <p>开户失败后您可以重新填写相关信息，您可以登录账户首页，在我的店铺列表中点击修改，重新修改资料，提交申请</p>
                </div>
                <div class="agreement-item">
                    <h3>收款账户开通需要多久？</h3>
                    <p>开通收款账户结果会在2个工作日以邮件和短信形式通知您开户结果。</p>
                </div>
                <div class="agreement-item">
                    <h3>为什么显示“补充店铺信息”？我如果不写店铺链接会影响账户正常使用吗？</h3>
                    <p>
                        显示“补充店铺信息”是由于您的店铺买家编号、访问键编码、秘钥还未填写。填写店铺信息是为了核实您的贸易真实性。账户可进行正常收款，但是不能提现，为了使您能够正常提现，请您及时填写店铺链接信息。</p>
                </div>
                <div class="agreement-item">
                    <h3>我在亚马逊上新开店，没有店铺信息怎么办？</h3>
                    <p>若您申请境外收款账户时尚未开通亚马逊店铺，可在申请境外收款账号时点击“暂无店铺”提前拿到收款账号，待店铺开始正常营运时再补充店铺链接。</p>
                </div>
                <div class="agreement-item">
                    <h3>使用宝付提现的汇率如何计算？提现会有汇损吗？</h3>
                    <p>您好，宝付给您的结算汇率为中国银行上午十点更新的牌价。</p>
                </div>
                <div class="agreement-item">
                    <h3>使用宝付提现会占用我的外管额度吗？</h3>
                    <p>不会占用外汇管理局的年度结汇额度，您的外币账户是在国外当地的银行账户，只要您的资金来源于拥有真实贸易背景，通过宝付支付进行的结汇不计入您每年5万美金的结汇额度。</p>
                </div>
                <div class="agreement-item">
                    <h3>我发起提现之后，多久能够到账？</h3>
                    <p>您在工作日下午4点前发起的提现预计T+0个工作日到账，下午4点后发起的提现预计T+1个工作日到账，节假日及周末顺延。</p>
                </div>
                <div class="agreement-item">
                    <h3>我能够支持提现到哪些银行？</h3>
                    <p>宝付支持所有主流银行及小型农村信用合作社(境内用户），为您提供最便捷的服务。</p>
                </div>
                <div class="agreement-item">
                    <h3>我发起提现之后为何会失败？</h3>
                    <p>您好，请确认您的银行卡信息是否与实名认证信息一致。如您无法确认提现失败的原因，还请联系客服或您的客户经理核实原因。</p>
                </div>
                <div class="agreement-item">
                    <h3>我如何联系宝付官网的客服？</h3>
                    <p>如您有其他任何疑问，您可以通过以下方式联系我们：</p>
                    <p>拨打统一服务热线：021-58395261</p>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>