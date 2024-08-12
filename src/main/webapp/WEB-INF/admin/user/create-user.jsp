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
            <h1 class="fw-bold">Add new user</h1>
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item fs-5"><a href="/admin">Home</a></li>
                    <li class="breadcrumb-item fs-5 active">Manage Users</li>
                    <li class="breadcrumb-item fs-5 active">Add new user</li>
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

                            <h5 class="card-title text-center my-4 fs-1">Add new user</h5>

                            <!-- Horizontal Form -->
                            <form action="/admin/add-new-user" method="post" id="formSubmitCreateAndUpdateUser">
                                <div class="row mb-3">
                                    <label for="inputText" class="col-sm-2 col-form-label fw-bold">UserName: </label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" required name="userName" placeholder="Username" id="inputText">
                                    </div>
                                </div>

                                <div class="row mb-3">
                                    <label for="inputPassword" class="col-sm-2 col-form-label fw-bold">Password: </label>
                                    <div class="col-sm-10">
                                        <input type="password" class="form-control" required name="password" placeholder="password" id="inputPassword">
                                    </div>
                                </div>

                                <div class="row mb-3">
                                    <label for="inputFullname" class="col-sm-2 col-form-label fw-bold">FullName: </label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" required name="fullName" placeholder="FullName" id="inputFullname">
                                    </div>
                                </div>

                                <div class="d-flex mb-3 justify-content-between">
                                    <div class="p-2" style="flex: 1">
                                        <label class="col-form-label fw-bold" for="selectRole">Role: </label>
                                        <div>
                                            <select class="form-select" required name="role"
                                                    aria-label="Default select example" id="selectRole" onchange="checkSelectRole(this)">
                                                <option value="">Select Role</option>
                                                <option value="1">ADMIN</option>
                                                <option value="2">SHIPPER</option>
                                                <option value="3">USER</option>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="p-2" style="flex: 3; display: none" id="listStore">
                                        <label class="fw-bold col-form-label fw-bold">Manage Store*: </label>
                                        <div class="dropdown">
                                            <button class="btn btn-secondary w-100 bg-light text-dark" type="button"
                                                    id="dropdownMenuSelectCake" data-bs-toggle="dropdown"
                                                    aria-expanded="false">
                                                Select store <i class="bi bi-chevron-down"></i>
                                            </button>
                                            <div class="dropdown-menu w-100 z-index-0" aria-labelledby="dropdownMenuSelectCake">
                                                <div class="input-group p-2">
                                                    <span class="input-group-text" id="basic-addon1"><i class="bi bi-search"></i></span>
                                                    <input type="text" class="form-control form-control-focus" id="searchInput" placeholder="Search...">
                                                </div>
                                                <ul class="text-center content-select-store">
                                                    <c:forEach items="${storeList}" var="store">
                                                        <li class="dropdown-item hover p-1" data-value="${store.id}" style="border-bottom: 1px solid rgb(128,128,128);">
                                                            <div class="row w-100 mx-auto" style="font-size: 11px;">
                                                                <div class="col-2 p-0">
                                                                    <p class="fw-bold mb-2">Code: </p> <span>${store.code}</span>
                                                                </div>
                                                                <div class="col-3 p-0">
                                                                    <p class="fw-bold mb-2">Name: </p> <span>${store.name}</span>
                                                                </div>
                                                                <div class="col-3 p-0">
                                                                    <p class="fw-bold mb-2">Phone: </p> <span>${store.phone}</span>
                                                                </div>
                                                                <div class="col-4 p-0">
                                                                    <p class="fw-bold mb-2">Address: </p> <span>${store.address}</span>
                                                                </div>
                                                            </div>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </div>
                                        <input type="text" name="storeId" hidden id="storeId">
                                        <p class="text-danger mb-0 mt-2" style="display: none" id="textMessage">Please choose manage store!</p>
                                    </div>

                                    <div class="p-2" style="flex: 1">
                                        <label class="col-form-label fw-bold me-un" style="padding-right: unset" for="selectStatus">Status: </label>
                                        <div>
                                            <select class="form-select" required name="status" aria-label="Default select example" id="selectStatus">
                                                <option value="">Select status</option>
                                                <option value="1">ACTIVATE</option>
                                                <option value="0">DISABLE</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>

                                <div class="mb-3 mt-4">
                                    <span for="inputFullname" data-bs-toggle="collapse" data-bs-target="#address" class="col-form-label hover fw-bold">
                                        Address (Optional): <i class="bi bi-arrow-down-circle-fill link-success"></i>
                                    </span>
                                    <div id="address" class="collapse padding-form-address mt-2">
                                        <div class="row">
                                            <div class="col-sm-4">
                                                <label class="col-form-label fw-bold" for="nameAddress">Address type name: </label>
                                                <input type="text" id="nameAddress" name="addressType" class="form-control" placeholder="Example: company, home,...">
                                            </div>

                                            <div class="col-sm-4">
                                                <label class="col-form-label fw-bold" for="province">Province: </label>
                                                <select class="form-select" aria-label="Default select example" id="province" onchange="getDistrictFromIdProvince(event, this)">
                                                    <option value="Hà Nội" selected>Hà Nội</option>
    <%--                                                content from js--%>
                                                </select>
                                                <input type="text" hidden name="province" id="provinceText">
                                            </div>

                                            <div class="col-sm-4">
                                                <label class="col-form-label fw-bold" for="district">District: </label>
                                                <select class="form-select" aria-label="Default select example" id="district" onchange="getWardFromIdDistrict(event, this)">
    <%--                                                content from js--%>
                                                </select>
                                                <input type="text" hidden name="district" id="districtText">
                                            </div>
                                        </div>

                                        <div class="row mt-3">
                                            <div class="col-sm-6">
                                                <label class="col-form-label fw-bold" for="ward">Ward: </label>
                                                <select class="form-select" aria-label="Default select example" id="ward" onchange="getWardSelected(this)">
                                                    <%--       content from js--%>
                                                </select>
                                                <input type="text" hidden name="ward" id="wardText">
                                            </div>

                                            <div class="col-sm-6">
                                                <label class="col-form-label fw-bold" for="phoneNumber">Phone Number: </label>
                                                <input type="text" id="phoneNumber" name="phoneNumber" class="form-control" placeholder="phone">
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="text-center py-3 mt-5">
                                    <button type="submit" class="btn btn-success fs-5 px-4 me-4">Create</button>
                                    <a href="/admin/users" class="btn btn-secondary fs-5 px-4">Back</a>
                                </div>
                            </form><!-- End Horizontal Form -->

                        </div>
                    </div>

                </div>
            </div>
        </section>

    </main><!-- End #main -->

    <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>
    <script src="/js/user.js"></script>
    <script src="/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="/assets/js/main.js"></script>
    </body>
</html>