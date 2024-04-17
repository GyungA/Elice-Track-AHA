import * as Api from "../js/api.js";
import { createNavbar } from "../js/useful-functions.js";

// 요소(element), input 혹은 상수
const titleInput = document.querySelector("#titleInput");
const TopCategoryBox = document.querySelector("#TopCategoryBox");
const submitButton = document.querySelector("#addCategoryButton");
const registerCategoryForm = document.querySelector("#registerCategoryForm");

// checkLogin();
addAllElements();
addAllEvents();

// html에 요소를 추가하는 함수들을 묶어주어서 코드를 깔끔하게 하는 역할임.
async function addAllElements() {
  createNavbar();
}

// 여러 개의 addEventListener들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllEvents() {
  submitButton.addEventListener("click", handleSubmit);
  TopCategoryBox.addEventListener("change", handleColorChange);
}

// 상위 카테고리 조회
async function loadTopCategories() {
  try {
    const categories = await Api.get("http://localhost:8080/categories/top-level");
    const TopCategoryBox = document.getElementById('TopCategoryBox');
    categories.forEach(category => {
      const option = new Option(category.name, category.id);
      TopCategoryBox.appendChild(option);
    });
  } catch (error) {
    console.error('Error loading top categories:', error);
  }
}

loadTopCategories();


// 카테고리 추가하기 - 카테고리 정보를 백엔드 db에 저장.
async function handleSubmit(e) {
  e.preventDefault();

  const title = titleInput.value;
  const topCategory = TopCategoryBox.value;

  if (!title.trim()) {
    return alert("카테고리 제목을 입력해 주세요.");
  }

  if (!topCategory) {
    return alert("상위 카테고리를 선택해 주세요.");
  }

  try {
    const categoryData = { title, topCategory};

    await Api.post("http://localhost:8080/categories/api/add", categoryData);

    alert(`정상적으로 ${title} 카테고리가 등록되었습니다.`);

    // 폼 초기화
    registerCategoryForm.reset();
    fileNameSpan.innerText = "";

  } catch (err) {
    console.error(err.stack);
    alert(`문제가 발생하였습니다. 확인 후 다시 시도해 주세요: ${err.message}`);
  }
}



