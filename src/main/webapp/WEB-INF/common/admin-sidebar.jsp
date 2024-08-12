<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<aside id="sidebar" class="sidebar">
    <ul class="sidebar-nav" id="sidebar-nav">
        <li class="nav-item">
            <a class="nav-link collapsed" href="/admin">
                <i class="bi bi-grid"></i>
                <span>Dashboard</span>
            </a>
        </li><!-- End Dashboard Nav -->

        <c:if test="${sessionScope.get('role') == 0}">
            <li class="nav-heading fs-6">Super admin</li>
            <li class="nav-item">
                <a class="nav-link collapsed" href="#">
                    <i class="bi bi-journal-text"></i><span>Manage Users</span>
                </a>
                <ul id="forms-nav" class="nav-content  " data-bs-parent="#sidebar-nav">
                    <li>
                        <a href="/admin/users">
                            <i class="bi bi-circle"></i><span>View Users</span>
                        </a>
                    </li>
                    <li>
                        <a href="/admin/add-new-user">
                            <i class="bi bi-circle"></i><span>Add User</span>
                        </a>
                    </li>
                </ul>
            </li>
            <!-- End Users Nav -->
            <li class="nav-item">
                <a class="nav-link collapsed" href="#">
                    <i class="bi bi-bar-chart"></i><span>Manage Cakes</span>
                </a>
                <ul id="cake-nav" class="nav-content  " data-bs-parent="#sidebar-nav">
                    <li>
                        <a href="/admin/cake/view">
                            <i class="bi bi-circle"></i><span>Cakes</span>
                        </a>
                    </li>
                    <li>
                        <a href="/admin/material/view">
                            <i class="bi bi-circle"></i><span>Material</span>
                        </a>
                    </li>
                    <li>
                        <a href="/admin/category/view">
                            <i class="bi bi-circle"></i><span>Category</span>
                        </a>
                    </li>
                </ul>
            </li>
            <!-- End Cakes Nav -->
        </c:if>

        <li class="nav-heading fs-6">Admin</li>

        <li class="nav-item">
            <a class="nav-link collapsed" href="#">
                <i class="bi bi-menu-button-wide"></i><span>Manage Stores</span>
            </a>
            <ul id="components-nav " class="nav-content  " data-bs-parent="#sidebar-nav">

                <li>
                    <a href="/admin/stores">
                        <i class="bi bi-circle"></i><span>View Stores</span>
                    </a>
                </li>
                <c:if test="${sessionScope.get('role') == 0}">
                    <li>
                        <a href="/admin/add-store">
                            <i class="bi bi-circle"></i><span>Add Store</span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </li><!-- End Stores Nav -->
        <li class="nav-item">
            <a class="nav-link collapsed" href="#">
                <i class="bi bi-layout-text-window-reverse"></i><span>Manage Orders</span>
            </a>
            <ul id="tables-nav" class="nav-content  " data-bs-parent="#sidebar-nav">
                <li>
                    <a href="/admin/orders">
                        <i class="bi bi-circle"></i><span>View Orders</span>
                    </a>
                </li>
                <li>
                    <a href="/admin/create-order">
                        <i class="bi bi-circle"></i><span>Create Order</span>
                    </a>
                </li>
            </ul>
        </li><!-- End Orders Nav -->

        <li class="nav-heading fs-6">Pages</li>
        <li class="nav-item">
            <a class="nav-link collapsed" href="/admin/profile">
                <i class="bi bi-person"></i>
                <span>Profile</span>
            </a>
        </li><!-- End Profile Page Nav -->

        <li class="nav-item">
            <a class="nav-link collapsed" href="/shop">
                <i class="bi bi-file-earmark"></i>
                <span>Shop</span>
            </a>
        </li><!-- End Blank Page Nav -->

    </ul>

</aside>
<!-- End Sidebar-->
