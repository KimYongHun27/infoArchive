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