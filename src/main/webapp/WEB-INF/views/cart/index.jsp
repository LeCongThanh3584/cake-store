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

    <style>
        input[type='checkbox'] {
            height: 24px;
            width: 24px;
            margin-left: 8px;
            margin-right: 8px;
        }

        .address-option {
            padding: 8px;
        }

        .cart__total .submit.disabled {
            background-color: #666;
            user-select: none;
            pointer-events: none;
        }

        ul.address-information {
            list-style: none;

            &.hidden {
                display: none;
            }

            > li {
                font-size: 16px;
                font-weight: 600;
                color: #999999;

                > span {
                    margin-right: 4px;
                    color: #666;
                }
            }
        }
    </style>
</head>
<body class="vh-100">
<div class="d-flex flex-column h-100">
    <jsp:include page="../../common/header.jsp"></jsp:include>

    <!-- Breadcrumb Begin -->
    <div class="breadcrumb-option">
        <div class="container">
            <div class="row">
                <div class="col-lg-6 col-md-6 col-sm-6">
                    <div class="breadcrumb__text">
                        <h2>Shopping cart</h2>
                    </div>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-6">
                    <div class="breadcrumb__links">
                        <a href="/index">Home</a>
                        <span>Shopping cart</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Breadcrumb End -->

    <!-- Shopping Cart Section Begin -->
    <section class="shopping-cart spad">
        <div class="container">
            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                        ${error}
                </div>
            </c:if>

            <div class="row">
                <div class="col-lg-8">
                    <div class="shopping__cart__table">
                        <table>
                            <thead>
                            <tr>
                                <th><input id="checkAll" type="checkbox"></th>
                                <th>Product</th>
                                <th>Quantity</th>
                                <th>Total</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${carts.size() == 0}">
                                <tr>
                                    <td colspan="4">
                                        <h2 class="text-center">Empty Cart</h2>
                                    </td>
                                </tr>
                            </c:if>
                            <jsp:include page="product.jsp"></jsp:include>
                            </tbody>
                        </table>
                    </div>
                    <div class="row">
                        <div class="col-lg-6 col-md-6 col-sm-6">
                            <div class="continue__btn">
                                <a href="/shop">Continue Shopping</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4">
                    <div class="cart__discount">
                        <h6 class="mb-2">Address</h6>
                        <div>
                            <select class="address-option w-100">
                                <option value="">Choose addresses</option>
                                <c:forEach var="item" items="${addresses}">
                                    <option data-name="${item.name}"
                                            data-district="${item.district}"
                                            data-ward="${item.ward}"
                                            data-province="${item.province}"
                                            data-phone="${item.phone}"
                                            value="${item.id}">
                                            ${item.name} - ${item.district} - ${item.phone}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class=" my-2 w-100">
                            <ul class="address-information">
                            </ul>
                        </div>
                    </div>
                    <div class="cart__total">
                        <h6>Cart total</h6>
                        <ul>
                            <li class="cart-number">Number <span>0</span></li>
                            <li class="cart-total">Total <span>$ 0</span></li>
                        </ul>
                        <button class="submit disabled primary-btn">Proceed to checkout</button>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <form id="cart-form" action="/checkout" method="get" hidden></form>
    <form id="update-form" action="/cart/update" method="get" hidden></form>
    <!-- Shopping Cart Section End -->

    <jsp:include page="../../common/footer.jsp"></jsp:include>
    <script defer>
        const submitButton = document.querySelector('.cart__total .submit')
        const formatter = new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: 'USD',
        });
        // CHECK BOX COMPONENTS
        const checkAll = document.querySelector("#checkAll")
        const checkBoxs = document.querySelectorAll("input[type='checkbox'].checkbox")
        const checkList = {}

        // ADDRESS COMPONENTS
        const addressOption = document.querySelector('.address-option')
        const addressInformation = document.querySelector('.address-information')
        let address

        // CART FORM COMPONENTS
        const cartForm = document.querySelector('form#cart-form')
        const cartNumber = document.querySelector('.cart__total .cart-number span')
        const cartTotal = document.querySelector('.cart__total .cart-total span')

        const handleButton = () => {
            if (Object.keys(checkList).length > 0) {
                submitButton.classList.remove('disabled')
            } else {
                submitButton.classList.add('disabled')
            }
            let number = 0
            let total = 0
            Object.keys(checkList).forEach(index => {
                const element = checkList[index]
                number += parseInt(element.dataset.quantity)
                total += parseInt(element.dataset.price)
            })
            cartNumber.innerHTML = number
            cartTotal.innerHTML = formatter.format(total)

        }
        const handleOnclick = (item) => {
            if (item.checked) {
                checkList[item.dataset.index] = item
            } else if (checkList[item.dataset.index]) {
                delete checkList[item.dataset.index]
            }
            checkAll.checked = Object.keys(checkList).length === checkBoxs.length
            handleButton()
        }
        const handleAddress = () => {
            if (!addressOption.value) {
                address = null
                addressInformation.innerHTML = ''
            } else {
                address = addressOption.querySelector('option[value="' + addressOption.value + '"]')
                addressInformation.innerHTML = "" +
                    "<li><span>Name:</span>" + address.dataset.name + "</li>" +
                    "<li><span>District:</span>" + address.dataset.district + "</li> " +
                    "<li><span>Ward:</span>" + address.dataset.ward + "</li> " +
                    "<li><span>Province:</span>" + address.dataset.province + "</li> " +
                    "<li><span>Phone:</span>" + address.dataset.phone + "</li>"
            }
        }
        submitButton.addEventListener('click', function () {
            if (Object.keys(checkList).length > 0) {
                cartForm.innerHTML = address && '' +
                    '<input name="receiver" value="' + address.dataset.name + '">' +
                    '<input name="phone" value="' + address.dataset.phone + '">' +
                    '<input name="district" value="' + address.dataset.district + '">' +
                    '<input name="ward" value="' + address.dataset.ward + '">' +
                    '<input name="province" value="' + address.dataset.province + '">'
                Object.values(checkList).forEach(item => {
                    const element = document.createElement("input")
                    element.name = 'cartId'
                    element.value = item.dataset.index
                    element.hidden = true
                    cartForm.appendChild(element)
                })
                cartForm.submit()
            }
        })
        checkBoxs.forEach(item => {
            handleOnclick(item)
            item.addEventListener('change', function () {
                handleOnclick(item)
            })
        })
        checkAll.addEventListener('change', function () {
            if (checkAll.checked) {
                checkBoxs.forEach(item => {
                    item.checked = true
                    checkList[item.dataset.index] = item
                })
            } else {
                checkBoxs.forEach(item => {
                    item.checked = false
                    delete checkList[item.dataset.index]
                })
            }
            handleButton()
        })
        addressOption.addEventListener('change', handleAddress)
        handleAddress()
    </script>
    <script defer>
        const cartItems = document.querySelectorAll('tr.cart-item')
        const updateForm = document.getElementById('update-form')

        cartItems.forEach(cartItem => {
            const inputText = cartItem.querySelector('input.cart-input')
            const updateButton = cartItem.querySelector('td.cart__close button')
            let newValue = inputText.value

            inputText.addEventListener('click', function (e) {
                e.stopPropagation()
            })
            inputText.addEventListener('focus', function () {
                updateButton.classList.remove("d-none")
                inputText.classList.remove("border-0")
            })
            updateButton.addEventListener('click', function () {
                updateForm.innerHTML = "" +
                    "<input name='cartId' value='" + inputText.dataset.index + "'hidden'>" +
                    "<input name='quantity' value='" + inputText.value + "'hidden'>"
                updateForm.submit()
            })
            document.addEventListener('click', function (e) {
                inputText.classList.add("border-0")
                if (inputText.value === newValue) {
                    updateButton.classList.add("d-none")
                }
            })
        })
    </script>
</div>

<!-- Js Plugins -->
<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/jquery.slicknav.js"></script>
<script src="/js/owl.carousel.min.js"></script>
<script src="/js/jquery.nicescroll.min.js"></script>
</body>
</html>