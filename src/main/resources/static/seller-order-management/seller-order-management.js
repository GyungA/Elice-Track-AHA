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

setCookie("userId", 2);
const userId = getCookie("userId");
let page = getCookie("page");

addAllElements();
addAllEvents();

// html에 요소를 추가하는 함수들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllElements() {
  redirectOrders(userId, page);
}

// addEventListener들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllEvents() {}

async function redirectOrders(userId, page) {
  try {
    //buyer id로 필터링 가능?
    const endpoint = `admin/orders/user/${userId}?page=${page}`;
    const response = await Api.get("http://localhost:8080", endpoint);

    console.log(response);
    const responsePageable = response.pageable;
    const {
      offset, //시작하는 페이지
      pageNumber, //요청한 페이지 번호
      pageSize, //한 페이지의 크기
      paged, //페이지네이션 사용 여부
    } = responsePageable;
    const responseData = response.content;
    const productNumber = responseData.length; //주문 개수

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

        redirect("/seller-order-detail/seller-order-detail.html");
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

async function redirectOrderCancel(userId, orderId, orderDetailId) {
  try {
    const data = {
      userId: userId,
      orderId: orderId,
      orderDetailId: orderDetailId,
    };
    await Api.patch("http://localhost:8080/admin/orders/cancel", "", data);
    alert("주문 취소가 완료되었습니다.");
    redirect("/mypage-order-management/mypage-order-management.html");
    setCookie("userId", userId);
    setCookie("orderId", orderId);
    setCookie("orderDetailId", orderDetailId);
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
    // payTimeP.textContent = "2024.08.28 주문"; // 주문 시간 설정
    const statusP = document.createElement("p");
    // statusP.textContent = "|";
    const statusTagP = document.createElement("p");
    statusTagP.classList.add("proudct-status");
    statusTagP.setAttribute("id", "statusTag");
    // statusTagP.textContent = "주문 완료"; // 주문 상태 설정
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
    // titleP.textContent = "삼각대및셀카봉"; // 상품 이름 설정
    const remainStockP = document.createElement("p");
    remainStockP.classList.add("remain-stock");
    remainStockP.setAttribute("id", "remainStock");
    // remainStockP.textContent = "2개 주문, 총 20,000원"; // 재고 설정
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
