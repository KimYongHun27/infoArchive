const daysEl = document.getElementById("days");
const hoursEl = document.getElementById("hours");
const minutesEl = document.getElementById("minutes");
const secondsEl = document.getElementById("seconds");

if (daysEl && hoursEl && minutesEl && secondsEl) {
  const endTime =
    new Date().getTime()
    + (30 * 24 * 60 * 60 * 1000)
    + (12 * 60 * 60 * 1000)
    + (4 * 60 * 1000)
    + (1 * 1000);

  function updateCountdown() {
    const now = new Date().getTime();
    const distance = endTime - now;

    if (distance <= 0) {
      daysEl.textContent = "0";
      hoursEl.textContent = "0";
      minutesEl.textContent = "0";
      secondsEl.textContent = "0";
      return;
    }

    daysEl.textContent = Math.floor(distance / (1000 * 60 * 60 * 24));
    hoursEl.textContent = Math.floor((distance / (1000 * 60 * 60)) % 24);
    minutesEl.textContent = Math.floor((distance / (1000 * 60)) % 60);
    secondsEl.textContent = Math.floor((distance / 1000) % 60);
  }

  updateCountdown();
  setInterval(updateCountdown, 1000);
}