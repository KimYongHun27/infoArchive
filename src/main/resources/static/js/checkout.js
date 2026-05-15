let originalPrice = 239000; // 서버에서 받은 price라고 보면 됨
let discount = 0;

const paymentBtn = document.getElementById('payment-btn');
paymentBtn.addEventListener('click', () => {
    window.location.href = 'payment-complete';
});
/* =========================
   쿠폰 적용
========================= */
function applyCoupon() {
    const couponInput = document.getElementById("couponInput").value.trim();
    const result = document.getElementById("couponResult");

    // 쿠폰 예시 로직
    if (couponInput === "DISCOUNT10") {
        discount = 0.1; // 10%
        result.innerText = "10% 할인 적용 완료!";
    }
    else if (couponInput === "DISCOUNT5000") {
        discount = 5000; // 5000원
        result.innerText = "5,000원 할인 적용 완료!";
    }
    else {
        discount = 0;
        result.innerText = "유효하지 않은 쿠폰입니다.";
    }

    updateTotalPrice();
}

/* =========================
   총 결제 금액 계산
========================= */
function updateTotalPrice() {
    let finalPrice;

    if (discount < 1) {
        finalPrice = originalPrice - (originalPrice * discount);
    } else {
        finalPrice = originalPrice - discount;
    }

    if (finalPrice < 0) finalPrice = 0;

    document.getElementById("totalPrice").innerText =
        Math.floor(finalPrice).toLocaleString() + "원";
}

/* =========================
   결제 요청 (서버 전송)
========================= */
function pay() {

    const agree = document.getElementById("agreeCheck").checked;

    if (!agree) {
        alert("이용약관에 동의해주세요.");
        return;
    }

    const payMethod = document.querySelector('input[name="pay"]:checked').value;

    const totalPriceText = document.getElementById("totalPrice").innerText;
    const totalPrice = Number(totalPriceText.replace(/[^0-9]/g, ""));

    const productName = document.querySelector(".name").innerText;

    const requestData = {
        productName: productName,
        payMethod: payMethod,
        price: totalPrice
    };

    fetch("/payment/checkout", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(requestData)
    })
    .then(res => res.json())
    .then(data => {
        if (data.success) {
            alert("결제 완료!");
            window.location.href = "/payment/success";
        } else {
            alert("결제 실패: " + data.message);
        }
    })
    .catch(err => {
        console.error(err);
        alert("서버 오류 발생");
    });
}