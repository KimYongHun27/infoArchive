const buttons = document.querySelectorAll('.cart-add-btn');

buttons.forEach(button => {
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