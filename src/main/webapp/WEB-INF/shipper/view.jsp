<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>Dashboard - Shipper</title>
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
<jsp:include page="../common/shipper-header.jsp"></jsp:include>

<div id="main" class="main ms-0">
    <div>
        <h1>View orders</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item fs-5"><a href="/shipper">Home</a></li>
                <li class="breadcrumb-item fs-5 active">View orders</li>
            </ol>
        </nav>
    </div><!-- End Page Title -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert">
                ${error}
        </div>
    </c:if>
    <section class="section">
        <div class="row">
            <div class="col-lg-12">
                <div class="card">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center">
                            <h3 class="card-title fs-3">List of available orders</h3>
                            <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
                                <a class="btn btn-primary">Available Orders</a>
                                <a href="/shipper/active" class="btn btn-outline-primary">Your orders</a>
                                <a href="/shipper/history" class="btn btn-outline-primary">Order history</a>
                            </div>
                        </div>

                        <form class="py-2 d-flex align-items-center justify-content-between">
                            <div class="d-flex">
                                <div class="d-flex align-items-center">
                                    <h4 class="mb-0">Select Store: </h4>
                                    <select class="datatable-input ms-2 px-3 py-2" name="storeId"
                                            title="Select within table">
                                        <c:choose>
                                            <c:when test="${param.storeId} == ''">
                                                <option selected value="">All</option>
                                                <c:forEach var="store" items="${stores}">
                                                    <option value="${store.id}">${store.name}
                                                        - ${store.address}</option>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="">All</option>
                                                <c:forEach var="store" items="${stores}">
                                                    <option ${''.concat(store.id) == param.storeId ? "selected" : ""}
                                                            value="${store.id}">${store.name}
                                                        - ${store.address}</option>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </div>
                            </div>
                            <div class="datatable-search border border-1 border-dark">
                                <input class="datatable-input px-3 py-2 border-0" placeholder="Search..." type="search"
                                       name="query"
                                       title="Search within table" style="width: 300px;">
                                <button class="px-3 py-2 bg-secondary text-white border-0">Search</button>
                            </div>
                        </form>

                        <table class="table datatable">
                            <thead>
                            <tr>
                                <th>NO</th>
                                <th>Code</th>
                                <th>Receiver</th>
                                <th>Phone</th>
                                <th>Order Time</th>
                                <th>Description</th>
                                <th>Total Value</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:choose>
                                <c:when test="${orders.size() == 0}">
                                    <tr>
                                        <td colspan="8">
                                            <h2 class="mb-4">There is order with such information</h2>
                                        </td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="item" items="${orders}" varStatus="status">
                                        <tr>
                                            <td>${status.index+1}</td>
                                            <td>${item.code}</td>
                                            <td>${item.reciver}</td>
                                            <td>${item.phone}</td>
                                            <td>
                                                <fmt:formatDate value="${item.createdAt}"
                                                                pattern="yyyy-MM-dd HH:mm:ss"/>
                                            </td>
                                            <td>${item.description}</td>
                                            <td>
                                                <fmt:formatNumber value="${item.totalMoney}" type="currency"/>
                                            </td>
                                            <td class="d-flex">
                                                <a data-bs-toggle='modal'
                                                   data-id="${item.id}"
                                                   data-code="${item.code}"
                                                   data-receiver="${item.reciver}"
                                                   data-phone="${item.phone}"
                                                   data-address="${item.address}"
                                                   data-bs-target='#exampleModal'
                                                   class="btn btn-success select-button">
                                                    Select
                                                </a>
                                                <a href="/shipper/order/${item.id}"
                                                   class="btn btn-info text-white ms-2">View</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                            </tbody>
                        </table>
                        <!-- End Table with stripped rows -->

                        <div class=" d-flex justify-content-between">
                            <div></div>
                            <nav class="datatable-pagination" style="float: unset">
                                <ul class="datatable-pagination-list">
                                    <li class="datatable-pagination-list-item <c:if test="${page < 2}">datatable-hidden</c:if>">
                                        <a href="?storeId=${param.storeId}&&query=${param.query}&&page=${page-1}"
                                           data-page="1" class="datatable-pagination-list-item-link"
                                           aria-label="Page 1">
                                            ‹‹
                                        </a>
                                    </li>

                                    <c:forEach begin="${startPage}" end="${endPage}" var="i">
                                        <li class="datatable-pagination-list-item <c:if test="${page == i}">datatable-active</c:if>">
                                            <a href="?storeId=${param.storeId}&&query=${param.query}&&page=${i}"
                                               data-page="${i}" class="datatable-pagination-list-item-link"
                                               aria-label="Page ${i}">
                                                    ${i}
                                            </a>
                                        </li>
                                    </c:forEach>

                                    <li class="datatable-pagination-list-item <c:if test="${page >= endPage}">datatable-hidden</c:if>">
                                        <a href="" ?storeId=${param.storeId}&&query=${param.query}&&page=${page+1}""
                                           data-page="2" class="datatable-pagination-list-item-link"
                                           aria-label="Page 2">
                                            ››
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                            <p>Showing ${pageSize * (page - 1) + 1}-${pageSize * (page - 1) + orders.size()}
                                of ${pageCount} results</p>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </section>
</div>
<div class='modal fade' id='exampleModal' tabindex='-1' aria-labelledby='exampleModalLabel' aria-hidden='true'>
    <div class='modal-dialog'>
        <div class='modal-content'>
            <div class='modal-header'>
                <h1 class='modal-title fs-5' id='exampleModalLabel'>Deliver this order</h1>
                <button class='btn-close' data-bs-dismiss='modal' aria-label='Close'></button>
            </div>
            <form method='post' action="/shipper/select" class='modal-body'>

            </form>
            <div class='modal-footer'>
                <button type='submit' class='btn btn-success text-white'>Select</button>
                <button class='btn btn-secondary' data-bs-dismiss='modal'>Exit</button>
            </div>
        </div>
    </div>
</div>
<script defer>
    const exampleModal = document.getElementById('exampleModal');

    if (exampleModal) {
        const modalBody = exampleModal.querySelector('.modal-body');
        const submitButton = exampleModal.querySelector('.btn.btn-success');

        exampleModal.addEventListener('show.bs.modal', (event) => {
            const button = event.relatedTarget;
            const orderId = button.dataset.id;

            modalBody.innerHTML = "" +
                "<p><strong class='me-2'>Order's code:</strong>" + button.dataset.code + "</p>" +
                "<p><strong class='me-2'>Order's receiver:</strong>" + button.dataset.receiver + "</p>" +
                "<p><strong class='me-2'>Order's address:</strong>" + button.dataset.address + "</p>" +
                "<p><strong class='me-2'>Order's phone:</strong>" + button.dataset.phone + "</p>" +
                "<div class='mb-3'><label for= 'shipperDesc' class= 'form-label fw-semibold fs-5' > Shipper's message </label>" +
                "<input name='description' placeholder='A message for your client' value ='' class='form-control' id='shipperDesc' aria-describedby='description'>" +
                "<input name='orderId' hidden value='" + orderId + "' class='form-control' aria-describedby='description'>" +
                "</div>";
        });
        submitButton.addEventListener('click', () => {
            modalBody.submit()
        })
    }
</script>

<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i
        class="bi bi-arrow-up-short"></i></a>
<script src="/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="/assets/js/main.js"></script>
</body>
</html>