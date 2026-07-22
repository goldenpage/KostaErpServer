function changeQty(btn, delta) {
    const span = btn.parentElement.querySelector('span');
    let val = parseInt(span.innerText);
    if (val + delta >= 1) {
        span.innerText = val + delta;
    }
}

function processSale() {
    const total = document.getElementById('totalAmount').innerText;
    if (total === "0" || total === "") {
        alert("주문 내역이 없습니다.");
        return;
    }

    alert("판매 기록이 추가되었습니다. 총 : " + total + "원");

    document.querySelector('.list_container tbody').innerHTML = "";
    document.getElementById('totalAmount').innerText = "0";
}

function filterMenu() {
    const keyword = document.querySelector('.search-box input').value.toLowerCase();
    const rows = document.querySelectorAll('.menu-table tbody tr');

    rows.forEach(row => {
        const menuName = row.cells[0].innerText.toLowerCase();
        row.style.display = menuName.includes(keyword) ? "" : "none";
    });
}

function addOrder(btn) {
    const row = btn.closest('tr');
    const menuName = row.cells[0].innerText;
    const price = parseInt(row.cells[2].innerText.replace(/,/g, ''));
    const qtySpan = row.querySelector('span');
    const qty = parseInt(qtySpan.innerText);
    const orderTableBody = document.querySelector('.order-list-area .list_container tbody');
    const newRow = orderTableBody.insertRow();

    newRow.innerHTML = `
        <td>${menuName}</td>
        <td>${qty}</td>
        <td>${(price * qty).toLocaleString()}</td>
        <td><button onclick="removeOrder(this)">X</button></td>
    `;
    updateTotal();
    qtySpan.innerText = "1";
}

function removeOrder(btn) {
    const row = btn.closest('tr');
    row.remove();
    updateTotal();
}

function updateTotal() {
    let total = 0;
    const rows = document.querySelectorAll('.order-list-area .list_container tbody tr');

    rows.forEach(row => {
        const priceText = row.cells[2].innerText.replace(/,/g, '');
        total += parseInt(priceText);
    });
    document.getElementById('totalAmount').innerText = total.toLocaleString();
}