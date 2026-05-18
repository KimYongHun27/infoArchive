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

    // 숫자만 입력 가능
    const numberOnlyInputs = document.querySelectorAll(
        "input[name='cardNumber'], input[name='cardExpire'], input[name='cardCvc'], input[name='cardPassword']"
    );

    numberOnlyInputs.forEach(function (input) {
        input.addEventListener("input", function () {
            input.value = input.value.replace(/[^0-9]/g, "");
        });
    });

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

        // 무통장입금이면 카드정보 없이 바로 제출
        paymentForm.submit();
    });

    // 최종 결제하기 submit 검증
    paymentForm.addEventListener("submit", function (event) {
        const selectedPayment = document.querySelector("input[name='paymentMethod']:checked");

        if (selectedPayment && selectedPayment.value === "CARD") {
            const cardNumber = document.querySelector("input[name='cardNumber']").value.trim();
            const cardExpire = document.querySelector("input[name='cardExpire']").value.trim();
            const cardCvc = document.querySelector("input[name='cardCvc']").value.trim();
            const cardPassword = document.querySelector("input[name='cardPassword']").value.trim();

            if (!/^[0-9]{19}$/.test(cardNumber)) {
                alert("카드번호는 숫자 19자리로 입력해주세요.");
                event.preventDefault();
                return;
            }

            if (!/^[0-9]{4}$/.test(cardExpire)) {
                alert("유효기간은 숫자 4자리로 입력해주세요. 예: 1228");
                event.preventDefault();
                return;
            }

            if (!/^[0-9]{3}$/.test(cardCvc)) {
                alert("CVC는 숫자 3자리로 입력해주세요.");
                event.preventDefault();
                return;
            }

            if (!/^[0-9]{2}$/.test(cardPassword)) {
                alert("카드 비밀번호는 앞 2자리만 입력해주세요.");
                event.preventDefault();
            }
        }
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