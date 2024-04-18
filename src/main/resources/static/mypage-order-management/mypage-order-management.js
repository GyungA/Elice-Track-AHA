import * as Api from "../js/api.js";
import {
  setCookie,
  getCookie,
  redirect,
  activePageButtons,
  createPageNumber,
} from "../js/useful-functions.js";

// 요소(element), input 혹은 상수
const section = document.querySelector(".one-product-container");
let pageWrapper = document.querySelector(".page-wrapper");

setCookie("userId", 1);
const userId = getCookie("userId");

console.log(userId);
addAllElements();
addAllEvents();

// html에 요소를 추가하는 함수들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllElements() {
  redirectOrders(userId);
}

// addEventListener들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllEvents() {}

async function redirectOrders(userId) {
  try {
    const endpoint = `orders/user/${userId}`;
    const response = await Api.get("http://localhost:8080", endpoint);

    const responseData = response.content;
    const productNumber = responseData.length;

    let endPageNumber = Math.floor(productNumber / 20);
    if (productNumber % 20 != 0) {
      endPageNumber += 1;
    }
    await addOrder(productNumber);
    //페이지네이션
    await createPageNumber(endPageNumber, pageWrapper);
    activePageButtons(endPageNumber);
    const payTimeTag = document.querySelectorAll(".pay-time");
    const orderStatusTag = document.querySelectorAll(".proudct-status");
    const productNameTag = document.querySelectorAll(".title-tag");
    const stockTotalCostTag = document.querySelectorAll(".remain-stock");
    const productImageTag = document.querySelectorAll(".product-image");

    //버튼
    const detailButton = document.querySelectorAll(".detail-button");
    const purchaseCancelButton = document.querySelectorAll(".cancel-button");

    for (let i = 0; i < productNumber; i++) {
      const {
        orderDate,
        orderId,
        orderStatus,
        productImage,
        productName,
        totalPayment,
        totalProductCount,
        userId,
      } = responseData[i];

      //   productImageTag[i].src = `${productImage}`; // 이미지 src 속성에 값 설정
      if (orderStatus === "CANCELLATION_COMPLETE") {
        orderStatusTag[i].innerText = `  ${orderStatus}`;
      }
      payTimeTag[i].innerText = `${orderDate} 주문`;
      productNameTag[i].innerText = `${productName}`;
      stockTotalCostTag[
        i
      ].innerText = `${totalProductCount}개 주문, 총 ${totalPayment}원`;

      detailButton[i].addEventListener("click", () => {
        setCookie("userId", userId);
        setCookie("orderId", orderId);
        redirect("/mypage-order-detail/mypage-order-detail.html");
      });
      purchaseCancelButton[i].addEventListener("click", () => {
        redirectOrderCancel(userId, orderId);
      });
    }
  } catch (err) {
    console.log(err);
    alert(`페이지 로드 중 문제가 발생하였습니다: ${err.message}`);
  }
}

async function redirectOrderCancel(userId, orderId) {
  try {
    const data = {
      userId: userId,
      orderId: orderId,
    };
    await Api.patch("http://localhost:8080/orders/cancel", "", data);
    alert("주문 취소가 완료되었습니다.");

    setCookie("userId", userId);
    redirect("/mypage-order-management/mypage-order-management.html");
  } catch (err) {
    console.log(err);
    alert(`페이지 로드 중 문제가 발생하였습니다: ${err.message}`);
  }
}

function addOrder(productNumber) {
  for (let i = 0; i < productNumber; i++) {
    // 새로운 <div class="one-product"> 요소 생성
    const productDiv = document.createElement("div");
    productDiv.classList.add("one-product");

    // 상품 이미지 요소 생성
    const imageDiv = document.createElement("div");
    imageDiv.classList.add("is-child", "box", "product-image");
    const figure = document.createElement("figure");
    figure.classList.add("image", "is-square");
    const img = document.createElement("img");
    img.classList.add("product-image");
    img.setAttribute("id", "productImageTag");
    img.setAttribute("src", ""); // 이미지 소스 설정
    figure.appendChild(img);
    imageDiv.appendChild(figure);

    // 상품 내용 요소 생성
    const contentWrapperDiv = document.createElement("div");
    contentWrapperDiv.classList.add(
      "tile",
      "is-parent",
      "is-vertical",
      "product-content-wrapper"
    );
    const detailDiv = document.createElement("div");
    detailDiv.classList.add("tile", "is-child", "box", "product-detail");
    const upperDiv = document.createElement("div");
    upperDiv.classList.add("upper");
    const payTimeP = document.createElement("p");
    payTimeP.classList.add("pay-time");
    const statusP = document.createElement("p");
    const statusTagP = document.createElement("p");
    statusTagP.classList.add("proudct-status");
    statusTagP.setAttribute("id", "statusTag");
    upperDiv.appendChild(payTimeP);
    upperDiv.appendChild(statusP);
    upperDiv.appendChild(statusTagP);

    // upper 아래부분
    const contentDiv = document.createElement("div");
    contentDiv.classList.add("content");
    const productNameDiv = document.createElement("div");
    productNameDiv.classList.add("product-name-status");
    const titleP = document.createElement("p");
    titleP.classList.add(
      "subtitle",
      "is-4",
      "is-family-monospace",
      "title-tag"
    );
    // titleP.setAttribute("id", "titleTag");
    const remainStockP = document.createElement("p");
    remainStockP.classList.add("remain-stock");
    remainStockP.setAttribute("id", "remainStock");
    productNameDiv.appendChild(titleP);
    productNameDiv.appendChild(remainStockP);
    const buttonsDiv = document.createElement("div");
    buttonsDiv.classList.add("buttons-container");
    const detailButton = document.createElement("button");
    detailButton.classList.add("button", "detail-button");
    detailButton.setAttribute("id", "detailButton");
    detailButton.textContent = "상세보기";
    const cancelButton = document.createElement("button");
    cancelButton.classList.add("button", "cancel-button");
    cancelButton.setAttribute("id", "purchaseCancelButton");
    cancelButton.textContent = "주문 취소";
    buttonsDiv.appendChild(detailButton);
    buttonsDiv.appendChild(cancelButton);
    contentDiv.appendChild(productNameDiv);
    contentDiv.appendChild(buttonsDiv);
    detailDiv.appendChild(upperDiv);
    detailDiv.appendChild(contentDiv);
    contentWrapperDiv.appendChild(detailDiv);

    // <div class="one-product"> 요소에 자식 요소들 추가
    productDiv.appendChild(imageDiv);
    productDiv.appendChild(contentWrapperDiv);

    // <section class="section"> 요소에 자식 요소 추가
    section.appendChild(productDiv);
  }
}
