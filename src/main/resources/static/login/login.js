function signIn() {
    // 사용자가 입력한 데이터 수집
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;

    // 데이터 유효성 검사
    if (email === "" || password === "") {
        alert("모든 필드를 입력해주세요.");
        return;
    }

    // 데이터를 JSON 형식으로 변환
    var requestData = {
        email: email,
        password: password
    };

    // POST 요청을 보냄
    fetch("/signIn", {
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
        alert("로그인이 완료되었습니다.");
        window.location.href = "/home";
    })
    .catch(error => {
        // 오류 발생 시 처리할 내용
        console.error("Error:", error);
        alert("로그인 중 오류가 발생했습니다.");
    });
}