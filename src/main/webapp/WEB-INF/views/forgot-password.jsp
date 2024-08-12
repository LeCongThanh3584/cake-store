<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page errorPage="error.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Cake Template">
    <meta name="keywords" content="Cake, unica, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Cake | Template</title>

    <!-- Css Styles -->
    <link rel="stylesheet" href="/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="/css/flaticon.css" type="text/css">
    <link rel="stylesheet" href="/css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="/css/elegant-icons.css" type="text/css">
    <link rel="stylesheet" href="/css/owl.carousel.min.css" type="text/css">
    <link rel="stylesheet" href="/css/slicknav.min.css" type="text/css">
    <link rel="stylesheet" href="/css/style.css" type="text/css">
</head>
<body class="vh-100">
<div class="d-flex flex-column h-100">
    <jsp:include page="../common/header.jsp"></jsp:include>

    <!-- Login Begin -->
    <div class="container my-5">
        <div class="row d-flex justify-content-center">
            <div class="col-lg-6 col-md-9 col-sm-12">
                <form class="card px-5 py-5" action="/forgot-password" method="post">
                    <div class="form-data">
                        <h2 class="text-center font-weight-bold my-3 mb-4">Forgot Password Form</h2>
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
                        <a href="/register" class="ml-2">Login</a>
                    </div>
                    <div class="mt-3 d-flex justify-content-center">
                        <span>Don't have an account?</span>
                        <a href="/register" class="ml-2">Register</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- Login End -->

    <jsp:include page="../common/footer.jsp"></jsp:include>
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