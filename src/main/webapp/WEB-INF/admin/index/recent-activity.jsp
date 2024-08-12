<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="card">
    <div class="filter">
        <a class="icon" href="#" data-bs-toggle="dropdown"><i class="bi bi-three-dots"></i></a>
        <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow">
            <li class="dropdown-header text-start">
                <h6>Filter</h6>
            </li>

            <li><a class="dropdown-item" href="#">Today</a></li>
            <li><a class="dropdown-item" href="#">This Month</a></li>
            <li><a class="dropdown-item" href="#">This Year</a></li>
        </ul>
    </div>

    <div class="card-body">
        <jsp:include page="time-sorter.jsp">
            <jsp:param name="name" value="Recent Activity"/>
        </jsp:include>
        <div class="activity py-2">
            <c:forEach items="${latestChange}" var="item">
                <div class="activity-item d-flex">
                    <div class="activite-label" style="width: 25%">${item.timeAgo}</div>
                    <c:choose>
                        <c:when test="${item.status == 0}">
                            <i class='bi bi-circle-fill activity-badge text-warning align-self-start'></i>
                            <div class="activity-content">
                                New order pending <a href="/admin/order?id=${item.id}" class="fw-bold">#${item.code}</a>
                                by ${item.createdBy}
                            </div>
                        </c:when>
                        <c:when test="${item.status == 1}">
                            <i class='bi bi-circle-fill activity-badge text-success align-self-start'></i>
                            <div class="activity-content">
                                Order <a href="/admin/order?id=${item.id}" class="fw-bold">#${item.code}</a> confirmed
                                by ${item.createdBy}
                            </div>
                        </c:when>
                        <c:when test="${item.status == 2}">
                            <i class='bi bi-circle-fill activity-badge text-info align-self-start'></i>
                            <div class="activity-content">
                                Order <a href="/admin/order?id=${item.id}" class="fw-bold">#${item.code}</a> delivering
                                by ${item.createdBy}
                            </div>
                        </c:when>
                        <c:when test="${item.status == 3}">
                            <i class='bi bi-circle-fill activity-badge text-primary align-self-start'></i>
                            <div class="activity-content">
                                Order <a href="/admin/order?id=${item.id}" class="fw-bold">#${item.code}</a> delivered
                                by ${item.createdBy}
                            </div>
                        </c:when>
                        <c:when test="${item.status == 4}">
                            <i class='bi bi-circle-fill activity-badge text-danger align-self-start'></i>
                            <div class="activity-content">
                                Order <a href="/admin/order?id=${item.id}" class="fw-bold">#${item.code}</a> declined
                                by ${item.createdBy}
                            </div>
                        </c:when>
                    </c:choose>
                </div>
                <!-- End activity item-->
            </c:forEach>
        </div>

    </div>
</div>
<!-- End Recent Activity -->