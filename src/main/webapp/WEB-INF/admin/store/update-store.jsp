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
            <h1 class="fw-bold">Update Store</h1>
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item fs-5"><a href="/admin">Home</a></li>
                    <li class="breadcrumb-item fs-5 active">Manage Stores</li>
                    <li class="breadcrumb-item fs-5 active">Update Store</li>
                </ol>
            </nav>
        </div><!-- End Page Ti -->

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

                            <h5 class="card-title text-center my-4 fs-1">Update Store</h5>

                            <!-- Horizontal Form -->
                            <form action="/admin/update-store" method="post" enctype="multipart/form-data">
                                <div class="row mb-3">
                                    <input type="text" hidden value="${store.id}" name="id">
                                    <label for="inputText" class="col-sm-2 col-form-label font-size-label fw-bold">Store's Name: </label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="name" id="inputText" value="${store.name}">
                                    </div>
                                </div>

                                <div class="row mb-3">
                                    <label class="col-sm-2 col-form-label font-size-label fw-bold">Code: </label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="code" readonly value="${store.code}">
                                    </div>
                                </div>

                                <div class="row mb-3">
                                    <label for="inputEmail" class="col-sm-2 col-form-label font-size-label fw-bold">Address: </label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="address" id="inputEmail" value="${store.address}">
                                    </div>
                                </div>

                                <div class="row mb-3">
                                    <label for="inputPassword" class="col-sm-2 col-form-label font-size-label fw-bold">Phone Number: </label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="phoneNumber" id="inputPassword" value="${store.phone}">
                                    </div>
                                </div>

                                <div class="row mb-3">
                                    <label for="status" class="col-sm-2 col-form-label font-size-label fw-bold">Status: </label>
                                    <div class="col-sm-10">
                                        <select id="status" class="form-select" name="status" aria-label="Default select example">
                                            <option selected="">Select status</option>
                                            <option value="1" ${store.status == 1 ? 'selected' : ''}>ACTIVATE</option>
                                            <option value="0" ${store.status == 0 ? 'selected' : ''}>INACTIVATE</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="row mb-3">
                                    <label class="col-sm-2 col-form-label font-size-label fw-bold" for="inputImageStore">Image: </label>
                                    <div class="col-sm-6">
                                        <input class="form-control" type="file" accept="image/*" name="image" id="inputImageStore">
                                    </div>

                                    <div class="col-sm-4">
                                        <img id="imageStore" style="width: 80%;" src="${store.image}" alt="Image store">
                                    </div>

                                </div>

                                <div class="text-center mt-5">
                                    <button type="submit" class="btn btn-success fs-5 px-4 me-4">Update</button>
                                    <a href="/admin/stores" class="btn btn-secondary fs-5 px-4">Back</a>
                                </div>

                            </form><!-- End Horizontal Form -->

                        </div>
                    </div>

                </div>
            </div>
        </section>

    </main><!-- End #main -->

    <!-- The Modal image -->
    <div id="myModal" class="modal">
        <span class="close" style="margin-top: 50px">&times;</span>
        <img class="modal-content" id="img01">
        <div id="caption"></div>
    </div>

    <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i
            class="bi bi-arrow-up-short"></i></a>
    <script src="/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="/assets/js/main.js"></script>
    <script src="/js/store.js"></script>
</body>
</html>