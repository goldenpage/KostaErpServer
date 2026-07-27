function updateReason(disposalId) {
    const select = document.getElementById(`reason_${disposalId}`);
    if (!select) return;
    const requestData = {
        reasonId: select.value
    };

    fetch(`/api/disposal-items/${disposalId}/reason`, {
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
    const url = `/api/disposal-items?bId=${bId}&category=${category}&reason=${reason}&page=${page}&size=5`;

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
        const itemList = data.list;
        let htmlContent = "";

        if (itemList && itemList.length > 0) {
            itemList.forEach((item) => {
                const isBroken = (item.reason === '파손' || item.reason === 'B');
                const isOther = (item.reason === '기타' || item.reason === 'BETC');
                const isSpoiled = (item.reason === '변질' || item.reason === 'D');
                const isExpired = (item.reason === '유통기한만료' || item.reason === 'E');

                htmlContent += `
                    <tr>
                        <td class="center">${item.disposalId}</td>
                        <td class="center">${item.foodMaterialName || ''}</td>
                        <td class="center">${item.foodCategory || ''}</td>
                        <td class="center">${item.foodMaterialType || ''}</td>
                        <td class="center">${item.disposalCountAll || 0}g</td>
                        <td class="center">${item.disposalPrice || 0}원</td>
                        <td class="center">${item.disposalDate || ''}</td>
                        <td class="center">
                            <select id="reason_${item.disposalId}" onchange="updateReason('${item.disposalId}')">
                                <option value="B" ${isBroken ? 'selected' : ''}>파손</option>
                                <option value="BETC" ${isOther ? 'selected' : ''}>기타</option>
                                <option value="D" ${isSpoiled ? 'selected' : ''}>변질</option>
                                <option value="E" ${isExpired ? 'selected' : ''}>유통기한만료</option>
                            </select>
                        </td>
                    </tr>
                `;
            });
        } else {
            htmlContent = `<tr><td colspan="8" style="text-align:center;">조회된 폐기 품목이 없습니다.</td></tr>`;
        }
        document.getElementById('disposalTableBody').innerHTML = htmlContent;

        const pageContainer = document.getElementById('paginationContainer');
        pageContainer.innerHTML = '';
        if (itemList && itemList.length > 0 && data.totalPages > 1) {
            // 페이지네이션이 필요한 경우에만 보이게 설정 (CSS display 속성 사용)
            pageContainer.style.display = 'block';

            if (data.currentPage > 1) {
                pageContainer.innerHTML += `<a href="javascript:void(0);" onclick="loadDisposalData(${data.currentPage - 1})">이전</a>`;
            }

            for (let i = 1; i <= data.totalPages; i++) {
                pageContainer.innerHTML += `<a href="javascript:void(0);" onclick="loadDisposalData(${i})" class="${i === data.currentPage ? 'active' : ''}">${i}</a>`;
            }

            if (data.currentPage < data.totalPages) {
                pageContainer.innerHTML += `<a href="javascript:void(0);" onclick="loadDisposalData(${data.currentPage + 1})">다음</a>`;
            }
        } else {
            // 데이터가 없거나 페이지가 1개뿐이면 영역을 숨김
            pageContainer.style.display = 'none';
        }

        if (!isPopState) {
            const displayUrl = `/disposal-items?bId=${bId}&category=${category}&reason=${reason}&page=${page}`;
            history.pushState({ bId, category, reason, page }, '', displayUrl);
        }
    })
        .catch(error => {
            console.error("Error:", error);
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
    const urlParams = new URLSearchParams(window.location.search);
    const initialPage = parseInt(urlParams.get('page')) || 1;

    loadDisposalData(initialPage);
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