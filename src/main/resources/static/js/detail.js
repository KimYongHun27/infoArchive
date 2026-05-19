// 수정 모드 상태를 관리하는 변수
let isEditMode = false;

// [공통] 권한 체크 함수
function checkAuthority(loginUserId, authorId, actionText) {
    if (!loginUserId || loginUserId === 'anonymousUser' || !authorId) {
        alert("로그인 정보가 없거나 권한을 확인할 수 없습니다.");
        return false;
    }
    if (Number(loginUserId) !== Number(authorId)) {
        alert(actionText + " 권한이 없습니다.");
        return false;
    }
    return true;
}

// 수정 버튼 클릭 시 실행
function tryEdit(id, loginUserId, authorId) {
    if (!checkAuthority(loginUserId, authorId, "수정")) return;

    const editBtn = document.getElementById("editBtn");
    const deleteBtn = document.getElementById("deleteBtn");
    const viewBox = document.getElementById("CommunityView-" + id);
    const editBox = document.getElementById("CommunityEdit-" + id);

    if (!isEditMode) {
        // --- 수정 모드 진입 ---
        isEditMode = true;

        viewBox.style.display = "none";
        editBox.style.display = "block";

        editBtn.innerText = "수정 완료";
        deleteBtn.innerText = "취소";

        // 수정 완료 버튼 동작 변경
        editBtn.onclick = function() {
            editBox.querySelector("form").submit();
        };

        // 취소 버튼 동작 변경 (원래 상태로 복구)
        deleteBtn.onclick = function() {
            isEditMode = false;
            viewBox.style.display = "block";
            editBox.style.display = "none";
            editBtn.innerText = "수정";
            deleteBtn.innerText = "삭제";

            // 원래의 함수로 다시 연결
            editBtn.onclick = function() { tryEdit(id, loginUserId, authorId); };
            deleteBtn.onclick = function() { tryDelete(loginUserId, authorId); };
        };
    }
}

// 삭제 버튼 클릭 시 실행
function tryDelete(loginUserId, authorId) {
    if (!checkAuthority(loginUserId, authorId, "삭제")) return;

    if (confirm("정말로 삭제하시겠습니까?")) {
        document.getElementById("deleteForm").submit();
    }
}