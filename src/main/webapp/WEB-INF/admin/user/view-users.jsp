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

    <!-- Vendor CSS Files -->
    <link href="/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="/assets/vendor/simple-datatables/style.css" rel="stylesheet">
    <link href="/assets/css/style.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="../../common/admin-header.jsp"></jsp:include>
    <jsp:include page="../../common/admin-sidebar.jsp"></jsp:include>

    <main id="main" class="main">
        <div>
            <h1 class="fw-bold">View users</h1>
            <nav>
                <ol class="breadcrumb">
                    <li class="breadcrumb-item fs-5"><a href="/admin">Home</a></li>
                    <li class="breadcrumb-item fs-5 active">Manage Users</li>
                    <li class="breadcrumb-item fs-5 active">View users</li>
                </ol>
            </nav>
        </div><!-- End Page Title -->
        <section class="section">
            <div class="row">
                <div class="col-lg-12">
                    <div class="card">
                        <div class="card-body">

                            <c:if test="${not empty sessionScope.messageResponse}">
                                <div class="alert alert-primary mt-3 alert-dismissible fade show" role="alert">
                                        ${sessionScope.messageResponse}
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>

                                <!-- Xóa thông báo khỏi session để không hiện lại khi chuyển trang -->
                                <c:remove var="messageResponse" scope="session" />
                            </c:if>

                            <div class="d-flex justify-content-between align-items-center">
                                <h3 class="card-title fs-3">List of users</h3>
                                <a href="/admin/add-new-user" class="btn btn-success text-white"><i class="bi bi-plus-circle-fill"></i> Add new user</a>
                            </div>
                            <!-- Table with stripped rows -->
                            <div class="datatable-top">
                                <form class="datatable-search text-end" method="get">
                                    <input class="datatable-input" placeholder="Search..." type="search" value="${keyword}" name="keyword" title="Search within table">
                                    <button type="submit" class="btn btn-success">Search</button>
                                </form>
                            </div>

                            <table class="table datatable text-center mt-3">
                                <thead>
                                    <tr>
                                        <th>No</th>
                                        <th>UserName</th>
                                        <th>FullName</th>
                                        <th>Role</th>
                                        <th>Status</th>
                                        <th>Options</th>
                                        <th>Manage</th>
                                    </tr>
                                </thead>

                                <tbody style="vertical-align: middle">
                                    <c:forEach items="${listUsers}" var="user" varStatus="status">
                                        <tr>
                                            <td>${(pageNumber - 1) * pageSize + status.index + 1}</td>
                                            <td>${user.username}</td>
                                            <td>${user.fullName}</td>
                                            <td>
                                                ${
                                                    user.role == 0 ? "SUPER ADMIN" :
                                                    user.role == 1 ? "ADMIN" :
                                                    user.role == 2 ? "SHIPPER" :
                                                    user.role == 3 ? "USER" : "Unknown"
                                                }
                                            </td>
                                            <td>
                                                <div class="p-1" style="display: inline; color: white; background-color: ${user.status == 1 ? 'green' : 'red'}">
                                                        ${user.status == 1 ? "ACTIVATE" : "DISABLE"}
                                                </div>
                                            </td>
                                            <td>
<%--                                                <a class="btn btn-warning"><i class="bi bi-info-circle-fill text-white"></i></a>--%>
                                                <a href="/admin/update-user?id=${user.id}" class="btn btn-primary"><i class="bi bi-pencil-square text-white"></i></a>
                                                <a class="btn btn-danger" data-bs-toggle="modal"
                                                   data-bs-target="#modalDeleteUser" onclick="clickDeleteUser(${user.id})">
                                                    <i class="bi bi-trash-fill text-white"></i>
                                                </a>
                                            </td>
                                            <td>
                                                <a href="/admin/user/${user.id}/view-address" class="btn btn-primary">Address</a>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                </tbody>
                            </table>

<%--                            pagination--%>
                            <div class="datatable-bottom d-flex justify-content-center">
                                <nav class="datatable-pagination" style="float: unset">
                                    <ul class="datatable-pagination-list">
                                        <li class="datatable-pagination-list-item <c:if test="${pageNumber < 2}">datatable-hidden</c:if>">
                                            <a href="?<c:if test="${not empty keyword}">keyword=${keyword}&</c:if>page=${pageNumber - 1}"
                                               data-page="1" class="datatable-pagination-list-item-link" aria-label="Page 1">
                                                ‹‹
                                            </a>
                                        </li>

                                        <c:forEach var="i" begin="1" end="${totalPage}">
                                            <li class="datatable-pagination-list-item <c:if test="${pageNumber == i}">datatable-active</c:if>">
                                                <a href="?<c:if test="${not empty keyword}">keyword=${keyword}&</c:if>page=${i}"
                                                   data-page="${i}" class="datatable-pagination-list-item-link" aria-label="Page ${i}">
                                                        ${i}
                                                </a>
                                            </li>
                                        </c:forEach>

                                        <li class="datatable-pagination-list-item <c:if test="${pageNumber >= totalPage}">datatable-hidden</c:if>">
                                            <a href="?<c:if test="${not empty keyword}">keyword=${keyword}&</c:if>page=${pageNumber + 1}"
                                               data-page="2" class="datatable-pagination-list-item-link" aria-label="Page 2">
                                                ››
                                            </a>
                                        </li>
                                    </ul>
                                </nav>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </section>

        <div class="modal fade" id="modalDeleteUser" tabindex="-1" style="display: none;" aria-hidden="true">
            <div class="modal-dialog">
                <form class="modal-content" action="/admin/delete-user" method="post">
                    <div class="modal-header">
                        <h5 class="modal-title">Delete User</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p>Are you sure want to delete user with id: <span id="idDeleleUser"> </span> ?</p>
                        <input type="text" name="idUserDelete" hidden id="idUserDelete">
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-danger">Delete</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    </div>
                </form>
            </div>
        </div>

    </main>

    <a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i
            class="bi bi-arrow-up-short"></i></a>
    <script src="/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="/assets/js/main.js"></script>
    <script src="/js/user.js"></script>
</body>
</html>