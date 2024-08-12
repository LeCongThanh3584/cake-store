<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="d-flex justify-content-between align-items-center">
    <h4 class="card-title fs-4">${param.name}
        <c:choose>
            <c:when test="${param.dateType == 'month'}">
                <span>| This month</span>
            </c:when>
            <c:when test="${param.dateType == 'week'}">
                <span>| This week</span>
            </c:when>
            <c:otherwise>
                <span>| Today</span>
            </c:otherwise>
        </c:choose>

    </h4>
    <c:if test="${param.sort}">
        <div class="btn-group" role="group">
            <c:choose>
                <c:when test="${param.dateType == 'week'}">
                    <a href="?storeId=${param.storeId}&dateType=date" class="btn btn-outline-primary">Today</a>
                    <a href="?storeId=${param.storeId}&dateType=week" class="btn btn-primary">This week</a>
                    <a href="?storeId=${param.storeId}&dateType=month" class="btn btn-outline-primary">This month</a>
                </c:when>
                <c:when test="${param.dateType == 'month'}">
                    <a href="?storeId=${param.storeId}&dateType=date" class="btn btn-outline-primary">Today</a>
                    <a href="?storeId=${param.storeId}&dateType=week" class="btn btn-outline-primary">This week</a>
                    <a href="?storeId=${param.storeId}&dateType=month" class="btn btn-primary">This month</a>
                </c:when>
                <c:otherwise>
                    <a href="?storeId=${param.storeId}&dateType=date" class="btn btn-primary">Today</a>
                    <a href="?storeId=${param.storeId}&dateType=week" class="btn btn-outline-primary">This week</a>
                    <a href="?storeId=${param.storeId}&dateType=month" class="btn btn-outline-primary">This month</a>
                </c:otherwise>
            </c:choose>
        </div>
    </c:if>
</div>