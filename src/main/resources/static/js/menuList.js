let selectedMenuId = "";

window.onload = function() {
    const menuPage = document.getElementById("menuPage");

    if (menuPage !== null) {
        bId = menuPage.dataset.bId || "0000000000";
        selectedMenuId = menuPage.dataset.selectedMenuId || "";
    }

    const saleBtn = document.getElementById("saleBtn");

    if (saleBtn !== null) {
        saleBtn.onclick = function() {
            saleMenu();
        };
    }

    document.addEventListener("click", function(event) {
        if (event.target.classList.contains("menuBtn")) {
            const menuId = event.target.value;

            selectedMenuId = menuId;
            selectMenuRadio(menuId);
            loadMenuMaterials(menuId);
        }

        // 추가
        if (event.target.classList.contains("deleteBtn")) {
            const menuId = event.target.value;
            deleteMenu(menuId)
        }
    });
};

function loadMenuMaterials(menuId) {
    fetch("/api/menus/" + encodeURIComponent(menuId) + "/materials")
        .then(function(response) {
            if (!response.ok) {
                throw new Error("메뉴 식자재 조회 실패");
            }

            return response.json();
        })
        .then(function(data) {
            drawIngredientTable(data.materialList);
        })
        .catch(function(error) {
            console.log(error);
            alert("메뉴 식자재 조회 중 오류가 발생했습니다.");
        });
}

function saleMenu() {
    if (selectedMenuId === "") {
        alert("판매할 메뉴를 선택하세요.");
        return;
    }

    const saleCount = Number(document.getElementById("saleCount").value);

    if (saleCount < 1) {
        alert("판매 수량은 1 이상이어야 합니다.");
        return;
    }

    if (!confirm(saleCount + "개 판매 처리하시겠습니까?")) {
        return;
    }

    fetch("/api/menus/" + encodeURIComponent(selectedMenuId) + "/sales", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            saleCount: saleCount
        })
    })
        .then(function(response) {
            if (!response.ok) {
                throw new Error("판매 처리 실패");
            }

            return response.json();
        })
        .then(function(data) {
            alert(data.message);
            loadMenuMaterials(selectedMenuId);
        })
        .catch(function(error) {
            console.log(error);
            alert("판매 처리 중 오류가 발생했습니다.");
        });
}

function drawIngredientTable(detailList) {
    const tbody = document.getElementById("ingredientTableBody");
    tbody.innerHTML = "";

    if (detailList.length === 0) {
        tbody.innerHTML =
            "<tr>" +
            "<td colspan='4'>선택된 메뉴의 식자재 정보가 없습니다.</td>" +
            "</tr>";
        return;
    }

    detailList.forEach(function(detail) {
        const tr = document.createElement("tr");

        tr.innerHTML =
            "<td>" + checkNull(detail.foodMaterialName) + "</td>" +
            "<td>" + checkNull(detail.usedCount) + "</td>" +
            "<td>" + checkNull(detail.foodMaterialPrice) + "</td>" +
            "<td>" + checkNull(detail.usedPrice) + "</td>";

        tbody.appendChild(tr);
    });
}

function selectMenuRadio(menuId) {
    const radios = document.querySelectorAll("input[name='menuSelect']");

    radios.forEach(function(radio) {
        radio.checked = radio.value === menuId;
    });
}

function deleteMenu(menuId) {
    if (!confirm("삭제하시겠습니까?")) {
        return;
    }

    fetch("/api/menus/" + encodeURIComponent(menuId), {
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
            loadMenuMaterials(menuId);
        })
        .catch(function(error) {
            console.log(error);
            alert("삭제 중 오류가 발생했습니다.");
        });
}



function checkNull(value) {
    if (value === null || value === undefined) {
        return "";
    }

    return value;
}

function goBack() {
    history.back();
}