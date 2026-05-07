function togglePassword(id) {
    const input = document.getElementById(id);

    if (!input) {
        return;
    }

    input.type = input.type === "password" ? "text" : "password";
}

function checkAll() {
    const agreeAll = document.getElementById("agreeAll");
    const checks = document.querySelectorAll(".required-check");

    if (!agreeAll) {
        return;
    }

    checks.forEach(function (check) {
        check.checked = agreeAll.checked;
    });
}

function validateSignup() {
    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();
    const passwordCheck = document.getElementById("passwordCheck").value.trim();

    if (name === "") {
        alert("이름을 입력해주세요.");
        return false;
    }

    if (email === "") {
        alert("이메일을 입력해주세요.");
        return false;
    }

    if (password === "") {
        alert("비밀번호를 입력해주세요.");
        return false;
    }

    if (password !== passwordCheck) {
        alert("비밀번호가 일치하지 않습니다.");
        return false;
    }

    const checks = document.querySelectorAll(".required-check");

    for (const check of checks) {
        if (!check.checked) {
            alert("필수 약관에 모두 동의해주세요.");
            return false;
        }
    }

    return true;
}