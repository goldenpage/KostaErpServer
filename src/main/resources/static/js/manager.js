const loadReviews = async () => {
  const response = await fetch(
      "/api/manager/registration-reviews?status=PENDING"
  );
  const page = await response.json();

  renderReviews(page.content);
};

const approve = async (reviewId) => {
  await fetch(`/api/manager/registration-reviews/${reviewId}/approve`, {
    method: "POST"
  });

  await loadReviews();
};

const reject = async (reviewId) => {
  const reason = prompt("반려 사유를 입력해주세요.");

  await fetch(`/api/manager/registration-reviews/${reviewId}/reject`, {
    method: "POST",
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify({reason})
  });

  await loadReviews();
};