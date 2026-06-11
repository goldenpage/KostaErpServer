function updateTableAndTotal(data) {
    console.log("서버에서 받은 데이터:", data);
    const tbody = document.getElementById('salesTableBody');
    const totalSpan = document.getElementById('totalRevenue');

    tbody.innerHTML = "";
    initFilters(data);
    let total = 0;


    data.forEach((sale) => {
        const subtotal = sale.qty * sale.price;
        total += sale.totalPrice;
        const row = `<tr>
            <td>${sale.saleId}</td>
            <td>${sale.saleDate}</td>
            <td>${sale.menuName}</td>
            <td>${sale.category}</td>
            <td>${sale.qty}</td>
            <td>${sale.price.toLocaleString()}</td>
            <td>${subtotal.toLocaleString()}</td>
            <td>${sale.paymentMethod || ''}</td>
            <td>
                <button type="button" class="btn-edit" onclick="editSale('${sale.saleId}')">수정</button>
                <button type="button" class="btn-delete" onclick="deleteSale('${sale.saleId}')">삭제</button>
            </td>
        </tr>`;
        tbody.innerHTML += row;
    });

    totalSpan.innerText = total.toLocaleString() + "원";
}

// window.onload = function() {
//     const initialData = /*[[${salesList}]]*/ [];
//     console.log(initialData);
//     if (initialData && initialData.length > 0) {
//         updateTableAndTotal(initialData);
//     }
// };

function searchSales() {
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;

    fetch(`/api/sales/search?startDate=${startDate}&endDate=${endDate}`)
        .then(response => response.json())
        .then(data => {
            updateTableAndTotal(data);
        })
        .catch(error => console.error("조회 실패:", error));
}

document.addEventListener("click", function(e) {
    if (e.target.classList.contains("btn-delete")) {
        const id = e.target.getAttribute("data-id");
        deleteSale(id);
    }
    if (e.target.classList.contains("btn-edit")) {
        const id = e.target.getAttribute("data-id");
        editSale(id);
    }
});

function deleteSale(id) {
    if (!confirm("판매 기록을 삭제하시겠습니까?")) return;

    fetch(`/api/sales/delete/${id}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (response.ok) {
                alert("삭제되었습니다.");
                location.reload();
            } else {
                alert("삭제 실패");
            }
        });
}

function editSale(id) {
    fetch(`/api/sales/${id}`)
        .then(res => res.json())
        .then(data => {
            document.getElementById('editSaleId').value = data.saleId;
            document.getElementById('editMenuName').value = data.menuName;
            document.getElementById('editQty').value = data.qty;
            document.getElementById('editPayment').value = data.paymentMethod;
            document.getElementById('editModal').style.display = 'block';
        });
}

function saveEdit() {
    const id = document.getElementById('editSaleId').value;
    const requestData = {
        saleMenuCount: document.getElementById('editQty').value,
        payment: document.getElementById('editPayment').value
    };

    fetch(`/api/sales/update/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(requestData)
    }).then(res => {
        if (res.ok) {
            alert("수정 완료");
            closeModal();
            location.reload();
        }
    });
}

function closeModal() {
    document.getElementById('editModal').style.display = 'none';
}

function initFilters(data) {
    // 1. 드롭박스 요소 찾기
    const categorySelect = document.getElementById('categorySelect');
    const paymentSelect = document.getElementById('paymentSelect');
    const menuSelect = document.getElementById('menuSelect');

    // 2. 기존 옵션 비우기 (기본값 "카테고리", "결제수단", "메뉴명"만 남김)
    categorySelect.innerHTML = '<option value="">카테고리</option>';
    paymentSelect.innerHTML = '<option value="">결제수단</option>';
    menuSelect.innerHTML = '<option value="">메뉴명</option>';

    // 3. Set을 사용해 중복 없는 고유 목록 추출
    const categories = [...new Set(data.map(item => item.category))];
    const payments = [...new Set(data.map(item => item.paymentMethod).filter(Boolean))]; // 빈 값 제외
    const menus = [...new Set(data.map(item => item.menuName))];

    // 4. 드롭박스에 추가
    categories.forEach(c => {
        categorySelect.add(new Option(c, c));
    });
    payments.forEach(p => {
        paymentSelect.add(new Option(p, p));
    });
    menus.forEach(m => {
        menuSelect.add(new Option(m, m));
    });
}

// 2. 통합 필터링 함수
function filterSales() {
    const category = document.getElementById('categorySelect').value;
    const payment = document.getElementById('paymentSelect').value;
    const menu = document.getElementById('menuSelect').value;

    const rows = document.querySelectorAll("#salesTableBody tr");
    let total = 0;

    rows.forEach(row => {
        const rowCategory = row.cells[3].innerText;
        const rowPayment = row.cells[7].innerText;
        const rowMenu = row.cells[2].innerText;

        const matchCategory = (category === "" || rowCategory === category);
        const matchPayment = (payment === "" || rowPayment === payment);
        const matchMenu = (menu === "" || rowMenu === menu);

        if (matchCategory && matchPayment && matchMenu) {
            row.style.display = "";
            const subtotalText = row.cells[6].innerText.replace(/,/g, '');
            total += parseInt(subtotalText || 0);
        } else {
            row.style.display = "none";
        }
    });

    document.getElementById('totalRevenue').innerText = total.toLocaleString() + "원";
}

function resetFilters() {
    document.getElementById('categorySelect').value = "";
    document.getElementById('paymentSelect').value = "";
    document.getElementById('menuSelect').value = "";

    const rows = document.querySelectorAll("#salesTableBody tr");
    let total = 0;

    rows.forEach(row => {
        row.style.display = "";

        const subtotalText = row.cells[6].innerText.replace(/,/g, '');
        total += parseInt(subtotalText || 0);
    });

    document.getElementById('totalRevenue').innerText = total.toLocaleString() + "원";
}