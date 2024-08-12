<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.demo.util.Constant" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.demo.entity.OrderHistory" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>Dashboard - NiceAdmin Bootstrap Template</title>
    <meta content="" name="description">
    <meta content="" name="keywords">

    <!-- Favicons -->
    <link href="/assets/img/favicon.png" rel="icon">
    <link href="/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

    <!-- Vendor CSS Files -->
    <link href="/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="/assets/css/style.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../../common/admin-header.jsp"></jsp:include>
<jsp:include page="../../common/admin-sidebar.jsp"></jsp:include>

<main id="main" class="main">

    <div>
        <h1>Create new order</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item fs-5"><a href="/admin">Home</a></li>
                <li class="breadcrumb-item fs-5 active">Manage Orders</li>
                <li class="breadcrumb-item fs-5 active">Create order</li>
            </ol>
        </nav>
    </div><!-- End Page Title -->

    <section class="section">
        <div class="row">
            <div class="col-lg-12 mx-auto">
                <div class="card">

                    <div class="card-body">
                        <div class="row">
                            <div class="col-4 mb-3">
                                <label class="col-lg-12 col-form-label">Code</label>
                                <div class="col-lg-12">
                                    <input disabled type="text" name="receiver" value="${order.code}"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="col-4 mb-3">
                                <label class="col-lg-12 col-form-label">Customer</label>
                                <div class="col-lg-12">
                                    <input disabled type="text" name="receiver" value="${order.userName}"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="col-4 mb-3">
                                <label class="col-lg-12 col-form-label">Receiver</label>
                                <div class="col-lg-12">
                                    <input disabled type="text" name="receiver" value="${order.reciver}"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="col-4 mb-3">
                                <label class="col-lg-12 col-form-label">Phone number</label>
                                <div class="col-lg-12">
                                    <input disabled type="text" name="phone" value="${order.phone}"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="col-4 mb-3">
                                <label class="col-lg-12 col-form-label">Address</label>
                                <div class="col-lg-12">
                                    <input disabled type="text" name="address" value="${order.address}"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="col-4 mb-3">
                                <label class="col-lg-12 col-form-label">
                                    Store
                                </label>
                                <div class="col-lg-12">
                                    <input disabled type="text" name="receiver"
                                           value="${order.storeName}"
                                           class="form-control">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <table class="table datatable">
                                    <thead>
                                    <tr>
                                        <th>Product</th>
                                        <th>Quantity</th>
                                        <th>Total</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="item" items="${orderDetails}">
                                        <tr class="cart-item">
                                            <td class="d-flex align-items-center">
                                                <div class="product__cart__item__pic">
                                                    <img style="height: 80px;" src="${item.cakeImage}"
                                                         alt="">
                                                </div>
                                                <div class="ms-2">
                                                    <h5 class="fw-semibold"
                                                        style="color: #555;">${item.cakeName}</h5>
                                                    <fmt:formatNumber value="${productView.price}"
                                                                      type="currency"/>
                                                    </h5>
                                                </div>
                                            </td>
                                            <td class="quantity px-2 fs-5">
                                                    ${item.quantity}
                                            </td>
                                            <td class="cart__price  fs-5">
                                                <fmt:formatNumber
                                                        value="${item.price * item.quantity}"
                                                        type="currency"/>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <tr>
                                        <td></td>
                                        <td class="fs-5 fw-semibold">Total</td>
                                        <td class="fs-5  fw-semibold"><fmt:formatNumber
                                                value="${order.totalMoney}"
                                                type="currency"/></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                    </div>

                </div>
                <div class="card">
                    <div class="card-body">
                        <h2 class="card-title fs-2">Order History</h2>
                        <div class="col-sm-12">
                            <table class="table table-striped table-sm mb-0 pb-0">
                                <thead>
                                <tr class="mb-0">
                                    <th>Stt</th>
                                    <th>Status</th>
                                    <th>Created_date</th>
                                    <th>Created_time</th>
                                    <th>Description</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="item" items="${orderHistories}" varStatus="loop">
                                    <tr>
                                    <td>${loop.index + 1}</td>
                                    <td class="py-2">
                                        <c:choose>
                                                <c:when test="${item.status == 0}">
                                                    <span class="px-2 py-1 rounded bg-warning text-white">PENDING</span>
                                                </c:when>
                                                <c:when test="${item.status == 1}">
                                                    <span class="px-2 py-1 rounded bg-success text-white">CONFIRMED</span>
                                                </c:when>
                                                <c:when test="${item.status == 2}">
                                                    <span class="px-2 py-1 rounded bg-info text-white">DELIVERING</span>
                                                </c:when>
                                                <c:when test="${item.status == 3}">
                                                    <span class="px-2 py-1 rounded bg-primary text-white">DELIVERED</span>
                                                </c:when>
                                                <c:when test="${item.status == 4}">
                                                    <span class="px-2 py-1 rounded bg-danger text-white">CANCELLED</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="px-2 py-1 rounded bg-dark text-white">UNKNOWN</span>
                                                </c:otherwise>
                                            </c:choose>
                                    </td>
                                    <td class="py-2"><fmt:formatDate value="${item.createdAt}"
                                                                     pattern="YYYY-MM-dd"/></td>
                                    <td class="py-2"><fmt:formatDate value="${item.createdAt}"
                                                                     pattern="hh:mm"/></td>
                                    <td class="py-2">${item.description}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="text-center">
                        <a href="/admin/orders" class="btn btn-secondary fs-5 px- mb-4">Back</a>
                    </div>
                </div>
            </div>
        </div>
    </section>

</main><!-- End #main -->

<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i
        class="bi bi-arrow-up-short"></i></a>
<script src="/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="/assets/js/main.js"></script>
</body>
</html>