document.addEventListener('DOMContentLoaded', function() {
    const categoryButtons = document.querySelectorAll('.category-btn');

    // 1. 페이지 로드 시 URL의 카테고리 파라미터에 따라 버튼 활성화 상태 설정
    const urlParams = new URLSearchParams(window.location.search);
    const currentCategory = urlParams.get('category') || 'all';

    categoryButtons.forEach(button => {
        const buttonCategory = button.getAttribute('data-category');

        // 현재 선택된 카테고리와 버튼의 data-category가 일치하면 active 클래스 추가
        if (buttonCategory === currentCategory) {
            button.classList.add('active');
        } else {
            button.classList.remove('active');
        }

        // 2. 버튼 클릭 시 해당 카테고리 페이지로 이동 (서버 요청)
        button.addEventListener('click', function() {
            const selectedCategory = this.getAttribute('data-category');
            // 페이지는 0번(첫 페이지)으로 리셋하며 카테고리 이동
            location.href = '/community?page=0&category=' + encodeURIComponent(selectedCategory);
        });
    });
});