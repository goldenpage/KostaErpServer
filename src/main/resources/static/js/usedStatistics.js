let rankChart = null;
let monthlyChart = null

const won = (value) => {
  return Number(value || 0).toLocaleString();
}

const getMonthRange = (month)=> {
  const [year, monthValue] = month.split("-").map(Number);
  const startDate = `${year}-${String(monthValue).padStart(2, "0")}-01`;
  const nextMonth = monthValue === 12 ? 1 : monthValue + 1;
  const nextYear = monthValue === 12 ? year + 1 : year;
  const endDate = `${nextYear}-${String(nextMonth).padStart(2,"0")}-01`

  return {
    startDate,
    endDate
  }
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



const fetchJson = async (url) => {
  const res = await fetch(url, {
    method: "GET",
    headers: {
      "Accept": "application/json"
    }
  })
  if (res.status === 401) {
    location.href = "/login";
    return null;
  }

  if (!res.ok) {
    throw new Error(`API 요청 실패: ${res.status}`)
  }

  return await res.json();
}

const renderRankTable = (rankList)=>{
  const tbody = document.querySelector("#rankTableBody");

  if (!rankList || rankList.length === 0) {
    tbody.innerHTML = `
     <tr>
      <td colspan="5">지출 데이터가 없습니다.</td>
     </tr>
    `;
    return;
  }
  tbody.innerHTML = rankList.map(item => `
   <tr>
      <td>${item.ranking}</td>
      <td>${item.foodMaterialName}</td>
      <td>${won(item.foodMaterialPrice)} 원</td>
      <td>${item.foodMaterialCount}</td>
      <td>${won(item.totalExpense)} 원</td>
   </tr>
  `).join("");
}


const renderRankChart = (rankList) => {
  if (rankChart) {
    rankChart.destroy();
  }

  rankChart = new Chart(document.querySelector("#rankChart"), {
    type: "bar",
    data: {
      labels: rankList.map(item => item.foodMaterialName),
      datasets: [{
        label: "지출액",
        data: rankList.map(item => item.totalExpense),
        backgroundColor: "#2563eb"
      }]
    },
    options: {
      indexAxis: "y",
      responsive: true,
      maintainAspectRatio: false
    }
  });
};

const renderMonthlyChart = (monthlyList) => {
  if (monthlyChart) {
    monthlyChart.destroy();
  }

  monthlyChart = new Chart(document.querySelector("#monthlyChart"), {
    type: "bar",
    data: {
      labels: monthlyList.map(item => item.expenseMonth),
      datasets: [{
        label: "월별 총 지출액",
        data: monthlyList.map(item => item.totalExpense),
        backgroundColor: "#16a34a"
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false
    }
  });
};

const loadUsedStatistics =async () => {
  const monthInput = document.querySelector("#month");

  if (!monthInput.value) {
    const today = new Date();
    monthInput.value = `${today.getFullYear()}-${String(
        today.getMonth() + 1).padStart(2, "0")}`;
  }
  const monthRange = getMonthRange(monthInput.value);
  const sixMonthRange = getSixMonthRange(monthInput.value);

  const monthQuery = new URLSearchParams(monthRange);
  const sixMonthQuery = new URLSearchParams(sixMonthRange);

  const [totalExpense, rankList, monthlyList] = await Promise.all([
    fetchJson(`/api/statistics/expenses/total?${monthQuery}`),
    fetchJson(`/api/statistics/expenses/material-rank?${monthQuery}`),
    fetchJson(`/api/statistics/expenses/monthly?${sixMonthQuery}`)
  ]);

  document.querySelector("#totalUsedPrice").textContent = won(totalExpense);

  renderRankTable(rankList);
  renderRankChart(rankList);
  renderMonthlyChart(monthlyList);

}

document.addEventListener("DOMContentLoaded", () => {
  loadUsedStatistics();

  document.querySelector("#searchForm").addEventListener("submit", event => {
    event.preventDefault();
    loadUsedStatistics();
  });
})