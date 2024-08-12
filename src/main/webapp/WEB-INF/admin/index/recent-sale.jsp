<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- Recent Sales -->
<div class="col-12">
    <div class="card recent-sales overflow-auto">
        <div class="card-body">
            <jsp:include page="time-sorter.jsp">
                <jsp:param name="name" value="Recent Orders"/>
                <jsp:param name="sort" value="${true}"/>
            </jsp:include>

            <table class="table table-striped table-borderless">

                <thead>
                <tr>
                    <th></th>
                    <th scope="col">Order</th>
                    <th scope="col">Customer</th>
                    <th scope="col">Address</th>
                    <c:if test="${role == 0}">
                        <th scope="col">Store</th>
                    </c:if>
                    <th scope="col">Price</th>
                    <th scope="col">Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${latestChange}" var="item">
                    <tr>
                        <td>${item.timeAgo}</td>
                        <th scope="row">
                            <a href="/admin/order?id=${item.id}">${item.code}</a>
                        </th>
                        <td>${item.receiver}</td>
                        <td>${item.address}</td>
                        <c:if test="${role == 0}">
                            <td>${item.storeName}</td>
                        </c:if>
                        <td><fmt:formatNumber value="${item.totalMoney}" type="currency"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${item.status == 0}">
                                    <span class="badge bg-warning">PENDING</span>
                                </c:when>
                                <c:when test="${item.status == 1}">
                                    <span class="badge bg-success">CONFIRMED</span>
                                </c:when>
                                <c:when test="${item.status == 2}">
                                    <span class="badge bg-info">DELIVERING</span>
                                </c:when>
                                <c:when test="${item.status == 3}">
                                    <span class="badge bg-primary">DELIVERED</span>
                                </c:when>
                                <c:when test="${item.status == 4}">
                                    <span class="badge bg-danger">CANCELLED</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-dark">UNKNOWN</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>

            </table>
            <div class="text-center"><a href="/admin/orders" class="btn btn-outline-primary">Show more</a></div>
        </div>

    </div>
</div>
<!-- End Recent Sales -->