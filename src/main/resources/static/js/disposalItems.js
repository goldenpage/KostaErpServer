function updateReason(disposalId) {
    const select = document.getElementById(`reason_${disposalId}`);
    if (!select) return;
    const requestData = {
        reasonId: select.value
    };

    fetch(`http://127.0.0.1:15000/api/disposal-items/${disposalId}/reason`, {
        method: "PATCH",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        body: JSON.stringify(requestData)
    }).then(response => {
        if (!response.ok) {
            alert("폐기 사유 변경에 실패했습니다.");
            return;
        }
        alert("폐기 사유 변경에 성공했습니다.")
        location.reload();
    }).catch(error => {
        console.error("Error:", error);
        alert("네트워크 오류가 발생했습니다.");
    });
}

function loadDisposalData(page = 1, isPopState = false) {
    const bId = document.querySelector('input[name="bId"]').value;
    const category = document.getElementById('category').value;
    const reason = document.getElementById('reason').value;
    const url = `http://127.0.0.1:15000/api/disposal-items?bId=${bId}&category=${category}&reason=${reason}&page=${page}&size=5`;

    fetch(url, {
        method: "GET",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            if (!response.ok){
                throw new Error("데이터 조회 실패");
            }
            return response.json();
        }).then(data => {
        let htmlContent = "";

        if (data && data.length > 0) {
            data.forEach((item, index) => {
                const displayNum = item.disposalId;
                const isBroken = (item.reason === '파손' || item.reason === 'B');
                const isOther = (item.reason === '기타' || item.reason === 'BETC');
                const isSpoiled = (item.reason === '변질' || item.reason === 'D');
                const isExpired = (item.reason === '유통기한만료' || item.reason === 'E');

                htmlContent += `
                    <tr>
                        <td class="center">${displayNum}</td>
                        <td class="center">${item.foodMaterialName || ''}</td>
                        <td class="center">${item.foodCategory || ''}</td>
                        <td class="center">${item.foodMaterialType || ''}</td>
                        <td class="center">${item.disposalCountAll || 0}g</td>
                        <td class="center">${item.disposalPrice || 0}원</td>
                        <td class="center">${item.disposalDate || ''}</td>
                        <td class="center">
                            <select id="reason_${item.disposalId}" onchange="updateReason('${item.disposalId}')" style="display: block; margin: 0 auto; text-align: center;">
                                <option value="">선택</option>
                                <option value="B" ${isBroken ? 'selected' : ''}>파손</option>
                                <option value="BETC" ${isOther ? 'selected' : ''}>기타</option>
                                <option value="D" ${isSpoiled ? 'selected' : ''}>변질</option>
                                <option value="E" ${isExpired ? 'selected' : ''}>유통기한만료</option>
                            </select>
                        </td>
                    </tr>
                `;
            });
            renderPagination(page, data.length);
        } else {
            htmlContent = `<tr><td colspan="8" style="text-align:center;">조회된 폐기 품목이 없습니다.</td></tr>`;
            document.getElementById('disposalPagination').innerHTML = "";
        }

        document.getElementById('disposalTableBody').innerHTML = htmlContent;

        if(!isPopState){
            const displayUrl = `http://127.0.0.1:15000/disposal-items?bId=${bId}&category=${category}&reason=${reason}&page=${page}`;
            history.pushState({ bId, category, reason, page }, '', displayUrl);
        }
    }).catch(error => {
        console.error("Error:", error);
        alert("데이터 조회 중 오류 발생");
    });
}

function renderPagination(currentPage, currentDataLength) {
    const container = document.getElementById("disposalPagination");
    if(!container) return;

    let html = "";

    if (currentPage > 1) {
        html += `<a href="javascript:void(0);" onclick="loadDisposalData(${currentPage - 1})">이전</a>`;
    }

    const totalPages = 3;

    for (let i = 1; i <= totalPages; i++) {
        if (i === currentPage) {
            html += `<a href="javascript:void(0);" class="active">${i}</a>`;
        } else {
            html += `<a href="javascript:void(0);" onclick="loadDisposalData(${i})">${i}</a>`;
        }
    }
    if (currentPage < totalPages && currentDataLength === 5) {
        html += `<a href="javascript:void(0);" onclick="loadDisposalData(${currentPage + 1})">다음</a>`;
    }
    container.innerHTML = html;
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
    loadDisposalData(1);
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