<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>Dashboard - Admin</title>
    <meta content="" name="description">
    <meta content="" name="keywords">

    <%--    css-custom--%>

    <link rel="stylesheet" href="/css/store.css">

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
        <h1 class="fw-bold">Add New Store</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item fs-5"><a href="/admin">Home</a></li>
                <li class="breadcrumb-item fs-5 active">Manage Stores</li>
                <li class="breadcrumb-item fs-5 active">Add New Store</li>
            </ol>
        </nav>
    </div><!-- End Page Title -->

    <section class="section">
        <div class="row">
            <div class="col-lg-8 mx-auto col-md-12">

                <div class="card">
                    <div class="card-body">

                        <c:if test="${not empty sessionScope.messageResponse}">
                            <div class="alert alert-primary mt-3 alert-dismissible fade show" role="alert">
                                    ${sessionScope.messageResponse}
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>

                            <!-- Xóa thông báo khỏi session để không hiện lại khi chuyển trang -->
                            <c:remove var="messageResponse" scope="session" />
                        </c:if>

                        <h5 class="card-title text-center my-4 fs-1">Add New Store</h5>

                        <!-- Horizontal Form -->
                        <form action="/admin/add-store" method="post" enctype="multipart/form-data" >
                            <div class="row mb-3">
                                <label for="inputText" class="col-sm-2 col-form-label font-size-label fw-bold">Store's Name: </label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" required name="nameStore" id="inputText">
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label for="inputEmail" class="col-sm-2 col-form-label font-size-label fw-bold">Address: </label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" required name="address" id="inputEmail">
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label for="inputPassword" class="col-sm-2 col-form-label font-size-label fw-bold">Phone Number: </label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" required name="phoneNumber" id="inputPassword">
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label for="status" class="col-sm-2 col-form-label font-size-label fw-bold">Status: </label>
                                <div class="col-sm-10">
                                    <select id="status" class="form-select" required name="status" aria-label="Default select example">
                                        <option value="">Select status</option>
                                        <option value="1">ACTIVATE</option>
                                        <option value="0">INACTIVATE</option>
                                    </select>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label class="col-sm-2 col-form-label font-size-label fw-bold" for="inputImageStore">Image: </label>
                                <div class="col-sm-6">
                                    <input class="form-control" type="file" required accept="image/*" name="image" id="inputImageStore">
                                </div>

                                <div class="col-sm-4">
                                    <img id="imageStore" style="width: 70%;">
                                </div>

                            </div>

                            <div class="text-center mt-5">
                                <button type="submit" class="btn btn-success fs-5 px-4 me-4">Add</button>
                                <a href="/admin/stores" class="btn btn-secondary fs-5 px-4">Back</a>
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
<script src="/js/store.js"></script>
</body>
</html>