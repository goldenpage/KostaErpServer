let pendingList = [];

document.addEventListener('DOMContentLoaded', function() {
    loadFoodMaterialList();
});

function selectCategory(btn) {
    document.querySelectorAll('#categoryArea button[data-category-id]').forEach(function(b) {
        b.classList.remove('selected');
    });
    btn.classList.add('selected');
    document.getElementById('selectedCategoryId').value = btn.getAttribute('data-category-id');
}

function getSelectedCategoryName() {
    let sel = document.querySelector('#categoryArea button.selected');
    return sel ? sel.textContent.trim() : '';
}

function loadFoodMaterialList() {
    fetch('/api/menu/foodmaterial/list')
        .then(res => res.json())
        .then(list => {
            let select = document.getElementById('inputIngredientSelect');
            select.innerHTML = '<option value="">-- 선택 --</option>';
            list.forEach(function(fm) {
                let option = document.createElement('option');
                option.value = fm.foodMaterialId;
                option.setAttribute('data-name', fm.foodMaterialName);
                option.textContent = fm.foodMaterialName + ' (' + fm.foodCategory + ')';
                select.appendChild(option);
            });
        })
        .catch(() => { console.error('식자재 목록 불러오기 실패'); });
}

function addIngredient(){
    let select  = document.getElementById('inputIngredientSelect');
    let foodMaterialId = select.value;
    let foodMaterialName = select.options[select.selectedIndex].getAttribute('data-name');
    let usedCount = document.getElementById('inputIngredientAmount').value;

    if (!foodMaterialId) {
        alert('식자재를 선택해주세요.');
        return;
    }
    if (!usedCount || Number(usedCount) <= 0) {
        alert('수량을 올바르게 입력해주세요.');
        return;
    }

    let body = document.getElementById('ingredientBody');
    let emptyRow = body.querySelector('td[colspan]');
    if (emptyRow) emptyRow.closest('tr').remove();

    let tr = document.createElement('tr');
    tr.setAttribute('data-food-material-id', foodMaterialId);
    tr.setAttribute('data-used-count', usedCount);
    tr.innerHTML =
        '<td>' + foodMaterialName + '</td>' +
        '<td>' + Number(usedCount).toLocaleString() + 'g</td>' +
        '<td><span class="remove_btn" onclick="removeIngredientRow(this)">&#8854;</span></td>';
    body.appendChild(tr);

    select.selectedIndex = 0;
    document.getElementById('inputIngredientAmount').value = '';
}

function removeIngredientRow(el) {
    let body = document.getElementById('ingredientBody');
    el.closest('tr').remove();
    if (body.querySelectorAll('tr').length === 0) {
        body.innerHTML = '<tr><td colspan="3" class="empty_msg">추가된 식자재가 없습니다</td></tr>';
    }
}


function addCategoryAjax() {
    let input = document.getElementById('getMenuCategory');
    let categoryName = input.value.trim();
    let msg = document.getElementById('categoryMsg');

    if(!categoryName){
        alert('카테고리명을 입력해주세요');
        return;
    }

    fetch('/api/menu/menucategory/add', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({menuCategory:categoryName})
    }).then(res => res.json())
        .then(data => {
            if(data.result === 'success'){
                msg.style.color = 'green';
                msg.innerText = '카테고리가 추가되었습니다,';

                let span = document.createElement('span');
                span.style.cssText = 'display:inline-flex; align-items:center; gap:2px;';

                let selectBtn = document.createElement('button');
                selectBtn.type = 'button';
                selectBtn.textContent = data.menuCategory;
                selectBtn.setAttribute('data-category-id', data.menuCategoryId);
                selectBtn.onclick = function() { selectCategory(this); };

                let delBtn = document.createElement('button');
                delBtn.type = 'button';
                delBtn.className = 'remove_btn';
                delBtn.innerHTML = '&#10005;';
                delBtn.onclick = function() { deleteCategoryAjax(data.menuCategory, this); };

                span.appendChild(selectBtn);
                span.appendChild(delBtn);
                document.getElementById('categoryArea').appendChild(span);
                input.value = '';
            }else{
                msg.style.color = 'red';
                msg.innerText = data.message;
            }
        })
        .catch(() => {
            msg.style.color = 'red';
            msg.innerText = '카테고리 추가 중 오류 발생';
        });
}

function deleteCategoryAjax(menuCategory, delBtn) {
    if (!confirm(menuCategory + ' 카테고리를 삭제하시겠습니까?')) return;

    let msg = document.getElementById('categoryMsg');

    fetch('/api/menu/menucategory/delete', {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ menuCategory: menuCategory })
    })
        .then(res => res.json())
        .then(data => {
            if (data.result === 'success') {
                msg.style.color = 'green';
                msg.innerText = data.message;

                let span = delBtn.closest('span');
                let selectedId = document.getElementById('selectedCategoryId').value;
                let selectBtn = span.querySelector('button:not(.remove_btn)');
                if (selectBtn && selectBtn.getAttribute('data-category-id') === selectedId) {
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
            msg.innerText = '카테고리 삭제 중 오류가 발생했습니다.';
        });
}

function getIngredientList(){
    let rows = document.querySelectorAll('#ingredientBody tr[data-food-material-id]');
    let list = [];
    rows.forEach(function(row){
        list.push({
            foodMaterialId: row.getAttribute('data-food-material-id'),
            foodMaterialName: row.querySelectorAll('td')[0].textContent,
            usedCount: row.getAttribute('data-used-count')
        });
    });
    return list;
}

function addMenuToList(){
    let menuName = document.getElementById('inputMenuName').value.trim();
    let menuCategoryId = document.getElementById('selectedCategoryId').value;
    let menuCategory = getSelectedCategoryName();
    let menuPrice = document.getElementById('inputMenuPrice').value;
    let ingredients = getIngredientList();

    if (!menuName) {
        alert('메뉴명을 입력해주세요.');
        return;
    }
    if (!menuCategoryId) {
        alert('카테고리를 선택해주세요.');
        return;
    }
    if (!menuPrice || Number(menuPrice) < 0) {
        alert('메뉴 가격을 올바르게 입력해주세요.');
        return;
    }
    if (ingredients.length === 0) {
        alert('사용 식자재를 추가해주세요.');
        return;
    }

    let isDuplicate = pendingList.some(function(m) {
        return m.menuName === menuName;
    });
    if (isDuplicate) {
        alert('"' + menuName + '"은 이미 등록된 메뉴입니다.');
        return;
    }

    pendingList.push({
        menuName: menuName,
        menuCategoryId: menuCategoryId,
        menuCategory: menuCategory,
        menuPrice: menuPrice,
        ingredients: ingredients
    });

    renderMenuList();

    document.getElementById('inputMenuName').value = '';
    document.getElementById('inputMenuPrice').value = '';
    document.getElementById('selectedCategoryId').value = '';
    document.querySelectorAll('#categoryArea button[data-category-id]').forEach(function(b) {
        b.classList.remove('selected');
    });
    document.getElementById('ingredientBody').innerHTML = '<tr><td colspan="3" class="empty_msg">추가된 식자재가 없습니다</td></tr>';
}

function renderMenuList(){
    let body = document.getElementById('registerMenuBody');
    body.innerHTML = '';

    if (pendingList.length === 0) {
        body.innerHTML = '<tr><td colspan="4" class="empty_msg">추가된 메뉴가 없습니다</td></tr>';
        return;
    }

    pendingList.forEach(function(menu) {
        let tr = document.createElement('tr');
        tr.setAttribute('data-menu-name', menu.menuName);
        tr.innerHTML =
            '<td>' + menu.menuName + '</td>' +
            '<td>' + menu.menuCategory + '</td>' +
            '<td>' + Number(menu.menuPrice).toLocaleString() + '원</td>' +
            '<td><span class="remove_btn" onclick="removeMenuRow(this)">&#10005;</span></td>';
        tr.addEventListener('click', function(e) {
            if (e.target.classList.contains('remove_btn')) return;
            showIngredientDetail(menu.menuName);
            document.querySelectorAll('#registerMenuBody tr').forEach(function(r) {
                r.classList.remove('selected_row');
            });
            this.classList.add('selected_row');
        });
        body.appendChild(tr);
    });
}

function removeMenuRow(el){
    let menuName = el.closest('tr').getAttribute('data-menu-name');
    let idx = pendingList.findIndex(function(m) {
        return m.menuName === menuName;
    });
    if (idx !== -1)
        pendingList.splice(idx, 1);
    renderMenuList();
    document.getElementById('selectedMenuName').textContent = '메뉴를 선택하세요';
    document.getElementById('ingredientDetailBody').innerHTML =
        '<tr><td colspan="2" class="empty_msg">-</td></tr>';
}

function showIngredientDetail(menuName){
    let menu = pendingList.find(function(m) {
        return m.menuName === menuName;
    });
    if (!menu)
        return;
    document.getElementById('selectedMenuName').textContent = menuName;
    let body = document.getElementById('ingredientDetailBody');
    body.innerHTML = menu.ingredients.map(function(ing) {
        return '<tr><td>' + ing.foodMaterialName + '</td><td>' + ing.usedCount + 'g</td></tr>';
    }).join('');
}

function registerAllMenus() {
    if (pendingList.length === 0) {
        alert('등록할 메뉴가 없습니다.');
        return;
    }

    let formData = new FormData();
    pendingList.forEach(function (item){
        formData.append('menuName', item.menuName);
        formData.append('menuPrice', item.menuPrice);
        formData.append('menuCategoryId', item.menuCategoryId);
        formData.append('menuIngredientCount', item.ingredients.length);

        item.ingredients.forEach(function(used){
            formData.append('foodMaterialId', used.foodMaterialId);
            formData.append('usedCount', used.usedCount);
        });
    });

    fetch('/menu/add',{
        method:'POST',
        body: formData
    }).then(res => res.json())
        .then(data => {
        if(data.result === 'success'){
            alert(data.message);
            pendingList = [];
            renderMenuList();

            document.getElementById('ingredientDetailBody').innerHTML =
                '<tr><td colspan="2" class="empty_msg">-</td></tr>';
            document.getElementById('selectedMenuName').textContent = '메뉴를 선택하세요';

        }else{
            alert(data.message);
        }
    }).catch(()=>{
        alert("메뉴 등록 중 오류 발생");
    })
}
