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
                        <a class="nav-link my-0 active" href="/order/view"
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
                            <div class="row">
                                <div class="col-lg-12 mx-auto">
                                    <c:if test="${not empty sessionScope.response}">
                                        <div class="toast-container position-fixed bottom-0 end-0 p-3">
                                            <div id="liveToast" class="toast" role="alert" aria-live="assertive"
                                                 aria-atomic="true">
                                                <div class="toast-header">
                                                    <strong class="me-auto">sweetk cake</strong>
                                                    <button type="button" class="btn-close"
                                                            data-bs-dismiss="toast"
                                                            aria-label="Close"></button>
                                                </div>
                                                <div class="toast-body">
                                                    <strong>Notice:</strong> ${sessionScope.response}
                                                </div>
                                            </div>
                                        </div>
                                        <c:remove var="response" scope="session"/>
                                    </c:if>
                                    <div class="order-history card shadow-0 border">
                                        <div class="justify-content-between align-items-center card-body">
                                            <h3 class="card-title fs-3">history</h3>
                                            <hr>
                                            <div class="progress">
                                                <div id="progress-bar" class="progress-bar bg-success progress-bar-striped progress-bar-animated"
                                                     role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100">
                                                    <span id="progress-label">0%</span>
                                                </div>
                                            </div>
                                            <div id="statu" data-statu="${order.status}" style="display: none;"></div>
                                        </div>
                                        <div class="accordion" id="accordionExample">
                                            <div class="accordion-item">
                                                <h2 class="accordion-header" id="headingOne">
                                                    <button class="accordion-button" type="button"
                                                            data-bs-toggle="collapse" data-bs-target="#collapseOne"
                                                            aria-expanded="true" aria-controls="collapseOne">
                                                        Open to see detail
                                                    </button>
                                                </h2>
                                                <div id="collapseOne" class="accordion-collapse collapse show"
                                                     aria-labelledby="headingOne" data-bs-parent="#accordionExample">
                                                    <div class="accordion-body">
                                                        <table class="table datatable">
                                                            <thead>
                                                            <tr>
                                                                <th>STT</th>
                                                                <th>created at</th>
                                                                <th>created by</th>
                                                                <th>status</th>
                                                                <th>description</th>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                            <c:forEach items="${orderHistory}" var="history"
                                                                       varStatus="i">
                                                                <tr>
                                                                    <td>${i.index + 1}</td>
                                                                    <td>${history.createdBy}</td>
                                                                    <td>${history.createdBy}</td>
                                                                    <td>
                                                            <span class="badge
                                                                <c:choose>
                                                                    <c:when test="${history.status == 0}">
                                                                        bg-warning
                                                                    </c:when>
                                                                    <c:when test="${history.status == 1}">
                                                                        bg-success
                                                                    </c:when>
                                                                    <c:when test="${history.status == 2}">
                                                                        bg-info
                                                                    </c:when>
                                                                    <c:when test="${history.status == 3}">
                                                                        bg-primary
                                                                    </c:when>
                                                                    <c:when test="${history.status == 4}">
                                                                        bg-danger
                                                                    </c:when>
                                                                </c:choose>
                                                                text-white">
                                                                <c:choose>
                                                                    <c:when test="${history.status == 0}">
                                                                        PENDING
                                                                    </c:when>
                                                                    <c:when test="${history.status == 1}">
                                                                        CONFIRMED
                                                                    </c:when>
                                                                    <c:when test="${history.status == 2}">
                                                                        DELIVERING
                                                                    </c:when>
                                                                    <c:when test="${history.status == 3}">
                                                                        DELIVERED
                                                                    </c:when>
                                                                    <c:when test="${history.status == 4}">
                                                                        CANCELLED
                                                                    </c:when>
                                                                </c:choose>
                                                            </span>
                                                                    </td>
                                                                    <td>${history.description}</td>
                                                                </tr>
                                                            </c:forEach>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>


                                    <div class="order-history card shadow-0 border">
                                        <div class="justify-content-between align-items-center card-body">
                                            <h3 class="d-flex card-title fs-3">Information</h3>
                                            <hr>
                                            <div class="row">
                                                <div class="col-4">
                                                    <p class="mb-1 font-weight-bold d-inline">store's name:</p> <span
                                                        class="d-inline">${order.storeName}</span>
                                                </div>
                                                <div class="col-4">

                                                    <p class="mb-1 font-weight-bold d-inline">admin:</p>
                                                    <c:if test="${order.adminName != null}">
                                                        <span class="d-inline">${order.adminName}</span>
                                                    </c:if>
                                                </div>
                                                <div class="col-4">
                                                    <p class="mb-1 font-weight-bold d-inline">shipper:</p>
                                                    <c:if test="${order.shipperName != null}">
                                                        <span
                                                                class="d-inline">${order.shipperName}</span>
                                                    </c:if>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-4">
                                                    <p class="mb-1 font-weight-bold d-inline">code:</p> <span
                                                        class="d-inline">${order.code}</span>
                                                </div>
                                                <div class="col-4">
                                                    <c:if test="${order.reciver != null}">
                                                        <p class="mb-1 font-weight-bold d-inline">reciever:</p> <span
                                                            class="d-inline">${order.reciver}</span>
                                                    </c:if>
                                                </div>
                                                <div class="col-4">
                                                    <c:if test="${order.phone != null}">
                                                        <p class="mb-1 font-weight-bold d-inline">phone:</p> <span
                                                            class="d-inline">${order.phone}</span>
                                                    </c:if>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <%--                                                <div class="col-4">--%>
                                                <%--                                                    <p class="mb-1 font-weight-bold d-inline">address:</p> <span--%>
                                                <%--                                                        class="d-inline">${order.address}</span>--%>
                                                <%--                                                </div>--%>
                                                <div class="col-4">
                                                    <c:if test="${order.totalMoney != null}">
                                                        <p class="mb-1 font-weight-bold d-inline">total money:</p> <span
                                                            class="d-inline">${order.totalMoney}</span>
                                                    </c:if>
                                                </div>
                                                <div class="col-4">
                                                    <c:if test="${order.phone != null}">
                                                        <p class="mb-1 font-weight-bold d-inline">address:</p> <span
                                                            class="d-inline">${order.address}</span>
                                                    </c:if>
                                                </div>
                                                <div class="col-4">
                                                    <p class="mb-1 font-weight-bold d-inline">status:</p>
                                                    <span class="badge
                                                                <c:choose>
                                                                    <c:when test="${order.status == 0}">
                                                                        bg-warning
                                                                    </c:when>
                                                                    <c:when test="${order.status == 1}">
                                                                        bg-success
                                                                    </c:when>
                                                                    <c:when test="${order.status == 2}">
                                                                        bg-info
                                                                    </c:when>
                                                                    <c:when test="${order.status == 3}">
                                                                        bg-primary
                                                                    </c:when>
                                                                    <c:when test="${order.status == 4}">
                                                                        bg-danger
                                                                    </c:when>
                                                                </c:choose>
                                                                text-white">
                                                                <c:choose>
                                                                    <c:when test="${order.status == 0}">
                                                                        PENDING
                                                                    </c:when>
                                                                    <c:when test="${order.status == 1}">
                                                                        CONFIRMED
                                                                    </c:when>
                                                                    <c:when test="${order.status == 2}">
                                                                        DELIVERING
                                                                    </c:when>
                                                                    <c:when test="${order.status == 3}">
                                                                        DELIVERED
                                                                    </c:when>
                                                                    <c:when test="${order.status == 4}">
                                                                        CANCELLED
                                                                    </c:when>
                                                                </c:choose>
                                                            </span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="order-history card shadow-0 border">
                                        <div class="justify-content-between align-items-center card-body">
                                            <h3 class="card-title fs-3">product</h3>
                                            <hr>
                                            <div class="d-flex card text-justify shadow-0 border product-card p-3"
                                                 style="margin-bottom: 10px">
                                                <div class="row  text-center d-flex align-items-center">
                                                    <div class="col-2 font-weight-bold">
                                                        Code
                                                    </div>
                                                    <div class="col-2 font-weight-bold">
                                                        Name
                                                    </div>
                                                    <div class="col-2 font-weight-bold">
                                                        Image
                                                    </div>
                                                    <div class="col-2 font-weight-bold">
                                                        Quantity
                                                    </div>
                                                    <div class="col-2 font-weight-bold">
                                                        Price
                                                    </div>
                                                    <div class="col-2 font-weight-bold">
                                                        Expiration date
                                                    </div>
                                                </div>
                                            </div>
                                            <c:forEach var="pr" items="${orderDetail}">
                                                <div class="d-flex card text-justify shadow-0 border product-card p-3">
                                                    <div class="row  text-center d-flex align-items-center">
                                                        <div class="col-2">
                                                                ${pr.cakeCode}
                                                        </div>
                                                        <div class="col-2">
                                                                ${pr.name}
                                                        </div>
                                                        <div class="col-2">
                                                            <img src="${pr.cakeImage}">
                                                        </div>
                                                        <div class="col-2">
                                                                ${pr.quantity}
                                                        </div>
                                                        <div class="col-2">
                                                                ${pr.price}<span>.đ</span>
                                                        </div>
                                                        <div class="col-2">
                                                                ${pr.expirationDate}
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>

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


<script>
    document.addEventListener('DOMContentLoaded', function () {
        var statuElement = document.getElementById('statu');
        var statu = parseInt(statuElement.getAttribute('data-statu'));
        statu += 1;

        var progressBar = document.getElementById('progress-bar');
        var progressLabel = document.getElementById('progress-label');

        var progress = statu * 20;

        progressBar.style.width = progress + '%';
        progressBar.setAttribute('aria-valuenow', progress);

        if (statu == 1) {
            progressBar.classList.add('bg-warning');
            progressLabel.textContent = "PENDING";
        } else if (statu == 2) {
            progressBar.classList.add('bg-success');
            progressLabel.textContent = "CONFIRMED";
        } else if (statu == 3) {
            progressBar.classList.add('bg-info');
            progressLabel.textContent = "DELIVERING";
        } else if (statu == 4) {
            progressBar.classList.add('bg-primary');
            progressLabel.textContent = "DELIVERED";
        } else if (statu == 5) {
            progressBar.classList.add('bg-danger');
            progressLabel.textContent = "CANCELLED";
        }
    });
</script>
<!-- Js Plugins -->
<!-- Bootstrap Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-x6FJH6A/Y5fou6+b8k1aLOfFF24MLCylC+qj9BjOERuZmJw/5+gZ47/ZjWYRSW0P"
        crossorigin="anonymous"></script>
<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/jquery.slicknav.js"></script>
<script src="/js/owl.carousel.min.js"></script>
<script src="/js/jquery.nicescroll.min.js"></script>
<script src="/js/main.js"></script>
</body>
</html>