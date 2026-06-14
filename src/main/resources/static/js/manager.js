const loadReviews = async (status = "PENDING") => {
  const response = await fetch(
      `/api/manager/registration-reviews?status=${status}&size=20`
  );

  if (!response.ok) {
    alert("가입 신청 목록 조회에 실패했습니다.");
    return;
  }

  const page = await response.json();
  renderReviews(page.content);
};

const renderReviews = (reviews) => {
  const tbody = document.querySelector("#review-list");

  tbody.innerHTML = reviews.map(review => `
    <tr>
      <td>${formatDate(review.requestedAt)}</td>
      <td>${review.businessId}</td>
      <td>${review.storeName ?? "-"}</td>
      <td>${review.representativeName ?? "-"}</td>
      <td>${review.status}</td>
      <td>
        <button onclick="openDetail(${review.reviewId})">심사</button>
      </td>
    </tr>
  `).join("");
};

document.addEventListener("DOMContentLoaded", () => loadReviews());


const openDetail = async (reviewId) => {
  const response = await fetch(
      `/api/manager/registration-reviews/${reviewId}`
  );
  const review = await response.json();

  const dialog = document.querySelector("#review-detail");

  dialog.innerHTML = `
    <h2>가입 신청 상세</h2>

    <p>사업자번호: ${review.businessId}</p>
    <p>상호명: ${review.storeName}</p>
    <p>대표자명: ${review.representativeName}</p>

    <iframe
      src="/api/manager/registration-reviews/${reviewId}/document"
      width="100%"
      height="500">
    </iframe>

    ${review.status === "PENDING" ? `
      <textarea id="reject-reason" placeholder="반려 사유"></textarea>
      <button onclick="approve(${reviewId})">승인</button>
      <button onclick="reject(${reviewId})">반려</button>
    ` : ""}

    <button onclick="this.closest('dialog').close()">닫기</button>
  `;

  dialog.showModal();
};