const moveToCartButtons = document.querySelectorAll('.cart-add-btn');
const moveToMyPageButton = document.getElementById('black-btn');
const moveToOrderHistoryButton = document.getElementById('white-btn');

moveToCartButtons.forEach(button => {
    button.addEventListener('click', (event) => {
        // 클릭된 버튼의 data-id 값을 가져옴
        const dataId = event.target.getAttribute('data-id');

        const data = {
            id: dataId,
            msg: "button_clicked"
        };

        const queryString = new URLSearchParams(data).toString();
        window.location.href = `cart`;
    });
});

moveToMyPageButton.addEventListener('click', () => {
    window.location.href = 'taking-course';
});

moveToOrderHistoryButton.addEventListener('click', () => {
    window.location.href = 'orders';
});
