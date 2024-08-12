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
<jsp:include page="../common/admin-header.jsp"></jsp:include>
<jsp:include page="../common/admin-sidebar.jsp"></jsp:include>

<main id="main" class="main">
    <div>
        <h1>Dashboard</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item fs-5"><a href="/index">Home</a></li>
                <li class="breadcrumb-item fs-5 active">Dashboard</li>
            </ol>
        </nav>
    </div><!-- End Page Title -->

    <section class="section">
        <div class="row align-items-top">
            <div class="col-lg-6">
                <div class="card" style="">
                    <div href="#" class="card-body p-4 pb-2">
                        <h2 class="card-subtitle mb-2 text-secondary-emphasis">Manage Stores</h2>
                        <p class="card-text text-secondary-emphasis">Some quick example text to build on the card title
                            and make up the bulk of the card's content.</p>
                        <div class="d-flex">
                            <p class="card-text me-2"><a href="/admin/add-store" class="btn btn-primary">Add Store</a></p>
                            <p class="card-text me-2"><a href="/admin/stores" class="btn btn-info text-white">View Stores</a></p>
                        </div>
                    </div>
                </div>
                <div class="card" style="">
                    <div href="#" class="card-body p-4 pb-2">
                        <h2 class="card-subtitle mb-2 text-secondary-emphasis">Manage Products</h2>
                        <p class="card-text text-secondary-emphasis">Some quick example text to build on the card title
                            and make up the bulk of the card's content.</p>
                        <div class="d-flex">
                            <p class="card-text me-2"><a href="#" class="btn btn-primary">Add Products</a></p>
                            <p class="card-text me-2"><a href="#" class="btn btn-info text-white">View Products</a></p>
                        </div>
                    </div>
                </div>

            </div>

            <div class="col-lg-6">
                <div class="card" style="">
                    <div href="#" class="card-body p-4 pb-2">
                        <h2 class="card-subtitle mb-2 text-secondary-emphasis">Manage Users</h2>
                        <p class="card-text text-secondary-emphasis">Some quick example text to build on the card title
                            and make up the bulk of the card's content.</p>
                        <div class="d-flex">
                            <p class="card-text me-2"><a href="/admin/add-new-user" class="btn btn-primary">Add User</a></p>
                            <p class="card-text me-2"><a href="/admin/users" class="btn btn-info text-white">View Users</a></p>
                        </div>
                    </div>
                </div>
            </div>

            <h1>Store option</h1>
            <div class="col-lg-6">
                <div class="card" style="">
                    <div href="#" class="card-body p-4">
                        <h2 class="card-subtitle mb-2 text-secondary-emphasis">Manage Input/Output</h2>
                        <p class="card-text text-secondary-emphasis">Some quick example text to build on the card title
                            and make up the bulk of the card's content.</p>
                        <div class="d-flex">
                            <p class="card-text me-2"><a href="#" class="btn btn-danger">Add</a></p>
                            <p class="card-text me-2"><a href="#" class="btn btn-warning text-white">View</a></p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="card" style="">
                    <div href="#" class="card-body p-4">
                        <h2 class="card-subtitle mb-2 text-secondary-emphasis">Manage Input/Output</h2>
                        <p class="card-text text-secondary-emphasis">Some quick example text to build on the card title
                            and make up the bulk of the card's content.</p>
                        <div class="d-flex">
                            <p class="card-text me-2"><a href="#" class="btn btn-danger">Add</a></p>
                            <p class="card-text me-2"><a href="#" class="btn btn-warning text-white">View</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i
        class="bi bi-arrow-up-short"></i></a>
<script src="/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="/assets/js/main.js"></script>
</body>
</html>