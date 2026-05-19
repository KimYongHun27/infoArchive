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

// [공통] 권한 체크 함수 (숫자 ID 비교)
function checkAuthority(loginUserId, authorId, actionText) {
    console.log("로그인 유저 ID:", loginUserId);
    console.log("작성자 ID:", authorId);

    // 1. 데이터가 넘어왔는지 확인
    if (!loginUserId || loginUserId === 'anonymousUser' || !authorId) {
        alert("로그인 정보가 없거나 권한을 확인할 수 없습니다.");
        return false;
    }

    // 2. 숫자형으로 변환 후 비교 (가장 확실함)
    if (Number(loginUserId) !== Number(authorId)) {
        alert(actionText + " 권한이 없습니다.");
        return false;
    }

    return true;
}

// 수정 버튼용 함수
function tryEdit(id, loginUserId, authorId) {
    if (checkAuthority(loginUserId, authorId, "수정")) {
        openCommunityEdit(id);
    }
}

// 삭제 버튼용 함수 (onsubmit에서 호출)
function checkDeleteAuthority(loginUserId, authorId) {
    return checkAuthority(loginUserId, authorId, "삭제") && confirm("정말로 삭제하시겠습니까?");
}