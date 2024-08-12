<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="row mx-0 ">
    <div class="col-lg-12 d-flex px-3">
        <select class="border-0 p-3" name="storeId" style="font-size: 18px;">
            <c:choose>
                <c:when test="${param.store} == ''">
                    <option selected value="">Stores</option>
                    <c:forEach var="store" items="${stores}">
                        <option value="${store.name}">${store.name} - ${store.address}</option>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <option value="">Stores</option>
                    <c:forEach var="store" items="${stores}">
                        <option ${store.id == param.storeId ? "selected" : ""}
                                value="${store.id}">${store.name} - ${store.address}</option>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </select>
        <input class="border-0 flex-grow-1 p-3" type="text" name="query" placeholder="Search" value="${param.query}">
        <button class="border-0 p-3 bg-white" type="submit"><i class="fa fa-search"></i></button>
    </div>
    <div class="col-lg-12 mt-3 px-3 d-flex justify-content-end">
        <div class="ml-4">
            <label>Sort by</label>
            <select id="sort-option" class="p-3 ml-2 border-0 rounded">
                <option data-sort="production_date" data-direction="asc" value="1">Newest baked</option>
                <option data-sort="expiration_date" data-direction="desc" value="2">Longest expire date</option>
                <option data-sort="min_price" data-direction="asc" value="3">Low to high</option>
                <option data-sort="max_price" data-direction="desc" value="4">High to low</option>
            </select>
            <input name="sortBy" id="sortBy" value="" hidden="">
            <input name="direction" id="sortDirection" value="" hidden="">
        </div>
    </div>
</div>
<script defer>
    const sortDirection = document.getElementById('sortDirection');
    const sortBy = document.getElementById('sortBy');
    const sortOption = document.getElementById('sort-option');

    const handleOption = () => {
        const option = sortOption.querySelector('option[value="' + sortOption.value + '"]')
        sortBy.value = option.dataset.sort
        sortDirection.value = option.dataset.direction
    }

    sortOption.addEventListener("change", handleOption)

    sortOption.querySelectorAll('option').forEach(element => {
        const searchParams = new URLSearchParams(window.location.search);
        if (searchParams.get('sortBy') === element.dataset.sort && searchParams.get('direction') === element.dataset.direction) {
            element.selected = 'selected'

        }
    })
    handleOption()
</script>