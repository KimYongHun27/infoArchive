const bannerTrack = document.querySelector(".banner-track");
const bannerImages = document.querySelectorAll(".banner-track img");
const prevBtn = document.querySelector(".banner-btn.prev");
const nextBtn = document.querySelector(".banner-btn.next");
const dots = document.querySelectorAll(".banner-dots button");

let currentBanner = 0;
const totalBanner = bannerImages.length;

function moveBanner(index) {
  currentBanner = index;

  if (currentBanner < 0) {
    currentBanner = totalBanner - 1;
  }

  if (currentBanner >= totalBanner) {
    currentBanner = 0;
  }

  bannerTrack.style.transform = `translateX(-${currentBanner * 100}%)`;

  dots.forEach(dot => dot.classList.remove("active"));
  dots[currentBanner].classList.add("active");
}

nextBtn.addEventListener("click", function () {
  moveBanner(currentBanner + 1);
});

prevBtn.addEventListener("click", function () {
  moveBanner(currentBanner - 1);
});

dots.forEach(function (dot, index) {
  dot.addEventListener("click", function () {
    moveBanner(index);
  });
});

setInterval(function () {
  moveBanner(currentBanner + 1);
}, 4000);