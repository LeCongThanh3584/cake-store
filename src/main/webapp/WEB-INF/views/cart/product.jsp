<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:forEach var="cart" items="${carts}">
    <c:choose>
        <c:when test="${cart.expired }">
            <tr class="cart-item" style="background-color: #eee">
                <td style="width: 5%;writing-mode:vertical-rl; background-color: #ccc"
                    class="text-white fw-bold text-center">
                    Expired
                </td>
                <td class="product__cart__item">
                    <div class="product__cart__item__pic">
                        <img style="height: 80px;" src="${cart.image}" alt="">
                    </div>
                    <div class="product__cart__item__text">
                        <h6>${cart.name}</h6>
                        <h5>
                            <fmt:formatNumber value="${cart.price}" type="currency"/>
                        </h5>
                    </div>
                </td>
                <td class="quantity__item">
                    <div class="quantity">
                        <input name="number" disabled
                               data-index="${cart.id}"
                               class="cart-input text-center border-0 p-2"
                               style="width: 100px" value="${cart.quantity}">
                    </div>
                </td>
                <td class="cart__price">
                    <fmt:formatNumber value="${cart.price * cart.quantity}" type="currency"/>
                </td>
                <td class="cart__close px-2">
                    <a href="/cart/delete?cartId=${cart.id}" class="btn btn-danger">
                        <i class="fa fa-remove"></i>
                    </a>
                </td>
            </tr>

        </c:when>
        <c:otherwise>
            <tr class="cart-item">

                <td style="width: 5%">
                    <input data-index="${cart.id}"
                           data-price="${cart.price}"
                           data-quantity="${cart.quantity}"
                           class="checkbox" type="checkbox">
                </td>
                <td class="product__cart__item">
                    <div class="product__cart__item__pic">
                        <img style="height: 80px;" src="${cart.image}" alt="">
                    </div>
                    <div class="product__cart__item__text">
                        <h6>${cart.name}</h6>
                        <h5>
                            <fmt:formatNumber value="${cart.price}" type="currency"/>
                        </h5>
                    </div>
                </td>
                <td class="quantity__item">
                    <div class="quantity">
                        <input name="number"
                               data-index="${cart.id}"
                               class="cart-input text-center border-0 p-2"
                               style="width: 100px" value="${cart.quantity}">
                    </div>
                </td>
                <td class="cart__price">
                    <fmt:formatNumber value="${cart.price * cart.quantity}" type="currency"/>
                </td>
                <td class="cart__close px-2">
                    <a href="/cart/delete?cartId=${cart.id}" class="btn btn-danger">
                        <i class="fa fa-remove"></i>
                    </a>
                    <button class="btn btn-primary mt-2 d-none">
                        <i class="fa fa-refresh"></i>
                    </button>
                </td>
            </tr>
        </c:otherwise>
    </c:choose>

</c:forEach>
