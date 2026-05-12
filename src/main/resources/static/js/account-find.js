document.addEventListener("DOMContentLoaded", function () {
    const phoneInputs = document.querySelectorAll(".phone-input");

    phoneInputs.forEach(function (input) {
        input.addEventListener("input", function () {
            let value = this.value.replace(/[^0-9]/g, "");

            if (value.length > 11) {
                value = value.substring(0, 11);
            }

            if (value.length <= 3) {
                this.value = value;
            } else if (value.length <= 7) {
                this.value = value.replace(/(\d{3})(\d+)/, "$1-$2");
            } else {
                this.value = value.replace(/(\d{3})(\d{4})(\d+)/, "$1-$2-$3");
            }
        });
    });
});

function openFindTab(type) {
    const idTab = document.getElementById("idTab");
    const passwordTab = document.getElementById("passwordTab");
    const buttons = document.querySelectorAll(".tab-btn");

    if (!idTab || !passwordTab || buttons.length < 2) {
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