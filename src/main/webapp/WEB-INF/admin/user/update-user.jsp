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

    <!-- Favicons -->
    <link href="/assets/img/favicon.png" rel="icon">
    <link href="/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

<%--    custom css--%>
    <link rel="stylesheet" href="/css/user.css">
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
        <h1 class="fw-bold">Update user</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item fs-5"><a href="/admin">Home</a></li>
                <li class="breadcrumb-item fs-5 active">Manage Users</li>
                <li class="breadcrumb-item fs-5 active">Update user</li>
            </ol>
        </nav>
    </div><!-- End Page Title -->

    <section class="section">
        <div class="row">
            <div class="col-lg-9 col-md-12 mx-auto">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title text-center my-4 fs-1">Update user</h5>

                        <!-- Horizontal Form -->
                        <form action="/admin/update-user" method="post" id="formSubmitCreateAndUpdateUser">
                            <input type="text" value="${user.idUser}" hidden name="id">
                            <div class="row mb-3">
                                <label for="inputText" class="col-sm-2 col-form-label fw-bold">UserName: </label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" required readonly name="userName" value="${user.username}" id="inputText">
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label for="inputPassword" class="col-sm-2 col-form-label fw-bold">Password: </label>
                                <div class="col-sm-10">
                                    <input type="password" class="form-control mb-2" name="password" placeholder="Password" id="inputPassword">
                                    <p style="font-style: italic; opacity: 0.5" class="m-0">If you do not update your password, please leave it blank</p>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <label for="inputFullname" class="col-sm-2 col-form-label fw-bold">FullName: </label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" placeholder="Fullname" name="fullName" required value="${user.fullName}" id="inputFullname">
                                </div>
                            </div>

                            <div class="d-flex mb-3 justify-content-between">
                                <div class="p-2" style="flex: 1;">
                                    <label class="col-form-label fw-bold" for="selectRole">Role: </label>
                                    <div>
                                        <select class="form-select" disabled name="role" aria-label="Default select example" id="selectRole">
                                            <option value="0" ${user.role == 0 ? 'selected' : ''} >SUPER ADMIN</option>
                                            <option value="1" ${user.role == 1 ? 'selected' : ''} >ADMIN</option>
                                            <option value="2" ${user.role == 2 ? 'selected' : ''} >SHIPPER</option>
                                            <option value="3" ${user.role == 3 ? 'selected' : ''} >USER</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="p-2" style="flex: 3; display: ${user.role == 1 ? 'block' : 'none'}" id="listStore">
                                    <label class="fw-bold col-form-label fw-bold">Manage Store*: </label>
                                    <div class="dropdown">
                                        <button class="btn btn-secondary w-100 bg-light text-dark" type="button" id="dropdownMenuSelectCake" data-bs-toggle="dropdown" aria-expanded="false">
                                            <div class="row w-100 mx-auto" style="font-size: 11px;">
                                                <div class="col-2 p-0">
                                                    <p class="fw-bold mb-2">Code: </p> <span>${user.codeStore}</span>
                                                </div>
                                                <div class="col-3 p-0">
                                                    <p class="fw-bold mb-2">Name: </p> <span>${user.storeName}</span>
                                                </div>
                                                <div class="col-3 p-0">
                                                    <p class="fw-bold mb-2">Phone: </p> <span>${user.phone}</span>
                                                </div>
                                                <div class="col-4 p-0">
                                                    <p class="fw-bold mb-2">Address: </p> <span>${user.address}</span>
                                                </div>
                                            </div>
                                        </button>
                                        <div class="dropdown-menu w-100 z-index-0" aria-labelledby="dropdownMenuSelectCake">
                                            <div class="input-group p-2">
                                                <span class="input-group-text" id="basic-addon1"><i class="bi bi-search"></i></span>
                                                <input type="text" class="form-control form-control-focus" id="searchInput" placeholder="Search...">
                                            </div>
                                            <ul class="text-center content-select-store p-1">
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
                                    <input type="text" name="storeId" hidden id="storeId" value="${user.idStore}">
                                    <p class="text-danger mb-0 mt-2" style="display: none" id="textMessage">Please choose store manage!</p>
                                </div>

                                <div class="p-2" style="flex: 1;">
                                    <label class="col-form-label fw-bold" for="selectStatus" style="padding-right: unset">Status: </label>
                                    <div>
                                        <select class="form-select" name="status" required aria-label="Default select example" id="selectStatus">
                                            <option selected value="">Select status</option>
                                            <option value="1" ${user.status == 1 ? 'selected' : ''}>ACTIVATE</option>
                                            <option value="0" ${user.status == 0 ? 'selected' : ''}>DISABLE</option>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <div class="text-center py-3 mt-5">
                                <button type="submit" class="btn btn-success fs-5 px-4 me-4">Update</button>
                                <a href="/admin/users" class="btn btn-secondary fs-5 px-4">Back</a>
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
<script src="/js/user.js"></script>
</body>
</html>