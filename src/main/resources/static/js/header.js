document.addEventListener("DOMContentLoaded", function () {
    const openBtn = document.getElementById("searchOpenBtn");
    const closeBtn = document.getElementById("searchCloseBtn");
    const searchForm = document.getElementById("searchForm");

    if (!openBtn || !closeBtn || !searchForm) {
        console.log("검색 요소 없음");
        console.log("openBtn =", openBtn);
        console.log("closeBtn =", closeBtn);
        console.log("searchForm =", searchForm);
        return;
    }

    const searchInput = searchForm.querySelector("input[name='kw']");

    openBtn.addEventListener("click", function () {
        searchForm.classList.add("open");

        setTimeout(function () {
            if (searchInput) {
                searchInput.focus();
            }
        }, 100);
    });

    closeBtn.addEventListener("click", function () {
        searchForm.classList.remove("open");

        if (searchInput) {
            searchInput.value = "";
        }
    });

    searchForm.addEventListener("submit", function (event) {
        if (!searchInput || searchInput.value.trim() === "") {
            event.preventDefault();

            if (searchInput) {
                searchInput.focus();
            }
        }
    });
});