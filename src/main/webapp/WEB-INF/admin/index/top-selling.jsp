<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- Top Selling -->
<div class="col-12">
    <div class="card top-selling overflow-auto">
        <div class="card-body ">
            <jsp:include page="time-sorter.jsp">
                <jsp:param name="name" value="Top Selling"/>
                <jsp:param name="sort" value="${true}"/>
            </jsp:include>

            <table class="table table-striped table-borderless">
                <thead>
                <tr>
                    <th scope="col">Preview</th>
                    <th scope="col">Product</th>
                    <th scope="col">Price</th>
                    <th scope="col">Sold</th>
                    <th scope="col">Revenue</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${topSelling}">
                    <tr>
                        <th scope="row">
                            <img src="${item.image}" alt="">
                        </th>
                        <td><a href="/admin/cake/update?id=${item.id}" class="text-primary fw-bold">${item.name}</a>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${item.minPrice == item.maxPrice}">
                                    $${item.maxPrice}
                                </c:when>
                                <c:otherwise>
                                    $${item.minPrice} - ${item.maxPrice}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="fw-bold">${item.quantity}</td>
                        <td><fmt:formatNumber value="${item.revenue}" type="currency"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="text-center">
                <a href="/admin/cake/view" class="btn btn-outline-primary">Show more</a>
            </div>
        </div>
    </div>
</div>
<!-- End Top Selling -->