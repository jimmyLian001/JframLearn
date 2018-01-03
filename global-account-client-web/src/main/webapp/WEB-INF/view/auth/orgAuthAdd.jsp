<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge，chrome=1">
    <meta name="renderer" content="webkit">
    <title>实名认证</title>

    <link rel="stylesheet" href="${ctx}/resource/css/errorStyle.css?version=${version}">
    <link rel="stylesheet" href="${ctx}/resource/css/auth.css?version=${version}">
    <link href="${ctx}/resource/images/icon.ico" rel="shortcut icon">
    <script src="${ctx}/business/auth/authAdd.js?version=${version}"></script>
    <script src="${ctx}/resource/js/common/idCardCheck.js?version=${version}"></script>
</head>
<body>
<form id="orgAuthForm" name="orgAuthForm" method="post" action="${ctx}/auth/orgAuthAdd.do"
      enctype="multipart/form-data">
    <input type="hidden" id="multiLicenseType" name="multiLicenseType" value="1"/>
    <input type="hidden" id="requestType" name="requestType" value="${requestType}"/>

    <div class="login-layout">
        <%@ include file="/WEB-INF/view/common/header.jsp" %>
        <div class="authentication-layout register-content">
            <c:choose>
                <c:when test="${requestType ==3 || requestType ==4}">
                    <div class="authentication-title">新增持有人信息</div>
                </c:when>
                <c:otherwise>
                    <div class="authentication-title">企业信息实名认证</div>
                </c:otherwise>
            </c:choose>
            <div class="authentication-container">
                <div class="questions-info">
                    <div class="forget-item clearfix">
                        <div class="item-left aut-left fl">企业名称</div>
                        <div class="input-box fl">
                            <input type="text" id="companyName" name="companyName" placeholder="请输入企业真实名称">
                            <div class="input-tip" style="display: none">
                                <em class="fl"></em>
                                <span>以上内容不能为空/输入格式不正确</span>
                            </div>
                        </div>
                    </div>
                    <div class="forget-item clearfix">
                        <div class="item-left aut-left fl">企业英文名称</div>
                        <div class="input-box fl">
                            <input type="text" id="englishName" name="englishName" placeholder="请输入企业英文名称">
                        </div>
                    </div>

                    <div class="forget-item clearfix">
                        <div class="forget-item clearfix <c:if test="${requestType != 3 && requestType!=4}"> hide</c:if>">
                            <div class="item-left aut-left fl">绑定邮箱</div>
                            <div class="input-box fl">
                                <input type="text" name="email" placeholder="请输入绑定邮箱">
                            </div>
                        </div>
                        <div class="forget-item clearfix">
                            <div class="item-left aut-left fl">企业证件类型</div>
                            <div class="item-right bank-btn account-btn fl">
                                <button class="current mul-btn" type="button" onclick="changeMultiLicenseType(true)">
                                    多证合一营业执照
                                </button>
                                <button id="org_account_span" class="general-btn" type="button"
                                        onclick="changeMultiLicenseType(false)">普通营业执照
                                </button>
                            </div>
                        </div>
                        <div class="business-license">
                            <div class="forget-item clearfix">
                                <div class="item-left aut-left fl">营业执照照片</div>
                                <div class="item-right fl">
                                    <span>请确保图片上的文字清晰可见，图片支持JPG、PNG、GIF，文件小于2MB</span>
                                    <div class="input-tip" id="licenseImage_error_div" hidden>
                                        <em class="fl"></em>
                                        <span id="licenseImage_error_span"></span>
                                    </div>
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
                                        <p>点击上传</p>
                                    </div>
                                    <input id="licenseImage" name="licenseImage" type="file"
                                           onchange="viewImage(this, 'license')">
                                    <div id="image_license" class="preview">
                                        <img id="license"/>
                                    </div>
                                </div>
                                <div class="picture-example fl ex3" data-imgUrl="${ctx}/resource/images/hy.jpg">
                                    <div class="picture-example-tip">
                                        <i class="search"></i>
                                        <p>示例</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="forget-item clearfix">
                            <div class="item-left aut-left fl">营业执照号</div>
                            <div class="input-box fl">
                                <input type="text" name="licenseNo" placeholder="请输入营业执照号">
                                <div class="input-tip" style="display: none;">
                                    <em class="fl"></em>
                                    <span>以上内容不能为空/输入格式不正确</span>
                                </div>
                            </div>
                        </div>
                        <div class="general-display hide">
                            <div class="tax-certificate">
                                <div class="forget-item clearfix">
                                    <div class="item-left aut-left fl">税务登记证照片</div>
                                    <div class="item-right fl">
                                        <span>请确保图片上的文字清晰可见，图片支持JPG、PNG、GIF，文件小于2MB</span>
                                        <div class="input-tip" id="taxRegCertImage_error_div" hidden>
                                            <em class="fl"></em>
                                            <span id="taxRegCertImage_error_span"></span>
                                        </div>
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
                                            <p>点击上传</p>
                                        </div>
                                        <input id="taxRegCertImage" name="taxRegCertImage" type="file"
                                               onchange="viewImage(this, 'taxRegCert')">
                                        <div id="image_taxRegCert" class="preview">
                                            <img id="taxRegCert"/>
                                        </div>
                                    </div>
                                    <div class="picture-example fl ex4" data-imgUrl="${ctx}/resource/images/swdj.jpg">
                                        <div class="picture-example-tip">
                                            <i class="search"></i>
                                            <p>示例</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="institutional-proof">
                                <div class="forget-item clearfix">
                                    <div class="item-left aut-left fl">组织机构代码证照片</div>
                                    <div class="item-right fl">
                                        <span>请确保图片上的文字清晰可见，图片支持JPG、PNG、GIF，文件小于2MB</span>
                                        <div class="input-tip" id="orgCodeCertImage_error_div" hidden>
                                            <em class="fl"></em>
                                            <span id="orgCodeCertImage_error_span"></span>
                                        </div>
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
                                            <p>点击上传</p>
                                        </div>
                                        <input id="orgCodeCertImage" name="orgCodeCertImage" type="file"
                                               onchange="viewImage(this, 'orgCodeCert')">
                                        <div id="image_orgCodeCert" class="preview">
                                            <img id="orgCodeCert"/>
                                        </div>
                                    </div>
                                    <div class="picture-example fl ex5" data-imgUrl="${ctx}/resource/images/zzjg.jpg">
                                        <div class="picture-example-tip">
                                            <i class="search"></i>
                                            <p>示例</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="forget-item clearfix">
                            <div class="item-left aut-left fl">法人姓名</div>
                            <div class="input-box fl">
                                <input type="text" id="legalName" name="legalName" placeholder="请输入法人姓名">
                                <div class="input-tip" style="display: none;">
                                    <em class="fl"></em>
                                    <span>以上内容不能为空/输入格式不正确</span>
                                </div>
                            </div>
                        </div>
                        <div class="id-card">
                            <div class="forget-item clearfix">
                                <div class="item-left aut-left fl">法人代表证件照片</div>
                                <div class="item-right fl">
                                    <span>请确保图片上的文字清晰可见，图片支持JPG、PNG、GIF，文件小于2MB</span>
                                    <div class="input-tip" id="div_error" hidden>
                                        <em class="fl"></em>
                                        <span id="span_error"></span>
                                    </div>
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
                                    <input id="idFrontImage" name="idFrontImage" type="file"
                                           onchange="viewImage(this, 'idFront')">
                                    <div id="image_idFront" class="preview">
                                        <img id="idFront"/>
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
                                    <input id="idReverseImage" name="idReverseImage" type="file"
                                           onchange="viewImage(this, 'idReverse')">
                                    <div id="image_idReverse" class="preview">
                                        <img id="idReverse"/>
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
                            <div class="item-left aut-left fl">法人证件号码</div>
                            <div class="input-box fl">
                                <input type="text" id="legalNo" name="legalNo" placeholder="请输入法人证件号码">
                                <div class="input-tip" style="display: none;">
                                    <em class="fl"></em>
                                    <span>以上内容不能为空/输入格式不正确</span>
                                </div>
                            </div>
                        </div>
                        <div class="forget-item clearfix">
                            <div class="item-left aut-left fl">邮编</div>
                            <div class="input-box fl">
                                <input type="text" id="postCode" name="postCode" placeholder="请输入邮编">
                                <div class="input-tip" style="display: none;">
                                    <em class="fl"></em>
                                    <span>以上内容不能为空/输入格式不正确</span>
                                </div>
                            </div>
                        </div>
                        <div class="forget-item clearfix">
                            <div class="item-left aut-left fl">所在地址</div>
                            <div class="input-box fl">
                                <select id="province" name="province" class="select-box small-select"
                                        onchange="initCity()">
                                    <option value="" class="select-default">请选择省</option>
                                </select>
                                <select id="city" name="city" class="select-box small-select" onchange="initArea()">
                                    <option value="" class="select-default">请选择市</option>
                                </select>
                                <select id="area" name="area" class="select-box small-select last-select">
                                    <option value="" class="select-default">请选择地区</option>
                                </select>
                                <div class="input-tip" style="display: none;">
                                    <em class="fl"></em>
                                    <span>请选择所在地址</span>
                                </div>
                            </div>
                        </div>
                        <div class="forget-item clearfix">
                            <div class="item-left aut-left fl">详细地址</div>
                            <div class="textarea-box fl">
                                <textarea id="address" name="address" placeholder="请输入详细地址，如***路***号"></textarea>
                            </div>
                        </div>
                        <div class="read-agreement" id="address_error_div" hidden>
                            <div class="address">
                                <em class="fl"></em>
                                <span id="address_error_span"></span>
                            </div>
                        </div>
                        <div class="read-agreement">
                            <input id="agreeCheck" class="checkbox" type="checkbox"
                                   onchange="changeOrgButtonState()"><label for="agreeCheck">我已阅读并同意</label>
                            <a class="protocol main-color" href="${ctx}/common/agreement" target="_blank">《用户协议》</a>
                        </div>
                        <div class="btn-box login-btn">
                            <button id="org_reg_sub" type="submit" disabled>提交</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="mask">
            <img src="${ctx}/resource/images/zm.jpg" alt="pic">
        </div>
        <%@ include file="/WEB-INF/view/common/footer.jsp" %>
        <script src="${ctx}/business/auth/verify/orgAuthAddVerify.js?version=${version}"></script>
</body>
</html>