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
    <style>
        .dropdown-menu {
            max-height: 300px;
            overflow-y: auto;
            overflow-x: hidden;
            width: 100%;
            text-align: center;
        }

        .btn-group {
            width: 100%;
        }

        .dropdown-toggle {
            width: 100%;
        }

        .dropdown-item {
            white-space: normal;
            padding: 10px;
        }

        .dropdown-item div {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<jsp:include page="../../common/admin-header.jsp"></jsp:include>
<jsp:include page="../../common/admin-sidebar.jsp"></jsp:include>

<main id="main" class="main">

    <div>
        <h1>update cake</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item fs-5"><a href="/index">Home</a></li>
                <li class="breadcrumb-item fs-5 active">Manage Stores</li>
                <li class="breadcrumb-item fs-5 active">update cake</li>
            </ol>
        </nav>
    </div><!-- End Page Title -->

    <section class="section">
        <div class="row">
            <div class="col-lg-9 mx-auto col-md-12">

                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title text-center my-4 fs-1">update cake</h5>
                        <form class="row g-3" method="post" action="/admin/cake/update?id=${cake.id}"
                              enctype="multipart/form-data">
                            <div class="col-md-6">
                                <label for="inputName" class="form-label">Name</label>
                                <input type="text" class="form-control" id="inputName" name="cakeName"
                                       value="${cake.name}">
                                <c:if test="${not empty errors.name}">
                                    <div class="text-danger">${errors.name}</div>
                                </c:if>
                            </div>
                            <div class="col-md-6">
                                <label for="inputCode" class="form-label">Code</label>
                                <input type="text" class="form-control" id="inputCode" name="cakeCode"
                                       value="${cake.code}">
                                <c:if test="${not empty errors.code}">
                                    <div class="text-danger">${errors.code}</div>
                                </c:if>
                            </div>
                            <div class="col-md-4">
                                <label for="inputWeight" class="form-label">Weight</label>
                                <input type="number" class="form-control" id="inputWeight" name="cakeWeight"
                                       value="${cake.weight}">
                                <c:if test="${not empty errors.weight}">
                                    <div class="text-danger">${errors.weight}</div>
                                </c:if>
                            </div>
                            <div class="col-md-4">
                                <label for="inputColor" class="form-label">Color</label>
                                <input type="text" class="form-control" id="inputColor" name="cakeColor"
                                       value="${cake.color}">
                                <c:if test="${not empty errors.color}">
                                    <div class="text-danger">${errors.color}</div>
                                </c:if>
                            </div>
                            <div class="col-md-4">
                                <label for="inputSize" class="form-label">Size</label>
                                <input type="text" class="form-control" id="inputSize" name="cakeSize"
                                       value="${cake.size}">
                                <c:if test="${not empty errors.size}">
                                    <div class="text-danger">${errors.size}</div>
                                </c:if>
                            </div>
                            <div class="col-md-4">
                                <label for="inputHeight" class="form-label">Height</label>
                                <input type="number" class="form-control" id="inputHeight" name="cakeHeight"
                                       value="${cake.height}">
                                <c:if test="${not empty errors.height}">
                                    <div class="text-danger">${errors.height}</div>
                                </c:if>
                            </div>
                            <div class="col-md-4">
                                <label for="inputLength" class="form-label">Length</label>
                                <input type="number" class="form-control" id="inputLength" name="cakeLength"
                                       value="${cake.length}">
                                <c:if test="${not empty errors.length}">
                                    <div class="text-danger">${errors.length}</div>
                                </c:if>
                            </div>

                            <div class="col-md-4">
                                <label for="inputState" class="form-label">Categories</label>
                                <select id="inputState" class="form-select" name="idCategory">
                                    <c:forEach items="${categories}" var="ct">
                                        <option value="${ct.id}" ${ct.id == cake.idCategory ? 'selected="selected"' : ''}>${ct.name}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="col-md-12">

                                <div class="d-flex">
                                    <legend class="col-form-label col-sm-2 pt-0">Status:</legend>
                                    <div class="form-check mr-3">
                                        <input class="form-check-input" type="radio" name="status"
                                               id="flexRadioDefault1" value="1" ${cake.status == 1? 'checked': ''}>
                                        <label class="form-check-label" for="flexRadioDefault1">
                                            ACTIVE
                                        </label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="status"
                                               id="flexRadioDefault2" value="0" ${cake.status == 0? 'checked': ''}>
                                        <label class="form-check-label" for="flexRadioDefault2">
                                            IN ACTIVE
                                        </label>
                                    </div>
                                </div>
                                <c:if test="${not empty errors.length}">
                                    <div class="text-danger">${errors.length}</div>
                                </c:if>
                            </div>


                            <div class="btn-group">
                                <button type="button" class="btn btn-success dropdown-toggle" data-bs-toggle="dropdown"
                                        aria-expanded="false">
                                    Material
                                </button>
                                <ul class="dropdown-menu">
                                    <li>
                                        <div class="input-group p-2">
                                            <span class="input-group-text" id="basic-addon1"><i
                                                    class="bi bi-search"></i></span>
                                            <input type="text" class="form-control form-control-focus" id="searchInput"
                                                   placeholder="Search...">
                                        </div>
                                    </li>
                                    <li class="dropdown-item" style="border-bottom: 1px solid rgb(128,128,128);">
                                        <div class="row">
                                            <div class="col-2">
                                                <input type="checkbox" class="form-check-input" id="checkAll"
                                                       onchange="confirm('hello')">
                                            </div>
                                            <div class="col-5">
                                                <p class="fw-bold mb-2">Code</p>
                                            </div>
                                            <div class="col-5">
                                                <p class="fw-bold mb-2">Name</p>
                                            </div>
                                        </div>
                                    </li>
                                    <c:forEach items="${materials}" var="mt">
                                        <li class="dropdown-item" data-id="${mt.id}" data-name="${mt.name}"
                                            data-code="${mt.code}"
                                            style="border-bottom: 1px solid rgb(128,128,128);">
                                            <div class="row">
                                                <div class="col-2">
                                                    <input type="checkbox" class="form-check-input dropdownCheck">
                                                </div>
                                                <div class="col-5">
                                                    <span>${mt.code}</span>
                                                </div>
                                                <div class="col-5">
                                                    <span>${mt.name}</span>
                                                </div>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                            <hr>
                            <label for="inputFileUpload" class="form-label">New data</label>
                            <div class="col-md-12">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th>Code</th>
                                        <th>Name</th>
                                        <th>weight</th>
                                        <th>description</th>
                                    </tr>
                                    </thead>
                                    <tbody id="table-body">
                                    </tbody>
                                </table>
                            </div>

                            <label for="inputFileUpload" class="form-label ">Old data</label>
                            <div class="col-md-12">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th>Code</th>
                                        <th>Name</th>
                                        <th>weight</th>
                                        <th>description</th>
                                        <th>Delete</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${materialCakes}" var="mc">
                                        <tr>
                                            <td>${mc.code}</td>
                                            <td>${mc.name}</td>
                                            <td><input type="number" class="form-control" value="${mc.weight}"
                                                       name="materials[${mc.idMaterial}][input1]"></td>
                                            <td><input type="text" class="form-control" value="${mc.description}"
                                                       name="materials[${mc.idMaterial}][input2]"></td>
                                            <td><input type="checkbox" class="form-check-input row-checkbox"></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="col-md-12">
                                <label for="inputFileUpload" class="form-label">Images Upload</label>
                                <input class="form-control" type="file" accept="image/*" id="inputFileUpload"
                                       name="image">
                            </div>
                            <div class="card">
                                <img src="${cake.image}" class="card-img-top" alt="..." id="prev">
                            </div>

                            <div class="col-12">
                                <label for="floatingTextarea" class="form-label">Descripiton</label>
                                <div class="form-floating">
                                    <textarea class="form-control" placeholder="Address" id="floatingTextarea"
                                              style="height: 100px;" name="descripiton">${cacke.description}</textarea>
                                </div>
                            </div>
                            <div class="text-center">
                                <button type="submit" class="btn btn-success fs-5 px-4 me-4">Submit</button>
                                <a href="/admin/cake/view" class="btn btn-secondary fs-5 px-4">Back</a>
                            </div>
                        </form>
                    </div>
                </div>

            </div>
        </div>
    </section>
</main>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const dropdownItems = document.querySelectorAll('.dropdown-item');


        dropdownItems.forEach(item => {
            const checkbox = item.querySelector('.dropdownCheck');
            const name = item.getAttribute('data-name');
            const code = item.getAttribute('data-code');
            const id = item.getAttribute('data-id');
            let isChecked = false;

            item.addEventListener('click', function (event) {
                if (event.target.tagName.toLowerCase() !== 'input' || event.target.type !== 'checkbox') {
                    checkbox.checked = !isChecked;
                    isChecked = !isChecked;
                }

                if (checkbox.checked) {
                    const tableBody = document.getElementById('table-body');
                    const newRow = document.createElement('tr');
                    newRow.innerHTML = `
                    <td>` + code + `</td>
                    <td>` + name + `</td>
                    <td><input type="number" class="form-control" name="materials[` + id + `][input1]"></td>
                    <td><input type="text" class="form-control" name="materials[` + id + `][input2]"></td>`;
                    tableBody.appendChild(newRow);

                    const form = document.getElementById('myForm');

                    const hiddenCodeInput = document.createElement('input');
                    hiddenCodeInput.type = 'hidden';
                    hiddenCodeInput.name = 'materials[' + code + '][code]';
                    hiddenCodeInput.value = code;
                    hiddenCodeInput.id = 'hidden-' + code + '-code';

                    const hiddenNameInput = document.createElement('input');
                    hiddenNameInput.type = 'hidden';
                    hiddenNameInput.name = 'materials[' + code + '][name]';
                    hiddenNameInput.value = name;
                    hiddenNameInput.id = 'hidden-' + code + '-name';

                    const hiddentIdInput = document.createElement('input');
                    hiddenNameInput.type = 'hidden';
                    hiddenNameInput.name = 'materials[' + id + '][name]';
                    hiddenNameInput.value = name;
                    hiddenNameInput.id = 'hidden-' + code + '-id';

                    form.appendChild(hiddentIdInput);
                    form.appendChild(hiddenCodeInput);
                    form.appendChild(hiddenNameInput);
                } else {
                    const tableRows = document.querySelectorAll('#table-body tr');
                    tableRows.forEach(row => {
                        if (row.children[0].textContent === code) {
                            row.remove();
                        }
                    });

                    document.getElementById('hidden-' + code + '-code').remove();
                    document.getElementById('hidden-' + code + '-name').remove();
                    document.getElementById('hidden-' + id + '-name').remove();
                }
            });
        });

        //     thao tac voi du lieu cu
        const checkboxes = document.querySelectorAll('.row-checkbox');

        checkboxes.forEach(function (checkbox) {
            checkbox.addEventListener('click', function () {
                const row = checkbox.parentNode.parentNode;
                const inputs = row.querySelectorAll('input[type="text"], input[type="number"]');
                if (checkbox.checked) {
                    row.classList.add('table-dark');
                    inputs.forEach(function (input) {
                        input.disabled = true;
                    });
                } else {
                    row.classList.remove('table-dark');
                    inputs.forEach(function (input) {
                        input.disabled = false;
                    });
                }
            });
        });

    });


</script>

<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i
        class="bi bi-arrow-up-short"></i></a>
<script src="/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="/assets/js/main.js"></script>
</body>
</html>