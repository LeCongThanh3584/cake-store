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

    <%--    custom-css--%>
    <link rel="stylesheet" href="/css/user.css">

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
        <h1 class="fw-bold">Update address</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item fs-5"><a href="/admin">Home</a></li>
                <li class="breadcrumb-item fs-5 active">User</li>
                <li class="breadcrumb-item fs-5 active">Address</li>
                <li class="breadcrumb-item fs-5 active">Update address</li>
            </ol>
        </nav>
    </div><!-- End Page Title -->

    <section class="section">
        <div class="row">
            <div class="col-lg-9 col-md-12 mx-auto">
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

                        <h5 class="card-title text-center my-4 fs-1">Update address</h5>

                        <!-- Horizontal Form -->
                        <form action="/admin/update-address" method="post">
                            <input type="text" name="addressId" hidden value="${addressUpdate.id}">
                            <input type="text" name="userId" hidden value="${userId}">
                            <div class="row">
                                <div class="col-sm-4">
                                    <label class="col-form-label fw-bold" for="nameAddress">Address type name: </label>
                                    <input type="text" id="nameAddress" name="addressType" class="form-control mt-1" value="${addressUpdate.name}" placeholder="Example: company, home,...">
                                </div>

                                <div class="col-sm-4">
                                    <div>
                                        <label class="col-form-label fw-bold me-1" for="province">Province: </label>
                                        <select class="form-select" aria-label="Default select example" id="province" onchange="getDistrictFromIdProvince(event, this)">
                                            <c:forEach items="${listProvince}" var="province">
                                                <option value="${province.id}" ${province.full_name == addressUpdate.province ? 'selected' : ''} >${province.full_name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <input type="text" class="form-control mt-1" readonly hidden name="province" id="provinceText" value="${addressUpdate.province}">
                                </div>

                                <div class="col-sm-4">
                                    <div>
                                        <label class="col-form-label fw-bold me-1" for="district">District: </label>
                                        <select class="form-select" aria-label="Default select example" id="district" onchange="getWardFromIdDistrict(event, this)">
                                            <c:forEach items="${listDistrict}" var="district">
                                                <option value="${district.id}" ${district.full_name == addressUpdate.district ? 'selected' : ''} >${district.full_name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <input type="text" class="form-control mt-1" readonly hidden name="district" id="districtText" value="${addressUpdate.district}">
                                </div>
                            </div>

                            <div class="row mt-3">
                                <div class="col-sm-6">
                                   <div>
                                       <label class="col-form-label fw-bold me-1" for="ward">Ward: </label>
                                       <select class="form-select" aria-label="Default select example" id="ward" onchange="getWardSelected(this)">
                                           <c:forEach items="${listWard}" var="ward">
                                               <option value="${ward.id}" ${ward.full_name == addressUpdate.ward ? 'selected' : ''} >${ward.full_name}</option>
                                           </c:forEach>
                                       </select>
                                   </div>
                                    <input type="text" class="form-control mt-1" hidden readonly name="ward" id="wardText" value="${addressUpdate.ward}">
                                </div>

                                <div class="col-sm-6">
                                    <label class="col-form-label fw-bold" for="phoneNumber">Phone Number: </label>
                                    <input type="text" id="phoneNumber" name="phoneNumber" class="form-control mt-1" value="${addressUpdate.phone}" placeholder="phone">
                                </div>
                            </div>

                            <div class="text-center py-3 mt-5">
                                <button type="submit" class="btn btn-success fs-5 px-4 me-4">Update</button>
                                <a href="/admin/user/${userId}/view-address" class="btn btn-secondary fs-5 px-4">Back</a>
                            </div>
                        </form><!-- End Horizontal Form -->

                    </div>
                </div>

            </div>
        </div>
    </section>

</main><!-- End #main -->

<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>
<script src="/js/address.js"></script>
<script src="/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="/assets/js/main.js"></script>
</body>
</html>