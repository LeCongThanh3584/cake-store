fetch("https://esgoo.net/api-tinhthanh/1/0.htm")
    .then(response => response.json())
    .then(data => {
        const provinces = data.data;
        const elementSelectProvince = document.getElementById("provinceAddNew");

        let htmlOption = `<option value="">Select Province</option>`;

        provinces.forEach((element, index) => {
            htmlOption += `<option value="${element.id}" >${element.full_name}</option>`;
        })

        elementSelectProvince.innerHTML = htmlOption;
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

const clickDeleteAddress = (id) => {
    document.getElementById("idDeleleAddress").innerText = id;
    document.getElementById("idAddressDelete").value = id;
}