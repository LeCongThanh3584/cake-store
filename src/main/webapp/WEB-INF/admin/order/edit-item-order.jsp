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
    <link href="/assets/css/style.css" rel="stylesheet">
    <script type="text/javascript">
        function confirmDeletion(url) {
            if (confirm("Are you sure you want to delete this item?")) {
                window.location.href = url;
            }
        }
    </script>
</head>
<body>
<jsp:include page="../../common/admin-header.jsp"></jsp:include>
<jsp:include page="../../common/admin-sidebar.jsp"></jsp:include>

<main id="main" class="main">

    <div>
        <h1>Update order</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item fs-5"><a href="/admin">Home</a></li>
                <li class="breadcrumb-item fs-5 active"><a href="/admin/orders">Manage Orders</a></li>
                <li class="breadcrumb-item fs-5 active">Update order</li>
            </ol>
        </nav>
    </div><!-- End Page Title -->

    <section class="section">
        <div class="row">
            <div class="col-lg-10 col-md-12 mx-auto">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title text-center my-3 fs-2">Add Item In Order</h5>

                        <c:if test="${not empty sessionScope.success}">
                            <div class="alert alert-primary mt-3 alert-dismissible fade show" role="alert">
                                    ${sessionScope.success}
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                            <c:remove var="success" scope="session" />
                        </c:if>
                        <!-- Horizontal Form -->
                        <form action="/admin/update-item" method="post">
                            <input type="hidden" name="itemId" id="itemId" value="">
                            <input type="text" name="orderId" class="form-control" hidden value="${orderId}">
                            <div class="row mb-3">
                                <label class="col-sm-2 col-form-label font-size-label fw-bold">Cake: </label>
                                <div class="dropdown col-sm-9">
                                    <button class="btn btn-secondary w-100 bg-light text-dark" type="button" id="dropdownMenuSelectCake" data-bs-toggle="dropdown" aria-expanded="false">
                                        Select cake <i class="bi bi-chevron-down"></i>
                                    </button>
                                    <c:if test="${not empty sessionScope.error}">
                                        <span style="color: red">${sessionScope.error}</span>
                                        <c:remove var="error" scope="session" />
                                    </c:if>
                                    <div class="dropdown-menu w-100 z-index-0" aria-labelledby="dropdownMenuSelectCake">
                                        <div class="input-group p-2">
                                            <span class="input-group-text" id="basic-addon1"><i class="bi bi-search"></i></span>
                                            <input type="text" class="form-control form-control-focus" id="searchInput" placeholder="Search...">
                                        </div>
                                        <ul class="text-center content-select-cake">
                                            <c:forEach items="${cakeStores}" var="cakeStore">
                                                <li class="dropdown-item" data-id="${cakeStore.id}" data-price="${cakeStore.price}" data-quantity="${cakeStore.quantity}" style="border-bottom: 1px solid rgb(128,128,128);">
                                                    <div class="row">
                                                        <div class="col-2" hidden>
                                                            <p class="fw-bold mb-2">Id: </p> <span>${cakeStore.id}</span>
                                                        </div>
                                                        <div class="col-3">
                                                            <p class="fw-bold mb-2">Name: </p> <span>${cakeStore.cakeName}</span>
                                                        </div>
                                                        <div class="col-2">
                                                            <p class="fw-bold mb-2">Code: </p> <span>${cakeStore.codeCake}</span>
                                                        </div>
                                                        <div class="col-3">
                                                            <p class="fw-bold mb-2">Quantity: </p> <span>${cakeStore.quantity}</span>
                                                        </div>
                                                        <div class="col-2">
                                                            <p class="fw-bold mb-2">Image: </p>
                                                            <img style="width: 35px;" src="${cakeStore.imageCake}" alt="">
                                                        </div>
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                                <input type="text" name="cakeId" id="cakeId" hidden />
                                <input type="hidden" id="cakePrice" name="cake_price">
                                <input type="hidden" id="cakeQuantity" name="cake_quantity">
                            </div>
                            <div class="row mb-3">
                                <label for="quantityInput" class="col-sm-2 col-form-label fw-bold">Quantity</label>
                                <div class="col-sm-9">
                                    <input type="number" name="quantity" class="form-control" id="quantityInput">
                                    <c:if test="${not empty sessionScope.error1}">
                                        <span style="color: red">${sessionScope.error1}</span>
                                        <c:remove var="error1" scope="session" />
                                    </c:if>
                                    <c:if test="${not empty sessionScope.error2}">
                                        <span style="color: red">${sessionScope.error2}</span>
                                        <c:remove var="error2" scope="session" />
                                    </c:if>
                                </div>
                            </div>
                            <div class="text-center">
                                <button type="submit" class="btn btn-primary fs-5 px-4">Add</button>
                            </div>
                        </form>

                    </div>
                    <div class="card-body">
                        <label class="col-sm-3 col-form-label">List Items:</label>
                        <div class="col-sm-12">
                            <table class="table table-striped table-sm mb-0 pb-0">
                                <thead>
                                    <tr class="mb-0">
                                        <th>Cake</th>
                                        <th>Price</th>
                                        <th>Quantity</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody id="itemList">
                                <c:forEach var="item" items="${list}">
                                    <tr data-id="${item.id}" data-cake-id="${item.idCakeStore}" data-price="${item.price}" data-quantity="${item.quantity}">
                                        <td>${item.cakeName}</td>
                                        <td>${item.price}</td>
                                        <td>${item.quantity}</td>
                                        <td>
                                            <button type="button" class="edit-btn btn btn-primary"><i class="bi bi-pencil-square"></i></button>
                                            <a href="#"
                                               onclick="confirmDeletion('/admin/delete-item?itemId=${item.id}&orderId=${orderId}')"
                                               class="btn btn-danger"><i class="bi bi-trash"></i></a>
                                        </td>
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

</main><!-- End #main -->

<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i
        class="bi bi-arrow-up-short"></i></a>
<script src="/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="/assets/js/main.js"></script>
<script>
    const listItem = document.querySelectorAll('.dropdown-item');
    const inputCakeStoreId = document.getElementById('cakeId');
    const inputCakePrice = document.getElementById('cakePrice');
    const inputCakeQuantity = document.getElementById('cakeQuantity');
    const buttonSelect = document.getElementById('dropdownMenuSelectCake');

    const search = document.getElementById('searchInput');

    //select cake
    listItem.forEach(item => {
        item.addEventListener('click',()=>{
            inputCakeStoreId.value = item.getAttribute('data-id');
            inputCakePrice.value = item.getAttribute('data-price');
            inputCakeQuantity.value = item.getAttribute('data-quantity');
            buttonSelect.innerHTML = item.innerHTML;
        })
    })

    //search cake
    search.addEventListener('input',()=>{
        const keywordSearch = search.value.toLowerCase();

        listItem.forEach(item => {
            const content = item.textContent.toLowerCase();

            if(content.includes(keywordSearch)){
                item.style.display = 'block';
            } else {
                item.style.display = 'none';
            }
        })
    })

    //edit-item
    document.addEventListener('DOMContentLoaded',function (){
        document.querySelectorAll('.edit-btn').forEach(function (button){
            button.addEventListener("click",function (){
                let row = this.closest('tr');
                let itemId = row.getAttribute('data-id');
                let cakeId = row.getAttribute('data-cake-Id');
                let quantity = row.getAttribute('data-quantity');
                let price = row.getAttribute('data-price');

                document.getElementById('itemId').value = itemId;
                inputCakeStoreId.value = cakeId;
                inputCakePrice.value = price;

                listItem.forEach(item => {
                    if(item.getAttribute('data-id') === cakeId){
                        buttonSelect.innerHTML = item.innerHTML;
                        inputCakeQuantity.value = item.getAttribute('data-quantity');
                    }
                })

                document.getElementById('quantityInput').value = quantity;
            })
        })
    })
</script>
</body>
</html>