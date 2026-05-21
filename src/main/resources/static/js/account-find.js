document.addEventListener("DOMContentLoaded", function () {
    const findIdTabBtn = document.getElementById("findIdTabBtn");
    const findPasswordTabBtn = document.getElementById("findPasswordTabBtn");

    const idTab = document.getElementById("idTab");
    const passwordTab = document.getElementById("passwordTab");

    if (!findIdTabBtn || !findPasswordTabBtn || !idTab || !passwordTab) {
        return;
    }

    findIdTabBtn.addEventListener("click", function () {
        findIdTabBtn.classList.add("active");
        findPasswordTabBtn.classList.remove("active");

        idTab.classList.add("active");
        passwordTab.classList.remove("active");
    });

    findPasswordTabBtn.addEventListener("click", function () {
        findPasswordTabBtn.classList.add("active");
        findIdTabBtn.classList.remove("active");

        passwordTab.classList.add("active");
        idTab.classList.remove("active");
    });

    const phoneInputs = document.querySelectorAll(".phone-input");

    phoneInputs.forEach(function (input) {
        input.addEventListener("input", function () {
            let value = input.value.replace(/[^0-9]/g, "");

            if (value.length < 4) {
                input.value = value;
            } else if (value.length < 8) {
                input.value = value.substring(0, 3) + "-" + value.substring(3);
            } else {
                input.value =
                    value.substring(0, 3) +
                    "-" +
                    value.substring(3, 7) +
                    "-" +
                    value.substring(7, 11);
            }
        });
    });
});