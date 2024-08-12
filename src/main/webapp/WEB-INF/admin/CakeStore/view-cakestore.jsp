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
    <link rel="stylesheet" href="/css/cake-store.css">
</head>
<body>
<jsp:include page="../../common/admin-header.jsp"></jsp:include>
<jsp:include page="../../common/admin-sidebar.jsp"></jsp:include>

<main id="main" class="main">
    <div>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item fs-5"><a href="/admin">Home</a></li>
                <li class="breadcrumb-item fs-5 active">Store</li>
                <li class="breadcrumb-item fs-5 active">List of cakes for sale</li>
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
                            <h3 class="card-title fs-3">List of cakes for sale</h3>
                            <a href="/admin/store/${storeId}/add-new-cake" class="btn btn-success text-white"><i class="bi bi-plus-circle-fill"></i> Add new cake</a>
                        </div>

                        <!-- Table with stripped rows -->
                        <div class="datatable-top">
                            <form class="datatable-search text-end" method="get">
                                <input class="datatable-input" placeholder="Search..." type="search" name="keyword" title="Search within table" value="${keyword}">
                                <button type="submit" class="btn btn-success" >Search</button>
                            </form>
                        </div>

                        <table class="table datatable text-center mt-3">
                            <thead>
                            <tr>
                                <th>Code</th>
                                <th>Name</th>
                                <th>Image</th>
                                <th>Quantity</th>
                                <th>Price</th>
                                <th>MFD</th>
                                <th>EXP</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody style="vertical-align: middle">
                                <c:forEach items="${cakeStoreList}" var="cakeStore">
                                    <tr>
                                        <td>${cakeStore.codeCake}</td>
                                        <td>${cakeStore.cakeName}</td>
                                        <td>
                                                <div class="lightbox" data-mdb-lightbox-init>
                                                    <img style="width: 60px" id="myImg" src="${cakeStore.imageCake}" alt="Image cake">
                                                </div>
                                        </td>
                                        <td>${cakeStore.quantity}</td>
                                        <td>${cakeStore.price}đ</td>
                                        <td>
                                            <fmt:formatDate value="${cakeStore.productionDate}" pattern="dd-MM-yyyy " />
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${cakeStore.expirationDate}" pattern="dd-MM-yyyy " />
                                        </td>
                                        <td>
                                                <div class="p-1" style="display: inline; color: white; background-color: ${cakeStore.status == 1 ? 'green' : 'red'}">
                                                        ${cakeStore.status == 1 ? "ON SALES" : "STOP SALES"}
                                                </div>
                                        </td>
                                        <td>
                                            <a href="/admin/store/${storeId}/update-store-cake/${cakeStore.id}" class="btn btn-primary">
                                                <i class="bi bi-pencil-square text-white"></i>
                                            </a>
                                            <a class="btn btn-danger" data-bs-toggle="modal"
                                               data-bs-target="#modalDeleteStore"
                                               onclick="clickDeleteCakeStore(${cakeStore.id}, '${cakeStore.cakeName}')">
                                                <i class="bi bi-trash-fill text-white"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <!-- Pagination -->
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

                        <div class="text-center py-3 mt-3">
                            <a href="/admin/stores" class="btn btn-secondary fs-5 px-4">Back</a>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </section>

<%--    modal delete cake store--%>
    <div class="modal fade" id="modalDeleteStore" tabindex="-1" style="display: none;" aria-hidden="true">
        <div class="modal-dialog">
            <form class="modal-content" method="post" action="/admin/delete-cake-from-store">
                <div class="modal-header">
                    <h5 class="modal-title">Stop selling the product</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p>Are you sure want to stop selling the product: <span id="cakeName">  </span> ?</p>
                    <input type="text" name="cakeStoreId" hidden id="cakeStoreId">
                    <input type="text" name="storeId" hidden value="${storeId}">
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-danger">Stop</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                </div>
            </form>
        </div>
    </div>

<%--    The Modal image--%>
    <div id="imageModal" class="modal-image">
        <span class="close" style="margin-top: 50px">&times;</span>
        <img class="modal-content-image" id="imgCake">
        <div id="caption"></div>
    </div>

</main>

<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>
<script src="/js/cake-store.js"></script>
<script src="/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="/assets/js/main.js"></script>

<script>
    var modal = document.getElementById("imageModal");

    // Get the image and insert it inside the modal - use its "alt" text as a caption
    var img = document.getElementById("myImg");
    var modalImg = document.getElementById("imgCake");
    var captionText = document.getElementById("caption");
    img.onclick = function(){
        modal.style.display = "block";
        modalImg.src = this.src;
        captionText.innerHTML = this.alt;
    }

    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];

    // When the user clicks on <span> (x), close the modal
    span.onclick = function() {
        modal.style.display = "none";
    }
</script>

</body>
</html>
