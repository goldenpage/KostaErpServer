let globalSalesData = [];

function updateTableAndTotal(data) {
    if (data) globalSalesData = data;
    const tbody = document.getElementById('salesTableBody');
    const totalSpan = document.getElementById('totalRevenue');

    tbody.innerHTML = "";

    if (!globalSalesData || globalSalesData.length === 0) {
        tbody.innerHTML = `<tr><td colspan="9" style="text-align:center; padding:20px;">조회된 데이터가 없습니다.</td></tr>`;
        totalSpan.innerText = "0원";
        initFilters([]);
        return;
    }

    renderTable(globalSalesData)
    let total = 0;

    globalSalesData.forEach((sale) => {
        const subtotal = sale.qty * sale.price;
        total += subtotal;
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

function searchSales() {
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;

    fetch(`/api/sales/search?startDate=${startDate}&endDate=${endDate}`)
        .then(response => response.json())
        .then(data => {
            globalSalesData = data;
            filterSales();
        })
        .catch(error => console.error("조회 실패:", error));
}

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
    const categorySelect = document.getElementById('categorySelect');
    const paymentSelect = document.getElementById('paymentSelect');
    const menuSelect = document.getElementById('menuSelect');

    categorySelect.innerHTML = '<option value="">카테고리</option>';
    paymentSelect.innerHTML = '<option value="">결제수단</option>';
    menuSelect.innerHTML = '<option value="">메뉴명</option>';

    const categories = [...new Set(data.map(item => item.category))];
    const payments = [...new Set(data.map(item => item.paymentMethod).filter(Boolean))];
    const menus = [...new Set(data.map(item => item.menuName))];

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

function filterSales() {
    const category = document.getElementById('categorySelect').value;
    const payment = document.getElementById('paymentSelect').value;
    const menu = document.getElementById('menuSelect').value;

    const filteredData = globalSalesData.filter(item => {
        return (category === "" || item.category === category) &&
            (payment === "" || item.paymentMethod === payment) &&
            (menu === "" || item.menuName === menu);
    });

    renderTable(filteredData);
}

function resetFilters() {
    document.getElementById('startDate').value = "";
    document.getElementById('endDate').value = "";
    document.getElementById('categorySelect').value = "";
    document.getElementById('paymentSelect').value = "";
    document.getElementById('menuSelect').value = "";

    fetch(`/api/sales/list?page=0&size=20`)
        .then(response => response.json())
        .then(data => {
            globalSalesData = data.content;
            renderTable(data);
        })
        .catch(error => console.error("초기화 실패:", error));
}

function loadSalesList() {
    fetch('/api/sales/list')
        .then(res => res.json())
        .then(data => {
            renderTable(data);
        })
        .catch(err => console.error("데이터 로드 실패:", err));
}

function renderTable(data) {
    const listToRender = (data && data.content) ? data.content : (Array.isArray(data) ? data : []);

    const tbody = document.getElementById('salesTableBody');
    const totalSpan = document.getElementById('totalRevenue');

    tbody.innerHTML = "";

    if (listToRender.length === 0) {
        tbody.innerHTML = `<tr><td colspan="9" style="text-align:center; padding:20px;">조회된 데이터가 없습니다.</td></tr>`;
        totalSpan.innerText = "0원";
        return;
    }

    let total = 0;
    listToRender.forEach((sale) => {
        const subtotal = sale.qty * sale.price;
        total += subtotal;

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