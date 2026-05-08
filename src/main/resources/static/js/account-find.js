function openFindTab(type) {
    const idTab = document.getElementById("idTab");
    const passwordTab = document.getElementById("passwordTab");
    const buttons = document.querySelectorAll(".tab-btn");

    if (!idTab || !passwordTab || buttons.length < 2) {
        console.log("탭 요소를 찾을 수 없습니다.");
        return;
    }

    buttons.forEach(function (btn) {
        btn.classList.remove("active");
    });

    if (type === "id") {
        idTab.style.display = "block";
        passwordTab.style.display = "none";
        buttons[0].classList.add("active");
    }

    if (type === "password") {
        idTab.style.display = "none";
        passwordTab.style.display = "block";
        buttons[1].classList.add("active");
    }
}