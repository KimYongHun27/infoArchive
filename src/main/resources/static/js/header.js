document.addEventListener("DOMContentLoaded", function () {
    const openBtn = document.getElementById("searchOpenBtn");
    const closeBtn = document.getElementById("searchCloseBtn");
    const searchBox = document.getElementById("searchBox");

    if (!openBtn || !closeBtn || !searchBox) {
        return;
    }

    const searchInput = searchBox.querySelector("input[name='kw']");

    openBtn.addEventListener("click", function () {
        searchBox.classList.add("open");

        setTimeout(function () {
            searchInput.focus();
        }, 100);
    });

    closeBtn.addEventListener("click", function () {
        searchBox.classList.remove("open");
        searchInput.value = "";
    });

    searchBox.addEventListener("submit", function (event) {
        if (searchInput.value.trim() === "") {
            event.preventDefault();
            searchInput.focus();
        }
    });
});