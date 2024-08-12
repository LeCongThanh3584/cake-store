<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
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
    <link href="/assets/vendor/simple-datatables/style.css" rel="stylesheet">
    <link href="/assets/css/style.css" rel="stylesheet">
    <link href="/assets/css/view-orders.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../../common/admin-header.jsp"></jsp:include>
<jsp:include page="../../common/admin-sidebar.jsp"></jsp:include>

<main id="main" class="main">
        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Change Status Order</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- Form -->
                    <form action="/admin/order-status" method="post">
                        <input type="hidden" id="order-id" name="order-id" value="">
                        <input type="hidden" name="store_id" value="${storeId}">
                        <input type="hidden" name="page" value="${listOrders.pageNo}">
                        <input type="hidden" name="search" value="${search}">
                        <div class="mb-3">
                            <label for="status-name" class="col-form-label">Status:</label>
                            <select  class="form-control" name="status" id="status-name">
                                <option value="0">PENDING</option>
                                <option value="1">CONFIRMED</option>
                                <option value="2">DELIVERING</option>
                                <option value="3">DELIVERED</option>
                                <option value="4">CANCELLED</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="message-text" class="col-form-label">Description:</label>
                            <textarea type="text" name="description" class="form-control" id="message-text"></textarea>
                        </div>
                        <div class="modal-footer">
                            <a class="btn btn-secondary" data-bs-dismiss="modal">Close</a>
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div>
        <h1>View orders</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item fs-5"><a href="/admin">Home</a></li>
                <li class="breadcrumb-item fs-5 active">Manage Orders</li>
                <li class="breadcrumb-item fs-5 active">View orders</li>
            </ol>
        </nav>
    </div><!-- End Page Title -->
    <section class="section">
        <div class="row">
            <div class="col-lg-12">
                <div class="card">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center">
                            <h3 class="card-title fs-3">List of orders</h3>
                            <a href="/admin/create-order" class="btn btn-success text-white">+ Create order</a>
                        </div>

                        <div class="datatable-top">
                            <form action="" method="get">
                                <div class="d-flex">
                                    <c:if test="${sessionScope.role == 0}">
                                    <div>
                                        <label>Select Store: </label>
                                        <select class="datatable-input"  name="storeId"
                                                title="Select within table" style="height: 100%;">
                                            <option value="0" <c:if test="${storeId == 0}">selected</c:if>>All</option>

                                            <c:forEach var="store" items="${stores}">
                                                <option value="${store.id}" <c:if test="${storeId == store.id}">selected</c:if>>
                                                    ${store.name}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    </c:if>
                                    <div class="ms-2">
                                        <div class="datatable-search">
                                            <input class="datatable-input" placeholder="Search..." type="search" name="search"
                                                   title="Search within table" value="${search}">
                                        </div>
                                    </div>
                                    <div class="ms-2">
                                        <button class="btn btn-secondary text-white">Search</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <table class="table datatable">
                            <thead>
                            <tr>
                                <th>NO</th>
                                <th>Code</th>
                                <th>Reciver</th>
                                <th>Phone</th>
                                <th>Order Time</th>
                                <th>Total Value</th>
                                <th>Status</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="o" items="${listOrders.list}" varStatus="status">
                            <tr>
                                <td>${status.index+1}</td>
                                <td>${o.code}</td>
                                <td>${o.reciver}</td>
                                <td>${o.phone}</td>
                                <td><fmt:formatDate value="${o.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td><fmt:formatNumber value="${o.totalMoney}" type="currency"/> </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${o.status == 0}">PENDING</c:when>
                                        <c:when test="${o.status == 1}">CONFIRMED</c:when>
                                        <c:when test="${o.status == 2}">DELIVERING</c:when>
                                        <c:when test="${o.status == 3}">DELIVERED</c:when>
                                        <c:when test="${o.status == 4}">CANCELLED</c:when>
                                        <c:otherwise>UNKNOWN</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <a href="/admin/order?id=${o.id}" class="btn btn-secondary">
                                        <i class="bi bi-info-circle-fill text-white"></i>
                                    </a>
                                    <c:if test="${o.status == 0 || o.status == 1}">
                                        <a href="/admin/update-order?id=${o.id}" class="btn btn-primary">
                                            <i class="bi bi-pencil-square"></i></a>
                                    </c:if>
                                    <c:if test="${o.status != 4 && o.status != 3}">
                                        <a class="btn btn-info" data-bs-toggle="modal" data-bs-target="#exampleModal" data-order-id="${o.id}">
                                            <i class="bi bi-truck"></i></a>
                                    </c:if>
                                </td>
                            </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <!-- End Table with stripped rows -->
                        <div class="datatable-bottom">
                            <div class="datatable-info">Showing ${(listOrders.pageNo-1)*listOrders.pageSize + 1} to
                                ${(listOrders.pageNo-1)*listOrders.pageSize + listOrders.pageSizeFact} of ${listOrders.total} entries
                            </div>
                            <nav class="datatable-pagination">
                                <ul class="datatable-pagination-list">
                                    <li class="datatable-pagination-list-item datatable-hidden datatable-disabled">
                                        <button data-page="1" class="datatable-pagination-list-item-link"
                                                aria-label="Page 1">‹
                                        </button>
                                    </li>
                                    <c:forEach begin="1" end="${listOrders.totalPages}" var="page">
                                        <c:url var="pageUrl" value="">
                                            <c:param name="page" value="${page}"/>
                                            <c:param name="storeId" value="${storeId}"/>
                                            <c:param name="search" value="${search}"/>
                                        </c:url>
                                        <li class="datatable-pagination-list-item ${page == listOrders.pageNo ? 'datatable-active' : ''}">
                                            <a href="${pageUrl}" class="datatable-pagination-list-item-link" aria-label="Page ${page}">
                                                ${page}
                                            </a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </nav>
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
<script src="https://stackpath.bootstrapcdn.com/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            let exampleModal = document.getElementById('exampleModal');
            exampleModal.addEventListener('show.bs.modal', function (event) {
                // Button that triggered the modal
                let button = event.relatedTarget;
                // Extract info from data-order-id attribute
                let orderId = button.getAttribute('data-order-id');
                // Update the modal's input with the orderId
                let orderIdInput = exampleModal.querySelector('#order-id');
                orderIdInput.value = orderId;
            });
        });

        function submitForm() {
            const form = document.getElementById('modalForm');
            form.reset();
        }
    </script>
</body>
</html>