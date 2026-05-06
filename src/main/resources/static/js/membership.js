const openPaymentModal = document.getElementById("openPaymentModal");
const closePaymentModal = document.getElementById("closePaymentModal");
const paymentModal = document.getElementById("paymentModal");
const methods = document.querySelectorAll(".method");

openPaymentModal.addEventListener("click", function () {
  paymentModal.classList.add("active");
});

closePaymentModal.addEventListener("click", function () {
  paymentModal.classList.remove("active");
});

paymentModal.addEventListener("click", function (event) {
  if (event.target === paymentModal) {
    paymentModal.classList.remove("active");
  }
});

methods.forEach(function (method) {
  method.addEventListener("click", function () {
    methods.forEach(item => item.classList.remove("active"));
    method.classList.add("active");
  });
});