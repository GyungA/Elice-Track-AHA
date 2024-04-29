import * as Api from "../js/api.js";


categoryList(1);

async function categoryList(parentId) {
    try {
        const categories = await Api.get('http://localhost:8080/categories/${parentId}');
        const categoryList = document.getElementById('category-list');
        categories.forEach(category => {
            const listItem = document.createElement('li');
            const anchor = document.createElement('a');
            anchor.href = `/categories/${category.id}`; // 수정된 부분: 변수 부분을 정확히 `${}`로 감싸주었습니다.
            anchor.textContent = category.name;

            categoryList.appendChild(listItem);
            listItem.appendChild(anchor);
        });
    } catch (error) {
        console.error('Error loading categories:', error);
    }
}

// async function categoryList(parentId) {
//     try {
//         const categories = await Api.get(`http://localhost:8080/categories/${parentId}`);
//         const categoryList = document.getElementById('category-list');
//         if (categories.length > 0 && categoryList) {
//             categories.forEach(category => {
//                 const listItem = document.createElement('li');
//                 const anchor = document.createElement('a');
//                 anchor.href = `/categories/${category.id}`;
//                 anchor.textContent = category.name;
//
//                 listItem.appendChild(anchor);
//                 categoryList.appendChild(listItem);
//             });
//         } else {
//             console.log('No categories found or category-list element missing');
//         }
//     } catch (error) {
//         console.error('Error loading categories:', error);
//     }
// }

