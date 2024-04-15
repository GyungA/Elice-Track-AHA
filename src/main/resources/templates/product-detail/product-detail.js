// import { getImageUrl } from "../../aws-s3.js";
// import * as Api from "../../api.js";
// import {
//   getUrlParams,
//   addCommas,
//   checkUrlParams,
//   createNavbar,
// } from "../../useful-functions.js";
// import { addToDb, putToDb } from "../../indexed-db.js";

// 요소(element), input 혹은 상수
const productImageTag = document.querySelector("#productImageTag");
const manufacturerTag = document.querySelector("#manufacturerTag");
const titleTag = document.querySelector("#titleTag");
const priceTag = document.querySelector("#priceTag");
const sellerName = document.querySelector("#sellerName");
const detailDescriptionTag = document.querySelector("#detailDescriptionTag");
const remainStock = document.querySelector("#remainStock");
const addToCartButton = document.querySelector("#addToCartButton");
const purchaseButton = document.querySelector("#purchaseButton");

//상품 상태를 나타내는 아이콘
const statusWrapper = document.querySelector(".status-wrapper");
const icon = statusWrapper.querySelector(".material-symbols-outlined");

// 상품 수량 증감 버튼
let plus = document.querySelector(".plus");
let minus = document.querySelector(".minus");
let result = document.querySelector("#result");
let totalcost = document.querySelector(".total-cost");
let i = 1;

// checkUrlParams("id");
addAllElements();
addAllEvents();

// html에 요소를 추가하는 함수들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllElements() {
  // createNavbar();
  //insertProductData();
}

// addEventListener들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllEvents() {
  //TODO: 장바구니 추가하기 버튼 눌렀을 때 api 호출하는 함수 추가하기
  //TODO: "바로 구매하기" 버튼 눌렀을 때 api 호출하는 함수 추가하기
}

async function insertProductData() {
  const { id } = getUrlParams();
  const product = await Api.get(`/products/${id}`);

  // 객체 destructuring
  const {
    title,
    detailDescription,
    menufacturer,
    imageKey,
    isRecommended,
    price,
  } = product;

  //상품 상태에 따른 아이콘 색 변경
  //ex. 판매중: 초록, 일시품절: 흰색, 판매중단: 검정색
  icon.style.color = "black"; //검정색으로 색 변경

  // 상품 수량 증감 동작
  totalcost.textContent = price + "원";
  plus.addEventListener("click", () => {
    i++;
    result.textContent = i;
    let totalcostNum = i * price;
    totalcost.textContent = totalcostNum.toLocaleString() + "원";
  });

  minus.addEventListener("click", () => {
    if (i > 0) {
      i--;
      result.textContent = i;
      let totalcostNum = i * price;
      totalcost.textContent = totalcostNum.toLocaleString() + "원";
    } else {
      totalcost.textContent = 0 + "원";
    }
  });

  const imageUrl = await getImageUrl(imageKey);

  productImageTag.src = imageUrl;
  titleTag.innerText = title;
  detailDescriptionTag.innerText = detailDescription;
  manufacturerTag.innerText = menufacturer;
  priceTag.innerText = `${addCommas(price)}원`;

  if (isRecommended) {
    titleTag.insertAdjacentHTML(
      "beforeend",
      '<span class="tag is-success is-rounded">추천</span>'
    );
  }

  addToCartButton.addEventListener("click", async () => {
    try {
      await insertDb(product);

      alert("장바구니에 추가되었습니다.");
    } catch (err) {
      // Key already exists 에러면 아래와 같이 alert함
      if (err.message.includes("Key")) {
        alert("이미 장바구니에 추가되어 있습니다.");
      }

      console.log(err);
    }
  });

  purchaseButton.addEventListener("click", async () => {
    try {
      await insertDb(product);

      window.location.href = "/order";
    } catch (err) {
      console.log(err);

      //insertDb가 에러가 되는 경우는 이미 제품이 장바구니에 있던 경우임
      //따라서 다시 추가 안 하고 바로 order 페이지로 이동함
      window.location.href = "/order";
    }
  });
}

async function insertDb(product) {
  // 객체 destructuring
  const { id: id, price } = product;

  // 장바구니 추가 시, indexedDB에 제품 데이터 및
  // 주문수량 (기본값 1)을 저장함.
  await addToDb("cart", { ...product, quantity: 1 }, id);

  // 장바구니 요약(=전체 총합)을 업데이트함.
  await putToDb("order", "summary", (data) => {
    // 기존 데이터를 가져옴
    const count = data.productsCount;
    const total = data.productsTotal;
    const ids = data.ids;
    const selectedIds = data.selectedIds;

    // 기존 데이터가 있다면 1을 추가하고, 없다면 초기값 1을 줌
    data.productsCount = count ? count + 1 : 1;

    // 기존 데이터가 있다면 가격만큼 추가하고, 없다면 초기값으로 해당 가격을 줌
    data.productsTotal = total ? total + price : price;

    // 기존 데이터(배열)가 있다면 id만 추가하고, 없다면 배열 새로 만듦
    data.ids = ids ? [...ids, id] : [id];

    // 위와 마찬가지 방식
    data.selectedIds = selectedIds ? [...selectedIds, id] : [id];
  });
}
