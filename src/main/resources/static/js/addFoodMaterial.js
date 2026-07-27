let pendingList = [];

function today() {
    return new Date().toISOString().substring(0, 10);
}

document.getElementById('incomeDate').value = today();

function calcAuto() {
    let count = Number(document.getElementById('foodMaterialCount').value);
    let weight = Number(document.getElementById('foodMaterialWeight').value);
    let price = Number(document.getElementById('foodMaterialPrice').value);

    document.getElementById('totalWeight').value = (count > 0 && weight > 0) ? count * weight : '';
    document.getElementById('totalPrice').value = (count > 0 && price > 0) ? count * price : '';
}

function selectCategory(btn) {
    document.querySelectorAll('#categoryArea button').forEach(function(b) {
        b.classList.remove('selected');
    });
    btn.classList.add('selected');
    document.getElementById('selectedCategoryId').value = btn.getAttribute('data-category-id');
}

function getSelectedCategoryName() {
    let sel = document.querySelector('#categoryArea button.selected');
    return sel ? sel.textContent.trim() : '';
}

function addCategoryAjax(){
    let input = document.getElementById('getFoodCategory');
    let categoryName = input.value.trim();
    let msg = document.getElementById('categoryMsg');

    if (!categoryName) {
        alert('카테고리명을 입력해주세요.');
        return;
    }

    fetch('/api/foodmaterial/foodcategory/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({foodCategory:categoryName})
    })
        .then(response => response.json())
        .then(data => {
            if (data.result === 'success') {
                console.log(categoryName)

                msg.style.color = 'green';
                msg.innerText = '카테고리가 추가되었습니다.';

                let span = document.createElement('span');
                span.style.cssText = 'display:inline-flex; align-items:center; gap:2px;';

                let selectBtn = document.createElement('button');
                selectBtn.type = 'button';
                selectBtn.textContent = data.foodCategory;
                selectBtn.setAttribute('data-category-id', data.foodCategory_Id);
                selectBtn.onclick = function() { selectCategory(this); };

                let delBtn = document.createElement('button');
                delBtn.type = 'button';
                delBtn.className = 'remove_btn';
                delBtn.innerHTML = '&#10005;';
                delBtn.onclick = function() { deleteCategoryAjax(data.foodCategory, this); };

                span.appendChild(selectBtn);
                span.appendChild(delBtn);
                document.getElementById('categoryArea').appendChild(span);
                input.value = '';
            } else {
                msg.style.color = 'red';
                msg.innerText = data.message;
                console.log(categoryName)
            }
        })
        .catch(() => {
            msg.style.color = 'red';
            msg.innerText = '카테고리 추가 오류';
        });
}

function deleteCategoryAjax(foodCategory, delBtn) {
    if (!confirm(foodCategory + ' 카테고리를 삭제하시겠습니까?'))
        return;

    let msg = document.getElementById('categoryMsg');

    fetch('/api/foodmaterial/foodcategory/delete', {
        method: 'DELETE',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({foodCategory:foodCategory})
    })
        .then(response => response.json())
        .then(data => {
            if (data.result === 'success') {
                msg.style.color = 'green';
                msg.innerText = data.message;

                let span = delBtn.closest('span');
                let selectedId = document.getElementById('selectedCategoryId');
                let selectBtn = span.querySelector('button:not(.remove_btn)');
                if(selectBtn && selectBtn.getAttribute('data-category-id') === selectedId){
                    document.getElementById('selectedCategoryId').value = '';
                }
                span.remove();
            } else {
                msg.style.color = 'red';
                msg.innerText = data.message;
            }
        })
        .catch(() => {
            msg.style.color = 'red';
            msg.innerText = '카테고리 삭제 오류 발생';
        });
}

function addToList() {
    let foodMaterialName = document.getElementById('foodMaterialName').value.trim();
    let foodCategory_Id = document.getElementById('selectedCategoryId').value;
    let foodCategoryName = getSelectedCategoryName();
    let foodMaterialCount = document.getElementById('foodMaterialCount').value;
    let foodMaterialWeight = document.getElementById('foodMaterialWeight').value;
    let unit = document.getElementById('inputUnit').value;
    let foodMaterialPrice = document.getElementById('foodMaterialPrice').value;
    let foodMaterialType = document.getElementById('foodMaterialType').value.trim();
    let vender = document.getElementById('vender').value.trim();
    let incomeDate = document.getElementById('incomeDate').value;
    let expirationDate = document.getElementById('expirationDate').value;

    if (!incomeDate) incomeDate = today();

    if (!foodMaterialName) { alert('식자재명을 입력해주세요.'); return; }
    if (!foodCategory_Id) { alert('카테고리를 선택해주세요.'); return; }
    if (!foodMaterialCount || Number(foodMaterialCount) < 0) { alert('전체수량을 올바르게 입력해주세요.'); return; }
    if (!foodMaterialWeight || Number(foodMaterialWeight) < 0) { alert('식자재 중량을 올바르게 입력해주세요.'); return; }
    if (!foodMaterialPrice || Number(foodMaterialPrice) < 0) { alert('가격을 올바르게 입력해주세요.'); return; }
    if (!foodMaterialType) { alert('타입을 입력해주세요.'); return; }
    if (!vender) { alert('구입처를 입력해주세요.'); return; }
    if (!expirationDate) { alert('유통기한을 입력해주세요.'); return; }
    if (expirationDate < incomeDate) { alert('유통기한이 매입일자보다 이전입니다.'); return; }

    pendingList.push({
        foodMaterialName: foodMaterialName,
        foodCategory_Id: foodCategory_Id,
        foodCategoryName: foodCategoryName,
        foodMaterialCount: foodMaterialCount,
        foodMaterialWeight: foodMaterialWeight,
        unit: unit,
        foodMaterialPrice: foodMaterialPrice,
        foodMaterialType: foodMaterialType,
        vender: vender,
        incomeDate: incomeDate,
        expirationDate: expirationDate
    });

    renderPendingList();
    clearInputs();
}

function renderPendingList() {
    let body = document.getElementById('registerBody');
    body.innerHTML = '';

    if (pendingList.length === 0) {
        body.innerHTML = '<tr><td colspan="6" class="empty_msg">추가된 식자재가 없습니다</td></tr>';
        return;
    }

    pendingList.forEach(function(item, idx) {
        var tr = document.createElement('tr');
        tr.innerHTML =
            '<td>' + item.foodMaterialName + '</td>' +
            '<td>' + item.foodCategoryName + '</td>' +
            '<td>' + item.foodMaterialCount + '</td>' +
            '<td>' + item.foodMaterialWeight + item.unit + '</td>' +
            '<td>' + Number(item.foodMaterialPrice).toLocaleString() + '원</td>' +
            '<td><span class="remove_btn" data-index="' + idx + '" onclick="removeRow(this)">&#10005;</span></td>';
        body.appendChild(tr);
    });
}

function removeRow(el) {
    let idx = Number(el.getAttribute('data-index'));
    pendingList.splice(idx, 1);
    renderPendingList();
}

function clearInputs() {
    document.getElementById('foodMaterialName').value = '';
    document.getElementById('foodMaterialCount').value = '';
    document.getElementById('foodMaterialWeight').value = '';
    document.getElementById('totalWeight').value = '';
    document.getElementById('inputUnit').selectedIndex = 0;
    document.getElementById('foodMaterialPrice').value = '';
    document.getElementById('totalPrice').value = '';
    document.getElementById('foodMaterialType').selectedIndex = 0;
    document.getElementById('vender').value = '';
    document.getElementById('expirationDate').value = '';
    document.getElementById('incomeDate').value = today();
    document.getElementById('selectedCategoryId').value = '';
    document.querySelectorAll('#categoryArea button').forEach(function(b) {
        b.classList.remove('selected');
    });
}

function searchMaterial() {
    let keyword = document.getElementById('searchInput').value.trim();
    let body = document.getElementById('searchResultBody');

    if(!keyword){
        body.innerHTML = '<tr><td colspan="5" class="empty_msg">검색어를 입력하세요</td></tr>';
        return;
    }

    fetch('/api/foodmaterial/search/add/' + encodeURIComponent(keyword))
        .then(res => res.json())
        .then(list => {
            if (!list || list.length === 0) {
                body.innerHTML = '<tr><td colspan="5" class="empty_msg">검색 결과가 없습니다</td></tr>';
                return;
            }
            body.innerHTML = list.map(function(m) {
                return '<tr>' +
                    '<td>' + m.foodMaterialName + '</td>' +
                    '<td>' + m.foodCategory + '</td>' +
                    '<td>' + m.vender + '</td>' +
                    '<td>' + m.foodMaterialType + '</td>' +
                    '<td><button type="button" onclick=\'fillFromSearch(' + JSON.stringify(m) + ')\'>&#8853;</button></td>' +
                    '</tr>';
            }).join('');
        })
        .catch(() => {
            body.innerHTML = '<tr><td colspan="5" class="empty_msg">검색 중 오류가 발생했습니다</td></tr>';
        });
}

function fillFromSearch(data) {
    document.getElementById('foodMaterialName').value = data.foodMaterialName;
    document.getElementById('vender').value = data.vender;
    document.getElementById('foodMaterialType').value = data.foodMaterialType || '';

    let catBtns = document.querySelectorAll('#categoryArea button[data-category-id]');
    let matched = false;

    catBtns.forEach(function(btn) {
        btn.classList.remove('selected');
        if (btn.textContent.trim() === data.foodCategory) {
            btn.classList.add('selected');
            document.getElementById('selectedCategoryId').value = btn.getAttribute('data-category-id');
            matched = true;
        }
    });

    if (!matched) {
        document.getElementById('selectedCategoryId').value = '';
    }

    alert('"' + data.foodMaterialName + '" 정보를 불러왔습니다.');
}

function registerAll() {
    if (pendingList.length === 0) {
        alert('등록할 식자재가 없습니다.');
        return;
    }
    console.log("1: " + pendingList)
    let formData = new FormData();
    pendingList.forEach(function(item) {
        formData.append('foodMaterialName', item.foodMaterialName);
        formData.append('foodCategory_Id', item.foodCategory_Id);
        formData.append('foodMaterialCount', item.foodMaterialCount);
        formData.append('foodMaterialWeight', item.foodMaterialWeight);
        formData.append('foodMaterialPrice', item.foodMaterialPrice);
        formData.append('foodMaterialType', item.foodMaterialType);
        formData.append('vender', item.vender);
        formData.append('incomeDate', item.incomeDate);
        formData.append('expirationDate', item.expirationDate);
    });
    console.log("2: " + pendingList)
    fetch('/api/foodmaterial/add', {
        method: 'POST',
        body: formData
    }).then(res => {
        if (res.ok || res.redirected) {
            alert('식자재 등록에 성공했습니다.');
            pendingList = [];
            renderPendingList();
            console.log("success: " + pendingList)
        } else {
            alert('식자재 등록에 실패했습니다.');
            console.log("fail: " + pendingList)
        }
    }).catch(() => {
        alert('식자재 등록 중 오류가 발생했습니다.');
        console.log("error: " + pendingList)
    });
}
