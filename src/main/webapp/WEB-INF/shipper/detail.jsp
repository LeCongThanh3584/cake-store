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
    <link href="/assets/vendor/simple-datatables/style.css" rel="stylesheet">
    <link href="/assets/css/style.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../common/shipper-header.jsp"></jsp:include>


<div id="main" class="main ms-0">
    <div>
        <div class="d-flex align-items-center ">
            <h1>Order detail</h1>
        </div>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item fs-5"><a href="/shipper">Order list</a></li>
                <li class="breadcrumb-item fs-5 "><a href="/shipper/history">Order history</a></li>
                <li class="breadcrumb-item fs-5 active">Order detail</li>
            </ol>
        </nav>
    </div><!-- End Page Title -->

    <section class="section">
        <div class="row">
            <div class="col-lg-12 mx-auto">
                <div class="card">

                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center">
                            <h2 class="card-title fs-2 r">Order information</h2>
                            <div class="btn-group ms-4">
                                <a href="/shipper/view" class="btn btn-outline-dark">Available Orders</a>
                                <a href="/shipper/active" class="btn btn-outline-dark">Your orders</a>
                                <a href="/shipper/history" class="btn btn-outline-dark">Order history</a>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-4 mb-3">
                                <label class="col-lg-12 col-form-label">Code</label>
                                <div class="col-lg-12">
                                    <input disabled type="text" name="receiver" value="${orderView.code}"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="col-4 mb-3">
                                <label class="col-lg-12 col-form-label">Customer</label>
                                <div class="col-lg-12">
                                    <input disabled type="text" name="receiver" value="${orderView.username}"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="col-4 mb-3">
                                <label class="col-lg-12 col-form-label">Receiver</label>
                                <div class="col-lg-12">
                                    <input disabled type="text" name="receiver" value="${orderView.receiver}"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="col-4 mb-3">
                                <label class="col-lg-12 col-form-label">Phone number</label>
                                <div class="col-lg-12">
                                    <input disabled type="text" name="phone" value="${orderView.phone}"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="col-4 mb-3">
                                <label class="col-lg-12 col-form-label">Address</label>
                                <div class="col-lg-12">
                                    <input disabled type="text" name="address" value="${orderView.address}"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="col-4 mb-3">
                                <label class="col-lg-12 col-form-label">
                                    Store
                                </label>
                                <div class="col-lg-12">
                                    <input disabled type="text" name="receiver"
                                           value="${orderView.storeName} - ${orderView.storeAddress}"
                                           class="form-control">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <table class="table datatable">
                                    <thead>
                                    <tr>
                                        <th>Product</th>
                                        <th>Quantity</th>
                                        <th>Total</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="productView" items="${orderView.orderDetails}">
                                        <input hidden name="cartId" value="${productView.id}">
                                        <tr class="cart-item">
                                            <td class="d-flex align-items-center">
                                                <div class="product__cart__item__pic">
                                                    <img style="height: 80px;" src="${productView.image}"
                                                         alt="">
                                                </div>
                                                <div class="ms-2">
                                                    <h5 class="fw-semibold"
                                                        style="color: #555;">${productView.name}</h5>
                                                    <fmt:formatNumber value="${productView.price}"
                                                                      type="currency"/>
                                                    </h5>
                                                </div>
                                            </td>
                                            <td class="quantity px-2 fs-5">
                                                    ${productView.quantity}
                                            </td>
                                            <td class="cart__price  fs-5">
                                                <fmt:formatNumber
                                                        value="${productView.price * productView.quantity}"
                                                        type="currency"/>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <tr>
                                        <td></td>
                                        <td class="fs-5 fw-semibold">Total</td>
                                        <td class="fs-5  fw-semibold"><fmt:formatNumber
                                                value="${orderView.totalMoney}"
                                                type="currency"/></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <c:if test="${orderView.status == 1}">
                            <div class="text-center">
                                <a data-bs-toggle='modal'
                                   data-id="${orderView.id}"
                                   data-code="${orderView.code}"
                                   data-receiver="${orderView.receiver}"
                                   data-phone="${orderView.phone}"
                                   data-address="${orderView.address}"
                                   data-bs-target='#exampleModal'
                                   class="btn btn-outline-info select-button fw-semibold">
                                    Select delivery
                                </a>
                            </div>
                        </c:if>
                        
                    </div>

                </div>
                <div class="card">
                    <div class="card-body">
                        <h2 class="card-title fs-2">Order History</h2>
                        <div class="col-sm-12">
                            <table class="table table-striped table-sm mb-0 pb-0">
                                <thead>
                                <tr class="mb-0">
                                    <th>Stt</th>
                                    <th>Status</th>
                                    <th>Created_date</th>
                                    <th>Created_time</th>
                                    <th>Description</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="item" items="${orderView.orderHistory}" varStatus="loop">
                                    <td>${loop.index + 1}</td>
                                    <td class="py-2">
                                        <c:choose>
                                            <c:when test="${item.statusName == 'PENDING'}">
                                                <span class="px-2 py-1 rounded bg-warning text-white">${item.statusName}</span>
                                            </c:when>
                                            <c:when test="${item.statusName == 'CONFIRMED'}">
                                                <span class="px-2 py-1 rounded bg-success text-white">${item.statusName}</span>
                                            </c:when>
                                            <c:when test="${item.statusName == 'DELIVERING'}">
                                                <span class="px-2 py-1 rounded bg-info text-white">${item.statusName}</span>
                                            </c:when>
                                            <c:when test="${item.statusName == 'DELIVERED'}">
                                                <span class="px-2 py-1 rounded bg-primary text-white">${item.statusName}</span>
                                            </c:when>
                                            <c:when test="${item.statusName == 'CANCELLED'}">
                                                <span class="px-2 py-1 rounded bg-danger text-white">${item.statusName}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="px-2 py-1 rounded bg-dark text-white">${item.statusName}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="py-2"><fmt:formatDate value="${item.createdAt}"
                                                                     pattern="YYYY-MM-dd"/></td>
                                    <td class="py-2"><fmt:formatDate value="${item.createdAt}"
                                                                     pattern="hh:mm"/></td>
                                    <td class="py-2">${item.description}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
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
</div>
</body>
</html>