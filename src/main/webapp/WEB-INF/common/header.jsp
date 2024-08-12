<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Offcanvas Menu Begin -->
<div class="offcanvas-menu-overlay"></div>
<div class="offcanvas-menu-wrapper">
    <div class="offcanvas__cart">
        <div class="offcanvas__cart__links">
            <a href="#" class="search-switch"><img src="/img/icon/search.png" alt=""></a>
            <a href="#"><img src="/img/icon/heart.png" alt=""></a>
        </div>
        <div class="offcanvas__cart__item">
            <a href="#"><img src="/img/icon/cart.png" alt=""> <span>0</span></a>
            <div class="cart__price">Cart: <span>$0.00</span></div>
        </div>
    </div>
    <div class="offcanvas__logo">
        <a href="/index"><img src="/img/logo.png" alt=""></a>
    </div>
    <div id="mobile-menu-wrap"></div>
    <div class="offcanvas__option">
        <ul>
            <li><a href="#">Sign in</a> <span class="arrow_carrot-down"></span></li>
        </ul>
    </div>
</div>
<!-- Offcanvas Menu End -->

<!-- Header Section Begin -->
<header class="header">
    <div class="header__top">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div class="header__top__inner d-flex align-items-center justify-content-between">
                        <div class="header__top__left">
                            <div class="header__logo">
                                <a href="/index"><img src="/img/logo.png" alt=""></a>
                            </div>
                            <ul>
                                <li><a class="active" href="/index">Home</a></li>
                                <li><a href="/shop">Shop</a></li>
                                <li><a href="/contact">Contact</a></li>
                            </ul>
                        </div>
                        <div>

                        </div>
                        <div class="header__top__right">
                            <div class="header__top__right__cart">
                                <a href="/cart"><img src="/img/icon/cart.png" alt=""> <span>0</span></a>
                            </div>
                            <c:choose>
                                <c:when test="${sessionScope.username != null ? true : false}">
                                    <div class="header__top__right__links">
                                        <a href="/user-info">
                                            <img src="/img/icon/person.png" alt="">
                                        </a>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="header__top__right__links">
                                        <a class="btn btn-outline-dark ml-2" href="/login">Sign in</a>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
            <div class="canvas__open"><i class="fa fa-bars"></i></div>
        </div>
    </div>
</header>
<!-- Header Section End -->