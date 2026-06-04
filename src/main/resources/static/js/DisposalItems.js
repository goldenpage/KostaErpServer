function updateReason(disposalId) {
    const select = document.getElementById(`reason_${disposalId}`);
    fetch(`/api/disposal-items/${disposalId}/reason`, {
        method: "PATCH",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({reasonId: select.value})
    }).then(response => {
        if (!response.ok) {
            alert("폐기 사유 변경에 실패했습니다.");
            return;
        }
        location.reload();
    });
}