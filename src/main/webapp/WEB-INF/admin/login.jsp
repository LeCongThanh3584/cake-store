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
<body class="vh-100 bg-light">

<div class="d-flex flex-column h-100">
    <!-- Login Begin -->
    <div class="container my-5">
        <div class="row d-flex justify-content-center">
            <div class="col-lg-6 col-md-9 col-sm-12">
                <form class="card px-5 py-5 shadow-lg" action="/sign-in" method="post">
                    <div class="form-data">
                        <h2 class="text-center font-weight-bold my-3 mb-4">Sign In</h2>
                        <div class="form-group">
                            <label for="exampleInputEmail" class="fw-bold text-secondary mb-1">Email </label>
                            <input type="email" class="form-control" id="exampleInputEmail"
                                   aria-describedby="emailHelp" placeholder="Enter username" name="email" required>
                            <small class="form-text text-muted"></small>
                        </div>
                    </div>
                    <div class="form-group mt-3">
                        <label for="exampleInputPassword" class="fw-bold text-secondary mb-1">Password</label>
                        <input type="password" class="form-control" id="exampleInputPassword"
                               aria-describedby="passwordHelp" placeholder="Enter password" name="password" required>
                        <small class="form-text text-muted"></small>
                    </div>
                    <span style="color: red">${errors}</span>
                    <div class="mb-3 mt-4">
                        <button class="btn btn-dark w-100">Login</button>
                    </div>
                    <div class="mt-3 d-flex justify-content-center">
                        <span>Forgot your password? </span>
                        <a href="/forgot-password-admin" class="ml-2">forgot password</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i
        class="bi bi-arrow-up-short"></i></a>
<script src="/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="/assets/js/main.js"></script>
</body>
</html>