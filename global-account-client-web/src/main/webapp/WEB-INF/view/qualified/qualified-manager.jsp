<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>持有人管理</title>
    <%@ include file="/WEB-INF/view/common/common.jsp" %>
</head>
<body>
<div class="login-layout">
    <%@ include file="/WEB-INF/view/common/header.jsp" %>
    <div class="same-layout">
        <div class="same-title">
            <div class="title-left title-pic fl">
                <i class="fl holder-pic"></i>
                <h2>持有人管理</h2>
            </div>
            <c:if test="${userType ==2}">
                <div class="title-right fr">
                    <a href="javascript:void(0); "
                       onclick="userRealNameStatusQuery2('${ctx}/auth/4/orgAuthAddPage.do',2)">
                        <em class="bold">+</em>
                        <span>新增持有人</span>
                    </a>
                </div>
            </c:if>
        </div>
        <div class="same-content bottom-content">
            <div class="table-box">
                <table>
                    <thead>
                    <tr>
                        <th>企业名称/英文名称</th>
                        <th>营业执照号</th>
                        <th>邮箱</th>
                        <th>法人姓名</th>
                        <th>法人证件号</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageInfo}" var="org">
                        <tr>
                            <td>${org.name}/${org.enName}</td>
                            <td><c:out value="${org.idNo}"/></td>
                            <td><c:out value="${org.email}"/></td>
                            <td><c:out value="${org.legalName}"/></td>
                            <td><c:out value="${org.legalIdNo}"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${org.realnameStatus == 2}">
                                        审核成功
                                    </c:when>
                                    <c:when test="${org.realnameStatus == 3}">
                                        审核失败
                                    </c:when>
                                    <c:otherwise>
                                        待审核
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="modify-btn">
                                <c:if test="${org.realnameStatus == 3}">
                                    <a href="${ctx}/auth/4/orgAuthEditPage.do?userInfoNo=${org.userInfoNo}">修改</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <%--<div class="clearfix">
                <div class="pagination fl">
                    <ul class="clearfix">
                        <li>
                            <a href="javascript:;">首页</a>
                        </li>
                        <li class="prev-btn page">
                            <a href="javascript:;">上一页</a>
                        </li>
                        <li class="page-num">
                            <select>
                                <option>1</option>
                                <option>2</option>
                                <option>3</option>
                            </select>
                        </li>
                        <li class="next-btn page">
                            <a href="javascript:;">下一页</a>
                        </li>
                        <li>
                            <a href="javascript:;">末页</a>
                        </li>
                    </ul>
                </div>
            </div>--%>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/view/common/footer.jsp" %>
</body>
</html>