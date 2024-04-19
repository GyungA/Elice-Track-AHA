import * as Api from "../js/api.js";
import {
  setCookie,
  getCookie,
  redirect,
  activePageButtons,
  createPageNumber,
} from "../js/useful-functions.js";

// 요소(element), input 혹은 상수
// const productItemContainer = document.querySelector("#producItemContainer");
// const addToCartButton = document.querySelector("#addToCartButton"); //상품 삭제 버튼
// const newPriceInput = document.querySelector("#newPrice");

// const productImageTag = document.querySelector("#productImageTag");
// const manufacturerTag = document.querySelector("#manufacturerTag");
// const titleTag = document.querySelector("#titleTag");
// const statusTag = document.querySelector("#statusTag");
// const priceTag = document.querySelector("#priceTag");
// const remainStock = document.querySelector("#remainStock");

const filteringButton = document.querySelector("#filtering");

//상품 상태를 나타내는 아이콘
const statusWrapper = document.querySelector(".status-wrapper");
const icon = statusWrapper.querySelector(".material-symbols-outlined");

//가격 수정 모달
const modalNomal = document.querySelector(".modal-modify"); //모달창
const btnOpenModal = document.querySelector(".product-modify-btn"); //모달 여는 버튼
const btnModify = document.querySelector(".modal_body .modify-btn"); //모달창 안의 "수정하기" 버튼
const btnCloseModal = document.querySelector(".cancel-btn");

//상품 추가 모달
const modalAdd = document.querySelector(".modal-add"); //모달창
const btnOpenAddModal = document.querySelector("#addToProductButton"); //모달 여는 버튼
const btnCreate = document.querySelector(".modal_body .create-btn"); //모달창 안의 "추가" 버튼
const btnCloseAddModal = document.querySelector(".cancel-add-btn");

//드롭다운
const statusDropdown = document.querySelector("#productStatusDropdown");
const categoryDropdown = document.querySelector("#productCategoryDropdown");

//필터링
const filteringInput = document.querySelector(".filtering-input"); //input태그에 작성한 값

const userId = getCookie("userId");
// checkUrlParams("category");
addAllElements();
addAllEvents();

// html에 요소를 추가하는 함수들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllElements() {
  // createNavbar();
  // addProductItemsToContainer();
  loadProductInfo(userId);
}

// addEventListener들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllEvents() {
  filteringButton.addEventListener("click", createFilter);

  //클릭시 상품 수정 모달창 열림
  btnOpenModal.addEventListener("click", () => {
    modalNomal.style.display = "flex";
  });

  btnCloseModal.addEventListener("click", () => {
    modalNomal.style.display = "none";
  });

  //클릭시 상품 수정 모달창 열림
  btnOpenAddModal.addEventListener("click", () => {
    modalAdd.style.display = "flex";
  });

  btnCloseAddModal.addEventListener("click", () => {
    modalAdd.style.display = "none";
  });
}

async function loadProductInfo(userId) {
  try {
    const endpoint = `product/list/${userId}`;
    const response = await Api.get(host, endpoint);

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
    // const payTimeTag = document.querySelectorAll(".pay-time");
    const orderStatusTag = document.querySelectorAll(".proudct-status");
    const orderStatusColor = document.querySelectorAll(
      ".proudct-status .material-symbols-outlined"
    );

    const productNameTag = document.querySelectorAll(".title-tag");
    const stockTotalCostTag = document.querySelectorAll(".remain-stock");
    const priceTag = document.querySelectorAll(".price-tag");
    // const productImageTag = document.querySelectorAll(".product-image");

    //버튼
    const detailButton = document.querySelectorAll(".detail-button");
    const purchaseCancelButton = document.querySelectorAll(".cancel-button");

    for (let i = 0; i < productNumber; i++) {
      const {
        id,
        category,
        price,
        name,
        description,
        status,
        current_stock,
        seller,
        image,
        createAt,
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
        redirect("/order/detail");
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

function createFilter() {
  const filterWrapper = document.createElement("div");
  filterWrapper.classList.add("filtering-wrapper");

  // 버튼을 대체할 input 요소를 생성합니다.
  const input = document.createElement("input");
  input.classList.add("filtering-input");
  input.type = "text";

  // 새로운 버튼을 생성
  const newButton = document.createElement("button");
  newButton.textContent = "검색";
  newButton.classList.add("filtering-btn");

  // 버튼 삭제
  filteringButton.remove();

  // container 요소에 input 요소를 추가합니다.
  const container = document.querySelector(".product-add-filterSearch");
  container.appendChild(filterWrapper);

  filterWrapper.appendChild(input);
  filterWrapper.appendChild(newButton);
}

// async function addProductItemsToContainer() {
//   const { category } = getUrlParams();
//   console.log(category);
//   const products = await Api.get(`/products/lists?categoryTitle=${category}`);

//   for (const product of products) {
//     // 객체 destructuring
//     const { id, title, shortDescription, imageKey, isRecommended, price } =
//       product;
//     const imageUrl = await getImageUrl(imageKey);
//     const random = randomId();

//     productItemContainer.insertAdjacentHTML(
//       "beforeend",
//       `
//       <div class="message media product-item" id="a${random}">
//         <div class="media-left">
//           <figure class="image">
//             <img
//               src="${imageUrl}"
//               alt="제품 이미지"
//             />
//           </figure>
//         </div>
//         <div class="media-content">
//           <div class="content">
//             <p class="title">
//               ${title}
//               ${
//                 isRecommended
//                   ? '<span class="tag is-success is-rounded">추천</span>'
//                   : ""
//               }
//             </p>
//             <p class="description">${shortDescription}</p>
//             <p class="price">${addCommas(price)}원</p>
//           </div>
//         </div>
//       </div>
//       `
//     );

//     const productItem = document.querySelector(`#a${random}`);
//     productItem.addEventListener("click", navigate(`/product/detail?id=${id}`));
//   }
// }
