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

                                    <div class="card">
                                        <div class="card-body">

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

                                            <div class="d-flex justify-content-between align-items-center">
                                                <h3 class="card-title fs-3">order history</h3>
                                            </div>
                                            <div class="datatable-top">
                                                <form class="datatable-search" action="/order/view" method="get">
                                                    <div class="row">
                                                        <div class="col-md-4">
                                                            <div class="input-group mb-3">
                                                                <input type="hidden" name="page" value="${currentPage}">
                                                                <input type="hidden" name="status" value="${status}">
                                                                <input class="form-control" placeholder="Search..."
                                                                       type="text" name="search"
                                                                       title="Search within table" value="${search}">
                                                                <button type="submit" class="btn btn-success">Search
                                                                </button>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-8 text-end">
                                                            <div class="btn-group" aria-label="Order States">
                                                                <a href="/order/view?page=${currentPage}&status=0&search=${search}"
                                                                   class="btn btn-warning text-white">PENDING</a>
                                                                <a href="/order/view?page=${currentPage}&status=1&search=${search}"
                                                                   class="btn btn-success text-white">CONFIRMED</a>
                                                                <a href="/order/view?page=${currentPage}&status=2&search=${search}"
                                                                   class="btn btn-info text-white">DELIVERING</a>
                                                                <a href="/order/view?page=${currentPage}&status=3&search=${search}"
                                                                   class="btn btn-primary text-white">DELIVERED</a>
                                                                <a href="/order/view?page=${currentPage}&status=4&search=${search}"
                                                                   class="btn btn-danger text-white">CANCELLED</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                            <table class="table datatable">
                                                <thead>
                                                <tr>
                                                    <th>STT</th>
                                                    <th>Order code</th>
                                                    <th>Store's name</th>
                                                    <th>Status</th>
                                                    <th>Phone</th>
                                                    <th>Total money</th>
                                                    <th>Action</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach items="${listOrder}" var="order" varStatus="i">
                                                    <tr>
                                                        <td>${i.index + 1}</td>
                                                        <td>${order.code}</td>
                                                        <td>${order.storeName}</td>
                                                        <td>
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
                                                        </td>

                                                        <td>${order.phone}</td>
                                                        <td>${order.totalMoney}</td>
                                                        <td>
                                                            <a class="btn btn-primary"
                                                               href="/order/detail?id=${order.orderId}"><i
                                                                    class="bi bi-pencil-square"></i></a>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                            <div class="datatable-bottom">
                                                <div class="datatable-info">Showing ${currentPage} to 10 of ${totalPage}
                                                    entries
                                                </div>
                                                <nav class="datatable-pagination">
                                                    <ul class="datatable-pagination-list">
                                                        <c:if test="${totalPage > 1}">
                                                            <li class="datatable-pagination-list-item ${currentPage == 1 ? 'datatable-hidden datatable-disabled' : ''}">
                                                                <button data-page="${currentPage - 1}"
                                                                        class="datatable-pagination-list-item-link"
                                                                        aria-label="Previous Page">
                                                                    <a href="/order/view?page=${currentPage - 1}&status=${status}&search=${search}">‹</a>
                                                                </button>
                                                            </li>
                                                        </c:if>

                                                        <c:forEach var="i" begin="1" end="${totalPage}">
                                                            <c:if test="${totalPage == 1 || i <= 5 || i >= totalPage - 4 || (i >= currentPage - 2 && i <= currentPage + 2)}">
                                                                <li class="datatable-pagination-list-item ${i == currentPage ? 'datatable-active' : ''}">
                                                                    <button data-page="${i}"
                                                                            class="datatable-pagination-list-item-link"
                                                                            aria-label="Page ${i}">
                                                                        <a href="/order/view?page=${i}&status=${status}&search=${search}">${i}</a>
                                                                    </button>
                                                                </li>
                                                            </c:if>
                                                            <c:if test="${i == currentPage + 3 && totalPage > 5}">
                                                                <li class="datatable-pagination-list-item datatable-ellipsis datatable-disabled">
                                                                    <button class="datatable-pagination-list-item-link">
                                                                        …
                                                                    </button>
                                                                </li>
                                                            </c:if>
                                                        </c:forEach>

                                                        <c:if test="${totalPage > 1}">
                                                            <li class="datatable-pagination-list-item ${currentPage == totalPage ? 'datatable-hidden datatable-disabled' : ''}">
                                                                <button data-page="${currentPage + 1}"
                                                                        class="datatable-pagination-list-item-link"
                                                                        aria-label="Next Page">
                                                                    <a href="/order/view?page=${currentPage + 1}&status=${status}&search=${search}">›</a>
                                                                </button>
                                                            </li>
                                                        </c:if>
                                                    </ul>
                                                </nav>
                                            </div>
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