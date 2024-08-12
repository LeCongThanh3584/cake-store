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

    <div class="container my-5">
        <div class="row d-flex justify-content-center">
            <div class="col-lg-6 col-md-9 col-sm-12">
                <form class="card px-5 py-5 shadow-lg" action="/forgot-password-admin" method="post">
                    <div class="form-data">
                        <h2 class="text-center font-weight-bold my-3 mb-4">Forgot Password</h2>
                        <c:if test="${not empty message}">
                            <div class="alert alert-success text-center" role="alert">
                                ${message}
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label for="exampleInputEmail" class="font-weight-bold text-secondary">Email </label>
                            <input type="email" class="form-control" id="exampleInputEmail"
                                   aria-describedby="emailHelp" placeholder="Enter username" name="email">
                            <small class="form-text text-muted"></small>
                        </div>
                        <span style="color: red">${errors}</span>
                    </div>
                    <div class="mb-3 mt-4">
                        <button type="submit" class="btn btn-dark w-100">Send</button>
                    </div>
                    <div class="mt-3 d-flex justify-content-center">
                        <span>Login:</span>
                        <a href="/sign-in" class="ml-2">Sign in</a>
                    </div>
                </form>
            </div>
        </div>
    </div>

</div>


<!-- Js Plugins -->
<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/jquery.slicknav.js"></script>
<script src="/js/owl.carousel.min.js"></script>
<script src="/js/jquery.nicescroll.min.js"></script>
<script src="/js/main.js"></script>
</body>
</html>