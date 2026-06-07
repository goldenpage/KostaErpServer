let bId = "0000000000";
let currentSort = "idDesc";
let pageSize = 5;
let currentPage = 1;
let totalPage = 1;

window.onload = function() {
    const pageInfo = document.getElementById("foodMaterialPage");

    if (pageInfo !== null) {
        bId = pageInfo.dataset.bId || "0000000000";
        currentSort = pageInfo.dataset.sort || "idDesc";
        pageSize = Number(pageInfo.dataset.size || 5);
        currentPage = Number(pageInfo.dataset.currentPage || 1);
        totalPage = Number(pageInfo.dataset.totalPage || 1);
    }

    document.getElementById("searchBtn").onclick = function() {
        loadFoodMaterials(currentSort, 1);
    };

    document.getElementById("categorySelect").onchange = function() {
        filterCategory();
    };

    document.getElementById("selectAllBtn").onclick = function() {
        selectAllFoodMaterial();
    };

    document.getElementById("deleteSelectedBtn").onclick = function() {
        deleteSelectedFoodMaterials();
    };

    document.getElementById("searchKeyword").onkeydown = function(event) {
        if (event.key === "Enter") {
            event.preventDefault();
            loadFoodMaterials(currentSort, 1);
        }
    };

    document.addEventListener("click", function(event) {
        if (event.target.classList.contains("sortBtn")) {
            const sort = event.target.value;
            loadFoodMaterials(sort, 1);
        }

        if (event.target.classList.contains("pageBtn")) {
            const page = Number(event.target.value);
            loadFoodMaterials(currentSort, page);
        }

        if (event.target.classList.contains("deleteBtn")) {
            const foodMaterialId = event.target.value;
            deleteFoodMaterial(foodMaterialId);
        }

        if (event.target.classList.contains("backBtn")) {
            history.back();
        }
    });

    document.getElementById("prevPageBtn").onclick = function() {
        if (currentPage > 1) {
            loadFoodMaterials(currentSort, currentPage - 1);
        }
    };

    document.getElementById("nextPageBtn").onclick = function() {
        if (currentPage < totalPage) {
            loadFoodMaterials(currentSort, currentPage + 1);
        }
    };
    updatePageButtons();
};

function loadFoodMaterials(sort, page) {
    currentSort = sort;
    currentPage = page;

    const keyword = document.getElementById("searchKeyword").value.trim();

    let url = "/api/foodmaterials"
        + "?bId=" + encodeURIComponent(bId)
        + "&sort=" + encodeURIComponent(sort)
        + "&page=" + page
        + "&size=" + pageSize;

    if (keyword !== "") {
        url += "&keyword=" + encodeURIComponent(keyword);
    }

    fetch(url)
        .then(function(response) {
            if (!response.ok) {
                throw new Error("식자재 조회 실패");
            }
            return response.json();
        })
        .then(function(foodList) {
            drawFoodTable(foodList);
            filterCategory();
            updatePageButtons();
        })
        .catch(function(error) {
            console.log(error);
            alert("식자재 조회 중 오류가 발생했습니다.");
        });
}

function drawFoodTable(foodList) {
    const tbody = document.getElementById("foodTableBody");
    tbody.innerHTML = "";

    if (foodList.length === 0) {
        tbody.innerHTML =
            "<tr class='emptyRow'>" +
            "<td colspan='13'>조회된 식자재가 없습니다.</td>" +
            "</tr>";
        return;
    }

    foodList.forEach(function(food) {
        const tr = document.createElement("tr");
        tr.className = "foodRow";

        tr.innerHTML =
            "<td>" + checkNull(food.foodMaterialId) + "</td>" +
            "<td>" + checkNull(food.foodMaterialName) + "</td>" +
            "<td>" + checkNull(food.foodCategory) + "</td>" +
            "<td>" + checkNull(food.foodMaterialCount) + "</td>" +
            "<td>" + checkNull(food.foodMaterialCountAll) + "</td>" +
            "<td>" + checkNull(food.foodMaterialPrice) + "</td>" +
            "<td>" + checkNull(food.vender) + "</td>" +
            "<td>" + formatDate(food.incomeDate) + "</td>" +
            "<td>" + formatDate(food.expirationDate) + "</td>" +
            "<td>" + checkNull(food.foodMaterialType) + "</td>" +
            "<td>폐기수정</td>" +
            "<td><input type='checkbox' name='foodMaterialId' value='" + checkNull(food.foodMaterialId) + "'></td>" +
            "<td><button type='button' class='deleteBtn' value='" + checkNull(food.foodMaterialId) + "'>삭제</button></td>";

        tbody.appendChild(tr);
    });
}

function filterCategory() {
    const category = document.getElementById("categorySelect").value;
    const rows = document.querySelectorAll(".foodRow");

    rows.forEach(function(row) {
        const cells = row.querySelectorAll("td");

        if (cells.length < 3) {
            return;
        }

        const foodCategory = cells[2].textContent.trim();

        if (category === "" || foodCategory === category) {
            row.style.display = "";
        } else {
            row.style.display = "none";
        }
    });
}

function deleteFoodMaterial(foodMaterialId) {
    if (!confirm("삭제하시겠습니까?")) {
        return;
    }

    fetch("/api/foodmaterials/" + encodeURIComponent(foodMaterialId) + "?bId=" + encodeURIComponent(bId), {
        method: "DELETE"
    })
        .then(function(response) {
            if (!response.ok) {
                throw new Error("삭제 실패");
            }
            return response.text();
        })
        .then(function() {
            alert("삭제 완료");
            loadFoodMaterials(currentSort, 1);
        })
        .catch(function(error) {
            console.log(error);
            alert("삭제 중 오류가 발생했습니다.");
        });
}

function selectAllFoodMaterial() {
    const checkboxes = document.querySelectorAll("input[name='foodMaterialId']");
    let allChecked = true;

    checkboxes.forEach(function(checkbox) {
        const row = checkbox.closest("tr");

        if (row.style.display !== "none" && checkbox.checked === false) {
            allChecked = false;
        }
    });

    checkboxes.forEach(function(checkbox) {
        const row = checkbox.closest("tr");

        if (row.style.display !== "none") {
            checkbox.checked = !allChecked;
        }
    });
}

async function deleteSelectedFoodMaterials() {
    const checkedBoxes = document.querySelectorAll("input[name='foodMaterialId']:checked");

    if (checkedBoxes.length === 0) {
        alert("삭제할 식자재를 선택해주세요.");
        return;
    }

    if (!confirm(checkedBoxes.length + "개의 식자재를 삭제하시겠습니까?")) {
        return;
    }

    for (let i = 0; i < checkedBoxes.length; i++) {
        const foodMaterialId = checkedBoxes[i].value;

        const response = await fetch("/api/foodmaterials/" + encodeURIComponent(foodMaterialId) + "?bId=" + encodeURIComponent(bId), {
            method: "DELETE"
        });

        if (!response.ok) {
            alert(foodMaterialId + " 삭제 중 오류가 발생했습니다.");
            loadFoodMaterials(currentSort, 1);
            return;
        }
    }
    alert("선택 삭제 완료");
    loadFoodMaterials(currentSort, 1);
}

function updatePageButtons() {
    const prevPageBtn = document.getElementById("prevPageBtn");
    const nextPageBtn = document.getElementById("nextPageBtn");

    if (prevPageBtn !== null) {
        prevPageBtn.disabled = currentPage <= 1;
    }

    if (nextPageBtn !== null) {
        nextPageBtn.disabled = currentPage >= totalPage;
    }

    const pageButtons = document.querySelectorAll(".pageBtn");

    pageButtons.forEach(function(button) {
        button.disabled = Number(button.value) === currentPage;
    });
}

function checkNull(value) {
    if (value === null || value === undefined) {
        return "";
    }

    return value;
}

function formatDate(value) {
    if (value === null || value === undefined || value === "") {
        return "";
    }

    return String(value).substring(0, 10);
}