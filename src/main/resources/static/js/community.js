    document.addEventListener('DOMContentLoaded', function() {
    const categoryButtons = document.querySelectorAll('.category-btn');
    const tableRows = document.querySelectorAll('.community-table tbody tr');

    categoryButtons.forEach(button => {
        button.addEventListener('click', function() {
            // 1. 버튼 활성화 상태 변경
            categoryButtons.forEach(btn => btn.classList.remove('active'));
            this.classList.add('active');

            const selectedCategory = this.getAttribute('data-category');

            // 2. 행 필터링
            tableRows.forEach(row => {
                const rowCategory = row.getAttribute('data-category');

                if (selectedCategory === 'all' || rowCategory === selectedCategory) {
                    row.style.display = ''; // 보이기
                } else {
                    row.style.display = 'none'; // 숨기기
                }
            });
        });
    });
});