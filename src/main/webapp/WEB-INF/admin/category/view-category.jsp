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

    <script type="text/javascript">
        function confirmDeletion(url) {
            if (confirm("Are you sure you want to delete this category?")) {
                window.location.href = url;
            }
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

</head>
<body>
<jsp:include page="../../common/admin-header.jsp"></jsp:include>
<jsp:include page="../../common/admin-sidebar.jsp"></jsp:include>

<main id="main" class="main">
    <div>
        <h1>View category</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item fs-5"><a href="/admin">Home</a></li>
                <li class="breadcrumb-item fs-5 active">Manage Cake</li>
                <li class="breadcrumb-item fs-5 active">View category</li>
            </ol>
        </nav>
    </div><!-- End Page Title -->
    <section class="section">
        <div class="row">
            <div class="col-lg-12 mx-auto">

                <div class="card">
                    <div class="card-body">

                        <c:if test="${not empty sessionScope.response}">
                            <div class="toast-container position-fixed bottom-0 end-0 p-3">
                                <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                                    <div class="toast-header">
                                        <strong class="me-auto">sweetk cake</strong>
                                        <button type="button" class="btn-close" data-bs-dismiss="toast"
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
                            <h3 class="card-title fs-3">List of category</h3>
                            <a href="/admin/category/create" class="btn btn-success text-white"><i
                                    class="bi bi-file-earmark-plus">Add category</i></a>
                        </div>
                        <div class="datatable-top">
                            <form class="datatable-search text-end" action="/admin/category/view">
                                <input hidden="hidden" name="page" value="${currentPage}">
                                <input class="datatable-input" placeholder="Search..." type="text" name="search"
                                       title="Search within table">
                                <button type="submit" class="btn btn-success">Search</button>
                            </form>
                        </div>
                        <table class="table datatable">
                            <thead>
                            <tr>
                                <th>Stt</th>
                                <th>Code</th>
                                <th>Name</th>
                                <th>Description</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${categories}" var="ct" varStatus="i">
                                <tr>
                                    <td>${i.index + 1}</td>
                                    <td>${ct.code}</td>
                                    <td>${ct.name}</td>
                                    <td>${ct.description}</td>
                                    <td>
                                        <a class="btn btn-primary"
                                           href="/admin/category/update?id=${ct.id}"><i
                                                class="bi bi-pencil-square"></i></a>
                                        <a class="btn btn-danger" href="#"
                                           onclick="confirmDeletion('/admin/category/delete?id=${ct.id}')">
                                            <i class="bi bi-trash"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div class="datatable-bottom">
                            <div class="datatable-info">Showing ${currentPage} to 10 of ${totalPage} entries</div>
                            <nav class="datatable-pagination">
                                <ul class="datatable-pagination-list">
                                    <c:if test="${totalPage > 1}">
                                        <li class="datatable-pagination-list-item ${currentPage == 1 ? 'datatable-hidden datatable-disabled' : ''}">
                                            <button data-page="${currentPage - 1}"
                                                    class="datatable-pagination-list-item-link"
                                                    aria-label="Previous Page">
                                                <a href="/admin/category/view?page=${currentPage - 1}&search=${search}">‹</a>
                                            </button>
                                        </li>
                                    </c:if>

                                    <c:forEach var="i" begin="1" end="${totalPage}">
                                        <c:if test="${totalPage == 1 || i <= 5 || i >= totalPage - 4 || (i >= currentPage - 2 && i <= currentPage + 2)}">
                                            <li class="datatable-pagination-list-item ${i == currentPage ? 'datatable-active' : ''}">
                                                <button data-page="${i}" class="datatable-pagination-list-item-link"
                                                        aria-label="Page ${i}">
                                                    <a href="/admin/category/view?page=${i}&search=${search}">${i}</a>
                                                </button>
                                            </li>
                                        </c:if>
                                    </c:forEach>

                                    <c:if test="${totalPage > 1}">
                                        <li class="datatable-pagination-list-item ${currentPage == totalPage ? 'datatable-hidden datatable-disabled' : ''}">
                                            <button data-page="${currentPage + 1}"
                                                    class="datatable-pagination-list-item-link" aria-label="Next Page">
                                                <a href="/admin/category/view?page=${currentPage + 1}&search=${search}">›</a>
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
    </section>
</main>

<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i
        class="bi bi-arrow-up-short"></i></a>

<script>
    document.addEventListener('DOMContentLoaded', (event) => {
        var toastElList = [].slice.call(document.querySelectorAll('.toast'))
        var toastList = toastElList.map(function (toastEl) {
            return new bootstrap.Toast(toastEl)
        })
        toastList.forEach(toast => toast.show());
    });
</script>
<script src="/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="/assets/js/main.js"></script>
</body>
</html>