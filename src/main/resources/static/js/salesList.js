function searchSales() {
    const startDate = document.querySelector('input[type="date"]:first-child').value;
    const endDate = document.querySelector('input[type="date"]:last-child').value;
    const category = document.querySelector('select').value;

    location.href = `/sales/list?startDate=${startDate}&endDate=${endDate}&category=${category}`;
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
    alert(id + "번 데이터를 수정합니다. (수정 로직 구현 예정)");
}