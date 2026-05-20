// 수정 모드 상태를 관리하는 변수
let isEditMode = false;

// [공통] 권한 체크 함수
function checkAuthority(loginUserId, authorId, actionText, isAdmin) {
    if (isAdmin === true || String(isAdmin).toLowerCase() === 'true') {
        return true;
    }

    if (!loginUserId || loginUserId === 'anonymousUser' || !authorId) {
        alert("로그인 정보가 없거나 권한을 확인할 수 없습니다.");
        return false;
    }
    if (String(loginUserId) !== String(authorId)) {
        alert(actionText + " 권한이 없습니다.");
        return false;
    }
    return true;
}

// 수정 버튼 클릭 시 실행
function tryEdit(id, loginUserId, authorId, isAdmin) {
    if (!checkAuthority(loginUserId, authorId, "수정", isAdmin)) return;

    const editBtn = document.getElementById("editBtn");
    const deleteBtn = document.getElementById("deleteBtn");
    const viewBox = document.getElementById("CommunityView-" + id);
    const editBox = document.getElementById("CommunityEdit-" + id);

    if (!isEditMode) {
        isEditMode = true;

        viewBox.style.display = "none";
        editBox.style.display = "block";

        editBtn.innerText = "수정 완료";
        deleteBtn.innerText = "취소";

        editBtn.onclick = function() {
            const form = editBox.querySelector("form");
            form.submit();
        };

        deleteBtn.onclick = function() {
            isEditMode = false;
            viewBox.style.display = "block";
            editBox.style.display = "none";
            editBtn.innerText = "수정";
            deleteBtn.innerText = "삭제";

            editBtn.onclick = function() { tryEdit(id, loginUserId, authorId, isAdmin); };
            deleteBtn.onclick = function() { tryDelete(loginUserId, authorId, isAdmin); };
        };
    }
}

// 삭제 버튼 클릭 시 실행
function tryDelete(loginUserId, authorId, isAdmin) {
    if (!checkAuthority(loginUserId, authorId, "삭제", isAdmin)) return;

    if (confirm("정말로 삭제하시겠습니까?")) {
        document.getElementById("deleteForm").submit();
    }
}

// 대댓글 입력창 열고 닫기
function toggleReplyForm(answerId) {
     const replyForm = document.getElementById('replyForm-' + answerId);
     if (replyForm) {
         if (replyForm.style.display === 'none' || replyForm.style.display === '') {
             replyForm.style.display = 'block';
         } else {
             replyForm.style.display = 'none';
         }
     }
}

// 댓글/대댓글 수정 폼 토글 함수
function toggleEditAnswerForm(answerId) {
     const viewBox = document.getElementById('answerView-' + answerId);
     const editForm = document.getElementById('answerEditForm-' + answerId);

     if (viewBox && editForm) {
         if (editForm.style.display === 'none' || editForm.style.display === '') {
             viewBox.style.display = 'none';
             editForm.style.display = 'block';
         } else {
             viewBox.style.display = 'block';
             editForm.style.display = 'none';
         }
     }
}