<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:forEach items="${bankInfoDtoList}" var="bankInfo" varStatus="status">
    <ul class="clearfix">
        <li class="withdrawals-li">
            <c:if test="${status.first}">
                <input id="${bankInfo.recordNo}" class="checkbox" type="radio" checked="checked" name="recordNo" value="${bankInfo.recordNo}">
            </c:if>
            <c:if test="${!status.first}">
                <input id="${bankInfo.recordNo}" class="checkbox" type="radio" name="recordNo" value="${bankInfo.recordNo}">
            </c:if>
            <label for="${bankInfo.recordNo}">
                <img src="${ctx}/resource/images/bank/${bankInfo.bankCode}.png" alt="bank" onerror="onImgError(this)">
                 ${bankInfo.bankName}
            </label>
        </li>
        <li>${bankInfo.cardHolder}</li>
        <li>${bankInfo.cardNo}</li>
    </ul>
</c:forEach>