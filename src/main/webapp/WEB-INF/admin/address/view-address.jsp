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
        <h1 class="fw-bold">View address</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item fs-5"><a href="/admin">Home</a></li>
                <li class="breadcrumb-item fs-5 active">User</li>
                <li class="breadcrumb-item fs-5 active">View address</li>
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
                            <h3 class="card-title fs-3">List of address</h3>
                            <a href="/admin/user/${userId}/add-new-address" class="btn btn-success text-white"><i class="bi bi-plus-circle-fill"></i> Add new address</a>
                        </div>
                        <!-- Table with stripped rows -->

                        <table class="table datatable text-center mt-3">
                            <thead>
                            <tr>
                                <th>No</th>
                                <th>Type</th>
                                <th>Province</th>
                                <th>District</th>
                                <th>Ward</th>
                                <th>Phone</th>
                                <th>Options</th>
                            </tr>
                            </thead>

                            <tbody style="vertical-align: middle">

                                <c:forEach items="${listAddress}" var="address" varStatus="status">
                                    <tr>
                                        <td>${status.index + 1}</td>
                                        <td>${address.name}</td>
                                        <td>${address.province}</td>
                                        <td>${address.district}</td>
                                        <td>${address.ward}</td>
                                        <td>${address.phone}</td>
                                        <td>
                                            <a href="/admin/user/${userId}/update-address/${address.id}" class="btn btn-primary"><i class="bi bi-pencil-square text-white"></i></a>
                                            <a class="btn btn-danger" data-bs-toggle="modal"
                                               data-bs-target="#modalDeleteUser" onclick="clickDeleteAddress(${address.id})">
                                                <i class="bi bi-trash-fill text-white"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>

                            </tbody>
                        </table>

                        <div class="text-center py-3 mt-5">
                            <a href="/admin/users" class="btn btn-secondary fs-5 px-4">Back</a>
                        </div>

                    </div>
                </div>

            </div>
        </div>
    </section>

    <div class="modal fade" id="modalDeleteUser" tabindex="-1" style="display: none;" aria-hidden="true">
        <div class="modal-dialog">
            <form class="modal-content" action="/admin/delete-address" method="post">
                <div class="modal-header">
                    <h5 class="modal-title">Delete Address</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p>Are you sure want to delete address with id: <span id="idDeleleAddress"> </span> ?</p>
                    <input type="text" name="idAddressDelete" hidden id="idAddressDelete">
                    <input type="text" name="idUser" hidden value="${userId}"> <%--  Gửi lên userId để redirect sang đường link khác--%>
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
<script src="/js/address.js"></script>
</body>
</html>