document.addEventListener('DOMContentLoaded', function() {
    // [1] 카테고리 버튼 관리
    const categoryButtons = document.querySelectorAll('.category-btn');
    const urlParams = new URLSearchParams(window.location.search);
    const currentCategory = urlParams.get('category') || 'all';
    const currentKw = urlParams.get('kw') || ''; // 현재 검색어도 가져옴

    categoryButtons.forEach(button => {
        const buttonCategory = button.getAttribute('data-category');

        // 현재 카테고리 활성화 표시
        if (buttonCategory === currentCategory) {
            button.classList.add('active');
        } else {
            button.classList.remove('active');
        }

        // 카테고리 클릭 시 이동 (검색어 유지 여부는 선택이지만, 보통은 리셋함)
        button.addEventListener('click', function() {
            const selectedCategory = this.getAttribute('data-category');
            location.href = `/community?page=0&category=${encodeURIComponent(selectedCategory)}&kw=${encodeURIComponent(currentKw)}`;
        });
    });

    // [2] 검색 기능 관리 (루프 밖으로 뺌)
    const btn_search = document.getElementById("btn_search");
    const search_input = document.getElementById("search_kw");

    if (btn_search && search_input) {
        // 검색 버튼 클릭 시
        btn_search.addEventListener('click', function() {
            const kw = search_input.value;
            // 검색 시 현재 선택된 카테고리는 유지함
            location.href = `/community?page=0&category=${encodeURIComponent(currentCategory)}&kw=${encodeURIComponent(kw)}`;
        });

        // 엔터키 입력 시 검색
        search_input.addEventListener("keydown", function(e) {
            if (e.key === "Enter") {
                btn_search.click();
            }
        });
    }
});