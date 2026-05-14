document.addEventListener("DOMContentLoaded", function () {
    const faqItems = document.querySelectorAll(".faq-item");
    const categoryButtons = document.querySelectorAll(".category-btn");
    const searchInput = document.getElementById("faqSearchInput");
    const clearButton = document.getElementById("faqSearchClear");
    const emptyBox = document.getElementById("faqEmpty");

    let currentCategory = "all";

    faqItems.forEach(function (item) {
        const question = item.querySelector(".faq-question");

        question.addEventListener("click", function () {
            item.classList.toggle("open");
        });
    });

    categoryButtons.forEach(function (button) {
        button.addEventListener("click", function () {
            categoryButtons.forEach(function (btn) {
                btn.classList.remove("active");
            });

            button.classList.add("active");
            currentCategory = button.dataset.category;

            filterFaq();
        });
    });

    if (searchInput) {
        searchInput.addEventListener("input", filterFaq);
    }

    if (clearButton) {
        clearButton.addEventListener("click", function () {
            if (searchInput) {
                searchInput.value = "";
            }

            currentCategory = "all";

            categoryButtons.forEach(function (btn) {
                btn.classList.remove("active");

                if (btn.dataset.category === "all") {
                    btn.classList.add("active");
                }
            });

            filterFaq();
        });
    }

    function filterFaq() {
        const keyword = searchInput ? searchInput.value.trim().toLowerCase() : "";
        let visibleCount = 0;

        faqItems.forEach(function (item) {
            const category = item.dataset.category;
            const text = item.innerText.toLowerCase();

            const matchCategory = currentCategory === "all" || category === currentCategory;
            const matchKeyword = keyword === "" || text.includes(keyword);

            if (matchCategory && matchKeyword) {
                item.style.display = "block";
                visibleCount++;
            } else {
                item.style.display = "none";
                item.classList.remove("open");
            }
        });

        if (emptyBox) {
            if (visibleCount === 0) {
                emptyBox.classList.add("show");
            } else {
                emptyBox.classList.remove("show");
            }
        }
    }
});