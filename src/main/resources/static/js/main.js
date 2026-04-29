const bannerTrack = document.querySelector(".banner-track");
const bannerImages = document.querySelectorAll(".banner-track img");
const prevBtn = document.querySelector(".banner-btn.prev");
const nextBtn = document.querySelector(".banner-btn.next");
const currentText = document.getElementById("banner-current");
const totalText = document.getElementById("banner-total");
const progressBar = document.querySelector(".banner-progress-bar");

let currentBanner = 0;
const totalBanner = bannerImages.length;
const slideTime = 4000;
let timer;

if (totalText) {
  totalText.textContent = totalBanner;
}

function resetProgress() {
  progressBar.style.transition = "none";
  progressBar.style.width = "0%";

  setTimeout(() => {
    progressBar.style.transition = `width ${slideTime}ms linear`;
    progressBar.style.width = "100%";
  }, 50);
}

function moveBanner(index) {
  currentBanner = index;

  if (currentBanner < 0) currentBanner = totalBanner - 1;
  if (currentBanner >= totalBanner) currentBanner = 0;

  bannerTrack.style.transform = `translateX(-${currentBanner * 100}%)`;

  if (currentText) {
    currentText.textContent = currentBanner + 1;
  }

  resetProgress();

  clearInterval(timer);
  timer = setInterval(() => {
    moveBanner(currentBanner + 1);
  }, slideTime);
}

nextBtn.addEventListener("click", function () {
  moveBanner(currentBanner + 1);
});

prevBtn.addEventListener("click", function () {
  moveBanner(currentBanner - 1);
});

moveBanner(0);