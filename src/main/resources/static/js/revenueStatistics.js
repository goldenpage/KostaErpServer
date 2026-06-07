let rankChart = null;
let monthlyChart = null;

function won(value) {
  return Number(value || 0).toLocaleString();
}

function getMonthRange(month) {
  const [year, monthValue] = month.split("-").map(Number);
  const startDate = `${year}-${String(monthValue).padStart(2, "0")}-01`;
  const nextMonth = monthValue === 12 ? 1 : monthValue + 1;
  const nextYear = monthValue === 12 ? year + 1 : year;
  const endDate = `${nextYear}-${String(nextMonth).padStart(2, "0")}-01`;

  return {
    startDate,
    endDate
  };
}

const getSixMonthRange = (month) => {
  const [year, monthValue] = month.split("-").map(Number);
  const startDateObject = new Date(year, monthValue - 6, 1);
  const endDateObject = new Date(year, monthValue, 1);

  const startDate =
      `${startDateObject.getFullYear()}-`
      + `${String(startDateObject.getMonth() + 1).padStart(2, "0")}-01`;

  const endDate =
      `${endDateObject.getFullYear()}-`
      + `${String(endDateObject.getMonth() + 1).padStart(2, "0")}-01`;

  return {
    startDate,
    endDate
  };
}

async function fetchJson(url) {
  const res = await fetch(url, {
    method: "GET",
    headers: {
      "Accept": "application/json"
    }
  });

  if (res.status === 401) {
    location.href = "/login";
    return null;
  }

  if (!res.ok) {
    throw new Error(`API 요청 실패: ${res.status}`);
  }

  return await res.json();
}

function renderTotalRevenue(data) {
  const totalRevenuePrice = document.querySelector("#totalRevenuePrice");
  totalRevenuePrice.textContent = won(data);
}

function renderRankTable(rankList) {
  const tbody = document.querySelector("#rankTableBody");
  if (!rankList || rankList.length === 0) {
    tbody.innerHTML = `
      <tr>
        <td colspan="5">매출 데이터가 없습니다.</td>
      </tr>
    `;
    return;
  }

  tbody.innerHTML = rankList.map(item => `
    <tr>
      <td>${item.ranking}</td>
      <td>${item.menuName}</td>
      <td>${won(item.menuPrice)} 원</td>
      <td>${item.totalSaleCount}</td>
      <td>${won(item.menuPrice * item.totalSaleCount)} 원</td>
    </tr>
  `).join("");
}

function renderRankChart(rankList) {
  const labels = rankList.map(item => item.menuName);
  const salesAmount = rankList.map(item => item.menuPrice * item.totalSaleCount);
  const saleCount = rankList.map(item => item.totalSaleCount);

  if (rankChart) {
    rankChart.destroy();
  }

  rankChart = new Chart(document.querySelector("#rankChart"), {
    type: "bar",
    data: {
      labels,
      datasets: [{
        label: "매출",
        data: salesAmount
      }]
    },
    options: {
      indexAxis: "y",
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          display: false
        },
        tooltip: {
          callbacks: {
            label: function (context) {
              const index = context.dataIndex;
              return "매출: " + won(context.raw) + "원 / 판매수량: "
                  + saleCount[index];
            }
          }
        }
      },
      scales: {
        x: {
          ticks: {
            callback: function (value) {
              return won(value) + "원";
            }
          }
        }
      }
    }
  });
}

function renderMonthlyChart(monthlyList) {
  const labels = monthlyList.map(item => item.revenueMonth);
  const revenue = monthlyList.map(item => item.totalRevenuePrice);

  if (monthlyChart) {
    monthlyChart.destroy();
  }

  monthlyChart = new Chart(document.querySelector("#monthlyChart"), {
    type: "bar",
    data: {
      labels,
      datasets: [{
        label: "월별 매출",
        data: revenue
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          display: false
        },
        tooltip: {
          callbacks: {
            label: function (context) {
              return "매출: " + won(context.raw) + "원";
            }
          }
        }
      },
      scales: {
        y: {
          ticks: {
            callback: function (value) {
              return won(value) + "원";
            }
          }
        }
      }
    }
  });
}

async function loadRevenueStatistics() {
  const monthInput = document.querySelector("#month");

  const today = new Date();
  const defaultMonth =
      `${today.getFullYear()}-${String(today.getMonth() + 1).padStart(2, "0")}`;

  if (!monthInput.value) {
    monthInput.value = defaultMonth;
  }

  const selectedMonthRange = getMonthRange(monthInput.value);
  const sixMonthRange = getSixMonthRange(monthInput.value);


  const selectedMonthQueryString = new URLSearchParams({
    startDate: selectedMonthRange.startDate,
    endDate: selectedMonthRange.endDate
  }).toString();

  const sixMonthQueryString = new URLSearchParams({
    startDate: sixMonthRange.startDate,
    endDate: sixMonthRange.endDate
  }).toString();

  try {
    const [totalRevenue, rankList, monthlyList] = await Promise.all([
      fetchJson(`/api/statistics/revenue/total?${selectedMonthQueryString}`),
      fetchJson(`/api/statistics/revenue/menu-rank?${selectedMonthQueryString}`),
      fetchJson(`/api/statistics/revenue/monthly?${sixMonthQueryString}`)
    ]);

    renderTotalRevenue(totalRevenue);
    renderRankTable(rankList);
    renderRankChart(rankList);
    renderMonthlyChart(monthlyList)
  } catch (error) {
    console.error(error);
    alert("매출 통계 조회 중 오류가 발생했습니다.");
  }
}

document.addEventListener("DOMContentLoaded", () => {
  loadRevenueStatistics();

  document.querySelector("#searchForm").addEventListener("submit", (event) => {
    event.preventDefault();
    loadRevenueStatistics();
  });
});