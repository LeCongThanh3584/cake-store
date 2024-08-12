fetch("https://esgoo.net/api-tinhthanh/1/0.htm")
    .then(response => response.json())
    .then(data => {
        const provinces = data.data;
        const elementSelectProvice = document.getElementById("province");
        let htmlOption = `<option value="">Select Province</option>`;

        provinces.forEach((element, index) => {
            htmlOption += `<option value="${element.id}" >${element.full_name}</option>`;
        })

        elementSelectProvice.innerHTML = htmlOption;
    })
    .catch(error => {
        console.log("Error In API get all province: ", error);
    })


const getDistrictFromIdProvince = (event, select) => {
    const idProvince = event.target.value; //get id of province
    fetch(`https://esgoo.net/api-tinhthanh/2/${idProvince}.htm`)
        .then(response => response.json())
        .then(data => {
            const districts = data.data;
            const elementSelectDistrict = document.getElementById("district");
            const elementSelectWard = document.getElementById("ward");

            let htmlOption = `<option selected value="">Select District</option>`
            districts.forEach((element, index) => {
                htmlOption += `<option value="${element.id}">${element.full_name}</option>`;
            })

            elementSelectDistrict.innerHTML = htmlOption;
            elementSelectWard.innerHTML = `<option selected value="">Select Ward</option>`;

        })
        .catch(error => {
            console.log("Error In API get district from province: ", error);
        });

    const selectedOption = select.options[select.selectedIndex];
    const selectedText = selectedOption.text;
    document.getElementById('provinceText').value = selectedText;
}

const getWardFromIdDistrict = (event, select) => {
    const idDistrict = event.target.value;  //get id district
    fetch(`https://esgoo.net/api-tinhthanh/3/${idDistrict}.htm`)
        .then(response => response.json())
        .then(data => {
            const wards = data.data;
            const elementSelectWard = document.getElementById("ward");
            let htmlOption = `<option selected value="">Select Ward</option>`;

            wards.forEach((element, index) => {
                htmlOption += `<option value="${element.id}">${element.full_name}</option>`
            })

            elementSelectWard.innerHTML = htmlOption;
        })
        .catch(error => {
            console.log("Error in API get ward from district: ", error);
        });

    const selectedOption = select.options[select.selectedIndex];
    const selectedText = selectedOption.text;
    document.getElementById('districtText').value = selectedText;
}

const getWardSelected = (select) => {
    const selectedOption = select.options[select.selectedIndex];
    const selectedText = selectedOption.text;
    document.getElementById('wardText').value = selectedText;
}

const clickDeleteUser = id => {
    document.getElementById("idDeleleUser").innerText = id;
    document.getElementById("idUserDelete").value = id;
}

const checkSelectRole = (select) => {
    const listStore = document.getElementById("listStore");
    const selectedText = select.options[select.selectedIndex].text;

    if(selectedText === "ADMIN") {
        listStore.style.display = 'block';
    } else {
        listStore.style.display = 'none';
    }
}

const listItem = document.querySelectorAll(".dropdown-item"); //Lấy danh sách các cửa hàng
const inputStoreId = document.getElementById("storeId"); //lấy thẻ input storeId
const buttonSelect = document.getElementById("dropdownMenuSelectCake");  //Lấy button select
const searchInput = document.getElementById("searchInput");  //Lấy ra thanh search store
const textMessage = document.getElementById("textMessage");  //Lấy thông báo lỗi khi không chọn store

//Xử lý lặp các thẻ li và set giá trị
listItem.forEach(item => { //item ở đây là đại diện cho các thẻ li
    item.addEventListener('click', () => {
        const storeId = item.getAttribute("data-value");
        inputStoreId.value = storeId;

        buttonSelect.innerHTML = item.innerHTML;
        textMessage.style.display = 'none';
        buttonSelect.classList.remove("border-red");
    })
})

//Xử lý search store
searchInput.addEventListener('input', () => {
    const search = searchInput.value.toLowerCase();

    listItem.forEach(item => {
        const contentItem = item.textContent.toLowerCase();

        if(contentItem.includes(search)) {
            item.style.display = 'block';
        } else {
            item.style.display = 'none';
        }
    })
})

const formSubmitCreateAndUpdateUser = document.getElementById("formSubmitCreateAndUpdateUser");  //Lấy form createUser
const selectRole = document.getElementById("selectRole");  //Lấy thẻ select option role

//Validate submit form
formSubmitCreateAndUpdateUser.addEventListener('submit', (e) => {

    const valueSelectRole  = selectRole.options[selectRole.selectedIndex].text;
    console.log(valueSelectRole);
    if(valueSelectRole === "ADMIN") {  //Nếu chọn role là admin
        if(inputStoreId.value.trim() === '') {  //Nếu không chọn store quản lý
            textMessage.style.display = 'block';
            buttonSelect.classList.add("border-red");
            e.preventDefault();
        } else {
            e.target.submit();
        }
    } else {
        inputStoreId.value = '';
        console.log("submit thành công");
        e.target.submit();
    }
})


