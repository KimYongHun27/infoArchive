function openReviewEdit(id) {
    const viewBox = document.getElementById("CommunityView-" + id);
    const editBox = document.getElementById("CommunityEdit-" + id);

    if (!viewBox || !editBox) return;

    viewBox.style.display = "none";
    editBox.style.display = "block";
}

function closeReviewEdit(id) {
    const viewBox = document.getElementById("CommunityView-" + id);
    const editBox = document.getElementById("CommunityEdit-" + id);

    if (!viewBox || !editBox) return;

    editBox.style.display = "none";
    viewBox.style.display = "block";
}