import * as Api from "../api.js";
/*import {
  checkLogin,
  addCommas,
  convertToNumber,
  navigate,
  randomPick,
  createNavbar,
} from "../useful-functions.js";*/
//import { deleteFromDb, getFromDb, putToDb } from "../indexed-db.js";

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

// checkLogin();
addAllElements();
addAllEvents();

// html에 요소를 추가하는 함수들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllElements() {
  // createNavbar();
  insertOrderSummary(1, 4); //나중에 userId, orderId 넣기
  // insertUserData();
}

// addEventListener들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllEvents() {
  // subtitleCart.addEventListener("click", navigate("/cart"));
  searchAddressButton.addEventListener("click", searchAddress);
  // requestSelectBox.addEventListener("change", handleRequestChange); //요청사항
  checkoutButton.addEventListener("click", doCheckout); //나중에 userId, orderId 넣기
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

// 페이지 로드 시 실행되며, 결제정보 카드에 값을 삽입함.
async function insertOrderSummary(userId, orderId) {
  try {
    const endpoint = `orders/pay/user/${userId}/order/${orderId}`;
    const response = await Api.get("http://localhost:8080", endpoint);
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

// async function insertUserData() {
//   const userData = await Api.get("/user");
//   const { fullName, phoneNumber, address } = userData;

//   // 만약 db에 데이터 값이 있었다면, 배송지정보에 삽입
//   if (fullName) {
//     receiverNameInput.value = fullName;
//   }

//   if (phoneNumber) {
//     receiverPhoneNumberInput.value = phoneNumber;
//   }

//   if (address) {
//     postalCode.value = address.postalCode;
//     address1Input.value = address.address1;
//     address2Input.value = address.address2;
//   }
// }

// "직접 입력" 선택 시 input칸 보이게 함
// default값(배송 시 요청사항을 선택해 주세여) 이외를 선택 시 글자가 진해지도록 함
/*function handleRequestChange(e) {
  const type = e.target.value;

  if (type === "6") {
    customRequestContainer.style.display = "flex";
    customRequestInput.focus();
  } else {
    customRequestContainer.style.display = "none";
  }

  if (type === "0") {
    requestSelectBox.style.color = "rgba(0, 0, 0, 0.3)";
  } else {
    requestSelectBox.style.color = "rgba(0, 0, 0, 1)";
  }
}*/

// 결제 진행
async function doCheckout() {
  const receiverName = receiverNameInput.value;
  const receiverPhoneNumber = receiverPhoneNumberInput.value;
  const postalCode = postalCodeInput.value;
  const address1 = address1Input.value;
  const address2 = address2Input.value;
  // const requestType = requestSelectBox.value;
  // const customRequest = customRequestInput.value;
  // const summaryTitle = productsTitleElem.innerText;
  // const totalPrice = convertToNumber(orderTotalElem.innerText);
  // const { selectedIds } = await getFromDb("order", "summary");

  if (!receiverName || !receiverPhoneNumber || !postalCode || !address2) {
    return alert("배송지 정보를 모두 입력해 주세요.");
  }

  // 요청사항의 종류에 따라 request 문구가 달라짐
  // let request;
  // if (requestType === "0") {
  //   request = "요청사항 없음.";
  // } else if (requestType === "6") {
  //   if (!customRequest) {
  //     return alert("요청사항을 작성해 주세요.");
  //   }
  //   request = customRequest;
  // } else {
  //   request = requestOption[requestType];
  // }

  // const address = {
  //   postalCode,
  //   address1,
  //   address2,
  //   receiverName,
  //   receiverPhoneNumber,
  // };

  try {
    // // 전체 주문을 등록함
    // const orderData = await Api.post("/orders/pay", {
    //   summaryTitle,
    //   totalPrice,
    //   address,
    //   request,
    // });

    // const orderId = orderData._id;

    // // 제품별로 주문아이템을 등록함
    // for (const productId of selectedIds) {
    //   const { quantity, price } = await getFromDb("cart", productId);
    //   const totalPrice = quantity * price;

    //   await Api.post("/api/orderitem", {
    //     orderId,
    //     productId,
    //     quantity,
    //     totalPrice,
    //   });

    //   // indexedDB에서 해당 제품 관련 데이터를 제거함
    //   await deleteFromDb("cart", productId);
    //   await putToDb("order", "summary", (data) => {
    //     data.ids = data.ids.filter((id) => id !== productId);
    //     data.selectedIds = data.selectedIds.filter((id) => id !== productId);
    //     data.productsCount -= 1;
    //     data.productsTotal -= totalPrice;
    //   });
    // }

    // 입력된 배송지정보를 유저db에 등록함
    // const data = {
    //   phoneNumber: receiverPhoneNumber,
    //   address: {
    //     postalCode,
    //     address1,
    //     address2,
    //   },
    // };
    const data = {
      userId: 1,
      orderId: 4,
      deliveryAddress: `${postalCode}, ${address1} ${address2}`,
      receiverName: receiverName,
      receiverPhoneNumber: formatPhoneNumber(receiverPhoneNumber),
    };
    await Api.post("http://localhost:8080/orders/pay", data);

    alert("결제 및 주문이 정상적으로 완료되었습니다.\n감사합니다.");
    // window.location.href = "/order/complete"; 나중에 redirect url 정해지면 추가
  } catch (err) {
    console.log(err);
    alert(`결제 중 문제가 발생하였습니다: ${err.message}`);
  }
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
