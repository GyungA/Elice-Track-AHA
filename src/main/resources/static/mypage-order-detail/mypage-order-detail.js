import * as Api from "../js/api.js";

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

// checkLogin();
addAllElements();
addAllEvents();

// html에 요소를 추가하는 함수들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllElements() {
  //   addElements(4);
  insertOrderDetail(1, 1);
}

// addEventListener들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllEvents() {}

// 페이지 로드 시 실행되며, 주문 내역 상세 정보 로드.
async function insertOrderDetail(userId, orderId) {
  try {
    const endpoint = `orders/user/${userId}/order/${orderId}`;
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

    // let productsTitle = "";
    // let productsPayment = "";

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
    // for (const orderDetail of orderDetailInfoDtos) {
    //   const { name: productName, payment, amount, orderStatus } = orderDetail;

    // //   if (productsTitle) {
    // //     productsTitle += "\n";
    // //     productsPayment += "\n";
    // //   }

    // //   productsTitle += `${productName} / ${amount}개`;
    // //   productsPayment += `${payment}원 x ${amount}`;
    // }

    // productsPaymentElem.innerText = productsPayment;
    // productsTitleElem.innerText = productsTitle;
    // productsTotalElem.innerText = `${totalPayment}원`;

    //결제 날짜
    payTimeElem.innerText = `${orderDate}`;

    // 배송지 정보
    receiverNameElem.innerText = `${receiverName}`;
    receiverPhoneElem.innerText = `${receiverPhone}`;
    receiverAddressElem.innerText = `${deliveryAddress}`;

    // 총 결제 금액
    totalPaymentElem.innerText = `${totalPayment}원`;

    // receiverNameInput.focus();
  } catch (err) {
    console.log(err);
    alert(`페이지 로드 중 문제가 발생하였습니다: ${err.message}`);
  }
}

//결제한 상품 개수만큼 추가
function addElements(numberOfElementsToAdd) {
  for (var i = 0; i < numberOfElementsToAdd; i++) {
    // one-product-inner 요소 생성
    var newInnerProduct = document.createElement("div");
    newInnerProduct.classList.add("one-product-inner");

    // image-url 요소 생성
    var newImageUrl = document.createElement("div");
    newImageUrl.classList.add("image-url");

    // other-product-info 요소 생성
    var newOtherProductInfo = document.createElement("div");
    newOtherProductInfo.classList.add("other-product-info");

    // product-name, product-amount-payment, product-order-status 요소 생성
    var newProductName = document.createElement("p");
    newProductName.classList.add("product-name");

    var newProductAmountPayment = document.createElement("p");
    newProductAmountPayment.classList.add("product-amount-payment");

    var newProductOrderStatus = document.createElement("p");
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
