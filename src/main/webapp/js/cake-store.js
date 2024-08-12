const clickDeleteCakeStore = (cakeStoreId, cakeName) => {
    document.getElementById("cakeName").innerText = cakeName;
    document.getElementById("cakeStoreId").value = cakeStoreId;
}

const listItem = document.querySelectorAll(".dropdown-item");
const inputCakeId = document.getElementById("cakeId");
const buttonSelect = document.getElementById("dropdownMenuSelectCake");

const searchInput = document.getElementById("searchInput");

//select cake
listItem.forEach(item => {
    item.addEventListener('click', () => {
        const selectedValue = item.getAttribute("data-value");
        inputCakeId.value = selectedValue;
        buttonSelect.innerHTML = item.innerHTML;
    })
})

//search cake
searchInput.addEventListener('input', () => {
    const searchKeyword =  searchInput.value.toLowerCase();

    listItem.forEach(item => {
        const content = item.textContent.toLowerCase();

        if(content.includes(searchKeyword)) {
            item.style.display = 'block';
        } else {
            item.style.display = 'none';
        }
    })
})

//modal image



