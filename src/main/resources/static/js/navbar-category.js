import * as Api from "../js/api.js";

async function navbarTopCategories() {
    try {
        const categories = await Api.get('http://localhost:8080/categories/top-level'); // Api.get 사용하여 상위 카테고리 데이터 가져오기
        const navCategory = document.getElementById('navbar-category-dropdown');
        categories.forEach(category => {
            const option = new Option(category.name, category.id);
            navCategory.appendChild(option); // 옵션 요소로 추가
        });
    } catch (error) {
        console.error('Error loading categories:', error);
    }
}

navbarTopCategories();

async function navbarTopCategories() {
    try {
        const categories = await Api.get('http://localhost:8080/categories/top-level'); // Api.get 사용하여 상위 카테고리 데이터 가져오기
        const navCategory = document.getElementById('navbar-category-dropdown');
        categories.forEach(category => {
            const option = new Option(category.name, category.id);
            navCategory.appendChild(option); // 옵션 요소로 추가
        });
    } catch (error) {
        console.error('Error loading categories:', error);
    }
}