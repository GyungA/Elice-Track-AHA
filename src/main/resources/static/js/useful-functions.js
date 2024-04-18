// 문자열+숫자로 이루어진 랜덤 5글자 반환
export const randomId = () => {
  return Math.random().toString(36).substring(2, 7);
};

// 이메일 형식인지 확인 (true 혹은 false 반환)
export const validateEmail = (email) => {
  return String(email)
    .toLowerCase()
    .match(
      /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    );
};

// 주소창의 url로부터 params를 얻어 객체로 만듦
export const getUrlParams = () => {
  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);

  const result = {};

  for (const [key, value] of urlParams) {
    result[key] = value;
  }

  return result;
};

// 숫자에 쉼표를 추가함. (10000 -> 10,000)
export const addCommas = (n) => {
  return n.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
};

// 로그인 여부(토큰 존재 여부) 확인
export const checkLogin = () => {
  const token = sessionStorage.getItem("token");
  if (!token) {
    // 현재 페이지의 url 주소 추출하기
    const pathname = window.location.pathname;
    const search = window.location.search;

    // 로그인 후 다시 지금 페이지로 자동으로 돌아가도록 하기 위한 준비작업임.
    window.location.replace(`/login?previouspage=${pathname + search}`);
  }
};

// 관리자 여부 확인
export const checkAdmin = async () => {
  // 우선 화면을 가리고 시작함 -> 화면 번쩍거림으로 인해 일단 미적용
  //window.document.body.style.display = 'none';

  const token = sessionStorage.getItem("token");

  // 우선 토큰 존재 여부 확인
  if (!token) {
    // 현재 페이지의 url 주소 추출하기
    const pathname = window.location.pathname;
    const search = window.location.search;

    // 로그인 후 다시 지금 페이지로 자동으로 돌아가도록 하기 위한 준비작업임.
    window.location.replace(`/login?previouspage=${pathname + search}`);
  }

  // 관리자 토큰 여부 확인
  const res = await fetch("/users/admin-check", {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  const { result } = await res.json();

  if (result === "success") {
    window.document.body.style.display = "block";

    return;
  } else {
    alert("관리자 전용 페이지입니다.");

    window.location.replace("/");
  }
};

// 로그인 상태일 때에는 접근 불가한 페이지로 만듦. (회원가입 페이지 등)
export const blockIfLogin = () => {
  const token = sessionStorage.getItem("token");

  if (token) {
    alert("로그인 상태에서는 접근할 수 없는 페이지입니다.");
    window.location.replace("/");
  }
};

// 해당 주소로 이동하는 콜백함수를 반환함.
// 이벤트 핸들 함수로 쓰면 유용함
export const navigate = (pathname) => {
  return function () {
    window.location.href = pathname;
  };
};

// 13,000원, 2개 등의 문자열에서 쉼표, 글자 등 제외 후 숫자만 뺴냄
// 예시: 13,000원 -> 13000, 20,000개 -> 20000
export const convertToNumber = (string) => {
  return parseInt(string.replace(/(,|개|원)/g, ""));
};

// ms만큼 기다리게 함.
export const wait = (ms) => {
  return new Promise((r) => setTimeout(r, ms));
};

// 긴 문자열에서 뒷부분을 ..으로 바꿈
export const compressString = (string) => {
  if (string.length > 10) {
    return string.substring(0, 9) + "..";
  }
  return string;
};

// 주소에 특정 params가 없다면 잘못된 접근으로 하고 싶은 경우 사용.
export const checkUrlParams = (key) => {
  const { [key]: params } = getUrlParams();

  if (!params) {
    window.location.replace("/page-not-found");
  }
};

// 배열 혹은 객체에서 랜덤으로 1개 고름
export const randomPick = (items) => {
  const isArray = Array.isArray(items);

  // 배열인 경우
  if (isArray) {
    const randomIndex = [Math.floor(Math.random() * items.length)];

    return items[randomIndex];
  }

  // 객체인 경우
  const keys = Object.keys(items);
  const randomIndex = [Math.floor(Math.random() * keys.length)];
  const randomKey = keys[randomIndex];

  return items[randomKey];
};

export let setCookie = function (name, value) {
  // var date = new Date();
  // date.setTime(date.getTime() + exp * 24 * 60 * 60 * 1000);
  document.cookie = name + "=" + value + ";path=/";
};

export let getCookie = function (name) {
  console.log(document.cookie);
  let value = document.cookie.match("(^|;) ?" + name + "=([^;]*)(;|$)");
  return value ? value[2] : null;
};

export let redirect = function (address) {
  const hostName = window.location.hostname;
  let additionalAddr = "";
  if (hostName === "localhost") {
    additionalAddr = "/ShoppingProject/src/main/resources";
  }
  window.location.href = `${additionalAddr}/static${address}`;
};

export let formatPhoneNumber = function (phoneNumber) {
  // 전화번호에서 "-"를 제외한 숫자만 추출
  const cleaned = phoneNumber.replace(/\D/g, "");

  // 전화번호 길이에 따라 적절한 형식으로 변환
  let formatted;
  if (cleaned.length === 11) {
    formatted = cleaned.replace(/^(\d{3})(\d{4})(\d{4})$/, "$1-$2-$3");
  } else if (cleaned.length === 10) {
    formatted = cleaned.replace(/^(\d{3})(\d{3})(\d{4})$/, "$1-$2-$3");
  } else {
    // 예외 처리: 전화번호 길이가 10자 또는 11자가 아닌 경우
    console.error("Invalid phone number length");
    return phoneNumber; // 변환하지 않고 그대로 반환
  }

  return formatted;
};

// 페이지네이션
export let activePageButtons = function (endPageNumber) {
  //페이지 번호 클릭시
  const pageButtons = document.querySelectorAll(".page-number-button");
  for (let i = 0; i < endPageNumber; i++) {
    pageButtons[i].addEventListener("click", () => {
      // redirectOrders(userId, i);
      setCookie("page", i);
      redirect("/seller-order-management/seller-order-management.html");
    });
  }

  //왼오 클릭시.
  const leftPage = document.querySelector(".page-before");
  const rightPage = document.querySelector(".page-after");
  leftPage.addEventListener("click", () => {
    let currentPage = getCookie("page");
    if (currentPage > 0) {
      setCookie("page", currentPage - 1);
      redirect("/seller-order-management/seller-order-management.html");
    } else {
      alert("첫 페이지입니다.");
    }
  });
  rightPage.addEventListener("click", () => {
    let currentPage = getCookie("page");
    if (currentPage < endPageNumber - 1) {
      setCookie("page", currentPage + 1);
      redirect("/seller-order-management/seller-order-management.html");
    } else {
      alert("마지막 페이지입니다.");
    }
  });
};

// 페이지 번호를 생성하는 함수를 정의합니다.
export let createPageNumber = function createPageNumber(
  endPageNumber,
  pageWrapper
) {
  for (let i = endPageNumber; i >= 1; i--) {
    // 새 페이지 번호를 생성합니다.
    let newPageNumber = document.createElement("button");
    newPageNumber.setAttribute("class", "page-number-button");
    // newPageButton.classList.add(`${i}`);

    // 페이지 번호를 생성하여 페이지 번호 요소에 추가합니다.
    let pageNumberSpan = document.createElement("span");
    pageNumberSpan.setAttribute("class", "page-number");
    pageNumberSpan.textContent = i;

    // 페이지 번호 요소에 페이지 번호를 추가합니다.
    newPageNumber.appendChild(pageNumberSpan);

    // 생성된 페이지 번호를 적절한 위치에 추가합니다.
    let pageBeforeLink = pageWrapper.querySelector(".page-before");
    pageWrapper.insertBefore(newPageNumber, pageBeforeLink.nextSibling);
  }
};

// 주변 다른 파일 것도 여기서 일괄 export 함
export { createNavbar } from "./navbar.js";
