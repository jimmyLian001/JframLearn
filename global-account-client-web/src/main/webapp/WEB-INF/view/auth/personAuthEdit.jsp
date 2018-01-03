<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge，chrome=1">
    <title>实名认证</title>
    <link rel="stylesheet" href="${ctx}/resource/css/errorStyle.css?version=${version}">
    <link rel="stylesheet" href="${ctx}/resource/css/auth.css?version=${version}">
    <link href="${ctx}/resource/images/icon.ico" rel="shortcut icon">
    <script src="${ctx}/business/auth/authEdit.js?version=${version}"></script>
</head>
<body>

<form id="personAuthForm" method="post" action="${ctx}/auth/personAuthEdit.do" enctype="multipart/form-data">
    <input type="hidden" id="requestType" name="requestType" value="${requestType}"/>
    <input type="hidden" id="userInfoNo" name="userInfoNo" value="${userPersonalInfo.userInfoNo}"/>

    <div class="login-layout">
        <%@ include file="/WEB-INF/view/common/header.jsp" %>
        <div class="authentication-layout register-content">
            <div class="authentication-title">个人实名认证</div>
            <div class="failure-reason ">
                <span>审核失败原因：${userPersonalInfo.remarks}</span>
            </div>
            <div class="authentication-container">
                <div class="questions-info">
                    <div class="forget-item clearfix">
                        <div class="item-left aut-left fl">姓名</div>
                        <div class="input-box fl">
                            <input type="text" id="cardHolder" name="cardHolder" value="${userPersonalInfo.name}" readonly="readonly">
                        </div>
                    </div>
                    <div class="forget-item clearfix">
                        <div class="item-left aut-left fl">英文姓名</div>
                        <div class="input-box fl">
                            <input type="text" id="englishName" name="englishName" value="${userPersonalInfo.enName}" readonly="readonly">
                        </div>
                    </div>
                    <div class="forget-item clearfix">
                        <div class="item-left aut-left fl">银行卡号</div>
                        <div class="input-box fl">
                            <input type="text" id="cardNo" name="cardNo" value="${userBankCardInfo.cardNo}" readonly="readonly">
                        </div>
                    </div>
                    <div class="forget-item clearfix">
                        <div class="item-left aut-left fl">绑定邮箱</div>
                        <div class="input-box fl">
                            <input type="text" id="email" name="email" value="${userPersonalInfo.email}" placeholder="请输入邮箱">
                        </div>
                    </div>
                    <div class="id-card">
                        <div class="forget-item clearfix">
                            <div class="item-left aut-left fl">身份证照片</div>
                            <div class="item-right fl">
                                <span>请确保图片上的文字清晰可见，图片支持JPG、PNG、GIF，文件小于2MB</span>
                            </div>
                        </div>
                        <div class="upload-con clearfix">
                            <div class="upload-box fl">
                                <div class="view-picture none">
                                    <button>查看</button>
                                </div>
                                <div class="upload-again none">
                                    <button>修改</button>
                                </div>
                                <div class="upload-tip">
                                    <i class="bold">+</i>
                                    <p>身份证&nbsp;&nbsp; 照片页</p>
                                </div>
                                <input id="idFrontImage" name="idFrontImage" type="file" onchange="viewImage(this, 'idFront')">
                                <div id="image_idFront" class="preview">
                                    <img id="idFront" src="${idFrontImageUrl}" width="120" height="100"/>
                                </div>
                            </div>
                            <div class="picture-example fl ex1" data-imgUrl="${ctx}/resource/images/zm.jpg">
                                <div class="picture-example-tip">
                                    <i class="search"></i>
                                    <p>示例</p>
                                </div>
                            </div>
                            <div class="upload-box fl">
                                <div class="view-picture none">
                                    <button>查看</button>
                                </div>
                                <div class="upload-again none">
                                    <button>修改</button>
                                </div>
                                <div class="upload-tip">
                                    <i class="bold">+</i>
                                    <p>身份证&nbsp;&nbsp;国徽页</p>
                                </div>
                                <input id="idReverseImage" name="idReverseImage" type="file" onchange="viewImage(this, 'idReverse')">
                                <div id="image_idReverse" class="preview">
                                    <img id="idReverse" src="${idReverseImageUrl}" width="120" height="100"/>
                                </div>
                            </div>
                            <div class="picture-example fl ex2" data-imgUrl="${ctx}/resource/images/fm.jpg">
                                <div class="picture-example-tip">
                                    <i class="search"></i>
                                    <p>示例</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="forget-item clearfix">
                        <div class="item-left aut-left fl">身份证号码</div>
                        <div class="input-box fl">
                            <input type="text" id="idNo" name="idNo" value="${userPersonalInfo.idNo}" readonly="readonly">
                        </div>
                    </div>
                    <div class="forget-item clearfix">
                        <div class="item-left aut-left fl">邮编</div>
                        <div class="input-box fl">
                            <input type="text" id="postCode" name="postCode" value="${userPersonalInfo.postCode}" placeholder="请输入邮编">
                            <div class="input-tip" style="display:none;">
                                <em class="fl"></em>
                                <span>以上内容不能为空/输入格式不正确</span>
                            </div>
                        </div>
                    </div>
                    <div class="forget-item clearfix">
                        <div class="item-left aut-left fl">所在地址</div>
                        <div class="input-box fl">
                            <select id="province" name="province" class="select-box small-select" onchange="initCity(null)">
                                <option value="${userPersonalInfo.province}" class="select-default"></option>
                            </select>
                            <select id="city" name="city" class="select-box small-select" onchange="initArea(null)">
                                <option value="${userPersonalInfo.city}" class="select-default"></option>
                            </select>
                            <select id="area" name="area" class="select-box small-select last-select">
                                <option value="${userPersonalInfo.area}" class="select-default"></option>
                            </select>
                            <div class="input-tip" style="display:none;">
                                <em class="fl"></em>
                                <span>请选择所在地址</span>
                            </div>
                        </div>
                    </div>
                    <div class="forget-item clearfix">
                        <div class="item-left aut-left fl">详细地址</div>
                        <div class="textarea-box fl">
                            <textarea id="address" name="address" placeholder="请输入详细地址，如***路***号">${userPersonalInfo.address}</textarea>
                        </div>
                    </div>
                    <div class="read-agreement" id="address_error_div" hidden>
                        <div class="address">
                            <em class="fl"></em>
                            <span id="address_error_span"></span>
                        </div>
                    </div>
                    <div class="read-agreement">
                        <input id="agreeCheck" name="agreeCheck" type="checkbox" class="checkbox" onchange="changePersonButtonState()">&nbsp;&nbsp;<label for="agreeCheck">我已阅读并同意</label>
                        <a class="protocol main-color" href="${ctx}/common/agreement" target="_blank">《用户协议》</a>
                    </div>
                    <div class="btn-box login-btn">
                        <button id="person_reg_sub" type="submit" disabled>提交</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="mask">
        <img src="${ctx}/resource/images/zm.jpg" alt="pic">
    </div>
    <%@ include file="/WEB-INF/view/common/footer.jsp" %>
    <script src="${ctx}/business/auth/verify/personAuthEditVerify.js?version=${version}"></script>
</body>
</html>