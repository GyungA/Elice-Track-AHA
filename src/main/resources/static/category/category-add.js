import * as Api from "../js/api.js";
import { createNavbar } from "../js/useful-functions.js";

// 요소(element), input 혹은 상수
const nameInput = document.querySelector("#nameInput");
const topCategoryBox = document.querySelector("#TopCategoryBox");
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
  // topCategoryBox.addEventListener("click", selectTopCategories);

}

// 상위 카테고리 조회
async function selectTopCategories() {
    try {
        const categories = await Api.get('http://localhost:8080/categories/top-level'); // Api.get 사용하여 상위 카테고리 데이터 가져오기
        const topCategoryBox = document.getElementById('TopCategoryBox');
        categories.forEach(category => {
            const option = new Option(category.name, category.categoryId);
            topCategoryBox.appendChild(option); // 옵션 요소로 추가
        });
    } catch (error) {
        console.error('Error loading categories:', error);
    }
}

// addAllEvents() 에 포함시키면 중복으로 여러번 데이터를 불러와서 따로 불러오도록 설정
selectTopCategories();




// 카테고리 추가하기 - 카테고리 정보를 백엔드 db에 저장.
async function handleSubmit(e) {
  e.preventDefault();

    console.log(nameInput); // 요소 자체를 로그로 확인
    console.log(nameInput.value); // 입력된 값 확인

  const name = nameInput.value;
  const parentId = TopCategoryBox.value;

  if (!name.trim()) {
    return alert("카테고리 제목을 입력해 주세요.");
  }

  if (!parentId) {
    return alert("상위 카테고리를 선택해 주세요.");
  }

    const categoryData = { name, parentId };

    console.log(categoryData);

  try {
      console.log(name, parentId);

    await Api.post("http://localhost:8080/categories/api/add", categoryData);

    alert(`정상적으로 ${name} 카테고리가 등록되었습니다.`);

    // 폼 초기화
    registerCategoryForm.reset();

  } catch (err) {
    console.error(err.stack);
    alert(`문제가 발생하였습니다. 확인 후 다시 시도해 주세요: ${err.message}`);
  }
}



