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
        <h1>Update category</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item fs-5"><a href="/index">Home</a></li>
                <li class="breadcrumb-item fs-5 active">Manage Stores</li>
                <li class="breadcrumb-item fs-5 active">Update category</li>
            </ol>
        </nav>
    </div><!-- End Page Title -->

    <section class="section">
        <div class="row">
            <div class="col-lg-9 mx-auto col-md-12">

                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title text-center my-4 fs-1">Update category</h5>

                        <!-- Horizontal Form -->
                        <form action="/admin/category/update?id=${ct.id}" method="post">
                            <div class="row mb-3">
                                <label for="inputCode" class="col-sm-2 col-form-label">Code</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="inputCode" name="code"
                                           value="${ct.code}">
                                    <c:if test="${not empty errors.code}">
                                        <div class="text-danger">${errors.code}</div>
                                    </c:if>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="inputName" class="col-sm-2 col-form-label">Name</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="inputName" name="name"
                                           value="${ct.name}">
                                    <c:if test="${not empty errors.name}">
                                        <div class="text-danger">${errors.name}</div>
                                    </c:if>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label for="inputDescription" class="col-sm-2 col-form-label">Description</label>
                                <div class="col-sm-10">
                                        <textarea class="form-control" id="inputDescription"
                                                  name="description">${ct.description}</textarea>
                                </div>
                            </div>
                            <div class="text-center">
                                <button type="submit" class="btn btn-success fs-5 px-4 me-4">Submit</button>
                                <a href="/admin/category/view" class="btn btn-secondary fs-5 px-4">Back</a>
                            </div>
                        </form>

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