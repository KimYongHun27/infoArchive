document.addEventListener("DOMContentLoaded", function () {
    console.log("special.js 연결됨");

    // 현재 URL에 맞는 사이드바 메뉴 active 처리
    const currentPath = window.location.pathname;
    const menuLinks = document.querySelectorAll(".side-menu a");

    menuLinks.forEach(function (link) {
        const linkPath = link.getAttribute("href");

        link.classList.remove("active");

        if (linkPath === currentPath) {
            link.classList.add("active");
        }
    });

    // 카드 등장 애니메이션
    const animatedItems = document.querySelectorAll(
        ".summary-card, .panel-card, .notice-box"
    );

    animatedItems.forEach(function (item, index) {
        item.classList.add("fade-up");

        setTimeout(function () {
            item.classList.add("show");
        }, index * 120);
    });

    // 패널 카드 클릭 로그
    const panelCards = document.querySelectorAll(".panel-card");

    panelCards.forEach(function (card) {
        card.addEventListener("click", function () {
            console.log("특별계정 메뉴 이동:", card.getAttribute("href"));
        });
    });
});

function checkSystemStatus() {
    const resultText = document.getElementById("resultText");

    if (resultText) {
        resultText.innerText = "시스템 상태 확인 완료: 특별계정 페이지가 정상 작동 중입니다.";
    }

    console.log("시스템 상태 확인 실행");
}

function checkAccessRole() {
    const resultText = document.getElementById("resultText");

    if (resultText) {
        resultText.innerText = "접근 권한 확인 완료: 현재 계정은 SPECIAL 권한으로 접근 중입니다.";
    }

    console.log("접근 권한 확인 실행");
}

function createTempLog() {
    const resultText = document.getElementById("resultText");
    const now = new Date().toLocaleString();

    if (resultText) {
        resultText.innerText = "임시 로그 생성 완료: " + now;
    }

    console.log("임시 로그 생성 실행:", now);
}

function addLogRow() {
    const tableBody = document.getElementById("logTableBody");

    if (!tableBody) {
        return;
    }

    const rowCount = tableBody.querySelectorAll("tr").length + 1;
    const now = new Date().toLocaleString();

    const newRow = document.createElement("tr");

    newRow.innerHTML = `
        <td>${rowCount}</td>
        <td>임시 로그 생성</td>
        <td><span class="status success">성공</span></td>
        <td>화면 테스트용 로그가 추가되었습니다.</td>
        <td>${now}</td>
    `;

    tableBody.prepend(newRow);
}

function generateReport() {
    const reportResult = document.getElementById("reportResult");

    if (!reportResult) {
        return;
    }

    const now = new Date().toLocaleString();

    reportResult.innerText =
        "리포트 생성 완료: " + now +
        " / 특별계정 페이지, 내부 도구, 실행 로그, 리포트 화면이 정상 연결되었습니다.";

    console.log("특별계정 리포트 생성:", now);
}