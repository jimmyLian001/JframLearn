<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="mvc" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://global.baofu.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="time" value="<%=System.currentTimeMillis() %>"/>
<c:set var="version" value="1.0.4" />
<meta name="keywords" content="">
<meta name="description" content="">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ctx}/resource/images/icon.ico?version=${version}" rel="shortcut icon">
<!-- 公共的css文件 -->
<link href="${ctx}/resource/css/base.css?version=${version}" rel="stylesheet" media="screen" type="text/css">
<link href="${ctx}/resource/css/main.css?version=${version}" rel="stylesheet" media="screen" type="text/css">

<script src="${ctx}/resource/js/common/jquery-1.11.1.min.js?version=${version}" type="text/javascript"></script>
<script src="${ctx}/resource/js/common/common.js?version=${version}" type="text/javascript"></script>

<!-- jQuery及其扩展插件 -->
<script src="${ctx}/resource/js/common/jquery.form.js?version=${version}"></script>
<script src="${ctx}/resource/js/common/plugins/jquery.json-2.4.min.js?version=${version}" type="text/javascript"></script>
<script src="${ctx}/resource/js/common/plugins/jQuery.rTabs.js?version=${version}"></script>

<script src="${ctx}/resource/js/common/plugins/verify/jquery.validate.min.js?version=${version}"></script>
<script src="${ctx}/resource/js/common/plugins/verify/messages_zh.min.js?version=${version}"></script>

<script type="text/javascript">
    var ctx = '${ctx}';

    var loginOut = function () {
        window.location = ctx + "/login/loginOut.do"
    }
</script>
