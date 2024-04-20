import * as Api from "../js/api.js";
import {
  setCookie,
  getCookie,
  redirect,
  activePageButtons,
  createPageNumber,
} from "../js/useful-functions.js";

// const host = "http://localhost:8080";
const host = "http://34.22.75.198:8080";
setCookie("userId", 1);
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
// const icon = statusWrapper.querySelector(".material-symbols-outlined");

//가격 수정 모달
const modalNomal = document.querySelector(".modal-modify"); //모달창
// const btnOpenModal = document.querySelector(".product-modify-btn"); //모달 여는 버튼
const btnModify = document.querySelector(".modal_body .modify-btn"); //모달창 안의 "수정하기" 버튼
const btnCloseModal = document.querySelector(".cancel-btn");
const newPriceInput = document.querySelector("#newPrice"); //수정된 가격

//상품 추가 모달
const modalAdd = document.querySelector(".modal-add"); //모달창
const btnOpenAddModal = document.querySelector("#addToProductButton"); //모달 여는 버튼
const btnCreate = document.querySelector(".modal_body .create-btn"); //모달창 안의 "추가" 버튼
const btnCloseAddModal = document.querySelector(".cancel-add-btn");
const priceInput = document.querySelector("#price");
const productNameInput = document.querySelector("#productName");
const stockInput = document.querySelector("#stock");

//드롭다운
const statusDropdown = document.querySelector("#productStatusDropdown");
const categoryDropdown = document.querySelector("#productCategoryDropdown");

//필터링
const filteringInput = document.querySelector(".filtering-input"); //input태그에 작성한 값

//page
const pageWrapper = document.querySelector(".page-wrapper");

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

  btnCreate.addEventListener("click", () => {
    addProductDB(userId);
  });
}

async function loadProductInfo(userId) {
  try {
    const endpoint = `product/list/${userId}`;
    const response = await Api.get(host, endpoint);

    // console.log(response);
    const responseData = response.content;
    const productNumber = responseData.length;

    let endPageNumber = Math.floor(productNumber / 20);
    if (productNumber % 20 != 0) {
      endPageNumber += 1;
    }
    await addProduct(productNumber);
    //페이지네이션
    await createPageNumber(endPageNumber, pageWrapper);
    activePageButtons(endPageNumber, "/seller/product/management");
    // const payTimeTag = document.querySelectorAll(".pay-time");
    const orderStatusTag = document.querySelectorAll(".proudct-status");
    let orderStatusColor = document.querySelectorAll(".order-status-icon");

    const productNameTag = document.querySelectorAll(".title-tag");
    const stockTotalCostTag = document.querySelectorAll(".remain-stock");
    const priceTag = document.querySelectorAll(".price-tag");
    // const productImageTag = document.querySelectorAll(".product-image");

    //버튼
    const btnOpenModal = document.querySelectorAll(".product-modify-btn"); //모달 여는 버튼
    const btnDelete = document.querySelectorAll(".delete-product");
    // const detailButton = document.querySelectorAll(".detail-button");
    // const purchaseCancelButton = document.querySelectorAll(".cancel-button");

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
      if (status) {
        orderStatusTag[i].innerText = "판매중";
        orderStatusColor[i].style.color = "green";
      } else {
        orderStatusTag[i].innerText = "품절";
        orderStatusColor[i].style.color = "red";
      }
      // payTimeTag[i].innerText = `${orderDate} 주문`;
      productNameTag[i].innerText = `${name}`;
      stockTotalCostTag[i].innerText = `${current_stock}개`;

      //상품 수정하기 버튼
      btnOpenModal[i].addEventListener("click", () => {
        modalNomal.style.display = "flex";
        setCookie("userId", userId);
        setCookie("orderId", orderId);
        redirect("/seller/order/detail");
      });

      //삭제 버튼 클릭시
      btnDelete[i].addEventListener("click", async () => {
        alert(`상품 [ ${name} ]이 삭제됩니다.`);
        await deleteProduct(id);
        redirect(`/product/list/${userId}`);
        // redirectOrderCancel(userId, orderId);
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

const producItemContainer = document.querySelector("#producItemContainer");

function addProduct(productNumber) {
  for (let i = 0; i < productNumber; i++) {
    let newDiv = document.createElement("div");
    newDiv.className = "one-product";

    // div 요소 안에 HTML 코드를 추가합니다.
    newDiv.innerHTML = `
        <div class="is-child box product-image">
            <figure class="image is-sqaure">
                <!-- 상품 이미지 -->
                <img id="productImageTag" src="" />
            </figure>
        </div>

        <div class="tile is-parent is-vertical product-content-wrapper">
            <div class="tile is-child box product-detail">
                <div class="tabs">
                    <ul>
                        <!-- 카테고리 -->
                        <li id="manufacturerTag"></li>
                    </ul>
                </div>
                <div class="content">
                    <div class="product-name-status">
                        <!-- 상품 이름 -->
                        <p class="subtitle is-4 is-family-monospace title-tag" id="titleTag"></p>
                        <div class="status-wrapper">
                            <!-- 상품 상태를 나타내는 아이콘 -->
                            <span class="material-symbols-outlined order-status-icon">fiber_manual_record</span>
                            <!-- 상품 상태 -->
                            <p class="proudct-status" id="statusTag"></p>
                        </div>
                    </div>

                    <div class="price-stock-wrapper">
                        <!-- 상품 가격 -->
                        <h2 id="priceTag"></h2>
                        <div class="stock-wrapper">
                            <!-- 남은 수량 -->
                            <p class="remain-stock-word">남은 수량:</p>
                            <p class="remain-stock" id="remainStock"></p>
                        </div>
                    </div>
                    <div class="buttons-container">
                        <button class="button delete-product">
                            <span class="material-symbols-outlined"> delete </span>
                        </button>
                        <button class="button product-modify-btn addToProductButton" id="purchaseButton">수정하기</button>
                    </div>
                </div>
            </div>
        </div>
    `;

    // 새로운 div 요소를 body에 추가합니다.
    producItemContainer.appendChild(newDiv);
  }
}

async function deleteProduct(productId) {
  const endpoint = `product/delete/${productId}`;
  const response = await Api.get(host, endpoint);
}

async function addProductDB(userId) {
  const price = priceInput.value;
  const name = productNameInput.value;
  const stock = stockInput.value;
  // let category = categoryDropdown.options[categoryDropdown.selectedIndex].text;
  let productStatus = statusDropdown.options[statusDropdown.selectedIndex].text;

  //category값에 따른 categoryId 처리하기

  let status = "";
  if (productStatus === "판매중") {
    status = true;
  } else {
    status = false;
  }

  try {
    const data = {
      categoryId: 3, //이거 수저어어어엉
      sellerId: userId,
      price: price,
      name: name,
      status: status,
      stock: stock,
    };
    await Api.post(host + "/product/new/pro", data);

    alert("상품 추가가 정상적으로 완료되었습니다.");
    redirect(`/product/list/${userId}`);
  } catch (err) {
    console.log(err);
    alert(`상품 추가 중 문제가 발생하였습니다: ${err.message}`);
  }
}
