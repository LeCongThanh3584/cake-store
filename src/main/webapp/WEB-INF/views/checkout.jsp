<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page errorPage="error.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Cake Template">
    <meta name="keywords" content="Cake, unica, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Cake | Template</title>

    <!-- Css Styles -->
    <link rel="stylesheet" href="/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="/css/flaticon.css" type="text/css">
    <link rel="stylesheet" href="/css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="/css/elegant-icons.css" type="text/css">
    <link rel="stylesheet" href="/css/owl.carousel.min.css" type="text/css">
    <link rel="stylesheet" href="/css/slicknav.min.css" type="text/css">
    <link rel="stylesheet" href="/css/style.css" type="text/css">
    <style>
        .checkout__total__products span {
            white-space: nowrap;
        }

        .shopping__cart__table {
            margin-bottom: 12px !important;

            th {
                padding-bottom: 0 !important;
            }

            td {
                padding-bottom: 12px !important;
                padding-top: 12px !important;
            }
        }
    </style>
</head>
<body class="vh-100">
<div class="d-flex flex-column h-100">
    <jsp:include page="../common/header.jsp"></jsp:include>

    <!-- Breadcrumb Begin -->
    <div class="breadcrumb-option">
        <div class="container">
            <div class="row">
                <div class="col-lg-6 col-md-6 col-sm-6">
                    <div class="breadcrumb__text">
                        <h2>Checkout</h2>
                    </div>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-6">
                    <div class="breadcrumb__links">
                        <a href="/index">Home</a>
                        <span>Checkout</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Checkout Section Begin -->
    <section class="checkout spad">
        <div class="container">
            <div class="checkout__form">
                <form action="/checkout" method="post">
                    <div class="row">
                        <div class="col-lg-8 col-md-6">
                            <h4 class="checkout__title">Billing Details</h4>
                            <c:forEach var="orderView" items="${orderViews}" varStatus="loop">
                                <div class="shopping__cart__table px-3 pt-3 rounded" style="background-color: #eee;">
                                    <h4>Order ${loop.index + 1} ( ${orderView.storeName} - ${orderView.storeAddress}
                                        )</h4>
                                    <table>
                                        <thead>
                                        <tr>
                                            <th>Product</th>
                                            <th>Quantity</th>
                                            <th>Total</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="productView" items="${orderView.orderDetails}">
                                            <input hidden name="cartId" value="${productView.id}">
                                            <tr class="cart-item">
                                                <td class="product__cart__item">
                                                    <div class="product__cart__item__pic">
                                                        <img style="height: 80px;" src="${productView.image}"
                                                             alt="">
                                                    </div>
                                                    <div class="product__cart__item__text">
                                                        <h5>${productView.name}</h5>
                                                        <h5>
                                                            <fmt:formatNumber value="${productView.price}"
                                                                              type="currency"/>
                                                        </h5>
                                                    </div>
                                                </td>
                                                <td class="quantity__item">
                                                    <div class="quantity px-2 ">
                                                        <strong>${productView.quantity}</strong>
                                                    </div>
                                                </td>
                                                <td class="cart__price">
                                                    <fmt:formatNumber
                                                            value="${productView.price * productView.quantity}"
                                                            type="currency"/>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:forEach>


                            <div class="row">
                                <div class="col-lg-6">
                                    <div class="checkout__input mb-2">
                                        <p>Receiver Name<span>*</span></p>
                                        <input class="mb-0" name="receiver" value="${address.name}" type="text">
                                        <div style="color: #ff5e5e" class="mx-2">
                                            ${receiverError}
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-6">
                                    <div class="checkout__input mb-2">
                                        <p>Phone<span>*</span></p>
                                        <input class="mb-0" name="phone" value="${address.phone}" type="text">
                                        <div style="color: #ff5e5e" class="mx-2">
                                            ${phoneError}
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-4 col-sm-6">
                                    <div class="checkout__input mb-2">
                                        <p>District<span>*</span></p>
                                        <input class="mb-0" name="district"
                                               value="${address.district}">
                                        <div style="color: #ff5e5e" class="mx-2">
                                            ${districtError}
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-4 col-sm-6">
                                    <div class="checkout__input mb-2">
                                        <p>Ward<span>*</span></p>
                                        <input class="mb-0" name="ward" value="${address.ward}">
                                        <div style="color: #ff5e5e" class="mx-2">
                                            ${wardError}
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-4 col-sm-12">
                                    <div class="checkout__input mb-2">
                                        <p>Province<span>*</span></p>
                                        <input class="mb-0" name="province" value="${address.province}">
                                        <div style="color: #ff5e5e" class="mx-2">
                                            ${provinceError}
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <div class="col-lg-4 col-md-6">
                            <div class="checkout__order">
                                <h6 class="order__title">Your order</h6>
                                <div class="checkout__order__products">Order <span>Total</span></div>
                                <ul class="checkout__total__products">
                                    <c:forEach var="orderView" items="${orderViews}" varStatus="loop">
                                        <li>
                                            <samp>${loop.index + 1}.</samp>Order ${loop.index + 1}
                                            <span>
                                                <fmt:formatNumber value="${orderView.totalMoney}"
                                                                  type="currency"/>
                                            </span>
                                        </li>
                                    </c:forEach>
                                </ul>
                                <c:set var="total" value="${0}"/>
                                <c:forEach var="orderView" items="${orderViews}">
                                    <c:set var="total" value="${total + orderView.totalMoney}"/>
                                </c:forEach>
                                <ul class="checkout__total__all">
                                    <li>Total
                                        <span>
                                            <fmt:formatNumber value="${total}"
                                                              type="currency"/>
                                        </span>
                                    </li>
                                </ul>
                                <div class="checkout__input__checkbox">
                                    <label for="payment">
                                        Check Payment
                                        <input type="checkbox" id="payment">
                                        <span class="checkmark"></span>
                                    </label>
                                </div>
                                <div class="checkout__input__checkbox">
                                    <label for="paypal">
                                        Paypal
                                        <input type="checkbox" id="paypal">
                                        <span class="checkmark"></span>
                                    </label>
                                </div>
                                <button type="submit" class="site-btn">PLACE ORDER</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </section>
    <!-- Checkout Section End -->

    <!-- Search Begin -->
    <%--    <div class="search-model">--%>
    <%--        <div class="h-100 d-flex align-items-center justify-content-center">--%>
    <%--            <div class="search-close-switch">+</div>--%>
    <%--            <form class="search-model-form">--%>
    <%--                <input type="text" id="search-input" placeholder="Search here.....">--%>
    <%--            </form>--%>
    <%--        </div>--%>
    <%--    </div>--%>
    <!-- Search End -->
    <jsp:include page="../common/footer.jsp"></jsp:include>
</div>

<!-- Js Plugins -->
<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/jquery.slicknav.js"></script>
<script src="/js/owl.carousel.min.js"></script>
<script src="/js/jquery.nicescroll.min.js"></script>
</body>
</html>