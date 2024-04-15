// import { getImageUrl } from "../../aws-s3.js";
// import * as Api from "../../api.js";
// import {
//   randomId,
//   getUrlParams,
//   addCommas,
//   navigate,
//   checkUrlParams,
//   createNavbar,
// } from "../../useful-functions.js";

// 요소(element), input 혹은 상수
const productItemContainer = document.querySelector("#producItemContainer");
const addToCartButton = document.querySelector("#addToCartButton"); //상품 삭제 버튼
const newPriceInput = document.querySelector("#newPrice");

const productImageTag = document.querySelector("#productImageTag");
const manufacturerTag = document.querySelector("#manufacturerTag");
const titleTag = document.querySelector("#titleTag");
const statusTag = document.querySelector("#statusTag");
const priceTag = document.querySelector("#priceTag");
const remainStock = document.querySelector("#remainStock");

//상품 상태를 나타내는 아이콘
const statusWrapper = document.querySelector(".status-wrapper");
const icon = statusWrapper.querySelector(".material-symbols-outlined");

//모달
const modalNomal = document.querySelector(".modal"); //모달창
const btnOpenModal = document.querySelector("#purchaseButton"); //모달 여는 버튼
const btnModify = document.querySelector(".modal_body .modify-btn"); //모달창 안의 "수정하기" 버튼
const btnCloseModal = document.querySelector(".cancel-btn");

// checkUrlParams("category");
// addAllElements();
addAllEvents();

// html에 요소를 추가하는 함수들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllElements() {
  createNavbar();
  addProductItemsToContainer();
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
}

async function addProductItemsToContainer() {
  const { category } = getUrlParams();
  console.log(category);
  const products = await Api.get(`/products/lists?categoryTitle=${category}`);

  for (const product of products) {
    // 객체 destructuring
    const { id, title, shortDescription, imageKey, isRecommended, price } =
      product;
    const imageUrl = await getImageUrl(imageKey);
    const random = randomId();

    productItemContainer.insertAdjacentHTML(
      "beforeend",
      `
      <div class="message media product-item" id="a${random}">
        <div class="media-left">
          <figure class="image">
            <img
              src="${imageUrl}"
              alt="제품 이미지"
            />
          </figure>
        </div>
        <div class="media-content">
          <div class="content">
            <p class="title">
              ${title}
              ${
                isRecommended
                  ? '<span class="tag is-success is-rounded">추천</span>'
                  : ""
              }
            </p>
            <p class="description">${shortDescription}</p>
            <p class="price">${addCommas(price)}원</p>
          </div>
        </div>
      </div>
      `
    );

    const productItem = document.querySelector(`#a${random}`);
    productItem.addEventListener("click", navigate(`/product/detail?id=${id}`));
  }
}
