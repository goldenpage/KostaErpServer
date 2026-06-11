let currentSort = "idDesc";
let pageSize = 5;
let currentPage = 1;
let totalPage = 1;

window.onload = function() {
    const pageInfo = document.getElementById("foodMaterialPage");

    if (pageInfo !== null) {
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
    loadNoticeSettings();
};

function loadFoodMaterials(sort, page) {
    currentSort = sort;
    currentPage = page;

    const keyword = document.getElementById("searchKeyword").value.trim();

    let url = "/api/foodmaterials"
        + "?sort=" + encodeURIComponent(sort)
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
        .then(function(data) {
            currentPage = data.currentPage;
            totalPage = data.totalPage;

            drawFoodTable(data.foodList);
            filterCategory();
            updatePageButtons();
            applyColorToCurrentTable();
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

        const stockClass = getStockClass(food.foodMaterialCountAll);
        const expClass = getExpirationClass(food.expirationDate);

        tr.innerHTML =
            "<td>" + checkNull(food.foodMaterialId) + "</td>" +
            "<td>" + checkNull(food.foodMaterialName) + "</td>" +
            "<td>" + checkNull(food.foodCategory) + "</td>" +
            "<td>" + checkNull(food.foodMaterialCount) + "</td>" +
            "<td class='" + stockClass + "'>" + checkNull(food.foodMaterialCountAll) + "</td>" +
            "<td>" + checkNull(food.foodMaterialPrice) + "</td>" +
            "<td>" + checkNull(food.vender) + "</td>" +
            "<td>" + formatDate(food.incomeDate) + "</td>" +
            "<td class='" + expClass + "'>" + formatDate(food.expirationDate) + "</td>" +
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

    fetch("/api/foodmaterials/" + encodeURIComponent(foodMaterialId), {
        method: "DELETE"
    })
        .then(function(response) {
            if (!response.ok) {
                throw new Error("삭제 실패");
            }
            return response.json();
        })
        .then(function(data) {
            alert(data.message);
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

        const response = await fetch("/api/foodmaterials/" + encodeURIComponent(foodMaterialId), {
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

function goBack() {
    history.back();
}

function loadNoticeSettings() {
    fetch("/api/notice/exp")
        .then(function(response) {
            if (!response.ok) {
                throw new Error("유통기한 알림 설정 조회 실패");
            }

            return response.json();
        })
        .then(function(data) {
            expAlert = data.expAlert;
            expDays = data.expDays;

            applyColorToCurrentTable();
        })
        .catch(function(error) {
            console.log(error);
        });

    fetch("/api/notice/stock")
        .then(function(response) {
            if (!response.ok) {
                throw new Error("재고 부족 알림 설정 조회 실패");
            }

            return response.json();
        })
        .then(function(data) {
            foodmAlert = data.foodmAlert;
            foodmLimit = data.foodmLimit;

            applyColorToCurrentTable();
        })
        .catch(function(error) {
            console.log(error);
        });
}

function getExpirationClass(expirationDate) {
    if (expAlert === false) {
        return "";
    }

    if (expirationDate === null || expirationDate === undefined || expirationDate === "") {
        return "";
    }

    const today = new Date();
    const expDate = new Date(formatDate(expirationDate));

    today.setHours(0, 0, 0, 0);
    expDate.setHours(0, 0, 0, 0);

    const differTime = expDate.getTime() - today.getTime();
    const differDays = Math.ceil(differTime / (1000 * 60 * 60 * 24));

    if (differDays <= 0) {
        return "dangerCell";
    }

    if (differDays <= expDays) {
        return "warningCell";
    }

    return "";
}

function getStockClass(stockCount) {
    if (foodmAlert === false) {
        return "";
    }

    const count = Number(stockCount);

    if (isNaN(count)) {
        return "";
    }

    if (count <= 0) {
        return "dangerCell";
    }

    if (count <= foodmLimit) {
        return "warningCell";
    }

    return "";
}

function applyColorToCurrentTable() {
    const rows = document.querySelectorAll("#foodTableBody tr.foodRow");

    rows.forEach(function(row) {
        const cells = row.querySelectorAll("td");

        if (cells.length < 9) {
            return;
        }

        const stockCell = cells[4];
        const expCell = cells[8];

        const stockValue = stockCell.innerText.trim();
        const expValue = expCell.innerText.trim();

        stockCell.classList.remove("warningCell");
        stockCell.classList.remove("dangerCell");
        expCell.classList.remove("warningCell");
        expCell.classList.remove("dangerCell");

        const stockClass = getStockClass(stockValue);
        const expClass = getExpirationClass(expValue);

        if (stockClass !== "") {
            stockCell.classList.add(stockClass);
        }

        if (expClass !== "") {
            expCell.classList.add(expClass);
        }
    });
}