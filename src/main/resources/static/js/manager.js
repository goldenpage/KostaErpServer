let currentStatus = "PENDING";

const statusLabels = {
  PENDING: "대기 중",
  APPROVED: "승인 완료",
  REJECTED: "반려 완료"
};

const escapeHtml = (value) => {
  const element = document.createElement("div");
  element.textContent = value == null ? "" : String(value);
  return element.innerHTML;
};

const formatDate = (value) => {
  if (!value) {
    return "-";
  }

  const date = new Date(value);
  if (Number.isNaN(date.getTime())) {
    return "-";
  }

  return date.toLocaleString("ko-KR");
};

const formatBusinessNumber = (value) => {
  const number = String(value ?? "").replace(/\D/g, "");

  if (number.length !== 10) {
    return escapeHtml(value ?? "-");
  }

  return `${number.slice(0, 3)}-${number.slice(3, 5)}-${number.slice(5)}`;
};

const request = async (url, options = {}) => {
  const response = await fetch(url, options);
  const contentType = response.headers.get("content-type") ?? "";

  let body = null;

  if (contentType.includes("application/json")) {
    body = await response.json().catch(() => null);
  } else {
    body = await response.text().catch(() => null);
  }

  if (response.status === 401) {
    location.href = "/login";
    throw new Error("로그인이 필요합니다.");
  }

  if (response.status === 403) {
    throw new Error("관리자 권한이 필요합니다.");
  }

  if (!response.ok) {
    const message =
        body?.message
        || (typeof body === "string" && body.trim())
        || "요청 처리에 실패했습니다.";

    throw new Error(message);
  }

  return body;
};

async function loadReviews(status = "PENDING") {
  currentStatus = status;
  updateActiveTab(status);

  const tbody = document.querySelector("#review-list");
  tbody.innerHTML = `
    <tr>
      <td colspan="6">가입 신청을 불러오는 중입니다.</td>
    </tr>
  `;

  try {
    const page = await request(
        `/api/manager/registration-reviews?status=${status}&size=20`
    );

    renderReviews(page?.content ?? []);
  } catch (error) {
    tbody.innerHTML = `
      <tr>
        <td colspan="6">${escapeHtml(error.message)}</td>
      </tr>
    `;
  }
}

function updateActiveTab(status) {
  document.querySelectorAll(".review-tab").forEach((button) => {
    button.classList.toggle("active", button.dataset.status === status);
  });
}

function renderReviews(reviews) {
  const tbody = document.querySelector("#review-list");

  if (!reviews.length) {
    tbody.innerHTML = `
      <tr>
        <td colspan="6">해당 상태의 가입 신청이 없습니다.</td>
      </tr>
    `;
    return;
  }

  tbody.innerHTML = reviews.map((review) => `
    <tr>
      <td>${formatDate(review.requestedAt)}</td>
      <td>${formatBusinessNumber(review.businessId)}</td>
      <td>${escapeHtml(review.storeName ?? "-")}</td>
      <td>${escapeHtml(review.representativeName ?? "-")}</td>
      <td>${escapeHtml(statusLabels[review.status] ?? review.status)}</td>
      <td>
        <button type="button" onclick="openDetail(${review.reviewId})">
          상세 보기
        </button>
      </td>
    </tr>
  `).join("");
}

async function openDetail(reviewId) {
  try {
    const review = await request(
        `/api/manager/registration-reviews/${reviewId}`
    );

    const dialog = document.querySelector("#review-detail");

    dialog.innerHTML = `
      <div class="dialog-header">
        <h2>가입 신청 상세</h2>
        <button type="button" onclick="closeDetail()">닫기</button>
      </div>

      <div class="review-information">
        <p><strong>사업자번호:</strong>
          ${formatBusinessNumber(review.businessId)}
        </p>
        <p><strong>상호명:</strong>
          ${escapeHtml(review.storeName ?? "-")}
        </p>
        <p><strong>대표자명:</strong>
          ${escapeHtml(review.representativeName ?? "-")}
        </p>
        <p><strong>이메일:</strong>
          ${escapeHtml(review.email ?? "-")}
        </p>
        <p><strong>휴대폰:</strong>
          ${escapeHtml(review.phone ?? "-")}
        </p>
        <p><strong>자동 심사 사유:</strong>
          ${escapeHtml(review.reason ?? "-")}
        </p>
        <p><strong>신청일:</strong>
          ${formatDate(review.requestedAt)}
        </p>
        <p><strong>처리 관리자:</strong>
          ${escapeHtml(review.reviewedBy ?? "-")}
        </p>
        <p><strong>처리일:</strong>
          ${formatDate(review.reviewDate)}
        </p>
      </div>

      <iframe
        class="document-frame"
        src="/api/manager/registration-reviews/${reviewId}/document"
        title="제출 사업자등록증">
      </iframe>

      ${review.status === "PENDING" ? `
        <div class="review-actions">
          <textarea
            id="reject-reason"
            placeholder="반려할 경우 반려 사유를 입력해주세요.">
          </textarea>

          <button type="button"
                  class="approve-button"
                  onclick="approveReview(${reviewId})">
            승인
          </button>

          <button type="button"
                  class="reject-button"
                  onclick="rejectReview(${reviewId})">
            반려
          </button>
        </div>
      ` : ""}
    `;

    dialog.showModal();
  } catch (error) {
    alert(error.message);
  }
}

async function approveReview(reviewId) {
  if (!confirm("해당 회원가입 신청을 승인하시겠습니까?")) {
    return;
  }

  try {
    await request(
        `/api/manager/registration-reviews/${reviewId}/approve`,
        {method: "POST"}
    );

    alert("회원가입 신청을 승인했습니다.");
    closeDetail();
    await loadReviews(currentStatus);
  } catch (error) {
    alert(error.message);
  }
}

async function rejectReview(reviewId) {
  const reasonElement = document.querySelector("#reject-reason");
  const reason = reasonElement?.value.trim();

  if (!reason) {
    alert("반려 사유를 입력해주세요.");
    reasonElement?.focus();
    return;
  }

  if (!confirm("해당 회원가입 신청을 반려하시겠습니까?")) {
    return;
  }

  try {
    await request(
        `/api/manager/registration-reviews/${reviewId}/reject`,
        {
          method: "POST",
          headers: {"Content-Type": "application/json"},
          body: JSON.stringify({reason})
        }
    );

    alert("회원가입 신청을 반려했습니다.");
    closeDetail();
    await loadReviews(currentStatus);
  } catch (error) {
    alert(error.message);
  }
}

function closeDetail() {
  document.querySelector("#review-detail")?.close();
}

document.addEventListener("DOMContentLoaded", () => {
  loadReviews();
});