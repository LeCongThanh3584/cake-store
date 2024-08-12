<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page errorPage="error.jsp" %>
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
    <link rel="stylesheet" href="/css/nice-select.css" type="text/css">
    <link rel="stylesheet" href="/css/owl.carousel.min.css" type="text/css">
    <link rel="stylesheet" href="/css/slicknav.min.css" type="text/css">
    <link rel="stylesheet" href="/css/style.css" type="text/css">

    <style>
        input[type=radio]:checked + label {
            color: white;
            background-color: orange;
        }
    </style>
</head>
<body class="vh-100">
<div class="d-flex flex-column h-100">
    <jsp:include page="../common/header.jsp"></jsp:include>

    <!-- Breadcrumb Begin -->
    <div class="breadcrumb-option">
        <div class="container">
            <div class="row">
                <div class="col-lg-6 col-md-6 col-sm-6">
                    <div class="breadcrumb__text">
                        <h2>Product detail</h2>
                    </div>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-6">
                    <div class="breadcrumb__links">
                        <a href="/index">Home</a>
                        <a href="/shop">Shop</a>
                        <span>${cake.name}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Breadcrumb End -->

    <!-- Shop Details Section Begin -->
    <section class="product-details spad">
        <div class="container">
            <div class="row">
                <div class="col-lg-6">
                    <div class="product__details__img">
                        <div class="product__details__big__img">
                            <img class="big_img" src="${cake.image}" alt="">
                        </div>
                        <div class="product__details__thumb">
                            <div class="pt__item active">
                                <img data-imgbigurl="/img/shop/details/product-big-2.jpg"
                                     src="/img/shop/details/product-big-2.jpg" alt="">
                            </div>
                            <div class="pt__item">
                                <img data-imgbigurl="/img/shop/details/product-big-1.jpg"
                                     src="/img/shop/details/product-big-1.jpg" alt="">
                            </div>
                            <div class="pt__item">
                                <img data-imgbigurl="/img/shop/details/product-big-4.jpg"
                                     src="/img/shop/details/product-big-4.jpg" alt="">
                            </div>
                            <div class="pt__item">
                                <img data-imgbigurl="/img/shop/details/product-big-3.jpg"
                                     src="/img/shop/details/product-big-3.jpg" alt="">
                            </div>
                            <div class="pt__item">
                                <img data-imgbigurl="/img/shop/details/product-big-5.jpg"
                                     src="/img/shop/details/product-big-5.jpg" alt="">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-6">
                    <div class="product__details__text">
                        <div class="product__label">${cake.category}</div>
                        <h4>${cake.name}</h4>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, eiusmod tempor incididunt ut labore
                            et dolore magna aliqua. Quis ipsum suspendisse ultrices gravida</p>
                        <ul>
                            <li>Size: <span>${cake.size}cm x ${cake.length}cm x ${cake.height}cm</span></li>
                            <li>Weight: <span>${cake.weight} gr</span></li>
                            <li>Color: <span>${cake.color}</span></li>
                        </ul>
                        <c:choose>
                            <c:when test="${stores.size() == 1}">
                                <div class="mb-4 border border-secondary w-100 p-2">
                                        ${stores[0].name} - ${stores[0].address}
                                </div>
                            </c:when>
                            <c:otherwise>
                                <form method="get" class="mb-4">
                                    <select onchange="this.form.submit()" name="storeId"
                                            class="address-option w-100 p-2"
                                            style="font-size: 18px;">
                                        <c:choose>
                                            <c:when test="${param.storeId} == ''">
                                                <c:forEach var="item" items="${stores}" varStatus="loop">
                                                    <option value="${item.id}">${item.name} - ${item.address}</option>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach var="item" items="${stores}">
                                                    <option ${item.id == param.storeId ? "selected" : ""}
                                                            value="${item.id}">${item.name} - ${item.address}</option>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </form>
                            </c:otherwise>
                        </c:choose>

                        <form class="product__details__option" method="post" action="/cart">
                            <div class="mb-4">
                                <c:forEach var="cakeStore" items="${cake.cakeStores}" varStatus="loop">
                                    <span class="mr-2">
                                        <c:choose>
                                            <c:when test="${loop.index == 0}">
                                                <input name="cakeStoreId" id="cakeStore${cakeStore.id}" type="radio"
                                                       checked
                                                       hidden
                                                       value="${cakeStore.id}">
                                            </c:when>
                                            <c:otherwise>
                                                <input name="cakeStoreId" id="cakeStore${cakeStore.id}" type="radio"
                                                       hidden
                                                       value="${cakeStore.id}">
                                            </c:otherwise>
                                        </c:choose>
                                        <label class="btn btn-outline-warning" for="cakeStore${cakeStore.id}">
                                            <fmt:formatNumber value="${cakeStore.price}" type="currency"/>
                                            | Exp:<fmt:formatDate value="${cakeStore.expirationDate}"
                                                                  pattern="yyyy-MM-dd"/>
                                        </label>
                                    </span>
                                </c:forEach>
                            </div>
                            <div class="quantity">
                                <div class="pro-qty">
                                    <input type="text" name="quantity" value="1">
                                </div>
                            </div>
                            <button class="primary-btn">Add to cart</button>
                            <a href="#" class="heart__btn"><span class="icon_heart_alt"></span></a>
                        </form>
                    </div>
                </div>
            </div>
            <div class="product__details__tab">
                <div class="col-lg-12">
                    <ul class="nav nav-tabs" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" data-toggle="tab" href="#tabs-1" role="tab">Description</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" data-toggle="tab" href="#tabs-2" role="tab">Additional information</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" data-toggle="tab" href="#tabs-3" role="tab">Previews(1)</a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane active" id="tabs-1" role="tabpanel">
                            <div class="row d-flex justify-content-center">
                                <div class="col-lg-8">
                                    <p>This delectable Strawberry Pie is an extraordinary treat filled with sweet and
                                        tasty chunks of delicious strawberries. Made with the freshest ingredients, one
                                        bite will send you to summertime. Each gift arrives in an elegant gift box and
                                        arrives with a greeting card of your choice that you can personalize online!</p>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane" id="tabs-2" role="tabpanel">
                            <div class="row d-flex justify-content-center">
                                <div class="col-lg-8">
                                    <p>This delectable Strawberry Pie is an extraordinary treat filled with sweet and
                                        tasty chunks of delicious strawberries. Made with the freshest ingredients, one
                                        bite will send you to summertime. Each gift arrives in an elegant gift box and
                                        arrives with a greeting card of your choice that you can personalize online!2
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane" id="tabs-3" role="tabpanel">
                            <div class="row d-flex justify-content-center">
                                <div class="col-lg-8">
                                    <p>This delectable Strawberry Pie is an extraordinary treat filled with sweet and
                                        tasty chunks of delicious strawberries. Made with the freshest ingredients, one
                                        bite will send you to summertime. Each gift arrives in an elegant gift box and
                                        arrives with a greeting card of your choice that you can personalize online!3
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Shop Details Section End -->

    <!-- Related Products Section Begin -->
    <section class="related-products spad">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <div class="section-title">
                        <h2>Related Products</h2>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="related__products__slider owl-carousel">
                    <div class="col-lg-3">
                        <div class="product__item">
                            <div class="product__item__pic set-bg" data-setbg="/img/shop/product-1.jpg">
                                <div class="product__label">
                                    <span>Cupcake</span>
                                </div>
                            </div>
                            <div class="product__item__text">
                                <h6><a href="#">Dozen Cupcakes</a></h6>
                                <div class="product__item__price">$32.00</div>
                                <div class="cart_add">
                                    <a href="#">Add to cart</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="product__item">
                            <div class="product__item__pic set-bg" data-setbg="/img/shop/product-2.jpg">
                                <div class="product__label">
                                    <span>Cupcake</span>
                                </div>
                            </div>
                            <div class="product__item__text">
                                <h6><a href="#">Cookies and Cream</a></h6>
                                <div class="product__item__price">$30.00</div>
                                <div class="cart_add">
                                    <a href="#">Add to cart</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="product__item">
                            <div class="product__item__pic set-bg" data-setbg="/img/shop/product-3.jpg">
                                <div class="product__label">
                                    <span>Cupcake</span>
                                </div>
                            </div>
                            <div class="product__item__text">
                                <h6><a href="#">Gluten Free Mini Dozen</a></h6>
                                <div class="product__item__price">$31.00</div>
                                <div class="cart_add">
                                    <a href="#">Add to cart</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="product__item">
                            <div class="product__item__pic set-bg" data-setbg="/img/shop/product-4.jpg">
                                <div class="product__label">
                                    <span>Cupcake</span>
                                </div>
                            </div>
                            <div class="product__item__text">
                                <h6><a href="#">Cookie Dough</a></h6>
                                <div class="product__item__price">$25.00</div>
                                <div class="cart_add">
                                    <a href="#">Add to cart</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="product__item">
                            <div class="product__item__pic set-bg" data-setbg="/img/shop/product-5.jpg">
                                <div class="product__label">
                                    <span>Cupcake</span>
                                </div>
                            </div>
                            <div class="product__item__text">
                                <h6><a href="#">Vanilla Salted Caramel</a></h6>
                                <div class="product__item__price">$05.00</div>
                                <div class="cart_add">
                                    <a href="#">Add to cart</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3">
                        <div class="product__item">
                            <div class="product__item__pic set-bg" data-setbg="/img/shop/product-6.jpg">
                                <div class="product__label">
                                    <span>Cupcake</span>
                                </div>
                            </div>
                            <div class="product__item__text">
                                <h6><a href="#">German Chocolate</a></h6>
                                <div class="product__item__price">$14.00</div>
                                <div class="cart_add">
                                    <a href="#">Add to cart</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Related Products Section End -->

    <jsp:include page="../common/footer.jsp"></jsp:include>
</div>

<!-- Js Plugins -->
<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/jquery.nice-select.min.js"></script>
<script src="/js/jquery.slicknav.js"></script>
<script src="/js/owl.carousel.min.js"></script>
<script src="/js/jquery.nicescroll.min.js"></script>
<script src="/js/main.js"></script>
</body>
</html>