var pendingList = [];

function today() {
    return new Date().toISOString().substring(0, 10);
}

document.getElementById('incomeDate').value = today();

function selectCategory(btn) {
    document.querySelectorAll('#categoryArea button').forEach(function(b) {
        b.classList.remove('selected');
    });
    btn.classList.add('selected');
    document.getElementById('selectedCategoryId').value = btn.getAttribute('data-category-id');
}

function getSelectedCategoryName() {
    var sel = document.querySelector('#categoryArea button.selected');
    return sel ? sel.textContent.trim() : '';
}

function addCategoryAjax() {
    var input = document.getElementById('getfoodCategory');
    var categoryName = input.value.trim();
    var msg = document.getElementById('categoryMsg');

    if (!categoryName) {
        alert('카테고리명을 입력해주세요.');
        return;
    }

    var xhr = new XMLHttpRequest();
    xhr.open("POST", contextPath + "/controller?cmd=addFoodCategoryAction", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var parts = xhr.responseText.split("|");
            var result = parts[0];
            var getId = parts[1];
            var getName = parts[2];

            if (result === "success") {
                msg.style.color = 'green';
                msg.innerText = '카테고리가 추가되었습니다.';

                var span = document.createElement('span');
                span.style.cssText = 'display:inline-flex; align-items:center; gap:2px;';
                var selectBtn = document.createElement('button');
                selectBtn.type = 'button';
                selectBtn.textContent = getName;
                selectBtn.setAttribute('data-category-id', getId);
                selectBtn.onclick = function() { selectCategory(this); };

                var delBtn = document.createElement('button');
                delBtn.type = 'button';
                delBtn.className = 'remove_btn';
                delBtn.innerHTML = '&#10005;';
                delBtn.onclick = function() { deleteCategoryAjax(getName, this); };
                span.appendChild(selectBtn);
                span.appendChild(delBtn);
                document.getElementById('categoryArea').appendChild(span);
                input.value = '';
            } else {
                msg.style.color = 'red';
                msg.innerText = getId;
            }
        } else if (xhr.readyState === 4) {
            msg.style.color = 'red';
            msg.innerText = '카테고리 추가 중 오류가 발생했습니다.';
        }
    };

    xhr.send("foodCategory=" + encodeURIComponent(categoryName));
}

function deleteCategoryAjax(foodCategory, delBtn) {
    if (!confirm(foodCategory + ' 카테고리를 삭제하시겠습니까?'))
        return;

    var msg = document.getElementById('categoryMsg');
    var xhr = new XMLHttpRequest();
    xhr.open("POST", contextPath + "/controller?cmd=deleteFoodCategoryAction", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var parts = xhr.responseText.split("|");
            var result = parts[0];
            var value = parts[1];

            if (result === "success") {
                msg.innerText = value;
                var span = delBtn.closest('span');
                var selectedId = document.getElementById('selectedCategoryId').value;
                var selectBtn = span.querySelector('button:not(.remove_btn)');
                if (selectBtn && selectBtn.getAttribute('data-category-id') === selectedId) {
                    document.getElementById('selectedCategoryId').value = '';
                }
                span.remove();
            } else {
                msg.innerText = value;
            }
        } else if (xhr.readyState === 4) {
            msg.innerText = '카테고리 삭제 중 오류가 발생했습니다.';
        }
    };

    xhr.send("foodCategory=" + encodeURIComponent(foodCategory));
}

function addToList() {
    var foodMaterialName = document.getElementById('foodMaterialName').value.trim();
    var foodCategory_Id = document.getElementById('selectedCategoryId').value;
    var foodCategoryName = getSelectedCategoryName();
    var foodMaterialCount = document.getElementById('foodMaterialCount').value;
    var foodMaterialCountAll = document.getElementById('foodMaterialCountAll').value;
    var unit = document.getElementById('inputUnit').value;
    var foodMaterialPrice = document.getElementById('foodMaterialPrice').value;
    var foodMaterialType = document.getElementById('foodMaterialType').value.trim();
    var vender = document.getElementById('vender').value.trim();
    var incomeDate = document.getElementById('incomeDate').value;
    var expirationDate = document.getElementById('expirationDate').value;

    if (!incomeDate) incomeDate = today();

    if (!foodMaterialName) { alert('식자재명을 입력해주세요.'); return; }
    if (!foodCategory_Id) { alert('카테고리를 선택해주세요.'); return; }
    if (!foodMaterialCount || Number(foodMaterialCount) < 0) { alert('전체수량을 올바르게 입력해주세요.'); return; }
    if (!foodMaterialCountAll || Number(foodMaterialCountAll) < 0) { alert('식자재 용량을 올바르게 입력해주세요.'); return; }
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
        foodMaterialCountAll: foodMaterialCountAll,
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
    var body = document.getElementById('registerBody');
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
            '<td>' + item.foodMaterialCountAll + item.unit + '</td>' +
            '<td>' + Number(item.foodMaterialPrice).toLocaleString() + '원</td>' +
            '<td><span class="remove_btn" data-index="' + idx + '" onclick="removeRow(this)">&#10005;</span></td>';
        body.appendChild(tr);
    });
}

function removeRow(el) {
    var idx = Number(el.getAttribute('data-index'));
    pendingList.splice(idx, 1);
    renderPendingList();
}

function rebuildHiddenFields() {
    var container = document.getElementById('hiddenFields');
    container.innerHTML = '';
    pendingList.forEach(function(item) {
        var fields = [
            ['foodMaterialName', item.foodMaterialName],
            ['foodCategory_Id', item.foodCategory_Id],
            ['foodMaterialCount', item.foodMaterialCount],
            ['foodMaterialCountAll', item.foodMaterialCountAll],
            ['foodMaterialPrice', item.foodMaterialPrice],
            ['foodMaterialType', item.foodMaterialType],
            ['vender', item.vender],
            ['incomeDate', item.incomeDate],
            ['expirationDate', item.expirationDate]
        ];
        fields.forEach(function(f) {
            var input = document.createElement('input');
            input.type = 'hidden';
            input.name = f[0];
            input.value = f[1];
            container.appendChild(input);
        });
    });
}

function clearInputs() {
    document.getElementById('foodMaterialName').value = '';
    document.getElementById('foodMaterialCount').value = '';
    document.getElementById('foodMaterialCountAll').value = '';
    document.getElementById('inputUnit').selectedIndex = 0;
    document.getElementById('foodMaterialPrice').value = '';
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
    var keyword = document.getElementById('searchInput').value.trim();
    var body = document.getElementById('searchResultBody');

    if (!keyword) {
        body.innerHTML = '<tr><td colspan="5" class="empty_msg">검색어를 입력하세요</td></tr>';
        return;
    }

    var xhr = new XMLHttpRequest();
    xhr.open("GET", contextPath + "/controller?cmd=searchFoodMaterialAction&keyword=" + encodeURIComponent(keyword), true);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var list = JSON.parse(xhr.responseText);
            if (list.length === 0) {
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
        } else if (xhr.readyState === 4) {
            body.innerHTML = '<tr><td colspan="5" class="empty_msg">검색 중 오류가 발생했습니다</td></tr>';
        }
    };

    xhr.send();
}

function fillFromSearch(data) {
    document.getElementById('foodMaterialName').value = data.foodMaterialName;
    document.getElementById('vender').value = data.vender;
    document.getElementById('foodMaterialType').value = data.foodMaterialType || '';

    var catBtns = document.querySelectorAll('#categoryArea button[data-category-id]');
    var matched = false;

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
        return false;
    }
    rebuildHiddenFields();
    return true;
}
