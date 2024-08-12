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
    <link rel="stylesheet" href="/css/cake-store.css">

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
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item fs-5"><a href="/admin">Home</a></li>
                <li class="breadcrumb-item fs-5 active">Store</li>
                <li class="breadcrumb-item fs-5 active">Update cake of store</li>
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

                        <h5 class="card-title text-center my-2 fs-1">Update cake of store</h5>

                        <!-- Horizontal Form -->
                        <form action="/admin/update-store-cake" method="post">
                            <input type="text" name="storeId" value="${storeId}" hidden>
                            <input type="text" name="cakeStoreId" value="${cakeStore.id}" hidden>
                            <div class="row mb-5">
                                <label class="col-sm-2 col-form-label font-size-label fw-bold">Cake: </label>
                                <div class="dropdown col-sm-10">
                                    <button class="btn btn-secondary w-100 bg-light text-dark" type="button" id="dropdownMenuSelectCake" data-bs-toggle="dropdown" aria-expanded="false">
                                        <li class="dropdown-item hover">
                                            <div class="row">
                                                <div class="col-2">
                                                    <p class="fw-bold mb-2">Id: </p> <span>${cakeStore.idCake}</span>
                                                </div>
                                                <div class="col-3">
                                                    <p class="fw-bold mb-2">Name: </p> <span>${cakeStore.cakeName}</span>
                                                </div>
                                                <div class="col-2">
                                                    <p class="fw-bold mb-2">Code: </p> <span>${cakeStore.codeCake}</span>
                                                </div>
                                                <div class="col-3">
                                                    <p class="fw-bold mb-2">Category: </p> <span>${cakeStore.categoryName}</span>
                                                </div>
                                                <div class="col-2">
                                                    <p class="fw-bold mb-2">Image: </p>
                                                    <img style="width: 35px;" src="${cakeStore.imageCake}" alt="">
                                                </div>
                                            </div>
                                        </li>
                                    </button>
                                </div>
                                <input type="text" name="cakeId" value="${cakeStore.idCake}" hidden>
                            </div>

                            <div class="row mb-5">
                                <div class="col-sm-6">
                                    <label for="inputMFD" class="col-form-label font-size-label fw-bold">Manufacture Date: </label>
                                    <div>
                                        <input type="date" class="form-control"
                                               required name="manufactureDate" id="inputMFD"
                                               value="<fmt:formatDate value="${cakeStore.productionDate}" pattern="yyyy-MM-dd" />">
                                    </div>
                                </div>

                                <div class="col-sm-6">
                                    <label for="inputEXP" class="col-form-label font-size-label fw-bold">Expiration Date: </label>
                                    <div>
                                        <input type="date" class="form-control"
                                               required name="expirationDate" id="inputEXP"
                                               value="<fmt:formatDate value="${cakeStore.expirationDate}" pattern="yyyy-MM-dd" />">
                                    </div>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <div class="col-sm-4">
                                    <label for="price" class="col-form-label font-size-label fw-bold">Price: </label>
                                    <div>
                                        <input type="number" id="price" class="form-control" name="price" required value="${cakeStore.price}">
                                    </div>
                                </div>

                                <div class="col-sm-4">
                                    <label for="quantity" class="col-form-label font-size-label fw-bold">Quantity: </label>
                                    <div>
                                        <input type="number" id="quantity" class="form-control" name="quantity" required value="${cakeStore.quantity}">
                                    </div>
                                </div>

                                <div class="col-sm-4">
                                    <label for="status" class="col-form-label font-size-label fw-bold">Status: </label>
                                    <div class="col-sm-10">
                                        <select id="status" class="form-select" required name="status" aria-label="Default select example">
                                            <option value="">Select status</option>
                                            <option value="1" ${cakeStore.status == 1 ? 'selected' : ''}>ON SALES</option>
                                            <option value="0" ${cakeStore.status == 0 ? 'selected' : ''}>STOP SALES</option>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <div class="text-center mt-5">
                                <button type="submit" class="btn btn-success fs-5 px-4 me-4">Update</button>
                                <a href="/admin/store/${storeId}/list-cake" class="btn btn-secondary fs-5 px-4">Back</a>
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
<script src="/js/cake-store.js"></script>
<script src="/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="/assets/js/main.js"></script>
</body>
</html>