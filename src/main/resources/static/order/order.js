import * as Api from "../js/api.js";
import {
  setCookie,
  getCookie,
  redirect,
  formatPhoneNumber,
} from "../js/useful-functions.js";

//host
const host = "http://34.22.75.198:8080";
// const host = "http://localhost:8080";

// 요소(element), input 혹은 상수
const subtitleCart = document.querySelector("#subtitleCart");
const receiverNameInput = document.querySelector("#receiverName");
const receiverPhoneNumberInput = document.querySelector("#receiverPhoneNumber");
const postalCodeInput = document.querySelector("#postalCode");
const searchAddressButton = document.querySelector("#searchAddressButton");
const address1Input = document.querySelector("#address1");
const address2Input = document.querySelector("#address2");
const requestSelectBox = document.querySelector("#requestSelectBox");
const customRequestContainer = document.querySelector(
  "#customRequestContainer"
);
const customRequestInput = document.querySelector("#customRequest");
const productsTitleElem = document.querySelector("#productsTitle");
const productsPaymentElem = document.querySelector("#productsPayment");
const productsTotalElem = document.querySelector("#productsTotal");
// const deliveryFeeElem = document.querySelector("#deliveryFee");
const orderTotalElem = document.querySelector("#orderTotal");
const checkoutButton = document.querySelector("#checkoutButton");

// 결제자
const buyerNameElem = document.querySelector("#buyerName");
const buyerPhoneElem = document.querySelector("#buyerPhone");
const buyerEmailElem = document.querySelector("#buyerEmail");
const buyerGradeElem = document.querySelector("#buyerGrade");
const buyerAddressElem = document.querySelector("#buyerAddress");

const requestOption = {
  1: "직접 수령하겠습니다.",
  2: "배송 전 연락바랍니다.",
  3: "부재 시 경비실에 맡겨주세요.",
  4: "부재 시 문 앞에 놓아주세요.",
  5: "부재 시 택배함에 넣어주세요.",
  6: "직접 입력",
};

//임시
setCookie("userId", 1);
setCookie("orderId", 1);

const userId = getCookie("userId");
const orderId = getCookie("orderId");

// checkLogin();
addAllElements();
addAllEvents();

// html에 요소를 추가하는 함수들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllElements() {
  insertOrderSummary(userId, orderId); //나중에 userId, orderId 넣기
}

// addEventListener들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllEvents() {
  searchAddressButton.addEventListener("click", searchAddress);
  checkoutButton.addEventListener("click", () => {
    doCheckout(userId, orderId);
  });
}

// Daum 주소 API (사용 설명 https://postcode.map.daum.net/guide)
function searchAddress() {
  new daum.Postcode({
    oncomplete: function (data) {
      let addr = "";
      let extraAddr = "";

      if (data.userSelectedType === "R") {
        addr = data.roadAddress;
      } else {
        addr = data.jibunAddress;
      }

      if (data.userSelectedType === "R") {
        if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
          extraAddr += data.bname;
        }
        if (data.buildingName !== "" && data.apartment === "Y") {
          extraAddr +=
            extraAddr !== "" ? ", " + data.buildingName : data.buildingName;
        }
        if (extraAddr !== "") {
          extraAddr = " (" + extraAddr + ")";
        }
      } else {
      }

      postalCodeInput.value = data.zonecode;
      address1Input.value = `${addr} ${extraAddr}`;
      address2Input.placeholder = "상세 주소를 입력해 주세요.";
      address2Input.focus();
    },
  }).open();
}

// 페이지 로드 시 실행
async function insertOrderSummary(userId, orderId) {
  try {
    const endpoint = `orders/pay/user/${userId}/order/${orderId}`;
    const response = await Api.get(host, endpoint);
    const {
      name,
      phone,
      email,
      grade,
      address,
      orderDetailInfoDtos,
      totalPayment,
    } = response;

    let productsTitle = "";
    let productsPayment = "";

    for (const orderDetail of orderDetailInfoDtos) {
      const { name: productName, payment, amount, orderStatus } = orderDetail;

      if (productsTitle) {
        productsTitle += "\n";
        productsPayment += "\n";
      }

      productsTitle += `${productName} / ${amount}개`;
      productsPayment += `${payment}원 x ${amount}`;
    }
    productsPaymentElem.innerText = productsPayment;
    productsTitleElem.innerText = productsTitle;
    productsTotalElem.innerText = `${totalPayment}원`;

    // 결제자 정보
    buyerNameElem.innerText = `${name}`;
    buyerPhoneElem.innerText = `${phone}`;
    buyerEmailElem.innerText = `${email}`;
    buyerGradeElem.innerText = `${grade}`;
    buyerAddressElem.innerText = `${address}`;

    // 할인 기능이 있다면 수정하기
    orderTotalElem.innerText = `${totalPayment}원`;

    receiverNameInput.focus();
  } catch (err) {
    console.log(err);
    alert(`페이지 로드 중 문제가 발생하였습니다: ${err.message}`);
  }
}

// 결제 진행
async function doCheckout(userId, orderId) {
  const receiverName = receiverNameInput.value;
  const receiverPhoneNumber = receiverPhoneNumberInput.value;
  const postalCode = postalCodeInput.value;
  const address1 = address1Input.value;
  const address2 = address2Input.value;

  if (!receiverName || !receiverPhoneNumber || !postalCode || !address2) {
    return alert("배송지 정보를 모두 입력해 주세요.");
  }

  try {
    const data = {
      userId: userId,
      orderId: orderId,
      deliveryAddress: `${postalCode};${address1};${address2}`,
      receiverName: receiverName,
      receiverPhoneNumber: formatPhoneNumber(receiverPhoneNumber),
    };
    await Api.post(host + "/orders/pay", data);

    alert("결제 및 주문이 정상적으로 완료되었습니다.\n감사합니다.");
    setCookie("userId", userId);
    setCookie("orderId", orderId);
    redirect("/order/detail");
  } catch (err) {
    console.log(err);
    alert(`결제 중 문제가 발생하였습니다: ${err.message}`);
  }
}

// function formatPhoneNumber(phoneNumber) {
//   // 전화번호에서 "-"를 제외한 숫자만 추출
//   const cleaned = phoneNumber.replace(/\D/g, "");

//   // 전화번호 길이에 따라 적절한 형식으로 변환
//   let formatted;
//   if (cleaned.length === 11) {
//     formatted = cleaned.replace(/^(\d{3})(\d{4})(\d{4})$/, "$1-$2-$3");
//   } else if (cleaned.length === 10) {
//     formatted = cleaned.replace(/^(\d{3})(\d{3})(\d{4})$/, "$1-$2-$3");
//   } else {
//     // 예외 처리: 전화번호 길이가 10자 또는 11자가 아닌 경우
//     console.error("Invalid phone number length");
//     return phoneNumber; // 변환하지 않고 그대로 반환
//   }

//   return formatted;
// }

// export { formatPhoneNumber, searchAddress };
