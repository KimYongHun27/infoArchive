lucide.createIcons();

let totalSeconds = 533041;

setInterval(() => {

    totalSeconds--;

    const days = Math.floor(totalSeconds / 86400);
    const hours = Math.floor((totalSeconds % 86400) / 3600);
    const minutes = Math.floor((totalSeconds % 3600) / 60);
    const seconds = totalSeconds % 60;

    document.getElementById('day').innerText = days;
    document.getElementById('hour').innerText = hours;
    document.getElementById('min').innerText = minutes;
    document.getElementById('sec').innerText = seconds;

},1000);