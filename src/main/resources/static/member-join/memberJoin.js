function handleInput(el, maxLength) {
    if (el.value.length > maxLength) {
        el.value = el.value.slice(0, maxLength);
    }
}
function register() {
    // 사용자가 입력한 데이터 수집
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var password_check = document.getElementById("password_check").value;
    var name = document.getElementById("name").value;
    var year = document.getElementById("year").value;
    var month = document.getElementById("month").value;
    var day = document.getElementById("day").value;
    var address = document.getElementById("address").value;
    var phone = document.getElementById("phone").value;

    // 생년월일을 합쳐서 하나의 값으로 만들기 (예: 980610)
    var birthdate = year.substring(2) + month.padStart(2, '0') + day.padStart(2, '0');

    // 데이터 유효성 검사
    if (email === "" || password === "" || password_check === "" || name === "" || birthdate === "" || address === "" || phone === "") {
        alert("모든 필드를 입력해주세요.");
        return;
    }
    if (password !== password_check) {
        alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        return;
    }

    // 데이터를 JSON 형식으로 변환
    var requestData = {
        email: email,
        password: password,
        name: name,
        birthdate: birthdate,
        address: address,
        phone: phone
    };

    // POST 요청을 보냄
    fetch("/signUp", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(requestData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("HTTP error, status = " + response.status);
        }
        return response.json();
    })
    .then(data => {
        // 회원가입 성공 시 처리할 내용
        alert("회원가입이 완료되었습니다.");
        // 필요하다면 페이지를 리디렉션하거나 다른 작업을 수행할 수 있습니다.
        window.location.href = "/home";
    })
    .catch(error => {
        console.error("Error:", error);
        alert("회원가입 중 오류가 발생했습니다.");
    });
}

function updateBirthdate() {
    const year = document.getElementById('year').value;
    const month = document.getElementById('month').value.padStart(2, '0');
    const day = document.getElementById('day').value.padStart(2, '0');
    const birthdate = year.substring(2) + month + day;

    document.getElementById('birthdate').value = birthdate;
}

function setBirthdate(event) {
    event.preventDefault(); // 기본 이벤트 제거

    // 생년월일 값을 hidden input에 설정
    updateBirthdate();

    // 폼 제출
    document.forms[0].submit();
}
