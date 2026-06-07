let dailyChart = null;
let reasonChart = null;

const getMonthRange = () => {
  const monthInput = document.querySelector("input[name='month']");
  const month = monthInput?.value;

  if (!month) {
    const now = new Date();
    const year = now.getFullYear();
    const currentMonth = String(now.getMonth() + 1).padStart(2, "0");
    return getStartAndEndDate(`${year}-${currentMonth}`);
  }

  return getStartAndEndDate(month);
};

const getStartAndEndDate = (month) => {
  const [year, monthNumber] = month.split("-").map(Number);

  const startDate = `${year}-${String(monthNumber).padStart(2, "0")}-01`;

  const endDateObject = new Date(year, monthNumber, 1);
  const endYear = endDateObject.getFullYear();
  const endMonth = String(endDateObject.getMonth() + 1).padStart(2, "0");
  const endDate = `${endYear}-${endMonth}-01`;

  return {
    startDate,
    endDate
  };
};

const fetchJson = async (url) => {
  const response = await fetch(url, {
    method: "GET",
    credentials: "include"
  });

  if (!response.ok) {
    throw new Error(`API 요청 실패: ${response.status}`);
  }
  return await response.json();
};

const loadDisposalRate = async () => {
  const { startDate, endDate } = getMonthRange();

  const data = await fetchJson(
      `/api/statistics/disposals/rate?startDate=${startDate}&endDate=${endDate}`
  );

  const rateElement = document.querySelector("#disposalRate");
  if (rateElement) {
    rateElement.textContent = data.disposalRate ?? data;
  }
};

const loadTotalDisposalPrice = async () => {
  const { startDate, endDate } = getMonthRange();

  const data = await fetchJson(
      `/api/statistics/disposals/total-price?startDate=${startDate}&endDate=${endDate}`
  );

  const priceElement = document.querySelector("#totalDisposalPrice");
  if (priceElement) {
    priceElement.textContent = Number(data).toLocaleString();
  }
};

const loadTopMaterials = async () => {
  const { startDate, endDate } = getMonthRange();

  const list = await fetchJson(
      `/api/statistics/disposals/top-materials?startDate=${startDate}&endDate=${endDate}`
  );

  const tbody = document.querySelector("#topMaterialsBody");
  if (!tbody) {
    return;
  }

  tbody.innerHTML = "";

  if (!list || list.length === 0) {
    tbody.innerHTML = `
      <tr>
        <td colspan="4">폐기 데이터가 없습니다.</td>
      </tr>
    `;
    return;
  }

  list.forEach((item, index) => {
    tbody.innerHTML += `
      <tr>
        <td>${index + 1}</td>
        <td>${item.foodMaterialName ?? ""}</td>
        <td>${item.disposalCount ?? 0}</td>
        <td>${Number(item.totalDisposalPrice ?? 0).toLocaleString()} 원</td>
      </tr>
    `;
  });
};

const loadReasonChart = async () => {
  const { startDate, endDate } = getMonthRange();

  const data = await fetchJson(
      `/api/statistics/disposals/reason-ratio?startDate=${startDate}&endDate=${endDate}`
  );
  const list = Array.isArray(data) ? data : (data.list ?? []);

  const reasonLabels = list.map((item) => item.reason);
  const reasonValues = list.map((item) => item.reasonRatio ?? 0);

  const canvas = document.querySelector("#reasonChart");

  if (reasonChart) {
    reasonChart.destroy();
  }

  reasonChart = new Chart(canvas, {
    type: "doughnut",
    data: {
      labels: reasonLabels,
      datasets: [
        {
          data: reasonValues
        }
      ]
    }
  });
};

const loadDailyDisposalChart = async () => {
  const { startDate, endDate } = getMonthRange();

  const list = await fetchJson(
      `/api/statistics/disposals/daily-chart?startDate=${startDate}&endDate=${endDate}`
  );

  const dailyLabels = [...new Set(list.map((item) => item.disposalDay))];
  const materialTypes = [...new Set(list.map((item) => item.foodMaterialType ?? "전체"))];
  const dailyValues = new Map(
      list.map((item) => [
        `${item.foodMaterialType ?? "전체"}:${item.disposalDay}`,
        item.disposalCount ?? 0
      ])
  );
  const colors = {
    "고체": "#2563eb",
    "액체": "#16a34a",
    "전체": "#64748b"
  };

  const canvas = document.querySelector("#dailyDisposalChart");

  if (dailyChart) {
    dailyChart.destroy();
  }

  dailyChart = new Chart(canvas, {
    type: "line",
    data: {
      labels: dailyLabels,
      datasets: materialTypes.map((type) => ({
        label: `${type} 폐기량`,
        data: dailyLabels.map((day) => dailyValues.get(`${type}:${day}`) ?? 0),
        borderColor: colors[type] ?? "#64748b",
        backgroundColor: colors[type] ?? "#64748b"
      }))
    }
  });
};

const loadDisposalStatistics = async () => {
  const results = await Promise.allSettled([
    loadDisposalRate(),
    loadTotalDisposalPrice(),
    loadTopMaterials(),
    loadReasonChart(),
    loadDailyDisposalChart()
  ]);
  const failures = results.filter((result) => result.status === "rejected");

  if (failures.length > 0) {
    failures.forEach((failure) => console.error(failure.reason));
    alert("폐기 통계 데이터를 불러오지 못했습니다.");
  }
};

document.addEventListener("DOMContentLoaded", () => {
  loadDisposalStatistics();
});
