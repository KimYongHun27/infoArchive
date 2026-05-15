const moveToCartButtons = document.querySelectorAll('.cart-add-btn');
const moveToMyPageButton = document.getElementById('black-btn');
const moveToOrderHistoryButton = document.getElementById('white-btn');

moveToCartButtons.forEach(button => {
    button.addEventListener('click', (event) => {
        window.location.href = '/cart';
    });
});

moveToMyPageButton.addEventListener('click', () => {
    window.location.href = '/taking-course';
});

moveToOrderHistoryButton.addEventListener('click', () => {
    window.location.href = '/order-details';
});