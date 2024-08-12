<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="col-lg-3 h-100 p-4 pb-5 rounded" style="background-color: #fff">
    <h2><i class="fa fa-filter mr-2"></i>Filter</h2>
    <div>
        <h4 class="mt-4 mb-2">By category</h4>
        <span>
                <input onchange="this.form.submit()" name="categoryId" id="category0"
                       type="radio"
                       hidden checked
                       value="">
                <label class="btn btn-outline-dark" for="category0">
                        All
                </label>
            </span>
        <c:forEach items="${categories}" var="category">
            <span>
                <c:choose>
                    <c:when test="${param.categoryId == category.id}">
                        <input name="categoryId" id="category${category.id}"
                               type="radio"
                               hidden checked
                               value="${category.id}">
                    </c:when>
                    <c:otherwise>
                        <input onchange="this.form.submit()" name="categoryId" id="category${category.id}"
                               type="radio"
                               hidden
                               value="${category.id}">
                    </c:otherwise>
                </c:choose>

                <label class="btn btn-outline-dark" for="category${category.id}">
                        ${category.name}
                </label>
            </span>
        </c:forEach>
    </div>
    <div>
        <h4 class="mt-4 mb-2">By material</h4>
        <span>
                <input onchange="this.form.submit()" name="materialId" id="material0"
                       type="radio"
                       hidden checked
                       value="">
                <label class="btn btn-outline-dark" for="material0">
                        All
                </label>
            </span>
        <c:forEach items="${materials}" var="material">
            <span>
                <c:choose>
                    <c:when test="${param.materialId == material.id}">
                        <input onchange="this.form.submit()" name="materialId" id="material${material.id}"
                               type="radio"
                               hidden checked
                               value="${material.id}">
                    </c:when>
                    <c:otherwise>
                        <input onchange="this.form.submit()" name="materialId" id="material${material.id}"
                               type="radio"
                               hidden
                               value="${material.id}">
                    </c:otherwise>
                </c:choose>

                <label class="btn btn-outline-dark" for="material${material.id}">
                        ${material.name}
                </label>
            </span>
        </c:forEach>
    </div>
    <div>
        <h4 class="mb-3 mt-4">Price range</h4>
        <div class="mb-2">
            <span>
                <input data-from="" data-to="" name="range" id="range" type="radio"
                       hidden
                       value="${category.id}">
                <label class="btn btn-outline-dark" for="range">
                    None
                </label>
            </span>
            <span>
                <input data-from="" data-to="20" name="range" id="range1" type="radio"
                       hidden
                       value="${category.id}">
                <label class="btn btn-outline-dark" for="range1">
                    Below $20.00
                </label>
            </span>
            <span>
                <input data-from="20" data-to="50" name="range" id="range2" type="radio"
                       hidden
                       value="${category.id}">
                <label class="btn btn-outline-dark" for="range2">
                    $20.00 - $50.00
                </label>
            </span>
            <span>
                <input data-from="50" data-to="100" name="range" id="range3" type="radio"
                       hidden
                       value="${category.id}">
                <label class="btn btn-outline-dark" for="range3">
                    $50.00 - $100.00
                </label>
            </span>
            <span>
                <input data-from="100" data-to="" name="range" id="range4" type="radio"
                       hidden
                       value="${category.id}">
                <label class="btn btn-outline-dark" for="range4">
                    Above $100.00
                </label>
            </span>
        </div>
        <div>
            <input id="rangeFrom" name="from" class="p-2 w-100 rounded" placeholder="From..." value="${param.from}">
            <input id="rangeTo" name="to" class="p-2 mt-2 w-100 rounded" placeholder="To..." value="${param.to}">
            <button class="btn btn-dark w-100 mt-2">Apply</button>
        </div>
    </div>
</div>
<script defer>
    const elements = document.querySelectorAll("input[name='range']");
    const rangeFrom = document.getElementById('rangeFrom');
    const rangeTo = document.getElementById('rangeTo');

    const handleChange = (event) => {
        const element = event.target

        if (element.checked) {
            rangeFrom.value = element.dataset.from
            rangeTo.value = element.dataset.to
        }
    }
    elements.forEach(element => {
        const searchParams = new URLSearchParams(window.location.search);
        element.checked = searchParams.get('from') === element.dataset.from
            && searchParams.get('to') === element.dataset.to
        handleChange({target: element})
    })
    elements.forEach(element => element.addEventListener("change", handleChange));
</script>