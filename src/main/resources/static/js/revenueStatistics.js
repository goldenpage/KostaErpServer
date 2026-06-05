function readJson(id) {
  const text = document.getElementById(id).textContent.trim();
  return JSON.parse(text || "[]");
}

function won(value) {
  return Number(value).toLocaleString() + "원";
}

const rankLabels = readJson("rankLabelsJson");
const rankSalesAmount = readJson("rankSalesAmountJson");
const rankSaleCount = readJson("rankSaleCountJson");
const monthlyLabels = readJson("monthlyLabelsJson");
const monthlyRevenue = readJson("monthlyRevenueJson");

new Chart(document.getElementById("rankChart"), {
  type : "bar",
  data : {
    labels : rankLabels,
    datasets : [ {
      label : "매출",
      data : rankSalesAmount,
      backgroundColor : "#2563eb"
    } ]
  },
  options : {
    indexAxis : "y",
    responsive : true,
    maintainAspectRatio : false,
    plugins : {
      legend : {
        display : false
      },
      tooltip : {
        callbacks : {
          label : function(context) {
            const index = context.dataIndex;
            return "매출: " + won(context.raw) + " / 판매수량: "
                + rankSaleCount[index];
          }
        }
      }
    },
    scales : {
      x : {
        ticks : {
          callback : function(value) {
            return won(value);
          }
        }
      }
    }
  }
});

new Chart(document.getElementById("monthlyChart"), {
  type : "bar",
  data : {
    labels : monthlyLabels,
    datasets : [ {
      label : "월별 매출",
      data : monthlyRevenue,
      backgroundColor : "#16a34a"
    } ]
  },
  options : {
    responsive : true,
    maintainAspectRatio : false,
    plugins : {
      legend : {
        display : false
      },
      tooltip : {
        callbacks : {
          label : function(context) {
            return "매출: " + won(context.raw);
          }
        }
      }
    },
    scales : {
      y : {
        ticks : {
          callback : function(value) {
            return won(value);
          }
        }
      }
    }
  }
});