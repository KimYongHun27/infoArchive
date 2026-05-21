document.addEventListener("DOMContentLoaded", function () {
    const phoneInputs = document.querySelectorAll(".phone-input");

    phoneInputs.forEach(function (input) {
        input.addEventListener("input", function () {
            let value = input.value.replace(/[^0-9]/g, "");

            if (value.length <= 3) {
                input.value = value;
            } else if (value.length <= 7) {
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