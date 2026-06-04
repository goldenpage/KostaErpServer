window.onload = function() {
    document.getElementById("searchBtn").onclick = function() {
        searchFood();
    };

    document.getElementById("categorySelect").onchange = function() {
        categoryAjax();
    };

    document.getElementById("selectAllBtn").onclick = function() {
        selectAllFoodMaterial();
    };

    document.getElementById("searchKeyword").onkeydown = function(event) {
        if (event.key === "Enter") {
            searchFood();
        }
    };
};

function searchFood() {
    var keyword = document.getElementById("searchKeyword").value.trim();
    var category = document.getElementById("categorySelect").value;
    var rows = document.querySelectorAll(".foodRow");

    rows.forEach(function(row) {
        var cells = row.querySelectorAll("td");

        var foodName = cells[1].textContent.trim();
        var foodCategory = cells[2].textContent.trim();

        var keywordOk = true;
        var categoryOk = true;

        if (keyword !== "") {
            keywordOk = foodName.indexOf(keyword) !== -1;
        }

        if (category !== "") {
            categoryOk = foodCategory === category;
        }

        if (keywordOk && categoryOk) {
            row.style.display = "";
        } else {
            row.style.display = "none";
        }
    });
}

function categoryAjax() {
    searchFood();

    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            console.log("카테고리 비동기 요청 완료");
        }
    };

    xhr.open(
        "GET",
        contextPath + "/controller?cmd=foodMaterialAction&sortType=" + sortType + "&page=" + currentPage,
        true
    );
    xhr.send();
}

function selectAllFoodMaterial() {
    var checkboxes = document.querySelectorAll("input[name='foodMaterialId']");
    var allChecked = true;

    checkboxes.forEach(function(checkbox) {
        var row = checkbox.closest("tr");

        if (row.style.display !== "none" && checkbox.checked == false) {
            allChecked = false;
        }
    });

    checkboxes.forEach(function(checkbox) {
        var row = checkbox.closest("tr");

        if (row.style.display !== "none") {
            checkbox.checked = !allChecked;
        }
    });
}