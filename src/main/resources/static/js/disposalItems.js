function updateReason(disposalId) {
    const select = document.getElementById(`reason_${disposalId}`);
    fetch(`http://127.0.0.1:15000/api/disposal-items/${disposalId}/reason?reasonId=${select.value}`, {
        method: "PATCH"
    }).then(response => {
        if (!response.ok) {
            alert("폐기 사유 변경에 실패했습니다.");
            return;
        }
        location.reload();
    });
}

function loadDisposalData(page = 1, isPopState = false) {
    const bId = document.querySelector('input[name="bId"]').value;
    const category = document.getElementById('category').value;
    const reason = document.getElementById('reason').value;

    const url = `http://127.0.0.1:15000/disposal-items?bId=${bId}&category=${category}&reason=${reason}&page=${page}`;

    fetch(url).
        then(response => {
            if (!response.ok){
                throw new Error("데이터 조회 실패");
            }
                return response.text();
    }).then(htmlText => {
        const parser = new DOMParser();
        const doc = parser.parseFromString(htmlText, 'text/html');
        const newContent = doc.getElementById('disposalTableContainer').innerHTML;
        document.getElementById('disposalTableContainer').innerHTML = newContent;
        if(!isPopState){
            history.pushState({ bId, category, reason, page }, '', url);
        }
    }).catch(error => {
            console.error("Error:", error);
            alert("데이터 조회 중 오류 발생");
        });
}

document.addEventListener('DOMContentLoaded', function (){
    const form = document.querySelector('.category');
    if(form){
        form.addEventListener('submit', function (e){
            e.preventDefault();
            loadDisposalData(1);
        });
    }

    const bId = document.querySelector('input[name="bId"]').value;
    const category = document.getElementById('category')?.value || '';
    const reason = document.getElementById('reason')?.value || '';
    if(bId){
        history.replaceState({bId, category, reason, page: 1}, '', window.location.href);
    }
});

window.addEventListener('popstate', function (event){
    if(event.state){
        document.getElementById('category').value = event.state.category || '';
        document.getElementById('reason').value = event.state.reason || '';
        loadDisposalData(event.state.page, true);
    }else{
        location.reload();
    }
});

function movePage(page){
    loadDisposalData(page);
}

function resetFilter(){
    document.getElementById('category').value = '';
    document.getElementById('reason').value = '';
    loadDisposalData(1);
}

