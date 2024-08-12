<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
        <h1>Update order</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item fs-5"><a href="/admin">Home</a></li>
                <li class="breadcrumb-item fs-5 active"><a href="/admin/orders">Manage Orders</a></li>
                <li class="breadcrumb-item fs-5 active">Update order</li>
            </ol>
        </nav>
    </div><!-- End Page Title -->

    <section class="section">
        <div class="row">
            <div class="col-lg-9 col-md-12 mx-auto">

                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title text-center my-3 fs-2 fw-bold">Edit Order</h5>

                        <c:if test="${not empty sessionScope.info}">
                            <div class="alert alert-success mt-3 alert-dismissible fade show" role="alert">
                                ${sessionScope.info}
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                            <c:remove var="info" scope="session" />
                        </c:if>

                        <!-- Horizontal Form -->
                        <form action="/admin/update-order" method="post">
                            <input type="text" name="id" class="form-control" hidden value="${order.id}">
                             <div class="row mb-3">
                                <label class="col-sm-3 col-form-label fw-bold">Customer</label>
                                <div class="col-sm-9">
                                    <select class="form-select" name="user_id" aria-label="Default select example">
                                        <option value="" selected>Select user</option>
                                        <c:forEach var="user" items="${users}">
                                            <option value="${user.id}" <c:if test="${order.idUser == user.id}">selected</c:if>>${user.fullName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="receiverInput" class="col-sm-3 col-form-label fw-bold">Receiver</label>
                                <div class="col-sm-9">
                                    <input type="text" name="receiver" class="form-control" value="${order.reciver}" id="receiverInput">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="phoneInput" class="col-sm-3 col-form-label fw-bold">Phone number</label>
                                <div class="col-sm-9">
                                    <input type="text" name="phone" class="form-control" value="${order.phone}" id="phoneInput">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="addressInput" class="col-sm-3 col-form-label fw-bold">Address</label>
                                <div class="col-sm-9">
                                    <input type="text" name="address" class="form-control" value="${order.address}" id="addressInput">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label class="col-sm-3 col-form-label fw-bold">
                                    Store
                                    <i class="text-info">*</i>
                                </label>
                                <div class="col-sm-9">
                                    <c:choose>
                                        <c:when test="${sessionScope.role == 0}">
                                            <select id="storeSelect" name="store_id" class="form-select" aria-label="Default select example">
                                                <option value="" selected>Select store</option>
                                                <c:forEach var="store" items="${stores}">
                                                    <option value="${store.id}" <c:if test="${order.idStore == store.id}">selected</c:if>>${store.name}</option>
                                                </c:forEach>
                                            </select>
                                        </c:when>
                                        <c:otherwise>
                                            <select id="storeSelect" disabled name="store_id" class="form-select" aria-label="Default select example">
                                                <option value="" selected>Select store</option>
                                                <c:forEach var="store" items="${stores}">
                                                    <option value="${store.id}" <c:if test="${order.idStore == store.id}">selected</c:if>>${store.name}</option>
                                                </c:forEach>
                                            </select>
                                            <input type="hidden" name="store_id" value="${order.idStore}" />
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="text-center">
                                <button type="submit" class="btn btn-success fs-5 px-4 px-4">Update</button>
                                <a href="/admin/update-item?id=${order.id}" class="btn btn-danger fs-5 px-4">Edit Item</a>
                                <a href="/admin/orders" class="btn btn-secondary fs-5 px-4">Back</a>
                            </div>
                        </form><!-- End Horizontal Form -->

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