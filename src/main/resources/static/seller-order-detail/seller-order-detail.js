import * as Api from "../js/api.js";
import { setCookie, getCookie } from "../js/useful-functions.js";
// import * as Order from "../order/order.js";
// import { formatPhoneNumber, searchAddress } from "../order/order.js";

//결제일
const payTimeElem = document.querySelector("#payTime");

//상품
const oneProductOuter = document.querySelector("#oneProductOuter");

//배송지
const receiverNameElem = document.querySelector("#receiverName");
const receiverPhoneElem = document.querySelector("#receiverPhone");
const receiverAddressElem = document.querySelector("#receiverAddress");

//총 금액
const totalPaymentElem = document.querySelector("#totalPayment");

//모달
const modalNomal = document.querySelector(".modal-nomal");
// const modalSeller = document.querySelector(".modal-seller");
const btnOpenModal = document.querySelector(".order-modify-button");
const btnModify = document.querySelector(".modal_body .modify-btn");
const btnCloseModal = document.querySelector(".cancel-btn");

//드롭다운 값 가져오기?
const statusDropdown = document.querySelector("#productStatusDropdown");

//주문 수정
const searchAddressButton = document.querySelector("#searchAddressButton");
const postalCodeInput = document.querySelector("#postalCode");
const address1Input = document.querySelector("#address1");
const address2Input = document.querySelector("#address2");
const receiverNameInput = document.querySelector("#receiverNameInput");
const receiverPhoneNumberInput = document.querySelector("#receiverPhoneNumber");

// // // 현재 URL 가져오기
// const currentUrl = window.location.href;

// // // URL에서 쿼리 문자열 파싱
// const urlParams = new URLSearchParams(currentUrl);

// // 파라미터 값 가져오기
const userId = getCookie("userId");
const orderId = getCookie("orderId");
// const userId = urlParams.get("userId");
// const orderId = urlParams.get("orderId");
// const userId = 1;
// const orderId = 5;
// console.log("user id: " + userId);
// console.log("order id: " + orderId);

// checkLogin();
addAllElements();
addAllEvents();

// html에 요소를 추가하는 함수들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllElements() {
  insertOrderDetail(userId, orderId);
}

// addEventListener들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllEvents() {
  //클릭시 모달창 열림

  btnOpenModal.addEventListener("click", () => {
    modalNomal.style.display = "flex";
  });

  btnCloseModal.addEventListener("click", () => {
    modalNomal.style.display = "none";
  });
  searchAddressButton.addEventListener("click", searchAddress);

  btnModify.addEventListener("click", () => {
    modifyDelivery(userId, orderId);
  });
}

// 페이지 로드 시 실행되며, 주문 내역 상세 정보 로드.
async function insertOrderDetail(userId, orderId) {
  try {
    //여기서 user id값은 판매자
    const endpoint = `orders/user/${userId}/order/${orderId}?isSeller=true`;
    const response = await Api.get("http://localhost:8080", endpoint);
    const {
      id,
      orderDetailInfoDtos,
      deliveryAddress,
      receiverName,
      receiverPhone,
      totalPayment,
      orderDate,
    } = response;
    console.log(response);

    const numOfOrderDetails = orderDetailInfoDtos.length;
    await addElements(numOfOrderDetails);

    var allOneProductInner = document.querySelectorAll(".one-product-inner");

    for (let i = 0; i < numOfOrderDetails; i++) {
      const orderDetail = orderDetailInfoDtos[i];
      const { name: productName, payment, amount, orderStatus } = orderDetail;

      //   const oneProductInner = allOneProductInner[i];
      const otherProductInfo = allOneProductInner[i].querySelector(
        ".other-product-info"
      );

      const productNameElement =
        otherProductInfo.querySelector(".product-name");
      const productAmountPaymentElement = otherProductInfo.querySelector(
        ".product-amount-payment"
      );
      const productOrderStatusElement = otherProductInfo.querySelector(
        ".product-order-status"
      );

      productNameElement.innerText = `${productName}`;
      productAmountPaymentElement.innerText = `${payment}원, ${amount}개 주문`;
      productOrderStatusElement.innerText = `${orderStatus}`;
    }

    //결제 날짜
    payTimeElem.innerText = `${orderDate}`;

    // 배송지 정보
    const splittedAddress = deliveryAddress.split(";");

    receiverNameElem.innerText = `${receiverName}`;
    console.log(receiverName);
    console.log(receiverNameElem);

    receiverPhoneElem.innerText = `${receiverPhone}`;
    receiverAddressElem.innerText = `${splittedAddress[1]} ${splittedAddress[2]}`;

    // 총 결제 금액
    totalPaymentElem.innerText = `${totalPayment}원`;

    //모달창에 값 넣기
    postalCodeInput.value = splittedAddress[0];
    address1Input.value = splittedAddress[1];
    address2Input.value = splittedAddress[2];
    receiverNameInput.value = `${receiverName}`;
    const splittedPhone = receiverPhone.split("-");
    receiverPhoneNumberInput.value =
      splittedPhone[0] + splittedPhone[1] + splittedPhone[2];
  } catch (err) {
    console.log(err);
    alert(`페이지 로드 중 문제가 발생하였습니다: ${err.message}`);
  }
}

//결제한 상품 개수만큼 추가
function addElements(numberOfElementsToAdd) {
  for (let i = 0; i < numberOfElementsToAdd; i++) {
    // one-product-inner 요소 생성
    let newInnerProduct = document.createElement("div");
    newInnerProduct.classList.add("one-product-inner");

    // image-url 요소 생성
    let newImageUrl = document.createElement("div");
    newImageUrl.classList.add("image-url");

    // other-product-info 요소 생성
    let newOtherProductInfo = document.createElement("div");
    newOtherProductInfo.classList.add("other-product-info");

    // product-name, product-amount-payment, product-order-status 요소 생성
    let newProductName = document.createElement("p");
    newProductName.classList.add("product-name");

    let newProductAmountPayment = document.createElement("p");
    newProductAmountPayment.classList.add("product-amount-payment");

    let newProductOrderStatus = document.createElement("p");
    newProductOrderStatus.classList.add("product-order-status");

    // other-product-info에 각각의 요소 추가
    newOtherProductInfo.appendChild(newProductName);
    newOtherProductInfo.appendChild(newProductAmountPayment);
    newOtherProductInfo.appendChild(newProductOrderStatus);

    // one-product-inner에 image-url와 other-product-info 추가
    newInnerProduct.appendChild(newImageUrl);
    newInnerProduct.appendChild(newOtherProductInfo);

    // one-product-outer에 one-product-inner 추가
    oneProductOuter.appendChild(newInnerProduct);
  }
}

// 수정 버튼 클릭시 실행되며, 주문 배송지 수정
async function modifyDelivery(userId, orderId, orderDetailId) {
  const receiverName = receiverNameInput.value;
  const receiverPhoneNumber = receiverPhoneNumberInput.value;
  const postalCode = postalCodeInput.value;
  const address1 = address1Input.value;
  const address2 = address2Input.value;

  try {
    const data = {
      userId: userId,
      orderId: orderId,
      orderDetailId: orderDetailId,
      orderStatus: "DELIVERY_OVER",
    };
    await Api.patch("http://localhost:8080/orders/modify-status", "", data);
    alert("주문 수정이 완료되었습니다.");
  } catch (err) {
    console.log(err);
    alert(`페이지 로드 중 문제가 발생하였습니다: ${err.message}`);
  }
}

// export 함수 사용해보기 //////////////////////////////////////
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

function formatPhoneNumber(phoneNumber) {
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
}
