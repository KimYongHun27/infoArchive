let player = null;
let progressTimer = null;
let maxWatchedSeconds = 0;

function onYouTubeIframeAPIReady() {
    const iframe = document.getElementById("productPlayer");

    if (!iframe) {
        return;
    }

    player = new YT.Player("productPlayer", {
        events: {
            onStateChange: onPlayerStateChange
        }
    });
}

function onPlayerStateChange(event) {
    if (event.data === YT.PlayerState.PLAYING) {
        startProgressTracking();
    }

    if (event.data === YT.PlayerState.PAUSED || event.data === YT.PlayerState.ENDED) {
        saveProgress();
        stopProgressTracking();
    }
}

function startProgressTracking() {
    if (progressTimer !== null) {
        return;
    }

    progressTimer = setInterval(function () {
        saveProgress();
    }, 10000);
}

function stopProgressTracking() {
    if (progressTimer !== null) {
        clearInterval(progressTimer);
        progressTimer = null;
    }
}

function saveProgress() {
    const iframe = document.getElementById("productPlayer");

    if (!iframe || !player) {
        return;
    }

    const productId = iframe.dataset.productId;

    if (!productId) {
        return;
    }

    if (typeof player.getCurrentTime !== "function"
        || typeof player.getDuration !== "function") {
        return;
    }

    const currentTime = Math.floor(player.getCurrentTime());
    const duration = Math.floor(player.getDuration());

    if (!duration || duration <= 0) {
        return;
    }

    maxWatchedSeconds = Math.max(maxWatchedSeconds, currentTime);

    fetch(`/product/${productId}/progress`, {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: new URLSearchParams({
            watchedSeconds: maxWatchedSeconds,
            totalSeconds: duration
        })
    }).catch(function (error) {
        console.error("학습 진도 저장 실패:", error);
    });
}