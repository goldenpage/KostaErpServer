const loadEvent = async () =>{
  const res = await fetch(`/api/auth/userinfo`, {
    method: "GET",
    headers:{
      "Content-Type": "application/json"
    }
  })

  if (!res.ok) {
    location.href="/login"
  }else {
    const data = await res.json();
    document.querySelector("#user").textContent = `${data.userName}님`;
  }
}

function stockNoticeToggle() {
  const dropdown = document.getElementById("stockDropdown");
  const isHidden = dropdown.classList.contains("hidden");

  if (isHidden) {
    dropdown.classList.remove("hidden");
    stockLoadList();
  } else {
    dropdown.classList.add("hidden");
  }
}

async function stockLoadList() {
  const listEl = document.getElementById("stockList");
  listEl.innerHTML = '<li class="stock-empty">불러오는 중...</li>';

  try {
    const res = await fetch("/api/out-of-stock-notice");

    if (!res.ok) throw new Error("조회 실패");

    const data = await res.json();
    stockRenderList(data);
  } catch (e) {
    listEl.innerHTML = '<li class="stock-empty">불러오기 실패</li>';
  }
}

function stockRenderList(notices) {
  const listEl = document.getElementById("stockList");

  if (!notices || notices.length === 0) {
    listEl.innerHTML = '<li class="stock-empty">알림이 없습니다.</li>';
    stockUpdateBadge(0);
    return;
  }

  listEl.innerHTML = notices.map(n => `
        <li class="stock-item" data-id="${n.noticeId}" onclick="stockMarkAsReadAndMove(${n.noticeId})">
            <div class="stock-item-info">
                <span class="stock-item-name">${n.foodMaterialName} 재고 부족</span>
                <span class="stock-item-remain">잔여: ${n.remainStockAmount}</span>
            </div>
        </li>
    `).join("");

  stockUpdateBadge(notices.length);
}

async function stockMarkAsReadAndMove(noticeId) {
  try {
    const res = await fetch(`/api/out-of-stock-notice/${noticeId}/read`, {
      method: "PATCH"
    });

    if (!res.ok) return;

    location.href = "/foodmaterialadd";
  } catch (e) {
    console.error("읽음 처리 실패", e);
  }
}

async function stockMarkAllAsRead() {
  try {
    const res = await fetch("/api/out-of-stock-notice/read-all", {
      method: "PATCH"
    });

    if (!res.ok) return;

    document.getElementById("stockList").innerHTML =
        '<li class="stock-empty">재고 부족 알림이 없습니다.</li>';
    stockUpdateBadge(0);
  } catch (e) {
    console.error("전체 읽음 처리 실패", e);
  }
}

function stockUpdateBadge(count) {
  const badge = document.getElementById("stockBadge");
  if (!badge) return;

  if (count > 0) {
    badge.textContent = count;
    badge.classList.remove("hidden");
  } else {
    badge.classList.add("hidden");
  }
}

async function stockInitBadge() {
  try {
    const res = await fetch("/api/out-of-stock-notice/count");
    if (!res.ok) return;
    const count = await res.json();
    stockUpdateBadge(count);
  } catch (e) {
    console.error("알림 개수 조회 실패", e);
  }
}

document.addEventListener("click", (e) => {
  const wrapper = document.getElementById("stockNoticeWrapper");
  if (wrapper && !wrapper.contains(e.target)) {
    document.getElementById("stockDropdown")?.classList.add("hidden");
  }
});

window.addEventListener("DOMContentLoaded", ()=>{
  loadEvent();
  stockInitBadge();
})