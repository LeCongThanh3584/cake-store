<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page errorPage="../error.jsp" %>
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


    <!-- Vendor CSS Files -->
    <link href="/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="/assets/vendor/simple-datatables/style.css" rel="stylesheet">
    <link href="/assets/css/style.css" rel="stylesheet">
</head>
<body class="vh-100">
<div class="d-flex flex-column h-100">
    <jsp:include page="../../common/header.jsp"></jsp:include>

    <!-- Login Begin -->
    <section class="py-5 bg-light">
        <div class="container">
            <div class="row">
                <div class="col-lg-2 col-xl-2">
                    <nav class="nav flex-lg-column w-100 d-flex nav-pills mb-4">
                        <a class="nav-link active my-0" href="/user-info"
                        ><p class="pb-0 mb-0" style="width: 100px">Account</p></a
                        >
                        <a class="nav-link my-0 bg-light" href="/order/view"
                        ><p class="pb-0 mb-0" style="width: 100px">Order</p></a
                        >
                        <a class="nav-link my-0 bg-light" href="/address/view"
                        ><p class="pb-0 mb-0" style="width: 100px">Address</p></a
                        >
                        <a class="nav-link my-0 bg-light" href="/update-password"
                        ><p class="pb-0 mb-0" style="width: 100px">Change Password</p></a
                        >
                        <a class="nav-link my-0 bg-light" href="/logout"
                        ><p class="pb-0 mb-0" style="width: 100px">Logout</p></a
                        >
                    </nav>
                </div>
                <main class="col-lg-10 col-xl-10">
                    <div class="card p-4 mb-0 shadow-0 border">
                        <div class="content-body">
                            <div class="card">
                                <div class="card-body">
                                    <div class="tab-pane fade show active profile-overview" id="profile-overview">
                                        <h5 class="card-title">Profile Details</h5>
                                        <div class="row">
                                            <div class="col-lg-3 col-md-4 label ">Full Name</div>
                                            <div class="col-lg-9 col-md-8">Phạm Thanh Sơn</div>
                                        </div>

                                        <div class="row">
                                            <div class="col-lg-3 col-md-4 label">Username / email</div>
                                            <div class="col-lg-9 col-md-8">phamson191203@gmail.com</div>
                                        </div>

                                        <div class="row">
                                            <div class="col-lg-3 col-md-4 label">Role</div>
                                            <div class="col-lg-9 col-md-8">USER</div>
                                        </div>
                                        
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>


                </main>
            </div>
        </div>
    </section>
    <link rel="stylesheet" href="/user/css/plugin/mdb.min.css"/>
    <jsp:include page="../../common/footer.jsp"></jsp:include>
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