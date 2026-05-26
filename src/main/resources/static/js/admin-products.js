document.addEventListener("DOMContentLoaded", function () {
    const rejectOpenButtons = document.querySelectorAll(".reject-open-btn");
    const rejectCloseButtons = document.querySelectorAll(".reject-modal-close");
    const rejectCancelButtons = document.querySelectorAll(".modal-cancel-btn");
    const rejectForms = document.querySelectorAll(".reject-modal-form");

    rejectOpenButtons.forEach(function (button) {
        button.addEventListener("click", function () {
            const productId = button.dataset.productId;
            openRejectModal(productId);
        });
    });

    rejectCloseButtons.forEach(function (button) {
        button.addEventListener("click", function () {
            const productId = button.dataset.productId;
            closeRejectModal(productId);
        });
    });

    rejectCancelButtons.forEach(function (button) {
        button.addEventListener("click", function () {
            const productId = button.dataset.productId;
            closeRejectModal(productId);
        });
    });

    rejectForms.forEach(function (form) {
        form.addEventListener("submit", function (event) {
            const textarea = form.querySelector("textarea[name='rejectReason']");
            const rejectReason = textarea.value.trim();

            if (rejectReason.length === 0) {
                event.preventDefault();
                alert("반려 사유를 입력해주세요.");
                textarea.focus();
                return;
            }

            const confirmed = confirm("작성한 사유로 이 강의를 반려하시겠습니까?");

            if (!confirmed) {
                event.preventDefault();
            }
        });
    });

    document.addEventListener("click", function (event) {
        if (event.target.classList.contains("reject-modal")) {
            event.target.classList.remove("show");
        }
    });

    document.addEventListener("keydown", function (event) {
        if (event.key === "Escape") {
            const openedModal = document.querySelector(".reject-modal.show");

            if (openedModal) {
                openedModal.classList.remove("show");
            }
        }
    });
});

function openRejectModal(productId) {
    const modal = document.getElementById("rejectModal-" + productId);

    if (!modal) {
        return;
    }

    modal.classList.add("show");

    const textarea = modal.querySelector("textarea[name='rejectReason']");

    if (textarea) {
        textarea.focus();
    }
}

function closeRejectModal(productId) {
    const modal = document.getElementById("rejectModal-" + productId);

    if (!modal) {
        return;
    }

    modal.classList.remove("show");
}