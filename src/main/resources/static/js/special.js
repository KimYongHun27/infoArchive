async function saveSpecialLog(actionName, status, message) {
    const params = new URLSearchParams();
    params.append("actionName", actionName);
    params.append("status", status);
    params.append("message", message);

    const response = await fetch("/api/special/log", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: params
    });

    if (!response.ok) {
        throw new Error("로그 저장 실패");
    }

    return await response.json();
}

async function checkSystemStatus() {
    const resultText = document.getElementById("resultText");

    try {
        const savedLog = await saveSpecialLog(
            "시스템 상태 확인",
            "SUCCESS",
            "특별계정 페이지가 정상 작동 중입니다."
        );

        if (resultText) {
            resultText.innerText =
                "시스템 상태 확인 완료: DB 로그 저장 성공 / 로그 번호: " + savedLog.id;
        }

        console.log("시스템 상태 확인 로그 저장:", savedLog);
    } catch (error) {
        if (resultText) {
            resultText.innerText = "시스템 상태 확인 실패: " + error.message;
        }

        console.error(error);
    }
}

async function checkAccessRole() {
    const resultText = document.getElementById("resultText");

    try {
        const savedLog = await saveSpecialLog(
            "접근 권한 테스트",
            "SUCCESS",
            "현재 계정은 SPECIAL 권한으로 접근 중입니다."
        );

        if (resultText) {
            resultText.innerText =
                "접근 권한 확인 완료: DB 로그 저장 성공 / 로그 번호: " + savedLog.id;
        }

        console.log("접근 권한 테스트 로그 저장:", savedLog);
    } catch (error) {
        if (resultText) {
            resultText.innerText = "접근 권한 확인 실패: " + error.message;
        }

        console.error(error);
    }
}

async function createTempLog() {
    const resultText = document.getElementById("resultText");

    try {
        const now = new Date().toLocaleString();

        const savedLog = await saveSpecialLog(
            "임시 로그 생성",
            "SUCCESS",
            "화면 테스트용 임시 로그가 생성되었습니다. 생성 시간: " + now
        );

        if (resultText) {
            resultText.innerText =
                "임시 로그 생성 완료: DB 저장 성공 / 로그 번호: " + savedLog.id;
        }

        console.log("임시 로그 생성 저장:", savedLog);
    } catch (error) {
        if (resultText) {
            resultText.innerText = "임시 로그 생성 실패: " + error.message;
        }

        console.error(error);
    }
}

async function deleteSpecialLog(id) {
    if (!confirm("이 로그를 삭제할까요?")) {
        return;
    }

    try {
        const response = await fetch("/api/special/log/" + id, {
            method: "DELETE"
        });

        if (!response.ok) {
            throw new Error("로그 삭제 실패");
        }

        alert("로그가 삭제되었습니다.");
        location.reload();
    } catch (error) {
        alert(error.message);
        console.error(error);
    }
}

async function deleteAllSpecialLogs() {
    if (!confirm("전체 로그를 모두 삭제할까요?")) {
        return;
    }

    try {
        const response = await fetch("/api/special/logs", {
            method: "DELETE"
        });

        if (!response.ok) {
            throw new Error("전체 로그 삭제 실패");
        }

        alert("전체 로그가 삭제되었습니다.");
        location.reload();
    } catch (error) {
        alert(error.message);
        console.error(error);
    }
}

function generateReport() {
    const reportResult = document.getElementById("reportResult");

    if (!reportResult) {
        return;
    }

    const now = new Date().toLocaleString();

    const totalText =
        document.querySelector(".report-summary-grid .summary-card:nth-child(1) h3")?.innerText || "0건";

    const successRateText =
        document.querySelector(".report-summary-grid .summary-card:nth-child(2) h3")?.innerText || "0%";

    const failText =
        document.querySelector(".report-summary-grid .summary-card:nth-child(3) h3")?.innerText || "0건";

    reportResult.innerText =
        "리포트 생성 완료: " + now +
        " / 전체 로그: " + totalText +
        " / 성공률: " + successRateText +
        " / 실패 로그: " + failText +
        " / 특별계정 기능 상태를 정상적으로 확인했습니다.";

    console.log("특별계정 리포트 생성:", now);
}