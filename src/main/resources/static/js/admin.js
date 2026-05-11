document.addEventListener("DOMContentLoaded", function () {
    const dataBox = document.getElementById("adminChartData");

    if (!dataBox) return;

    const userCount = Number(dataBox.dataset.userCount || 0);
    const productCount = Number(dataBox.dataset.productCount || 0);
    const paymentCount = Number(dataBox.dataset.paymentCount || 0);
    const applyCount = Number(dataBox.dataset.applyCount || 0);

    createMiniLineChart("userChart", userCount, "전체 회원");
    createMiniLineChart("productChart", productCount, "등록 강의");
    createMiniLineChart("paymentChart", paymentCount, "결제 내역");
    createMiniLineChart("applyChart", applyCount, "강사 신청");
});

function createMiniLineChart(canvasId, currentValue, labelName) {
    const canvas = document.getElementById(canvasId);

    if (!canvas) return;

    const ctx = canvas.getContext("2d");

    const base = Math.max(currentValue, 1);

    const sampleData = [
        Math.max(base - 3, 0),
        Math.max(base - 2, 0),
        Math.max(base - 1, 0),
        base,
        currentValue
    ];

    new Chart(ctx, {
        type: "line",
        data: {
            labels: ["1차", "2차", "3차", "4차", "현재"],
            datasets: [{
                label: labelName,
                data: sampleData,
                borderColor: "#f58220",
                backgroundColor: "#f58220",
                pointBackgroundColor: "#f58220",
                pointBorderColor: "#f58220",
                pointRadius: 4,
                pointHoverRadius: 5,
                borderWidth: 3,
                fill: false,
                tension: 0.25
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            layout: {
                padding: {
                    top: 24,
                    right: 12,
                    bottom: 4,
                    left: 4
                }
            },
            plugins: {
                legend: {
                    display: false
                },
                datalabels: {
                    color: "#555",
                    anchor: "end",
                    align: "top",
                    offset: 4,
                    font: {
                        size: 11,
                        weight: "bold"
                    },
                    formatter: function (value) {
                        return value;
                    }
                },
                tooltip: {
                    callbacks: {
                        label: function (context) {
                            return labelName + " : " + context.raw;
                        }
                    }
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    grid: {
                        color: "#eeeeee"
                    },
                    ticks: {
                        precision: 0,
                        color: "#777"
                    }
                },
                x: {
                    grid: {
                        display: false
                    },
                    ticks: {
                        color: "#777"
                    }
                }
            }
        },
        plugins: [ChartDataLabels]
    });
}