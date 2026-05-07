function openReviewEdit(id) {
    const viewBox = document.getElementById("reviewView-" + id);
    const editBox = document.getElementById("reviewEdit-" + id);

    if (!viewBox || !editBox) return;

    viewBox.style.display = "none";
    editBox.style.display = "block";
}

function closeReviewEdit(id) {
    const viewBox = document.getElementById("reviewView-" + id);
    const editBox = document.getElementById("reviewEdit-" + id);

    if (!viewBox || !editBox) return;

    editBox.style.display = "none";
    viewBox.style.display = "block";
}