window.addEventListener("DOMContentLoaded", function () {
    loadExpNotice();
    loadStockNotice();

    const saveBtn = document.getElementById("saveNoticeSettingBtn");

    if (saveBtn !== null) {
        saveBtn.onclick = function () {
            saveNoticeSettings();
        };
    }
});

function loadExpNotice() {
    fetch("/api/notice/exp")
        .then(function (response) {
            if (!response.ok) {
                throw new Error("유통기한 알림 설정 조회 실패");
            }

            return response.json();
        })
        .then(function (data) {
            document.getElementById("expAlert").checked = data.expAlert;
            document.getElementById("expDays").value = data.expDays;
        })
        .catch(function (error) {
            console.log(error);
            showNoticeMessage("유통기한 알림 설정을 불러오지 못했습니다.", "red");
        });
}

function loadStockNotice() {
    fetch("/api/notice/stock")
        .then(function (response) {
            if (!response.ok) {
                throw new Error("재고 부족 알림 설정 조회 실패");
            }

            return response.json();
        })
        .then(function (data) {
            document.getElementById("foodmAlert").checked = data.foodmAlert;
            document.getElementById("foodmLimit").value = data.foodmLimit;
        })
        .catch(function (error) {
            console.log(error);
            showNoticeMessage("재고 부족 알림 설정을 불러오지 못했습니다.", "red");
        });
}

function saveNoticeSettings() {
    const expDays = Number(document.getElementById("expDays").value);
    const foodmLimit = Number(document.getElementById("foodmLimit").value);

    if (expDays < 1) {
        alert("유통기한 기준일은 1 이상이어야 합니다.");
        return;
    }

    if (foodmLimit < 1) {
        alert("재고 부족 기준 수량은 1 이상이어야 합니다.");
        return;
    }

    Promise.all([
        saveExpNotice(),
        saveStockNotice()
    ])
        .then(function () {
            showNoticeMessage("알림 설정이 저장되었습니다.", "green");
        })
        .catch(function (error) {
            console.log(error);
            showNoticeMessage("알림 설정 저장 중 오류가 발생했습니다.", "red");
        });
}

function saveExpNotice() {
    const expAlert = document.getElementById("expAlert").checked;
    const expDays = Number(document.getElementById("expDays").value);

    return fetch("/api/notice/exp", {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            expAlert: expAlert,
            expDays: expDays
        })
    })
        .then(function (response) {
            if (!response.ok) {
                throw new Error("유통기한 알림 설정 저장 실패");
            }

            return response.json();
        })
        .then(function (data) {
            document.getElementById("expAlert").checked = data.expAlert;
            document.getElementById("expDays").value = data.expDays;
        });
}

function saveStockNotice() {
    const foodmAlert = document.getElementById("foodmAlert").checked;
    const foodmLimit = Number(document.getElementById("foodmLimit").value);

    return fetch("/api/notice/stock", {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            foodmAlert: foodmAlert,
            foodmLimit: foodmLimit
        })
    })
        .then(function (response) {
            if (!response.ok) {
                throw new Error("재고 부족 알림 설정 저장 실패");
            }

            return response.json();
        })
        .then(function (data) {
            document.getElementById("foodmAlert").checked = data.foodmAlert;
            document.getElementById("foodmLimit").value = data.foodmLimit;
        });
}

function showNoticeMessage(message, color) {
    const messageArea = document.getElementById("noticeSettingMessage");

    if (messageArea === null) {
        return;
    }

    messageArea.style.color = color;
    messageArea.innerText = message;
}