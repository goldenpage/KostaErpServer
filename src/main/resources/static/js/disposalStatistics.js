
new Chart(document.querySelector("#dailyDisposalChart"), {
  type: "line",
  data: {
    labels: dailyLabels,
    datasets: dailyDatasets
  }
});

new Chart(document.querySelector("#reasonChart"), {
  type: "doughnut",
  data: {
    labels: reasonLabels,
    datasets: [{
      data: reasonValues
    }]
  }
});