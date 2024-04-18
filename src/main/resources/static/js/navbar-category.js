import * as Api from "../js/api.js";

navbarTopCategories();
navbarMenu();


async function navbarTopCategories() {
    try {
        const categories = await Api.get('http://localhost:8080/categories/top-level'); // 상위 카테고리 데이터 가져오기
        const navCategory = document.getElementById('navbar-category');
        categories.forEach(category => {
            const anchor = document.createElement('a'); // <a> 요소 생성
            anchor.href = `#${category.name.toLowerCase()}`; // href 속성 설정, 예: #food, #jewelry&fashion
            anchor.textContent = category.name; // 카테고리 이름으로 링크 텍스트 설정
            navCategory.appendChild(anchor); // 드롭다운 메뉴에 <a> 요소 추가
        });
    } catch (error) {
        console.error('Error loading categories:', error);
    }
}

async function navbarMenu() {

    const navbarLinks = document.querySelectorAll('.custom-navbar #navbar-menu');

    navbarLinks.forEach(link => {
        link.addEventListener('click', function(event) {
            event.preventDefault();
            alert('아직 준비중인 메뉴입니다.');  // 알림창 표시
        });
    });

}



// async function navbarTopCategories() {
//     try {
//         const categories = await Api.get('http://localhost:8080/categories/top-level'); // Api.get 사용하여 상위 카테고리 데이터 가져오기
//         const navCategory = document.getElementById('navbar-category');
//         categories.forEach(category => {
//             const option = new Option(category.name, category.id);
//             navCategory.appendChild(option); // 옵션 요소로 추가
//         });
//     } catch (error) {
//         console.error('Error loading categories:', error);
//     }
// }
//
// navbarTopCategories();

