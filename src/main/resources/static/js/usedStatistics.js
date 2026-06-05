function readJson(id) {
  var text = document.getElementById(id).textContent.trim();
  return JSON.parse(text || "[]");
}

var rankLabels = readJson("rankLabelsJson");
var rankValues = readJson("rankValuesJson");
var monthlyLabels = readJson("monthlyLabelsJson");
var monthlyValues = readJson("monthlyValuesJson");

new Chart(document.getElementById("rankChart"), {
  type : "bar",
  data : {
    labels : rankLabels,
    datasets : [ {
      label : "지출액",
      data : rankValues,
      backgroundColor : "#2563eb"
    } ]
  },
  options : {
    indexAxis : "y",
    responsive : true,
    maintainAspectRatio : false
  }
});

new Chart(document.getElementById("monthlyChart"), {
  type : "bar",
  data : {
    labels : monthlyLabels,
    datasets : [ {
      label : "월별 총 지출액",
      data : monthlyValues,
      backgroundColor : "#16a34a"
    } ]
  },
  options : {
    responsive : true,
    maintainAspectRatio : false
  }
});