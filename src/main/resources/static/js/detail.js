// 수정 모드 상태를 관리하는 변수
let isEditMode = false;

// [공통] 권한 체크 함수 ( 파라미터에 isAdmin 추가)
function checkAuthority(loginUserId, authorId, actionText, isAdmin) {
    if (isAdmin === true || String(isAdmin).toLowerCase() === 'true') {
        return true;
        }

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

// 수정 버튼 클릭 시 실행 ( 파라미터에 isAdmin 추가)
function tryEdit(id, loginUserId, authorId, isAdmin) {
    //  checkAuthority 호출 시 isAdmin 넘겨주기
    if (!checkAuthority(loginUserId, authorId, "수정", isAdmin)) return;

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
            const form = editBox.querySelector("form");
            form.action = "/community/SujungProc";
            form.submit();
        };

        // 취소 버튼 동작 변경 (원래 상태로 복구)
        deleteBtn.onclick = function() {
            isEditMode = false;
            viewBox.style.display = "block";
            editBox.style.display = "none";
            editBtn.innerText = "수정";
            deleteBtn.innerText = "삭제";

            //  원래 함수로 복구할 때도 isAdmin 포함해서 넘겨주기
            editBtn.onclick = function() { tryEdit(id, loginUserId, authorId, isAdmin); };
            deleteBtn.onclick = function() { tryDelete(loginUserId, authorId, isAdmin); };
        };
    }
}

// 삭제 버튼 클릭 시 실행 ( 파라미터에 isAdmin 추가)
function tryDelete(loginUserId, authorId, isAdmin) {
    //  checkAuthority 호출 시 isAdmin 넘겨주기
    if (!checkAuthority(loginUserId, authorId, "삭제", isAdmin)) return;

    if (confirm("정말로 삭제하시겠습니까?")) {
        document.getElementById("deleteForm").submit();
    }
}