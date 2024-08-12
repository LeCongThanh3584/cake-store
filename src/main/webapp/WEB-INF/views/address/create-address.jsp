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
    <link href="/assets/img/favicon.png" rel="icon">
    <link href="/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

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
                        <a class="nav-link  bg-light my-0" href="/user-info"
                        ><p class="pb-0 mb-0" style="width: 100px">Account</p></a
                        >
                        <a class="nav-link my-0 bg-light" href="#customer-order "
                        ><p class="pb-0 mb-0" style="width: 100px">Order</p></a
                        >
                        <a class="nav-link my-0 active" href="/address/view"
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
                            <div class="row">
                                <div class="col-lg-12 mx-auto">
                                    <div class="card">
                                        <div class="card-body">
                                            <h5 class="card-title text-center my-4 fs-1">Create address</h5>
                                            <form class="row g-3" method="post" action="/address/create">
                                                <div class="col-md-6">
                                                    <label for="inputName" class="form-label">Name</label>
                                                    <input type="text" class="form-control" id="inputName"
                                                           name="name">
                                                    <c:if test="${not empty errors.name}">
                                                        <div class="text-danger">${errors.name}</div>
                                                    </c:if>
                                                </div>
                                                <div class="col-md-6">
                                                    <label for="province" class="form-label">province</label>
                                                    <select id="province" class="form-select" name="idCategory"
                                                            onchange="getDistrictFromIdProvince(event, this)" required>
                                                        <option value="Hà Nội" selected>Hà Nội</option>
                                                    </select>
                                                    <input type="text" hidden name="province" id="provinceText">
                                                </div>
                                                <div class="col-md-4">
                                                    <label for="district" class="form-label">District</label>
                                                    <select id="district" class="form-select" name="idCategory"
                                                            onchange="getWardFromIdDistrict(event, this)" required>
                                                    </select>
                                                    <input type="text" hidden name="district" id="districtText">
                                                </div>
                                                <div class="col-md-4">
                                                    <label for="ward" class="form-label">Ward</label>
                                                    <select class="form-select" name="idCategory" id="ward"
                                                            onchange="getWardSelected(this)" required>
                                                    </select>
                                                    <input type="text" hidden name="ward" id="wardText">
                                                </div>
                                                <div class="col-md-4">
                                                    <label for="inputSize" class="form-label">Phone Number</label>
                                                    <input type="text" class="form-control" id="inputSize"
                                                           name="phone">
                                                    <c:if test="${not empty errors.phone}">
                                                        <div class="text-danger">${errors.phone}</div>
                                                    </c:if>
                                                </div>

                                                <div class="text-center">
                                                    <button type="submit" class="btn btn-success fs-5 px-4 me-4">
                                                        Submit
                                                    </button>
                                                    <a href="/address/view"
                                                       class="btn btn-secondary fs-5 px-4">Back</a>
                                                </div>
                                            </form>
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

<script src="/js/address.js"></script>
<!-- Js Plugins -->
<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/jquery.slicknav.js"></script>
<script src="/js/owl.carousel.min.js"></script>
<script src="/js/jquery.nicescroll.min.js"></script>
<script src="/js/main.js"></script>
</body>
</html>