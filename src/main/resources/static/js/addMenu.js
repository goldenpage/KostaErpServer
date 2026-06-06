let pendingList = [];

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

    fetch('/api/menucategory/add', {
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

    fetch('/api/menucategory/delete', {
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

function registerAll() {
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
    }).then(res =>{
        if(res.ok || res.redirected){
            alert('메뉴 등록에 성공했습니다.');
            pendingList = [];
            renderPendingList();

            document.getElementById('ingredientDetailBody').innerHTML =
                '<tr><td colspan="2" class="empty_msg">-</td></tr>';
            document.getElementById('selectedMenuName').textContent = '메뉴를 선택하세요';

        }else{
            alert('메뉴 등록에 실패했습니다.');
        }
    }).catch(()=>{
        alert("메뉴 등록 중 오류 발생");
    })
}
