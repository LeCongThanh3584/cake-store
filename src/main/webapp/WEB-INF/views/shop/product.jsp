<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="row px-3 mt-5">
    <c:forEach var="item" items="${cakes}">
        <div class="col-lg-3 col-md-6 col-sm-6">
            <div class="product__item">
                <a href="/detail/${item.id}" class="product__item__pic h-auto shadow">
                    <img src="${item.image}">
                    <div class="product__label">
                        <span style="text-transform: capitalize">${item.category}</span>

                    </div>
                </a>
                <div class="product__item__text">
                    <h5>${item.name}</h5>
                    <div>
                        Prod:
                        <fmt:formatDate value="${item.productionDate}" pattern="dd/MM"/>
                        -
                        <fmt:formatDate value="${item.expirationDate}" pattern="dd/MM"/>
                    </div>
                    <c:choose>
                        <c:when test="${item.minPrice == item.maxPrice}">
                            <div class="product__item__price">
                                <fmt:formatNumber value="${item.minPrice}" type="currency"/>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="product__item__price">
                                <fmt:formatNumber value="${item.minPrice}" type="currency"/>
                                -
                                <fmt:formatNumber value="${item.maxPrice}" type="currency"/>
                            </div>
                        </c:otherwise>
                    </c:choose>
                    <div class="cart_add">
                        <a href="/detail/${item.id}">Add to cart</a>
                    </div>
                </div>
            </div>
        </div>

    </c:forEach>
</div>