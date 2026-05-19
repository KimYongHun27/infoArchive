// 수정 폼 열기
function openCommunityEdit(id) {
    const viewBox = document.getElementById("CommunityView-" + id);
    const editBox = document.getElementById("CommunityEdit-" + id);

    if (viewBox && editBox) {
        viewBox.style.display = "none";
        editBox.style.display = "block";
    }
}

// 수정 폼 닫기 (취소)
function closeCommunityEdit(id) {
    const viewBox = document.getElementById("CommunityView-" + id);
    const editBox = document.getElementById("CommunityEdit-" + id);

    if (viewBox && editBox) {
        editBox.style.display = "none";
        viewBox.style.display = "block";
    }
}

// [공통] 권한 체크 함수
function checkAuthority(loginUser, author, actionText) {
    if (!loginUser || loginUser === 'anonymousUser') {
        alert("로그인이 필요한 서비스입니다.");
        return false;
    }
    if (!author) {
        alert("작성자 정보를 찾을 수 없습니다.");
        return false;
    }
    if (loginUser !== author) {
        alert(actionText + " 권한이 없습니다."); // 여기서 "수정 권한이 없습니다" 등이 뜸
        return false;
    }
    return true;
}

// 수정 버튼용 함수
function tryEdit(id, loginUser, author) {
    if (checkAuthority(loginUser, author, "수정")) {
        openCommunityEdit(id);
    }
}

// 삭제 버튼용 함수 (onsubmit에서 호출)
function checkDeleteAuthority(loginUser, author) {
    if (checkAuthority(loginUser, author, "삭제")) {
        return confirm("정말로 삭제하시겠습니까?");
    }
    return false;
}

// ... 나머지 openCommunityEdit, closeCommunityEdit 함수는 그대로 유지