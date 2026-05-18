document.addEventListener("DOMContentLoaded", function () {
    const payOpenBtn = document.getElementById("payOpenBtn");
    const cardModal = document.getElementById("cardModal");
    const cardModalClose = document.getElementById("cardModalClose");
    const paymentForm = document.getElementById("paymentForm");

    if (!payOpenBtn || !cardModal || !paymentForm) {
        console.log("결제 JS 연결 실패");
        console.log("payOpenBtn =", payOpenBtn);
        console.log("cardModal =", cardModal);
        console.log("paymentForm =", paymentForm);
        return;
    }

    // 결제하기 버튼 클릭
    payOpenBtn.addEventListener("click", function () {
        const selectedPayment = document.querySelector("input[name='paymentMethod']:checked");
        const agreeTerms = document.querySelector("input[name='agreeTerms']");

        if (!agreeTerms || !agreeTerms.checked) {
            alert("이용약관에 동의해주세요.");
            return;
        }

        if (!selectedPayment) {
            alert("결제수단을 선택해주세요.");
            return;
        }

        // 신용카드면 카드정보 모달 열기
        if (selectedPayment.value === "CARD") {
            cardModal.classList.add("open");
            return;
        }

        // 무통장입금이면 바로 결제 처리
        paymentForm.submit();
    });

    // 모달 닫기 버튼
    if (cardModalClose) {
        cardModalClose.addEventListener("click", function () {
            cardModal.classList.remove("open");
        });
    }

    // 모달 바깥 클릭 시 닫기
    cardModal.addEventListener("click", function (event) {
        if (event.target === cardModal) {
            cardModal.classList.remove("open");
        }
    });
});