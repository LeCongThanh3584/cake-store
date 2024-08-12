<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="shop__last__option">
    <div class="row">
        <div class="col-lg-12 ">
            <div class="shop__pagination d-flex justify-content-center">
                <c:if test="${page >= 2}">
                    <a href="?categoryId=${param.categoryId}&materialId=${param.materialId}&from=${param.from}&to=${param.to}&storeId=${param.storeId}&query=&${param.query}sortBy=${param.sortBy}&direction=${param.direction}&page=${page-1}"><span
                            class="arrow_carrot-left"></span></a>
                </c:if>
                <c:forEach begin="${startPage}" end="${endPage}" var="count">
                    <a href="?categoryId=${param.categoryId}&materialId=${param.materialId}&from=${param.from}&to=${param.to}&storeId=${param.storeId}&query=&${param.query}sortBy=${param.sortBy}&direction=${param.direction}&page=${count}">${count}</a>
                </c:forEach>
                <c:if test="${page < endPage}">
                    <a href="?categoryId=${param.categoryId}&materialId=${param.materialId}&from=${param.from}&to=${param.to}&storeId=${param.storeId}&query=&${param.query}sortBy=${param.sortBy}&direction=${param.direction}&page=${page+1}"><span
                            class="arrow_carrot-right"></span></a>
                </c:if>
            </div>
        </div>
        <div class="col-lg-12">
            <div class="shop__last__text">
                <p>
                    Showing ${pageSize * (page - 1) + 1}-${pageSize * (page - 1) + cakes.size()}
                    of ${pageCount} results</p>
            </div>
        </div>
    </div>
</div>