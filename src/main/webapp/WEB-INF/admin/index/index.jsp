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
    <style>
        .list-history {
            display: flex;
            align-items: center;
            flex-direction: column;
            position: relative;
            transition: 0.2s;
            border-radius: 8px;
            padding: 12px 8px;

            &:hover {
                cursor: pointer;
                background-color: #eee;
            }

            & + .list-history::after {
                content: "";
                width: 1px;
                height: calc(100% - 16px);
                background-color: #e5e5e5;
                position: absolute;
                left: 0;
            }
        }

        div.card-body {
            padding: 12px;
        }
    </style>
</head>
<body>
<jsp:include page="../../common/admin-header.jsp"></jsp:include>
<jsp:include page="../../common/admin-sidebar.jsp"></jsp:include>

<main id="main" class="main">
    <div>
        <div class="d-flex justify-content-between">
            <h1>Dashboard</h1>
            <c:if test="${not empty stores}">
                <form class="d-flex align-items-center" method="get" action="/admin">
                    <h2 class="pe-2 mb-0">Store:</h2>
                    <input hidden name="dateType" value="${param.dateType}"/>
                    <select onchange="this.form.submit()" name="storeId" class="p-2" style="width: 200px">
                        <option value="">All</option>
                        <c:forEach items="${stores}" var="item">
                            <c:choose>
                                <c:when test="${param.storeId == item.id}">
                                    <option value="${item.id}" selected>${item.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${item.id}">${item.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </form>
            </c:if>
        </div>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item fs-5"><a href="/index">Home</a></li>
                <li class="breadcrumb-item fs-5 active">Dashboard</li>
            </ol>
        </nav>
    </div><!-- End Page Title -->

    <section class="section dashboard">
        <div class="row">
            <!-- Left side columns -->
            <div class="col-lg-8">
                <div class="row">
                    <!-- Statistics Card -->
                    <div class="col-xxl-12 col-md-12">
                        <div class="card info-card sales-card">
                            <div class="card-body">
                                <jsp:include page="time-sorter.jsp">
                                    <jsp:param name="name" value="To do list"/>
                                    <jsp:param name="sort" value="${true}"/>
                                </jsp:include>

                                <div class="d-flex align-items-center">
                                    <a class="col-3 list-history">
                                        <span class="text-primary fw-bold fs-4">${status0  }</span>
                                        <h5 class="text-dark">Pending orders</h5>
                                    </a>
                                    <a class="col-3 list-history">
                                        <span class="text-primary fw-bold fs-4">${status1  }</span>
                                        <h5 class="text-dark">Confirmed orders</h5>
                                    </a>
                                    <a class="col-3 list-history">
                                        <span class="text-primary fw-bold fs-4">${status2 }</span>
                                        <h5 class="text-dark">Delivering orders</h5>
                                    </a>
                                    <a class="col-3 list-history">
                                        <span class="text-primary fw-bold fs-4">${status3  }</span>
                                        <h5 class="text-dark">Delivered orders</h5>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div><!-- End Statistics Card -->

                    <!-- Sales Card -->
                    <div class="col-xxl-4 col-md-6">
                        <div class="card info-card sales-card">

                            <div class="card-body">
                                <jsp:include page="time-sorter.jsp">
                                    <jsp:param name="name" value="Sales"/>
                                </jsp:include>

                                <div class="d-flex align-items-center">
                                    <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                                        <i class="bi bi-cart"></i>
                                    </div>
                                    <div class="ps-3">
                                        <h6>${statistic.sale} orders</h6>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div><!-- End Sales Card -->

                    <!-- Revenue Card -->
                    <div class="col-xxl-4 col-md-6">
                        <div class="card info-card revenue-card">
                            <div class="card-body">
                                <jsp:include page="time-sorter.jsp">
                                    <jsp:param name="name" value="Revenue"/>
                                </jsp:include>

                                <div class="d-flex align-items-center">
                                    <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                                        <i class="bi bi-currency-dollar"></i>
                                    </div>
                                    <div class="ps-3">
                                        <h6><fmt:formatNumber value="${statistic.revenue}" type="currency"/></h6>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div><!-- End Revenue Card -->

                    <!-- Customers Card -->
                    <div class="col-xxl-4 col-md-12">

                        <div class="card info-card customers-card">
                            <div class="card-body">
                                <jsp:include page="time-sorter.jsp">
                                    <jsp:param name="name" value="Customers"/>
                                </jsp:include>

                                <div class="d-flex align-items-center">
                                    <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                                        <i class="bi bi-people"></i>
                                    </div>
                                    <div class="ps-3">
                                        <h6>${statistic.customer} clients</h6>
                                    </div>
                                </div>

                            </div>
                        </div>

                    </div><!-- End Customers Card -->

                    <!-- Reports -->
                    <div class="col-12">
                        <div class="card">
                            <div class="card-body">
                                <jsp:include page="time-sorter.jsp">
                                    <jsp:param name="name" value="Reports"/>
                                    <jsp:param name="sort" value="${true}"/>
                                </jsp:include>
                                <!-- Line Chart -->
                                <div id="reportsChart"></div>
                                <!-- End Line Chart -->
                            </div>
                        </div>
                    </div><!-- End Reports -->


                    <jsp:include page="recent-sale.jsp"></jsp:include>
                    <jsp:include page="top-selling.jsp"></jsp:include>

                </div>
            </div><!-- End Left side columns -->

            <!-- Right side columns -->
            <div class="col-lg-4">

                <jsp:include page="recent-activity.jsp"></jsp:include>
                <!-- Budget Report -->
                <div class="card">


                    <div class="card-body pb-0">
                        <jsp:include page="time-sorter.jsp">
                            <jsp:param name="name" value="Budget Report"/>
                            <jsp:param name="sort" value="${false}"/>
                        </jsp:include>

                        <div id="budgetChart" style="min-height: 400px;" class="echart"></div>

                        <script>
                            document.addEventListener("DOMContentLoaded", () => {
                                var budgetChart = echarts.init(document.querySelector("#budgetChart")).setOption({
                                    legend: {
                                        data: ['Allocated Budget', 'Actual Spending']
                                    },
                                    radar: {
                                        // shape: 'circle',
                                        indicator: [{
                                            name: 'Sales',
                                            max: 6500
                                        },
                                            {
                                                name: 'Administration',
                                                max: 16000
                                            },
                                            {
                                                name: 'Information Technology',
                                                max: 30000
                                            },
                                            {
                                                name: 'Customer Support',
                                                max: 38000
                                            },
                                            {
                                                name: 'Development',
                                                max: 52000
                                            },
                                            {
                                                name: 'Marketing',
                                                max: 25000
                                            }
                                        ]
                                    },
                                    series: [{
                                        name: 'Budget vs spending',
                                        type: 'radar',
                                        data: [{
                                            value: [4200, 3000, 20000, 35000, 50000, 18000],
                                            name: 'Allocated Budget'
                                        },
                                            {
                                                value: [5000, 14000, 28000, 26000, 42000, 21000],
                                                name: 'Actual Spending'
                                            }
                                        ]
                                    }]
                                });
                            });
                        </script>

                    </div>
                </div><!-- End Budget Report -->

                <!-- Website Traffic -->
                <div class="card">

                    <div class="card-body pb-0">
                        <jsp:include page="time-sorter.jsp">
                            <jsp:param name="name" value="Website Traffic"/>
                            <jsp:param name="sort" value="${false}"/>
                        </jsp:include>
                        <div id="trafficChart" style="min-height: 400px;" class="echart"></div>

                        <script>
                            document.addEventListener("DOMContentLoaded", () => {
                                echarts.init(document.querySelector("#trafficChart")).setOption({
                                    tooltip: {
                                        trigger: 'item'
                                    },
                                    legend: {
                                        top: '5%',
                                        left: 'center'
                                    },
                                    series: [{
                                        name: 'Access From',
                                        type: 'pie',
                                        radius: ['40%', '70%'],
                                        avoidLabelOverlap: false,
                                        label: {
                                            show: false,
                                            position: 'center'
                                        },
                                        emphasis: {
                                            label: {
                                                show: true,
                                                fontSize: '18',
                                                fontWeight: 'bold'
                                            }
                                        },
                                        labelLine: {
                                            show: false
                                        },
                                        data: [{
                                            value: 1048,
                                            name: 'Search Engine'
                                        },
                                            {
                                                value: 735,
                                                name: 'Direct'
                                            },
                                            {
                                                value: 580,
                                                name: 'Email'
                                            },
                                            {
                                                value: 484,
                                                name: 'Union Ads'
                                            },
                                            {
                                                value: 300,
                                                name: 'Video Ads'
                                            }
                                        ]
                                    }]
                                });
                            });
                        </script>

                    </div>
                </div><!-- End Website Traffic -->

            </div><!-- End Right side columns -->
        </div>
    </section>

    <ul id="statistics" class="d-none">
        <c:forEach items="${statistics}" var="item">
            <li data-date="${item.statTime}" data-sale="${item.sale}" data-revenue=
                    "${item.revenue}" data-customer="${item.customer}"></li>
        </c:forEach>
    </ul>
</main>

<a href="#" class=" back-to-top d-flex align-items-center justify-content-center"><i
        class="bi bi-arrow-up-short"></i></a>
<script defer>
    document.addEventListener("DOMContentLoaded", () => {
        const list = document.getElementById("statistics").querySelectorAll("li")
        const sales = []
        const customers = []
        const revenues = []
        const dates = []

        list.forEach(item => {
            sales.push(item.dataset.sale)
            customers.push(item.dataset.customer)
            revenues.push(item.dataset.revenue)
            dates.push(item.dataset.date)
        })

        new ApexCharts(document.querySelector("#reportsChart"), {
            series: [{
                name: 'Sales',
                data: sales,
            }, {
                name: 'Revenue',
                data: revenues
            }, {
                name: 'Customers',
                data: customers
            }],
            chart: {
                height: 350,
                type: 'area',
                toolbar: {show: false},
            },
            markers: {
                size: 4
            },
            colors: ['#4154f1', '#2eca6a', '#ff771d'],
            fill: {
                type: "gradient",
                gradient: {
                    shadeIntensity: 1,
                    opacityFrom: 0.3,
                    opacityTo: 0.4,
                    stops: [0, 90, 100]
                }
            },
            dataLabels: {enabled: false},
            stroke: {
                curve: 'smooth',
                width: 2
            },
            xaxis: {
                type: 'datetime',
                categories: dates
            },
            tooltip: {
                x: {format: 'dd/MM/yy HH:mm'},
            }
        }).render();
    });
</script>
<!-- Vendor JS Files -->
<script src="/assets/vendor/apexcharts/apexcharts.min.js"></script>
<script src="/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="/assets/vendor/echarts/echarts.min.js"></script>

<!-- Template Main JS File -->
<script src="/assets/js/main.js"></script>
</body>
</html>