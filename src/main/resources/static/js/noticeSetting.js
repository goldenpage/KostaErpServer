window.addEventListener("DOMContentLoaded", function () {
    loadExpNotice();

    const saveBtn = document.getElementById("saveNoticeSettingBtn");

    if (saveBtn !== null) {
        saveBtn.onclick = function () {
            saveExpNotice();
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

function saveExpNotice() {
    const expAlert = document.getElementById("expAlert").checked;
    const expDays = Number(document.getElementById("expDays").value);

    if (expDays < 1) {
        alert("유통기한 기준일은 1 이상이어야 합니다.");
        return;
    }

    fetch("/api/notice/exp", {
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

            showNoticeMessage("유통기한 알림 설정이 저장되었습니다.", "green");
        })
        .catch(function (error) {
            console.log(error);
            showNoticeMessage("유통기한 알림 설정 저장 중 오류가 발생했습니다.", "red");
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